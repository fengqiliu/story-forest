<template>
  <view class="age-select-page">
    <!-- 返回按钮 -->
    <view class="back-btn" @click="handleBack" v-if="canBack">
      <u-icon name="arrow-left" size="24" color="#1B4332"></u-icon>
    </view>

    <!-- 内容区域 -->
    <view class="content-wrapper">
      <text class="page-title">小朋友几岁啦？🎂</text>
      <text class="page-subtitle">选择年龄，为你匹配适合的故事</text>

      <!-- 年龄选项列表 -->
      <view class="age-list">
        <view
          v-for="item in ageOptions"
          :key="item.value"
          class="age-item"
          :class="{ selected: selectedAge === item.value }"
          @click="selectAge(item.value)"
        >
          <view class="age-icon">{{ item.icon }}</view>
          <view class="age-info">
            <text class="age-range">{{ item.label }}</text>
            <text class="age-stage">{{ item.stage }}</text>
          </view>
          <view class="age-check" v-if="selectedAge === item.value">
            <u-icon name="checkmark-circle-fill" size="24" color="#2D6A4F"></u-icon>
          </view>
        </view>
      </view>
    </view>

    <!-- 底部确定按钮 -->
    <view class="bottom-wrapper safe-area-bottom">
      <u-button
        type="primary"
        size="large"
        shape="round"
        class="confirm-btn"
        :disabled="!selectedAge"
        @click="handleConfirm"
      >
        确 定
      </u-button>
    </view>

    <!-- 加载状态 -->
    <u-loading-page :loading="isLoading" loading-text="保存中..." />
  </view>
</template>

<script>
export default {
  data() {
    return {
      selectedAge: null,
      isLoading: false,
      canBack: false, // 首次登录不允许返回
      ageOptions: [
        { value: '3-4', label: '3-4 岁', icon: '🍼', stage: '启蒙阶段' },
        { value: '5-6', label: '5-6 岁', icon: '🧸', stage: '幼儿阶段' },
        { value: '7-8', label: '7-8 岁', icon: '📚', stage: '小学低年级' },
        { value: '9-10', label: '9-10 岁', icon: '🎓', stage: '小学中年级' }
      ]
    }
  },
  onLoad() {
    // 检查是否从登录页跳转而来（首次登录）
    const scene = this.$page?.fullPath?.includes('from=login')
    this.canBack = !scene
  },
  methods: {
    selectAge(age) {
      this.selectedAge = age
      // 添加触觉反馈
      // #ifdef MP-WEIXIN
      uni.vibrateShort({ type: 'light' })
      // #endif
    },

    async handleConfirm() {
      if (!this.selectedAge) {
        uToast.warning({ title: '请先选择年龄' })
        return
      }

      this.isLoading = true

      try {
        // 获取当前用户信息
        const userInfo = uni.getStorageSync('userInfo') || {}
        userInfo.ageGroup = this.selectedAge

        // 保存到本地
        uni.setStorageSync('userInfo', userInfo)

        // 调用 API 更新用户年龄
        await this.updateUserAge(this.selectedAge)

        // 跳转到首页
        uni.switchTab({
          url: '/pages/index/index'
        })

      } catch (error) {
        console.error('保存年龄失败:', error)
        uToast.error({ title: '保存失败，请重试' })
      } finally {
        this.isLoading = false
      }
    },

    async updateUserAge(ageGroup) {
      // TODO: 替换为实际的 API 地址
      return new Promise((resolve) => {
        setTimeout(resolve, 300)

        // 实际 API 调用示例:
        // const token = uni.getStorageSync('token')
        // return uni.request({
        //   url: 'https://api.example.com/user/age-group',
        //   method: 'PUT',
        //   header: {
        //     Authorization: `Bearer ${token}`
        //   },
        //   data: { ageGroup }
        // })
      })
    },

    handleBack() {
      uni.navigateBack()
    }
  }
}
</script>

<style lang="scss" scoped>
.age-select-page {
  min-height: 100vh;
  background: linear-gradient(180deg, #FFFBF0 0%, #F0F7F4 100%);
  padding: 24px;
}

.back-btn {
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 16px;
}

.content-wrapper {
  margin-bottom: 40px;

  .page-title {
    display: block;
    font-size: 28px;
    font-weight: 600;
    color: #1B4332;
    text-align: center;
    margin-bottom: 12px;
  }

  .page-subtitle {
    display: block;
    font-size: 16px;
    color: #52796F;
    text-align: center;
    margin-bottom: 32px;
  }

  .age-list {
    .age-item {
      display: flex;
      align-items: center;
      padding: 20px 16px;
      margin-bottom: 16px;
      background: #FFFFFF;
      border: 2px solid transparent;
      border-radius: 16px;
      transition: all 0.2s ease;

      &.selected {
        border-color: #2D6A4F;
        background: #E8F5E9;
        transform: scale(1.02);
        box-shadow: 0 4px 16px rgba(45, 106, 79, 0.2);
      }

      &:active {
        transform: scale(0.98);
      }

      .age-icon {
        font-size: 40px;
        margin-right: 16px;
      }

      .age-info {
        flex: 1;

        .age-range {
          display: block;
          font-size: 18px;
          font-weight: 600;
          color: #1B4332;
          margin-bottom: 4px;
        }

        .age-stage {
          display: block;
          font-size: 14px;
          color: #94B49F;
        }
      }

      .age-check {
        margin-left: 12px;
      }
    }
  }
}

.bottom-wrapper {
  .confirm-btn {
    background: linear-gradient(135deg, #2D6A4F 0%, #1B4332 100%);
    border: none;
  }
}
</style>
