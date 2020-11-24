<template>
  <div>
    <a-form :form="form"  label-width="120px" @submit.native.prevent>

      <spu-sku-list ref="spuSkuList" @getSelectData="getSelectData"></spu-sku-list>


      <a-form-item label="批量设置" style="margin-bottom: 20px" :labelCol="{span: 5}" :wrapperCol="{span: 19}">

            <a-input-search
              addon-before="数量" 
              onkeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
              placeholder="请输入数量"
              enter-button="设置"
              size="default"
              v-model="setStock" 
              style="width: 250px;margin-right: 20px" 
              @keyup.native="proving(1)"
              @search="clicksSet(1)"
            />

        </a-form-item>

      <a-form-item label="物品列表" :labelCol="{span: 0}" :wrapperCol="{span: 24}">
          <a-table :dataSource="tableData" border :pagination="pagination">
          
              <a-table-column  key="spuId" title="物品编号" align="center" width="100">
                <template slot-scope="scope">
                    <span>{{ scope.spuId }}</span>
                </template>
              </a-table-column>

              <a-table-column  key="title" title="物品名称" align="center" width="120">
                <template slot-scope="scope">
                    <span>{{ scope.title }}</span>
                </template>
              </a-table-column>

              <a-table-column  key="skuKey" title="规格" align="center" width="80">
                <template slot-scope="scope">
                    <span>{{ scope.skuKey }}</span>
                </template>
              </a-table-column>

              <a-table-column key="price" title="单价" align="center" width="60">
                <template slot-scope="scope">
                    <span>{{ scope.price }}</span>
                </template>
              </a-table-column>

              <a-table-column key="useNum" title="数量" align="center" width="150">
                <template slot-scope="scope">
                    <a-input onkeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" v-model="scope.useNum" style="width: 100px;"></a-input>
                </template>
              </a-table-column>

              <a-table-column key="action" title="操作" align="center" width="80">
                <template slot-scope="scope">
                    <button @click="del(scope)">删除</button>
                </template>
              </a-table-column>
    
          </a-table>
        </a-form-item>

      <a-form-item :wrapperCol="{span: 14, offset: 10}">
        <a-button :loading="loading" type="primary" @click="nextStep">提交</a-button>
        <a-button style="margin-left: 8px" @click="prevStep">上一步</a-button>
      </a-form-item>

    </a-form>

  </div>
</template>

<script>

    const defaultTable = {
        spuId: '',
        title: '',
        skuKey: '',
        price: '',
        useNum: '',
    };

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import SpuSkuList from '../SpuSkuList'

  export default {
    name: 'CaseStep3',
    mixins:[ mixinDevice, JeecgListMixin],
    components: {
      SpuSkuList
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
        description: 'spu_relation管理页面',
        url: {
          list: "/commodity/spuRelation/list",
          delete: "/commodity/spuRelation/delete",
          deleteBatch: "/commodity/spuRelation/deleteBatch",
          exportXlsUrl: "/commodity/spuRelation/exportXls",
          importExcelUrl: "commodity/spuRelation/importExcel",
          
        },
        tableData: [],
        selectionRows: [],
        pagination: false
      }
    },
    created(){

    },
    mounted() {
      // if (this.goods){
      //   let record = this.goods;
      //   this.edit(record);
      // }
    },
    computed: {
      // 用vuex读取数据(读取的是getters.js中的数据)
      // 相当于this.$store.getters.goods(vuex语法糖)
      // ...mapGetters(["goods"])
    },
    methods: {
      // ...mapActions([ "getSpecArr", "getTableData", "setOldTableData", "SetGoodsStore3" ]),
      nextStep () {
        const that = this;
        // 触发表单验证
        that.$emit('nextStep');
        // that.form.validateFields((err, values) => {
        //   if (!err) {
        //     that.confirmLoading = true;
        //     that.model.id = that.goods.id;
        //     let formData = Object.assign(that.model, values);
     
        //     that.SetGoodsStore3(formData).then((res) => {
        //       that.$emit('nextStep');
        //     }).catch((err) => {
        //       that.$message.warning(res.message);
        //     }).finally(() => {
        //       that.confirmLoading = false;
        //     });  
        //   }
        // })
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
      getSelectData (val) {
        let that = this;
        this.selectionRows = val;
        
        for(var i=0; i< this.selectionRows.length; i++){

          let dataArr = {
            id: that.selectionRows[i].id,
            spuId: that.selectionRows[i].spuId,
            title: that.selectionRows[i].spuId_dictText,
            skuKey: that.selectionRows[i].skuKey,
            price: that.selectionRows[i].price,
            useNum: 0
          }

          that.tableData.push(dataArr);
        }
      },
      del(scope){
        debugger;
      }
    }
  }
</script>

<style scoped>
  @import '~@assets/less/common.less';
</style>