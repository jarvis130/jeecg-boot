<template>
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
      <a-button type="primary" icon="download" @click="handleExportXls('user_account')">导出</a-button>
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

    <user-account-modal ref="modalForm" @ok="modalFormOk"></user-account-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import UserAccountModal from './modules/UserAccountModal'

  export default {
    name: 'UserAccountList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      UserAccountModal
    },
    data () {
      return {
        description: 'user_account管理页面',
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
            title:'会员编号',
            align:"center",
            dataIndex: 'userId'
          },
          {
            title:'用户名',
            align:"center",
            dataIndex: 'userName'
          },
          {
            title:'用户密码',
            align:"center",
            dataIndex: 'password'
          },
          {
            title:'salt',
            align:"center",
            dataIndex: 'salt'
          },
          {
            title:'昵称',
            align:"center",
            dataIndex: 'nickname'
          },
          {
            title:'手机',
            align:"center",
            dataIndex: 'mobile'
          },
          {
            title:'会员邮箱',
            align:"center",
            dataIndex: 'email'
          },
          {
            title:'证件类型 1：身份证',
            align:"center",
            dataIndex: 'idType'
          },
          {
            title:'证件号',
            align:"center",
            dataIndex: 'idCode'
          },
          {
            title:'性别，0，保密；1，男；2，女',
            align:"center",
            dataIndex: 'sex'
          },
          {
            title:'生日日期',
            align:"center",
            dataIndex: 'birthday',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'用户现有资金',
            align:"center",
            dataIndex: 'userMoney'
          },
          {
            title:'用户冻结资金',
            align:"center",
            dataIndex: 'frozenMoney'
          },
          {
            title:'消费积分',
            align:"center",
            dataIndex: 'payPoints'
          },
          {
            title:'会员等级积分',
            align:"center",
            dataIndex: 'rankPoints'
          },
          {
            title:'收货信息id，取值表user_address ',
            align:"center",
            dataIndex: 'addressId'
          },
          {
            title:'最后一次登录时间',
            align:"center",
            dataIndex: 'lastLogin',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'最后一次登录ip',
            align:"center",
            dataIndex: 'lastIp'
          },
          {
            title:'登录次数',
            align:"center",
            dataIndex: 'loginCount'
          },
          {
            title:'会员等级id，取值user_rank',
            align:"center",
            dataIndex: 'userRank'
          },
          {
            title:'状态（0停用 1正常）',
            align:"center",
            dataIndex: 'status'
          },
          {
            title:'创建时间',
            align:"center",
            dataIndex: 'createTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'创建人',
            align:"center",
            dataIndex: 'createBy'
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
            title:'更新人',
            align:"center",
            dataIndex: 'updateBy'
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
          list: "/user/userAccount/list",
          delete: "/user/userAccount/delete",
          deleteBatch: "/user/userAccount/deleteBatch",
          exportXlsUrl: "/user/userAccount/exportXls",
          importExcelUrl: "user/userAccount/importExcel",
          
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
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>