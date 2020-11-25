<template>
  <div>
    <a-form :form="form"  label-width="120px" @submit.native.prevent>

      <a-form-item
        label="启用规格"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
         <a-switch v-model="model.enableSpecialSpec"/>
      </a-form-item>

      <div v-if="model.enableSpecialSpec == true">

        <!-- <a-form-item label="规格组和规格值" :labelCol="{span: 5}" :wrapperCol="{span: 19}">
          <a-input-search
            addon-before="规格组" 
            onkeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
            placeholder="请输入内容"
            enter-button="设置"
            size="default"
            v-model="specName" 
            style="width: 400px" 
            @keyup.enter.native="createdSpecifi"
            @search="createdSpecifi"
          />
        </a-form-item> -->

        <a-form-item label="" :labelCol="{span: 0}" :wrapperCol="{span: 24}" style="margin: 10px 0 20px 0">
            <div class="introTitle" v-for="(tab, indexs) in specArr" :key="indexs">
                <span class="fontWidth">{{tab.name}}</span>
                <span class="delete clear" @click="clearSpe(indexs)">×</span>
                <div class="introCon">
                    <ul>
                        <li v-for="(val, index) in tab.conName" :key="index">
                            <span>{{val}}</span>
                            <span class="delete" @click="clearSpecif(indexs, index)">×</span>
                        </li>
                        <li>
                            <a-input-search
                              addon-before="规格值" 
                              onkeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
                              placeholder="请输入内容"
                              enter-button="设置"
                              size="default"
                              v-model="tab.addField" 
                              style="width: 250px" 
                              @keyup.enter.native="clickSpecVal(indexs, tab.addField)"
                              @search="clickSpecVal(indexs, tab.addField)"
                            />
                        </li>
                    </ul>
                </div>
            </div>
        </a-form-item>

        <a-form-item label="批量设置" style="margin-bottom: 20px" :labelCol="{span: 5}" :wrapperCol="{span: 19}" v-if="tableData.length > 0">

            <a-input-search
              addon-before="单价" 
              onkeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
              placeholder="请输入单价"
              enter-button="设置"
              size="default"
              v-model="setPrice" 
              style="width: 250px;margin-right: 20px" 
              @keyup.native="proving(2)"
              @search="clicksSet(2)"
            />

            <a-input-search
              addon-before="库存" 
              onkeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
              placeholder="请输入库存"
              enter-button="设置"
              size="default"
              v-model="setStock" 
              style="width: 250px;margin-right: 20px" 
              @keyup.native="proving(1)"
              @search="clicksSet(1)"
            />
        </a-form-item>

        <a-form-item label="价格和库存" :labelCol="{span: 0}" :wrapperCol="{span: 24}" v-if="tableData.length > 0">
          <a-table :dataSource="tableData" border :pagination="pagination">
          
              <a-table-column v-for="(item, index) in specArr" :key="index" :title="item.name" align="center" width="10%">
                <template slot-scope="scope">
                    <span>{{ scope.specs[index] }}</span>
                </template>
              </a-table-column>
              <a-table-column key="price" title="单价" align="center" width="20%">
                <template slot-scope="scope">
                    <a-input onkeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" v-model="scope.price" style="width: 100px;"></a-input>
                </template>
              </a-table-column>
              <a-table-column key="stock" title="库存" align="center" width="20%">
                <template slot-scope="scope">
                    <a-input onkeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" v-model="scope.stock" style="width: 100px;"></a-input>
                </template>
              </a-table-column>

              <!-- <a-table-column key="indexs" title="indexs" align="center" width="20%">
                <template slot-scope="scope">
                    <a-input onkeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" v-model="scope.indexes" style="width: 100px;"></a-input>
                </template>
              </a-table-column> -->
              
              <a-table-column key="images" title="图片" align="center">
                <template slot-scope="scope">
                    <!-- {{scope.picture}} -->

                    <j-image-upload v-model="scope.images" :data="{biz:bizPath}" :isMultiple="isMultiple"></j-image-upload>

                </template>
              </a-table-column>
          </a-table>
        </a-form-item>
        <!-- <p>{{specArr}}</p> -->
        <!--<p>{{allData}}</p>-->
        <!--<p>{{tableData}}</p>-->

      </div>

      <a-form-item :wrapperCol="{span: 14, offset: 10}">
        <a-button :loading="loading" type="primary" @click="nextStep">下一步</a-button>
        <a-button style="margin-left: 8px" @click="prevStep">上一步</a-button>
      </a-form-item>

    </a-form>
  </div>
</template>

<script>

  import Vue from 'vue'
  import pick from 'lodash.pick'
  import moment from 'moment'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { mapGetters, mapActions } from "vuex";
  import { randomUUID, randomNumber } from '@/utils/util'
  import { ACCESS_TOKEN } from "@/store/mutation-types"
  import { getFileAccessHttpUrl, httpAction, getAction } from '@/api/manage'
  import JImageUpload from '@/components/jeecg/JImageUpload'
  import descartes from "@/utils/descartes"

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

  const defaultTable = {
        specs: [],
        stock: 0,
        price: "0.00",
        images: "",
        id: ""
    };

  export default {
    name: "Step3",
    components: {
      JImageUpload,
      JeecgListMixin
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
    data () {
      return {
        loading: false,
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
        specName: "",
        specArr: [],
        specIdArr: [],
        specContent: {},
        tableData: [],
        changeTableData: [],
        allData: [],
        setStock: "",
        setPrice: "",
        pagination: false,
        url: {
          list: "/commodity/specGroup/querySpecialList"
        },
        model: {
          enableSpecialSpec: false,
        },
        //
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
      ...mapActions([ "getSpecArr", "getTableData", "setOldTableData", "SetGoodsStore3" ]),
      // 点击添加规格组
      createdSpecifi() {
          if (this.specName) {
              const data = {
                  id: "",
                  name: "",
                  conName: [],
                  addField: ''
              };
              this.specContent = Object.assign({}, data);
              this.specContent.id = this.id;
              this.specContent.name = this.specName;
              this.specContent.conName = [];
              this.specArr.push(this.specContent);
              let obj = {};
  
              this.specArr = this.specArr.reduce((cur, next) => {
                  obj[next.name] ? "" : (obj[next.name] = true && cur.push(next));
                  return cur;
              }, []);

              this.specName = "";
              this.$store.dispatch("getSpecArr", this.specArr);
          }
      },
      // 点击添加规格值
      clickSpecVal(indexs, name) {
          if (name) {
              this.specArr[indexs].conName.push(name);
              this.specArr[indexs].conName = [
                  ...new Set(this.specArr[indexs].conName)
              ];
              for(const val in this.specArr) {
                  if(this.specArr[val].conName.length === 0) {
                      this.changeTableData[val] = ['']
                  } else {
                      this.changeTableData[val] = this.specArr[val].conName
                  }
              }
              this.changeDataTable(this.changeTableData, this.specArr);
              this.specArr[indexs].addField = "";
              this.$store.dispatch("getSpecArr", this.specArr);
          }
      },
      // 删除规格组
      clearSpe(indexs) {
          this.specArr.splice(indexs, 1);
          this.changeTableData = [];
          for (const item of this.specArr) {
              this.changeTableData.push(item.conName);
          }
          this.changeDataTable(this.changeTableData, this.specArr);
          this.$store.dispatch("getSpecArr", this.specArr);
      },
      // 删除规格值
      clearSpecif(indexs, index) {
          this.specArr[indexs].conName.splice(index, 1);
          this.changeTableData[indexs] = this.specArr[indexs].conName;
          for (const item in this.changeTableData) {
              if (this.changeTableData[item].length === 0) {
                  this.changeTableData.splice(item, 1);
              }
          }
          this.changeDataTable(this.changeTableData, this.specArr);
          this.$store.dispatch("getSpecArr", this.specArr);
      },
      // 整理数据
      changeDataTable(m, n) {
          //保存当前表格数据
          if(this.tableData.length > 0){
            this.$store.dispatch("setOldTableData", this.tableData);
          }
          //
          this.tableData = []
          this.allData = descartes(m);
          if (n.length > 1) {
              for (const i in this.allData) {
                  const dataArr = Object.assign({}, defaultTable);
                  if (typeof this.allData[i] === "string") {
                      dataArr.specs[i] = this.allData[i];
                  } else {
                      dataArr.specs = this.allData[i];
                  }
          
                  //设置indexs
                  let indexes = dataArr.specs.join(",");
                  if(indexes.substr(indexes.length-1, 1) == ","){
                    indexes = indexes.substr(0, indexes.length - 1);
                  }

                  dataArr['skuKey'] = indexes;
                  this.tableData.push(dataArr);
                  this.$store.dispatch("getTableData", this.tableData);
              }
          } else {
              for (const i in this.allData) {
                  const dataArr = Object.assign({}, defaultTable);
                  dataArr.specs = [this.allData[i]];
        
                  //设置indexs
                  let indexes = dataArr.specs.join(",");
                  if(indexes.substr(indexes.length-1, 1) == ","){
                    indexes = indexes.substr(0, indexes.length - 1);
                  }
                  dataArr['skuKey'] = indexes;
                  this.tableData.push(dataArr);
                  this.$store.dispatch("getTableData", this.tableData);
              }
          }
          debugger;
          //取出old数据，然后向当前表格赋值
          let oldData = this.goods.oldTableData;
          for(var i=0;i<this.tableData.length-1;i++){
            this.tableData[i]['price'] = oldData[i]['price']
            this.tableData[i]['stock'] = oldData[i]['stock']
            this.tableData[i]['images'] = oldData[i]['images']
          }
      },
      clicksSet(name) {
        for (const item of this.tableData) {
            if (name === 1 && this.setStock != '') {
                item.stock = this.setStock;
            } else if (name === 2 && this.setPrice != '') {
                item.price = this.setPrice;
            }
        }
      },
      proving(num) {
          if (num === 1) {
              this.setStock = this.setStock.replace(/[^\.\d]/g, "");
              this.setStock = this.setStock.replace(".", "");
          } else if (num === 2) {
              this.setPrice = this.setPrice.replace(/[^\.\d]/g, "");
              this.setPrice = this.setPrice.replace(".", "");
          }
      },
      //功能方法
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

              for(var i=0; i<skus.length; i++){
                let sku = skus[i];
                let specName = sku.name;
                if (specName) {
                    const data = {
                        id: "",
                        name: "",
                        conName: [],
                        addField: ''
                    };
                    that.specContent = Object.assign({}, data);
                    that.specContent.id = sku.id;
                    that.specContent.name = specName;
                    that.specContent.conName = [];
                    that.specArr.push(that.specContent);
                    let obj = {};
                    that.specArr = that.specArr.reduce((cur, next) => {
                        obj[next.name] ? "" : (obj[next.name] = true && cur.push(next));
                        return cur;
                    }, []);
                    that.specName = "";
                    that.$store.dispatch("getSpecArr", that.specArr);
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
     
            that.SetGoodsStore3(formData).then((res) => {
              that.$emit('nextStep');
            }).catch((err) => {
              that.$message.warning(res.message);
            }).finally(() => {
              that.confirmLoading = false;
            });  
            
            
          }
         
        })
      },
      prevStep () {
        this.$emit('prevStep')
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        if(this.model.enableSpecialSpec == "") this.model.enableSpecialSpec = false;
        this.tableData = this.model.tableData;
        this.specArr = this.model.specArr;
      },
    }
  }
</script>

<style scoped>
    html,
    body,
    div,
    span,
    p,
    i,
    ul,
    li {
        margin: 0;
        padding: 0;
    }

    .a-row {
        margin-bottom: 20px;
    }

    .a-col {
        border-radius: 4px;
    }

    .introTitle {
        border: 1px solid rgb(238, 238, 238);
        padding-left: 10px;
        padding-top: 10px;
        line-height: 30px;
        margin-bottom: 10px;
    }

    .fontWidth {
        font-weight: 500;
        font-size: 16px;
        display: inline-block;
        padding: 0 10px;
        vertical-align: middle;
    }

    .introTitle i {
        cursor: pointer;
    }

    .introCon ul {
        list-style: none;
        padding: 5px 0 10px 0;
    }

    .introCon ul li {
        display: inline-block;
        margin: 5px 10px;
        background-color: rgb(238, 238, 238);
        line-height: 30px;
    }

    .introCon ul li:last-child {
        background-color: #ffffff;
        width: 300px;
    }

    .introCon ul li span:nth-child(1) {
        display: inline-block;
        padding: 0 10px;
        text-align: center;
    }

    .introCon ul li span:nth-child(2) {
        display: inline-block;
        width: 30px;
        background-color: rgb(211, 207, 208);
        text-align: center;
    }

    .delete {
        color: #ffffff;
        cursor: pointer;
    }

    .clear {
        background-color: rgb(238, 238, 238);
        display: inline-block;
        width: 15px;
        line-height: 15px;
        height: 15px;
        vertical-align: middle;
        border-radius: 50%;
        text-align: center;
    }

    /deep/ .ant-upload.ant-upload-select-picture-card{
      width: 60px;
      height: 30px;
    }

    /deep/ .ant-upload-list-picture-card .ant-upload-list-item {
      float: left;
      width: 60px;
      height: 60px;
      margin: 0 8px 8px 0;
    }

     /deep/ .ant-upload-list-picture-card-container {
      float: left;
      width: 60px;
      height: 60px;
      margin: 0 8px 8px 0;
    } 

</style>