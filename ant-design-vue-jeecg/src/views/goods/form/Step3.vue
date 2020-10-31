<template>
  <div>
    <a-form :form="form" style="margin: 40px auto 0;">

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
            v-for="(sku, index) in dynamicValidateForm.skus"
            :key="sku.key"
            v-bind="index === 0 ? formItemLayout : {}"
            :label="index === 0 ? '规格' : ''"
            :prop="'skus.' + index + '.value'"
            :rules="{
              required: true,
              message: '规格不能为空',
              trigger: 'blur',
            }"
          >
            <a-input
              v-model="sku.value"
              placeholder="请输入规格"
              style="width: 20%; margin-right: 8px"
              @change = "skuInputChange"
            />
            <a-icon
              v-if="dynamicValidateForm.skus.length > 0"
              class="dynamic-delete-button"
              type="minus-circle-o"
              :disabled="dynamicValidateForm.skus.length === 1"
              @click="removeDomain(sku)"
            />
          </a-form-model-item>
          
          <a-divider></a-divider>

          <a-form-item
            v-if="dynamicValidateForm.skus.length > 0"
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
              :actionButton="true"
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
        <a-button :loading="loading" type="primary" @click="nextStep">下一步</a-button>
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
    name: "Step3",
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
          skus: [],
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
            defaultValue: 0.00,
            placeholder: '${title}',
            validateRules: [{ required: true, message: '请输入${title}' }]
          },
          {
            title: '库存',
            key: 'stock',
            width: '120px',
            type: FormTypes.inputNumber,
            defaultValue: '0',
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
        selectedRowIds: [],
        dataSourceStr: '',
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
      ...mapActions([ "UpdateGoodsInfo" ]),
      nextStep () {
        const that = this;
        // 触发表单验证
        that.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            that.model.id = that.goods.id;
            let formData = Object.assign(that.model, values);
  
            //将表格数据解析成字符串
            this.$refs.editableTable.getValues((error, values) => {
              // 错误数 = 0 则代表验证通过
              if (error === 0) {
                  // this.$message.success('验证通过')
                  // 将通过后的数组提交到后台或自行进行其他处理
                  let arr = {
                    skus: that.dynamicValidateForm.skus,
                    columns: that.columns,
                    dataSource: values    
                  };
                  formData.skuJsonData = JSON.stringify(arr);
                  console.log("表单提交数据",formData)
                  that.UpdateGoodsInfo(formData).then((res) => {
                    this.$emit('nextStep');
                  }).catch((err) => {
                    that.$message.warning(res.message);
                  }).finally(() => {
                    that.confirmLoading = false;
                  });

              } else {
                  // this.$message.error('验证未通过')
              }
            })
            
          }
         
        })
      },
      prevStep () {
        this.$emit('prevStep')
      },
      removeDomain(item) {
        let index = this.dynamicValidateForm.skus.indexOf(item);
        if (index !== -1) {
          this.dynamicValidateForm.skus.splice(index, 1);
          this.columns.splice(index, 1);
        }
      },
      /**新增规格 */
      addDomain() {
        let length = this.dynamicValidateForm.skus.length + 1;
        let name = '规格'+length;
        let key = Date.now();
        this.dynamicValidateForm.skus.unshift({
          value: name,
          key: key,
        });
        this.columns.unshift(
          {
            title: name,
            key: 'c' + key,
            width: '120px',
            type: FormTypes.input,
            defaultValue: '',
            placeholder: '请输入规格值',
            validateRules: [{ required: true, message: '请输入规格值' }]
          }
        );
      },
      /**修改规格时触发 */
      skuInputChange() {
        let _this = this;
        let size = _this.dynamicValidateForm.skus.length;
        for (let i = 0; i < size; i++) {
          let value = _this.dynamicValidateForm.skus[i].value;
          _this.columns[i].title = value;
        }
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
        // this.visible = true;
        // this.$nextTick(() => {
        //   this.form.setFieldsValue(pick(this.model,'enableSku'))
        // });
        // debugger;
        let skusJsonData = this.model.skuJsonData;
        if(skusJsonData){
          let arr = JSON.parse(skusJsonData);
          this.columns = arr.columns;
          this.dataSource = arr.dataSource;
          this.dynamicValidateForm.skus = arr.skus || [];
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