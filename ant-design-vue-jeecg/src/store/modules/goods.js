import Vue from 'vue'
import { putAction, postAction, getAction } from '@/api/manage'

const goods = {
  state: {
    id: '',
  },
  mutations: {
    SET_ID: (state, id) => {
      state.id = id;
    }
  },
  actions: {
    SaveGoodsInfo({ commit }, params) {
      return new Promise((resolve, reject) => {
        postAction("/goods/goodsInfo/add", params).then(response => {
          debugger;
          if(response.success){
            const result = response.result
            commit('SET_ID', result);
            resolve(response)
          }else{
            resolve(response)
          }
        }).catch(error => {
          reject(error)
        })
      })
    },

  }
}

export default goods