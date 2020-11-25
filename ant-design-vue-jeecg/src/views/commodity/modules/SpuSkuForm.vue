<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="spu id" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['spuId', validatorRules.spuId]" placeholder="请输入spu id"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="商品标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['title', validatorRules.title]" placeholder="请输入商品标题"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="商品的图片，多个图片以‘,’分割" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['images']" placeholder="请输入商品的图片，多个图片以‘,’分割"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="销售价格，单位为分" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['price', validatorRules.price]" placeholder="请输入销售价格，单位为分" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="特有规格属性在spu属性模板中的对应下标组合" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['indexes']" placeholder="请输入特有规格属性在spu属性模板中的对应下标组合"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="sku的特有规格参数，json格式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['ownSpec']" placeholder="请输入sku的特有规格参数，json格式"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="是否有效，0无效，1有效" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['status', validatorRules.status]" placeholder="请输入是否有效，0无效，1有效" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="添加时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择添加时间" v-decorator="['createTime', validatorRules.createTime]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="最后修改时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择最后修改时间" v-decorator="['updateTime', validatorRules.updateTime]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="创建人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['createBy', validatorRules.createBy]" placeholder="请输入创建人"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="更新人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['updateBy', validatorRules.updateBy]" placeholder="请输入更新人"></a-input>
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
    name: 'SpuSkuForm',
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
          spuId: {
            rules: [
              { required: true, message: '请输入spu id!'},
            ]
          },
          title: {
            rules: [
              { required: true, message: '请输入商品标题!'},
            ]
          },
          price: {
            rules: [
              { required: true, message: '请输入销售价格，单位为分!'},
            ]
          },
          status: {
            rules: [
              { required: true, message: '请输入是否有效，0无效，1有效!'},
            ]
          },
          createTime: {
            rules: [
              { required: true, message: '请输入添加时间!'},
            ]
          },
          updateTime: {
            rules: [
              { required: true, message: '请输入最后修改时间!'},
            ]
          },
          createBy: {
            rules: [
              { required: true, message: '请输入创建人!'},
            ]
          },
          updateBy: {
            rules: [
              { required: true, message: '请输入更新人!'},
            ]
          },
        },
        url: {
          add: "/commodity/spuSku/add",
          edit: "/commodity/spuSku/edit",
          queryById: "/commodity/spuSku/queryById"
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
          this.form.setFieldsValue(pick(this.model,'spuId','title','images','price','indexes','ownSpec','status','createTime','updateTime','createBy','updateBy'))
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
        this.form.setFieldsValue(pick(row,'spuId','title','images','price','indexes','ownSpec','status','createTime','updateTime','createBy','updateBy'))
      },
    }
  }
</script>