<template>
  <view class="login-page">
    <!-- 顶部插图区域 -->
    <view class="illustration-wrapper">
      <view class="illustration">
        <text class="illustration-icon">🌲🦊🌳</text>
      </view>
    </view>

    <!-- 内容区域 -->
    <view class="content-wrapper">
      <text class="welcome-title">欢迎来到故事森林</text>
      <text class="welcome-subtitle">专为 3-10 岁儿童打造的<br/>互动阅读成长平台</text>

      <!-- 价值主张 -->
      <view class="features-list">
        <view class="feature-item">
          <text class="feature-icon">✨</text>
          <text class="feature-text">海量优质故事</text>
        </view>
        <view class="feature-item">
          <text class="feature-icon">🎯</text>
          <text class="feature-text">AI 个性化推荐</text>
        </view>
        <view class="feature-item">
          <text class="feature-icon">🏆</text>
          <text class="feature-text">趣味互动成长</text>
        </view>
        <view class="feature-item">
          <text class="feature-icon">👨‍👩‍👧</text>
          <text class="feature-text">家长安心管控</text>
        </view>
      </view>
    </view>

    <!-- 底部按钮区域 -->
    <view class="bottom-wrapper safe-area-bottom">
      <u-button
        type="primary"
        size="large"
        shape="round"
        class="login-btn"
        @click="handleWechatLogin"
      >
        <view class="btn-content">
          <text class="btn-icon">🐼</text>
          <text class="btn-text">微信一键登录</text>
        </view>
      </u-button>

      <text class="agreement-text">
        登录即表示同意
        <text class="link-text" @click.stop="openPage('user-agreement')">《用户协议》</text>
        和
        <text class="link-text" @click.stop="openPage('privacy-policy')">《隐私政策》</text>
      </text>
    </view>

    <!-- 加载状态 -->
    <u-loading-page :loading="isLoading" loading-text="登录中..." />
  </view>
</template>

<script>
export default {
  data() {
    return {
      isLoading: false
    }
  },
  methods: {
    async handleWechatLogin() {
      this.isLoading = true

      try {
        // #ifdef MP-WEIXIN
        // 微信小程序登录
        const { code } = await uni.login({
          provider: 'weixin'
        })

        // 调用后端 API 获取 token
        const res = await this.loginByWechat(code)

        if (res.code === 0) {
          // 保存 token 和用户信息
          uni.setStorageSync('token', res.data.token)
          uni.setStorageSync('userInfo', res.data.userInfo)

          // 检查是否设置了年龄段
          if (!res.data.userInfo.ageGroup) {
            // 未设置年龄，跳转到年龄选择页
            uni.reLaunch({
              url: '/pages/age-select/age-select'
            })
          } else {
            // 已设置年龄，跳转到首页
            uni.switchTab({
              url: '/pages/index/index'
            })
          }
        } else {
          uToast.error({ title: res.message || '登录失败' })
        }
        // #endif

        // #ifdef H5
        // H5 模拟登录（开发测试用）
        await new Promise(resolve => setTimeout(resolve, 1000))

        const mockUserInfo = {
          id: 1,
          nickname: '测试用户',
          avatar: '',
          ageGroup: null
        }

        uni.setStorageSync('token', 'mock_token_' + Date.now())
        uni.setStorageSync('userInfo', mockUserInfo)

        // 跳转到年龄选择页
        uni.reLaunch({
          url: '/pages/age-select/age-select'
        })
        // #endif

      } catch (error) {
        console.error('登录失败:', error)
        uToast.error({ title: '登录失败，请重试' })
      } finally {
        this.isLoading = false
      }
    },

    async loginByWechat(code) {
      // TODO: 替换为实际的 API 地址
      return new Promise((resolve) => {
        // 模拟 API 请求
        setTimeout(() => {
          resolve({
            code: 0,
            message: 'success',
            data: {
              token: 'mock_token',
              userInfo: {
                id: 1,
                nickname: '微信用户',
                avatar: '',
                ageGroup: null
              }
            }
          })
        }, 500)

        // 实际 API 调用示例:
        // return uni.request({
        //   url: 'https://api.example.com/auth/wechat-login',
        //   method: 'POST',
        //   data: { code }
        // })
      })
    },

    openPage(page) {
      // 打开用户协议或隐私政策页面
      uni.navigateTo({
        url: `/pages/${page}/${page}`
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.login-page {
  min-height: 100vh;
  background: linear-gradient(180deg, #FFFBF0 0%, #F0F7F4 100%);
  display: flex;
  flex-direction: column;
}

.illustration-wrapper {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px 24px;

  .illustration {
    text-align: center;

    .illustration-icon {
      font-size: 64px;
      display: block;
      margin-bottom: 16px;
    }
  }
}

.content-wrapper {
  padding: 0 32px 40px;

  .welcome-title {
    display: block;
    font-size: 28px;
    font-weight: 600;
    color: #1B4332;
    text-align: center;
    margin-bottom: 12px;
  }

  .welcome-subtitle {
    display: block;
    font-size: 16px;
    color: #52796F;
    text-align: center;
    line-height: 1.8;
    margin-bottom: 32px;
  }

  .features-list {
    .feature-item {
      display: flex;
      align-items: center;
      padding: 12px 16px;
      margin-bottom: 8px;
      background: #FFFFFF;
      border-radius: 12px;
      box-shadow: 0 2px 8px rgba(45, 106, 79, 0.08);

      .feature-icon {
        font-size: 24px;
        margin-right: 12px;
      }

      .feature-text {
        font-size: 16px;
        color: #1B4332;
      }
    }
  }
}

.bottom-wrapper {
  padding: 24px 32px 32px;

  .login-btn {
    margin-bottom: 16px;
    background: linear-gradient(135deg, #FF8C42 0%, #F57C00 100%);
    border: none;

    .btn-content {
      display: flex;
      align-items: center;
      justify-content: center;

      .btn-icon {
        font-size: 20px;
        margin-right: 8px;
      }

      .btn-text {
        font-size: 18px;
        font-weight: 500;
        color: #FFFFFF;
      }
    }
  }

  .agreement-text {
    display: block;
    font-size: 12px;
    color: #94B49F;
    text-align: center;
    line-height: 1.8;

    .link-text {
      color: #2D6A4F;
      text-decoration: underline;
    }
  }
}
</style>
