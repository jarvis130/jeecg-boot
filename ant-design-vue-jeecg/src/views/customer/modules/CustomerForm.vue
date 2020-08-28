<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="12">
            <a-form-item label="客户主键" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['customerId', validatorRules.customerId]" placeholder="请输入客户主键" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="平台账号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['userId', validatorRules.userId]" placeholder="请输入平台账号" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="客户名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['customerName', validatorRules.customerName]" placeholder="请输入客户名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="客户类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['customerType', validatorRules.customerType]" :trigger-change="true" dictCode="customer_type" placeholder="请选择客户类型"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="手机号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['mobile', validatorRules.mobile]" placeholder="请输入手机号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="公司名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['companyName']" placeholder="请输入公司名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="公司地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['companyAddress']" placeholder="请输入公司地址"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="行业类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['industryType', validatorRules.industryType]" :trigger-change="true" dictCode="industry_type" placeholder="请选择行业类型"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="客户经理编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['handleId']" placeholder="请输入客户经理编号" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="创建人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['createBy']" placeholder="请输入创建人" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="创建时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择创建时间" v-decorator="['createTime']" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="更新人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['updateBy']" placeholder="请输入更新人" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="更新时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择更新时间" v-decorator="['updateTime']" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="来源" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['source', validatorRules.source]" :trigger-change="true" dictCode="customer_source" placeholder="请选择来源"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="介绍人编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['introducerId']" placeholder="请输入介绍人编号" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="主体编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['subjectId']" placeholder="请输入主体编号" style="width: 100%"/>
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
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: 'CustomerForm',
    components: {
      JFormContainer,
      JDate,
      JDictSelectTag,
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
          customerId: {
            rules: [
              { required: true, message: '请输入客户主键!'},
            ]
          },
          userId: {
            rules: [
              { required: true, message: '请输入平台账号!'},
            ]
          },
          customerName: {
            rules: [
              { required: true, message: '请输入客户名称!'},
            ]
          },
          customerType: {
            rules: [
              { required: true, message: '请输入客户类型!'},
            ]
          },
          mobile: {
            rules: [
              { required: true, message: '请输入手机号!'},
            ]
          },
          industryType: {
            rules: [
              { required: true, message: '请输入行业类型!'},
            ]
          },
          source: {
            rules: [
              { required: true, message: '请输入来源!'},
            ]
          },
        },
        url: {
          add: "/customer/add",
          edit: "/customer/edit",
          queryById: "/customer/queryById"
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
          this.form.setFieldsValue(pick(this.model,'customerId','userId','customerName','customerType','mobile','companyName','companyAddress','industryType','handleId','createBy','createTime','updateBy','updateTime','source','introducerId','subjectId'))
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
        this.form.setFieldsValue(pick(row,'customerId','userId','customerName','customerType','mobile','companyName','companyAddress','industryType','handleId','createBy','createTime','updateBy','updateTime','source','introducerId','subjectId'))
      },
    }
  }
</script>