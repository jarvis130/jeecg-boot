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

          <j-editable-table
            ref="editableTable"
            :loading="loading"
            :columns="columns"
            :dataSource="genericList"
            style="margin-top: 8px;"
            @selectRowChange="handleSelectRowChange">

            <template slot="groupName" slot-scope="text">
              <a>123</a>
              <span style="margin-left: 10px;">{{ text }}</span>
            </template>
          </j-editable-table>

     

      </div>
     
      <a-form-item :wrapperCol="{span: 14, offset: 10}">
        <a-button :loading="loading" type="primary" @click="nextStep">提交</a-button>
        <a-button style="margin-left: 8px" @click="prevStep">上一步</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>

// 动态计算需要合并的单元格的行
    const temp = {}; // 当前重复的值,支持多列
    const mergeCells = (text, array, columns) => {
        let i = 0;
        if (text !== temp[columns]) {
            temp[columns] = text;
            array.forEach((item) => {
                if (item.groupName === temp[columns]) {
                    i += 1;
                }
            });
        }
        return i;
    };

  import pick from 'lodash.pick'
  import moment from 'moment'
  import { FormTypes } from '@/utils/JEditableTableUtil'
  import JEditor from '@/components/jeecg/JEditor'
  import JEditableTable from '@/components/jeecg/JEditableTable'
  import { randomUUID, randomNumber } from '@/utils/util'
  import { mapGetters, mapActions } from "vuex";
  import { httpAction, getAction } from '@/api/manage'
  export default {
    name: "Step4",
    components: {
      JEditor,
      JEditableTable
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
        columns: [
          {
            title: '属性组',
            key: 'groupName',
            width: '150px',
            type: FormTypes.nomal,
            align: "center",
            scopedSlots: { customRender: 'groupName' },
            className: 'column-money',
          },
          {
            title: '属性名称',
            key: 'specName',
            align: "center",
            width: '150px',
            type: FormTypes.nomal
          },
          {
            title: '属性值',
            key: 'attributeValue',
            align: "center",
            width: '300px',
            type: FormTypes.input,
            allowInput: true,
            defaultValue: '',
            placeholder: '请选择${title}',
            validateRules: [{ required: true, message: '请选择${title}' }]
          }

        ],
        dataSource: [],
        selectedRowIds: [],
        genericList: [],
        visible: false,
        url: {
          list: "/commodity/specGroup/queryGenericList"
        }
      }
    },
    mounted() {
      if (this.goods){
        let record = this.goods;
        this.edit(record);
      }else{
        this.loadGenericData();
      }
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
            console.log("表单提交数据",formData)
            
            if(that.model.enableGenericSpec){
              
              //将表格数据解析成字符串
              that.$refs.editableTable.getValues((error, values) => {
                // 错误数 = 0 则代表验证通过
                if (error === 0) {
             
                    // this.$message.success('验证通过')
                    // 将通过后的数组提交到后台或自行进行其他处理
                    if(values != null){
                      formData.genericSpec = JSON.stringify(values);
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
                    

                } else {
                    this.$message.error('验证未通过');
                    return;
                }
              })

            }else{

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

          }
         
        })
      },
      prevStep () {
        this.$emit('prevStep')
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      removeDomain(item) {
        let index = this.dynamicValidateForm.domains.indexOf(item);
        if (index !== -1) {
          this.dynamicValidateForm.domains.splice(index, 1);
        }
      },
      addDomain() {
        this.dynamicValidateForm.domains.push({
          value: '',
          key: Date.now(),
        });
      },
      /**Jtable */
       /** 表单验证 */
      handleTableCheck() {
        this.$refs.editableTable.getValues((error) => {
          if (error === 0) {
            this.$message.success('验证通过')
          } else {
            this.$message.error('验证未通过')
          }
        })
      },
      /** 获取值，忽略表单验证 */
      handleTableGet() {
        this.$refs.editableTable.getValues((error, values) => {
          console.log('values:', values)
        }, false)
        console.log('deleteIds:', this.$refs.editableTable.getDeleteIds())

        this.$message.info('获取值成功，请看控制台输出')

      },

      handleSelectRowChange(selectedRowIds) {
        this.selectedRowIds = selectedRowIds
      },

      handleDelete(props) {
        let { rowId, target } = props
        target.removeRows(rowId)
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
            that.genericList = arr;
            // let tmp = [];
            // for(var i=0; i<arr.length; i++){
            //   let item = arr[i];
            //   tmp.push({
            //     rowKey: item['id'], // 行的id
            //     values: { // 在这里 values 中的 name 是你 columns 中配置的 key
            //         'attributeValue': item['attributeValue']
            //     }    
            //   });
            // }
            // debugger;
            // that.$refs.editableTable.setValues(tmp);
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
            // debugger
            //渲染组件
            that.genericList = res.result;
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