<template>
  <div>
    <a-form :form="form" style="margin: 40px auto 0;">

      <a-form-item
        label="关键词"
        :labelCol="{span: 2}"
        :wrapperCol="{span: 10}"
      >
         <a-input v-model="model.keywords" placeholder="请输入关键词"></a-input>
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
         <a-textarea v-model="model.goodsBrief" rows="4" placeholder="请输入商品简述"/>
      </a-form-item>

      <a-form-item
        label="商品详情"
        :labelCol="{span: 2}"
        :wrapperCol="{span: 20}"
        class="stepFormText"
      >
        <j-editor v-model="model.goodsDesc"/>
      </a-form-item>
     
      <a-form-item :wrapperCol="{span: 14, offset: 10}">
        <a-button type="primary" @click="nextStep">下一步</a-button>
        <a-button style="margin-left: 8px" @click="prevStep">上一步</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
  import pick from 'lodash.pick'
  import JEditor from '@/components/jeecg/JEditor'
  import JCheckbox from '@/components/jeecg/JCheckbox'
  import { httpAction, getAction } from '@/api/manage'
  import { mapGetters, mapActions } from "vuex";

  export default {
    name: "Step2",
    components: {
      JEditor,
      JCheckbox
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
          keywords: '',
          goodsBrief: '',
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
    computed: {
      // 用vuex读取数据(读取的是getters.js中的数据)
      // 相当于this.$store.getters.goods(vuex语法糖)
      ...mapGetters(["goods"])
    },
    mounted() {
      if (this.goods.id != null && this.goods.id != ""){
        let record = this.goods;
        this.edit(record);
      }
    },
    methods: {
      ...mapActions([ "SetGoodsStore2" ]),
      nextStep () {
        const that = this;
        // 触发表单验证
        that.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            that.model.id = that.goods.id;
            let formData = Object.assign(that.model, values);
            
            that.SetGoodsStore2(formData).then((res) => {
              that.$emit('nextStep');
            }).catch((err) => {
              that.$message.warning(res.message);
            }).finally(() => {
              that.confirmLoading = false;
            });

          }
         
        })
      },
      prevStep () {
        this.$emit('prevStep')
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'catId','goodsSn','goodsName','goodsType','brandId','marketPrice', 'salePrice','keywords','originalImg','goodsThumb','goodsImg','goodsBrief','goodsDesc','isReal','extensionCode','isOnSale','isBest','isNew','isHot','isPromote','tenantId','enableSku','skuJsonData', 'enableAttribute','attributeJsonData','sortOrder','delFlag','createTime','updateBy','createBy','updateTime'))
        })
      },
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