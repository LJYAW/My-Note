// 如果没有通过拦截器配置域名的话，可以在这里写上完整的URL(加上域名部分)
import query from './api/query.js'
// 此处第二个参数vm，就是我们在页面使用的this，你可以通过vm获取vuex等操作，更多内容详见uView对拦截器的介绍部分：
// https://uviewui.com/js/http.html#%E4%BD%95%E8%B0%93%E8%AF%B7%E6%B1%82%E6%8B%A6%E6%88%AA%EF%BC%9F
const install = (Vue, vm) => {
	vm.$u.api = {
		query: query(vm),
	};
}

export default {
	install
}

// 调用getSearch接口
// this.$u.api.User.getSearch().then(res => {
//     console.log(res);
// })
