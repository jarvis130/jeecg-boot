<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    switchFullscreen
    @ok="handleOk"
    okText="提交"
    destroyOnClose
    :okButtonProps="{ class:{'jee-hidden': true} }"
    @cancel="handleCancel"
    cancelText="关闭">
    <goods-info-form ref="realForm" @ok="submitCallback" :disabled="disableSubmit"></goods-info-form>
  </j-modal>
</template>

<script>

  // import GoodsInfoForm from './GoodsInfoForm'
  import GoodsInfoForm from '../form/Index'
  import { mapGetters, mapActions } from "vuex"; 
  
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
      ...mapActions([ "SetGoodsStore", "SaveGoodsInfo", "UpdateGoodsInfo" ]),
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
        this.$emit('ok');
        this.visible = false;
      },
      handleOk () {
        this.$refs.realForm.submitForm();

        that.confirmLoading = true;
        let formData = Object.assign(this.model, values);
        if(!that.model.id){
          that.SaveGoodsInfo(formData).then((res) => {
            that.$emit('nextStep');
          }).catch((err) => {
            that.$message.warning(res.message);
          }).finally(() => {
            that.confirmLoading = false;
          });
        }else{
          that.UpdateGoodsInfo(formData).then((res) => {
            that.$emit('nextStep');
          }).catch((err) => {
            that.$message.warning(res.message);
          }).finally(() => {
            that.confirmLoading = false;
          });
        }

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