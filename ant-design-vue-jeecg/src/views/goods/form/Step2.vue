<template>
  <div>
    <a-form :form="form" style="margin: 40px auto 0;">

      <a-form-item
        label="关键词"
        :labelCol="{span: 2}"
        :wrapperCol="{span: 10}"
      >
         <a-input v-decorator="['keywords']" placeholder="请输入关键词"></a-input>
      </a-form-item>

      <a-form-item
        label="过滤条件"
        :labelCol="{span: 2}"
        :wrapperCol="{span: 20}"
      >
         <j-checkbox
            v-model="jcheckbox.values"
            :options="jcheckbox.options"
          />
      </a-form-item>

      <a-form-item
        label="商品简述"
        :labelCol="{span: 2}"
        :wrapperCol="{span: 20}"
      >
         <a-textarea v-decorator="['goodsBrief']" rows="4" placeholder="请输入商品简述"/>
      </a-form-item>

      <a-form-item
        label="商品详情"
        :labelCol="{span: 2}"
        :wrapperCol="{span: 20}"
        class="stepFormText"
      >
        <j-editor v-model="model.goodsDesc"/>
      </a-form-item>
     
      <a-form-item :wrapperCol="{span: 19, offset: 5}">
        <a-button type="primary" @click="nextStep">下一步</a-button>
        <a-button style="margin-left: 8px" @click="prevStep">上一步</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
  import JEditor from '@/components/jeecg/JEditor'
  import JCheckbox from '@/components/jeecg/JCheckbox'
  import { mapGetters } from "vuex";

  export default {
    name: "Step2",
    components: {
      JEditor,
      JCheckbox
    },
    computed: {
        // 用vuex读取数据(读取的是getters.js中的数据)
        // 相当于this.$store.getters.news(vuex语法糖)
        ...mapGetters(["goods"])
	  },
    data () {
      return {
        form: this.$form.createForm(this),
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        model: {
          id: '',
          goodsDesc: ''
        },
        jcheckbox: {
          values: 'spring,jeecgboot',
          options: [
            { label: '新品', value: 'isNew' },
            { label: '热卖', value: 'isHot' },
            { label: '推荐', value: 'isPromote'}
          ]
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
            debugger;
            this.model.id = this.goods.id;
            httpurl+=this.url.edit;
            method = 'put';
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                // that.$message.success(res.message);
                debugger;
                this.$store.commit('saveGoodsInfo' , res.result) ;
                this.$emit('nextStep');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
      prevStep () {
        this.$emit('prevStep')
      }
    }
  }
</script>

<style lang="less" scoped>
  .stepFormText {
    margin-bottom: 24px;

    .ant-form-item-label,
    .ant-form-item-control {
      line-height: 22px;
    }
  }

</style>