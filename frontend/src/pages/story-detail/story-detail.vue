<template>
  <view class="story-detail-page">
    <!-- 封面区域 -->
    <view class="cover-section">
      <image class="cover-image" :src="story.cover" mode="aspectFill" />
      <view class="cover-mask"></view>
    </view>

    <!-- 内容区域 -->
    <scroll-view scroll-y class="content-scroll">
      <!-- 故事信息卡片 -->
      <view class="story-info-card">
        <view class="story-header">
          <view>
            <text class="story-title">{{ story.title }}</text>
            <view class="story-meta">
              <up-icon name="star-fill" size="14" color="#FF8C42"></up-icon>
              <text class="story-rating">{{ story.rating }}</text>
              <text class="story-count">({{ story.reviewCount }}评价)</text>
              <up-icon name="account" size="14" color="#94B49F"></up-icon>
              <text class="story-age">{{ story.ageGroup }}</text>
              <up-icon name="clock" size="14" color="#94B49F"></up-icon>
              <text class="story-duration">{{ story.duration }}</text>
            </view>
          </view>
          <view class="action-buttons">
            <up-button
              class="action-btn"
              icon="heart"
              :icon-size="20"
              :plain="!isLiked"
              :type="isLiked ? 'error' : 'info'"
              size="small"
              shape="circle"
              @click="toggleLike"
            ></up-button>
            <up-button
              class="action-btn"
              icon="star"
              :icon-size="20"
              :plain="!isFavorite"
              type="warning"
              size="small"
              shape="circle"
              @click="toggleFavorite"
            ></up-button>
          </view>
        </view>

        <!-- 简介 -->
        <view class="story-section">
          <text class="section-title">简介</text>
          <view class="story-description">
            <text>{{ story.description }}</text>
            <text class="read-more" @click="showFullDescription"> › 更多</text>
          </view>
        </view>

        <!-- 章节列表 -->
        <view class="story-section">
          <view class="section-header">
            <text class="section-title">章节 ({{ currentChapter }}/{{ totalChapters }})</text>
          </view>
          <view class="chapter-list">
            <chapter-item
              v-for="(chapter, index) in chapters"
              :key="index"
              :chapter="chapter"
              :is-locked="chapter.isLocked"
              :is-completed="chapter.isCompleted"
              @click="playChapter(chapter)"
            ></chapter-item>
          </view>
        </view>

        <!-- 统计信息 -->
        <view class="stats-section">
          <view class="stat-item">
            <up-icon name="heart-fill" size="24" color="#F44336"></up-icon>
            <text class="stat-value">{{ formatNumber(stats.likeCount) }}</text>
            <text class="stat-label">点赞</text>
          </view>
          <view class="stat-item">
            <up-icon name="chat-fill" size="24" color="#2196F3"></up-icon>
            <text class="stat-value">{{ formatNumber(stats.commentCount) }}</text>
            <text class="stat-label">评论</text>
          </view>
        </view>
      </view>

      <!-- 底部占位 -->
      <view style="height: 100px;"></view>
    </scroll-view>

    <!-- 底部操作栏 -->
    <view class="bottom-bar">
      <up-button
        class="play-btn"
        text="开始阅读 ▶"
        type="primary"
        size="large"
        shape="round"
        @click="startReading"
      ></up-button>
    </view>
  </view>
</template>

<script>
import ChapterItem from '@/components/ChapterItem.vue'

export default {
  components: {
    ChapterItem
  },
  data() {
    return {
      storyId: null,
      isLiked: false,
      isFavorite: false,
      story: {
        id: 1,
        title: '灰姑娘 🧚‍♀️',
        cover: 'https://picsum.photos/750/400?random=20',
        rating: 4.9,
        reviewCount: '2.3 万',
        ageGroup: '5-6 岁',
        theme: '童话',
        duration: '8 分钟',
        description: '善良美丽的灰姑娘，在仙女教母的帮助下，参加了王子的舞会，最终和王子幸福地生活在一起。这是一个关于善良、勇敢和梦想的经典童话故事...',
        progress: 25
      },
      currentChapter: 3,
      totalChapters: 12,
      chapters: [
        {
          id: 1,
          title: '第 1 章 善良的女孩',
          duration: 150,
          isLocked: true,
          isCompleted: false
        },
        {
          id: 2,
          title: '第 2 章 仙女教母',
          duration: 195,
          isLocked: false,
          isCompleted: true
        },
        {
          id: 3,
          title: '第 3 章 舞会奇遇',
          duration: 165,
          isLocked: true,
          isCompleted: false
        },
        {
          id: 4,
          title: '第 4 章 水晶鞋',
          duration: 180,
          isLocked: true,
          isCompleted: false
        },
        {
          id: 5,
          title: '第 5 章 寻找主人',
          duration: 170,
          isLocked: true,
          isCompleted: false
        }
      ],
      stats: {
        likeCount: 152000,
        commentCount: 8234
      }
    }
  },
  onLoad(options) {
    if (options.id) {
      this.storyId = options.id
      this.loadStoryDetail()
    }
  },
  methods: {
    loadStoryDetail() {
      // TODO: 调用 API 获取故事详情
      console.log('Load story detail for id:', this.storyId)
    },
    toggleLike() {
      this.isLiked = !this.isLiked
      uni.showToast({
        title: this.isLiked ? '已点赞' : '已取消',
        icon: 'none'
      })
    },
    toggleFavorite() {
      this.isFavorite = !this.isFavorite
      uni.showToast({
        title: this.isFavorite ? '已收藏' : '已取消',
        icon: 'none'
      })
    },
    showFullDescription() {
      uni.showModal({
        title: '故事简介',
        content: this.story.description,
        showCancel: false
      })
    },
    playChapter(chapter) {
      if (chapter.isLocked) {
        uni.showToast({
          title: '答对前一章解锁',
          icon: 'none'
        })
        return
      }
      this.goToReading(chapter)
    },
    startReading() {
      const firstUnlockedChapter = this.chapters.find(c => !c.isLocked) || this.chapters[0]
      this.goToReading(firstUnlockedChapter)
    },
    goToReading(chapter) {
      uni.navigateTo({
        url: `/pages/reading/reading?storyId=${this.story.id}&chapterId=${chapter.id}`
      })
    },
    formatNumber(num) {
      if (num >= 10000) {
        return (num / 10000).toFixed(1) + '万'
      }
      return num.toString()
    }
  }
}
</script>

<style lang="scss" scoped>
@import '@/styles/variables.scss';

.story-detail-page {
  min-height: 100vh;
  background-color: $bg-primary;
  padding-bottom: 80px;
}

.cover-section {
  position: relative;
  height: 250px;

  .cover-image {
    width: 100%;
    height: 100%;
  }

  .cover-mask {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    height: 100px;
    background: linear-gradient(to bottom, transparent, $bg-primary);
  }
}

.content-scroll {
  height: calc(100vh - 250px);
}

.story-info-card {
  margin-top: -60px;
  margin-left: $page-padding;
  margin-right: $page-padding;
  padding: $spacing-lg;
  background: $bg-secondary;
  border-radius: $radius-lg;
  box-shadow: $shadow-lg;
}

.story-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: $spacing-lg;

  .story-title {
    font-size: $font-size-xl;
    font-weight: $font-weight-bold;
    color: $text-primary;
    display: block;
    margin-bottom: $spacing-xs;
  }

  .story-meta {
    display: flex;
    align-items: center;
    gap: 6px;
    font-size: $font-size-sm;
    color: $text-secondary;

    .story-rating {
      font-weight: $font-weight-semibold;
      color: $text-primary;
    }

    .story-count {
      color: $text-hint;
    }

    .story-age {
      margin-left: 8px;
    }

    .story-duration {
      color: $text-hint;
    }
  }

  .action-buttons {
    display: flex;
    gap: $spacing-sm;

    .action-btn {
      width: 40px;
      height: 40px;
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 0;
    }
  }
}

.story-section {
  margin-bottom: $spacing-lg;

  .section-title {
    font-size: $font-size-md;
    font-weight: $font-weight-semibold;
    color: $text-primary;
    display: block;
    margin-bottom: $spacing-sm;
  }

  .section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: $spacing-sm;
  }

  .story-description {
    font-size: $font-size-base;
    color: $text-secondary;
    line-height: 1.6;

    .read-more {
      color: $primary-500;
    }
  }

  .chapter-list {
    display: flex;
    flex-direction: column;
    gap: $spacing-sm;
  }
}

.stats-section {
  display: flex;
  justify-content: center;
  gap: $spacing-xl;
  padding: $spacing-lg 0;
  border-top: 1px solid $bg-tertiary;

  .stat-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: $spacing-xs;

    .stat-value {
      font-size: $font-size-lg;
      font-weight: $font-weight-bold;
      color: $text-primary;
    }

    .stat-label {
      font-size: $font-size-sm;
      color: $text-hint;
    }
  }
}

.bottom-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  padding: $spacing-md $page-padding;
  padding-bottom: calc(#{$spacing-md} + env(safe-area-inset-bottom));
  background: $bg-secondary;
  box-shadow: 0 -2px 8px rgba(0, 0, 0, 0.05);

  .play-btn {
    width: 100%;
  }
}
</style>