<template>
  <view class="container">
    <view class="md3-demo">
      <!-- 页面标题 -->
      <view class="page-header">
        <h1 class="page-title">Material Design 3</h1>
        <p class="page-subtitle">设计系统组件演示</p>
      </view>

      <!-- 按钮部分 -->
      <section class="demo-section">
        <h2 class="section-title">按钮 Buttons</h2>

        <!-- 实心按钮 -->
        <view class="button-group">
          <p class="group-label">实心按钮 (Filled)</p>
          <view class="button-row">
            <md3-button variant="filled">默认</md3-button>
            <md3-button variant="filled" disabled>禁用</md3-button>
            <md3-button variant="filled" :icon="true">
              <template #icon>
                <svg width="18" height="18" viewBox="0 0 24 24" fill="currentColor">
                  <path d="M19 19H5V5h7V3H5c-1.11 0-2 .9-2 2v14c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2v-7h-2v7zM14 3v2h3.59l-9.83 9.83 1.41 1.41L19 6.41V10h2V3h-7z"/>
                </svg>
              </template>
              带图标
            </md3-button>
          </view>
        </view>

        <!-- 边框按钮 -->
        <view class="button-group">
          <p class="group-label">边框按钮 (Outlined)</p>
          <view class="button-row">
            <md3-button variant="outlined">默认</md3-button>
            <md3-button variant="outlined" disabled>禁用</md3-button>
            <md3-button variant="outlined">次要操作</md3-button>
          </view>
        </view>

        <!-- 文字按钮 -->
        <view class="button-group">
          <p class="group-label">文字按钮 (Text)</p>
          <view class="button-row">
            <md3-button variant="text">默认</md3-button>
            <md3-button variant="text" disabled>禁用</md3-button>
            <md3-button variant="text">取消</md3-button>
          </view>
        </view>

        <!-- FAB -->
        <view class="button-group">
          <p class="group-label">悬浮按钮 (FAB)</p>
          <view class="button-row">
            <button class="md-fab" aria-label="add">
              <span class="md-fab-icon">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                  <path d="M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z"/>
                </svg>
              </span>
            </button>
            <button class="md-fab md-fab--extended">
              <span class="md-fab-icon">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                  <path d="M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z"/>
                </svg>
              </span>
              创建
            </button>
          </view>
        </view>

        <!-- 图标按钮 -->
        <view class="button-group">
          <p class="group-label">图标按钮 (Icon Button)</p>
          <view class="button-row">
            <button class="md-icon-button">
              <svg class="md-icon-button__icon" viewBox="0 0 24 24" fill="currentColor">
                <path d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z"/>
              </svg>
            </button>
            <button class="md-icon-button md-icon-button--filled">
              <svg class="md-icon-button__icon" viewBox="0 0 24 24" fill="currentColor">
                <path d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z"/>
              </svg>
            </button>
            <button class="md-icon-button md-icon-button--tonal">
              <svg class="md-icon-button__icon" viewBox="0 0 24 24" fill="currentColor">
                <path d="M17 10.5V7c0-.55-.45-1-1-1H4c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1v-3.5l4 4v-11l-4 4z"/>
              </svg>
            </button>
          </view>
        </view>
      </section>

      <!-- 输入框部分 -->
      <section class="demo-section">
        <h2 class="section-title">输入框 Text Fields</h2>

        <view class="input-group">
          <p class="group-label">轮廓式输入框 (Outlined)</p>
          <md3-input
            v-model="inputValue"
            label="用户名"
            placeholder="请输入用户名"
            variant="outlined"
          />
        </view>

        <view class="input-group">
          <p class="group-label">带图标的输入框</p>
          <md3-input
            v-model="searchValue"
            label="搜索"
            placeholder="搜索内容..."
            variant="outlined"
            :prefix-icon="true"
          >
            <template #prefix-icon>
              <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
                <path d="M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z"/>
              </svg>
            </template>
          </md3-input>
        </view>

        <view class="input-group">
          <p class="group-label">带错误状态的输入框</p>
          <md3-input
            v-model="emailValue"
            label="邮箱"
            placeholder="example@email.com"
            variant="outlined"
            :error="emailError"
            error-message="请输入有效的邮箱地址"
          />
        </view>

        <view class="input-group">
          <p class="group-label">带字符计数的输入框</p>
          <md3-input
            v-model="messageValue"
            label="留言"
            placeholder="请输入留言内容..."
            variant="outlined"
            :counter="100"
            rows="3"
            type="textarea"
          />
        </view>
      </section>

      <!-- 卡片部分 -->
      <section class="demo-section">
        <h2 class="section-title">卡片 Cards</h2>

        <!-- 轮廓式卡片 -->
        <view class="card-group">
          <p class="group-label">轮廓式卡片 (Outlined)</p>
          <md3-card
            variant="outlined"
            title="故事森林"
            subtitle="让孩子爱上阅读的魔法世界"
          >
            这是一个专为儿童设计的互动故事阅读平台，包含海量优质故事、AI 个性化推荐、互动答题和角色扮演配音功能。
            <template #actions>
              <md3-button variant="text">收藏</md3-button>
              <md3-button variant="filled">开始阅读</md3-button>
            </template>
          </md3-card>
        </view>

        <!-- 实心卡片 -->
        <view class="card-group">
          <p class="group-label">实心卡片 (Elevated)</p>
          <md3-card
            variant="elevated"
            title="阅读记录"
            subtitle="本周阅读统计"
          >
            <view class="stats-row">
              <view class="stat-item">
                <span class="stat-value">5</span>
                <span class="stat-label">阅读天数</span>
              </view>
              <view class="stat-item">
                <span class="stat-value">180</span>
                <span class="stat-label">总分钟</span>
              </view>
              <view class="stat-item">
                <span class="stat-value">12</span>
                <span class="stat-label">完成故事</span>
              </view>
            </view>
            <template #actions>
              <md3-button variant="text">查看详情</md3-button>
            </template>
          </md3-card>
        </view>

        <!-- 填充式卡片 -->
        <view class="card-group">
          <p class="group-label">填充式卡片 (Filled)</p>
          <md3-card
            variant="filled"
            title="VIP 会员"
            subtitle="解锁全部内容"
          >
            成为 VIP 会员，享受更多特权：
            <ul class="feature-list">
              <li>无限制访问所有故事</li>
              <li>专属角色音效</li>
              <li>离线下载</li>
              <li>成长报告导出</li>
            </ul>
            <template #actions>
              <md3-button variant="contained" full-width>立即升级</md3-button>
            </template>
          </md3-card>
        </view>

        <!-- 带媒体的卡片 -->
        <view class="card-group">
          <p class="group-label">带媒体的卡片</p>
          <md3-card
            variant="outlined"
            title="安徒生童话"
            subtitle="经典童话系列"
            media-aspect-ratio="wide"
          >
            <template #media>
              <view class="card-media-placeholder">
                <svg width="48" height="48" viewBox="0 0 24 24" fill="currentColor" opacity="0.3">
                  <path d="M21 19V5c0-1.1-.9-2-2-2H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2zM8.5 13.5l2.5 3.01L14.5 12l4.5 6H5l3.5-4.5z"/>
                </svg>
                <span>封面图片</span>
              </view>
            </template>
            <template #actions>
              <md3-button variant="text">详情</md3-button>
              <md3-button variant="outlined">播放</md3-button>
            </template>
          </md3-card>
        </view>
      </section>

      <!-- 对话框部分 -->
      <section class="demo-section">
        <h2 class="section-title">对话框 Dialog</h2>

        <view class="button-row">
          <md3-button variant="filled" @click="showDialog = true">打开对话框</md3-button>
        </view>

        <!-- 对话框遮罩 -->
        <view v-if="showDialog" class="md-dialog-overlay" @click="showDialog = false"></view>

        <!-- 对话框 -->
        <view v-if="showDialog" class="md-dialog" role="dialog" aria-modal="true">
          <h3 class="md-dialog__title">提示</h3>
          <div class="md-dialog__content">
            这是一个 Material Design 3 风格的对话框组件。
            点击按钮可以执行相应的操作。
          </div>
          <div class="md-dialog__actions">
            <md3-button variant="text" @click="showDialog = false">取消</md3-button>
            <md3-button variant="filled" @click="handleDialogConfirm">确定</md3-button>
          </div>
        </view>
      </section>

      <!-- 色彩展示 -->
      <section class="demo-section">
        <h2 class="section-title">色彩系统 Color Palette</h2>

        <view class="color-grid">
          <view class="color-item">
            <div class="color-swatch" style="background-color: #2D6A4F;"></div>
            <span class="color-label">Primary</span>
            <span class="color-value">#2D6A4F</span>
          </view>
          <view class="color-item">
            <div class="color-swatch" style="background-color: #FF8C42;"></div>
            <span class="color-label">Secondary</span>
            <span class="color-value">#FF8C42</span>
          </view>
          <view class="color-item">
            <div class="color-swatch" style="background-color: #4ECDC4;"></div>
            <span class="color-label">Tertiary</span>
            <span class="color-value">#4ECDC4</span>
          </view>
          <view class="color-item">
            <div class="color-swatch" style="background-color: #F44336;"></div>
            <span class="color-label">Error</span>
            <span class="color-value">#F44336</span>
          </view>
          <view class="color-item">
            <div class="color-swatch" style="background-color: #FFFBF0;"></div>
            <span class="color-label">Surface</span>
            <span class="color-value">#FFFBF0</span>
          </view>
          <view class="color-item">
            <div class="color-swatch" style="background-color: #1B4332;"></div>
            <span class="color-label">On Surface</span>
            <span class="color-value">#1B4332</span>
          </view>
        </view>
      </section>
    </view>
  </view>
</template>

<script>
import Md3Button from '@/components/md3-button/md3-button.vue'
import Md3Input from '@/components/md3-input/md3-input.vue'
import Md3Card from '@/components/md3-card/md3-card.vue'

export default {
  name: 'Md3Demo',
  components: {
    Md3Button,
    Md3Input,
    Md3Card
  },
  data() {
    return {
      inputValue: '',
      searchValue: '',
      emailValue: '',
      emailError: false,
      messageValue: '',
      showDialog: false
    }
  },
  methods: {
    handleDialogConfirm() {
      this.showDialog = false
      uni.showToast({ title: '已确认', icon: 'success' })
    }
  }
}
</script>

<style lang="scss">
@import '@/styles/md3-global.scss';

.container {
  min-height: 100vh;
  background-color: $md-surface;
}

.md3-demo {
  padding: 24px 16px 48px;
  max-width: 800px;
  margin: 0 auto;
}

// 页面头部
.page-header {
  margin-bottom: 32px;
  padding-bottom: 16px;
  border-bottom: 1px solid $md-outline-variant;
}

.page-title {
  @include md-display-small;
  color: $md-on-surface;
  margin-bottom: 8px;
}

.page-subtitle {
  @include md-body-large;
  color: $md-on-surface-variant;
}

// 演示区块
.demo-section {
  margin-bottom: 40px;

  .section-title {
    @include md-title-large;
    color: $md-on-surface;
    margin-bottom: 16px;
    padding-bottom: 8px;
    border-bottom: 2px solid $md-primary;
  }
}

// 组标签
.group-label {
  @include md-label-medium;
  color: $md-on-surface-variant;
  margin-bottom: 12px;
}

// 按钮行
.button-row {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-bottom: 20px;
}

.button-group {
  margin-bottom: 24px;
}

// 输入框组
.input-group {
  margin-bottom: 20px;
}

// 卡片组
.card-group {
  margin-bottom: 24px;
}

// 统计行
.stats-row {
  display: flex;
  justify-content: space-around;
  padding: 16px 0;
}

.stat-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;

  .stat-value {
    @include md-headline-medium;
    color: $md-primary;
  }

  .stat-label {
    @include md-label-small;
    color: $md-on-surface-variant;
  }
}

// 特性列表
.feature-list {
  list-style: none;
  padding: 0;
  margin: 16px 0;

  li {
    @include md-body-medium;
    color: $md-on-surface-variant;
    padding: 8px 0;
    padding-left: 24px;
    position: relative;

    &::before {
      content: '✓';
      position: absolute;
      left: 0;
      color: $md-primary;
      font-weight: 600;
    }
  }
}

// 卡片媒体占位符
.card-media-placeholder {
  width: 100%;
  height: 100%;
  min-height: 120px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 8px;
  background-color: $md-surface-container;
  color: $md-on-surface-variant;
}

// 对话框样式
.md-dialog-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 1000;
  display: flex;
  align-items: center;
  justify-content: center;
}

.md-dialog {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: calc(100% - 48px);
  max-width: 400px;
  padding: 24px;
  background-color: $md-surface-container-high;
  border-radius: $md-radius-xxl;
  z-index: 1001;
  @include md-elevation-3;

  &__title {
    @include md-headline-small;
    color: $md-on-surface;
    margin: 0 0 16px 0;
  }

  &__content {
    @include md-body-medium;
    color: $md-on-surface-variant;
    line-height: 1.5;
    margin-bottom: 24px;
  }

  &__actions {
    display: flex;
    justify-content: flex-end;
    gap: 8px;
  }
}

// 色彩网格
.color-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
  gap: 16px;
}

.color-item {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.color-swatch {
  width: 100%;
  height: 80px;
  border-radius: $md-radius-md;
  @include md-elevation-1;
}

.color-label {
  @include md-label-medium;
  color: $md-on-surface;
}

.color-value {
  @include md-label-small;
  color: $md-on-surface-variant;
  text-transform: uppercase;
}

// 响应式调整
@media (max-width: 599px) {
  .md3-demo {
    padding: 16px 12px;
  }

  .page-title {
    @include md-headline-large;
  }

  .color-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>
