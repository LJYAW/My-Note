/*
 * @Author: zzx
 * @Date: 2020-09-24 11:29:25
 * @LastEditTime: 2020-12-16 16:30:06
 * @FilePath: /zhijian_web/src/store/index.js
 */
import Vue from 'vue'
import Vuex from 'vuex'
import self from '@/main'
import videoM from '../views/video_editor/video_store'
import axios from '../axios/index'
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    edtor_total_ms: 0,
    resource_cut_video: {},
    modal_video: {},
    hot_link: '',
    user_details: JSON.parse(localStorage.getItem('user_details')) || null,
    user_info: JSON.parse(localStorage.getItem('user_info')) || null,
    alert_msg: ''
  },
  mutations: {
    // 保存用户Token 以及用户信息
    SET_USER_INFO(state, data) {
      localStorage.setItem('user_info', JSON.stringify(data))
      state.user_info = data
    },

    // 保存设置用户 信息
    SET_USER_DETAILS(state, data) {
      localStorage.setItem('user_details', JSON.stringify(data))
      state.user_details = data
    },

    LOGUOT(state, data) {
      state.user_details = null
      state.user_info = null
      localStorage.removeItem('user_info')
      localStorage.removeItem('user_details')
    },

    // setUserDetails(state, data) {
    //   state.user_details = data
    // },
    setUserInfo(state, data) {
      console.log('setUserInfo -> data', data)
      localStorage.setItem('user_info', JSON.stringify(data))
      state.user_info = data
    },
    alertMsg(state, msg) {
      state.alert_msg = msg
    },
    // 弹框显示隐藏
    modalShow(state, id) {
      var dom = document.getElementById(id)
      dom.classList.add('fade')
      document.body.classList.add('model-open')
    },
    modalHidden(state, id) {
      var dom = document.getElementById(id)
      if (dom) {
        dom.classList.remove('fade')
        document.body.classList.remove('model-open')
      }
    },
    setEdtorTotalMs(state, ms) {
      state.edtor_total_ms = ms
    },
    setResourceCutVideo(state, data) {
      state.resource_cut_video = data
    },
    setModalvideoDate(state, data) {
      state.modal_video = data
    },
    setHotLink(state, link) {
      state.hot_link = link
    }
  },
  actions: {
    setUserDetails({ commit }) {
      return new Promise((resolve, reject) => {
        axios
          .get('/user/info')
          .then(res => {
            if (res.data.code == '0000') {
              let data = res.data.data
              commit('SET_USER_DETAILS', data)
              resolve(data)
            }
          })
          .catch(err => reject(err)) //抛出错误回调
      })
    }
  },
  modules: {
    videoM: videoM
  }
})
