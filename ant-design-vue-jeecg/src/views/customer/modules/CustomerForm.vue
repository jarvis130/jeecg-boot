<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>

          <a-col :span="6">
            <a-form-item label="客户名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['customerName', validatorRules.customerName]" placeholder="请输入客户名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="手机号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['customerMobile', validatorRules.customerMobile]" placeholder="请输入手机号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="客户类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag v-decorator="['customerType', validatorRules.customerType]"  placeholder="请输入客户类型" dictCode="customer_type" :triggerChange="true"  style="width: 100%"/>
            </a-form-item>
          </a-col>

          <a-divider/>

          <a-col :span="6">
            <a-form-item label="公司名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['companyName']" placeholder="请输入公司名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="公司地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['companyAddress']" placeholder="请输入公司地址"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="行业类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag v-decorator="['industryType']"  placeholder="请输入行业类型" dictCode="industry_type" :triggerChange="true"  style="width: 100%"/>
            </a-form-item>
          </a-col>

          <a-divider/>

          <a-col :span="6">
            <a-form-item label="客户来源" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag v-decorator="['customerSource']"  placeholder="请输入来源" dictCode="customer_source" :triggerChange="true"  style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="介绍人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['introducerName']" placeholder="请输入介绍人姓名" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="联系电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['introducerMobile']" placeholder="请输入介绍人电话" style="width: 100%"/>
            </a-form-item>
          </a-col>

          <a-divider/>

          <a-col :span="6">
            <a-form-item label="客户经理" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-user-by-dep v-decorator="['customerManagerId']" placeholder="请输入客户经理" :multi="false" style="width: 100%"></j-select-user-by-dep>
            </a-form-item>
          </a-col>

          <a-col :span="6">
            <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag v-decorator="['status', validatorRules.status]" placeholder="请输入客户状态" dictCode="user_status"  :triggerChange="true" style="width: 100%"/>
            </a-form-item>
          </a-col>

          <a-divider/>

          <a-col :span="6">
            <a-form-item label="创建人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['createBy']" placeholder="系统自动生成" style="width: 100%; background-color: bisque;" readOnly/>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="创建时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="系统自动生成" v-decorator="['createTime']" :trigger-change="true" style="width: 100%; background-color: bisque;" readOnly/>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="更新人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['updateBy']" placeholder="系统自动生成" style="width: 100%; background-color: bisque;" readOnly/>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="更新时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="系统自动生成" v-decorator="['updateTime']" :trigger-change="true" style="width: 100%; background-color: bisque;" readOnly/>
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

  export default {
    name: 'CustomerForm',
    components: {
      JFormContainer,
      JDate,
      JSelectUserByDep,
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
          sm: { span: 6 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
          customerName: {
            rules: [
              { required: true, message: '请输入客户名称!'},
            ]
          },
          customerMobile: {
            rules: [
              { required: true, message: '请输入客户手机号码!'},
            ]
          },
          customerType: {
            rules: [
              { required: true, message: '请输入客户类型!'},
            ]
          },
          status: {
            rules: [
              { required: true, message: '请输入客户状态!'},
            ]
          }
        },
        url: {
          add: "/customer/customer/add",
          edit: "/customer/customer/edit",
          queryById: "/customer/customer/queryById"
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
          this.form.setFieldsValue(pick(this.model,'id','customerName','customerType','customerMobile','companyName','companyAddress','industryType','customerManagerId','createBy','createTime','updateBy','updateTime','customerSource','introducerName','introducerMobile','subjectId','status'))
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
        this.form.setFieldsValue(pick(row,'userId','customerName','customerType','customer_mobile','companyName','companyAddress','industryType','handleId','createBy','createTime','updateBy','updateTime','source','introducerId','subjectId','status'))
      },
    }
  }
</script>