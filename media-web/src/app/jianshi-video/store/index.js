/*
 * @Author: your name
 * @Date: 2021-08-31 18:28:39
 * @LastEditTime: 2021-09-24 18:27:38
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/store/index.js
 */

import materailModel from './modules/materail-model'
import material from './modules/material'
import videoEffect from './modules/video-effect'
import subtitle from './modules/subtitle'
import phonetic from './modules/phonetic'

const state = { ...materailModel.state, ...material.state, ...videoEffect.state, ...subtitle.state, ...phonetic.state }
const mutations = { ...materailModel.mutations, ...material.mutations, ...videoEffect.mutations, ...subtitle.mutations, ...phonetic.mutations }
const actions = { ...materailModel.actions, ...material.actions, ...videoEffect.actions, ...subtitle.actions, ...phonetic.actions }

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

