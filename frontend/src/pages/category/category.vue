<template>
  <view class="category-page">
    <!-- 顶部导航 -->
    <view class="top-bar" :style="{ paddingTop: statusBarHeight + 'px' }">
      <view class="top-left" @click="goBack">
        <up-icon name="arrow-left" size="20" color="#fff"></up-icon>
      </view>
      <view class="top-center">
        <text class="page-title">故事分类</text>
      </view>
      <view class="top-right" @click="showFilter">
        <up-icon name="filter" size="20" color="#fff"></up-icon>
      </view>
    </view>

    <!-- 搜索栏 -->
    <view class="search-section">
      <view class="search-box">
        <up-icon name="search" size="18" color="#94B49F"></up-icon>
        <input
          class="search-input"
          v-model="keyword"
          placeholder="搜索故事/角色/主题"
          confirm-type="search"
          @confirm="onSearch"
        />
      </view>
    </view>

    <!-- 分类 Tab -->
    <view class="tab-section">
      <scroll-view scroll-x class="tab-scroll" show-scrollbar="false">
        <view class="tab-items">
          <view
            v-for="(tab, index) in tabs"
            :key="index"
            class="tab-item"
            :class="{ active: activeTab === index }"
            @click="switchTab(index)"
          >
            <text class="tab-text">{{ tab }}</text>
            <view v-if="activeTab === index" class="tab-indicator"></view>
          </view>
        </view>
      </scroll-view>
    </view>

    <!-- 内容区域 -->
    <scroll-view scroll-y class="content-scroll">
      <!-- 年龄筛选 -->
      <view v-if="activeTab === 0" class="filter-section">
        <view class="filter-grid">
          <view
            v-for="(age, index) in ageGroups"
            :key="index"
            class="filter-item"
            :class="{ active: selectedAge === index }"
            @click="selectAge(index)"
          >
            <text class="filter-icon">{{ age.icon }}</text>
            <text class="filter-name">{{ age.name }}</text>
            <text class="filter-count">{{ age.count }}个故事</text>
          </view>
        </view>
      </view>

      <!-- 主题筛选 -->
      <view v-if="activeTab === 1" class="filter-section">
        <view class="filter-grid">
          <view
            v-for="(theme, index) in themes"
            :key="index"
            class="filter-item"
            :class="{ active: selectedTheme === index }"
            @click="selectTheme(index)"
          >
            <text class="filter-icon">{{ theme.icon }}</text>
            <text class="filter-name">{{ theme.name }}</text>
            <text class="filter-count">{{ theme.count }}个故事</text>
          </view>
        </view>
      </view>

      <!-- 时长筛选 -->
      <view v-if="activeTab === 2" class="filter-section">
        <view class="filter-grid">
          <view
            v-for="(duration, index) in durations"
            :key="index"
            class="filter-item"
            :class="{ active: selectedDuration === index }"
            @click="selectDuration(index)"
          >
            <text class="filter-icon">{{ duration.icon }}</text>
            <text class="filter-name">{{ duration.name }}</text>
            <text class="filter-count">{{ duration.count }}个故事</text>
          </view>
        </view>
      </view>

      <!-- 故事列表 -->
      <view class="story-section">
        <view class="section-header">
          <text class="section-title">共 {{ totalStories }} 个故事</text>
          <view class="sort-btn" @click="showSort">
            <text>排序</text>
            <up-icon name="arrow-down" size="14"></up-icon>
          </view>
        </view>
        <view class="story-list">
          <story-card
            v-for="(story, index) in storyList"
            :key="index"
            :story="story"
            @click="goToStory(story)"
          ></story-card>
        </view>
      </view>

      <!-- 底部占位 -->
      <view style="height: 40px;"></view>
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
      keyword: '',
      activeTab: 0,
      tabs: ['按年龄', '按主题', '按时长'],
      selectedAge: null,
      selectedTheme: null,
      selectedDuration: null,
      totalStories: 156,
      ageGroups: [
        { icon: '🍼', name: '3-4 岁', count: 35, desc: '启蒙阶段' },
        { icon: '🧸', name: '5-6 岁', count: 48, desc: '幼儿阶段' },
        { icon: '📚', name: '7-8 岁', count: 42, desc: '小学低年级' },
        { icon: '🎓', name: '9-10 岁', count: 31, desc: '小学中年级' }
      ],
      themes: [
        { icon: '🧚', name: '童话', count: 56 },
        { icon: '🔬', name: '科普', count: 28 },
        { icon: '🦊', name: '寓言', count: 24 },
        { icon: '📜', name: '历史', count: 18 },
        { icon: '📖', name: '成语', count: 22 },
        { icon: '🔤', name: '英文', count: 8 }
      ],
      durations: [
        { icon: '⏱️', name: '短篇', count: 45, desc: '5 分钟内' },
        { icon: '⏱️', name: '中篇', count: 68, desc: '10 分钟内' },
        { icon: '⏱️', name: '长篇', count: 43, desc: '20 分钟+' }
      ],
      storyList: [
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
  onLoad(options) {
    this.statusBarHeight = uni.getSystemInfoSync().statusBarHeight || 20
    if (options.type) {
      // 根据类型选择 Tab
      const typeMap = { fairy: 1, science: 1, fable: 1, history: 1, idiom: 1, english: 1 }
      if (typeMap[options.type]) {
        this.activeTab = 1
      }
    }
  },
  methods: {
    goBack() {
      uni.navigateBack()
    },
    switchTab(index) {
      this.activeTab = index
      this.selectedAge = null
      this.selectedTheme = null
      this.selectedDuration = null
    },
    selectAge(index) {
      this.selectedAge = this.selectedAge === index ? null : index
    },
    selectTheme(index) {
      this.selectedTheme = this.selectedTheme === index ? null : index
    },
    selectDuration(index) {
      this.selectedDuration = this.selectedDuration === index ? null : index
    },
    showFilter() {
      uni.showActionSheet({
        itemList: ['综合排序', '热门优先', '最新上架', '时长升序', '时长降序'],
        success: (res) => {
          console.log('Selected:', res.tapIndex)
        }
      })
    },
    showSort() {
      this.showFilter()
    },
    onSearch() {
      console.log('Search:', this.keyword)
    },
    goToStory(story) {
      uni.navigateTo({
        url: `/pages/story-detail/story-detail?id=${story.id}`
      })
    }
  }
}
</script>

<style lang="scss" scoped>
@import '@/styles/variables.scss';

.category-page {
  min-height: 100vh;
  background-color: $bg-primary;
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
  background: linear-gradient(135deg, $primary-500 0%, $primary-600 100%);
  z-index: 100;

  .top-left,
  .top-right {
    width: 50px;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: $spacing-sm;
  }

  .top-center {
    flex: 1;
    text-align: center;

    .page-title {
      font-size: $font-size-md;
      font-weight: $font-weight-semibold;
      color: #fff;
    }
  }
}

.search-section {
  margin-top: 50px;
  padding: $spacing-md $page-padding;

  .search-box {
    display: flex;
    align-items: center;
    background: $bg-secondary;
    border-radius: $radius-full;
    padding: $spacing-sm $spacing-md;
    gap: $spacing-sm;

    .search-input {
      flex: 1;
      font-size: $font-size-sm;
    }
  }
}

.tab-section {
  padding: 0 $page-padding $spacing-md;
  border-bottom: 1px solid $bg-tertiary;

  .tab-scroll {
    white-space: nowrap;

    .tab-items {
      display: inline-flex;
      gap: $spacing-lg;

      .tab-item {
        display: inline-flex;
        flex-direction: column;
        align-items: center;
        padding: $spacing-sm $spacing-sm;
        position: relative;

        &.active .tab-text {
          color: $primary-500;
          font-weight: $font-weight-semibold;
        }

        .tab-text {
          font-size: $font-size-md;
          color: $text-secondary;
        }

        .tab-indicator {
          position: absolute;
          bottom: -8px;
          left: 50%;
          transform: translateX(-50%);
          width: 20px;
          height: 3px;
          background: $primary-500;
          border-radius: $radius-full;
        }
      }
    }
  }
}

.content-scroll {
  height: calc(100vh - 140px);
}

.filter-section {
  padding: $spacing-lg $page-padding;

  .filter-grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: $spacing-md;

    .filter-item {
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: $spacing-lg;
      background: $bg-secondary;
      border-radius: $radius-md;
      box-shadow: $shadow-sm;
      border: 2px solid transparent;
      transition: all 0.2s ease;

      &.active {
        border-color: $primary-500;
        background: $primary-50;
      }

      &:active {
        transform: scale(0.98);
      }

      .filter-icon {
        font-size: 40px;
        margin-bottom: $spacing-xs;
      }

      .filter-name {
        font-size: $font-size-md;
        font-weight: $font-weight-semibold;
        color: $text-primary;
        margin-bottom: 2px;
      }

      .filter-count {
        font-size: $font-size-xs;
        color: $text-hint;
      }
    }
  }
}

.story-section {
  padding: $spacing-lg $page-padding;

  .section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: $spacing-md;

    .section-title {
      font-size: $font-size-sm;
      color: $text-hint;
    }

    .sort-btn {
      display: flex;
      align-items: center;
      gap: 4px;
      padding: $spacing-xs $spacing-sm;
      background: $bg-secondary;
      border-radius: $radius-sm;

      text {
        font-size: $font-size-xs;
        color: $text-secondary;
      }
    }
  }

  .story-list {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: $spacing-md;
  }
}
</style>