<template>
  <a-card :bordered="false">

        <!-- 抽屉 -->
    <a-drawer
      title="会员列表"
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

          <a-col :md="6" :sm="12">
            <a-form-item label="姓名">
              <j-input placeholder="输入姓名模糊查询" v-model="queryParam.realName"></j-input>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="8">
              <a-form-item label="手机号码">
                <a-input placeholder="请输入手机号码" v-model="queryParam.mobile"></a-input>
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

      </div>

    <div
        :style="{
          position: 'absolute',
          bottom: 0,
          width: '100%',
          borderTop: '1px solid #e8e8e8',
          padding: '10px 16px',
          textAlign: 'right',
          left: 0,
          background: '#fff',
          borderRadius: '0 0 4px 4px',
        }"
      >
        <a-button style="marginRight: 8px" @click="onClose">
          关闭
        </a-button>
        <a-button type="primary" @click="onSelect">
          选择
        </a-button>
      </div>

    </a-drawer>

  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import JInput from '@/components/jeecg/JInput'

  export default {
    name: 'SelectAccountWithDrawer',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      JInput
    },
    data () {
      return {
        description: '',
        visible: false,
        screenWidth: 800,
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
            title:'用户名',
            align:"center",
            dataIndex: 'userName'
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
            title:'性别',
            align:"center",
            dataIndex: 'sex_dictText',
          },
          {
            title:'注册时间',
            align:"center",
            dataIndex: 'createTime',
            // customRender:function (text) {
            //   return !text?"":(text.length>10?text.substr(0,10):text)
            // }
          },
          // {
          //   title:'状态',
          //   align:"center",
          //   dataIndex: 'status_dictText'
          // },
          // {
          //   title: '操作',
          //   dataIndex: 'action',
          //   align:"center",
          //   fixed:"right",
          //   width:147,
          //   scopedSlots: { customRender: 'action' }
          // }
        ],
        selectionRows: [],
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
      },
      openModel(){
        this.visible = true;
      },
      onSelect(){
        if(this.selectionRows.length == 0){
          return false;
        }
        if(this.selectionRows.length > 1){
          this.$message.warning('只能选择一条数据');
          return false;
        }
        this.$emit('getSelectData', this.selectionRows);
        this.visible = false;
      },
      onClose(){
        this.visible = false;
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>