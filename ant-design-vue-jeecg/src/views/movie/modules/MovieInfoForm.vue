<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="商品分类" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['catId', validatorRules.catId]" placeholder="请输入商品分类"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="商品编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['goodsSn', validatorRules.goodsSn]" placeholder="请输入商品编号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['goodsName', validatorRules.goodsName]" placeholder="请输入商品名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="商品分类；1商品；2方案；3视频" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['goodsType']" placeholder="请输入商品分类；1商品；2方案；3视频" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="品牌编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['brandId', validatorRules.brandId]" placeholder="请输入品牌编号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="市场价" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['marketPrice', validatorRules.marketPrice]" placeholder="请输入市场价" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="关键词" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['keywords', validatorRules.keywords]" placeholder="请输入关键词"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="原始图片" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['originalImg']" placeholder="请输入原始图片"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="默认缩略图" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['goodsThumb']" placeholder="请输入默认缩略图"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="默认压缩后图片" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['goodsImg']" placeholder="请输入默认压缩后图片"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="商品简述" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['goodsBrief', validatorRules.goodsBrief]" placeholder="请输入商品简述"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="商品描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['goodsDesc']" rows="4" placeholder="请输入商品描述"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="是否实物商品，是1，否2" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['isReal', validatorRules.isReal]" placeholder="请输入是否实物商品，是1，否2"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="外部编码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['extensionCode', validatorRules.extensionCode]" placeholder="请输入外部编码"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="是否上架" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['isOnSale', validatorRules.isOnSale]" placeholder="请输入是否上架"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="是否推荐" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['isBest', validatorRules.isBest]" placeholder="请输入是否推荐"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="是否新品" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['isNew', validatorRules.isNew]" placeholder="请输入是否新品"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="是否热卖" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['isHot', validatorRules.isHot]" placeholder="请输入是否热卖"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="是否推荐" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['isPromote', validatorRules.isPromote]" placeholder="请输入是否推荐"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="租户编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['tenantId']" placeholder="请输入租户编号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="是否启用sku编辑，1是，2否" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['enableSku']" placeholder="请输入是否启用sku编辑，1是，2否" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="规格json数据，用于前台页面展示" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['skuJsonData']" rows="4" placeholder="请输入规格json数据，用于前台页面展示"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="属性json数据，用于前台页面展示" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['attributeJsonData']" rows="4" placeholder="请输入属性json数据，用于前台页面展示"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="排序" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['sortOrder', validatorRules.sortOrder]" placeholder="请输入排序" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="删除标志（1代表存在 2代表删除）" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['delFlag']" placeholder="请输入删除标志（1代表存在 2代表删除）"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="创建时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择创建时间" v-decorator="['createTime', validatorRules.createTime]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="更新者" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['updateBy']" placeholder="请输入更新者"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="创建者" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['createBy']" placeholder="请输入创建者"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="更新时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择更新时间" v-decorator="['updateTime', validatorRules.updateTime]" :trigger-change="true" style="width: 100%"/>
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
    name: 'MovieInfoForm',
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
          catId: {
            rules: [
              { required: true, message: '请输入商品分类!'},
            ]
          },
          goodsSn: {
            rules: [
              { required: true, message: '请输入商品编号!'},
            ]
          },
          goodsName: {
            rules: [
              { required: true, message: '请输入商品名称!'},
            ]
          },
          brandId: {
            rules: [
              { required: true, message: '请输入品牌编号!'},
            ]
          },
          marketPrice: {
            rules: [
              { required: true, message: '请输入市场价!'},
            ]
          },
          keywords: {
            rules: [
              { required: true, message: '请输入关键词!'},
            ]
          },
          goodsBrief: {
            rules: [
              { required: true, message: '请输入商品简述!'},
            ]
          },
          isReal: {
            rules: [
              { required: true, message: '请输入是否实物商品，是1，否2!'},
            ]
          },
          extensionCode: {
            rules: [
              { required: true, message: '请输入外部编码!'},
            ]
          },
          isOnSale: {
            rules: [
              { required: true, message: '请输入是否上架!'},
            ]
          },
          isBest: {
            rules: [
              { required: true, message: '请输入是否推荐!'},
            ]
          },
          isNew: {
            rules: [
              { required: true, message: '请输入是否新品!'},
            ]
          },
          isHot: {
            rules: [
              { required: true, message: '请输入是否热卖!'},
            ]
          },
          isPromote: {
            rules: [
              { required: true, message: '请输入是否推荐!'},
            ]
          },
          sortOrder: {
            rules: [
              { required: true, message: '请输入排序!'},
            ]
          },
          createTime: {
            rules: [
              { required: true, message: '请输入创建时间!'},
            ]
          },
          updateTime: {
            rules: [
              { required: true, message: '请输入更新时间!'},
            ]
          },
        },
        url: {
          add: "/goods/goodsInfo/add",
          edit: "/goods/goodsInfo/edit",
          queryById: "/goods/goodsInfo/queryById"
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
          this.form.setFieldsValue(pick(this.model,'catId','goodsSn','goodsName','goodsType','brandId','marketPrice','keywords','originalImg','goodsThumb','goodsImg','goodsBrief','goodsDesc','isReal','extensionCode','isOnSale','isBest','isNew','isHot','isPromote','tenantId','enableSku','skuJsonData','attributeJsonData','sortOrder','delFlag','createTime','updateBy','createBy','updateTime'))
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
        this.form.setFieldsValue(pick(row,'catId','goodsSn','goodsName','goodsType','brandId','marketPrice','keywords','originalImg','goodsThumb','goodsImg','goodsBrief','goodsDesc','isReal','extensionCode','isOnSale','isBest','isNew','isHot','isPromote','tenantId','enableSku','skuJsonData','attributeJsonData','sortOrder','delFlag','createTime','updateBy','createBy','updateTime'))
      },
    }
  }
</script>