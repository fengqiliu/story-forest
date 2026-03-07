<template>
  <view class="reading-page">
    <!-- 顶部导航栏 -->
    <view class="top-bar" :style="{ paddingTop: statusBarHeight + 'px' }">
      <view class="top-left" @click="goBack">
        <up-icon name="arrow-left" size="20" color="#1B4332"></up-icon>
      </view>
      <view class="top-center">
        <text class="chapter-info">{{ currentChapter }}/{{ totalChapters }}</text>
      </view>
      <view class="top-right">
        <up-icon
          name="heart"
          :color="isFavorite ? '#F44336' : '#1B4332'"
          size="20"
          @click="toggleFavorite"
        ></up-icon>
        <up-icon name="more-dot-fill" size="20" color="#1B4332" @click="showMenu"></up-icon>
      </view>
    </view>

    <!-- 插画/Lottie 动画区域 -->
    <view class="illustration-area">
      <view class="lottie-container">
        <!-- 这里使用图片模拟 Lottie 动画效果 -->
        <image
          class="illustration-image"
          :src="currentIllustration"
          mode="aspectFit"
        ></image>
        <!-- 装饰性动画元素 -->
        <view class="sparkle sparkle-1">✨</view>
        <view class="sparkle sparkle-2">✨</view>
        <view class="sparkle sparkle-3">✨</view>
      </view>
    </view>

    <!-- 文字内容区域 -->
    <scroll-view scroll-y class="text-content">
      <view class="text-wrapper">
        <text class="story-text">{{ storyText }}</text>
        <view class="interactive-words">
          <text class="word-hint">点击词语查看解释</text>
        </view>
      </view>
    </scroll-view>

    <!-- 背景音效提示 -->
    <view class="bg-sound-tip">
      <up-icon name="volume-up" size="14" color="#94B49F"></up-icon>
      <text>背景音效：厨房环境音</text>
    </view>

    <!-- 底部播放控制 -->
    <view class="player-controls">
      <view class="progress-bar">
        <text class="time-current">{{ formatTime(currentTime) }}</text>
        <slider
          class="slider"
          :value="progress"
          :max="100"
          active-color="#2D6A4F"
          block-size="16"
          @change="onProgressChange"
        ></slider>
        <text class="time-total">{{ formatTime(duration) }}</text>
      </view>

      <view class="control-buttons">
        <up-icon
          name="skip-backward"
          size="24"
          color="#52796F"
          @click="playPrevious"
        ></up-icon>

        <up-icon
          :name="isPlaying ? 'pause-circle-fill' : 'play-circle-fill'"
          size="56"
          color="#2D6A4F"
          @click="togglePlay"
        ></up-icon>

        <up-icon
          name="skip-forward"
          size="24"
          color="#52796F"
          @click="playNext"
        ></up-icon>
      </view>

      <view class="action-buttons">
        <view class="action-btn" @click="goToDubbing">
          <up-icon name="mic" size="20" color="#2D6A4F"></up-icon>
          <text>配音</text>
        </view>
        <view class="action-btn" @click="goToQuiz">
          <up-icon name="star" size="20" color="#2D6A4F"></up-icon>
          <text>答题</text>
        </view>
        <view class="action-btn" @click="toggleTimer">
          <up-icon name="clock" size="20" color="#2D6A4F"></up-icon>
          <text>{{ timerMinutes ? timerMinutes + '分' : '定时' }}</text>
        </view>
      </view>
    </view>

    <!-- 定时关闭弹窗 -->
    <up-popup v-model="showTimerPopup" round="20" mode="bottom">
      <view class="timer-popup">
        <view class="popup-header">
          <text class="popup-title">定时关闭</text>
          <up-icon name="close" size="20" @click="showTimerPopup = false"></up-icon>
        </view>
        <view class="timer-options">
          <view
            v-for="item in timerOptions"
            :key="item.value"
            class="timer-option"
            :class="{ active: timerMinutes === item.value }"
            @click="setTimer(item.value)"
          >
            <text class="timer-text">{{ item.label }}</text>
          </view>
        </view>
      </view>
    </up-popup>
  </view>
</template>

<script>
export default {
  data() {
    return {
      statusBarHeight: 0,
      storyId: null,
      chapterId: null,
      currentChapter: 3,
      totalChapters: 12,
      isFavorite: false,
      isPlaying: false,
      currentTime: 45,
      duration: 150,
      progress: 30,
      currentIllustration: 'https://picsum.photos/400/300?random=30',
      storyText: '从前，有一个美丽善良的女孩，她的名字叫辛德瑞拉。她每天都要在厨房里干活，擦地、做饭、洗衣服。虽然继母和姐姐们对她很不好，但她始终保持着一颗善良的心...',
      timerMinutes: 0,
      showTimerPopup: false,
      timerOptions: [
        { label: '关闭', value: 0 },
        { label: '10 分钟', value: 10 },
        { label: '15 分钟', value: 15 },
        { label: '30 分钟', value: 30 },
        { label: '60 分钟', value: 60 }
      ]
    }
  },
  onLoad(options) {
    this.statusBarHeight = uni.getSystemInfoSync().statusBarHeight || 20
    if (options.storyId) {
      this.storyId = options.storyId
    }
    if (options.chapterId) {
      this.chapterId = options.chapterId
    }
  },
  methods: {
    goBack() {
      uni.navigateBack()
    },
    toggleFavorite() {
      this.isFavorite = !this.isFavorite
      uni.showToast({
        title: this.isFavorite ? '已收藏' : '已取消',
        icon: 'none'
      })
    },
    showMenu() {
      uni.showActionSheet({
        itemList: ['分享故事', '举报', '设置'],
        success: (res) => {
          console.log('Selected:', res.tapIndex)
        }
      })
    },
    togglePlay() {
      this.isPlaying = !this.isPlaying
      // TODO: 控制音频播放
    },
    playPrevious() {
      if (this.currentChapter > 1) {
        this.currentChapter--
        this.loadChapter()
      }
    },
    playNext() {
      if (this.currentChapter < this.totalChapters) {
        this.currentChapter++
        this.loadChapter()
      }
    },
    onProgressChange(e) {
      this.progress = e.detail.value
      // TODO: 跳转播放进度
    },
    goToDubbing() {
      uni.navigateTo({
        url: `/pages/dubbing/dubbing?storyId=${this.storyId}&chapterId=${this.chapterId}`
      })
    },
    goToQuiz() {
      uni.navigateTo({
        url: `/pages/quiz/quiz?storyId=${this.storyId}&chapterId=${this.chapterId}`
      })
    },
    toggleTimer() {
      this.showTimerPopup = true
    },
    setTimer(value) {
      this.timerMinutes = value
      this.showTimerPopup = false
      if (value > 0) {
        uni.showToast({
          title: `${value}分钟后关闭`,
          icon: 'none'
        })
        // TODO: 设置定时器
      }
    },
    formatTime(seconds) {
      const m = Math.floor(seconds / 60)
      const s = seconds % 60
      return `${m}:${s.toString().padStart(2, '0')}`
    },
    loadChapter() {
      // TODO: 加载章节内容
      console.log('Load chapter:', this.currentChapter)
    }
  }
}
</script>

<style lang="scss" scoped>
@import '@/styles/variables.scss';

.reading-page {
  min-height: 100vh;
  background-color: $bg-primary;
  display: flex;
  flex-direction: column;
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
  background: $bg-primary;
  z-index: 100;

  .top-left,
  .top-right {
    display: flex;
    align-items: center;
    gap: $spacing-sm;
    padding: $spacing-sm;
  }

  .top-center {
    .chapter-info {
      font-size: $font-size-sm;
      color: $text-secondary;
      font-weight: $font-weight-medium;
    }
  }
}

.illustration-area {
  margin-top: 50px;
  padding: $spacing-lg;
  flex-shrink: 0;

  .lottie-container {
    position: relative;
    width: 100%;
    height: 250px;
    background: linear-gradient(135deg, $primary-50, $accent-50);
    border-radius: $radius-lg;
    overflow: hidden;
    display: flex;
    align-items: center;
    justify-content: center;

    .illustration-image {
      width: 100%;
      height: 100%;
      object-fit: contain;
    }

    .sparkle {
      position: absolute;
      font-size: 16px;
      animation: twinkle 1.5s infinite ease-in-out;
    }

    .sparkle-1 {
      top: 20%;
      left: 20%;
      animation-delay: 0s;
    }

    .sparkle-2 {
      top: 30%;
      right: 25%;
      animation-delay: 0.5s;
    }

    .sparkle-3 {
      bottom: 25%;
      left: 30%;
      animation-delay: 1s;
    }
  }
}

@keyframes twinkle {
  0%, 100% {
    opacity: 0.3;
    transform: scale(1);
  }
  50% {
    opacity: 1;
    transform: scale(1.2);
  }
}

.text-content {
  flex: 1;
  padding: 0 $spacing-lg;
  margin-bottom: 200px;

  .text-wrapper {
    .story-text {
      font-size: $font-size-lg;
      line-height: 1.8;
      color: $text-primary;
      display: block;
    }

    .interactive-words {
      margin-top: $spacing-md;

      .word-hint {
        font-size: $font-size-sm;
        color: $text-hint;
      }
    }
  }
}

.bg-sound-tip {
  position: fixed;
  top: 50%;
  left: $spacing-md;
  transform: translateY(-50%);
  display: flex;
  align-items: center;
  gap: 4px;
  padding: $spacing-sm $spacing-md;
  background: rgba(255, 255, 255, 0.9);
  border-radius: $radius-full;
  box-shadow: $shadow-sm;

  text {
    font-size: $font-size-xs;
    color: $text-hint;
  }
}

.player-controls {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background: $bg-secondary;
  padding: $spacing-md $spacing-lg;
  padding-bottom: calc(#{$spacing-md} + env(safe-area-inset-bottom));
  box-shadow: 0 -2px 8px rgba(0, 0, 0, 0.05);

  .progress-bar {
    display: flex;
    align-items: center;
    gap: $spacing-sm;
    margin-bottom: $spacing-md;

    .time-current,
    .time-total {
      font-size: $font-size-xs;
      color: $text-hint;
      min-width: 40px;
    }

    .time-total {
      text-align: right;
    }

    .slider {
      flex: 1;
    }
  }

  .control-buttons {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: $spacing-lg;
    margin-bottom: $spacing-md;
  }

  .action-buttons {
    display: flex;
    justify-content: space-around;

    .action-btn {
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 2px;

      text {
        font-size: $font-size-xs;
        color: $text-secondary;
      }
    }
  }
}

.timer-popup {
  padding: $spacing-lg;

  .popup-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: $spacing-lg;

    .popup-title {
      font-size: $font-size-lg;
      font-weight: $font-weight-semibold;
      color: $text-primary;
    }
  }

  .timer-options {
    display: flex;
    gap: $spacing-sm;

    .timer-option {
      flex: 1;
      padding: $spacing-md;
      text-align: center;
      background: $bg-tertiary;
      border-radius: $radius-base;
      border: 2px solid transparent;

      &.active {
        border-color: $primary-500;
        background: $primary-50;
      }

      .timer-text {
        font-size: $font-size-sm;
        color: $text-secondary;
      }
    }
  }
}
</style>