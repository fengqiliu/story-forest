<template>
  <view class="index-page">
    <!-- 自定义导航栏 -->
    <view class="custom-navbar" :style="{ paddingTop: statusBarHeight + 'px' }">
      <view class="navbar-top">
        <view class="search-bar" @click="goToSearch">
          <up-icon name="search" size="18" color="#94B49F"></up-icon>
          <text class="search-placeholder">搜索故事/角色/主题</text>
        </view>
        <view class="notification-icon" @click="goToNotification">
          <up-badge :value="3" :show-zero="false">
            <up-icon name="bell" size="22" color="#1B4332"></up-icon>
          </up-badge>
        </view>
      </view>
    </view>

    <!-- 内容区域 -->
    <scroll-view scroll-y class="content-scroll">
      <!-- 用户问候卡片 -->
      <view class="greeting-card">
        <view class="greeting-content">
          <view class="greeting-text">
            <text class="greeting-emoji">👋</text>
            <text class="greeting-title">早上好，乐乐</text>
            <view class="vip-tag">
              <up-icon name="star-fill" size="12" color="#FFD700"></up-icon>
              <text>VIP</text>
            </view>
          </view>
          <up-button class="sign-btn" size="small" type="warning" shape="round">
            签到
          </up-button>
        </view>
      </view>

      <!-- 今日精选入口 -->
      <view class="featured-section">
        <view class="section-header" @click="goToFeatured">
          <text class="section-title">今日精选</text>
          <up-icon name="arrow-right" size="16" color="#94B49F"></up-icon>
        </view>
        <view class="featured-grid">
          <view class="featured-item" @click="goToDailyRecommend">
            <view class="featured-icon">📖</view>
            <text class="featured-text">今日推荐</text>
          </view>
          <view class="featured-item" @click="goToQuizChallenge">
            <view class="featured-icon">🎯</view>
            <text class="featured-text">答题挑战</text>
          </view>
          <view class="featured-item" @click="goToCheckin">
            <view class="featured-icon">🏆</view>
            <text class="featured-text">打卡奖励</text>
          </view>
        </view>
      </view>

      <!-- 轮播卡片 -->
      <view class="banner-section">
        <up-swiper
          :list="bannerList"
          key-name="image"
          height="160"
          radius="12"
          indicator
          @click="onBannerClick"
        ></up-swiper>
      </view>

      <!-- 分类入口 -->
      <view class="category-section">
        <view class="section-header" @click="goToCategory">
          <text class="section-title">分类选择</text>
          <up-icon name="arrow-right" size="16" color="#94B49F"></up-icon>
        </view>
        <view class="category-grid">
          <view
            v-for="(item, index) in categoryList"
            :key="index"
            class="category-item"
            @click="goToCategory(item.type)"
          >
            <view class="category-icon">{{ item.icon }}</view>
            <text class="category-name">{{ item.name }}</text>
          </view>
        </view>
      </view>

      <!-- 猜你喜欢 -->
      <view class="recommend-section">
        <view class="section-header">
          <text class="section-title">猜你喜欢</text>
          <up-icon name="arrow-right" size="16" color="#94B49F"></up-icon>
        </view>
        <view class="recommend-grid">
          <story-card
            v-for="(story, index) in recommendList"
            :key="index"
            :story="story"
            @click="goToStoryDetail(story)"
          ></story-card>
        </view>
      </view>

      <!-- 底部占位 -->
      <view style="height: 80px;"></view>
    </scroll-view>
  </view>
</template>

<script>
import StoryCard from '@/components/StoryCard.vue'

export default {
  components: {
    StoryCard
  },
  data() {
    return {
      statusBarHeight: 0,
      bannerList: [
        {
          image: 'https://picsum.photos/750/320?random=1',
          storyId: 1
        },
        {
          image: 'https://picsum.photos/750/320?random=2',
          storyId: 2
        },
        {
          image: 'https://picsum.photos/750/320?random=3',
          storyId: 3
        }
      ],
      categoryList: [
        { icon: '🧚', name: '童话', type: 'fairy' },
        { icon: '🔬', name: '科普', type: 'science' },
        { icon: '🦊', name: '寓言', type: 'fable' },
        { icon: '📜', name: '历史', type: 'history' },
        { icon: '📖', name: '成语', type: 'idiom' },
        { icon: '🔤', name: '英文', type: 'english' }
      ],
      recommendList: [
        {
          id: 1,
          title: '灰姑娘',
          cover: 'https://picsum.photos/160/120?random=10',
          rating: 4.9,
          ageGroup: '5-6 岁',
          isNew: true
        },
        {
          id: 2,
          title: '小王子',
          cover: 'https://picsum.photos/160/120?random=11',
          rating: 4.8,
          ageGroup: '7-8 岁',
          isNew: false
        },
        {
          id: 3,
          title: '白雪公主',
          cover: 'https://picsum.photos/160/120?random=12',
          rating: 4.7,
          ageGroup: '5-6 岁',
          isNew: false
        },
        {
          id: 4,
          title: '十万个为什么',
          cover: 'https://picsum.photos/160/120?random=13',
          rating: 4.9,
          ageGroup: '9-10 岁',
          isNew: true
        }
      ]
    }
  },
  onLoad() {
    this.statusBarHeight = uni.getSystemInfoSync().statusBarHeight || 20
  },
  methods: {
    goToSearch() {
      uni.navigateTo({ url: '/pages/search/search' })
    },
    goToNotification() {
      uni.navigateTo({ url: '/pages/notification/notification' })
    },
    goToFeatured() {
      uni.navigateTo({ url: '/pages/featured/featured' })
    },
    goToDailyRecommend() {
      uni.navigateTo({ url: '/pages/story-detail/story-detail?id=1' })
    },
    goToQuizChallenge() {
      uni.navigateTo({ url: '/pages/quiz/quiz' })
    },
    goToCheckin() {
      uni.navigateTo({ url: '/pages/checkin/checkin' })
    },
    goToCategory(type) {
      uni.navigateTo({
        url: type ? `/pages/category/category?type=${type}` : '/pages/category/category'
      })
    },
    onBannerClick(index) {
      const banner = this.bannerList[index]
      uni.navigateTo({ url: `/pages/story-detail/story-detail?id=${banner.storyId}` })
    },
    goToStoryDetail(story) {
      uni.navigateTo({ url: `/pages/story-detail/story-detail?id=${story.id}` })
    }
  }
}
</script>

<style lang="scss" scoped>
@import '@/styles/variables.scss';

.index-page {
  min-height: 100vh;
  background-color: $bg-primary;
}

.custom-navbar {
  background: linear-gradient(135deg, $primary-500 0%, $primary-600 100%);
  padding-bottom: $spacing-sm;

  .navbar-top {
    display: flex;
    align-items: center;
    padding: 0 $page-padding;
    gap: $spacing-md;

    .search-bar {
      flex: 1;
      display: flex;
      align-items: center;
      background: rgba(255, 255, 255, 0.9);
      border-radius: $radius-full;
      padding: $spacing-sm $spacing-md;
      gap: $spacing-sm;

      .search-placeholder {
        font-size: $font-size-sm;
        color: $text-hint;
      }
    }

    .notification-icon {
      padding: $spacing-xs;
    }
  }
}

.content-scroll {
  height: calc(100vh - 100px);
  padding-bottom: $spacing-lg;
}

.greeting-card {
  margin: $spacing-md $page-padding $spacing-lg;
  padding: $spacing-lg;
  background: linear-gradient(135deg, $primary-100 0%, $primary-50 100%);
  border-radius: $radius-lg;

  .greeting-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .greeting-text {
    display: flex;
    align-items: center;
    gap: $spacing-sm;

    .greeting-emoji {
      font-size: 24px;
    }

    .greeting-title {
      font-size: $font-size-lg;
      font-weight: $font-weight-semibold;
      color: $text-primary;
    }

    .vip-tag {
      display: flex;
      align-items: center;
      gap: 2px;
      background: linear-gradient(135deg, #FFD700, #FFA500);
      padding: 2px 8px;
      border-radius: $radius-sm;

      text {
        font-size: 10px;
        color: #fff;
        font-weight: $font-weight-semibold;
      }
    }
  }

  .sign-btn {
    padding: $spacing-sm $spacing-lg;
    font-size: $font-size-sm;
  }
}

.featured-section {
  padding: 0 $page-padding $spacing-lg;

  .section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: $spacing-md;

    .section-title {
      font-size: $font-size-lg;
      font-weight: $font-weight-semibold;
      color: $text-primary;
    }
  }

  .featured-grid {
    display: flex;
    gap: $spacing-md;

    .featured-item {
      flex: 1;
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: $spacing-lg;
      background: $bg-secondary;
      border-radius: $radius-md;
      box-shadow: $shadow-sm;

      .featured-icon {
        font-size: 32px;
        margin-bottom: $spacing-sm;
      }

      .featured-text {
        font-size: $font-size-sm;
        color: $text-secondary;
      }
    }
  }
}

.banner-section {
  padding: 0 $page-padding $spacing-lg;
}

.category-section {
  padding: 0 $page-padding $spacing-lg;

  .section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: $spacing-md;

    .section-title {
      font-size: $font-size-lg;
      font-weight: $font-weight-semibold;
      color: $text-primary;
    }
  }

  .category-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: $spacing-md;

    .category-item {
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: $spacing-lg $spacing-sm;
      background: $bg-secondary;
      border-radius: $radius-md;
      box-shadow: $shadow-sm;

      .category-icon {
        font-size: 36px;
        margin-bottom: $spacing-xs;
      }

      .category-name {
        font-size: $font-size-sm;
        color: $text-secondary;
      }
    }
  }
}

.recommend-section {
  padding: 0 $page-padding $spacing-lg;

  .section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: $spacing-md;

    .section-title {
      font-size: $font-size-lg;
      font-weight: $font-weight-semibold;
      color: $text-primary;
    }
  }

  .recommend-grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: $spacing-md;
  }
}
</style>