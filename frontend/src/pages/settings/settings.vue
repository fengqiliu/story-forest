<template>
  <view class="settings-page">
    <!-- 账号管理 -->
    <view class="settings-section">
      <view class="section-title">账号管理</view>

      <view class="settings-list">
        <view class="settings-item" @click="navigateTo('/pages/user-info/user-info')">
          <view class="item-left">
            <u-icon name="account" size="20" color="#52796F" />
            <text class="item-text">个人信息</text>
          </view>
          <u-icon name="arrow-right" size="14" color="#C8D5C9" />
        </view>

        <view class="settings-item" @click="navigateTo('/pages/age-select/age-select')" v-if="!userInfo.ageGroup">
          <view class="item-left">
            <u-icon name="calendar" size="20" color="#52796F" />
            <text class="item-text">设置年龄段</text>
          </view>
          <u-icon name="arrow-right" size="14" color="#C8D5C9" />
        </view>

        <view class="settings-item" @click="showSwitchAccount = true">
          <view class="item-left">
            <u-icon name="swap" size="20" color="#52796F" />
            <text class="item-text">切换账号</text>
          </view>
          <u-icon name="arrow-right" size="14" color="#C8D5C9" />
        </view>

        <view class="settings-item" @click="handleLogout">
          <view class="item-left">
            <u-icon name="logout" size="20" color="#F44336" />
            <text class="item-text text-danger">退出登录</text>
          </view>
          <u-icon name="arrow-right" size="14" color="#C8D5C9" />
        </view>
      </view>
    </view>

    <!-- 通知设置 -->
    <view class="settings-section">
      <view class="section-title">通知设置</view>

      <view class="settings-list">
        <view class="settings-item" @click="toggleNotification('remindRead')">
          <view class="item-left">
            <u-icon name="clock" size="20" color="#52796F" />
            <text class="item-text">提醒我读书</text>
          </view>
          <view class="item-right">
            <text class="item-hint" v-if="notificationSettings.remindRead">{{ remindTime }}</text>
            <u-switch
              v-model="notificationSettings.remindRead"
              :active-color="'#2D6A4F'"
              size="20"
              @change="saveNotificationSettings"
            />
          </view>
        </view>

        <view class="settings-item" v-if="notificationSettings.remindRead" @click="selectRemindTime">
          <view class="item-left">
            <u-icon name="setting" size="20" color="#52796F" />
            <text class="item-text">提醒时间</text>
          </view>
          <view class="item-right">
            <text class="item-hint">{{ remindTime }}</text>
            <u-icon name="arrow-right" size="14" color="#C8D5C9" />
          </view>
        </view>

        <view class="settings-item" @click="toggleNotification('activityNotify')">
          <view class="item-left">
            <u-icon name="notification" size="20" color="#52796F" />
            <text class="item-text">活动通知</text>
          </view>
          <u-switch
            v-model="notificationSettings.activityNotify"
            :active-color="'#2D6A4F'"
            size="20"
            @change="saveNotificationSettings"
          />
        </view>
      </view>
    </view>

    <!-- 通用设置 -->
    <view class="settings-section">
      <view class="section-title">通用设置</view>

      <view class="settings-list">
        <view class="settings-item" @click="navigateTo('/pages/clean-cache/clean-cache')">
          <view class="item-left">
            <u-icon name="trash" size="20" color="#52796F" />
            <text class="item-text">清理缓存</text>
          </view>
          <view class="item-right">
            <text class="item-hint">{{ cacheSize }}</text>
            <u-icon name="arrow-right" size="14" color="#C8D5C9" />
          </view>
        </view>

        <view class="settings-item" @click="toggleDarkMode">
          <view class="item-left">
            <u-icon name="moon" size="20" color="#52796F" />
            <text class="item-text">夜间模式</text>
          </view>
          <u-switch
            v-model="isDarkMode"
            :active-color="'#2D6A4F'"
            size="20"
          />
        </view>
      </view>
    </view>

    <!-- 关于我们 -->
    <view class="settings-section">
      <view class="section-title">关于我们</view>

      <view class="settings-list">
        <view class="settings-item" @click="navigateTo('/pages/user-agreement/user-agreement')">
          <view class="item-left">
            <u-icon name="file-text" size="20" color="#52796F" />
            <text class="item-text">用户协议</text>
          </view>
          <u-icon name="arrow-right" size="14" color="#C8D5C9" />
        </view>

        <view class="settings-item" @click="navigateTo('/pages/privacy-policy/privacy-policy')">
          <view class="item-left">
            <u-icon name="shield" size="20" color="#52796F" />
            <text class="item-text">隐私政策</text>
          </view>
          <u-icon name="arrow-right" size="14" color="#C8D5C9" />
        </view>

        <view class="settings-item" @click="showAboutApp = true">
          <view class="item-left">
            <u-icon name="info-circle" size="20" color="#52796F" />
            <text class="item-text">关于应用</text>
          </view>
          <text class="item-hint">v{{ appVersion }}</text>
        </view>
      </view>
    </view>

    <!-- 底部 -->
    <view class="settings-footer">
      <text class="footer-text">故事森林 - 让孩子爱上阅读的魔法世界</text>
    </view>

    <!-- 切换账号弹窗 -->
    <u-modal
      v-model="showSwitchAccount"
      title="切换账号"
      content="确定要切换账号吗？"
      :show-cancel-button="true"
      @confirm="confirmSwitchAccount"
    />

    <!-- 关于应用弹窗 -->
    <u-popup v-model="showAboutApp" round="20">
      <view class="about-popup">
        <view class="about-logo">
          <text class="logo-icon">🌲</text>
        </view>
        <text class="about-title">故事森林</text>
        <text class="about-version">版本：{{ appVersion }}</text>
        <text class="about-desc">让孩子爱上阅读的魔法世界</text>

        <view class="about-info">
          <view class="info-item">
            <text class="info-label">开发商</text>
            <text class="info-value">故事森林团队</text>
          </view>
          <view class="info-item">
            <text class="info-label">客服邮箱</text>
            <text class="info-value" @click="contactSupport">support@storyforest.com</text>
          </view>
        </view>

        <u-button
          type="primary"
          shape="round"
          size="large"
          @click="showAboutApp = false"
        >
          知道了
        </u-button>
      </view>
    </u-popup>
  </view>
</template>

<script>
export default {
  data() {
    return {
      userInfo: {},
      appVersion: '1.0.0',
      cacheSize: '12.5 MB',
      isDarkMode: false,
      showSwitchAccount: false,
      showAboutApp: false,
      notificationSettings: {
        remindRead: false,
        activityNotify: true
      },
      remindTime: '20:00'
    }
  },
  onLoad() {
    this.loadUserInfo()
    this.loadSettings()
    this.calculateCacheSize()
  },
  methods: {
    loadUserInfo() {
      const userInfo = uni.getStorageSync('userInfo')
      this.userInfo = userInfo || {}
    },

    loadSettings() {
      const settings = uni.getStorageSync('notificationSettings')
      if (settings) {
        this.notificationSettings = settings
      }

      const darkMode = uni.getStorageSync('darkMode')
      if (darkMode) {
        this.isDarkMode = darkMode
      }

      const remindTime = uni.getStorageSync('remindTime')
      if (remindTime) {
        this.remindTime = remindTime
      }
    },

    calculateCacheSize() {
      // 计算缓存大小
      const info = uni.getStorageInfoSync()
      const size = info.currentSize || 0
      if (size > 1024) {
        this.cacheSize = (size / 1024).toFixed(1) + ' MB'
      } else {
        this.cacheSize = size + ' KB'
      }
    },

    navigateTo(url) {
      uni.navigateTo({ url })
    },

    handleLogout() {
      uModal.confirm({
        title: '退出登录',
        content: '确定要退出登录吗？',
        confirmColor: '#F44336',
        success: () => {
          // 清除本地数据
          uni.removeStorageSync('token')
          uni.removeStorageSync('userInfo')
          uni.removeStorageSync('userPoints')

          // 跳转到登录页
          uni.reLaunch({
            url: '/pages/login/login'
          })

          uToast.success({ title: '已退出登录' })
        }
      })
    },

    toggleNotification(type) {
      if (type === 'remindRead') {
        this.notificationSettings.remindRead = !this.notificationSettings.remindRead
      } else if (type === 'activityNotify') {
        this.notificationSettings.activityNotify = !this.notificationSettings.activityNotify
      }
      this.saveNotificationSettings()
    },

    saveNotificationSettings() {
      uni.setStorageSync('notificationSettings', this.notificationSettings)

      if (this.notificationSettings.remindRead) {
        // TODO: 设置提醒
        uToast.success({ title: '提醒已设置' })
      }
    },

    selectRemindTime() {
      // 使用时间选择器
      uni.showActionSheet({
        itemList: ['18:00', '19:00', '20:00', '21:00'],
        success: (res) => {
          const times = ['18:00', '19:00', '20:00', '21:00']
          this.remindTime = times[res.tapIndex]
          uni.setStorageSync('remindTime', this.remindTime)
        }
      })
    },

    toggleDarkMode() {
      this.isDarkMode = !this.isDarkMode
      uni.setStorageSync('darkMode', this.isDarkMode)

      // TODO: 实际应用中需要动态切换主题
      uToast.success({ title: this.isDarkMode ? '已开启夜间模式' : '已关闭夜间模式' })
    },

    confirmSwitchAccount() {
      this.showSwitchAccount = false
      // 清除登录状态
      uni.removeStorageSync('token')
      uni.removeStorageSync('userInfo')

      // 跳转到登录页
      uni.reLaunch({
        url: '/pages/login/login'
      })
    },

    contactSupport() {
      // 复制邮箱到剪贴板
      // #ifdef MP-WEIXIN
      uni.setClipboardData({
        data: 'support@storyforest.com',
        success: () => {
          uToast.success({ title: '已复制客服邮箱' })
        }
      })
      // #endif
    }
  }
}
</script>

<style lang="scss" scoped>
.settings-page {
  min-height: 100vh;
  background: #F5F5F5;
  padding-bottom: 40px;
}

.settings-section {
  margin-top: 16px;

  .section-title {
    padding: 12px 16px 8px;
    font-size: 14px;
    color: #94B49F;
  }
}

.settings-list {
  background: #FFFFFF;
  border-radius: 12px;
  overflow: hidden;

  .settings-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 16px;
    border-bottom: 1px solid #F0F0F0;

    &:last-child {
      border-bottom: none;
    }

    &:active {
      background: #F5F5F5;
    }

    .item-left {
      display: flex;
      align-items: center;

      .item-text {
        margin-left: 12px;
        font-size: 15px;
        color: #1B4332;

        &.text-danger {
          color: #F44336;
        }
      }
    }

    .item-right {
      display: flex;
      align-items: center;

      .item-hint {
        margin-right: 8px;
        font-size: 13px;
        color: #94B49F;
      }
    }
  }
}

.settings-footer {
  padding: 40px 16px;
  text-align: center;

  .footer-text {
    font-size: 12px;
    color: #C8D5C9;
  }
}

.about-popup {
  width: 300px;
  padding: 32px 24px;
  text-align: center;

  .about-logo {
    margin-bottom: 16px;

    .logo-icon {
      font-size: 64px;
    }
  }

  .about-title {
    display: block;
    font-size: 24px;
    font-weight: 600;
    color: #1B4332;
    margin-bottom: 8px;
  }

  .about-version {
    display: block;
    font-size: 14px;
    color: #94B49F;
    margin-bottom: 12px;
  }

  .about-desc {
    display: block;
    font-size: 13px;
    color: #52796F;
    margin-bottom: 24px;
  }

  .about-info {
    background: #F5F5F5;
    border-radius: 12px;
    padding: 16px;
    margin-bottom: 24px;
    text-align: left;

    .info-item {
      display: flex;
      justify-content: space-between;
      padding: 8px 0;

      &:not(:last-child) {
        border-bottom: 1px solid #E0E0E0;
      }

      .info-label {
        font-size: 13px;
        color: #94B49F;
      }

      .info-value {
        font-size: 13px;
        color: #2D6A4F;

        &:active {
          opacity: 0.7;
        }
      }
    }
  }
}
</style>
