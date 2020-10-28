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
      
      
     
      <a-form-item :wrapperCol="{span: 19, offset: 5}">
        <a-button :loading="loading" type="primary" @click="nextStep">提交</a-button>
        <a-button style="margin-left: 8px" @click="prevStep">上一步</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
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
    computed: {
    // 用vuex读取数据(读取的是getters.js中的数据)
    // 相当于this.$store.getters.goods(vuex语法糖)
    ...mapGetters(["goods"])
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
            // width: '19%',
            width: '200px',
            type: FormTypes.input,
            defaultValue: '',
            placeholder: '请输入${title}',
            validateRules: [
              {
                required: true, // 必填
                message: '请输入${title}' // 显示的文本
              },
              {
                pattern: /^[a-z|A-Z][a-z|A-Z\d_-]{0,}$/, // 正则
                message: '${title}必须以字母开头，可包含数字、下划线、横杠'
              },
              {
                unique: true,
                message: '${title}不能重复'
              },
              {
                handler(type, value, row, column, callback, target) {
                  // type 触发校验的类型（input、change、blur）
                  // value 当前校验的值
                  // callback(flag, message) 方法必须执行且只能执行一次
                  //          flag = 是否通过了校验，不填写或者填写 null 代表不进行任何操作
                  //          message = 提示的类型，默认使用配置的 message
                  // target 行编辑的实例对象

                  if (type === 'blur') {
                    if (value === 'abc') {
                      callback(false, '${title}不能是abc')  // false = 未通过校验
                    } else {
                      callback(true) // true = 通过验证
                    }
                  } else {
                    callback(true) // 不填写或者填写 null 代表不进行任何操作
                  }
                },
                message: '${title}默认提示'
              }
            ]
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
    },
    methods: {
      ...mapActions([ "UpdateGoodsInfo" ]),
      nextStep () {
        const that = this;
        // 触发表单验证
        that.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            that.model.id = that.goods.id;
            let formData = Object.assign(that.model, values);
            console.log("表单提交数据",formData)
            that.UpdateGoodsInfo(formData).then((res) => {
              this.$emit('nextStep');
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