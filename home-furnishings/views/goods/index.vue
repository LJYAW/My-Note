<template>
	<view class="goods-wrap">
		<view class="uni-margin-wrap">
			<swiper class="swiper" circular :indicator-dots="true" :autoplay="true" :interval="3000" :duration="500">
				<swiper-item v-for="(item,index) in goodsBanner" :key="index">
					<view class="swiper-item">
						<image :src="item.src" :alt="item.name" mode="aspectFill" @click="toDetail(item)">
					</view>
				</swiper-item>
			</swiper>
		</view>
		
		<view class="goods-content-wrap">
			<scroll-view scroll-y="true" class="catelog-wrap">
				<view v-for="(item,index) in catelogGoods" :key="index" @click="activeIndex=index" :class="[activeIndex==index&&'active']">
					<view class="cate-title">
						{{item.name}}
						<view class="border"></view>
					</view>
				</view>
			</scroll-view>
			
			<view class="content-wrap">
				<view v-for="(item,index) in goodsList" :key="index" class="goods-item" @click="toDetail(item)">
					<image :src="item.src" mode="aspectFill"></image>
					<view class="message">
						<view class="goods-title">{{item.name}}</view>
						<view class="price"><span>¥</span>{{item.price}}</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
import {goodsBanner} from '../../static/js/goodsBanner.js'
import {catelogGoods} from '../../static/js/catelogGoods.js'
export default {
	data() {
		return {
			goodsBanner,
			catelogGoods,
			activeIndex:0,
		}
	},
	created(){
		// this.getBannerData()
		// this.getCatelogGoods()
	},
	computed:{
		goodsList(){
			if(this.catelogGoods.length){
				return this.catelogGoods[this.activeIndex].list
			}
		}
	},
	methods: {
		getBannerData(){
			this.$u.api.query.getGoodsBanner().then((res)=>{
				this.goodsBanner=res.goodsBanner
			})
		},
		getCatelogGoods(){
			this.$u.api.query.getCatelogGoods().then((res)=>{
				this.catelogGoods=res.catelogGoods
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
.goods-wrap {
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
	.goods-content-wrap {
		display: flex;
		margin-top: 20rpx;
		.catelog-wrap {
			width: 160rpx;
			height: calc(100vh - 420rpx);
			background-color: #f8f9fb;
			border-right: 1px solid #eee;
			.cate-title {
				color: #999;
				font-size: 24rpx;
				padding: 30rpx 0;
				text-align: center;
				position: relative;
				.border {
					width: 8rpx;
					height: 100%;
					border-radius: 16rpx;
					position: absolute;
					left: 0;
					top: 0;
					background-color: #65c4aa;
					display: none;
				}
			}
			.active {
				.cate-title{
					background-color: #fff;
					color: #65c4aa;
					.border{
						display: block;
					}
				}
			}
		}
		.content-wrap {
			background-color: #fff;
			flex: 1;
			padding: 20rpx;
			padding-bottom: 10rpx;
			height: calc(100vh - 420rpx);
			overflow-y: auto;
			box-sizing: border-box;
			.goods-item {
				border: 1px solid #eee;
				box-shadow: 0 0 14px rgba(0,0,0,.1);
				padding: 10rpx;
				border-radius: 12rpx;
				margin-bottom: 20rpx;
				display: flex;
				image {
					display: inline-block;
					width: 150rpx;
					height: 150rpx;
					border: 1px solid #eee;
					border-radius: 12rpx;
					margin-right: 16rpx;
				}
				.message {
					flex: 1;
					display:flex;
					flex-direction: column;
					justify-content: space-between;
					.goods-title {
						font-size: 28rpx;
						text-overflow: -o-ellipsis-lastline;
						overflow: hidden;				//溢出内容隐藏
						text-overflow: ellipsis;		//文本溢出部分用省略号表示
						display: -webkit-box;			//特别显示模式
						-webkit-line-clamp: 2;			//行数
						line-clamp: 2;					
						-webkit-box-orient: vertical;	//盒子中内容竖直排列
						color: #353535;
					}
					.price {
						font-size: 34rpx;
						color: #65c4aa;
						span {
							font-size: 24rpx;
							margin-right: 4rpx;
						}
					}
				}
			}
		}
	}
}
</style>