<template>
  <div>
    <a-form :form="form" style="max-width: 500px; margin: 40px auto 0;">

      <a-form-item
        label="工号"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
         <a-input v-decorator="['code']" placeholder="请输入工号"></a-input>
      </a-form-item>

      <a-form-item
        label="花名"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
         <a-input v-decorator="['nickName', validatorRules.nickName]" placeholder="请输入花名"></a-input>
      </a-form-item>

      <a-form-item
        label="姓名"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
         <a-input v-decorator="['realName', validatorRules.realName]" placeholder="请输入姓名"></a-input>
      </a-form-item>

      <a-form-item
        label="性别"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
         <a-radio-group name="sex" :default-value="1">
          <a-radio :value="1">
            男
          </a-radio>
          <a-radio :value="2">
            女
          </a-radio>
        </a-radio-group>
      </a-form-item>

      <a-form-item
        label="出生日期"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
         <a-date-picker v-model="birthday" @change="onChange" />
      </a-form-item>

      <a-form-item
        label="上传身份证"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
      
        <j-image-upload v-model="model.id_code" :isMultiple="isMultiple"></j-image-upload>
      </a-form-item>

      <a-form-item
        label="手机号码"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
        <a-input v-decorator="['mobile', validatorRules.mobile]" placeholder="请输入手机号码"></a-input>
      </a-form-item>

      <a-form-item
        label="住址"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
        <j-select-brand  v-model="model.address" />
      </a-form-item>

      <a-form-item
        label="紧急联系人"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
        <a-input v-decorator="['linkName']" placeholder="请输入紧急联系人"></a-input>
      </a-form-item>

      <a-form-item
        label="手机号码"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
        <a-input v-decorator="['linkMobile']" placeholder="请输入紧急联系人手机号码"></a-input>
      </a-form-item>

      <a-form-item
        label="状态"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
      
        <a-switch default-unchecked v-model="model.status"/>
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
  import JSelectBrand from '@comp/jeecgbiz/JSelectBrand'

  export default {
    name: "Step1",
    components: {
      JImageUpload,
      JCategorySelect,
        JSelectBrand
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
        brandVisible: false,
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
          nickName: {
            rules: [
              { required: true, message: '请输入花名!'},
            ]
          },
          realName: {
            rules: [
              { required: true, message: '请输入真实姓名!'},
            ]
          },
          id_code: {
            rules: [
              { required: true, message: '请输入身份证号码!'},
            ]
          },
          mobile: {
            rules: [
              { required: true, message: '请输入手机号码!'},
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
      ...mapActions([ "SetGoodsStore1", "getSpuSkuBySpuId", "getTableData", "SetGoodsStore", "ClearGoodsStore" ]),
      nextStep () {
        const that = this;
        this.model.id = this.goods.id;
     
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
          this.form.setFieldsValue(pick(this.model, 'id', 'cid1', 'cid2', 'cid3','code','title','brandId','marketPrice', 'salePrice','keywords','thumbs','extensionCode','isOnSale'))
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
      onBrand(){
        this.brandVisible = true;
      }
    }
  }
</script>

<style scoped>

</style>