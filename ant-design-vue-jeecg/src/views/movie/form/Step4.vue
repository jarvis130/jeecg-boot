<template>
  <div>
    <a-form style="margin: 40px auto 0;">

      <a-form-item
        label="启用属性"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
         <a-switch v-model="model.enableGenericSpec"/>
      </a-form-item>

      <div v-if="model.enableGenericSpec == true">


      <a-row>
        <a-col :span="6">
        
        </a-col>
        <a-col :span="6">
        
        </a-col>
        <a-col :span="6">
      
        </a-col>
        <a-col :span="6">
          <a-button type="primary" @click="showModal">
            同步最新配置
          </a-button>
        </a-col>
      </a-row>

    <a-modal v-model="visible" title="同步最新配置" ok-text="继续" cancel-text="取消" @ok="hideModal">
      <p>同步配置将清空下表填写的内容，是否确定？</p>
    </a-modal>

          <a-table :dataSource="tableData" border :pagination="pagination">
          
              <a-table-column key="groupName" title="属性组" align="center" width="20%">
                <template slot-scope="scope">
                  <span>{{ scope.groupName }}</span>
                </template>
              </a-table-column>

              <a-table-column key="specName" title="属性名称" align="center" width="20%">
                <template slot-scope="scope">
                  <span>{{ scope.specName }}</span>
                </template>
              </a-table-column>
              
              <a-table-column key="specValue" title="属性值" align="center">
                <template slot-scope="scope">
                    
                    <a-input onkeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" v-model="scope.specValue" style="width: 300px;"></a-input>

                </template>
              </a-table-column>

          </a-table>

     

      </div>
     
      <a-form-item :wrapperCol="{span: 14, offset: 10}">
        <a-button :loading="loading" type="primary" @click="nextStep">提交</a-button>
        <a-button style="margin-left: 8px" @click="prevStep">上一步</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>

  const defaultTable = {
        groupName: '',
        specname: '',
        specValue: '',
    };

  import pick from 'lodash.pick'
  import moment from 'moment'
  import { FormTypes } from '@/utils/JEditableTableUtil'
  import { randomUUID, randomNumber } from '@/utils/util'
  import { mapGetters, mapActions } from "vuex";
  import { httpAction, getAction } from '@/api/manage'
  export default {
    name: "Step4",
    components: {
    },
    data () {
      return {
        form: this.$form.createForm(this),
        formItemLayout: {
          labelCol: {
            xs: { span: 24 },
            sm: { span: 4 },
          },
          wrapperCol: {
            xs: { span: 24 },
            sm: { span: 20 },
          },
        },
        formItemLayoutWithOutLabel: {
          wrapperCol: {
            xs: { span: 24, offset: 0 },
            sm: { span: 20, offset: 4 },
          },
        },
        dynamicValidateForm: {
          domains: [],
        },
        loading: false,
        model: {
          enableGenericSpec: false
        },
        tableData: [],
        selectedRowIds: [],
        genericList: [],
        visible: false,
        pagination: false,
        url: {
          list: "/commodity/specGroup/queryGenericList"
        }
      }
    },
    mounted() {
      if (this.goods){
        let record = this.goods;
        this.edit(record);
      }else
        this.loadGenericData();
      
    },
    computed: {
    // 用vuex读取数据(读取的是getters.js中的数据)
    // 相当于this.$store.getters.goods(vuex语法糖)
    ...mapGetters(["goods"])
	  },
    methods: {
      ...mapActions([ "SaveGoodsInfo", "UpdateGoodsInfo" ]),
      nextStep () {
        const that = this;
        // 触发表单验证
        that.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            that.model.id = that.goods.id;
            let formData = Object.assign(that.model, values);
            if(that.model.enableGenericSpec){
              formData.genericSpec = JSON.stringify(that.tableData);
            }
            formData.spuType = "3"; //spu类型1=视频
            console.log("表单提交数据",formData)
            
            if(!that.model.id){
              that.SaveGoodsInfo(formData).then((res) => {
                that.$emit('nextStep');
              }).catch((err) => {
                that.$message.warning(err.message);
              }).finally(() => {
                that.confirmLoading = false;
              });
            }else{
              that.UpdateGoodsInfo(formData).then((res) => {
                that.$emit('nextStep');
              }).catch((err) => {
                that.$message.warning(err.message);
              }).finally(() => {
                that.confirmLoading = false;
              });
            }

          }
         
        })
      },
      prevStep () {
        this.$emit('prevStep')
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'enableGenericSpec'))
        })
   
        let genericSpec = this.model.genericSpec;
        if(genericSpec){
          let that = this;
          let arr = JSON.parse(genericSpec);
          if(arr instanceof Array){
            that.tableData = arr;
          }
        }
      },
      loadGenericData() {
        if(!this.url.list){
          this.$message.error("请设置url.list属性!")
          return
        }
        let that = this;
        this.loading = true;
        let param = {
          cateId: this.goods.cid3
        }
        getAction(this.url.list, param).then((res) => {
          if (res.success) {
            //渲染组件
            that.tableData = res.result;
            //
          }
          if(res.code===510){
            that.$message.warning(res.message)
          }
          that.loading = false;
        })
      },
      showModal() {
        this.visible = true;
      },
      hideModal() {
        this.loadGenericData();
        this.visible = false;
      }
    }
  }
</script>

<style scoped>

.td span {
  margin-left: 10px !important;
}


</style>