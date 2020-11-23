<template>
  <div>
    <a-form :form="form"  label-width="120px" @submit.native.prevent>

      <a-card :bordered="false">


    <!-- table区域-begin -->
    <div>

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

        <!-- <span slot="action" slot-scope="text, record">
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
        </span> -->

      </a-table>
    </div>

    <spu-relation-modal ref="modalForm" @ok="modalFormOk"></spu-relation-modal>
  </a-card>

      <a-form-item :wrapperCol="{span: 14, offset: 10}">
        <a-button :loading="loading" type="primary" @click="nextStep">提交</a-button>
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
    name: 'CaseStep3',
    mixins:[ mixinDevice, JeecgListMixin],
    components: {
      // SpuRelationModal
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
            dataIndex: 'relationId'
          },
          {
            title:'规格',
            align:"center",
            dataIndex: 'skuId'
          },
          {
            title:'价格',
            align:"center",
            dataIndex: 'price'
          },
          {
            title:'数量',
            align:"center",
            dataIndex: 'useNum'
          },
          
          
          // {
          //   title: '操作',
          //   dataIndex: 'action',
          //   align:"center",
          //   fixed:"right",
          //   width:147,
          //   scopedSlots: { customRender: 'action' }
          // }
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
    }
  }
</script>

<style scoped>
  @import '~@assets/less/common.less';
</style>