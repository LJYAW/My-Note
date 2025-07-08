/*
 * @Author: your name
 * @Date: 2021-08-23 16:09:50
 * @LastEditTime: 2021-08-24 16:59:02
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/store/modules/company.js
 */
import axios from '@/axios/index'

const state = {
  companyInfo: {}
}
const mutations = {
  COMPANY_INFO: (state, data) => {
    state.companyInfo = data
  }
}

const actions = {
  getCompanyInfo({ commit }, userInfo) {
    return new Promise((resolve, reject) => {
      axios
        .get('/companies/')
        .then(res => {
          if (res.data.code === '200') {
            const { data } = res.data
            commit('COMPANY_INFO', data)
            resolve()
          } else {
            reject(res.data)
          }
        })
        .catch(error => {
          reject(error)
        })
    })
  }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations
}
