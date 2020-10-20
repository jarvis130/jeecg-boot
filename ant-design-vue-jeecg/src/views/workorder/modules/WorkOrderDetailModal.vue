<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    fullscreen
    switchFullscreen
    @ok="handleOk"
    :okButtonProps="{ class:{'jee-hidden': true} }"
    @cancel="handleCancel"
    cancelText="关闭">
    <work-order-detail-form ref="realForm" @ok="submitCallback" :disabled="disableSubmit"></work-order-detail-form>
  </j-modal>
</template>

<script>

  import WorkOrderDetailForm from './WorkOrderDetailForm'
  export default {
    name: 'WorkOrderDetailModal',
    components: {
      WorkOrderDetailForm
    },
    data () {
      return {
        title:'',
        width:800,
        visible: false,
        disableSubmit: false
      }
    },
    methods: {
      add () {
        this.visible=true
        this.$nextTick(()=>{
          this.$refs.realForm.add();
        })
      },
      edit (record) {
        this.visible=true
        this.$nextTick(()=>{
          this.$refs.realForm.edit(record);
        })
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