<template>
  <div>
    <a-form style="margin: 40px auto 0;">

      <a-form-item
        label="启用属性"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
         <a-switch v-model="model.enableAttribute"/>
      </a-form-item>

      <div v-if="model.enableAttribute == true">
    
        <j-editable-table
          ref="editableTable"
          :loading="loading"
          :columns="columns"
          :dataSource="dataSource"
          :actionButton="true"
          :dragSort="true"
          style="margin-top: 8px;"
          @selectRowChange="handleSelectRowChange">

          <template v-slot:action="props">
            <a @click="handleDelete(props)">删除</a>
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
  import pick from 'lodash.pick'
  import moment from 'moment'
  import { FormTypes } from '@/utils/JEditableTableUtil'
  import JEditor from '@/components/jeecg/JEditor'
  import JEditableTable from '@/components/jeecg/JEditableTable'
  import { randomUUID, randomNumber } from '@/utils/util'
  import { mapGetters, mapActions } from "vuex";
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
          enableAttribute: false
        },
        columns: [
          {
            title: '属性名称',
            key: 'attributeName',
            // width: '18%',
            width: '200px',
            type: FormTypes.input,
            allowInput: true,
            defaultValue: '',
            placeholder: '请选择${title}',
            validateRules: [{ required: true, message: '请选择${title}' }]
          },
          {
            title: '属性值',
            key: 'attributeValue',
            // width: '18%',
            width: '300px',
            type: FormTypes.input,
            allowInput: true,
            defaultValue: '',
            placeholder: '请选择${title}',
            validateRules: [{ required: true, message: '请选择${title}' }]
          },
          {
            title: '操作',
            key: 'action',
            // width: '8%',
            width: '100px',
            type: FormTypes.slot,
            slotName: 'action',
          }

        ],
        dataSource: [],
        selectedRowIds: []
      }
    },
    mounted() {
      if (this.goods.id != null && this.goods.id != ""){
        let record = this.goods;
        this.edit(record);
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
            
            if(that.form.enableAttribute){
              
              //将表格数据解析成字符串
              this.$refs.editableTable.getValues((error, values) => {
                // 错误数 = 0 则代表验证通过
                if (error === 0) {
                    // this.$message.success('验证通过')
                    // 将通过后的数组提交到后台或自行进行其他处理
                    if(values != null){
                      let arr = {
                        columns: that.columns,
                        dataSource: values    
                      };
                      formData.attributeJsonData = JSON.stringify(arr);
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
                    // this.$message.error('验证未通过')
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
      submitForm(formName) {
        this.$refs[formName].validate(valid => {
          if (valid) {
            alert('submit!');
          } else {
            console.log('error submit!!');
            return false;
          }
        });
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
        // this.$nextTick(() => {
        //   this.form.setFieldsValue(pick(this.model, 'enableAttribute'))
        // })

        let attributeJsonData = this.model.attributeJsonData;
        if(attributeJsonData){
          let arr = JSON.parse(attributeJsonData);
          this.columns = arr.columns;
          this.dataSource = arr.dataSource;
        }
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

  .dynamic-delete-button {
    cursor: pointer;
    position: relative;
    top: 4px;
    font-size: 24px;
    color: #999;
    transition: all 0.3s;
  }
  .dynamic-delete-button:hover {
    color: #777;
  }
  .dynamic-delete-button[disabled] {
    cursor: not-allowed;
    opacity: 0.5;
  }
</style>