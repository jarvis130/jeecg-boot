<template>

  <a-card :bordered="false">

    <!-- 抽屉 -->
    <a-drawer
      title="规格值列表"
      :width="screenWidth"
      @close="onClose"
      :visible="visible"
    >
      <!-- 抽屉内容的border -->
      <div
        :style="{
          padding:'10px',
          border: '1px solid #e9e9e9',
          background: '#fff',
        }">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
              <a-form-item label="编号">
                <a-input placeholder="请输入编号" v-model="queryParam.code"></a-input>
              </a-form-item>
            </a-col>

          <a-col :md="6" :sm="12">
            <a-form-item label="名称">
              <j-input placeholder="输入名称模糊查询" v-model="queryParam.title"></j-input>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="8">
              <a-form-item label="状态">
                <a-select v-model="queryParam.status" placeholder="请选择">
                  <a-select-option value="">请选择</a-select-option>
                  <a-select-option value="1">上架</a-select-option>
                  <a-select-option value="2">审核中</a-select-option>
                  <a-select-option value="0">下架</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>

          <a-col :md="6" :sm="8">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

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
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="isOnSale" slot-scope="text, record">
          <a-tag color="#2db7f5" v-if="record.isOnSale == 2">
            审核中
          </a-tag>
          <a-tag color="#87d068" v-if="record.isOnSale == 1">
            上架
          </a-tag>
          <a-tag color="#f50" v-if="record.isOnSale == 0">
            下架
          </a-tag>
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
          <a @click="handleEdit1(record)">编辑</a>

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

    </div>

    </a-drawer>

  </a-card>

</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { mapGetters, mapActions } from "vuex";
    import JInput from '@/components/jeecg/JInput'

  export default {
    name: 'SelectSpu',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      JInput
    },
    data () {
      return {
        description: '',
        visible: false,
        screenWidth: 800,
        queryParam: {
          spuType: 1
        },
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
            dataIndex: 'code'
          },
          {
            title:'商品名称',
            align:"center",
            width:150,
            dataIndex: 'title'
          },
          {
            title:'商品分类',
            align:"center",
            dataIndex: 'cid3_dictText'
          },
          {
            title:'品牌编号',
            align:"center",
            dataIndex: 'brandId'
          },
          {
            title:'市场价',
            align:"center",
            dataIndex: 'marketPrice'
          },
          {
            title:'状态',
            align:"center",
            dataIndex: 'isOnSale_dictText',
            scopedSlots: { customRender: 'isOnSale' }
          },
          {
            title:'更新时间',
            align:"center",
            dataIndex: 'updateTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
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
          list: "/commodity/spuInfo/list",
          delete: "/commodity/spuInfo/delete",
          deleteBatch: "/commodity/spuInfo/deleteBatch",
          exportXlsUrl: "/commodity/spuInfo/exportXls",
          importExcelUrl: "commodity/spuInfo/importExcel",
          
        },
        dictOptions:{},
      }
    },
    created() {
    },
    computed: {
      title () {
        return this.$route.meta.title
      },
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>