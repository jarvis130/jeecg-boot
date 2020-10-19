<template>
  <div>

    <a-row :gutter="24">
      <a-col :sm="24" :md="12" :xl="18">
        
        <!--工单详情begin-->
        <a-card title="需求描述" hoverable>
          <template slot="actions" class="ant-card-actions">
            <a-icon key="setting" type="setting" />
            <a-icon key="edit" type="edit" />
            <a-icon key="ellipsis" type="ellipsis" />
          </template>
          <a-card-meta description="This is the description">
          </a-card-meta>
        </a-card>
        <!--工单详情end-->

        <!--工单表单begin-->
        <!--工单表单end-->

        <!--工单记录begin-->
        <a-list
          class="comment-list"
          :header="`共计 ${list.length} 条回复`"
          item-layout="horizontal"
          :data-source="list"
        >
          <a-list-item slot="renderItem" slot-scope="item, index">
            <a-comment :author="item.author" :avatar="item.avatar">
              <template slot="actions">
                <span v-for="action in item.actions">{{ action }}</span>
              </template>
              <p slot="content">
                {{ item.content }}
              </p>
              <a-tooltip slot="datetime" :title="item.datetime.format('YYYY-MM-DD HH:mm:ss')">
                <span>{{ item.datetime.fromNow() }}</span>
              </a-tooltip>
            </a-comment>
          </a-list-item>
        </a-list>
        <!--工单记录end-->
      </a-col>
      <!--右侧布局-->
      <a-col :sm="24" :md="12" :xl="6">
        <a-card title="工单信息" hoverable>
          <a-descriptions layout="horizontal">
            <a-descriptions-item label="UserName">
              Zhou Maomao
            </a-descriptions-item>
            <a-descriptions-item label="Telephone">
              1810000000
            </a-descriptions-item>
            <a-descriptions-item label="Live">
              Hangzhou, Zhejiang
            </a-descriptions-item>
            <a-descriptions-item label="Address" span="2">
              No. 18, Wantang Road, Xihu District, Hangzhou, Zhejiang, China
            </a-descriptions-item>
            <a-descriptions-item label="Remark">
              empty
            </a-descriptions-item>
          </a-descriptions>
        </a-card>
        <br>
        <a-card title="客户信息" hoverable>
          <template slot="actions" class="ant-card-actions">
            <a-icon key="setting" type="setting" />
            <a-icon key="edit" type="edit" />
            <a-icon key="ellipsis" type="ellipsis" />
          </template>
          <a-card-meta title="Card title" description="This is the description">
            <a-avatar
              slot="avatar"
              src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
            />
          </a-card-meta>
        </a-card>
      </a-col>
    </a-row>

  </div>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JFormContainer from '@/components/jeecg/JFormContainer'
  import JDate from '@/components/jeecg/JDate'  
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
  import JUpload from '@/components/jeecg/JUpload'
  import JCategorySelect from '@/components/jeecg/JCategorySelect'
  import JSelectDepart from '@/components/jeecgbiz/JSelectDepart'
  import moment from 'moment'

  export default {
    name: 'WorkOrderDetailForm',
    components: {
      JFormContainer,
      JDate,
      JSelectUserByDep,
      JUpload,
      JCategorySelect,
      JSelectDepart
    },
    props: {
      //流程表单data
      formData: {
        type: Object,
        default: ()=>{},
        required: false
      },
      //表单模式：true流程表单 false普通表单
      formBpm: {
        type: Boolean,
        default: false,
        required: false
      },
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        form: this.$form.createForm(this),
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
          categoryId: {
            rules: [
              { required: true, message: '请输入工单类型!'},
            ]
          },
          content: {
            rules: [
              { required: true, message: '请输入标题/内容!'},
            ]
          },
          priorityLevel: {
            rules: [
              { required: true, message: '请输入优先级!'},
            ]
          },
          handleDept: {
            rules: [
              { required: true, message: '请输入负责组织!'},
            ]
          },
          handleMaster: {
            rules: [
              { required: true, message: '请输入负责人!'},
            ]
          },
        },
        url: {
          add: "/workorder/workOrder/add",
          edit: "/workorder/workOrder/edit",
          queryById: "/workorder/workOrder/queryById"
        },
        list: [
          {
            actions: ['Reply to'],
            author: 'Han Solo',
            avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
            content:
              'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
            datetime: moment().subtract(1, 'days'),
          },
          {
            actions: ['Reply to'],
            author: 'Han Solo',
            avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
            content:
              'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
            datetime: moment().subtract(2, 'days'),
          },
        ],
        moment,
      }
    },
    computed: {
      formDisabled(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return false
          }
          return true
        }
        return this.disabled
      },
      showFlowSubmitButton(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return true
          }
        }
        return false
      }
    },
    created () {
      //如果是流程中表单，则需要加载流程表单data
      this.showFlowData();
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'code','title','desc','attachedPath','status','createBy','createTime','updateBy','updateTime','handleTerm','handleBy'))
        })
      },
      //渲染流程表单数据
      showFlowData(){
        if(this.formBpm === true){
          let params = {id:this.formData.dataId};
          getAction(this.url.queryById,params).then((res)=>{
            if(res.success){
              this.edit (res.result);
            }
          });
        }
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            //status默认等于1
            values.status = '2';
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'code','title','desc','attachedPath','status','createBy','createTime','updateBy','updateTime','handleTerm','handleBy'))
      },
    }
  }
</script>