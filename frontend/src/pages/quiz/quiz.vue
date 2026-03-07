<template>
  <view class="quiz-page">
    <!-- 顶部进度栏 -->
    <view class="quiz-header" :style="{ paddingTop: statusBarHeight + 'px' }">
      <view class="header-left" @click="goBack">
        <up-icon name="close" size="20" color="#1B4332"></up-icon>
      </view>
      <view class="header-center">
        <text class="progress-text">第 {{ currentLevel }} 关 / 共 {{ totalLevels }} 关</text>
        <view class="progress-bar-bg">
          <view
            class="progress-bar-fill"
            :style="{ width: progressPercent + '%' }"
          ></view>
        </view>
      </view>
      <view class="header-right">
        <view class="heart-count">
          <up-icon name="heart-fill" size="18" color="#F44336"></up-icon>
          <text>{{ hearts }}</text>
        </view>
      </view>
    </view>

    <!-- 题目区域 -->
    <scroll-view scroll-y class="question-scroll">
      <view class="question-container">
        <!-- 题目类型图标 -->
        <view class="question-emoji">🤔</view>

        <!-- 题干 -->
        <view class="question-text">{{ question.title }}</view>

        <!-- 选项列表 -->
        <view class="options-list">
          <view
            v-for="(option, index) in question.options"
            :key="index"
            class="option-item"
            :class="{
              selected: selectedOption === option.key,
              correct: showResult && option.key === correctAnswer,
              wrong: showResult && selectedOption === option.key && selectedOption !== correctAnswer
            }"
            @click="selectOption(option.key)"
          >
            <view class="option-key">{{ option.key }}</view>
            <view class="option-content">{{ option.value }}</view>
            <view class="option-icon">
              <up-icon
                v-if="showResult && option.key === correctAnswer"
                name="checkmark"
                size="20"
                color="#4CAF50"
              ></up-icon>
              <up-icon
                v-if="showResult && selectedOption === option.key && selectedOption !== correctAnswer"
                name="close"
                size="20"
                color="#F44336"
              ></up-icon>
            </view>
          </view>
        </view>

        <!-- 提示区域 -->
        <view v-if="showHint" class="hint-box">
          <up-icon name="bulb" size="16" color="#FFC107"></up-icon>
          <text class="hint-text">{{ question.hint }}</text>
        </view>
      </view>
    </scroll-view>

    <!-- 底部操作区 -->
    <view class="bottom-area">
      <view v-if="showResult" class="result-message">
        <view class="result-icon">{{ isCorrect ? '🎉' : '💪' }}</view>
        <view class="result-content">
          <text class="result-title">{{ isCorrect ? '答对啦！' : '再想想哦~' }}</text>
          <view v-if="isCorrect" class="points-gain">
            <text>+{{ points }} 积分</text>
            <text v-if="comboBonus">连续答对 +{{ comboBonus }} 积分</text>
          </view>
        </view>
      </view>

      <view class="bottom-buttons">
        <up-button
          v-if="!showResult"
          class="submit-btn"
          type="primary"
          size="large"
          shape="round"
          :disabled="!selectedOption"
          @click="submitAnswer"
        >
          提交答案
        </up-button>

        <up-button
          v-else
          class="next-btn"
          type="primary"
          size="large"
          shape="round"
          @click="nextQuestion"
        >
          {{ isCorrect ? '下一题 →' : '再试一次' }}
        </up-button>
      </view>
    </view>

    <!-- 答对庆祝动画 -->
    <view v-if="showCelebration" class="celebration-overlay">
      <view class="confetti" v-for="n in 20" :key="n" :style="confettiStyle(n)"></view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      statusBarHeight: 0,
      storyId: null,
      chapterId: null,
      currentLevel: 2,
      totalLevels: 5,
      hearts: 3,
      selectedOption: null,
      showResult: false,
      isCorrect: false,
      correctAnswer: 'B',
      points: 10,
      comboBonus: 5,
      showHint: false,
      showCelebration: false,
      question: {
        id: 1,
        title: '小红帽应该相信大灰狼吗？',
        hint: '想一想，大灰狼是好人还是坏人呢？',
        options: [
          { key: 'A', value: '应该相信' },
          { key: 'B', value: '不应该相信' },
          { key: 'C', value: '看情况' }
        ]
      }
    }
  },
  computed: {
    progressPercent() {
      return ((this.currentLevel - 1) / this.totalLevels) * 100
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
      uni.showModal({
        title: '提示',
        content: '确定要退出答题吗？',
        success: (res) => {
          if (res.confirm) {
            uni.navigateBack()
          }
        }
      })
    },
    selectOption(key) {
      if (this.showResult) return
      this.selectedOption = key
    },
    submitAnswer() {
      if (!this.selectedOption) return

      this.showResult = true
      this.isCorrect = this.selectedOption === this.correctAnswer

      if (this.isCorrect) {
        // 答对了
        this.showCelebration = true
        setTimeout(() => {
          this.showCelebration = false
        }, 1500)
      } else {
        // 答错了
        this.hearts--
        if (this.hearts <= 0) {
          setTimeout(() => {
            this.showGameOver()
          }, 500)
        }
      }
    },
    nextQuestion() {
      if (this.isCorrect) {
        // 下一题
        this.currentLevel++
        if (this.currentLevel > this.totalLevels) {
          this.showQuizComplete()
          return
        }
        this.loadNextQuestion()
      } else {
        // 再试一次
        this.selectedOption = null
        this.showResult = false
      }
    },
    loadNextQuestion() {
      // TODO: 加载下一题
      this.selectedOption = null
      this.showResult = false
    },
    showGameOver() {
      uni.showModal({
        title: '游戏结束',
        content: '爱心用完啦，再试一次吧！',
        showCancel: false,
        success: () => {
          uni.navigateBack()
        }
      })
    },
    showQuizComplete() {
      uni.showModal({
        title: '恭喜通关！🎉',
        content: `获得 ${this.points * this.totalLevels + this.comboBonus * 3} 积分`,
        showCancel: false,
        success: () => {
          uni.navigateBack()
        }
      })
    },
    confettiStyle(n) {
      const colors = ['#FFC107', '#F44336', '#2196F3', '#4CAF50', '#FF8C42']
      return {
        left: Math.random() * 100 + '%',
        top: '-20px',
        backgroundColor: colors[Math.floor(Math.random() * colors.length)],
        animationDelay: Math.random() * 0.5 + 's'
      }
    }
  }
}
</script>

<style lang="scss" scoped>
@import '@/styles/variables.scss';

.quiz-page {
  min-height: 100vh;
  background-color: $bg-primary;
  display: flex;
  flex-direction: column;
}

.quiz-header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  display: flex;
  align-items: center;
  padding: 0 $spacing-md;
  background: $bg-secondary;
  box-shadow: $shadow-sm;
  z-index: 100;

  .header-left,
  .header-right {
    width: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: $spacing-sm;
  }

  .header-center {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: 4px;

    .progress-text {
      font-size: $font-size-xs;
      color: $text-hint;
      text-align: center;
    }

    .progress-bar-bg {
      height: 6px;
      background: $bg-tertiary;
      border-radius: $radius-full;
      overflow: hidden;

      .progress-bar-fill {
        height: 100%;
        background: linear-gradient(90deg, $secondary-400, $secondary-500);
        border-radius: $radius-full;
        transition: width 0.3s ease;
      }
    }
  }

  .heart-count {
    display: flex;
    align-items: center;
    gap: 4px;

    text {
      font-size: $font-size-sm;
      font-weight: $font-weight-semibold;
      color: #F44336;
    }
  }
}

.question-scroll {
  flex: 1;
  margin-top: 80px;
  margin-bottom: 180px;
}

.question-container {
  padding: $spacing-lg;

  .question-emoji {
    font-size: 64px;
    text-align: center;
    margin-bottom: $spacing-lg;
  }

  .question-text {
    font-size: $font-size-lg;
    font-weight: $font-weight-medium;
    color: $text-primary;
    text-align: center;
    line-height: 1.6;
    margin-bottom: $spacing-xl;
  }

  .options-list {
    display: flex;
    flex-direction: column;
    gap: $spacing-sm;

    .option-item {
      display: flex;
      align-items: center;
      padding: $spacing-md;
      background: $bg-secondary;
      border: 2px solid transparent;
      border-radius: $radius-base;
      transition: all 0.2s ease;

      &:active {
        transform: scale(0.98);
      }

      &.selected {
        border-color: $primary-500;
        background: $primary-50;
      }

      &.correct {
        border-color: $success;
        background: rgba($success, 0.1);
      }

      &.wrong {
        border-color: $error;
        background: rgba($error, 0.1);
      }

      .option-key {
        width: 36px;
        height: 36px;
        display: flex;
        align-items: center;
        justify-content: center;
        background: $bg-tertiary;
        border-radius: 50%;
        font-size: $font-size-md;
        font-weight: $font-weight-semibold;
        color: $text-secondary;
        margin-right: $spacing-sm;
        flex-shrink: 0;
      }

      .option-content {
        flex: 1;
        font-size: $font-size-md;
        color: $text-primary;
      }

      .option-icon {
        margin-left: $spacing-sm;
        width: 24px;
        display: flex;
        justify-content: center;
      }
    }
  }

  .hint-box {
    margin-top: $spacing-lg;
    padding: $spacing-md;
    background: rgba($warning, 0.1);
    border-left: 3px solid $warning;
    border-radius: $radius-sm;
    display: flex;
    align-items: flex-start;
    gap: $spacing-sm;

    .hint-text {
      font-size: $font-size-sm;
      color: $text-secondary;
      line-height: 1.5;
    }
  }
}

.bottom-area {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background: $bg-secondary;
  padding: $spacing-md $spacing-lg;
  padding-bottom: calc(#{$spacing-md} + env(safe-area-inset-bottom));
  box-shadow: 0 -2px 8px rgba(0, 0, 0, 0.05);

  .result-message {
    display: flex;
    align-items: center;
    gap: $spacing-md;
    margin-bottom: $spacing-md;

    .result-icon {
      font-size: 40px;
    }

    .result-content {
      flex: 1;

      .result-title {
        display: block;
        font-size: $font-size-lg;
        font-weight: $font-weight-semibold;
        color: $text-primary;
        margin-bottom: 4px;
      }

      .points-gain {
        display: flex;
        flex-direction: column;
        gap: 2px;

        text {
          font-size: $font-size-sm;
          color: $secondary-500;
          font-weight: $font-weight-medium;
        }
      }
    }
  }

  .bottom-buttons {
    .submit-btn,
    .next-btn {
      width: 100%;
    }
  }
}

.celebration-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  pointer-events: none;
  z-index: 1000;
  overflow: hidden;

  .confetti {
    position: absolute;
    width: 10px;
    height: 10px;
    animation: confettiFall 1.5s ease-out forwards;
  }
}

@keyframes confettiFall {
  0% {
    transform: translateY(0) rotate(0deg);
    opacity: 1;
  }
  100% {
    transform: translateY(100vh) rotate(720deg);
    opacity: 0;
  }
}
</style>