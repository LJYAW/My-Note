<template>
	<view class="about-wrap">
		<image src="../../static/logo.jpg" mode="aspectFill"></image>
		
		<view class="content-wrap">
			<view class="content-item" v-for="(item,index) in aboutMessage">
				<span class="title">{{item.title}}</span>
				<span class="message">{{item.value}}</span>
			</view>
		</view>
	</view>
</template>

<script>
import {aboutMessage} from '../../static/js/about.js'
export	default {
	data(){
		return{
			aboutMessage
		}
	}
}
</script>

<style lang="scss" scoped>
.about-wrap {
	image {
		display: block;
		width: 100%;
		height: 400rpx;
	}
	.content-wrap {
		background-color: #fff;
		margin-top: 20rpx;
		height: calc(100vh - 420rpx);
		.content-item {
			border-bottom: 1rpx solid #eee;
			padding: 40rpx;
			font-size: 30rpx;
			display: flex;
			justify-content: space-between;
			.title {
				width: 200rpx;
			}
			.message {
				flex: 1;
				text-align: right;
				color: #999;
			}
		}
	}
}
</style>