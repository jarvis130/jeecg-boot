<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="参数名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['name', validatorRules.name]" placeholder="请输入参数名"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="商品分类" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-category-select v-decorator="['cid', validatorRules.cid]" pcode="B04" placeholder="请输入商品分类,一个分类下有多个规格组"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="是否数字型参数" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag v-decorator="['isNumeric', validatorRules.isNumeric]" placeholder="请输入是否数字型参数，true或false" dictCode="sf_status"  :triggerChange="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['unit']" placeholder="请输入数字类型参数的单位，非数字可以为空"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="是否是sku通用属性" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag v-decorator="['generic', validatorRules.generic]" placeholder="是否是sku通用属性，true或false" dictCode="sf_status"  :triggerChange="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="是否用于搜索过滤" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag v-decorator="['searching']" placeholder="请输入是否用于搜索过滤，true或false" dictCode="sf_status"  :triggerChange="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="数值类型参数" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['segments']" placeholder="请输入数值类型参数，如果需要搜索，则添加分段间隔值，如CPU频率间隔:0.5-1.0"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag v-decorator="['status', validatorRules.status]" placeholder="请输入状态" dictCode="enable_status"  :triggerChange="true" style="width: 100%"/>
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
  import JCategorySelect from '@/components/jeecg/JCategorySelect'

  export default {
    name: 'SpecParamForm',
    components: {
      JFormContainer,
      JCategorySelect
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
        groupId: '',
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
          cid: {
            rules: [
              { required: true, message: '请输入商品分类ID!'},
            ]
          },
          groupId: {
            rules: [
              { required: true, message: '请输入groupId!'},
            ]
          },
          name: {
            rules: [
              { required: true, message: '请输入参数名!'},
            ]
          },
          isNumeric: {
            rules: [
              { required: true, message: '请输入是否数字型参数，true或false!'},
            ]
          },
          unit: {
            rules: [
              { required: true, message: '请输入数字类型参数的单位，非数字可以为空!'},
            ]
          },
          generic: {
            rules: [
              { required: true, message: '请输入是否是sku通用属性，true或false!'},
            ]
          },
          searching: {
            rules: [
              { required: true, message: '请输入是否用于搜索过滤，true或false!'},
            ]
          },
          segments: {
            rules: [
              { required: true, message: '请输入数值类型参数，如果需要搜索，则添加分段间隔值，如CPU频率间隔:0.5-1.0!'},
            ]
          },
          tennantId: {
            rules: [
              { required: true, message: '请输入租户编号!'},
            ]
          },
          status: {
            rules: [
              { required: true, message: '请输入删除标志（1代表存在 0表删除）!'},
            ]
          },
        },
        url: {
          add: "/commodity/specParam/add",
          edit: "/commodity/specParam/edit",
          queryById: "/commodity/specParam/queryById"
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
      add (groupId) {
        this.groupId = groupId;
        this.edit({});
      },
      edit (record) {
        if (record.id) {
          this.groupId = record.groupId;
        }
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.model.groupId = this.groupId;
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'cid','groupId','name','isNumeric','unit','generic','searching','segments', 'status'))
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
        this.form.setFieldsValue(pick(row,'cid','groupId','name','isNumeric','unit','generic','searching','segments','status'))
      },
    }
  }
</script>