/*
 * @Author: your name
 * @Date: 2021-12-15 11:10:55
 * @LastEditTime: 2021-12-23 19:31:45
 * @LastEditors: Please set LastEditors
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: /production_web/src/views/EPG/new-system-pad/store/index.js
 */
/*
 * @Author: your name
 * @Date: 2021-01-13 10:07:06
 * @LastEditTime: 2021-04-22 15:37:01
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /TM-prod-system/src/store/modules/user.js
 */
import axios from '@/axios/request'

const state = {
  cutTimeList: [],
  epgFormData: []
}

const mutations = {
  SET_CUT_TIME_LIST(state, data) {
    state.cutTimeList = data
  },
  SPLICE_CUT_TIME_LIST(state, index) {
    state.cutTimeList.splice(index, 1)
  },
  PUSH_CUT_TIME_LIST(state, data) {
    state.cutTimeList.push(data)
  },
  SET_EPG_FORM_DATA(state, data) {
    state.epgFormData = data
  }
}

const actions = {

}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
