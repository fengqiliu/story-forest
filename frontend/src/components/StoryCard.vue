<template>
  <view class="story-card" @click="handleClick">
    <view class="card-image-wrapper">
      <image
        class="card-image"
        :src="story.cover"
        mode="aspectFill"
      />
      <view v-if="story.isNew" class="new-tag">NEW</view>
      <view v-if="story.isVip" class="vip-tag">
        <up-icon name="star-fill" size="10" color="#FFD700"></up-icon>
      </view>
    </view>

    <view class="card-content">
      <text class="card-title">{{ story.title }}</text>
      <view class="card-meta">
        <up-icon name="star-fill" size="12" color="#FF8C42"></up-icon>
        <text class="card-rating">{{ story.rating }}</text>
        <text class="card-age">{{ story.ageGroup }}</text>
      </view>
    </view>

    <view v-if="showProgress && story.progress" class="card-progress">
      <up-progress
        :percent="story.progress"
        :show-text="false"
        height="4"
        active-color="#2D6A4F"
      ></up-progress>
    </view>
  </view>
</template>

<script>
export default {
  name: 'StoryCard',
  props: {
    story: {
      type: Object,
      required: true
    },
    showProgress: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    handleClick() {
      this.$emit('click', this.story)
    }
  }
}
</script>

<style lang="scss" scoped>
@import '@/styles/variables.scss';

.story-card {
  width: 100%;
  background: $bg-secondary;
  border-radius: $radius-md;
  overflow: hidden;
  box-shadow: $shadow-base;
  transition: transform 0.15s ease;

  &:active {
    transform: scale(0.98);
  }

  .card-image-wrapper {
    position: relative;
    width: 100%;

    .card-image {
      width: 100%;
      height: 120px;
      background-color: $bg-tertiary;
    }

    .new-tag {
      position: absolute;
      top: 8px;
      right: 8px;
      padding: 2px 8px;
      font-size: 10px;
      color: #fff;
      background: $secondary-500;
      border-radius: $radius-sm;
      font-weight: $font-weight-semibold;
    }

    .vip-tag {
      position: absolute;
      top: 8px;
      left: 8px;
      padding: 2px 6px;
      background: rgba(0, 0, 0, 0.6);
      border-radius: $radius-sm;
    }
  }

  .card-content {
    padding: $spacing-sm;

    .card-title {
      display: block;
      font-size: $font-size-md;
      font-weight: $font-weight-semibold;
      color: $text-primary;
      line-height: 1.4;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      margin-bottom: 4px;
    }

    .card-meta {
      display: flex;
      align-items: center;
      gap: 4px;

      .card-rating {
        font-size: $font-size-sm;
        color: $text-secondary;
        font-weight: $font-weight-medium;
      }

      .card-age {
        font-size: $font-size-sm;
        color: $text-hint;
        margin-left: auto;
      }
    }
  }

  .card-progress {
    padding: 0 $spacing-sm $spacing-sm;
  }
}
</style>