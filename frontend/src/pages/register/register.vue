<template>
  <view class="register-page">
    <view class="register-header">
      <text class="page-title">创建账号</text>
      <text class="page-subtitle">完善信息，开启阅读之旅</text>
    </view>

    <view class="form-wrapper">
      <!-- 昵称输入 -->
      <view class="form-item">
        <view class="form-label">
          <text class="label-icon">👤</text>
          <text class="label-text">昵称</text>
        </view>
        <u-input
          v-model="nickname"
          placeholder="请输入昵称（可选）"
          :border="false"
          clearable
        />
      </view>

      <!-- 手机号输入 -->
      <view class="form-item">
        <view class="form-label">
          <text class="label-icon">📱</text>
          <text class="label-text">手机号</text>
        </view>
        <u-input
          v-model="phone"
          type="number"
          maxlength="11"
          placeholder="请输入手机号"
          :border="false"
          clearable
        />
      </view>

      <!-- 验证码 -->
      <view class="form-item">
        <view class="form-label">
          <text class="label-icon">🔐</text>
          <text class="label-text">验证码</text>
        </view>
        <view class="code-input-wrapper">
          <u-input
            v-model="verifyCode"
            type="number"
            maxlength="6"
            placeholder="请输入验证码"
            :border="false"
            style="flex: 1"
          />
          <u-button
            size="small"
            :disabled="!canSendCode || isSending"
            class="send-code-btn"
            @click="sendVerifyCode"
          >
            {{ isSending ? `${countdown}s` : '获取验证码' }}
          </u-button>
        </view>
      </view>

      <!-- 家长信息 -->
      <view class="form-item">
        <view class="form-label">
          <text class="label-icon">👨‍👩‍👧</text>
          <text class="label-text">家长称呼</text>
        </view>
        <u-input
          v-model="parentName"
          placeholder="请输入家长称呼（可选）"
          :border="false"
          clearable
        />
      </view>

      <!-- 同意协议 -->
      <view class="agreement-item">
        <u-checkbox
          v-model="agreeAgreement"
          shape="circle"
          size="18"
        >
          <text class="agreement-text">
            我已阅读并同意
            <text class="link-text" @click.stop="openPage('user-agreement')">《用户协议》</text>
            和
            <text class="link-text" @click.stop="openPage('privacy-policy')">《隐私政策》</text>
          </text>
        </u-checkbox>
      </view>
    </view>

    <!-- 提交按钮 -->
    <view class="submit-wrapper safe-area-bottom">
      <u-button
        type="primary"
        size="large"
        shape="round"
        class="submit-btn"
        :disabled="!canSubmit"
        @click="handleSubmit"
      >
        完成注册
      </u-button>
    </view>

    <!-- 加载状态 -->
    <u-loading-page :loading="isLoading" loading-text="注册中..." />
  </view>
</template>

<script>
export default {
  data() {
    return {
      nickname: '',
      phone: '',
      verifyCode: '',
      parentName: '',
      agreeAgreement: false,
      canSendCode: true,
      isSending: false,
      countdown: 0,
      isLoading: false
    }
  },
  computed: {
    canSubmit() {
      // 手机号必须填写，验证码必须填写，必须同意协议
      return this.phone.length === 11 &&
             this.verifyCode.length === 6 &&
             this.agreeAgreement
    }
  },
  methods: {
    async sendVerifyCode() {
      if (this.phone.length !== 11) {
        uToast.warning({ title: '请输入正确的手机号' })
        return
      }

      this.isSending = true
      this.countdown = 60

      try {
        // TODO: 调用发送验证码 API
        await this.requestVerifyCode(this.phone)

        uToast.success({ title: '验证码已发送' })

        // 倒计时
        const timer = setInterval(() => {
          this.countdown--
          if (this.countdown <= 0) {
            clearInterval(timer)
            this.isSending = false
            this.canSendCode = true
          }
        }, 1000)

      } catch (error) {
        console.error('发送验证码失败:', error)
        uToast.error({ title: '发送失败，请重试' })
        this.isSending = false
      }
    },

    async requestVerifyCode(phone) {
      // TODO: 替换为实际的 API 地址
      return new Promise((resolve) => {
        setTimeout(resolve, 500)

        // 实际 API 调用示例:
        // return uni.request({
        //   url: 'https://api.example.com/auth/send-code',
        //   method: 'POST',
        //   data: { phone }
        // })
      })
    },

    async handleSubmit() {
      if (!this.canSubmit) {
        uToast.warning({ title: '请填写完整信息' })
        return
      }

      this.isLoading = true

      try {
        // TODO: 调用注册 API
        const res = await this.register({
          phone: this.phone,
          code: this.verifyCode,
          nickname: this.nickname,
          parentName: this.parentName
        })

        if (res.code === 0) {
          // 保存 token 和用户信息
          uni.setStorageSync('token', res.data.token)
          uni.setStorageSync('userInfo', res.data.userInfo)

          // 跳转到年龄选择页
          uni.reLaunch({
            url: '/pages/age-select/age-select'
          })
        } else {
          uToast.error({ title: res.message || '注册失败' })
        }

      } catch (error) {
        console.error('注册失败:', error)
        uToast.error({ title: '注册失败，请重试' })
      } finally {
        this.isLoading = false
      }
    },

    async register(data) {
      // TODO: 替换为实际的 API 地址
      return new Promise((resolve) => {
        setTimeout(() => {
          resolve({
            code: 0,
            message: 'success',
            data: {
              token: 'mock_token',
              userInfo: {
                id: 1,
                nickname: data.nickname || '新用户',
                phone: data.phone,
                ageGroup: null
              }
            }
          })
        }, 1000)
      })
    },

    openPage(page) {
      uni.navigateTo({
        url: `/pages/${page}/${page}`
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.register-page {
  min-height: 100vh;
  background: linear-gradient(180deg, #FFFBF0 0%, #F0F7F4 100%);
  padding: 24px;
}

.register-header {
  margin-bottom: 32px;

  .page-title {
    display: block;
    font-size: 28px;
    font-weight: 600;
    color: #1B4332;
    text-align: center;
    margin-bottom: 8px;
  }

  .page-subtitle {
    display: block;
    font-size: 16px;
    color: #52796F;
    text-align: center;
  }
}

.form-wrapper {
  margin-bottom: 32px;

  .form-item {
    padding: 16px;
    margin-bottom: 16px;
    background: #FFFFFF;
    border-radius: 12px;

    .form-label {
      display: flex;
      align-items: center;
      margin-bottom: 12px;

      .label-icon {
        font-size: 18px;
        margin-right: 8px;
      }

      .label-text {
        font-size: 16px;
        font-weight: 500;
        color: #1B4332;
      }
    }

    .code-input-wrapper {
      display: flex;
      align-items: center;
      gap: 12px;

      .send-code-btn {
        flex-shrink: 0;
        padding: 0 16px;
        height: 36px;
        font-size: 14px;
      }
    }
  }

  .agreement-item {
    padding: 16px;

    .agreement-text {
      font-size: 14px;
      color: #52796F;
      line-height: 1.6;

      .link-text {
        color: #2D6A4F;
        text-decoration: underline;
      }
    }
  }
}

.submit-wrapper {
  .submit-btn {
    background: linear-gradient(135deg, #2D6A4F 0%, #1B4332 100%);
    border: none;
  }
}
</style>
