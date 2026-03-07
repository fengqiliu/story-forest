<template>
  <view class="splash-page">
    <view class="splash-content">
      <!-- Logo 动画区域 -->
      <view class="logo-wrapper">
        <view class="logo-icon">🌲</view>
        <text class="app-name">故事森林</text>
        <text class="app-slogan">让孩子爱上阅读的魔法世界</text>
      </view>

      <!-- 装饰元素 -->
      <view class="decoration">
        <text class="deco-icon left">🦊</text>
        <text class="deco-icon right">🦉</text>
      </view>

      <!-- 加载状态 -->
      <view class="loading-wrapper">
        <view class="loading-dots">
          <view class="dot"></view>
          <view class="dot"></view>
          <view class="dot"></view>
        </view>
        <text class="loading-text">加载中...</text>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      isLoading: true
    }
  },
  onLoad() {
    this.checkLoginStatus()
  },
  methods: {
    async checkLoginStatus() {
      // 模拟加载延迟
      await new Promise(resolve => setTimeout(resolve, 1500))

      // 检查登录状态
      const token = uni.getStorageSync('token')
      const userInfo = uni.getStorageSync('userInfo')

      if (token && userInfo) {
        // 已登录，检查是否设置了年龄段
        if (userInfo.ageGroup) {
          // 已设置年龄，跳转到首页
          uni.switchTab({
            url: '/pages/index/index'
          })
        } else {
          // 未设置年龄，跳转到年龄选择页
          uni.reLaunch({
            url: '/pages/age-select/age-select'
          })
        }
      } else {
        // 未登录，跳转到登录页
        uni.reLaunch({
          url: '/pages/login/login'
        })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.splash-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #2D6A4F 0%, #1B4332 100%);
  display: flex;
  align-items: center;
  justify-content: center;
}

.splash-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 48px 24px;
}

.logo-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 80px;

  .logo-icon {
    font-size: 80px;
    margin-bottom: 16px;
    animation: bounce 1s ease-in-out infinite;
  }

  .app-name {
    font-size: 32px;
    font-weight: 600;
    color: #FFFFFF;
    margin-bottom: 12px;
    letter-spacing: 4px;
  }

  .app-slogan {
    font-size: 16px;
    color: rgba(255, 255, 255, 0.8);
    letter-spacing: 2px;
  }
}

.decoration {
  position: relative;
  width: 200px;
  height: 60px;
  margin-bottom: 40px;

  .deco-icon {
    position: absolute;
    font-size: 40px;
    animation: float 2s ease-in-out infinite;

    &.left {
      left: 20px;
      animation-delay: 0s;
    }

    &.right {
      right: 20px;
      animation-delay: 1s;
    }
  }
}

.loading-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;

  .loading-dots {
    display: flex;
    gap: 8px;
    margin-bottom: 12px;

    .dot {
      width: 10px;
      height: 10px;
      border-radius: 50%;
      background: rgba(255, 255, 255, 0.6);
      animation: dotBounce 1.4s infinite ease-in-out;

      &:nth-child(1) {
        animation-delay: -0.32s;
      }

      &:nth-child(2) {
        animation-delay: -0.16s;
      }
    }
  }

  .loading-text {
    font-size: 14px;
    color: rgba(255, 255, 255, 0.6);
  }
}

/* 动画定义 */
@keyframes bounce {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-10px);
  }
}

@keyframes float {
  0%, 100% {
    transform: translateY(0) rotate(0deg);
  }
  50% {
    transform: translateY(-8px) rotate(5deg);
  }
}

@keyframes dotBounce {
  0%, 80%, 100% {
    transform: scale(0);
    opacity: 0.5;
  }
  40% {
    transform: scale(1);
    opacity: 1;
  }
}
</style>
