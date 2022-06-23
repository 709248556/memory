<template>
	<view class="content">
		<view class="row b-b">
			<text class="tit">类型</text>
			<uni-data-select id="input" v-model="memory.type" :localdata="range"></uni-data-select>
		</view>
		<view class="row b-b">
			<text class="tit">题目</text>
			<input class="input" type="text" v-model="memory.question" placeholder="请获取题目"
				placeholder-class="placeholder" />
		</view>
		<view class="row b-b" id="answer">
			<text class="tit">答案</text>
			<textarea v-model="memory.answer" maxlength="-1" placeholder="请输入答案"></textarea>
		</view>
		<view class="row b-b" v-show="show" id="answer">
			<text class="tit">正确答案</text>
			<textarea v-model="memory.rightAnswer" maxlength="-1"></textarea>
		</view>
		<view class="row b-b" v-show="flag">
			<text class="tit">提示</text>
			<input class="input" type="text" v-model="memory.tip" placeholder="" placeholder-class="placeholder" />
		</view>
		<view class="row b-b" v-show="flag">
			<text class="tit">比重</text>
			<input class="input" type="number" v-model="memory.weight" placeholder="" placeholder-class="placeholder" />
		</view>
		<view class="row b-b" v-show="flag">
			id: {{memory.id}}
		</view>
		<view class="row b-b" >
			<button class="add-btn" @click="getSingleQuestion">获取题目</button>
			<button class="add-btn" @click="getSingleAnswer">查看正确答案</button>
		</view>
		<view class="row b-b">
			<button class="add-btn" @tap="raise">熟悉</button>
			<button class="add-btn" @tap="lower">陌生</button>
			<button class="add-btn" @tap="deleted">删除</button>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				flag: false,
				memory: {
					id: '',
					type: '',
					question: '',
					answer: '',
					rightAnswer: '',
					tip: '',
					weight: '',
				},
				footprintList: [],
				value: 0,
				range: [{
						value: 1,
						text: "教资"
					},
					{
						value: 2,
						text: "考编"
					},
					{
						value: 3,
						text: "行测"
					},
					{
						value: 4,
						text: "数学"
					},
					{
						value: 5,
						text: "申论"
					},
					{
						value: 7,
						text: "言语"
					}
				],
			}
		},
		methods: {
			getSingleQuestion() {
				if (this.memory.type == '') {
					uni.showToast({
						title: "请输入类型!",
						icon: "none"
					});
					return false;
				}
				uni.request({
					url: 'http://120.25.202.245/getSingleQuestion',
					method: 'GET',
					header: {
						'content-type': 'application/json'
					},
					data: {
						type: this.memory.type,
					},
					success: res => {
						console.log('success', res);
						if (res.statusCode == 200) {
							this.memory.id = res.data.id;
							this.memory.question = res.data.question;
							this.flag = false;
						} else {
							uni.showToast({
								title: "获取题目失败",
								duration: 2000
							});
						}
					},
					fail: () => {
						console.log('err', err)
						uni.showToast({
							title: "服务器响应失败，请稍后再试！",
							icon: "error"
						});
					},
					complete: () => {}
				})
			},
			getSingleAnswer() {
				console.log(this.memory.id)
				if (this.memory.id == '') {
					uni.showToast({
						title: "没有对应id",
						icon: "none"
					});
					return false;
				}
				uni.request({
					url: 'http://120.25.202.245/getSingleAnswer',
					method: 'GET',
					header: {
						'content-type': 'application/json'
					},
					data: {
						id: this.memory.id,
					},
					success: res => {
						console.log('success', res);
						if (res.statusCode == 200) {
							this.memory.rightAnswer = res.data.answer;
							this.memory.tip = res.data.tip;
							this.memory.weight = res.data.weight;
							this.flag = true;
						} else {
							uni.showToast({
								title: "获取正确答案失败",
								duration: 2000
							});
						}
					},
					fail: () => {
						console.log('err', err)
						uni.showToast({
							title: "服务器响应失败，请稍后再试！",
							icon: "error"
						});
					},
					complete: () => {}
				})
			},
			raise() {
				if (this.memory.id == '') {
					uni.showToast({
						title: "没有对应id",
						icon: "none"
					});
					return false;
				}
				uni.request({
					url: 'http://120.25.202.245/raise',
					method: 'GET',
					header: {
						'content-type': 'application/json'
					},
					data: {
						id: this.memory.id,
					},
					success: res => {
						uni.showToast({
							title: "权重已上升",
							duration: 2000
						});
					},
					fail: () => {
						console.log('err', err)
						uni.showToast({
							title: "服务器响应失败，请稍后再试！",
							icon: "error"
						});
					},
					complete: () => {}
				})
			},
			lower() {
				if (this.memory.id == '') {
					uni.showToast({
						title: "没有对应id",
						icon: "none"
					});
					return false;
				}
				uni.request({
					url: 'http://120.25.202.245/lower',
					method: 'GET',
					header: {
						'content-type': 'application/json'
					},
					data: {
						id: this.memory.id,
					},
					success: res => {
						uni.showToast({
							title: "权重已下降",
							duration: 2000
						});
					},
					fail: () => {
						console.log('err', err)
						uni.showToast({
							title: "服务器响应失败，请稍后再试！",
							icon: "error"
						});
					},
					complete: () => {}
				})
			},
			deleted() {
				if (this.memory.id == '') {
					uni.showToast({
						title: "没有对应id",
						icon: "none"
					});
					return false;
				}
				uni.request({
					url: 'http://120.25.202.245/delete',
					method: 'GET',
					header: {
						'content-type': 'application/json'
					},
					data: {
						id: this.memory.id,
					},
					success: res => {
						console.log('success', res);
						if (res.statusCode == 200) {
							uni.showToast({
								title: "题目已删除",
								duration: 2000
							});
						}
					},
					fail: () => {
						console.log('err', err)
						uni.showToast({
							title: "服务器响应失败，请稍后再试！",
							icon: "error"
						});
					},
					complete: () => {}
				})
			}
		}
	}
</script>

<style>
	page {
		padding-top: 16upx;
	}

	.content {
		padding: 15rpx;
		box-sizing: border-box;
	}

	.row {
		display: flex;
		align-items: center;
		position: relative;
		padding: 0 30upx;
		height: 110upx;
		background: #fff;
		margin-bottom: 1px;

		.tit {
			flex-shrink: 0;
			width: 120upx;
			font-size: 30upx;

		}

		.input {
			flex: 1;
			font-size: 30upx;
		}

		.icon-shouhuodizhi {
			font-size: 36upx;

		}
	}

	#answer {
		display: flex;
		align-items: center;
		position: relative;
		padding: 0 30upx;
		height: auto;
		background: #fff;
		margin-bottom: 1px;
	}

	.tit {
		width: 40px;
		font-size: 30upx;
	}

	.tit>span {
		display: block;
	}

	.add-btn {
		display: flex;
		align-items: center;
		justify-content: center;
		/* width: 690upx; */
		height: 80upx;
		font-size: 20px;
		margin: 60upx auto;
		background-color: rgb(28, 42, 134);
		color: #fff;

		border-radius: 10upx;
		box-shadow: 1px 2px 5px rgba(28, 42, 134, 0.4);
	}

	#input {
		min-height: 36px;
		position: relative;
		display: flex;
		flex: 1;
		flex-direction: row;
		width: 259px;
		align-items: center;
	}

	uni-input,
	textarea {
		border: 1px solid #DCDCDC;
		width: 280px;
		padding: 5px;
		border-radius: 5px;
		margin: 15px;
		margin-right: 0px;
	}

	@media screen and (max-width: 500px) {
		.hide-on-phone[data-v-6b64008e] {
			display: block;
		}
	}
</style>
