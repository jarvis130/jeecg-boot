<template>
  <div>
    <a-form :form="form" style="max-width: 500px; margin: 40px auto 0;">

      <a-form-item
        label="手机号码"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
        <a-input hidden v-decorator="['id']" placeholder=""></a-input>
         <a-input-search v-decorator="['mobile', validatorRules.mobile]" placeholder="请输入手机号码" enter-button @search="onSelectAccount"/>
      </a-form-item>

      <a-form-item
        label="工号"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
         <a-input v-decorator="['code']" placeholder="请输入工号"></a-input>
      </a-form-item>

      <a-form-item
        label="花名"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
         <a-input v-decorator="['nickName', validatorRules.nickName]" placeholder="请输入花名"></a-input>
      </a-form-item>

      <a-form-item
        label="姓名"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
         <a-input v-decorator="['realName', validatorRules.realName]" placeholder="请输入姓名"></a-input>
      </a-form-item>

      <a-form-item
        label="性别"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
         <a-radio-group name="model.sex" :default-value="1">
          <a-radio :value="1">
            男
          </a-radio>
          <a-radio :value="2">
            女
          </a-radio>
        </a-radio-group>
      </a-form-item>

      <a-form-item
        label="出生日期"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
         <a-date-picker v-model="model.birthday" @change="onChange" />
      </a-form-item>

      <a-form-item
        label="上传身份证"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
      
        <j-image-upload v-model="model.id_code" :isMultiple="isMultiple"></j-image-upload>
      </a-form-item>

      <!-- <a-form-item
        label="手机号码"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
        <a-input v-decorator="['mobile', validatorRules.mobile]" placeholder="请输入手机号码"></a-input>
      </a-form-item> -->

      <a-form-item
        label="住址"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
        <a-input v-decorator="['address']" placeholder="请输入住址"></a-input>
      </a-form-item>

      <a-form-item
        label="服务分类"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 8}"
      >
          <j-category-select v-decorator="['cid', validatorRules.cid]" pcode="B04" placeholder="请输入服务分类"/>
      </a-form-item>

      <a-form-item
        label="紧急联系人"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
        <a-input v-decorator="['linkName']" placeholder="请输入紧急联系人"></a-input>
      </a-form-item>

      <a-form-item
        label="手机号码"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
        <a-input v-decorator="['linkMobile']" placeholder="请输入紧急联系人手机号码"></a-input>
      </a-form-item>

      <a-form-item
        label="状态"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
      
        <a-switch default-unchecked v-model="model.status"/>
      </a-form-item>


      <a-form-item :wrapperCol="{span: 14, offset: 10}">
        <a-button type="primary" @click="nextStep">下一步</a-button>
      </a-form-item>

      

    </a-form>
  
  <j-select-account ref="selectAccountForm" @getSelectData="getSelectData"></j-select-account>

  </div>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JImageUpload from '@/components/jeecg/JImageUpload'
  import { mapGetters, mapActions } from "vuex"; 
  import JCategorySelect from '@/components/jeecg/JCategorySelect'
  import JSelectBrand from '@comp/jeecgbiz/JSelectBrand'
  import JSelectAccount from '@comp/biz/SelectAccountWithDrawer'

  export default {
    name: "Step1",
    components: {
      JImageUpload,
      JCategorySelect,
        JSelectBrand,
        JSelectAccount
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
        brandVisible: false,
        selectMultiple: false,
        model: {
          status: false
        },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        isMultiple: true,
        validatorRules: {
          userName: {
            rules: [
              { required: true, message: '请输入用户名!'},
            ]
          },
          nickName: {
            rules: [
              { required: true, message: '请输入花名!'},
            ]
          },
          realName: {
            rules: [
              { required: true, message: '请输入真实姓名!'},
            ]
          },
          id_code: {
            rules: [
              { required: true, message: '请输入身份证号码!'},
            ]
          },
          mobile: {
            rules: [
              { required: true, message: '请输入手机号码!'},
            ]
          },
          cid: {
            rules: [
              { required: true, message: '请输入服务分类!'},
            ]
          },
        },
        url: {

        }
      }
    },
    computed: {
      // 用vuex读取数据(读取的是getters.js中的数据)
      // 相当于this.$store.getters.goods(vuex语法糖)
      ...mapGetters(["tech"])
    },
    mounted() {
      if (this.tech){
        let record = this.tech;
        this.edit(record);
      }
    },
    methods: {
      ...mapActions([ "SetTech", "TechClear" ]),
      nextStep () {
        const that = this;
        
        // 触发表单验证
        that.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
       
            let formData = Object.assign(that.model, values);
            that.SetTech(formData).then((res) => {
              that.$emit('nextStep');
            }).catch((err) => {
              that.$message.warning(res.message);
            }).finally(() => {
              that.confirmLoading = false;
            });
 
          }
         
        })
      },
      add () {
        // this.edit({});
      },
      edit (record) {
        
        let that = this;
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'id', 'realName', 'nickName', 'birthday', 'sex', 'idCode', 'cid', 'code','status', 'mobile', 'userName', 'linkName', 'linkMobile', 'address'))
        })

        if(this.model.status == 1  || this.model.status){
          this.model.status = true;
        }else{
          this.model.status = false;
        }

        this.TechClear(record);
        
      },
      onSelectAccount(){
        this.$refs.selectAccountForm.openModel();
      },
      getSelectData(val){
        // this.model.id = val[0].id;
        // this.model.userName = val[0].userName;
        // this.model.realName = val[0].realName;
        // this.model.model = val[0].mobile;
        // this.model.sex = val[0].sex;
        // this.model.birthday = val[0].birthday;
        // this.model.nickName = val[0].nickName;
      
        this.model = Object.assign({}, val[0]);
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'id','userName','realName','mobile','sex','birthday','nickName'))
        })
      }
    }
  }
</script>

<style scoped>

</style>