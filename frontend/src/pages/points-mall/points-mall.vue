<template>
  <view class="points-mall-page">
    <!-- 头部积分余额 -->
    <view class="points-header">
      <view class="points-balance">
        <text class="balance-label">我的积分</text>
        <text class="balance-value">{{ userPoints }}</text>
        <text class="balance-unit">分</text>
      </view>
      <view class="points-actions">
        <u-button size="small" shape="round" plain @click="showPointsHistory">
          积分明细
        </u-button>
      </view>
    </view>

    <!-- -banner -->
    <view class="mall-banner">
      <text class="banner-title">积分当钱花</text>
      <text class="banner-subtitle">兑换精美礼品/故事会员</text>
    </view>

    <!-- 商品分类 -->
    <view class="category-tabs">
      <scroll-view scroll-x class="tabs-scroll">
        <view class="tabs-wrapper">
          <view
            v-for="(item, index) in categories"
            :key="index"
            class="tab-item"
            :class="{ active: currentCategory === index }"
            @click="switchCategory(index)"
          >
            {{ item }}
          </view>
        </view>
      </scroll-view>
    </view>

    <!-- 商品列表 -->
    <view class="goods-list">
      <view
        v-for="(goods, index) in filteredGoods"
        :key="index"
        class="goods-item"
        @click="exchangeGoods(goods)"
      >
        <view class="goods-image-wrapper">
          <image class="goods-image" :src="goods.image" mode="aspectFill" />
          <view v-if="goods.stock === 0" class="sold-out-tag">兑完啦</view>
          <view v-if="goods.isHot" class="hot-tag">热门</view>
        </view>
        <view class="goods-info">
          <text class="goods-name">{{ goods.name }}</text>
          <view class="goods-points">
            <text class="points-value">{{ goods.points }}</text>
            <text class="points-unit">积分</text>
            <text v-if="goods.originalPrice" class="original-price">
              ¥{{ goods.originalPrice }}
            </text>
          </view>
          <view class="goods-stock">
            <text>剩余{{ goods.stock }}件</text>
            <text v-if="goods.exchangeCount" class="exchange-count">
              已兑{{ goods.exchangeCount }}件
            </text>
          </view>
        </view>
      </view>
    </view>

    <!-- 空状态 -->
    <u-empty
      v-if="filteredGoods.length === 0"
      mode="favor"
      text="暂无商品"
    />

    <!-- 底部加载 -->
    <u-loadmore v-if="loading" :status="loadStatus" />

    <!-- 兑换确认弹窗 -->
    <u-modal
      v-model="showExchangeModal"
      title="确认兑换"
      :content="`确定使用 ${selectedGoods?.points || 0} 积分兑换 ${selectedGoods?.name || ''} 吗？`"
      :show-cancel-button="true"
      @confirm="confirmExchange"
      @cancel="showExchangeModal = false"
    />

    <!-- 积分明细弹窗 -->
    <u-popup v-model="showHistory" round="16">
      <view class="history-popup">
        <view class="history-header">
          <text class="history-title">积分明细</text>
          <u-icon name="close" size="24" @click="showHistory = false" />
        </view>
        <scroll-view scroll-y class="history-list">
          <view v-for="(item, index) in pointsHistory" :key="index" class="history-item">
            <view class="history-info">
              <text class="history-desc">{{ item.description }}</text>
              <text class="history-time">{{ item.time }}</text>
            </view>
            <text class="history-points" :class="{ positive: item.change > 0 }">
              {{ item.change > 0 ? '+' : '' }}{{ item.change }}
            </text>
          </view>
        </scroll-view>
      </view>
    </u-popup>
  </view>
</template>

<script>
export default {
  data() {
    return {
      userPoints: 1250, // 用户积分
      currentCategory: 0,
      categories: ['全部', '虚拟商品', '实物礼品', '会员服务', '学习用品'],
      loading: false,
      loadStatus: 'loadmore',
      showExchangeModal: false,
      showHistory: false,
      selectedGoods: null,
      goodsList: [
        {
          id: 1,
          name: '故事会员月卡',
          image: '/static/goods/vip-month.png',
          points: 500,
          originalPrice: 15,
          stock: 100,
          exchangeCount: 256,
          isHot: true,
          category: 3
        },
        {
          id: 2,
          name: '定制故事绘本',
          image: '/static/goods/book.png',
          points: 2000,
          originalPrice: 59,
          stock: 50,
          exchangeCount: 89,
          isHot: true,
          category: 1
        },
        {
          id: 3,
          name: '卡通文具套装',
          image: '/static/goods/stationery.png',
          points: 800,
          originalPrice: 25,
          stock: 200,
          exchangeCount: 156,
          isHot: false,
          category: 4
        },
        {
          id: 4,
          name: '故事森林徽章',
          image: '/static/goods/badge.png',
          points: 300,
          originalPrice: 10,
          stock: 500,
          exchangeCount: 423,
          isHot: false,
          category: 1
        },
        {
          id: 5,
          name: 'VIP 季卡',
          image: '/static/goods/vip-season.png',
          points: 1200,
          originalPrice: 39,
          stock: 80,
          exchangeCount: 178,
          isHot: true,
          category: 3
        },
        {
          id: 6,
          name: '儿童护眼台灯',
          image: '/static/goods/lamp.png',
          points: 5000,
          originalPrice: 199,
          stock: 20,
          exchangeCount: 45,
          isHot: false,
          category: 2
        }
      ],
      pointsHistory: [
        { description: '每日签到', time: '2026-03-05 09:30', change: 10 },
        { description: '阅读故事《灰姑娘》', time: '2026-03-04 20:15', change: 20 },
        { description: '答题正确', time: '2026-03-04 20:10', change: 15 },
        { description: '兑换故事会员月卡', time: '2026-03-03 14:20', change: -500 },
        { description: '连续打卡 7 天', time: '2026-03-02 08:00', change: 50 }
      ]
    }
  },
  computed: {
    filteredGoods() {
      if (this.currentCategory === 0) {
        return this.goodsList
      }
      return this.goodsList.filter(item => item.category === this.currentCategory)
    }
  },
  onLoad() {
    this.loadUserPoints()
  },
  methods: {
    async loadUserPoints() {
      // 从本地存储或 API 获取用户积分
      const points = uni.getStorageSync('userPoints')
      if (points) {
        this.userPoints = points
      }
    },

    switchCategory(index) {
      this.currentCategory = index
      // 添加触觉反馈
      // #ifdef MP-WEIXIN
      uni.vibrateShort({ type: 'light' })
      // #endif
    },

    showPointsHistory() {
      this.showHistory = true
    },

    exchangeGoods(goods) {
      if (goods.stock === 0) {
        uToast.warning({ title: '商品已兑完' })
        return
      }

      if (this.userPoints < goods.points) {
        uToast.warning({ title: '积分不足' })
        return
      }

      this.selectedGoods = goods
      this.showExchangeModal = true
    },

    async confirmExchange() {
      this.showExchangeModal = false

      try {
        // 调用兑换 API
        await this.doExchange(this.selectedGoods.id)

        // 更新积分
        this.userPoints -= this.selectedGoods.points

        // 保存兑换记录
        this.saveExchangeRecord(this.selectedGoods)

        uToast.success({ title: '兑换成功' })

        // 跳转到订单页或发放虚拟商品
        if (this.selectedGoods.category === 3) {
          // 虚拟商品，直接发放
          uToast.success({ title: '会员已到账' })
        }
      } catch (error) {
        console.error('兑换失败:', error)
        uToast.error({ title: '兑换失败，请重试' })
      }
    },

    async doExchange(goodsId) {
      // TODO: 调用兑换 API
      return new Promise((resolve) => {
        setTimeout(resolve, 500)
      })
    },

    saveExchangeRecord(goods) {
      const records = uni.getStorageSync('exchangeRecords') || []
      records.unshift({
        goodsId: goods.id,
        goodsName: goods.name,
        points: goods.points,
        time: new Date().toLocaleString()
      })
      uni.setStorageSync('exchangeRecords', records)
    }
  }
}
</script>

<style lang="scss" scoped>
.points-mall-page {
  min-height: 100vh;
  background: #F5F5F5;
  padding-bottom: 20px;
}

.points-header {
  background: linear-gradient(135deg, #2D6A4F 0%, #1B4332 100%);
  padding: 24px 16px 80px;
  position: relative;

  .points-balance {
    text-align: center;
    margin-bottom: 16px;

    .balance-label {
      display: block;
      font-size: 14px;
      color: rgba(255, 255, 255, 0.8);
      margin-bottom: 8px;
    }

    .balance-value {
      font-size: 48px;
      font-weight: 700;
      color: #FFFFFF;
      margin-right: 4px;
    }

    .balance-unit {
      font-size: 20px;
      color: rgba(255, 255, 255, 0.8);
    }
  }
}

.mall-banner {
  background: linear-gradient(135deg, #FF8C42 0%, #F57C00 100%);
  margin: -60px 16px 16px;
  padding: 20px 24px;
  border-radius: 16px;
  position: relative;

  .banner-title {
    display: block;
    font-size: 24px;
    font-weight: 600;
    color: #FFFFFF;
    margin-bottom: 4px;
  }

  .banner-subtitle {
    display: block;
    font-size: 14px;
    color: rgba(255, 255, 255, 0.8);
  }
}

.category-tabs {
  background: #FFFFFF;
  margin-bottom: 12px;
  padding: 12px 0;

  .tabs-scroll {
    white-space: nowrap;

    .tabs-wrapper {
      display: inline-flex;
      padding: 0 16px;

      .tab-item {
        padding: 8px 20px;
        font-size: 15px;
        color: #52796F;
        border-radius: 20px;
        margin-right: 8px;
        transition: all 0.2s;

        &.active {
          background: #2D6A4F;
          color: #FFFFFF;
        }
      }
    }
  }
}

.goods-list {
  padding: 0 12px;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;

  .goods-item {
    width: calc(50% - 8px);
    background: #FFFFFF;
    border-radius: 12px;
    overflow: hidden;
    margin-bottom: 12px;

    .goods-image-wrapper {
      position: relative;
      height: 160px;

      .goods-image {
        width: 100%;
        height: 100%;
      }

      .sold-out-tag, .hot-tag {
        position: absolute;
        top: 8px;
        left: 8px;
        padding: 2px 8px;
        font-size: 12px;
        color: #FFFFFF;
        border-radius: 4px;
      }

      .sold-out-tag {
        background: #94B49F;
      }

      .hot-tag {
        background: #FF4444;
      }
    }

    .goods-info {
      padding: 12px;

      .goods-name {
        display: block;
        font-size: 15px;
        color: #1B4332;
        margin-bottom: 8px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }

      .goods-points {
        display: flex;
        align-items: baseline;
        margin-bottom: 6px;

        .points-value {
          font-size: 20px;
          font-weight: 600;
          color: #FF8C42;
          margin-right: 4px;
        }

        .points-unit {
          font-size: 12px;
          color: #FF8C42;
          margin-right: 8px;
        }

        .original-price {
          font-size: 12px;
          color: #94B49F;
          text-decoration: line-through;
        }
      }

      .goods-stock {
        font-size: 12px;
        color: #94B49F;
        display: flex;
        justify-content: space-between;

        .exchange-count {
          color: #52796F;
        }
      }
    }
  }
}

.history-popup {
  width: 320px;
  padding: 20px;

  .history-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 16px;

    .history-title {
      font-size: 18px;
      font-weight: 600;
      color: #1B4332;
    }
  }

  .history-list {
    max-height: 400px;

    .history-item {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 12px 0;
      border-bottom: 1px solid #F0F0F0;

      .history-info {
        .history-desc {
          display: block;
          font-size: 14px;
          color: #1B4332;
          margin-bottom: 4px;
        }

        .history-time {
          display: block;
          font-size: 12px;
          color: #94B49F;
        }
      }

      .history-points {
        font-size: 16px;
        font-weight: 600;
        color: #94B49F;

        &.positive {
          color: #4CAF50;
        }
      }
    }
  }
}
</style>
