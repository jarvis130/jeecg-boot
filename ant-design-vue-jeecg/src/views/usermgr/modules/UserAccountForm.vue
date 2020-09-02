<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="12">
            <a-form-item label="会员编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['userId', validatorRules.userId]" placeholder="请输入会员编号" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="用户名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['userName', validatorRules.userName]" placeholder="请输入用户名"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="手机" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['mobile', validatorRules.mobile]" placeholder="请输入手机"></a-input>
            </a-form-item>
          </a-col>
          
          <a-divider/>

          <a-col :span="12">
            <a-form-item label="真实姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input placeholder="请输入会员真实姓名"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag placeholder="请输入用户性别" dictCode="sex" style="width: 100%"/>
            </a-form-item>
          </a-col>

          
          <a-col :span="12">
            <a-form-item label="证件类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number placeholder="请输入证件类型" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="证件号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input placeholder="请输入证件号"></a-input>
            </a-form-item>
          </a-col>

          <a-col :span="12">
            <a-form-item label="出生日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择出生日期" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>

          <a-col :span="12">
            <a-form-item label="会员邮箱" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input placeholder="请输入会员邮箱"></a-input>
            </a-form-item>
          </a-col>
          
          <a-divider/>

          <a-col :span="12">
            <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag v-decorator="['user_status', validatorRules.status]" placeholder="请输入会员状态" dictCode="user_status"  :triggerChange="true" style="width: 100%"/>
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

  export default {
    name: 'UserAccountForm',
    components: {
      JFormContainer,
      JDate,
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
          userId: {
            rules: [
              { required: true, message: '请输入会员编号!'},
            ]
          },
          userName: {
            rules: [
              { required: true, message: '请输入用户名!'},
            ]
          },
          mobile: {
            rules: [
              { required: true, message: '请输入手机!'},
            ]
          },
          email: {
            rules: [
              { required: true, message: '请输入会员邮箱!'},
            ]
          },
          idType: {
            rules: [
              { required: true, message: '请输入证件类型!'},
            ]
          },
          idCode: {
            rules: [
              { required: true, message: '请输入证件号!'},
            ]
          },
          sex: {
            rules: [
              { required: true, message: '请输入用户性别!'},
            ]
          },
          birthday: {
            rules: [
              { required: true, message: '请输入出生日期!'},
            ]
          },
          status: {
            rules: [
              { required: true, message: '请输入会员状态!'},
            ]
          }
        },
        url: {
          add: "/user/userAccount/add",
          edit: "/user/userAccount/edit",
          queryById: "/user/userAccount/queryById"
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
          this.form.setFieldsValue(pick(this.model,'userId','userName','password','salt','nickname','mobile','email','idType','idCode','sex','birthday','userMoney','frozenMoney','payPoints','rankPoints','addressId','lastLogin','lastIp','loginCount','userRank','status','createTime','createBy','updateTime','updateBy'))
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
        this.form.setFieldsValue(pick(row,'userId','userName','password','salt','nickname','mobile','email','idType','idCode','sex','birthday','userMoney','frozenMoney','payPoints','rankPoints','addressId','lastLogin','lastIp','loginCount','userRank','status','createTime','createBy','updateTime','updateBy'))
      },
    }
  }
</script>