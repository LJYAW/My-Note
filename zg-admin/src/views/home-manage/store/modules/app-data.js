/*
 * @Author: your name
 * @Date: 2021-11-01 20:29:05
 * @LastEditTime: 2021-11-25 16:47:56
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/home-manage/store/modules/app-data.js
 */

import { baseAppData, baseSingle } from '../../base-com-data'
import { get, post, put } from '@/axios/http'
const APP_DATA_KEY = 'ZHONGU_APP_DATA_V1'
import { Message } from 'element-ui'

export default {
  state: {
    newVersion: true,
    appData: {},
    appDataCopy: {},
    activeComName: 'HeaderBanner',
    singleIndex: -1,
    showComDialog: false,
    upCom: 0
  },
  getters: {},
  mutations: {
    SET_NEW_VERSION(state, val) {
      state.newVersion = val
    },
    SET_ZHONGGU_APP_DATA(state, data) {
      state.appData = data
      state.upCom++
    },
    SET_ZHONGGU_APP_DATA_COPY(state, data) {
      state.appDataCopy = data
    },
    SET_ACTIVE_COM(state, str) {
      state.activeComName = str
    },
    SET_SINGLE_INDEX(state, index) {
      state.singleIndex = index
    },
    SHOW_COM_DIALOG(state, bol) {
      state.showComDialog = bol
    },
    ADD_SINGLE(state, data) {
      state.appData.Single.push(data)
    },
    DEL_SINGLE(state, index) {
      state.appData.Single.splice(index, 1)
    }
  },
  actions: {
    GetZhongguAppData({ commit }) {
      return new Promise((resolve, reject) => {
        get(`/configs/key/${APP_DATA_KEY}`)
          .then((res) => {
            if (res.err && res.err.code === '3002') {
              // 首次配置数据
              commit('SET_ZHONGGU_APP_DATA', baseAppData)
              commit('SET_NEW_VERSION', true)
              resolve(baseAppData)
            } else if (res.res.code === '0000') {
              const content = JSON.parse(res.res.data.content)
              commit('SET_ZHONGGU_APP_DATA', content)
              commit('SET_NEW_VERSION', false)
              resolve(content)
            }
          })
          .catch((err) => {
            console.log(err)
            reject(err)
          })
      })
    },
    async PostZhongguAppData({ commit, state }) {
      const appData = state.appData
      console.log('🚀 ~ file: app-data.js ~ line 78 ~ PostZhongguAppData ~ appData', appData)

      // let errMsg = ''
      // for (const key in appData) {
      //   errMsg = checkoutAppData(appData, key)
      //   if (errMsg) {
      //     alert(errMsg)
      //     break
      //   }
      // }
      // if (errMsg) {
      //   return
      // }

      const Obj = {
        key: APP_DATA_KEY,
        memo: '测试数据',
        content: JSON.stringify(appData)
      }

      // 更新 该Key 下的数据
      if (!state.newVersion) {
        return new Promise((resolve, reject) => {
          put(`/admin/configs/key/${APP_DATA_KEY}`, Obj)
            .then((res) => {
              resolve(res)
            })
            .catch((err) => {
              reject(err)
            })
        })
      } else {
        // 新建 该Key 下的数据
        return new Promise((resolve, reject) => {
          post('/admin/configs', Obj)
            .then((res) => {
              resolve(res)
            })
            .catch((err) => {
              reject(err)
            })
        })
      }
    }
  }
}
