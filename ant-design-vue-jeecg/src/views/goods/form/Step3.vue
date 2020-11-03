<template>
  <div>
    <a-form :form="form" style="margin: 40px auto 0;">

      <a-form-item
        label="启用规格"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
         <a-switch v-model="model.enableSku"/>
      </a-form-item>

      <div v-if="model.enableSku == true">
        <a-form-model
          ref="dynamicValidateForm"
          :model="dynamicValidateForm"
          v-bind="formItemLayoutWithOutLabel"
        >
          <a-form-model-item v-bind="formItemLayoutWithOutLabel">
            <a-button type="primary" style="width: 20%" @click="addDomain">
              <a-icon type="plus" /> 添加规格
            </a-button>
          </a-form-model-item>

          <a-form-model-item
            v-for="(sku, index) in dynamicValidateForm.skus"
            :key="sku.key"
            v-bind="index === 0 ? formItemLayout : {}"
            :label="index === 0 ? '规格' : ''"
            :prop="'skus.' + index + '.value'"
            :rules="{
              required: true,
              message: '规格不能为空',
              trigger: 'blur',
            }"
          >
            <a-input
              v-model="sku.value"
              placeholder="请输入规格"
              style="width: 20%; margin-right: 8px"
              @change = "skuInputChange"
            />
            <a-icon
              v-if="dynamicValidateForm.skus.length > 0"
              class="dynamic-delete-button"
              type="minus-circle-o"
              :disabled="dynamicValidateForm.skus.length === 1"
              @click="removeDomain(sku)"
            />
          </a-form-model-item>
          
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
     
      <a-form-item :wrapperCol="{span: 19, offset: 5}">
        <a-button :loading="loading" type="primary" @click="nextStep">下一步</a-button>
        <a-button style="margin-left: 8px" @click="prevStep">上一步</a-button>
      </a-form-item>
    </a-form>

    <image-upload-modal ref="modalForm" @ok="modalFormOk" :images="imagesStr"></image-upload-modal>

  </div>
</template>

<script>
  import pick from 'lodash.pick'
  import moment from 'moment'
  import { FormTypes } from '@/utils/JEditableTableUtil'
  import JEditor from '@/components/jeecg/JEditor'
  import JEditableTable from '@/components/jeecg/JEditableTable'
  import ImageUploadModal from './ImageUploadModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { mapGetters, mapActions } from "vuex";

  export default {
    name: "Step3",
    components: {
      JEditor,
      JEditableTable,
      JeecgListMixin,
      ImageUploadModal
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
          enableSku: false
        },
        columns: [
          {
            title: '单价',
            key: 'price',
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
        imagesStr: ''
      }
    },
    mounted() {
        if (this.goods.id != null && this.goods.id != ""){
        let record = this.goods;
        this.edit(record);
      }
    },
    computed: {
      // 用vuex读取数据(读取的是getters.js中的数据)
      // 相当于this.$store.getters.goods(vuex语法糖)
      ...mapGetters(["goods"])
    },
    methods: {
      ...mapActions([ "UpdateGoodsInfo" ]),
      nextStep () {
        const that = this;
        // 触发表单验证
        that.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            that.model.id = that.goods.id;
            let formData = Object.assign(that.model, values);
  
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
                    //设置表格值
                    // that.editableTable.push([
                    //   {
                    //     rowKey: keyId, // 行的id
                    //     values: { // 在这里 values 中的 name 是你 columns 中配置的 key
                    //         'name': 'skuKey',
                    //         'skuKey': str
                    //     }
                    //   }
                    // ]);
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
                  that.UpdateGoodsInfo(formData).then((res) => {
                    this.$emit('nextStep');
                  }).catch((err) => {
                    that.$message.warning(res.message);
                  }).finally(() => {
                    that.confirmLoading = false;
                  });

              } else {
                  // this.$message.error('验证未通过')
              }
            })
            
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
      addDomain() {
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
      handleUploadImage(props) {

        let { rowId, caseId, allValues, target } = props
        this.rowId = rowId;
        let key = caseId + rowId;
        let imagesStr = '';
        let values = allValues.inputValues;
        let size = values.length;
        for(var i=0; i<size; i++){
          if(values[i]['id'] == key){
            imagesStr = values[i]['images'];
            break;
          }
        }
    
        if(imagesStr != '' || imagesStr != undefined){
          let arr = [];
          let imagesArr = [];
          arr = imagesStr.split(',');
          if(arr.length > 0){
            for(var i=0;i<arr.length;i++){
              let url = arr[i];
              if(url){
                var newStr=url.indexOf("http");
                if(newStr==0){
                  imagesArr.push(url);  
                }else{
                  imagesArr.push(window._CONFIG['domianURL']+"/"+url);      
                }
                  
              }
              
              // this.imageList.push(
              //   {
              //     uid: Date.now(),
              //     name: url,
              //     status: 'done',
              //     url: window._CONFIG['domianURL']+"/"+url,
              //   }
              // );

            }
            // debugger;
            this.imagesStr = imagesArr.join(",");
          }
          
        }
        this.$refs.modalForm.visible = true;
        this.$refs.modalForm.title = "图片管理";
        this.$refs.modalForm.disableSubmit = false;
      },
      modalFormOk(data) {
        
        if(!data) return;

        this.$refs.editableTable.setValues([
          {
            rowKey: this.rowId, // 行的id
            values: { // 在这里 values 中的 name 是你 columns 中配置的 key
                'name': 'images',
                'images': data
            }
          }
        ]);
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
        
      }
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