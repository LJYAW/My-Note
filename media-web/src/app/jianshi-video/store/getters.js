/*
 * @Author: your name
 * @Date: 2021-02-22 19:58:59
 * @LastEditTime: 2021-09-01 17:23:36
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /opinion-monit /src/store/getters.js
 */
const getters = {
  // 智能创作组件公用数据
  materialTabName: (state) => state.material.material_tabname,
  materialData: (state) => state.material.material_data,
  maxSize: (state) => state.material.max_size,
  maxTime: (state) => state.material.max_time,
  range: (state) => state.material.range,
  estimateDuration: (state) => state.material.estimate_duration,
  srcData: (state) => state.material.src_data,
  keyword: (state) => state.material.keyword,
  // 素材弹窗数据
  searchKey: (state) => state.materialModal.search_key,
  // 生成视频参数
  materialTitle: (state) => state.material.title,
  targetRatio: (state) => state.material.target_ratio,
  textList: (state) => state.material.text_list,
  imgList: (state) => state.material.img_list,
  subTitleList: (state) => state.material.subTitle_list,
  // 视频效果 start
  effectList: (state) => state.videoEffect.effectList,
  timbreCheckout: (state) => state.videoEffect.effectList,
  cornerMarker: (state) => state.videoEffect.cornerMarker, // 自定义角标数据
  videoTail: (state) => state.videoEffect.videoTail, // 自定义片尾数据
  videoTitle: (state) => state.videoEffect.videoTitle // 自定义片头数据
  // 视频效果 endå
}
export default getters
