<template>
  <div>
    <a-form style="margin: 40px auto 0;">

      <a-form-item
        label="启用规格"
        :labelCol="{span: 5}"
        :wrapperCol="{span: 19}"
      >
         <a-switch v-model="model.enableSku"/>
      </a-form-item>

      <div v-if="model.enableSku == true">
        <a-form-model
          ref="dynamicValidateForm"
          :model="dynamicValidateForm"
          v-bind="formItemLayoutWithOutLabel"
        >
          <a-form-model-item v-bind="formItemLayoutWithOutLabel">
            <a-button type="primary" style="width: 20%" @click="addDomain">
              <a-icon type="plus" /> 添加规格
            </a-button>
          </a-form-model-item>

          <a-form-model-item
            v-for="(domain, index) in dynamicValidateForm.domains"
            :key="domain.key"
            v-bind="index === 0 ? formItemLayout : {}"
            :label="index === 0 ? '规格' : ''"
            :prop="'domains.' + index + '.value'"
            :rules="{
              required: true,
              message: '规格不能为空',
              trigger: 'blur',
            }"
          >
            <a-input
              v-model="domain.value"
              placeholder="请输入规格"
              style="width: 20%; margin-right: 8px"
            />
            <a-icon
              v-if="dynamicValidateForm.domains.length > 1"
              class="dynamic-delete-button"
              type="minus-circle-o"
              :disabled="dynamicValidateForm.domains.length === 1"
              @click="removeDomain(domain)"
            />
          </a-form-model-item>
          
          <a-divider></a-divider>

          <a-form-item
            label=""
            :labelCol="{span: 2}"
            :wrapperCol="{span: 24}"
          >
            <j-editable-table
              ref="editableTable"
              :loading="loading"
              :columns="columns"
              :dataSource="dataSource"
              :dragSort="true"
              style="margin-top: 8px;"
              @selectRowChange="handleSelectRowChange">

              <template v-slot:action="props">
                <a @click="handleDelete(props)">删除</a>
              </template>

            </j-editable-table>
          </a-form-item>

        </a-form-model>

      </div>
      
      <br>
     
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
  export default {
    name: "Step3",
    components: {
      JEditor,
      JEditableTable
    },
    data () {
      return {
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
          enableSku: false
        },
        columns: [
          // {
          //   title: '字段名称',
          //   key: 'dbFieldName',
          //   width: '150px',
          //   type: FormTypes.input,
          //   defaultValue: '',
          //   placeholder: '请输入${title}',
          //   validateRules: [
          //     {
          //       required: true, // 必填
          //       message: '请输入${title}' // 显示的文本
          //     },
          //     {
          //       pattern: /^[a-z|A-Z][a-z|A-Z\d_-]{0,}$/, // 正则
          //       message: '${title}必须以字母开头，可包含数字、下划线、横杠'
          //     },
          //     {
          //       unique: true,
          //       message: '${title}不能重复'
          //     },
          //     {
          //       handler(type, value, row, column, callback, target) {
          //         if (type === 'blur') {
          //           if (value === 'abc') {
          //             callback(false, '${title}不能是abc')  // false = 未通过校验
          //           } else {
          //             callback(true) // true = 通过验证
          //           }
          //         } else {
          //           callback(true) // 不填写或者填写 null 代表不进行任何操作
          //         }
          //       },
          //       message: '${title}默认提示'
          //     }
          //   ]
          // },
          {
            title: '单价',
            key: 'price',
            width: '120px',
            type: FormTypes.inputNumber,
            defaultValue: 32,
            placeholder: '${title}',
            // 是否是统计列，只有 inputNumber 才能设置统计列
            statistics: true,
            validateRules: [{ required: true, message: '请输入${title}' }]
          },
          {
            title: '库存',
            key: 'stock',
            width: '120px',
            type: FormTypes.inputNumber,
            defaultValue: '100.32',
            placeholder: '请选择${title}',
            validateRules: [{ required: true, message: '请选择${title}' }]
          },
          // {
          //   title: '文件域',
          //   key: 'upload',
          //   type: FormTypes.upload,
          //   // width: '19%',
          //   width: '300px',
          //   placeholder: '点击上传',
          //   token: true,
          //   responseName: 'message',
          //   action: window._CONFIG['domianURL'] + '/sys/common/upload'
          // },
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
      this.randomData(23, false)
    },
    methods: {
      nextStep () {
        let that = this
        that.loading = true
        setTimeout(function () {
          that.$emit('nextStep')
        }, 1500)
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
        this.columns.unshift(
          {
            title: '库存1',
            key: 'stock',
            width: '120px',
            type: FormTypes.inputNumber,
            defaultValue: '100.32',
            placeholder: '请选择${title}',
            validateRules: [{ required: true, message: '请选择${title}' }]
          }
        );
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
      /** 模拟加载1000条数据 */
      handleTableSet() {
        this.randomData(1000, true)
      },

      handleSelectRowChange(selectedRowIds) {
        this.selectedRowIds = selectedRowIds
      },

      /* 随机生成数据 */
      randomData(size, loading = false) {
        if (loading) {
          this.loading = true
        }

        let randomDatetime = () => {
          let time = parseInt(randomNumber(1000, 9999999999999))
          return moment(new Date(time)).format('YYYY-MM-DD HH:mm:ss')
        }

        let begin = Date.now()
        let values = []
        for (let i = 0; i < size; i++) {
          values.push({
            id: randomUUID(),
            dbFieldName: `name_${i + 1}`,
            // dbFieldTxt: randomString(10),
            multipleSelect: ['string', ['int', 'double', 'boolean'][randomNumber(0, 2)]],
            dbFieldType: ['string', 'int', 'double', 'boolean'][randomNumber(0, 3)],
            dbLength: randomNumber(0, 233),
            datetime: randomDatetime(),
            isNull: ['Y', 'N'][randomNumber(0, 1)]
          })
        }

        this.dataSource = values
        let end = Date.now()
        let diff = end - begin

        if (loading && diff < size) {
          setTimeout(() => {
            this.loading = false
          }, size - diff)
        }

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