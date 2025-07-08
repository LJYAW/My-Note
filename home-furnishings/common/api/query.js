let query = (vm)=>{
	return {
		// 获取首页banner图
		getIndexBanner:() => {
			let url = '/indexBanner.json'
			return vm.$u.get(url)
		},
		// 获取人气热卖商品
		getHotSale:() => {
			let url = '/hotSale.json'
			return vm.$u.get(url)
		},
		// 获取商品页banner图
		getGoodsBanner:() => {
			let url = '/goodsBanner.json'
			return vm.$u.get(url)
		},
		// 获取商品分类图
		getCatelogGoods:() => {
			let url = '/catelogGoods.json'
			return vm.$u.get(url)
		},
		
	}
}
// 抛出当前模块
export default query
