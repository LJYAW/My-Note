<template>
	<view class="container">
		<view class="uni-margin-wrap">
			<swiper class="swiper" circular :indicator-dots="true" :autoplay="true" :interval="3000" :duration="500">
				<swiper-item v-for="(item,index) in indexBanner" :key="index">
					<view class="swiper-item">
						<image :src="item.src" :alt="item.name" mode="aspectFill" @click="toDetail(item)">
					</view>
				</swiper-item>
			</swiper>
		</view>
		<!-- 人气热卖 -->
		<view class="hot-sale-wrap">
			<view class='title'>-<span>人气热卖</span>-</view>
			<div class="sale-wrap">
				<view class="sale-item" v-for="(item,index) in hotSaleCommodity" :key="index" @click="toDetail(item)">
					<view class="img-wrap">
						<image :src="item.src" :alt="item.name" mode="aspectFill">
					</view>
					<view class="name">{{item.name}}</view>
					<view class="price"><span>¥</span>{{item.price}}</view>
				</view>
			</div>
			
		</view>
	</view>
</template>

<script>
import {indexBanner} from '../../static/js/indexBanner.js'
import {hotSale} from '../../static/js/hotSale.js'
export default {
	data() {
		return {
			indexBanner,
			hotSaleCommodity:hotSale
		}
	},
	created(){
		// this.getData()
		// this.getHotSaleData()
	},
	methods: {
		getData(){
			this.$u.api.query.getIndexBanner().then((res)=>{
				this.indexBanner=res.indexBanner
			})
		},
		getHotSaleData(){
			this.$u.api.query.getHotSale().then((res)=>{
				this.hotSaleCommodity=res.hotSale
			})
		},
		toDetail(obj){
			this.$store.commit('SET_GOODS_INFO',obj)
			this.$u.route({
				url: 'views/detail/detail',
			})
		}
	}
}
</script>

<style lang="scss" scoped>
.container {
	.uni-margin-wrap {
		.swiper {
			height: 400rpx;
			.swiper-item {
				height: 400rpx;
			}
			image {
				width:100%;
				height: 100%;
				display: inline-block;
			}
		}
	}
	.hot-sale-wrap {
		background-color: #fff;
		padding: 20rpx 30rpx;
		margin-top: 20rpx;
		.title {
			text-align: center;
			color: #707070;
			span {
				font-size: 32rpx;
				font-weight: 700;
				padding: 0 20rpx;
				color: #353535;
			}
		}
		.sale-wrap {
			display: flex;
			flex-wrap: wrap;
			margin-top: 20rpx;
			.sale-item {
				box-shadow: 0 0 14px rgba(0,0,0,.1);
				border-radius: 12rpx;
				margin-right: 30rpx;
				margin-bottom: 30rpx;
				width:calc((100vw - 90rpx)/2);
				&:nth-child(2n){
					margin-right: 0;
				}
				.img-wrap {
					height: 400rpx;
					padding: 1rpx;
					// border-bottom: 1rpx solid #eee;
					image {
						width: 100%;
						height: inherit;
						border-top-left-radius:12rpx;
						border-top-right-radius:12rpx;
						display: inline-block;
					}
				}
				.name {
					padding: 20rpx;
					padding-bottom: 0;
					font-size: 24rpx;
				}
				.price {
					color: #65c4aa;
					padding: 10rpx 20rpx;
					// font-size: 30rpx;
					font-size: 34rpx;
					span {
						font-size: 24rpx;
						margin-right: 4rpx;
					}
				}
			}
		}
	}
}
</style>
