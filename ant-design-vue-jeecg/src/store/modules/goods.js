import Vue from 'vue'
import { getAction } from '@/api/manage'


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
    // TODO 如果没找到可以尝试请求一下
    saveGoodsInfo({ commit }, id) {
      debugger
      commit('SET_ID', id);
    }

  }
}

export default goods