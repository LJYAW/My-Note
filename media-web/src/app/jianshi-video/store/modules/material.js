/*
 * @Author: your name
 * @Date: 2021-08-31 17:19:21
 * @LastEditTime: 2021-10-21 19:51:36
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/store/index.js
 */
const TEXT_MS = 4.5

const getDefaultState = () => {
  return {
    // 组件公用数据
    material_tabname: '',
    material_url: '',
    material_data: {},
    max_size: TEXT_MS * 60 * 3,
    max_time: 60 * 3,
    range: {},
    src_data: {},
    keyword: [],
    dataList: {},
    // 生成视频参数
    title: '',
    target_ratio: '16:9',
    text_list: [{ text: '' }],
    img_list: [{ duration: 0 }],
    subTitle_list: [{ title: '', show: false }],
    estimate_duration: 0
  }
}

export default {
  state: getDefaultState(),
  getters: {},
  mutations: {
    // 组件公用数据赋值
    SET_TABNAME: (state, data) => {
      state.material_tabname = data
    },
    SET_MATERIAL_URL: (state, data) => {
      state.material_url = data
    },
    SET_MATERIAL_DATA: (state, data) => {
      state.material_data = data
    },
    SET_MAX_SIZE: (state, data) => {
      state.max_size = data
    },
    SET_MAX_TIME: (state, data) => {
      state.max_time = data
    },
    SET_RANGE: (state, data) => {
      state.range = data
    },
    SET_SRC_DATA: (state, data) => {
      state.src_data = data
    },
    SET_KEYWORD: (state, data) => {
      state.keyword = data
    },
    SET_DATA_LIST: (state, data) => {
      state.dataList = data
    },
    //   接口参数赋值
    SET_TITLE: (state, data) => {
      state.title = data
    },
    SET_TARGET_RATIO: (state, data) => {
      state.target_ratio = data
    },
    SET_TEXT_LIST: (state, data) => {
      state.text_list = data
    },
    SET_IMG_LIST: (state, data) => {
      state.img_list = data
    },
    SET_SUBTITLEDATA: (state, data) => {
      state.subTitle_list = data
    },
    SET_ESTIMATE_DURATION: (state, data) => {
      state.estimate_duration = data
    },
    resetState(state) {
      Object.assign(state, getDefaultState())
    }
  },
  actions: {}
}
