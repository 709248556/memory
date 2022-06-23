<template>
	<view class="content">
		<view class="row b-b">
			<text class="tit">id</text>
			<input class="input" type="text" v-model="memory.id" placeholder="请输入id" placeholder-class="placeholder" />
		</view>
		<view class="row b-b">
			<text class="tit">类型</text>
			<uni-data-select id="input" v-model="memory.type" :localdata="range"></uni-data-select>
		</view>
		<view class="row b-b">
			<text class="tit">题目</text>
			<input class="input" type="text" v-model="memory.question" placeholder="" placeholder-class="placeholder" />
		</view>
		<view class="row b-b" id="answer">
			<text class="tit">答案</text>
			<!-- <input class="input" type="text" v-model="memory.answer" placeholder="" placeholder-class="placeholder" /> -->
			<textarea class="input" v-model="memory.answer" maxlength="-1" placeholder="请输入答案"></textarea>
		</view>
		<view class="row b-b">
			<text class="tit">提示</text>
			<input class="input" type="text" v-model="memory.tip" placeholder="" placeholder-class="placeholder" />
		</view>
		<view class="row b-b">
			<text class="tit">比重</text>
			<input class="input" type="number" v-model="memory.weight" placeholder="" placeholder-class="placeholder" />
		</view>
		<view class="row b-b">
			<button class="add-btn" @tap="getSingleAnswer">获取题目信息</button>
			<button class="add-btn" @tap="edit">保存</button>
		</view>
	</view>
</template>



<script>
	export default {
		data() {
			return {
				memory: {
					id: '',
					type: '',
					question: '',
					answer: '',
					tip: '',
					weight: '',
				},
				footprintList: [],
				value: 0,
				range: [{
						value: 0,
						text: "请选择类型"
					}, {
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
			getSingleAnswer() {
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
							this.memory.type = res.data.type;
							this.memory.question = res.data.question;
							this.memory.answer = res.data.answer;
							this.memory.tip = res.data.tip;
							this.memory.weight = res.data.weight;
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
			edit() {
				uni.request({
					url: 'http://120.25.202.245/edit',
					method: 'POST',
					header: {
						'content-type': 'application/json'
					},
					data: {
						id: this.memory.id,
						type: this.memory.type,
						question: this.memory.question,
						answer: this.memory.answer,
						tip: this.memory.tip,
						weight: this.memory.weight,
					},
					success: res => {
						console.log('success', res);
						if (res.statusCode == 200) {
							this.memory.type = res.data.type;
							this.memory.question = res.data.question;
							this.memory.answer = res.data.answer;
							this.memory.tip = res.data.tip;
							this.memory.weight = res.data.weight;
							uni.showToast({
								title: "编辑提交成功",
								duration: 2000
							});
						} else {
							uni.showToast({
								title: "编辑提交失败",
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
			/* flex: 1; */
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

	uni-input,textarea {
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
