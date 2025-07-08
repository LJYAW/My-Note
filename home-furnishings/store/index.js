import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

const store = new Vuex.Store({
	state: {
		goodsInfo: {},
	},
	mutations: {
		// 用户信息
		SET_GOODS_INFO(state, data) {
			console.log(data)
			state.goodsInfo = data
			uni.setStorageSync('goodsInfo', data);
		}
	},
	actions: {
		// 获取小程序 胶囊信息
		async GET_MENU_BUTTON_INFO({ commit }) {
			let menuButtonInfo = uni.getMenuButtonBoundingClientRect()
			commit('SET_MENU_BUTTON_INFO', menuButtonInfo)

			let res = await uni.getSystemInfoSync();
			commit('SET_SAFEAREA', res.safeArea)

			let menu = wx.getMenuButtonBoundingClientRect();
			let statusBarHeight = (menu.top - res.statusBarHeight) * 2 + menu.height + res.statusBarHeight;
			if (res.model.indexOf('iPhone') > -1) {
				statusBarHeight += 4
			}
			commit('SET_TOP_BAR_H', statusBarHeight)


			console.log(statusBarHeight)

		}
	}
})
export default store
