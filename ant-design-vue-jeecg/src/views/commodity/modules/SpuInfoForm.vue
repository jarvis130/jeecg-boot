<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['title', validatorRules.title]" placeholder="请输入标题"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="子标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['subTitle']" placeholder="请输入子标题"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="1级类目id" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['cid1', validatorRules.cid1]" placeholder="请输入1级类目id" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="2级类目id" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['cid2', validatorRules.cid2]" placeholder="请输入2级类目id" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="3级类目id" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['cid3', validatorRules.cid3]" placeholder="请输入3级类目id" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="商品所属品牌id" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['brandId', validatorRules.brandId]" placeholder="请输入商品所属品牌id" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="是否上架，0下架，1上架" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['isOnSale', validatorRules.isOnSale]" placeholder="请输入是否上架，0下架，1上架" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="是否有效，0已删除，1有效" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['delFlag', validatorRules.delFlag]" placeholder="请输入是否有效，0已删除，1有效" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="添加时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择添加时间" v-decorator="['createTime']" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="最后修改时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择最后修改时间" v-decorator="['updateTime']" :trigger-change="true" style="width: 100%"/>
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
    name: 'SpuInfoForm',
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
          title: {
            rules: [
              { required: true, message: '请输入标题!'},
            ]
          },
          cid1: {
            rules: [
              { required: true, message: '请输入1级类目id!'},
            ]
          },
          cid2: {
            rules: [
              { required: true, message: '请输入2级类目id!'},
            ]
          },
          cid3: {
            rules: [
              { required: true, message: '请输入3级类目id!'},
            ]
          },
          brandId: {
            rules: [
              { required: true, message: '请输入商品所属品牌id!'},
            ]
          },
          isOnSale: {
            rules: [
              { required: true, message: '请输入是否上架，0下架，1上架!'},
            ]
          },
          delFlag: {
            rules: [
              { required: true, message: '请输入是否有效，0已删除，1有效!'},
            ]
          },
        },
        url: {
          add: "/commodity/spuInfo/add",
          edit: "/commodity/spuInfo/edit",
          queryById: "/commodity/spuInfo/queryById"
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
          this.form.setFieldsValue(pick(this.model,'title','subTitle','cid1','cid2','cid3','brandId','onSale','delFlag','createTime','updateTime'))
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
        this.form.setFieldsValue(pick(row,'title','subTitle','cid1','cid2','cid3','brandId','onSale','delFlag','createTime','updateTime'))
      },
    }
  }
</script>