import Vue from 'vue'
import { putAction, postAction, getAction } from '@/api/manage'

const goods = {
  state: {
    id: '',
    type: '',
    cid1: '',
    cid2: '',
    cid3: '',
    code: '',
    title: '',
    subTitle: '',
    type: '',
    brandId: '',
    marketPrice: '',
    salePrice: '',
    keywords: '',
    thumbs: '',
    images: '',
    brief: '',
    description: '',
    isReal: '',
    extensionCode: '',
    isOnSale: '',
    isNew: '',
    isHot: '',
    isRecommend: '',
    enableSpecialSpec: '',
    specialSpec: '',
    enableGenericSpec: '',
    genericSpec: '',
    specArr: [],
    tableData: [],
    oldTableData: []
  },
  mutations: {
    SET_GOODS: (state, params) => {
      state.id = params.id = '' ? '' : params.id;
      state.type = params.type = '' ? '' : params.type;
      state.cid1 = params.cid1 = '' ? '' : params.cid1;
      state.cid2 = params.cid2 = '' ? '' : params.cid2;
      state.cid3 = params.cid3 = '' ? '' : params.cid3;
      state.code = params.code = '' ? '' : params.code;
      state.title = params.title = '' ? '' : params.title;
      state.subTitle = params.subTitle = '' ? '' : params.subTitle;
      state.brandId = params.brandId = '' ? '' : params.brandId;
      state.marketPrice = params.marketPrice = '' ? '' : params.marketPrice;
      state.salePrice = params.salePrice = '' ? '' : params.salePrice;
      state.thumbs = params.thumbs = '' ? '' : params.thumbs;
      state.keywords = params.keywords = '' ? '' : params.keywords;
      state.brief = params.brief = '' ? '' : params.brief;
      state.description = params.description = '' ? '' : params.description;
      state.isReal = params.isReal = '' ? '' : params.isReal;
      state.extensionCode = params.extensionCode = '' ? '' : params.extensionCode;
      state.isOnSale = params.isOnSale = '' ? '' : params.isOnSale;
      state.isNew = params.isNew = '' ? '' : params.isNew;
      state.isHot = params.isHot = '' ? '' : params.isHot;
      state.isRecommend = params.isRecommend = '' ? '' : params.isRecommend;
      state.enableSpecialSpec = params.enableSpecialSpec = '' ? false : params.enableSpecialSpec;
      state.specialSpec = params.specialSpec = '' ? '' : params.specialSpec;
      state.enableGenericSpec = params.enableGenericSpec = '' ? false : params.enableGenericSpec;
      state.genericSpec = params.genericSpec = '' ? '' : params.genericSpec;
      if(params.enableSpecialSpec){
        if(params.specialSpec){
          let arr = JSON.parse(params.specialSpec);
          state.specArr = arr['spec'];
          state.tableData = arr['table'];
        }
      }
    },
    SET_GOODS1: (state, params) => {
      state.id = params.id = '' ? '' : params.id;
      state.type = params.type = '' ? '' : params.type;
      state.cid1 = params.cid1 = '' ? '' : params.cid1;
      state.cid2 = params.cid2 = '' ? '' : params.cid2;
      state.cid3 = params.cid3 = '' ? '' : params.cid3;
      state.code = params.code = '' ? '' : params.code;
      state.title = params.title = '' ? '' : params.title;
      state.subTitle = params.subTitle = '' ? '' : params.subTitle;
      state.brandId = params.brandId = '' ? '' : params.brandId;
      state.marketPrice = params.marketPrice = '' ? '' : params.marketPrice;
      state.salePrice = params.salePrice = '' ? '' : params.salePrice;
      state.isReal = params.isReal = '' ? '' : params.isReal;
      state.extensionCode = params.extensionCode = '' ? '' : params.extensionCode;
      state.isOnSale = params.isOnSale = '' ? '' : params.isOnSale;
      state.thumbs = params.thumbs = '' ? '' : params.thumbs;
    },
    SET_GOODS2: (state, params) => {
      state.keywords = params.keywords = '' ? '' : params.keywords;
      state.brief = params.brief = '' ? '' : params.brief;
      state.description = params.description = '' ? '' : params.description;
      state.isNew = params.isNew = '' ? '' : params.isNew;
      state.isHot = params.isHot = '' ? '' : params.isHot;
      state.isRecommend = params.isRecommend = '' ? '' : params.isRecommend;
    },
    SET_GOODS3: (state, params) => {
      state.enableSpecialSpec = params.enableSpecialSpec = '' ? false : params.enableSpecialSpec;
      if(params.enableSpecialSpec){
        let specialSpec = {
          spec: state.specArr,
          table: state.tableData
        }
        state.specialSpec = JSON.stringify(specialSpec);
      }
      
    },
    SET_GOODS4: (state, params) => {
      state.enableGenericSpec = params.enableGenericSpec = '' ? false : params.enableGenericSpec;
      state.genericSpec = params.genericSpec = '' ? '' : params.genericSpec;
    },
    CLEAR_GOODS: (state) => {
      state.id = '';
      state.type = '';
      state.cid1 = '';
      state.cid2 = '';
      state.cid3 = '';
      state.code = '';
      state.title = '';
      state.brandId = '';
      state.marketPrice = '';
      state.salePrice = '';
      state.keywords = '';
      state.brief = '';
      state.description = '';
      state.isReal = '';
      state.extensionCode = '';
      state.isOnSale = '';
      state.isNew = '';
      state.isHot = '';
      state.isRecommend = '';
      state.enableSpecialSpec = '';
      state.specialSpec = '';
      state.enableGenericSpec = '';
      state.genericSpec = '';
      state.tableData = '';
    },
    getSpecArr(state, data) {
      state.specArr = data
    },
    getTableData(state, data) {
      state.tableData = data
    },
    setOldTableData(state, data) {
      state.oldTableData = data
    }
  },
  actions: {
    getSpecArr(state, data) {
      state.commit('getSpecArr', data)
    },
    getTableData(state, data) {
      state.commit('getTableData', data)
    },
    setOldTableData(state, data) {
      state.commit('setOldTableData', data)
    },
    SaveGoodsInfo({ commit }, params) {
      return new Promise((resolve, reject) => {
        postAction("/commodity/spuInfo/add", params).then(response => {
          if(response.success){
            const result = response.result
            commit('SET_GOODS', result);
            resolve(response)
          }else{
            resolve(response)
          }
        }).catch(error => {
          reject(error)
        })
      })
    },
    UpdateGoodsInfo({ commit }, params) {
      return new Promise((resolve, reject) => {
        putAction("/commodity/spuInfo/edit", params).then(response => {
          if(response.success){
            const result = response.result
            commit('SET_GOODS', result);
            resolve(response)
          }else{
            resolve(response)
          }
        }).catch(error => {
          reject(error)
        })
      })
    },
    getSpuSkuBySpuId({ commit }, params) {
      return new Promise((resolve, reject) => {
        getAction("/commodity/spuSku/queryBySpuId", params).then(response => {
          if(response.success){
            resolve(response)
          }else{
            resolve(response)
          }
        }).catch(error => {
          reject(error)
        })
      })
    },
    ClearGoodsStore({ commit }) {
      commit('CLEAR_GOODS');
    },
    SetGoodsStore({ commit }, params) {
      commit('SET_GOODS', params);
    },
    SetGoodsStore1({ commit }, params) {
      commit('SET_GOODS1', params);
    },
    SetGoodsStore2({ commit }, params) {
      commit('SET_GOODS2', params);
    },
    SetGoodsStore3({ commit }, params) {
      commit('SET_GOODS3', params);
    },
    SetGoodsStore4({ commit }, params) {
      commit('SET_GOODS4', params);
    }
  }
}

export default goods