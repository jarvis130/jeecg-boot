<template>
  <j-select-biz-component :width="1000" v-bind="configs" v-on="$listeners"/>
</template>

<script>
  import JSelectBizComponent from './JSelectBizComponent'

  export default {
    name: 'JSelectAccount',
    components: { JSelectBizComponent },
    props: ['value'],
    data() {
      return {
        settings: {
          name: '选择会员',
          displayKey: 'userName',
          returnKeys: ['id', 'userName', 'mobile'],
          listUrl: '/user/userAccount/list',
          queryParamCode: 'mobile',
          queryParamText: '手机号',
          columns: [
            { title: '用户名', dataIndex: 'userName', align: 'center', width: '30%', widthRight: '70%' },
            { title: '手机号码', dataIndex: 'mobile', align: 'center', width: '35%' }
          ]
        }
      }
    },
    computed: {
      configs() {
        return Object.assign({ value: this.value }, this.settings, this.$attrs)
      }
    }
  }
</script>

<style lang="less" scoped></style>