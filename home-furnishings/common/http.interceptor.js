// 这里的vm，就是我们在vue文件里面的this，所以我们能在这里获取vuex的变量，比如存放在里面的token
// 同时，我们也可以在此使用getApp().globalData，如果你把token放在getApp().globalData的话，也是可以使用的
import generateSignString from './setsign'

const install = (Vue, vm) => {
	Vue.prototype.$u.http.setConfig({
		baseUrl: process.env.NODE_ENV === 'development' ? 'http://rw18e3z2i.hb-bkt.clouddn.com/' :
			'http://rw18e3z2i.hb-bkt.clouddn.com/',
		// baseUrl: process.env.NODE_ENV === 'development' ? 'https://thb.tmsx.net' :
		// 	'https:/thb.tmsx.net',
		// 如果将此值设置为true，拦截回调中将会返回服务端返回的所有数据response，而不是response.data
		// 设置为true后，就需要在this.$u.http.interceptor.response进行多一次的判断，请打印查看具体值
		originalData: true,
		// 设置自定义头部content-type
		// header: {
		// 	'content-type': 'xxx'
		// }
	});
	// 响应拦截，判断状态码是否通过
	Vue.prototype.$u.http.interceptor.response = (res) => {
		let {Token} = res.header
		if(Token){
			vm.$store.commit('SET_TOKEN',Token)
		}
		let code = Number(res.statusCode || res.errMsg)
		if (code == 200) {
			// 如果把originalData设置为了true，这里得到将会是服务器返回的所有的原始数据
			// 判断可能变成了res.statueCode，或者res.data.code之类的，请打印查看结果
			// if (res.data.code == '200') {
				// 如果把originalData设置为了true，这里return回什么，this.$u.post的then回调中就会得到什么
				return res.data;
			// } 
			// else {
			// 	vm.$u.toast(res.data.msg)
			// 	return false
			// };
		} else {
			switch (code) {
				case 401:
					vm.$u.route({
						// type: 'redirect',
						url: '/pages/login/login',
					});
					break
				case 422:
					var thisErr = JSON.parse(res.data.msg);
					vm.$u.toast(thisErr[0].error[0]);
					break
				case 429:
					vm.$u.toast('请勿频繁请求')
					break
				case 500:
					vm.$u.toast('服务器端出错')
					break
				case 501:
					vm.$u.toast('网络未实现')
					break
				case 502:
					vm.$u.toast('网络错误')
					break
				case 503:
					vm.$u.toast('服务不可用')
					break
				case 504:
					vm.$u.toast('网络超时')
					break
				case 505:
					vm.$u.toast('http版本不支持该请求')
					break
				default:
					vm.$u.toast(`连接错误${code}`)
			}
			return false
		}

	}
}

export default {
	install
}
