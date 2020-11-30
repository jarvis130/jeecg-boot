<template>
  <div>
    <a-form style="margin: 40px auto 0;">
      <result title="操作成功" :is-success="true" description="">
        
        <div slot="action">
          <a-button type="primary" @click="toContinue">继续创建</a-button>
          <a-button style="margin-left: 8px" @click="toList">返回列表</a-button>
        </div>
      </result>
    </a-form>
  </div>
</template>

<script>
  import Result from '../../result/Result'

  export default {
    name: "Step3",
    components: {
      Result
    },
    data () {
      return {
        loading: false
      }
    },
    methods: {
      toContinue () {
        this.$router.push('/goods/form/Index')
      },
      toList () {
        this.$router.push('/goods/goodsInfoList')
      }
    }
  }
</script>
<style lang="less" scoped>
  .information {
    line-height: 22px;

    .ant-row:not(:last-child) {
      margin-bottom: 24px;
    }
  }
  .money {
    font-family: "Helvetica Neue",sans-serif;
    font-weight: 500;
    font-size: 20px;
    line-height: 14px;
  }
</style>