<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="spuId" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['spuId', validatorRules.spuId]" placeholder="请输入spuId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="商品描述信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['description']" rows="4" placeholder="请输入商品描述信息"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="通用规格参数数据" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['genericSpec', validatorRules.genericSpec]" rows="4" placeholder="请输入通用规格参数数据"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="特有规格参数及可选值信息，json格式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['specialSpec', validatorRules.specialSpec]" placeholder="请输入特有规格参数及可选值信息，json格式"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="包装清单" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['packingList']" rows="4" placeholder="请输入包装清单"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="售后服务" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['afterService']" rows="4" placeholder="请输入售后服务"/>
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

  export default {
    name: 'SpuDetailForm',
    components: {
      JFormContainer,
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
          spuId: {
            rules: [
              { required: true, message: '请输入spuId!'},
            ]
          },
          genericSpec: {
            rules: [
              { required: true, message: '请输入通用规格参数数据!'},
            ]
          },
          specialSpec: {
            rules: [
              { required: true, message: '请输入特有规格参数及可选值信息，json格式!'},
            ]
          },
        },
        url: {
          add: "/commodity/spuDetail/add",
          edit: "/commodity/spuDetail/edit",
          queryById: "/commodity/spuDetail/queryById"
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
          this.form.setFieldsValue(pick(this.model,'spuId','description','genericSpec','specialSpec','packingList','afterService'))
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
        this.form.setFieldsValue(pick(row,'spuId','description','genericSpec','specialSpec','packingList','afterService'))
      },
    }
  }
</script>