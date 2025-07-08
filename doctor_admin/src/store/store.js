/*
 * @Author: your name
 * @Date: 2020-11-09 23:01:34
 * @LastEditTime: 2021-02-08 15:06:00
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /doctor_admin/src/store/store.js
 */
import Vue from 'vue'
import Vuex from 'vuex'
import self from '@/main'
import axios from '../axios/index'
Vue.use(Vuex)
export default new Vuex.Store({
  state: {
    doctorData: []
  },
  mutations: {
    // 获取医生信息
    SET_DOCTORDATA(state, data) {
      state.doctorData = data
    },
  },
  actions: {
    SET_DOCTORDATA({ commit }) {
      return new Promise((resolve, reject) => {
        axios
          .get('/admin/task/assigned-user-list')
          .then(res => {
            if (res.data.code == '0000') {
              let data = res.data.data.list
              commit('SET_DOCTORDATA', data)
              resolve(data)
            }
          })
          .catch(err => reject(err)) //抛出错误回调
      })
    }
  },
  modules: {}
})
