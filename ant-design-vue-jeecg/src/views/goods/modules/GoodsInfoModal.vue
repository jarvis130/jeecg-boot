<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    fullscreen
    switchFullscreen
    @ok="handleOk"
    okText="提交"
    :okButtonProps="{ class:{'jee-hidden': disableSubmit} }"
    @cancel="handleCancel"
    cancelText="关闭">
    <goods-info-form ref="realForm" @ok="submitCallback" :disabled="disableSubmit"></goods-info-form>
  </j-modal>
</template>

<script>

  // import GoodsInfoForm from './GoodsInfoForm'
  import GoodsInfoForm from '../form/Index'
  export default {
    name: 'GoodsInfoModal',
    components: {
      GoodsInfoForm
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
        this.$refs.realForm.currentTab = 0;
      },
      edit (record) {
        this.visible=true
        this.$nextTick(()=>{
          this.$refs.realForm.edit(record);
        })
        this.$refs.realForm.currentTab = 0;
      },
      close () {
        this.$emit('ok');
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