<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <a-col :md="4" :sm="6">
              <a-form-item label="工单编号">
                <a-input placeholder="请输入工单编号" v-model="queryParam.code"></a-input>
              </a-form-item>
            </a-col>

          <a-col :md="4" :sm="6">
            <a-form-item label="工单名称">
              <j-input placeholder="输入工单名称模糊查询" v-model="queryParam.title"></j-input>
            </a-form-item>
          </a-col>

          <a-col :md="4" :sm="6">
              <a-form-item label="优先级">
                <a-select v-model="queryParam.priorityLevel" placeholder="请选择">
                  <a-select-option value="">请选择</a-select-option>
                  <a-select-option value="1">低</a-select-option>
                  <a-select-option value="2">一般</a-select-option>
                  <a-select-option value="3">紧急</a-select-option>
                  <a-select-option value="4">非常紧急</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>

          <a-col :md="4" :sm="6">
              <a-form-item label="状态">
                <a-select v-model="queryParam.status" placeholder="请选择">
                  <a-select-option value="">请选择</a-select-option>
                  <a-select-option value="1">待分配</a-select-option>
                  <a-select-option value="2">已指派</a-select-option>
                  <a-select-option value="3">受理中</a-select-option>
                  <a-select-option value="4">关闭</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>

          <a-col :md="4" :sm="6">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('work_order')">导出</a-button>
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
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="priorityLevel" slot-scope="text, record">
          <a-tag color="blue" v-if="record.priorityLevel == 1">
            低
          </a-tag>
          <a-tag color="orange" v-if="record.priorityLevel == 2">
            一般
          </a-tag>
          <a-tag color="pink" v-if="record.priorityLevel == 3">
            紧急
          </a-tag>
          <a-tag color="red" v-if="record.priorityLevel == 4">
            非常紧急
          </a-tag>
        </template>

        <template slot="status" slot-scope="text, record">
          <a-tag color="#f50" v-if="record.status == 1">
            待分配
          </a-tag>
          <a-tag color="#87d068" v-if="record.status == 2">
            已指派
          </a-tag>
          <a-tag color="#yelow" v-if="record.status == 3">
            受理中
          </a-tag>
          <a-tag color="#2db7f5" v-if="record.status == 4">
            关闭
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

        <span slot="action" slot-scope="text, record">
          <!-- <a @click="handleEdit(record)">编辑</a> -->
          <a @click="handleWorkOrder(record)">编辑</a>
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

    <work-order-modal ref="modalForm" @ok="modalFormOk"></work-order-modal>
    <work-order-detail-modal ref="modalDetailForm" @ok="modalFormOk"></work-order-detail-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WorkOrderModal from './modules/WorkOrderModal'
  import WorkOrderDetailModal from './modules/WorkOrderDetailModal'
  import JInput from '@/components/jeecg/JInput'

  export default {
    name: 'WorkOrderList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      WorkOrderModal,
      WorkOrderDetailModal,
      JInput
    },
    data () {
      return {
        description: 'work_order管理页面',
        // 表头
        columns: [
          {
            title: '序号',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'工单编号',
            align:"center",
            width:60,
            dataIndex: 'workCode'
          },
          {
            title:'主题/内容',
            align:"center",
            width:200,
            dataIndex: 'content'
          },
          {
            title:'优先级',
            align:"center",
            width:60,
            dataIndex: 'priorityLevel_dictText',
            scopedSlots: { customRender: 'priorityLevel' }
          },
          {
            title:'状态',
            align:"center",
            width:60,
            dataIndex: 'status_dictText',
            scopedSlots: { customRender: 'status' }
          },
          {
            title:'创建时间',
            align:"center",
            width:80,
            dataIndex: 'createTime'
          },
          {
            title:'责任团队',
            align:"center",
            width:60,
            dataIndex: 'handleDept_dictText'
          },
          {
            title:'责任人',
            align:"center",
            width:60,
            dataIndex: 'handleMaster_dictText'
          },
          {
            title:'处理人',
            align:"center",
            width:60,
            dataIndex: 'handleBy_dictText'
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
          list: "/workorder/workOrder/list",
          delete: "/workorder/workOrder/delete",
          deleteBatch: "/workorder/workOrder/deleteBatch",
          exportXlsUrl: "/workorder/workOrder/exportXls",
          importExcelUrl: "workorder/workOrder/importExcel",
          
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
      handleWorkOrder(record){
        this.$refs.modalDetailForm.title = '工单详情';
        this.$refs.modalDetailForm.edit(record);
        this.$refs.modalDetailForm.visible = true;
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>