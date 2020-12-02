import Vue from 'vue'
import { putAction, postAction, getAction } from '@/api/manage'

const tech = {
  state: {
    id: '',
    code: '',
    realName: '',
    nickName: '',
    mobile: '',
    email: '',
    idType: '',
    idCode: '',
    sex: '',
    birthday: '',
    linkName: '',
    linkMobile: '',
    thumbs: '',
    brief: '',
    desc: '',
    status: '',
    tableData: [],
    linkData: ''
  },
  mutations: {
    SET_TECH: (state, params) => {
      state.id = params.id = '' ? '' : params.id;
      state.code = params.code = '' ? '' : params.code;
      state.realName = params.realName = '' ? '' : params.realName;
      state.nickName = params.nickName = '' ? '' : params.nickName;
      state.mobile = params.mobile = '' ? '' : params.mobile;
      state.email = params.email = '' ? '' : params.email;
      state.idCode = params.idCode = '' ? '' : params.idCode;
      state.sex = params.sex = '' ? '' : params.sex;
      state.birthday = params.birthday = '' ? '' : params.birthday;
      state.linkName = params.linkName = '' ? '' : params.linkName;
      state.linkMobile = params.linkMobile = '' ? '' : params.linkMobile;
      state.thumbs = params.thumbs = '' ? '' : params.thumbs;
      state.brief = params.brief = '' ? '' : params.brief;
      state.desc = params.desc = '' ? '' : params.desc;
      state.status = params.status = '' ? '' : params.status;
      state.tableData = params.tableData = '' ? '' : params.tableData;
      state.linkData = params.linkData = '' ? '' : params.linkData;
    },
    TECH_CLEAR: (state) => {
      state.id = '';
      state.code = '';
      state.realName = '';
      state.nickName = '';
      state.mobile = '';
      state.email = '';
      state.idCode = '';
      state.sex = '';
      state.birthday = '';
      state.linkName = '';
      state.linkMobile = '';
      state.thumbs = '';
      state.brief = '';
      state.desc = '';
      state.status = '';
      state.tableData = '';
      state.linkData = '';
    },
    SetTableData(state, data) {
      state.tableData = data
    },
    LinkData(state, data) {
      state.linkData = data
    },
  },
  actions: {
    SetTableData(state, data) {
      state.commit('SetTableData', data)
    },
    SetLinkData(state, data) {
      state.commit('LinkData', data)
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
    },
    SetGoodsStore5({ commit }, params) {
      commit('SET_GOODS5', params);
    }
  }
}

export default tech