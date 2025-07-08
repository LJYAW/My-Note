<template>
	<view class="goods-detail-wrap">
		<view class="uni-margin-wrap">
			<swiper class="swiper" circular :indicator-dots="true" :autoplay="true" :interval="3000" :duration="500">
				<swiper-item>
					<view class="swiper-item">
						<image :src="goodsInfo.src" :alt="goodsInfo.name" mode="aspectFill">
					</view>
				</swiper-item>
				<swiper-item v-for="(item,index) in imgList" :key="index">
					<view class="swiper-item">
						<image :src="item" mode="aspectFill">
					</view>
				</swiper-item>
			</swiper>
		</view>
		
		<view class="goods-message">
			<view class="price"><span>¥</span>{{goodsInfo.price}}</view>
			<view class="title">{{goodsInfo.name}}</view>
		</view>
	</view>
</template>

<script>
import { mapState } from 'vuex';
export default {
	data() {
		return {
			imgList:[]
		}
	},
	computed: {
		...mapState(['goodsInfo'])
	},
	created(){
		this.imgList=this.goodsInfo.detail?this.goodsInfo.detail.imgList:[]
	},
	methods: {
		
	}
}
</script>

<style lang='scss' scoped>
.goods-detail-wrap {
	background-color: #fff;
	.uni-margin-wrap {
		.swiper {
			height: 600rpx;
			.swiper-item {
				height: 600rpx;
			}
			image {
				width:100%;
				height: 100%;
				display: inline-block;
			}
		}
	}
	.goods-message {
		height: calc(100vh - 600rpx);
		.price {
			color: #65c4aa;
			padding: 20rpx 30rpx 10rpx 30rpx;
			font-size: 44rpx;
			span {
				font-size: 34rpx;
				margin-right: 8rpx;
			}
		}
		.title {
			font-size: 32rpx;
			padding-left: 30rpx;
		}
	}
}
</style>
