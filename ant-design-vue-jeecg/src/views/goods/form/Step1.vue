<template>
  <div>
    <a-form :form="form" style="max-width: 500px; margin: 40px auto 0;">

      <a-form-item
        label="商品名称"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
         <a-input v-decorator="['goodsName', validatorRules.goodsName]" placeholder="请输入商品名称"></a-input>
      </a-form-item>

      <a-form-item
        label="商品编码"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
         <a-input v-decorator="['goodsSn']" placeholder="请输入商品编码"></a-input>
      </a-form-item>

      <a-form-item
        label="外部编码"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
         <a-input v-decorator="['extensionCode']" placeholder="请输入外部编码"></a-input>
      </a-form-item>

      <a-form-item
        label="商品分类"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
          <a-input v-decorator="['catId', validatorRules.catId]" placeholder="请输入商品分类"></a-input>
      </a-form-item>

      <a-form-item
        label="商品品牌"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
        <a-input v-decorator="['brandId', validatorRules.brandId]" placeholder="请输入品牌编号"></a-input>
      </a-form-item>

      <a-form-item
        label="商品图片"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
        <j-image-upload v-model="model.imgList"></j-image-upload>
      </a-form-item>

      <a-form-item
        label="市场价格"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
        <a-input v-decorator="['marketPrice', validatorRules.marketPrice]" placeholder="请输入市场价格"></a-input>
      </a-form-item>

      <a-form-item
        label="平台价格"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
        <a-input v-decorator="['salePrice', validatorRules.salePrice]" placeholder="请输入平台价格"></a-input>
      </a-form-item>

      <a-form-item
        label="是否上架"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
        <a-switch default-unchecked v-decorator="['isOnSale']"/>
      </a-form-item>


      <a-form-item :wrapperCol="{span: 19, offset: 5}">
        <a-button type="primary" @click="nextStep">下一步</a-button>
      </a-form-item>

    </a-form>
  </div>
</template>

<script>
  import { httpAction, getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JImageUpload from '@/components/jeecg/JImageUpload'
  
  export default {
    name: "Step1",
    components: {
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
      }
    },
    data () {
      return {
        form: this.$form.createForm(this),
        model: {
          imgList: []
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
          salePrice: {
            rules: [
              { required: true, message: '请输入平台价格!'},
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
    methods: {
      nextStep () {
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
                // that.$message.success(res.message);
                this.$store.dispatch('saveGoodsInfo' , res.result) ;
                this.$emit('nextStep');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      }
    }
  }
</script>

<style scoped>

</style>