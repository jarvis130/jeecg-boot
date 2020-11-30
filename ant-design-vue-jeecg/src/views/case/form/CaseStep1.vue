<template>
  <div>
    <a-form :form="form" style="max-width: 500px; margin: 40px auto 0;">

      <a-form-item
        label="方案标题"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
         <a-input v-decorator="['title', validatorRules.title]" placeholder="请输入方案标题"></a-input>
      </a-form-item>

      <a-form-item
        label="副标题"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
         <a-input v-decorator="['subTitle']" placeholder="请输入方案副标题"></a-input>
      </a-form-item>

      <a-form-item
        label="方案分类"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
          <j-category-select v-decorator="['cid3', validatorRules.cid3]" pcode="A01" placeholder="请输入方案分类"/>
      </a-form-item>

      <a-form-item
        label="方案品牌"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
        <a-input v-decorator="['brandId', validatorRules.brandId]" placeholder="请输入品牌编号"></a-input>
      </a-form-item>

      <a-form-item
        label="方案图片"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
      
        <j-image-upload v-model="model.thumbs" :isMultiple="isMultiple"></j-image-upload>
      </a-form-item>

      <a-form-item
        label="平台售价"
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
        <a-switch default-unchecked v-model="model.isOnSale"/>
      </a-form-item>


      <a-form-item :wrapperCol="{span: 14, offset: 10}">
        <a-button type="primary" @click="nextStep">下一步</a-button>
      </a-form-item>

    </a-form>
  </div>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JImageUpload from '@/components/jeecg/JImageUpload'
  import { mapGetters, mapActions } from "vuex"; 
  import JCategorySelect from '@/components/jeecg/JCategorySelect'

  export default {
    name: "CaseStep1",
    components: {
      JImageUpload,
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
        model: {
          thumbs: [],
          isOnSale: false
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
          cid3: {
            rules: [
              { required: true, message: '请输入方案分类!'},
            ]
          },
          title: {
            rules: [
              { required: true, message: '请输入方案名称!'},
            ]
          },
          code: {
            rules: [
              { required: true, message: '请输入方案编号!'},
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
              { required: true, message: '请输入方案简述!'},
            ]
          },
          isReal: {
            rules: [
              { required: true, message: '请输入是否实物方案，是1，否2!'},
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

        }
      }
    },
    computed: {
      // 用vuex读取数据(读取的是getters.js中的数据)
      // 相当于this.$store.getters.goods(vuex语法糖)
      ...mapGetters(["goods"])
    },
    mounted() {
      if (this.goods){
        let record = this.goods;
        this.edit(record);
      }
    },
    methods: {
      ...mapActions([ "SetGoodsStore1", "getSpuSkuBySpuId", "getTableData", "SetGoodsStore" ]),
      nextStep () {
        const that = this;
        this.model.id = this.goods.id;
        this.model.type = 2;
        // 触发表单验证
        that.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let formData = Object.assign(that.model, values);
            that.SetGoodsStore1(formData).then((res) => {
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
          this.form.setFieldsValue(pick(this.model, 'id', 'spuType', 'cid1', 'cid2', 'cid3','code','title','brandId','marketPrice', 'salePrice','keywords','thumbs','extensionCode','isOnSale'))
        })

        if(this.model.isOnSale == 1  || this.model.isOnSale){
          this.model.isOnSale = true;
        }else{
          this.model.isOnSale = false;
        }

        this.SetGoodsStore(record);
        if(record.enableSpecialSpec){
          let param = {
            spuId: record.id
          }
          //根据spuId得到最新的sku数据
          this.getSpuSkuBySpuId(param).then((res) => {
    
            if (res.success) {
              const result = res.result
              let list = this.goods.tableData;
              for(var i=0; i<list.length; i++){
                let item = list[i];
                for(var j=0; j<result.length; j++){
                  if(item.skuKey == result[j].skuKey){
                    list[i].id = result[j].id;
                    list[i].stock = result[j].stock;
                    break;
                  }
                }
              }
              that.getTableData(list);
            }
          });

          
        }else{
          that.SetGoodsStore(record); 
        }
      },
    }
  }
</script>

<style scoped>

</style>