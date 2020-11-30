<template>
<a-modal v-model="visible" title="选择品牌" @ok="handleOk" destroyOnClose>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- table区域-begin -->
    <div>
      
      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="brandLogo" slot-scope="text">
           <img :src="imageUrl + '/' + text" style="width:60px;height:60px;">
        </template>

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

  </a-card>
</a-modal>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: 'SelectBrand',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
  
    },
    props: {
      //流程表单data
      visible: {
        type: false,
        default: Boolean,
        required: false
      },
    },
    data () {
      return {
        description: 'spu_brand管理页面',
        imageUrl: window._CONFIG['domianURL'],
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
            title:'',
            align:"center",
            dataIndex: 'brandLogo',
            width: 80,
            scopedSlots: { customRender: 'brandLogo' }
          },
          {
            title:'品牌名称',
            align:"center",
            dataIndex: 'brandName'
          },
        ],
        url: {
          list: "/commodity/spuBrand/list",
          delete: "/commodity/spuBrand/delete",
          deleteBatch: "/commodity/spuBrand/deleteBatch",
          exportXlsUrl: "/commodity/spuBrand/exportXls",
          importExcelUrl: "commodity/spuBrand/importExcel",
          
        },
        dictOptions:{},
      }
    },
    created() {
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      handleOk(){

      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>