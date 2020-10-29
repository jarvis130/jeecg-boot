import Vue from 'vue'
import { putAction, postAction, getAction } from '@/api/manage'

const goods = {
  state: {
    id: '',
    catId: '',
    goodsSn: '',
    goodsName: '',
    goodsType: '',
    brandId: '',
    marketPrice: '',
    salePrice: '',
    keywords: '',
    originalImg: '',
    goodsThumb: '',
    goodsImg: '',
    goodsBrief: '',
    goodsDesc: '',
    isReal: '',
    extensionCode: '',
    isOnSale: '',
    isBest: '',
    isNew: '',
    isHot: '',
    isPromote: '',
    enableSku: '',
    skuJsonData: '',
    enableAttribute: '',
    attributeJsonData: ''
  },
  mutations: {
    SET_ID: (state, id) => {
      state.id = id;
    },
    SET_CAT_ID: (state, catId) => {
      state.catId = catId;
    },
    SET_GOODS_SN: (state, goodsSn) => {
      state.goodsSn = goodsSn;
    },
    SET_GOODS_NAME: (state, goodsName) => {
      state.goodsName = goodsName;
    },
    SET_BRAND_ID: (state, brandId) => {
      state.brandId = brandId;
    },
    SET_MARKER_PRICE: (state, marketPrice) => {
      state.marketPrice = marketPrice;
    },
    SET_SALE_PRICE: (state, salePrice) => {
      state.salePrice = salePrice;
    },
    SET_GOODS_KEYWORDS: (state, keywords) => {
      state.keywords = keywords;
    },
    SET_GOODS_BRIEF: (state, goodsBrief) => {
      state.goodsBrief = goodsBrief;
    },
    SET_GOODS_DESC: (state, goodsDesc) => {
      state.goodsDesc = goodsDesc;
    },
    SET_IS_REAL: (state, isReal) => {
      state.isReal = isReal;
    },
    SET_EXTENSION_CODE: (state, extensionCode) => {
      state.extensionCode = extensionCode;
    },
    SET_ISONSALE: (state, isOnSale) => {
      state.isOnSale = isOnSale;
    },
    SET_IS_BEST: (state, isBest) => {
      state.isBest = isBest;
    },
    SET_IS_NEW: (state, isNew) => {
      state.isNew = isNew;
    },
    SET_IS_HOT: (state, isHot) => {
      state.isHot = isHot;
    },
    SET_IS_PROMOTE: (state, isPromote) => {
      state.isPromote = isPromote;
    },
    SET_ENABLE_SKU: (state, enableSku) => {
      state.enableSku = enableSku;
    },
    SET_SKU_JSON_DATA: (state, skuJsonData) => {
      state.skuJsonData = skuJsonData;
    },
    SET_ENABLE_ATTRIBUTE: (state, enableAttribute) => {
      state.enableAttribute = enableAttribute;
    },
    SET_ATTRIBUTE_JSON_DATA: (state, attributeJsonData) => {
      state.attributeJsonData = attributeJsonData;
    },
    SET_GOODS: (state, params) => {
      state.id = params.id;
      state.catId = params.catId;
      state.goodsSn = params.goodsSn;
      state.goodsName = params.goodsName;
      state.brandId = params.brandId;
      state.marketPrice = params.marketPrice;
      state.salePrice = params.salePrice;
      state.keywords = params.keywords;
      state.goodsBrief = params.goodsBrief;
      state.goodsDesc = params.goodsDesc;
      state.isReal = params.isReal;
      state.extensionCode = params.extensionCode;
      state.isOnSale = params.isOnSale;
      state.isBest = params.isBest;
      state.isNew = params.isNew;
      state.isHot = params.isHot;
      state.isPromote = params.isPromote;
      state.enableSku = params.enableSku;
      state.skuJsonData = params.skuJsonData;
      state.enableAttribute = params.enableAttribute;
      state.attributeJsonData = params.attributeJsonData;
    },
    CLEAR_GOODS: (state) => {
      state.id = '';
      state.catId = '';
      state.goodsSn = '';
      state.goodsName = '';
      state.brandId = '';
      state.marketPrice = '';
      state.salePrice = '';
      state.keywords = '';
      state.goodsBrief = '';
      state.goodsDesc = '';
      state.isReal = '';
      state.extensionCode = '';
      state.isOnSale = '';
      state.isBest = '';
      state.isNew = '';
      state.isHot = '';
      state.isPromote = '';
      state.enableSku = '';
      state.skuJsonData = '';
      state.enableAttribute = '';
      state.attributeJsonData = '';
    },
  },
  actions: {
    SaveGoodsInfo({ commit }, params) {
      return new Promise((resolve, reject) => {
        postAction("/goods/goodsInfo/add", params).then(response => {
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
        putAction("/goods/goodsInfo/edit", params).then(response => {
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
    ClearGoodsStore({ commit }) {
      commit('CLEAR_GOODS');
    },
    SetGoodsStore({ commit }, params) {
      commit('SET_GOODS', params);
    }
  }
}

export default goods