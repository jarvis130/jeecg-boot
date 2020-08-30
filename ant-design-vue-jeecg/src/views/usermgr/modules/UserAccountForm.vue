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
            <a-form-item label="用户密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['password', validatorRules.password]" placeholder="请输入用户密码"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="salt" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['salt', validatorRules.salt]" placeholder="请输入salt"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="昵称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['nickname', validatorRules.nickname]" placeholder="请输入昵称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="手机" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['mobile', validatorRules.mobile]" placeholder="请输入手机"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="会员邮箱" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['email', validatorRules.email]" placeholder="请输入会员邮箱"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="证件类型 1：身份证" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['idType', validatorRules.idType]" placeholder="请输入证件类型 1：身份证" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="证件号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['idCode', validatorRules.idCode]" placeholder="请输入证件号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="性别，0，保密；1，男；2，女" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['sex', validatorRules.sex]" placeholder="请输入性别，0，保密；1，男；2，女" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="生日日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择生日日期" v-decorator="['birthday', validatorRules.birthday]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="用户现有资金" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['userMoney', validatorRules.userMoney]" placeholder="请输入用户现有资金" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="用户冻结资金" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['frozenMoney', validatorRules.frozenMoney]" placeholder="请输入用户冻结资金" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="消费积分" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['payPoints', validatorRules.payPoints]" placeholder="请输入消费积分" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="会员等级积分" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['rankPoints', validatorRules.rankPoints]" placeholder="请输入会员等级积分" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="收货信息id，取值表user_address " :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['addressId', validatorRules.addressId]" placeholder="请输入收货信息id，取值表user_address " style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="最后一次登录时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择最后一次登录时间" v-decorator="['lastLogin', validatorRules.lastLogin]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="最后一次登录ip" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['lastIp', validatorRules.lastIp]" placeholder="请输入最后一次登录ip"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="登录次数" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['loginCount', validatorRules.loginCount]" placeholder="请输入登录次数" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="会员等级id，取值user_rank" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['userRank', validatorRules.userRank]" placeholder="请输入会员等级id，取值user_rank" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="状态（0停用 1正常）" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['status']" placeholder="请输入状态（0停用 1正常）"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="创建时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择创建时间" v-decorator="['createTime']" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="创建人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['createBy']" placeholder="请输入创建人"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="更新时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择更新时间" v-decorator="['updateTime']" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="更新人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['updateBy']" placeholder="请输入更新人"></a-input>
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
          password: {
            rules: [
              { required: true, message: '请输入用户密码!'},
            ]
          },
          salt: {
            rules: [
              { required: true, message: '请输入salt!'},
            ]
          },
          nickname: {
            rules: [
              { required: true, message: '请输入昵称!'},
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
              { required: true, message: '请输入证件类型 1：身份证!'},
            ]
          },
          idCode: {
            rules: [
              { required: true, message: '请输入证件号!'},
            ]
          },
          sex: {
            rules: [
              { required: true, message: '请输入性别，0，保密；1，男；2，女!'},
            ]
          },
          birthday: {
            rules: [
              { required: true, message: '请输入生日日期!'},
            ]
          },
          userMoney: {
            rules: [
              { required: true, message: '请输入用户现有资金!'},
            ]
          },
          frozenMoney: {
            rules: [
              { required: true, message: '请输入用户冻结资金!'},
            ]
          },
          payPoints: {
            rules: [
              { required: true, message: '请输入消费积分!'},
            ]
          },
          rankPoints: {
            rules: [
              { required: true, message: '请输入会员等级积分!'},
            ]
          },
          addressId: {
            rules: [
              { required: true, message: '请输入收货信息id，取值表user_address !'},
            ]
          },
          lastLogin: {
            rules: [
              { required: true, message: '请输入最后一次登录时间!'},
            ]
          },
          lastIp: {
            rules: [
              { required: true, message: '请输入最后一次登录ip!'},
            ]
          },
          loginCount: {
            rules: [
              { required: true, message: '请输入登录次数!'},
            ]
          },
          userRank: {
            rules: [
              { required: true, message: '请输入会员等级id，取值user_rank!'},
            ]
          },
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