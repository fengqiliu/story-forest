<template>
  <view class="dubbing-page">
    <!-- 顶部导航 -->
    <view class="top-bar" :style="{ paddingTop: statusBarHeight + 'px' }">
      <view class="top-left" @click="goBack">
        <up-icon name="arrow-left" size="20" color="#1B4332"></up-icon>
      </view>
      <view class="top-center">
        <text class="page-title">角色配音</text>
      </view>
      <view class="top-right">
        <text class="save-btn" @click="saveDubbing">保存</text>
      </view>
    </view>

    <!-- 故事信息 -->
    <view class="story-info">
      <text class="story-title">为《{{ storyTitle }}》配音</text>
    </view>

    <!-- 角色选择 -->
    <view class="role-section">
      <text class="section-title">选择角色</text>
      <scroll-view scroll-x class="role-list" show-scrollbar="false">
        <view class="role-items">
          <view
            v-for="(role, index) in roles"
            :key="index"
            class="role-item"
            :class="{ active: selectedRole === index }"
            @click="selectRole(index)"
          >
            <view class="role-avatar">{{ role.avatar }}</view>
            <text class="role-name">{{ role.name }}</text>
            <view v-if="selectedRole === index" class="role-indicator"></view>
          </view>
        </view>
      </scroll-view>
    </view>

    <!-- 音效预设 -->
    <view class="effect-section">
      <text class="section-title">音效预设</text>
      <view class="effect-card">
        <view class="effect-info">
          <up-icon name="volume-up" size="20" color="#2D6A4F"></up-icon>
          <text class="effect-name">{{ currentRole.effect }}</text>
        </view>
        <view class="effect-value">
          <text class="effect-tag">{{ currentRole.effectValue }}</text>
        </view>
      </view>
    </view>

    <!-- 录音区域 -->
    <view class="record-section">
      <view class="record-display">
        <view class="avatar-large">{{ currentRole.avatar }}</view>
        <view class="record-status">
          <text v-if="isRecording" class="recording-text">🎤 录音中...</text>
          <text v-else class="duration-text">{{ formatDuration(recordDuration) }}</text>
        </view>
        <!-- 声波动画 -->
        <view v-if="isRecording" class="waveform">
          <view class="wave" v-for="n in 5" :key="n" :style="waveStyle(n)"></view>
        </view>
      </view>

      <!-- 录音控制 -->
      <view class="record-controls">
        <view class="control-btn" @click="playPreview">
          <up-icon name="play-circle-fill" size="40" color="#94B49F"></up-icon>
          <text>播放样例</text>
        </view>

        <view
          class="record-btn-wrapper"
          @touchstart="startRecording"
          @touchend="stopRecording"
        >
          <view class="record-btn" :class="{ recording: isRecording }">
            <up-icon
              :name="isRecording ? 'stop-circle-fill' : 'mic-fill'"
              size="50"
              color="#fff"
            ></up-icon>
          </view>
          <text class="record-hint">{{ isRecording ? '松开结束' : '长按录音' }}</text>
        </view>

        <view class="control-btn" @click="retake">
          <up-icon name="refresh" size="40" color="#94B49F"></up-icon>
          <text>重新录制</text>
        </view>
      </view>
    </view>

    <!-- 完成按钮 -->
    <view class="bottom-section">
      <up-button
        class="submit-btn"
        type="primary"
        size="large"
        shape="round"
        :disabled="!hasRecorded"
        @click="submitDubbing"
      >
        🎉 完成配音并保存
      </up-button>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      statusBarHeight: 0,
      storyTitle: '灰姑娘',
      selectedRole: 0,
      isRecording: false,
      recordDuration: 0,
      hasRecorded: false,
      roles: [
        {
          name: '灰姑娘',
          avatar: '🧚‍♀️',
          effect: '音调 +2 (孩童)',
          effectValue: '+2'
        },
        {
          name: '仙女教母',
          avatar: '🧙‍♀️',
          effect: '音调 -1 + 混响',
          effectValue: '-1+ 混响'
        },
        {
          name: '继母',
          avatar: '👸',
          effect: '音调 -2 (低沉)',
          effectValue: '-2'
        },
        {
          name: '王子',
          avatar: '🤴',
          effect: '音调 -1 + 回声',
          effectValue: '-1+ 回声'
        }
      ],
      recordTimer: null
    }
  },
  computed: {
    currentRole() {
      return this.roles[this.selectedRole] || this.roles[0]
    }
  },
  onLoad() {
    this.statusBarHeight = uni.getSystemInfoSync().statusBarHeight || 20
  },
  methods: {
    goBack() {
      uni.navigateBack()
    },
    selectRole(index) {
      this.selectedRole = index
    },
    startRecording() {
      this.isRecording = true
      this.recordDuration = 0
      this.recordTimer = setInterval(() => {
        if (this.recordDuration < 60) {
          this.recordDuration++
        }
      }, 1000)
      // TODO: 开始录音
      uni.vibrateShort()
    },
    stopRecording() {
      this.isRecording = false
      clearInterval(this.recordTimer)
      this.hasRecorded = true
      // TODO: 停止录音
      uni.vibrateShort()
      uni.showToast({
        title: '录音完成',
        icon: 'success'
      })
    },
    playPreview() {
      // TODO: 播放样例
      uni.showToast({
        title: '播放样例',
        icon: 'none'
      })
    },
    retake() {
      this.hasRecorded = false
      this.recordDuration = 0
      // TODO: 清除录音
    },
    saveDubbing() {
      // TODO: 保存配音
    },
    submitDubbing() {
      uni.showLoading({ title: '保存中...' })
      setTimeout(() => {
        uni.hideLoading()
        uni.showModal({
          title: '配音完成！🎉',
          content: '您的配音作品已保存，去看看大家的作品吧~',
          showCancel: false,
          success: () => {
            uni.navigateBack()
          }
        })
      }, 1000)
    },
    formatDuration(seconds) {
      const m = Math.floor(seconds / 60)
      const s = seconds % 60
      return `${m.toString().padStart(2, '0')}:${s.toString().padStart(2, '0')}`
    },
    waveStyle(n) {
      const heights = ['40%', '70%', '100%', '70%', '40%']
      return {
        height: heights[n - 1],
        animationDelay: (n - 1) * 0.1 + 's'
      }
    }
  }
}
</script>

<style lang="scss" scoped>
@import '@/styles/variables.scss';

.dubbing-page {
  min-height: 100vh;
  background-color: $bg-primary;
  padding-bottom: 100px;
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

  .save-btn {
    font-size: $font-size-sm;
    color: $primary-500;
    font-weight: $font-weight-medium;
  }
}

.story-info {
  margin-top: 60px;
  padding: $spacing-lg $page-padding;
  text-align: center;

  .story-title {
    font-size: $font-size-lg;
    font-weight: $font-weight-semibold;
    color: $text-primary;
  }
}

.role-section {
  padding: 0 $page-padding $spacing-lg;

  .section-title {
    display: block;
    font-size: $font-size-md;
    font-weight: $font-weight-semibold;
    color: $text-primary;
    margin-bottom: $spacing-md;
  }

  .role-list {
    white-space: nowrap;

    .role-items {
      display: inline-flex;
      gap: $spacing-md;
    }

    .role-item {
      display: inline-flex;
      flex-direction: column;
      align-items: center;
      gap: $spacing-xs;
      padding: $spacing-sm;
      position: relative;

      &.active .role-avatar {
        border-color: $primary-500;
        background: $primary-50;
      }

      .role-avatar {
        width: 64px;
        height: 64px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 36px;
        background: $bg-tertiary;
        border: 2px solid transparent;
        border-radius: 50%;
        transition: all 0.2s ease;
      }

      .role-name {
        font-size: $font-size-sm;
        color: $text-secondary;
      }

      .role-indicator {
        position: absolute;
        bottom: -4px;
        width: 8px;
        height: 8px;
        background: $primary-500;
        border-radius: 50%;
      }
    }
  }
}

.effect-section {
  padding: 0 $page-padding $spacing-lg;

  .section-title {
    display: block;
    font-size: $font-size-md;
    font-weight: $font-weight-semibold;
    color: $text-primary;
    margin-bottom: $spacing-md;
  }

  .effect-card {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: $spacing-md;
    background: $bg-secondary;
    border-radius: $radius-base;
    box-shadow: $shadow-sm;

    .effect-info {
      display: flex;
      align-items: center;
      gap: $spacing-sm;

      .effect-name {
        font-size: $font-size-md;
        color: $text-primary;
      }
    }

    .effect-value {
      .effect-tag {
        padding: 4px 12px;
        background: $primary-50;
        color: $primary-500;
        border-radius: $radius-full;
        font-size: $font-size-sm;
        font-weight: $font-weight-medium;
      }
    }
  }
}

.record-section {
  padding: 0 $page-padding $spacing-xl;

  .record-display {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-bottom: $spacing-xl;

    .avatar-large {
      font-size: 80px;
      margin-bottom: $spacing-lg;
    }

    .record-status {
      height: 24px;
      display: flex;
      align-items: center;
      justify-content: center;

      .recording-text,
      .duration-text {
        font-size: $font-size-md;
        color: $text-secondary;
      }
    }

    .waveform {
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 4px;
      height: 40px;
      margin-top: $spacing-md;

      .wave {
        width: 6px;
        background: $primary-500;
        border-radius: $radius-full;
        animation: waveAnimation 0.5s infinite ease-in-out;
      }
    }
  }

  .record-controls {
    display: flex;
    justify-content: space-around;
    align-items: center;

    .control-btn {
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 4px;

      text {
        font-size: $font-size-xs;
        color: $text-hint;
      }
    }

    .record-btn-wrapper {
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: $spacing-sm;

      .record-btn {
        width: 80px;
        height: 80px;
        display: flex;
        align-items: center;
        justify-content: center;
        background: $primary-500;
        border-radius: 50%;
        box-shadow: $shadow-lg;
        transition: all 0.2s ease;

        &.recording {
          background: $error;
          transform: scale(1.1);
        }

        &:active:not(.recording) {
          transform: scale(0.95);
        }
      }

      .record-hint {
        font-size: $font-size-sm;
        color: $text-hint;
      }
    }
  }
}

@keyframes waveAnimation {
  0%, 100% {
    transform: scaleY(0.5);
  }
  50% {
    transform: scaleY(1);
  }
}

.bottom-section {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  padding: $spacing-md $page-padding;
  padding-bottom: calc(#{$spacing-md} + env(safe-area-inset-bottom));
  background: $bg-secondary;
  box-shadow: 0 -2px 8px rgba(0, 0, 0, 0.05);

  .submit-btn {
    width: 100%;
  }
}
</style>