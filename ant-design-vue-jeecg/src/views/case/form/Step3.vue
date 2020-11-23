<template>
  <div>
    <a-form :form="form"  label-width="120px" @submit.native.prevent>

      <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('spu_relation')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <spu-relation-modal ref="modalForm" @ok="modalFormOk"></spu-relation-modal>
  </a-card>

      <a-form-item :wrapperCol="{span: 14, offset: 10}">
        <a-button :loading="loading" type="primary" @click="nextStep">下一步</a-button>
        <a-button style="margin-left: 8px" @click="prevStep">上一步</a-button>
      </a-form-item>

    </a-form>
  </div>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  // import SpuRelationModal from './modules/SpuRelationModal'

  export default {
    name: 'SpuRelationList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      // SpuRelationModal
    },
    data () {
      return {
        description: 'spu_relation管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'商品编号',
            align:"center",
            dataIndex: 'spuId'
          },
          {
            title:'关联的商品编号',
            align:"center",
            dataIndex: 'relationId'
          },
          {
            title:'使用商品数量，医药方案场景使用',
            align:"center",
            dataIndex: 'useNum'
          },
          {
            title:'使用的商品规格',
            align:"center",
            dataIndex: 'skuId'
          },
          {
            title:'商品价格',
            align:"center",
            dataIndex: 'price'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/commodity/spuRelation/list",
          delete: "/commodity/spuRelation/delete",
          deleteBatch: "/commodity/spuRelation/deleteBatch",
          exportXlsUrl: "/commodity/spuRelation/exportXls",
          importExcelUrl: "commodity/spuRelation/importExcel",
          
        },
        dictOptions:{},
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
  @import '~@assets/less/common.less';
</style>