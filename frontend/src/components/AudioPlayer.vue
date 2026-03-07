<template>
  <view class="audio-player" :class="{ 'player-fixed': fixed }">
    <!-- 播放进度条 -->
    <view class="player-progress" @click="onProgressClick">
      <slider
        :value="progress"
        :max="100"
        active-color="#2D6A4F"
        background-color="#E0E0E0"
        block-size="16"
        block-color="#FFFFFF"
        :show-value="false"
        @change="onProgressChange"
      />
      <view class="time-info">
        <text class="time-current">{{ currentTimeText }}</text>
        <text class="time-total">{{ durationText }}</text>
      </view>
    </view>

    <!-- 播放控制区 -->
    <view class="player-controls">
      <!-- 上一首 -->
      <view class="control-btn" @click="playPrevious">
        <u-icon name="skip-backward" size="24" color="#52796F" />
      </view>

      <!-- 播放/暂停按钮 -->
      <view class="control-btn play-btn" @click="togglePlay">
        <u-icon
          :name="isPlaying ? 'pause-circle-fill' : 'play-circle-fill'"
          size="56"
          color="#2D6A4F"
        />
      </view>

      <!-- 下一首 -->
      <view class="control-btn" @click="playNext">
        <u-icon name="skip-forward" size="24" color="#52796F" />
      </view>
    </view>

    <!-- 功能按钮区 -->
    <view class="player-actions">
      <view class="action-btn" @click="showSpeedPicker = true">
        <text class="action-icon">⚡</text>
        <text class="action-text">{{ playSpeed }}x</text>
      </view>

      <view class="action-btn" @click="showTimerPicker = true">
        <text class="action-icon">⏰</text>
        <text class="action-text">{{ timerText || '定时' }}</text>
      </view>

      <view class="action-btn" @click="handleDubbing">
        <text class="action-icon">🎤</text>
        <text class="action-text">配音</text>
      </view>

      <view class="action-btn" @click="handleQuiz">
        <text class="action-icon">🎯</text>
        <text class="action-text">答题</text>
      </view>
    </view>

    <!-- 倍速选择器 -->
    <u-popup v-model="showSpeedPicker" round="16">
      <view class="picker-popup">
        <view class="picker-header">
          <text class="picker-title">播放速度</text>
          <u-icon name="close" size="24" @click="showSpeedPicker = false" />
        </view>
        <view class="speed-options">
          <view
            v-for="speed in speedOptions"
            :key="speed"
            class="speed-option"
            :class="{ active: playSpeed === speed }"
            @click="selectSpeed(speed)"
          >
            {{ speed }}x
          </view>
        </view>
      </view>
    </u-popup>

    <!-- 定时关闭选择器 -->
    <u-popup v-model="showTimerPicker" round="16">
      <view class="picker-popup">
        <view class="picker-header">
          <text class="picker-title">定时关闭</text>
          <u-icon name="close" size="24" @click="showTimerPicker = false" />
        </view>
        <view class="timer-options">
          <view
            v-for="item in timerOptions"
            :key="item.value"
            class="timer-option"
            :class="{ active: timerMinutes === item.value }"
            @click="selectTimer(item.value)"
          >
            {{ item.label }}
          </view>
        </view>
      </view>
    </u-popup>
  </view>
</template>

<script>
import { useAudioStore } from '@/store/audio'
import { mapState, mapActions } from 'pinia'

export default {
  props: {
    fixed: {
      type: Boolean,
      default: true
    },
    audioUrl: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      showSpeedPicker: false,
      showTimerPicker: false,
      speedOptions: [0.5, 0.75, 1.0, 1.25, 1.5, 2.0],
      timerOptions: [
        { value: 0, label: '关闭' },
        { value: 15, label: '15 分钟后' },
        { value: 30, label: '30 分钟后' },
        { value: 45, label: '45 分钟后' },
        { value: 60, label: '60 分钟后' }
      ]
    }
  },
  computed: {
    ...mapState(useAudioStore, [
      'isPlaying',
      'currentTime',
      'duration',
      'progress',
      'playSpeed',
      'timerMinutes',
      'timerRemaining'
    ]),

    // 当前时间文本
    currentTimeText() {
      return this.formatTime(this.currentTime)
    },

    // 总时长文本
    durationText() {
      return this.formatTime(this.duration)
    },

    // 定时文本
    timerText() {
      if (this.timerRemaining > 0) {
        const minutes = Math.floor(this.timerRemaining / 60)
        const seconds = this.timerRemaining % 60
        if (minutes > 0) {
          return `${minutes}分${seconds}秒`
        }
        return `${seconds}秒`
      }
      return ''
    }
  },
  methods: {
    ...mapActions(useAudioStore, [
      'togglePlay',
      'seek',
      'setPlaySpeed',
      'setTimer',
      'clearTimer',
      'playPrevious',
      'playNext',
      'formatTime'
    ]),

    // 进度条点击
    onProgressClick(e) {
      // 获取点击位置计算进度
      const rect = e.detail.x
      // uni-app slider 点击需要手动处理
    },

    // 进度条改变
    onProgressChange(e) {
      const percent = e.detail.value
      const position = (percent / 100) * this.duration
      this.seek(position)
    },

    // 选择倍速
    selectSpeed(speed) {
      this.setPlaySpeed(speed)
      this.showSpeedPicker = false
      uToast.success({ title: `已切换到 ${speed}x 倍速` })
    },

    // 选择定时
    selectTimer(minutes) {
      if (minutes === 0) {
        this.clearTimer()
        uToast.success({ title: '定时关闭已取消' })
      } else {
        this.setTimer(minutes)
        uToast.success({ title: `将在${minutes}分钟后关闭` })
      }
      this.showTimerPicker = false
    },

    // 配音
    handleDubbing() {
      this.$emit('dubbing')
    },

    // 答题
    handleQuiz() {
      this.$emit('quiz')
    }
  },
  beforeDestroy() {
    // 组件销毁时不清除音频播放器，避免页面切换时中断播放
  }
}
</script>

<style lang="scss" scoped>
.audio-player {
  background: #FFFFFF;
  padding: 16px;
  border-radius: 16px;

  &.player-fixed {
    position: fixed;
    left: 0;
    right: 0;
    bottom: 0;
    border-radius: 16px 16px 0 0;
    box-shadow: 0 -4px 16px rgba(0, 0, 0, 0.1);
    z-index: 100;
  }
}

.player-progress {
  margin-bottom: 16px;

  .time-info {
    display: flex;
    justify-content: space-between;
    margin-top: 8px;

    .time-current, .time-total {
      font-size: 12px;
      color: #94B49F;
    }
  }
}

.player-controls {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 32px;
  margin-bottom: 16px;

  .control-btn {
    display: flex;
    align-items: center;
    justify-content: center;
    transition: transform 0.2s;

    &:active {
      transform: scale(0.9);
    }

    &.play-btn {
      .u-icon {
        filter: drop-shadow(0 2px 4px rgba(45, 106, 79, 0.3));
      }
    }
  }
}

.player-actions {
  display: flex;
  justify-content: space-around;

  .action-btn {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 4px;

    &:active {
      opacity: 0.7;
    }

    .action-icon {
      font-size: 24px;
    }

    .action-text {
      font-size: 12px;
      color: #52796F;
    }
  }
}

.picker-popup {
  padding: 20px;
  min-width: 280px;

  .picker-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;

    .picker-title {
      font-size: 18px;
      font-weight: 600;
      color: #1B4332;
    }
  }
}

.speed-options {
  display: flex;
  justify-content: space-between;

  .speed-option {
    width: 50px;
    height: 50px;
    display: flex;
    align-items: center;
    justify-content: center;
    background: #F5F5F5;
    border-radius: 12px;
    font-size: 16px;
    color: #52796F;
    transition: all 0.2s;

    &.active {
      background: #2D6A4F;
      color: #FFFFFF;
    }

    &:active {
      transform: scale(0.95);
    }
  }
}

.timer-options {
  display: flex;
  flex-direction: column;
  gap: 12px;

  .timer-option {
    padding: 16px;
    background: #F5F5F5;
    border-radius: 12px;
    text-align: center;
    font-size: 16px;
    color: #52796F;
    transition: all 0.2s;

    &.active {
      background: #2D6A4F;
      color: #FFFFFF;
    }

    &:active {
      transform: scale(0.98);
    }
  }
}
</style>
