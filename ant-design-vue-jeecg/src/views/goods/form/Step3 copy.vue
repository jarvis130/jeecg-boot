<template>
  <div>
    <a-form :form="form" style="margin: 40px auto 0;">

      <a-form-item
        label="启用规格"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
         <a-switch v-model="model.enableSpecialSpec"/>
      </a-form-item>

      <div v-if="model.enableSpecialSpec == true">
        <a-form-model
          ref="dynamicValidateForm"
          :model="dynamicValidateForm"
          v-bind="formItemLayoutWithOutLabel"
        >

          <a-form-item
            v-for="(sku, index) in dynamicValidateForm.skus"
            :key="sku.key"
            v-bind="{}"
            :label="sku.name"
            :prop="'skus.' + index + '.value'"
            :rules="{
              required: true,
              message: '规格值不能为空',
              trigger: 'blur',
            }"
            :labelCol="{span: 5}"
            :wrapperCol="{span: 19}"
          >
            <a-input
              v-model="sku.segments"
              placeholder="请输入规格值"
              style="width: 40%; margin-right: 8px"
              @change = "skuInputChange"
            />

          </a-form-item>
          
          <a-divider></a-divider>

          <a-form-item
            v-if="dynamicValidateForm.skus.length > 0"
            label=""
            :labelCol="{span: 2}"
            :wrapperCol="{span: 24}"
          >
            <j-editable-table
              ref="editableTable"
              :loading="loading"
              :columns="columns"
              :dataSource="dataSource"
              :dragSort="true"
              :actionButton="true"
              style="margin-top: 8px;"
              @selectRowChange="handleSelectRowChange">
              <template v-slot:action="props">
                <a @click="handleUploadImage(props)">图片管理</a>
                <a-divider type="vertical" />
                <a @click="handleDelete(props)">删除</a>
              </template>

            </j-editable-table>
          </a-form-item>

        </a-form-model>

      </div>
      
      <br>
     
      <a-form-item :wrapperCol="{span: 14, offset: 10}">
        <a-button :loading="loading" type="primary" @click="nextStep">下一步</a-button>
        <a-button style="margin-left: 8px" @click="prevStep">上一步</a-button>
      </a-form-item>
    </a-form>

    <j-modal
      :title="title"
      :width="width"
      :visible="visible"
      switchFullscreen
      destroyOnClose
      @ok="handleOk"
      @cancel="handleModalCancel"
      cancelText="关闭">

      <a-upload
        name="file"
        listType="picture-card"
        :multiple="isMultiple"
        :action="uploadAction"
        :headers="headers"
        :data="{biz:bizPath}"
        :fileList="fileList"
        :beforeUpload="beforeUpload"
        :disabled="disabled"
        :isMultiple="isMultiple"
        :showUploadList="isMultiple"
        @change="handleChange"
        @preview="handlePreview">
        <img v-if="!isMultiple && picUrl" :src="getAvatarView()" style="height:104px;max-width:300px"/>
        <div v-else >
          <a-icon :type="uploadLoading ? 'loading' : 'plus'" />
          <div class="ant-upload-text">{{ text }}</div>
        </div>
        <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel()">
          <img alt="" style="width: 100%" :src="previewImage"/>
        </a-modal>
      </a-upload>
  
    </j-modal>


  </div>
</template>

<script>

  import Vue from 'vue'
  import pick from 'lodash.pick'
  import moment from 'moment'
  import { FormTypes } from '@/utils/JEditableTableUtil'
  import JEditor from '@/components/jeecg/JEditor'
  import JEditableTable from '@/components/jeecg/JEditableTable'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { mapGetters, mapActions } from "vuex";
  import { randomUUID, randomNumber } from '@/utils/util'
  import { ACCESS_TOKEN } from "@/store/mutation-types"
  import { getFileAccessHttpUrl, httpAction, getAction } from '@/api/manage'

  const uidGenerator=()=>{
    return '-'+parseInt(Math.random()*10000+1,10);
  }
  const getFileName=(path)=>{
    if(path.lastIndexOf("\\")>=0){
      let reg=new RegExp("\\\\","g");
      path = path.replace(reg,"/");
    }
    return path.substring(path.lastIndexOf("/")+1);
  }

  export default {
    name: "Step3",
    components: {
      JEditor,
      JEditableTable,
      JeecgListMixin
    },
    data () {
      return {
        form: this.$form.createForm(this),
        formItemLayout: {
          labelCol: {
            xs: { span: 24 },
            sm: { span: 4 },
          },
          wrapperCol: {
            xs: { span: 24 },
            sm: { span: 20 },
          },
        },
        formItemLayoutWithOutLabel: {
          wrapperCol: {
            xs: { span: 24, offset: 0 },
            sm: { span: 20, offset: 4 },
          },
        },
        dynamicValidateForm: {
          skus: [],
        },
        loading: false,
        model: {
          enableSpecialSpec: false,
        },
        columns: [
          {
            title: '单价',
            key: 'salePrice',
            width: '120px',
            type: FormTypes.inputNumber,
            defaultValue: 0.00,
            placeholder: '${title}',
            validateRules: [{ required: true, message: '请输入${title}' }]
          },
          {
            title: '库存',
            key: 'stock',
            width: '120px',
            type: FormTypes.inputNumber,
            defaultValue: '0',
            placeholder: '请选择${title}',
            validateRules: [{ required: true, message: '请选择${title}' }]
          },
          {
            title: '图库',
            key: 'images',
            width: '200px',
            type: FormTypes.hidden
          },
          {
            title: 'skuKey',
            key: 'skuKey',
            width: '200px',
            type: FormTypes.hidden
          },
          {
            title: '操作',
            key: 'action',
            // width: '8%',
            width: '200px',
            type: FormTypes.slot,
            slotName: 'action',
          }

        ],
        dataSource: [],
        selectedRowIds: [],
        dataSourceStr: '',
        rowId: 0,
        //图片上传参数
        visible: false,
        // isMultiple: true,
        fileList: [],
        title: '',
        width: 800,
        headers:{},
        uploadAction: window._CONFIG['domianURL']+"/sys/common/upload",
        uploadLoading:false,
        picUrl:false,
        headers:{},
        previewImage:"",
        previewVisible: false,
        url: {
          list: "/commodity/specGroup/querySpecialList"
        }
      }
    },
    props:{
      text:{
        type:String,
        required:false,
        default:"上传"
      },
      /*这个属性用于控制文件上传的业务路径*/
      bizPath:{
        type:String,
        required:false,
        default:"skupic"
      },
      value:{
        type:[String,Array],
        required:false
      },
      disabled:{
        type:Boolean,
        required:false,
        default: false
      },
      isMultiple:{
        type:Boolean,
        required:false,
        default: true
      }
    },
    watch:{
      value(val){
        if (val instanceof Array) {
          this.initFileList(val.join(','))
        } else {
          this.initFileList(val)
        }
        if(!val || val.length==0){
          this.picUrl = false;
        }
      }
    },
    created(){
      const token = Vue.ls.get(ACCESS_TOKEN);
      this.headers = {"X-Access-Token":token}
    },
    mounted() {
      if (this.goods){
        let record = this.goods;
        this.edit(record);
      }
      this.loadSpecialData();
    },
    computed: {
      // 用vuex读取数据(读取的是getters.js中的数据)
      // 相当于this.$store.getters.goods(vuex语法糖)
      ...mapGetters(["goods"])
    },
    methods: {
      ...mapActions([ "SetGoodsStore3" ]),
      loadSpecialData() {
        if(!this.url.list){
          this.$message.error("请设置url.list属性!")
          return
        }
        let that = this;
        this.loading = true;
        let param = {
          cateId: this.goods.cid3
        }
        getAction(this.url.list, param).then((res) => {
          if (res.success) {
     
            //渲染组件 
            let skus = res.result;
            if(skus instanceof Array){
              that.dynamicValidateForm.skus = skus;
              for(var i=skus.length-1; i>=0; i-- ){
                let key = Date.now();
                let sku = skus[i];
                // this.dynamicValidateForm.skus.unshift({
                //   value: name,
                //   key: key,
                // });
                let str = sku['segments'];
                if(str){
                  let arr = str.split(',');
                  let tmp = [];
                  if(arr instanceof Array){
                    for(var j=0; j< arr.length; j++){
                      if(arr[j]){
                        tmp.push({
                          'title': arr[j],
                          'value': randomUUID() + j
                        });
                      }
                    }
                    that.columns.unshift(
                      {
                        title: sku['name'],
                        key: key,
                        width: '120px',
                        type: FormTypes.select,
                        options: tmp,
                        defaultValue: '',
                        placeholder: '请输入规格值',
                        validateRules: [{ required: true, message: '请输入规格值' }]
                      }
                    ); 
                  }
                     
                }
                
              }
            }
            
            //
          }
          if(res.code===510){
            that.$message.warning(res.message)
          }
          that.loading = false;
        })
      },
      nextStep () {
        const that = this;
        // 触发表单验证

        that.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            that.model.id = that.goods.id;
            let formData = Object.assign(that.model, values);
     
            if(that.model.enableSpecialSpec){

              //将表格数据解析成字符串
              this.$refs.editableTable.getValues((error, values) => {
                
                that.editableTable=values;
                // 错误数 = 0 则代表验证通过
                if (error === 0) {
      
                    //设置sku_key值
                    let str='';
                    let length = values.length;
                    let size = that.dynamicValidateForm.skus.length;
                    for(var j=0;j<length;j++){
                      let item = values[j];
                      let keyId = item['id'];
                      let tmpArr = [];
                      for(var i=0; i < size; i++){
                        let sku = that.dynamicValidateForm.skus[i];
                        let key = sku['key'];
                        tmpArr.push(item[key]);
                        str = tmpArr.join(",");
                      }
            
                      that.editableTable[j]['skuKey']=str;
                    }
                  
                    // 将通过后的数组提交到后台或自行进行其他处理
                    let arr = {
                      skus: that.dynamicValidateForm.skus,
                      columns: that.columns,
                      dataSource: that.editableTable    
                    };
                    formData.skuJsonData = JSON.stringify(arr);
          
                    console.log("表单提交数据",formData)

                    that.SetGoodsStore3(formData).then((res) => {
                      that.$emit('nextStep');
                    }).catch((err) => {
                      that.$message.warning(res.message);
                    }).finally(() => {
                      that.confirmLoading = false;
                    });

                } else {
                    // this.$message.error('验证未通过')
                }
              })

            }else{

              that.SetGoodsStore3(formData).then((res) => {
                that.$emit('nextStep');
              }).catch((err) => {
                that.$message.warning(res.message);
              }).finally(() => {
                that.confirmLoading = false;
              });   

            }
            
            
          }
         
        })
      },
      prevStep () {
        this.$emit('prevStep')
      },
      removeDomain(item) {
        let index = this.dynamicValidateForm.skus.indexOf(item);
        if (index !== -1) {
          this.dynamicValidateForm.skus.splice(index, 1);
          this.columns.splice(index, 1);
        }
      },
      /**新增规格 */
      addSku() {
        let length = this.dynamicValidateForm.skus.length + 1;
        let name = '规格'+length;
        let key = Date.now();
        this.dynamicValidateForm.skus.unshift({
          value: name,
          key: key,
        });
        this.columns.unshift(
          {
            title: name,
            key: key,
            width: '120px',
            type: FormTypes.input,
            defaultValue: '',
            placeholder: '请输入规格值',
            validateRules: [{ required: true, message: '请输入规格值' }]
          }
        );
      },
      /**修改规格时触发 */
      skuInputChange() {
        let _this = this;
        let size = _this.dynamicValidateForm.skus.length;
        for (let i = 0; i < size; i++) {
          let value = _this.dynamicValidateForm.skus[i].value;
          _this.columns[i].title = value;
        }
      },
      /**Jtable */
       /** 表单验证 */
      handleTableCheck() {
        this.$refs.editableTable.getValues((error) => {
          if (error === 0) {
            this.$message.success('验证通过')
          } else {
            this.$message.error('验证未通过')
          }
        })
      },
      /** 获取值，忽略表单验证 */
      handleTableGet() {
        this.$refs.editableTable.getValues((error, values) => {
          console.log('values:', values)
        }, false)
        console.log('deleteIds:', this.$refs.editableTable.getDeleteIds())

        this.$message.info('获取值成功，请看控制台输出')

      },
      handleSelectRowChange(selectedRowIds) {
        this.selectedRowIds = selectedRowIds
      },
      handleDelete(props) {
        let { rowId, target } = props
        target.removeRows(rowId)
      },
      /**图片管理 */
      handleUploadImage(props) {

        let { rowId, index, caseId, allValues, target } = props
     
        this.rowId = rowId;
        let imageStr = '';
        let images = [];
        let tmpArr = [];
        let values = allValues.inputValues;
        if(values){
          imageStr = values[index]['images'];
        }

        if(imageStr != '' && imageStr != undefined){
          images = imageStr.split(',');
          if(images.length > 0){
            let url = '';
            if(images.length > 0){
              for(var i=0;i<images.length;i++){
                let tmp = images[i];
  
                tmpArr.push(
                  {
                    uid: Date.now(),
                    name: tmp,
                    status: 'done',
                    url: window._CONFIG['domianURL']+"/"+ tmp,
                    // thumbUrl: tmp['thumbUrl'],
                    // type: tmp['type']
                  }
                );

              }
  
            }
            this.fileList.splice(0, this.fileList.length);// 清空fileList,重新赋值
            this.fileList = tmpArr;
          }
        }
         
        // const token = Vue.ls.get(ACCESS_TOKEN);
        // this.headers = {"X-Access-Token":token}
        this.visible = true;
       
      },
      handleModalCancel () {
        this.visible = false;
      },
      handleOk (e) {
        
        if(!this.fileList) return;
       
        let files = this.fileList;
        let arr=[];
        for(var i=0; i<files.length; i++){
          let file = files[i];
          arr.push(
            // {
            //   'uid': file['uid'],
            //   'name': file['name'],
            //   'status': file['status'],
            //   // 'thumbUrl': file['thumbUrl'],
            //   'type': file['type'],
            // }
            file['name']
          );
        }
        //将数组转化为字符串
        let str = arr.join(',');
        this.$refs.editableTable.setValues([
          {
            rowKey: this.rowId, // 行的id
            values: { // 在这里 values 中的 name 是你 columns 中配置的 key
                'name': 'images',
                'images': str
            }
          }
        ]);
      
        this.fileList.splice(0, this.fileList.length);// 清空fileList
        this.visible = false;
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        let skusJsonData = this.model.skuJsonData;
        if(skusJsonData){
          let arr = JSON.parse(skusJsonData);
          this.columns = arr.columns;
          this.dataSource = arr.dataSource;
          this.dynamicValidateForm.skus = arr.skus || [];
        }
        
      },
      /**图片处理 */
      initFileList(paths){
        debugger;
        if(!paths || paths.length==0){
          this.fileList = [];
          return;
        }
        this.picUrl = true;
        let fileList = [];
        let arr = paths.split(",")
        for(var a=0;a<arr.length;a++){
          let url = getFileAccessHttpUrl(arr[a]);
          fileList.push({
            uid: uidGenerator(),
            name: getFileName(arr[a]),
            status: 'done',
            url: url,
            response:{
              status:"history",
              message:arr[a]
            }
          })
        }
        this.fileList = fileList
      },
      beforeUpload: function(file){
        var fileType = file.type;
        if(fileType.indexOf('image')<0){
          this.$message.warning('请上传图片');
          return false;
        }
      },
      handleChange(info) {
        this.picUrl = false;
        let fileList = info.fileList
        if(info.file.status==='done'){
          if(info.file.response.success){
            this.picUrl = true;
            fileList = fileList.map((file) => {
              if (file.response) {
                file.url = file.response.message;
              }
              return file;
            });
          }
          //this.$message.success(`${info.file.name} 上传成功!`);
        }else if (info.file.status === 'error') {
          this.$message.error(`${info.file.name} 上传失败.`);
        }else if(info.file.status === 'removed'){
          this.handleFileDelete(info.file)
        }
        this.fileList = fileList
        if(info.file.status==='done' || info.file.status === 'removed'){
          this.handlePathChange()
        }
      },
      // 预览
      handlePreview (file) {
        this.previewImage = file.url || file.thumbUrl
        this.previewVisible = true
      },
      getAvatarView(){
        if(this.fileList.length>0){
          let url = this.fileList[0].url
          return getFileAccessHttpUrl(url)
        }
      },
      handlePathChange(){
        let uploadFiles = this.fileList
        let path = ''
        if(!uploadFiles || uploadFiles.length==0){
          path = ''
        }
        let arr = [];
        if(!this.isMultiple){
          arr.push(uploadFiles[uploadFiles.length-1].response.message)
        }else{
          for(let a=0;a<uploadFiles.length;a++){
            // update-begin-author:taoyan date:20200819 for:【开源问题z】上传图片组件 LOWCOD-783
            if(uploadFiles[a].status === 'done' ) {
              arr.push(uploadFiles[a].response.message)
            }else{
              return;
            }
            // update-end-author:taoyan date:20200819 for:【开源问题z】上传图片组件 LOWCOD-783
          }
        }
        if(arr.length>0){
          path = arr.join(",")
        }
        this.$emit('change', path);
      },
      handleFileDelete(file){
        //如有需要新增 删除逻辑
        console.log(file)
      },
      handleCancel() {
        this.close();
        this.previewVisible = false;
      },
      close () {

      },
    },
    model: {
      prop: 'value',
      event: 'change'
    }
  }
</script>

<style lang="less" scoped>
  .stepFormText {
    margin-bottom: 24px;

    .ant-form-item-label,
    .ant-form-item-control {
      line-height: 22px;
    }
  }

  .dynamic-delete-button {
    cursor: pointer;
    position: relative;
    top: 4px;
    font-size: 24px;
    color: #999;
    transition: all 0.3s;
  }
  .dynamic-delete-button:hover {
    color: #777;
  }
  .dynamic-delete-button[disabled] {
    cursor: not-allowed;
    opacity: 0.5;
  }
</style>