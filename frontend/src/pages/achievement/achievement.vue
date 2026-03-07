<template>
  <view class="achievement-page">
    <!-- 头部统计 -->
    <view class="achievement-header">
      <view class="header-bg"></view>
      <view class="header-content">
        <view class="avatar-wrapper">
          <image class="user-avatar" :src="userInfo.avatar || '/static/default-avatar.png'" mode="aspectFill" />
          <view class="vip-badge" v-if="userInfo.isVip">VIP</view>
        </view>
        <text class="user-name">{{ userInfo.nickname || '小书虫' }}</text>
        <view class="level-info">
          <text class="level-text">Lv.{{ userLevel }} {{ levelName }}</text>
          <view class="level-progress">
            <view class="progress-bar" :style="{ width: levelProgress + '%' }"></view>
          </view>
          <text class="level-exp">{{ exp }}/{{ maxExp }} 经验</text>
        </view>
      </view>
    </view>

    <!-- 数据统计 -->
    <view class="stats-cards">
      <view class="stat-card">
        <text class="stat-icon">📖</text>
        <text class="stat-value">{{ stats.readDays }}</text>
        <text class="stat-label">阅读天数</text>
      </view>
      <view class="stat-card">
        <text class="stat-icon">🔥</text>
        <text class="stat-value">{{ stats.continuousDays }}</text>
        <text class="stat-label">连续打卡</text>
      </view>
      <view class="stat-card">
        <text class="stat-icon">⭐</text>
        <text class="stat-value">{{ stats.totalPoints }}</text>
        <text class="stat-label">累计积分</text>
      </view>
      <view class="stat-card">
        <text class="stat-icon">🏆</text>
        <text class="stat-value">{{ stats.badgeCount }}</text>
        <text class="stat-label">获得勋章</text>
      </view>
    </view>

    <!-- 勋章墙 -->
    <view class="badge-section">
      <view class="section-header">
        <text class="section-title">我的勋章</text>
        <text class="section-count">{{ unlockedBadges.length }}/{{ allBadges.length }}</text>
      </view>

      <view class="badge-grid">
        <view
          v-for="(badge, index) in allBadges"
          :key="index"
          class="badge-item"
          :class="{ unlocked: badge.unlocked }"
          @click="showBadgeDetail(badge)"
        >
          <view class="badge-image-wrapper">
            <view class="badge-image" :class="{ locked: !badge.unlocked }">
              <text v-if="badge.unlocked" class="badge-icon">{{ badge.icon }}</text>
              <u-icon v-else name="lock-fill" size="32" color="#C8D5C9" />
            </view>
            <view v-if="badge.isNew" class="new-badge">NEW</view>
          </view>
          <text class="badge-name">{{ badge.name }}</text>
          <text class="badge-desc">{{ badge.description }}</text>
        </view>
      </view>
    </view>

    <!-- 成就进度 -->
    <view class="achievement-section">
      <view class="section-header">
        <text class="section-title">成就进度</text>
      </view>

      <view class="achievement-list">
        <view
          v-for="(item, index) in achievements"
          :key="index"
          class="achievement-item"
          :class="{ completed: item.completed }"
        >
          <view class="achievement-icon">
            <text class="icon">{{ item.icon }}</text>
          </view>
          <view class="achievement-info">
            <view class="achievement-header">
              <text class="achievement-name">{{ item.name }}</text>
              <text class="achievement-progress-text">{{ item.current }}/{{ item.target }}</text>
            </view>
            <view class="achievement-progress-bar">
              <view
                class="achievement-progress-fill"
                :style="{ width: (item.current / item.target * 100) + '%' }"
              ></view>
            </view>
            <text class="achievement-desc">{{ item.description }}</text>
          </view>
          <view class="achievement-reward" v-if="item.completed">
            <u-icon name="checkmark-circle-fill" size="28" color="#4CAF50" />
          </view>
        </view>
      </view>
    </view>

    <!-- 勋章详情弹窗 -->
    <u-popup v-model="showBadgeDetailPopup" round="20">
      <view class="badge-detail-popup" v-if="selectedBadge">
        <view class="badge-detail-image" :class="{ locked: !selectedBadge.unlocked }">
          <text class="detail-icon">{{ selectedBadge.icon }}</text>
        </view>
        <text class="detail-name">{{ selectedBadge.name }}</text>
        <text class="detail-desc">{{ selectedBadge.description }}</text>
        <view class="detail-stats" v-if="selectedBadge.unlocked">
          <view class="detail-stat">
            <text class="stat-label">获得时间</text>
            <text class="stat-value">{{ selectedBadge.unlockedTime }}</text>
          </view>
        </view>
        <u-button
          type="primary"
          shape="round"
          size="large"
          @click="showBadgeDetailPopup = false"
        >
          知道了
        </u-button>
      </view>
    </u-popup>
  </view>
</template>

<script>
export default {
  data() {
    return {
      userInfo: {},
      userLevel: 3,
      levelName: '阅读小达人',
      exp: 450,
      maxExp: 600,
      stats: {
        readDays: 15,
        continuousDays: 7,
        totalPoints: 1250,
        badgeCount: 8
      },
      showBadgeDetailPopup: false,
      selectedBadge: null,
      allBadges: [
        {
          id: 1,
          name: '阅读新手',
          description: '累计阅读 1 天',
          icon: '📚',
          unlocked: true,
          unlockedTime: '2026-02-20',
          isNew: false
        },
        {
          id: 2,
          name: '阅读达人',
          description: '累计阅读 7 天',
          icon: '🌟',
          unlocked: true,
          unlockedTime: '2026-02-26',
          isNew: false
        },
        {
          id: 3,
          name: '阅读王者',
          description: '累计阅读 30 天',
          icon: '👑',
          unlocked: false,
          isNew: false
        },
        {
          id: 4,
          name: '答题小能手',
          description: '答对 10 道题',
          icon: '✏️',
          unlocked: true,
          unlockedTime: '2026-02-22',
          isNew: false
        },
        {
          id: 5,
          name: '配音小明星',
          description: '完成 5 次配音',
          icon: '🎤',
          unlocked: true,
          unlockedTime: '2026-02-28',
          isNew: true
        },
        {
          id: 6,
          name: '打卡达人',
          description: '连续打卡 7 天',
          icon: '🔥',
          unlocked: true,
          unlockedTime: '2026-02-26',
          isNew: false
        },
        {
          id: 7,
          name: '故事收藏家',
          description: '收藏 20 个故事',
          icon: '💝',
          unlocked: false,
          isNew: false
        },
        {
          id: 8,
          name: '分享小使者',
          description: '分享 10 次',
          icon: '🎁',
          unlocked: false,
          isNew: false
        }
      ],
      achievements: [
        {
          id: 1,
          name: '阅读时长挑战',
          description: '累计阅读达到 10 小时',
          icon: '⏱️',
          current: 6.5,
          target: 10,
          completed: false
        },
        {
          id: 2,
          name: '故事收集',
          description: '读完 20 个不同的故事',
          icon: '📖',
          current: 12,
          target: 20,
          completed: false
        },
        {
          id: 3,
          name: '答题闯关',
          description: '通过 50 道答题',
          icon: '🎯',
          current: 50,
          target: 50,
          completed: true
        },
        {
          id: 4,
          name: '配音演员',
          description: '完成 10 个角色配音',
          icon: '🎭',
          current: 3,
          target: 10,
          completed: false
        }
      ]
    }
  },
  computed: {
    unlockedBadges() {
      return this.allBadges.filter(b => b.unlocked)
    },
    levelProgress() {
      return (this.exp / this.maxExp) * 100
    }
  },
  onLoad() {
    this.loadUserInfo()
    this.loadUserStats()
  },
  methods: {
    loadUserInfo() {
      const userInfo = uni.getStorageSync('userInfo')
      this.userInfo = userInfo || {}
    },

    loadUserStats() {
      // 从本地或 API 加载用户统计
      const stats = uni.getStorageSync('userStats')
      if (stats) {
        this.stats = { ...this.stats, ...stats }
      }
    },

    showBadgeDetail(badge) {
      this.selectedBadge = badge
      this.showBadgeDetailPopup = true
    }
  }
}
</script>

<style lang="scss" scoped>
.achievement-page {
  min-height: 100vh;
  background: linear-gradient(180deg, #F0F7F4 0%, #FFFBF0 100%);
}

.achievement-header {
  position: relative;
  background: linear-gradient(135deg, #2D6A4F 0%, #1B4332 100%);
  padding: 0 24px 100px;

  .header-bg {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: url('/static/header-pattern.png') repeat;
    opacity: 0.1;
  }

  .header-content {
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: center;
    padding-top: 40px;

    .avatar-wrapper {
      position: relative;
      margin-bottom: 12px;

      .user-avatar {
        width: 80px;
        height: 80px;
        border-radius: 50%;
        border: 4px solid rgba(255, 255, 255, 0.3);
      }

      .vip-badge {
        position: absolute;
        bottom: -4px;
        right: -4px;
        padding: 2px 8px;
        background: linear-gradient(135deg, #FFD700, #FFA500);
        border-radius: 10px;
        font-size: 10px;
        font-weight: 600;
        color: #FFFFFF;
      }
    }

    .user-name {
      font-size: 20px;
      font-weight: 600;
      color: #FFFFFF;
      margin-bottom: 12px;
    }

    .level-info {
      display: flex;
      flex-direction: column;
      align-items: center;

      .level-text {
        font-size: 14px;
        color: rgba(255, 255, 255, 0.9);
        margin-bottom: 6px;
      }

      .level-progress {
        width: 200px;
        height: 6px;
        background: rgba(255, 255, 255, 0.3);
        border-radius: 3px;
        overflow: hidden;
        margin-bottom: 4px;

        .progress-bar {
          height: 100%;
          background: linear-gradient(90deg, #FFD700, #FFA500);
          border-radius: 3px;
          transition: width 0.3s;
        }
      }

      .level-exp {
        font-size: 12px;
        color: rgba(255, 255, 255, 0.7);
      }
    }
  }
}

.stats-cards {
  display: flex;
  justify-content: space-around;
  padding: 0 16px;
  margin-top: -60px;
  position: relative;
  z-index: 1;

  .stat-card {
    background: #FFFFFF;
    border-radius: 16px;
    padding: 16px 12px;
    display: flex;
    flex-direction: column;
    align-items: center;
    box-shadow: 0 4px 16px rgba(45, 106, 79, 0.1);
    min-width: 80px;

    .stat-icon {
      font-size: 24px;
      margin-bottom: 6px;
    }

    .stat-value {
      font-size: 24px;
      font-weight: 700;
      color: #2D6A4F;
      margin-bottom: 4px;
    }

    .stat-label {
      font-size: 12px;
      color: #94B49F;
    }
  }
}

.badge-section, .achievement-section {
  margin-top: 24px;
  padding: 0 16px;

  .section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 16px;

    .section-title {
      font-size: 18px;
      font-weight: 600;
      color: #1B4332;
    }

    .section-count {
      font-size: 14px;
      color: #94B49F;
    }
  }
}

.badge-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
  padding-bottom: 16px;

  .badge-item {
    display: flex;
    flex-direction: column;
    align-items: center;

    .badge-image-wrapper {
      position: relative;
      margin-bottom: 8px;

      .badge-image {
        width: 64px;
        height: 64px;
        background: linear-gradient(135deg, #E8F5E9, #C8E6C9);
        border-radius: 16px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 32px;
        transition: all 0.3s;

        &.locked {
          background: #F5F5F5;
          filter: grayscale(1);
        }
      }

      .new-badge {
        position: absolute;
        top: -4px;
        right: -4px;
        padding: 2px 6px;
        background: #FF4444;
        color: #FFFFFF;
        font-size: 10px;
        border-radius: 8px;
      }
    }

    .badge-name {
      font-size: 12px;
      color: #1B4332;
      margin-bottom: 2px;
      text-align: center;
    }

    .badge-desc {
      font-size: 10px;
      color: #94B49F;
      text-align: center;
    }
  }
}

.achievement-list {
  .achievement-item {
    background: #FFFFFF;
    border-radius: 12px;
    padding: 16px;
    margin-bottom: 12px;
    display: flex;
    align-items: center;

    .achievement-icon {
      width: 48px;
      height: 48px;
      background: linear-gradient(135deg, #E8F5E9, #C8E6C9);
      border-radius: 12px;
      display: flex;
      align-items: center;
      justify-content: center;
      margin-right: 12px;
      flex-shrink: 0;

      .icon {
        font-size: 24px;
      }
    }

    .achievement-info {
      flex: 1;

      .achievement-header {
        display: flex;
        justify-content: space-between;
        margin-bottom: 6px;

        .achievement-name {
          font-size: 15px;
          font-weight: 600;
          color: #1B4332;
        }

        .achievement-progress-text {
          font-size: 13px;
          color: #94B49F;
        }
      }

      .achievement-progress-bar {
        height: 6px;
        background: #F0F0F0;
        border-radius: 3px;
        overflow: hidden;
        margin-bottom: 6px;

        .achievement-progress-fill {
          height: 100%;
          background: linear-gradient(90deg, #2D6A4F, #4ECDC4);
          border-radius: 3px;
          transition: width 0.3s;
        }
      }

      .achievement-desc {
        font-size: 12px;
        color: #94B49F;
      }
    }

    .achievement-reward {
      margin-left: 12px;
    }
  }
}

.badge-detail-popup {
  width: 300px;
  padding: 32px 24px;
  text-align: center;

  .badge-detail-image {
    width: 100px;
    height: 100px;
    margin: 0 auto 16px;
    background: linear-gradient(135deg, #E8F5E9, #C8E6C9);
    border-radius: 24px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 56px;

    &.locked {
      background: #F5F5F5;
      filter: grayscale(1);
    }
  }

  .detail-name {
    display: block;
    font-size: 20px;
    font-weight: 600;
    color: #1B4332;
    margin-bottom: 8px;
  }

  .detail-desc {
    display: block;
    font-size: 14px;
    color: #94B49F;
    margin-bottom: 20px;
  }

  .detail-stats {
    background: #F5F5F5;
    border-radius: 12px;
    padding: 12px;
    margin-bottom: 20px;

    .detail-stat {
      display: flex;
      justify-content: space-between;

      .stat-label {
        font-size: 13px;
        color: #94B49F;
      }

      .stat-value {
        font-size: 13px;
        color: #1B4332;
      }
    }
  }
}
</style>
