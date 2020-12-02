import Vue from 'vue'
import Vuex from 'vuex'

import app from './modules/app'
import user from './modules/user'
import permission from './modules/permission'
import enhance from './modules/enhance'
import online from './modules/online'
import goods from './modules/goods'
import tech from './modules/tech'
import getters from './getters'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    app,
    user,
    permission,
    enhance,
    online,
    goods,
    tech
  },
  state: {

  },
  mutations: {

  },
  actions: {

  },
  getters
})
