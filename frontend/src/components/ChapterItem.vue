<template>
  <view
    class="chapter-item"
    :class="{ locked: isLocked, completed: isCompleted }"
    @click="handleClick"
  >
    <view class="chapter-icon">
      <up-icon
        v-if="isCompleted"
        name="checkmark-circle"
        size="24"
        color="#4CAF50"
      ></up-icon>
      <up-icon
        v-else-if="isLocked"
        name="lock-fill"
        size="20"
        color="#94B49F"
      ></up-icon>
      <up-icon
        v-else
        name="play-circle-fill"
        size="24"
        color="#2D6A4F"
      ></up-icon>
    </view>

    <view class="chapter-info">
      <text class="chapter-title">{{ chapter.title }}</text>
      <view class="chapter-meta">
        <up-icon name="clock" size="12" color="#94B49F"></up-icon>
        <text class="chapter-duration">{{ formatDuration(chapter.duration) }}</text>
        <text v-if="isLocked" class="lock-hint">· 答对解锁</text>
      </view>
    </view>

    <view class="chapter-action">
      <up-icon name="arrow-right" size="16" color="#94B49F"></up-icon>
    </view>
  </view>
</template>

<script>
export default {
  name: 'ChapterItem',
  props: {
    chapter: {
      type: Object,
      required: true
    },
    isLocked: {
      type: Boolean,
      default: false
    },
    isCompleted: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    formatDuration(seconds) {
      const m = Math.floor(seconds / 60)
      const s = seconds % 60
      return `${m}:${s.toString().padStart(2, '0')}`
    },
    handleClick() {
      if (!this.isLocked) {
        this.$emit('click', this.chapter)
      } else {
        uni.showToast({
          title: '需答对前一章解锁',
          icon: 'none'
        })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
@import '@/styles/variables.scss';

.chapter-item {
  display: flex;
  align-items: center;
  padding: $spacing-md;
  background: $bg-secondary;
  border-radius: $radius-base;
  margin-bottom: $spacing-sm;
  transition: all 0.2s ease;

  &.locked {
    opacity: 0.6;
  }

  &.completed {
    background: $bg-tertiary;
  }

  &:active:not(.locked) {
    transform: scale(0.98);
  }

  .chapter-icon {
    margin-right: $spacing-sm;
    display: flex;
    align-items: center;
  }

  .chapter-info {
    flex: 1;

    .chapter-title {
      display: block;
      font-size: $font-size-md;
      color: $text-primary;
      margin-bottom: 4px;
    }

    .chapter-meta {
      display: flex;
      align-items: center;
      gap: 4px;
      font-size: $font-size-sm;
      color: $text-hint;

      .chapter-duration {
        color: $text-hint;
      }

      .lock-hint {
        color: $secondary-500;
      }
    }
  }

  .chapter-action {
    margin-left: $spacing-sm;
  }
}
</style>