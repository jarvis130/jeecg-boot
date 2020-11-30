<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="品牌名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['brandName', validatorRules.brandName]" placeholder="请输入品牌名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="品牌logo" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-image-upload v-model="model.brandLogo" :isMultiple="isMultiple" :data="{biz:bizPath}"></j-image-upload>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="品牌备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['brandDesc']" rows="4" placeholder="请输入品牌备注"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="官方网址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['siteUrl']" placeholder="请输入官方网址"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-switch default-unchecked v-model="model.status"/>
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
  import JImageUpload from '@/components/jeecg/JImageUpload'

  export default {
    name: 'SpuBrandForm',
    components: {
      JFormContainer,
      JDate,
      JImageUpload
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
      },
      bizPath:{
        type:String,
        required:false,
        default:"logo"
      },
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
          brandName: {
            rules: [
              { required: true, message: '请输入品牌名称!'},
            ]
          },
        },
        url: {
          add: "/commodity/spuBrand/add",
          edit: "/commodity/spuBrand/edit",
          queryById: "/commodity/spuBrand/queryById"
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
          this.form.setFieldsValue(pick(this.model,'brandName','brandLogo','brandDesc','siteUrl','sortOrder','status','tennantId','createBy','createTime','updateBy','updateTime','delFlag'))
        })
        if(model.status == 1){
          this.model.status = true
        }else{
          this.model.status = false
        }
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
        this.form.setFieldsValue(pick(row,'brandName','brandLogo','brandDesc','siteUrl','sortOrder','status','tennantId','createBy','createTime','updateBy','updateTime','delFlag'))
      },
    }
  }
</script>