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

    <!-- <image-upload-form ref="realForm" @ok="submitCallback" :disabled="disableSubmit"></image-upload-form> -->

    <a-upload
      :action="action"
      list-type="picture-card"
      :file-list="fileList"
      @preview="handlePreview"
      @change="handleChange"
    >
      <div v-if="fileList.length < 8">
        <a-icon type="plus" />
        <div class="ant-upload-text">
          上传图片
        </div>
      </div>
    </a-upload>
    <a-modal :visible="previewVisible" :footer="null" @cancel="handleImageCancel">
      <img alt="预览" style="width: 100%" :src="previewImage" />
    </a-modal>

    <!-- <j-image-upload text="上传" :isMultiple="isMultiple" v-model="fileList" ></j-image-upload> -->
 
  </j-modal>
</template>

<script>
  function getBase64(file) {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result);
      reader.onerror = error => reject(error);
    });
  }
  import JImageUpload from '@/components/jeecg/JImageUpload'
  export default {
    name: 'ImageUploadModal',
    components: {
      JImageUpload
    },
    props:{
      images: {
        type: String,
        default () {
            return ''
        }
      },
    },
    watch: {
      images(val) {
        // debugger;
        // this.fileList = this.images;
      }
    },
    data () {
      return {
        title:'',
        width:800,
        isMultiple: true,
        visible: false,
        disableSubmit: false,
        previewVisible: false,
        action: window._CONFIG['domianURL']+"/sys/common/upload",
        previewImage: '',
        // fileList: 'http://127.0.0.1:8080/jeecg-boot/temp/timg_1604368203974.jpeg,http://127.0.0.1:8080/jeecg-boot/temp/plief_1604368209705.jpg'
        fileList: [
          {
            uid: '-1',
            name: 'temp/timg_1604368203974.jpeg',
            status: 'done',
            url: 'http://127.0.0.1:8080/jeecg-boot/temp/timg_1604368203974.jpeg',
          }
        ],
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
        this.$emit('ok');
        this.visible = false;
      },
      handleOk () {
        // this.$refs.realForm.submitForm();
        this.$emit('ok', this.fileList);
        this.visible = false;
      },
      submitCallback(){
        this.$emit('ok');
        this.visible = false;
      },
      handleCancel () {
        this.close()
      },
      /**图片处理 */
      handleImageCancel() {
        this.previewVisible = false;
      },
      async handlePreview(file) {
        if (!file.url && !file.preview) {
          file.preview = await getBase64(file.originFileObj);
        }
        this.previewImage = file.url || file.preview;
        this.previewVisible = true;
      },
      handleChange({ fileList }) {
        this.fileList = fileList;
      }
    }
  }
</script>