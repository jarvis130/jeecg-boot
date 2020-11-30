<template>
  <j-select-biz-component :width="1000" v-bind="configs" v-on="$listeners"/>
</template>

<script>
  import JSelectBizComponent from './JSelectBizComponent'

  export default {
    name: 'JSelectBrand',
    components: { JSelectBizComponent },
    props: ['value'],
    data() {
      return {
        settings: {
          name: '品牌',
          displayKey: 'name',
          returnKeys: ['id', 'brandName'],
          listUrl: '/commodity/spuBrand/list',
          queryParamCode: 'brandName',
          queryParamText: '品牌名称',
          columns: [
            { title: '品牌名称', dataIndex: 'name', align: 'center', width: '30%', widthRight: '70%' },
            { title: '品牌编码', dataIndex: 'code', align: 'center', width: '35%' }
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