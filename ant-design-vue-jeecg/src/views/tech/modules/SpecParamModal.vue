<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    switchFullscreen
    @ok="handleOk"
    :okButtonProps="{ class:{'jee-hidden': disableSubmit} }"
    @cancel="handleCancel"
    cancelText="关闭">
    <spec-param-form ref="realForm" @ok="submitCallback" :disabled="disableSubmit"></spec-param-form>
  </j-modal>
</template>

<script>

  import pick from 'lodash.pick'
  import SpecParamForm from './SpecParamForm'
  export default {
    name: 'SpecParamModal',
    components: {
      SpecParamForm
    },
    data () {
      return {
        title:'',
        width:800,
        visible: false,
        disableSubmit: false,
        model: {},
        groupId: "",
        status: 1,
        form: this.$form.createForm(this),
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
      }
    },
    methods: {
      add(groupId) {
        debugger;
        this.groupId = groupId;
        this.edit({});
      },
      edit(record) {
        debugger;
        if (record.id) {
          this.groupId = record.groupId;
          this.visibleCheck = (record.status == 1) ? true : false;
        }
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.model.groupId = this.groupId;
        this.model.status = this.status;
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'cid','groupId','name','isNumeric','unit','generic','searching','segments', 'status'))
        });
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        this.$refs.realForm.submitForm();
      },
      submitCallback(){
        this.$emit('ok');
        this.visible = false;
      },
      handleCancel () {
        this.close()
      }
    }
  }
</script>