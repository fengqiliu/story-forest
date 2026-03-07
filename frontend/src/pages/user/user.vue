<template>
  <view class="user-page">
    <!-- 自定义导航栏 -->
    <view class="custom-navbar" :style="{ paddingTop: statusBarHeight + 'px' }">
      <view class="navbar-bg">
        <view class="navbar-content">
          <view class="user-info" @click="editProfile">
            <view class="avatar-wrapper">
              <image class="avatar" src="https://picsum.photos/80/80?random=50" mode="aspectFill"></image>
              <view class="vip-badge">VIP</view>
            </view>
            <view class="user-details">
              <text class="nickname">乐乐</text>
              <view class="user-meta">
                <up-icon name="account" size="12" color="#94B49F"></up-icon>
                <text>5 岁 · 中班</text>
              </view>
            </view>
            <up-icon name="arrow-right" size="16" color="#94B49F"></up-icon>
          </view>
        </view>
      </view>
    </view>

    <!-- 内容区域 -->
    <scroll-view scroll-y class="content-scroll" :style="{ top: navbarHeight + 'px' }">
      <!-- 数据统计卡片 -->
      <view class="stats-card">
        <view class="stat-item">
          <text class="stat-value">{{ stats.readDays }}</text>
          <text class="stat-label">阅读天数</text>
        </view>
        <view class="stat-divider"></view>
        <view class="stat-item">
          <text class="stat-value">{{ stats.continuousDays }}</text>
          <text class="stat-label">连续打卡</text>
        </view>
        <view class="stat-divider"></view>
        <view class="stat-item">
          <text class="stat-value">{{ stats.points }}</text>
          <text class="stat-label">积分</text>
        </view>
        <view class="stat-divider"></view>
        <view class="stat-item">
          <text class="stat-value">{{ stats.badges }}</text>
          <text class="stat-label">勋章</text>
        </view>
      </view>

      <!-- 阅读进度 -->
      <view class="section">
        <view class="section-header">
          <text class="section-title">📖 我的阅读</text>
          <text class="section-more">全部 ›</text>
        </view>
        <view class="reading-list">
          <view
            v-for="(book, index) in readingList"
            :key="index"
            class="reading-item"
            @click="continueReading(book)"
          >
            <image class="reading-cover" :src="book.cover" mode="aspectFill"></image>
            <view class="reading-info">
              <text class="reading-title">{{ book.title }}</text>
              <view class="reading-progress-info">
                <up-progress
                  :percent="book.progress"
                  :show-text="false"
                  height="4"
                  active-color="#2D6A4F"
                ></up-progress>
                <text class="progress-text">{{ book.progress }}%</text>
              </view>
            </view>
            <up-icon name="play-circle-fill" size="24" color="#2D6A4F"></up-icon>
          </view>
        </view>
      </view>

      <!-- 勋章展示 -->
      <view class="section">
        <view class="section-header">
          <text class="section-title">🏆 我的勋章</text>
          <text class="section-more" @click="goToAchievement">全部 ›</text>
        </view>
        <view class="badges-grid">
          <view
            v-for="(badge, index) in badges"
            :key="index"
            class="badge-item"
            :class="{ locked: badge.isLocked }"
          >
            <view class="badge-image">
              <text class="badge-emoji">{{ badge.emoji }}</text>
            </view>
            <text class="badge-name">{{ badge.name }}</text>
          </view>
        </view>
      </view>

      <!-- 功能列表 -->
      <view class="section">
        <view class="menu-list">
          <view class="menu-item" @click="goToPointsMall">
            <view class="menu-icon">🛒</view>
            <text class="menu-title">积分商城</text>
            <up-icon name="arrow-right" size="16" color="#94B49F"></up-icon>
          </view>
          <view class="menu-item" @click="goToParentCenter">
            <view class="menu-icon">👨‍👩‍👧</view>
            <text class="menu-title">家长中心</text>
            <up-icon name="arrow-right" size="16" color="#94B49F"></up-icon>
          </view>
          <view class="menu-item" @click="goToFavorites">
            <view class="menu-icon">⭐</view>
            <text class="menu-title">我的收藏</text>
            <up-icon name="arrow-right" size="16" color="#94B49F"></up-icon>
          </view>
          <view class="menu-item" @click="goToHistory">
            <view class="menu-icon">📜</view>
            <text class="menu-title">浏览历史</text>
            <up-icon name="arrow-right" size="16" color="#94B49F"></up-icon>
          </view>
        </view>
      </view>

      <!-- 底部占位 -->
      <view style="height: 80px;"></view>
    </scroll-view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      statusBarHeight: 0,
      navbarHeight: 0,
      stats: {
        readDays: 15,
        continuousDays: 7,
        points: 1250,
        badges: 12
      },
      readingList: [
        {
          id: 1,
          title: '灰姑娘',
          cover: 'https://picsum.photos/60/80?random=60',
          progress: 25
        },
        {
          id: 2,
          title: '白雪公主',
          cover: 'https://picsum.photos/60/80?random=61',
          progress: 100
        }
      ],
      badges: [
        { id: 1, name: '阅读达人', emoji: '📖', isLocked: false },
        { id: 2, name: '答题王者', emoji: '🎯', isLocked: false },
        { id: 3, name: '配音新人', emoji: '🎤', isLocked: false },
        { id: 4, name: '坚持之星', emoji: '⭐', isLocked: false },
        { id: 5, name: '探索者', emoji: '🔍', isLocked: true },
        { id: 6, name: '分享达人', emoji: '💝', isLocked: true }
      ]
    }
  },
  onLoad() {
    this.statusBarHeight = uni.getSystemInfoSync().statusBarHeight || 20
    this.navbarHeight = this.statusBarHeight + 120
  },
  methods: {
    editProfile() {
      uni.navigateTo({
        url: '/pages/profile/profile'
      })
    },
    continueReading(book) {
      uni.navigateTo({
        url: `/pages/reading/reading?storyId=${book.id}`
      })
    },
    goToAchievement() {
      uni.navigateTo({
        url: '/pages/achievement/achievement'
      })
    },
    goToPointsMall() {
      uni.navigateTo({
        url: '/pages/points-mall/points-mall'
      })
    },
    goToParentCenter() {
      uni.navigateTo({
        url: '/pages/parent/parent'
      })
    },
    goToFavorites() {
      uni.navigateTo({
        url: '/pages/favorites/favorites'
      })
    },
    goToHistory() {
      uni.navigateTo({
        url: '/pages/history/history'
      })
    }
  }
}
</script>

<style lang="scss" scoped>
@import '@/styles/variables.scss';

.user-page {
  min-height: 100vh;
  background-color: $bg-primary;
}

.custom-navbar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 100;

  .navbar-bg {
    background: linear-gradient(135deg, $primary-500 0%, $primary-600 100%);
    padding-bottom: $spacing-lg;

    .navbar-content {
      padding: 0 $page-padding;

      .user-info {
        display: flex;
        align-items: center;
        gap: $spacing-md;
        padding: $spacing-lg 0;

        .avatar-wrapper {
          position: relative;

          .avatar {
            width: 64px;
            height: 64px;
            border-radius: 50%;
            border: 3px solid rgba(255, 255, 255, 0.3);
          }

          .vip-badge {
            position: absolute;
            bottom: -2px;
            right: -2px;
            padding: 2px 6px;
            background: linear-gradient(135deg, #FFD700, #FFA500);
            border-radius: $radius-sm;
            font-size: 9px;
            font-weight: $font-weight-bold;
            color: #fff;
          }
        }

        .user-details {
          flex: 1;

          .nickname {
            display: block;
            font-size: $font-size-lg;
            font-weight: $font-weight-semibold;
            color: #fff;
            margin-bottom: 4px;
          }

          .user-meta {
            display: flex;
            align-items: center;
            gap: 4px;

            text {
              font-size: $font-size-xs;
              color: rgba(255, 255, 255, 0.8);
            }
          }
        }
      }
    }
  }
}

.content-scroll {
  position: absolute;
  top: 140px;
  left: 0;
  right: 0;
  bottom: 0;
}

.stats-card {
  margin: $spacing-md $page-padding;
  padding: $spacing-lg;
  background: $bg-secondary;
  border-radius: $radius-lg;
  box-shadow: $shadow-md;
  display: flex;
  justify-content: space-around;
  align-items: center;

  .stat-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 2px;

    .stat-value {
      font-size: $font-size-xl;
      font-weight: $font-weight-bold;
      color: $primary-500;
    }

    .stat-label {
      font-size: $font-size-xs;
      color: $text-hint;
    }
  }

  .stat-divider {
    width: 1px;
    height: 40px;
    background: $bg-tertiary;
  }
}

.section {
  margin-bottom: $spacing-lg;

  .section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: $spacing-md $page-padding $spacing-sm;

    .section-title {
      font-size: $font-size-md;
      font-weight: $font-weight-semibold;
      color: $text-primary;
    }

    .section-more {
      font-size: $font-size-sm;
      color: $text-hint;
    }
  }
}

.reading-list {
  padding: 0 $page-padding;

  .reading-item {
    display: flex;
    align-items: center;
    gap: $spacing-sm;
    padding: $spacing-md;
    background: $bg-secondary;
    border-radius: $radius-md;
    margin-bottom: $spacing-sm;

    &:active {
      transform: scale(0.98);
    }

    .reading-cover {
      width: 50px;
      height: 70px;
      border-radius: $radius-sm;
      background: $bg-tertiary;
    }

    .reading-info {
      flex: 1;

      .reading-title {
        display: block;
        font-size: $font-size-md;
        color: $text-primary;
        margin-bottom: $spacing-sm;
      }

      .reading-progress-info {
        display: flex;
        align-items: center;
        gap: $spacing-sm;

        .progress-text {
          font-size: $font-size-xs;
          color: $text-hint;
          min-width: 35px;
        }
      }
    }
  }
}

.badges-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: $spacing-md;
  padding: 0 $page-padding;

  .badge-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: $spacing-xs;

    &.locked {
      opacity: 0.5;
    }

    .badge-image {
      width: 56px;
      height: 56px;
      display: flex;
      align-items: center;
      justify-content: center;
      background: linear-gradient(135deg, $primary-100, $primary-50);
      border-radius: 50%;

      .badge-emoji {
        font-size: 28px;
      }
    }

    .badge-name {
      font-size: $font-size-xs;
      color: $text-secondary;
      text-align: center;
    }
  }
}

.menu-list {
  margin: 0 $page-padding;
  background: $bg-secondary;
  border-radius: $radius-lg;
  overflow: hidden;

  .menu-item {
    display: flex;
    align-items: center;
    gap: $spacing-md;
    padding: $spacing-md $spacing-lg;
    border-bottom: 1px solid $bg-tertiary;

    &:last-child {
      border-bottom: none;
    }

    &:active {
      background: $bg-tertiary;
    }

    .menu-icon {
      font-size: 22px;
    }

    .menu-title {
      flex: 1;
      font-size: $font-size-md;
      color: $text-primary;
    }
  }
}
</style>