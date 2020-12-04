<template>

  <a-spin :spinning="confirmLoading">
 
      <a-card :bordered="false">
        <a-steps class="steps" :current="currentTab">
          <a-step title="基本信息" />
          <a-step title="个人详情" />
          <a-step title="通用属性" />
          <a-step title="关联技能" />
        </a-steps>
        <div class="content">
          <step1 v-if="currentTab === 0" ref="step1Form" @nextStep="nextStep"/>
          <step2 v-if="currentTab === 1" ref="step2Form" @nextStep="nextStep" @prevStep="prevStep"/>
         
          <step4 v-if="currentTab === 2" ref="step4Form" @nextStep="nextStep" @prevStep="prevStep"/>
          <step5 v-if="currentTab === 3" ref="step5Form" @nextStep="nextStep" @prevStep="prevStep"/>
          <finish v-if="currentTab === 4" ref="finishForm" @nextStep="nextStep" @prevStep="prevStep"></finish>
        </div>
      </a-card>

  </a-spin>
</template>

<script>
  import Step1 from './Step1';//基本信息
  import Step2 from './Step2';//详情
  import Step3 from './Step3';//特殊规格
  import Step4 from './Step4';//通用规格
  import Step5 from './Step5';//关联物品
  import Finish from './Finish';
  import { mapGetters, mapActions } from "vuex";

  export default {
    name: "GoodsForm",
    components: {
      Step1,
      Step2,
      Step3,
      Step4,
      Step5,
      Finish
    },
    data () {
      return {
        description: '',
        currentTab: 0,
        flag: 0,
        // form
        form: null,
        confirmLoading: false,
      }
    },
    props: {
      //流程表单data
      formData: {
        type: Object,
        default: ()=>{},
        required: false
      },
      //表单模式：true流程表单 false普通表单
      formBpm: {
        type: Boolean,
        default: false,
        required: false
      },
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    computed: {
      formDisabled(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return false
          }
          return true
        }
        return this.disabled
      },
      showFlowSubmitButton(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return true
          }
        }
        return false
      }
    },
    mounted(){
      // this.flag = this.$route.query.flag
      // if( this.flag != 'edit' || this.flag == undefined){
      //   //新增商品清空store
      //   this.ClearGoodsStore();
      // }
    },
    computed: {
      // 用vuex读取数据(读取的是getters.js中的数据)
      // 相当于this.$store.getters.goods(vuex语法糖)
      ...mapGetters(["tech"])
    },
    methods: {
      ...mapActions([ "SetTech", "TechClear" ]),
      add () {
        this.TechClear();
        this.$refs.step1Form.add();
      },
      edit (record) {
        this.currentTab = 0;
        this.SetTech(record);
        this.$refs.step1Form.edit(record);   
      },
      nextStep () {
        if (this.currentTab < 4) {
          this.currentTab += 1
        }
      },
      prevStep () {
        if (this.currentTab > 0) {
          this.currentTab -= 1
        }
      },
      finish () {
        this.currentTab = 0
      }
    }
  }
</script>

<style lang="less" scoped>
  .steps {
    max-width: 750px;
    margin: 16px auto;
  }
</style>