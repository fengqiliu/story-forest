<template>
  <view class="parent-page">
    <!-- 顶部导航 -->
    <view class="top-bar" :style="{ paddingTop: statusBarHeight + 'px' }">
      <view class="top-left" @click="goBack">
        <up-icon name="arrow-left" size="20" color="#1B4332"></up-icon>
      </view>
      <view class="top-center">
        <text class="page-title">家长中心</text>
      </view>
      <view class="top-right"></view>
    </view>

    <!-- 家长验证弹窗 -->
    <up-popup v-model="showVerify" round="20" mode="center">
      <view class="verify-dialog">
        <view class="verify-header">
          <up-icon name="lock-closed" size="40" color="#2D6A4F"></up-icon>
          <text class="verify-title">家长验证</text>
        </view>

        <view class="verify-question">
          <text class="question-text">{{ verifyQuestion }}</text>
        </view>

        <input
          class="answer-input"
          type="number"
          v-model="verifyAnswer"
          placeholder="请输入答案"
          :focus="true"
          @confirm="submitVerify"
        />

        <view class="verify-actions">
          <up-button class="verify-btn" text @click="cancelVerify">取消</up-button>
          <up-button class="verify-btn" type="primary" @click="submitVerify">验证</up-button>
        </view>
      </view>
    </up-popup>

    <!-- 内容区域 -->
    <scroll-view scroll-y class="content-scroll" :style="{ top: contentTop + 'px' }">
      <!-- 时长管理 -->
      <view class="section">
        <text class="section-title">时长管理</text>
        <view class="setting-list">
          <view class="setting-item" @click="editSetting('dailyLimit')">
            <view class="setting-info">
              <text class="setting-label">每日阅读时长</text>
              <text class="setting-value">{{ settings.dailyLimit }}分钟</text>
            </view>
            <up-icon name="arrow-right" size="16" color="#94B49F"></up-icon>
          </view>
          <view class="setting-item" @click="editSetting('singleLimit')">
            <view class="setting-info">
              <text class="setting-label">单次阅读时长</text>
              <text class="setting-value">{{ settings.singleLimit }}分钟</text>
            </view>
            <up-icon name="arrow-right" size="16" color="#94B49F"></up-icon>
          </view>
          <view class="setting-item" @click="editSetting('forbidTime')">
            <view class="setting-info">
              <text class="setting-label">禁用时段</text>
              <text class="setting-value">{{ settings.forbidStart }}:00 - {{ settings.forbidEnd }}:00</text>
            </view>
            <up-icon name="arrow-right" size="16" color="#94B49F"></up-icon>
          </view>
        </view>
      </view>

      <!-- 内容管理 -->
      <view class="section">
        <text class="section-title">内容管理</text>
        <view class="setting-list">
          <view class="setting-item" @click="goToBlacklist">
            <view class="setting-info">
              <text class="setting-label">黑名单故事</text>
              <text class="setting-value">{{ settings.blacklistCount }}个</text>
            </view>
            <up-icon name="arrow-right" size="16" color="#94B49F"></up-icon>
          </view>
          <view class="setting-item" @click="goToSensitiveWords">
            <view class="setting-info">
              <text class="setting-label">敏感词设置</text>
              <text class="setting-value">{{ settings.sensitiveWordCount }}个</text>
            </view>
            <up-icon name="arrow-right" size="16" color="#94B49F"></up-icon>
          </view>
        </view>
      </view>

      <!-- 成长报告 -->
      <view class="section">
        <text class="section-title">成长报告</text>
        <view class="report-card" @click="viewReport">
          <view class="report-header">
            <text class="report-title">📊 本周报告</text>
            <up-icon name="arrow-right" size="16" color="#94B49F"></up-icon>
          </view>
          <view class="report-content">
            <view class="report-stat">
              <text class="report-value">{{ report.readDays }}</text>
              <text class="report-label">阅读天数</text>
            </view>
            <view class="report-stat">
              <text class="report-value">{{ report.totalMinutes }}</text>
              <text class="report-label">总时长 (分钟)</text>
            </view>
            <view class="report-stat">
              <text class="report-value">{{ report.storyCount }}</text>
              <text class="report-label">完成故事</text>
            </view>
          </view>
          <view class="report-preference">
            <text class="preference-label">最爱主题：</text>
            <text class="preference-value">{{ report.favoriteTheme }}</text>
          </view>
        </view>
      </view>

      <!-- 账户管理 -->
      <view class="section">
        <text class="section-title">账户管理</text>
        <view class="setting-list">
          <view class="setting-item" @click="goToVIP">
            <view class="setting-info">
              <text class="setting-label">💎 VIP 会员</text>
              <text class="setting-value setting-tag">续费</text>
            </view>
            <up-icon name="arrow-right" size="16" color="#94B49F"></up-icon>
          </view>
          <view class="setting-item" @click="switchChild">
            <view class="setting-info">
              <text class="setting-label">👨‍👩‍👧 切换孩子账号</text>
              <text class="setting-value">乐乐</text>
            </view>
            <up-icon name="arrow-right" size="16" color="#94B49F"></up-icon>
          </view>
        </view>
      </view>

      <!-- 底部占位 -->
      <view style="height: 40px;"></view>
    </scroll-view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      statusBarHeight: 0,
      contentTop: 0,
      showVerify: false,
      verifyVerified: false,
      verifyQuestion: '',
      verifyAnswer: '',
      correctAnswer: 0,
      verifyCallback: null,
      settings: {
        dailyLimit: 60,
        singleLimit: 30,
        forbidStart: 22,
        forbidEnd: 7,
        blacklistCount: 3,
        sensitiveWordCount: 12
      },
      report: {
        readDays: 5,
        totalMinutes: 180,
        storyCount: 12,
        favoriteTheme: '童话故事 (60%)'
      }
    }
  },
  onLoad() {
    this.statusBarHeight = uni.getSystemInfoSync().statusBarHeight || 20
    this.contentTop = this.statusBarHeight + 44
  },
  methods: {
    goBack() {
      uni.navigateBack()
    },
    async editSetting(type) {
      const verified = await this.requireVerify()
      if (!verified) return

      switch (type) {
        case 'dailyLimit':
          this.showTimePicker('每日', this.settings.dailyLimit, (value) => {
            this.settings.dailyLimit = value
          })
          break
        case 'singleLimit':
          this.showTimePicker('单次', this.settings.singleLimit, (value) => {
            this.settings.singleLimit = value
          })
          break
        case 'forbidTime':
          this.showTimeRangePicker()
          break
      }
    },
    requireVerify() {
      if (this.verifyVerified) {
        return Promise.resolve(true)
      }
      this.showVerifyPrompt()
      return new Promise(resolve => {
        this.verifyCallback = resolve
      })
    },
    showVerifyPrompt() {
      const num1 = Math.floor(Math.random() * 20) + 1
      const num2 = Math.floor(Math.random() * 20) + 1
      this.verifyQuestion = `${num1} + ${num2} = ?`
      this.correctAnswer = num1 + num2
      this.verifyAnswer = ''
      this.showVerify = true
    },
    submitVerify() {
      if (parseInt(this.verifyAnswer) === this.correctAnswer) {
        this.verifyVerified = true
        this.showVerify = false
        if (this.verifyCallback) {
          this.verifyCallback(true)
        }
        uni.showToast({
          title: '验证通过',
          icon: 'success'
        })
      } else {
        uni.showToast({
          title: '答案错误',
          icon: 'none'
        })
        this.verifyAnswer = ''
      }
    },
    cancelVerify() {
      this.showVerify = false
      if (this.verifyCallback) {
        this.verifyCallback(false)
      }
    },
    showTimePicker(label, currentValue, callback) {
      uni.showPickerView({
        ranges: [{
          values: [label, currentValue + '分钟']
        }],
        success: (res) => {
          // TODO: 显示时间选择器
        }
      })
    },
    showTimeRangePicker() {
      uni.showModal({
        title: '禁用时段设置',
        content: `当前设置：${this.settings.forbidStart}:00 - ${this.settings.forbidEnd}:00`,
        confirmText: '修改',
        success: (res) => {
          if (res.confirm) {
            // TODO: 显示时间范围选择器
          }
        }
      })
    },
    goToBlacklist() {
      uni.navigateTo({
        url: '/pages/blacklist/blacklist'
      })
    },
    goToSensitiveWords() {
      uni.navigateTo({
        url: '/pages/sensitive-words/sensitive-words'
      })
    },
    viewReport() {
      uni.navigateTo({
        url: '/pages/report/report'
      })
    },
    goToVIP() {
      uni.navigateTo({
        url: '/pages/vip/vip'
      })
    },
    switchChild() {
      uni.showModal({
        title: '切换孩子账号',
        content: '当前账号：乐乐',
        showCancel: false
      })
    }
  }
}
</script>

<style lang="scss" scoped>
@import '@/styles/variables.scss';

.parent-page {
  min-height: 100vh;
  background-color: $bg-primary;
}

.top-bar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 $spacing-md;
  background: $bg-secondary;
  box-shadow: $shadow-sm;
  z-index: 100;

  .top-left,
  .top-right {
    width: 50px;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: $spacing-sm;
  }

  .top-center {
    flex: 1;
    text-align: center;

    .page-title {
      font-size: $font-size-md;
      font-weight: $font-weight-semibold;
      color: $text-primary;
    }
  }
}

.verify-dialog {
  width: 300px;
  padding: $spacing-xl;

  .verify-header {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-bottom: $spacing-lg;

    .verify-title {
      margin-top: $spacing-sm;
      font-size: $font-size-lg;
      font-weight: $font-weight-semibold;
      color: $text-primary;
    }
  }

  .verify-question {
    margin-bottom: $spacing-lg;
    text-align: center;

    .question-text {
      font-size: $font-size-xxl;
      font-weight: $font-weight-bold;
      color: $text-primary;
    }
  }

  .answer-input {
    width: 100%;
    height: 48px;
    padding: 0 $spacing-md;
    background: $bg-tertiary;
    border-radius: $radius-base;
    font-size: $font-size-lg;
    text-align: center;
    margin-bottom: $spacing-lg;
  }

  .verify-actions {
    display: flex;
    gap: $spacing-md;

    .verify-btn {
      flex: 1;
    }
  }
}

.content-scroll {
  position: absolute;
  top: 44px;
  left: 0;
  right: 0;
  bottom: 0;
}

.section {
  padding: $spacing-lg $page-padding $spacing-md;

  .section-title {
    display: block;
    font-size: $font-size-md;
    font-weight: $font-weight-semibold;
    color: $text-primary;
    margin-bottom: $spacing-md;
  }
}

.setting-list {
  background: $bg-secondary;
  border-radius: $radius-lg;
  overflow: hidden;

  .setting-item {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: $spacing-md $spacing-lg;
    border-bottom: 1px solid $bg-tertiary;

    &:last-child {
      border-bottom: none;
    }

    &:active {
      background: $bg-tertiary;
    }

    .setting-info {
      display: flex;
      flex-direction: column;
      gap: 4px;

      .setting-label {
        font-size: $font-size-md;
        color: $text-primary;
      }

      .setting-value {
        font-size: $font-size-sm;
        color: $text-hint;

        &.setting-tag {
          padding: 2px 8px;
          background: linear-gradient(135deg, #FFD700, #FFA500);
          color: #fff;
          border-radius: $radius-sm;
          display: inline-block;
        }
      }
    }
  }
}

.report-card {
  padding: $spacing-lg;
  background: $bg-secondary;
  border-radius: $radius-lg;
  box-shadow: $shadow-md;

  .report-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: $spacing-lg;

    .report-title {
      font-size: $font-size-md;
      font-weight: $font-weight-semibold;
      color: $text-primary;
    }
  }

  .report-content {
    display: flex;
    justify-content: space-around;
    margin-bottom: $spacing-md;

    .report-stat {
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 4px;

      .report-value {
        font-size: $font-size-xl;
        font-weight: $font-weight-bold;
        color: $primary-500;
      }

      .report-label {
        font-size: $font-size-xs;
        color: $text-hint;
      }
    }
  }

  .report-preference {
    padding-top: $spacing-md;
    border-top: 1px solid $bg-tertiary;

    .preference-label {
      font-size: $font-size-sm;
      color: $text-hint;
    }

    .preference-value {
      font-size: $font-size-sm;
      color: $text-primary;
      font-weight: $font-weight-medium;
    }
  }
}
</style>