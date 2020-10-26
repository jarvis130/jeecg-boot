<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>

          <a-col :span="24">
            <a-form-item label="工单类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-category-select v-decorator="['categoryId', validatorRules.categoryId]" pcode="B03" placeholder="工单类型"/>
            </a-form-item>
          </a-col>
          
          <a-col :span="24">
            <a-form-item label="标题/内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['content', validatorRules.content]" rows="6" placeholder="请输入标题/内容"/>
            </a-form-item>
          </a-col>

          <a-col :span="24">
            <a-form-item label="上传附件"  :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-upload v-decorator="['attachedPath']" bizPath=""></j-upload>
            </a-form-item>
          </a-col>

          <a-col :span="24">
            <a-form-item label="优先级" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag v-decorator="['priorityLevel', validatorRules.priorityLevel]" placeholder="请输入优先级" dictCode="priority_level"  :triggerChange="true" style="width: 100%"/>
            </a-form-item>
          </a-col>

          <!-- <a-col :span="24">
            <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag v-decorator="['status', validatorRules.status]" placeholder="请输入工单状态" dictCode="work_order_status"  :triggerChange="true" style="width: 100%"/>
            </a-form-item>
          </a-col> -->

          <!-- <a-col :span="24">
            <a-form-item label="负责团队" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-depart v-decorator="['handleDept', validatorRules.handleDept]" placeholder="请输入负责团队" style="width: 100%"/>
            </a-form-item>
          </a-col> -->

           <a-col :span="24">
            <a-form-item label="负责人" :labelCol="labelCol" :wrapperCol="wrapperCol">
                  <j-select-user-by-dep v-decorator="['handleMaster', validatorRules.handleMaster]" placeholder="请输入负责人" :multi="false" style="width: 100%"></j-select-user-by-dep>
            </a-form-item>
          </a-col>

          <!-- <a-col :span="24">
            <a-form-item label="处理人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-user-by-dep v-decorator="['handleBy']" placeholder="请输入处理人" :multi="false" style="width: 100%"></j-select-user-by-dep>
            </a-form-item>
          </a-col> -->

          <a-col :span="24">
            <a-form-item label="关注者" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-user-by-dep v-decorator="['superviseBy']" placeholder="请输入关注者" :multi="true" style="width: 100%"></j-select-user-by-dep>
            </a-form-item>
          </a-col>

          <a-col :span="24">
            <a-form-item label="截止日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择截止日期" v-decorator="['expiryDate']" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>

          <a-col v-if="showFlowSubmitButton" :span="24" style="text-align: center">
            <a-button @click="submitForm">提 交</a-button>
          </a-col>
        </a-row>
      </a-form>
    </j-form-container>
  </a-spin>
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

  export default {
    name: 'WorkOrderForm',
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
        }
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