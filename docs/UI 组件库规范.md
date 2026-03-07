# UI 组件库规范 - 基于 uView Plus 定制

**版本：** 1.0
**更新日期：** 2026-03-05

---

## 一、设计系统基础

### 1.1 色彩系统

#### 主色调

```scss
// 森林绿 - 品牌主色
$primary-50: #E8F5E9;
$primary-100: #C8E6C9;
$primary-200: #A5D6A7;
$primary-300: #81C784;
$primary-400: #66BB6A;
$primary-500: #2D6A4F;  // 主色
$primary-600: #1B4332;
$primary-700: #081C15;

// 暖橙色 - 强调色
$secondary-50: #FFF3E0;
$secondary-100: #FFE0B2;
$secondary-200: #FFCC80;
$secondary-300: #FFB74D;
$secondary-400: #FFA726;
$secondary-500: #FF8C42;  // 主色
$secondary-600: #F57C00;
$secondary-700: #E65100;

// 天空蓝 - 辅助色
$accent-50: #E0F7FA;
$accent-100: #B2EBF2;
$accent-200: #80DEEA;
$accent-300: #4DD0E1;
$accent-400: #26C6DA;
$accent-500: #4ECDC4;  // 主色
$accent-600: #00ACC1;
$accent-700: #00838F;
```

#### 中性色

```scss
// 背景色
$bg-primary: #FFFBF0;     // 奶油白 - 主背景
$bg-secondary: #FFFFFF;   // 纯白 - 卡片背景
$bg-tertiary: #F0F7F4;    // 浅绿 - 装饰背景
$bg-overlay: rgba(0, 0, 0, 0.5);  // 遮罩

// 文字色
$text-primary: #1B4332;    // 主标题
$text-secondary: #52796F;  // 副标题/正文
$text-hint: #94B49F;       // 提示文字
$text-disabled: #C8D5C9;   // 禁用文字
$text-inverse: #FFFFFF;    // 反色文字
```

#### 功能色

```scss
// 状态色
$success: #4CAF50;
$warning: #FFC107;
$error: #F44336;
$info: #2196F3;

// 渐变色
$gradient-primary: linear-gradient(135deg, #2D6A4F 0%, #1B4332 100%);
$gradient-secondary: linear-gradient(135deg, #FF8C42 0%, #F57C00 100%);
$gradient-accent: linear-gradient(135deg, #4ECDC4 0%, #00ACC1 100%);
```

---

### 1.2 字体系统

```scss
// 字体家族
$font-family-base: 'PingFang SC', 'Helvetica Neue', Helvetica, 'microsoft yahei', arial, sans-serif;
$font-family-number: 'DIN Alternate', 'Roboto', sans-serif;

// 字体大小 (基于 14px 基准)
$font-size-xs: 10px;    // 标签/角标
$font-size-sm: 12px;    // 辅助文字
$font-size-base: 14px;  // 正文
$font-size-md: 16px;    // 小标题
$font-size-lg: 18px;    // 副标题
$font-size-xl: 24px;    // 主标题
$font-size-xxl: 32px;   // 大标题
$font-size-xxxl: 48px;  // 展示大字

// 字重
$font-weight-light: 300;
$font-weight-regular: 400;
$font-weight-medium: 500;
$font-weight-semibold: 600;
$font-weight-bold: 700;

// 行高
$line-height-none: 1;
$line-height-tight: 1.25;
$line-height-base: 1.5;
$line-height-relaxed: 1.75;
```

#### 字体使用规范

| 场景 | 大小 | 字重 | 行高 | 颜色 |
|------|------|------|------|------|
| 页面标题 | 24px | 600 | 1.25 | text-primary |
| 卡片标题 | 18px | 600 | 1.4 | text-primary |
| 正文 | 16px | 400 | 1.6 | text-secondary |
| 辅助文字 | 14px | 400 | 1.5 | text-hint |
| 按钮文字 | 16px | 500 | 1 | text-inverse |
| 数字展示 | 32px | 700 | 1 | secondary-500 |

---

### 1.3 间距系统

```scss
// 基础间距单位 (4px)
$spacing-unit: 4px;

$spacing-xs: 4px;     // 1 倍
$spacing-sm: 8px;     // 2 倍
$spacing-base: 12px;  // 3 倍
$spacing-md: 16px;    // 4 倍
$spacing-lg: 24px;    // 6 倍
$spacing-xl: 32px;    // 8 倍
$spacing-xxl: 48px;   // 12 倍

// 页面边距
$page-padding: 16px;
$card-padding: 16px;

// 元素间距
$gap-xs: 4px;
$gap-sm: 8px;
$gap-base: 12px;
$gap-md: 16px;
$gap-lg: 24px;
```

---

### 1.4 圆角系统

```scss
$radius-none: 0;
$radius-sm: 4px;      // 小按钮/标签
$radius-base: 8px;    // 卡片/输入框
$radius-md: 12px;     // 大卡片
$radius-lg: 16px;     // 弹窗
$radius-xl: 24px;     // 大按钮
$radius-full: 9999px; // 圆形/胶囊
```

---

### 1.5 阴影系统

```scss
// 基础阴影
$shadow-sm: 0 1px 2px rgba(0, 0, 0, 0.05);
$shadow-base: 0 2px 8px rgba(0, 0, 0, 0.08);
$shadow-md: 0 4px 16px rgba(0, 0, 0, 0.1);
$shadow-lg: 0 8px 32px rgba(0, 0, 0, 0.12);
$shadow-xl: 0 16px 48px rgba(0, 0, 0, 0.15);

// 彩色阴影
$shadow-primary: 0 4px 16px rgba(45, 106, 79, 0.3);
$shadow-secondary: 0 4px 16px rgba(255, 140, 66, 0.3);
$shadow-accent: 0 4px 16px rgba(78, 205, 196, 0.3);

// 悬浮阴影
$shadow-hover: 0 6px 20px rgba(0, 0, 0, 0.12);
$shadow-active: 0 2px 4px rgba(0, 0, 0, 0.06);
```

---

## 二、基础组件

### 2.1 Button 按钮

```html
<!-- 主按钮 -->
<u-button type="primary" size="large" shape="round">
  开始阅读
</u-button>

<!-- 次级按钮 -->
<u-button type="info" size="normal" plain>
  加入收藏
</u-button>

<!-- 文字按钮 -->
<u-button type="info" text>
  查看更多
</u-button>

<!-- 图标按钮 -->
<u-button icon="heart" shape="circle" />
```

#### 按钮变体

| 类型 | 用途 | 颜色 |
|------|------|------|
| primary | 主要操作 | primary-500 |
| info | 次要操作 | accent-500 |
| warning | 警示操作 | warning |
| error | 危险操作 | error |
| plain | 边框按钮 | transparent + border |

#### 按钮尺寸

| 尺寸 | 高度 | 字号 | 使用场景 |
|------|------|------|----------|
| large | 50px | 18px | 页面主操作 |
| normal | 40px | 16px | 常规操作 |
| small | 32px | 14px | 行内操作 |
| mini | 24px | 12px | 标签/角标 |

---

### 2.2 Card 卡片

```html
<!-- 故事卡片 -->
<u-card :bordered="false" :shadow="true" class="story-card">
  <template #image>
    <image :src="story.cover" mode="aspectFill" />
    <u-tag v-if="story.isNew" text="NEW" type="info" size="mini" />
  </template>

  <template #title>
    <text class="story-title">{{ story.title }}</text>
  </template>

  <template #subtitle>
    <view class="story-meta">
      <u-icon name="star" size="14" color="#FF8C42" />
      <text>{{ story.rating }}</text>
      <u-icon name="account" size="14" />
      <text>{{ story.ageGroup }}</text>
    </view>
  </template>
</u-card>
```

#### 卡片规格

```scss
.story-card {
  width: 160px;
  border-radius: $radius-md;
  overflow: hidden;

  image {
    width: 100%;
    height: 120px;
  }

  .story-title {
    font-size: 16px;
    font-weight: 600;
    line-height: 1.4;
  }

  .story-meta {
    display: flex;
    align-items: center;
    gap: 4px;
    font-size: 12px;
    color: $text-hint;
  }
}
```

---

### 2.3 Input 输入框

```html
<!-- 搜索框 -->
<u-search
  v-model="keyword"
  placeholder="搜索故事/角色/主题"
  shape="round"
  bg-color="#F0F7F4"
  @search="onSearch"
/>

<!-- 文本输入 -->
<u-input
  v-model="value"
  type="text"
  placeholder="请输入"
  :border="false"
  clearable
/>

<!-- 密码输入 -->
<u-input
  v-model="password"
  type="password"
  placeholder="请输入密码"
  :password-icon="true"
/>
```

---

### 2.4 Loading 加载

```html
<!-- 页面加载 -->
<u-loading-page :loading="loading" loading-text="加载中..." />

<!-- 局部加载 -->
<u-loading-icon size="24" color="#2D6A4F" />

<!-- 骨架屏 -->
<u-skeleton :loading="loading" :rows="3" />
```

#### 加载动画

```scss
// 自定义加载动画
.loading-dots {
  display: flex;
  gap: 4px;

  .dot {
    width: 8px;
    height: 8px;
    border-radius: 50%;
    background: $primary-500;
    animation: bounce 1.4s infinite ease-in-out;

    &:nth-child(1) { animation-delay: -0.32s; }
    &:nth-child(2) { animation-delay: -0.16s; }
  }
}

@keyframes bounce {
  0%, 80%, 100% { transform: scale(0); }
  40% { transform: scale(1); }
}
```

---

### 2.5 Toast 提示

```javascript
// 成功提示
uToast.success({ title: '收藏成功' });

// 失败提示
uToast.error({ title: '网络开小差了' });

// 警告提示
uToast.warning({ title: '请先选择年龄' });

// 自定义
uToast.show({
  title: '答题正确!',
  icon: 'checkmark-circle',
  duration: 2000
});
```

---

### 2.6 Dialog 弹窗

```html
<!-- 确认弹窗 -->
<u-modal
  v-model="showModal"
  title="提示"
  content="确定要退出阅读吗？"
  :show-cancel-button="true"
  confirm-color="#2D6A4F"
  @confirm="onConfirm"
  @cancel="onCancel"
/>

<!-- 自定义弹窗 -->
<u-popup v-model="showPopup" round="16">
  <view class="custom-popup">
    <!-- 自定义内容 -->
  </view>
</u-popup>
```

---

## 三、业务组件

### 3.1 StoryCard 故事卡片

```vue
<template>
  <view class="story-card" @click="handleClick">
    <view class="card-image-wrapper">
      <image
        class="card-image"
        :src="story.cover"
        mode="aspectFill"
      />
      <view v-if="story.isVip" class="vip-tag">VIP</view>
      <view v-if="story.isNew" class="new-tag">NEW</view>
    </view>

    <view class="card-content">
      <text class="card-title">{{ story.title }}</text>
      <view class="card-meta">
        <u-icon name="star" size="12" color="#FF8C42" />
        <text class="card-rating">{{ story.rating }}</text>
        <text class="card-age">{{ story.ageGroup }}</text>
      </view>
    </view>

    <view v-if="showProgress" class="card-progress">
      <u-progress
        :percent="story.progress"
        :show-text="false"
        height="4"
      />
    </view>
  </view>
</template>

<script>
export default {
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
      this.$emit('click', this.story);
    }
  }
}
</script>

<style scoped lang="scss">
.story-card {
  width: 160px;
  background: $bg-secondary;
  border-radius: $radius-md;
  overflow: hidden;
  box-shadow: $shadow-base;

  .card-image-wrapper {
    position: relative;

    .card-image {
      width: 100%;
      height: 120px;
    }

    .vip-tag, .new-tag {
      position: absolute;
      top: 8px;
      right: 8px;
      padding: 2px 8px;
      font-size: 10px;
      color: #fff;
      border-radius: $radius-sm;
    }

    .vip-tag {
      background: linear-gradient(135deg, #FFD700, #FFA500);
    }

    .new-tag {
      background: $secondary-500;
    }
  }

  .card-content {
    padding: $spacing-sm;

    .card-title {
      display: block;
      font-size: 15px;
      font-weight: 600;
      color: $text-primary;
      line-height: 1.4;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }

    .card-meta {
      display: flex;
      align-items: center;
      gap: 4px;
      margin-top: 4px;

      .card-rating {
        font-size: 12px;
        color: $text-secondary;
      }

      .card-age {
        font-size: 12px;
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
```

---

### 3.2 ChapterItem 章节项

```vue
<template>
  <view
    class="chapter-item"
    :class="{ locked: isLocked, completed: isCompleted }"
    @click="handleClick"
  >
    <view class="chapter-icon">
      <u-icon
        v-if="isCompleted"
        name="checkmark-circle"
        size="24"
        color="#4CAF50"
      />
      <u-icon
        v-else-if="isLocked"
        name="lock-fill"
        size="20"
        color="#94B49F"
      />
      <u-icon
        v-else
        name="play-circle-fill"
        size="24"
        color="#2D6A4F"
      />
    </view>

    <view class="chapter-info">
      <text class="chapter-title">{{ chapter.title }}</text>
      <view class="chapter-meta">
        <u-icon name="clock" size="12" />
        <text>{{ formatDuration(chapter.duration) }}</text>
        <text v-if="isLocked" class="lock-hint">答对解锁</text>
      </view>
    </view>

    <view class="chapter-action">
      <u-icon name="arrow-right" size="16" color="#94B49F" />
    </view>
  </view>
</template>

<script>
export default {
  props: {
    chapter: Object,
    isLocked: Boolean,
    isCompleted: Boolean
  },
  methods: {
    formatDuration(seconds) {
      const m = Math.floor(seconds / 60);
      const s = seconds % 60;
      return `${m}:${s.toString().padStart(2, '0')}`;
    },
    handleClick() {
      if (!this.isLocked) {
        this.$emit('click', this.chapter);
      }
    }
  }
}
</script>

<style scoped lang="scss">
.chapter-item {
  display: flex;
  align-items: center;
  padding: $spacing-md;
  background: $bg-secondary;
  border-radius: $radius-base;
  margin-bottom: $spacing-sm;

  &.locked {
    opacity: 0.6;
  }

  &.completed {
    background: $bg-tertiary;
  }

  .chapter-icon {
    margin-right: $spacing-sm;
  }

  .chapter-info {
    flex: 1;

    .chapter-title {
      display: block;
      font-size: 15px;
      color: $text-primary;
    }

    .chapter-meta {
      display: flex;
      align-items: center;
      gap: 4px;
      font-size: 12px;
      color: $text-hint;
      margin-top: 4px;

      .lock-hint {
        margin-left: 8px;
      }
    }
  }

  .chapter-action {
    margin-left: $spacing-sm;
  }
}
</style>
```

---

### 3.3 AudioPlayer 音频播放器

```vue
<template>
  <view class="audio-player">
    <view class="player-controls">
      <u-icon
        name="skip-backward"
        size="24"
        @click="handlePrevious"
      />

      <u-icon
        :name="isPlaying ? 'pause-circle-fill' : 'play-circle-fill'"
        size="48"
        color="#2D6A4F"
        @click="togglePlay"
      />

      <u-icon
        name="skip-forward"
        size="24"
        @click="handleNext"
      />
    </view>

    <view class="player-progress">
      <text class="time-current">{{ formatTime(currentTime) }}</text>
      <slider
        :value="progress"
        :max="100"
        active-color="#2D6A4F"
        block-size="16"
        @change="onProgressChange"
      />
      <text class="time-total">{{ formatTime(duration) }}</text>
    </view>

    <view class="player-actions">
      <u-button text icon="mic" size="small" @click="handleDubbing">
        配音
      </u-button>
      <u-button text icon="star" size="small" @click="handleQuiz">
        答题
      </u-button>
      <u-button text icon="clock" size="small" @click="handleTimer">
        定时
      </u-button>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      isPlaying: false,
      currentTime: 0,
      duration: 0,
      progress: 0
    };
  },
  methods: {
    togglePlay() {
      this.isPlaying = !this.isPlaying;
      this.$emit(this.isPlaying ? 'play' : 'pause');
    },
    formatTime(seconds) {
      const m = Math.floor(seconds / 60);
      const s = Math.floor(seconds % 60);
      return `${m}:${s.toString().padStart(2, '0')}`;
    }
  }
}
</script>

<style scoped lang="scss">
.audio-player {
  background: $bg-secondary;
  padding: $spacing-md;
  border-radius: $radius-md;

  .player-controls {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 24px;
    margin-bottom: $spacing-md;
  }

  .player-progress {
    display: flex;
    align-items: center;
    gap: 8px;
    margin-bottom: $spacing-md;

    .time-current, .time-total {
      font-size: 12px;
      color: $text-hint;
      min-width: 40px;
    }

    .time-total {
      text-align: right;
    }
  }

  .player-actions {
    display: flex;
    justify-content: space-around;
  }
}
</style>
```

---

### 3.4 QuizOption 答题选项

```vue
<template>
  <view
    class="quiz-option"
    :class="{ selected: isSelected, correct: isCorrect, wrong: isWrong }"
    @click="handleSelect"
  >
    <view class="option-key">{{ option.key }}</view>
    <view class="option-content">{{ option.value }}</view>
    <view class="option-icon">
      <u-icon
        v-if="isCorrect"
        name="checkmark"
        size="20"
        color="#4CAF50"
      />
      <u-icon
        v-if="isWrong"
        name="close"
        size="20"
        color="#F44336"
      />
    </view>
  </view>
</template>

<script>
export default {
  props: {
    option: Object,
    isSelected: Boolean,
    isCorrect: Boolean,
    isWrong: Boolean
  },
  methods: {
    handleSelect() {
      this.$emit('select', this.option.key);
    }
  }
}
</script>

<style scoped lang="scss">
.quiz-option {
  display: flex;
  align-items: center;
  padding: $spacing-md;
  background: $bg-secondary;
  border: 2px solid transparent;
  border-radius: $radius-base;
  margin-bottom: $spacing-sm;
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
    width: 32px;
    height: 32px;
    display: flex;
    align-items: center;
    justify-content: center;
    background: $bg-tertiary;
    border-radius: 50%;
    font-size: 14px;
    font-weight: 600;
    color: $text-secondary;
    margin-right: $spacing-sm;
  }

  .option-content {
    flex: 1;
    font-size: 16px;
    color: $text-primary;
  }

  .option-icon {
    margin-left: $spacing-sm;
  }
}
</style>
```

---

### 3.5 BadgeIcon 勋章图标

```vue
<template>
  <view class="badge-icon" :class="{ locked: isLocked }">
    <view class="badge-image">
      <image v-if="!isLocked" :src="badge.icon" mode="aspectFit" />
      <u-icon v-else name="lock-fill" size="24" color="#94B49F" />
    </view>
    <text class="badge-name">{{ badge.name }}</text>
    <text v-if="badge.count" class="badge-count">{{ badge.count }}</text>
  </view>
</template>

<script>
export default {
  props: {
    badge: Object,
    isLocked: Boolean
  }
}
</script>

<style scoped lang="scss">
.badge-icon {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 80px;

  &.locked {
    opacity: 0.5;
  }

  .badge-image {
    width: 48px;
    height: 48px;
    display: flex;
    align-items: center;
    justify-content: center;
    background: linear-gradient(135deg, $primary-100, $primary-50);
    border-radius: 50%;
    margin-bottom: 4px;

    image {
      width: 32px;
      height: 32px;
    }
  }

  .badge-name {
    font-size: 11px;
    color: $text-secondary;
    text-align: center;
  }

  .badge-count {
    font-size: 10px;
    color: $secondary-500;
    margin-top: 2px;
  }
}
</style>
```

---

### 3.6 ParentVerify 家长验证

```vue
<template>
  <u-popup v-model="visible" round="20">
    <view class="parent-verify">
      <view class="verify-header">
        <u-icon name="lock-closed" size="32" color="#2D6A4F" />
        <text class="verify-title">家长验证</text>
      </view>

      <view class="verify-question">
        <text class="question-text">{{ question }}</text>
      </view>

      <u-input
        v-model="answer"
        type="number"
        placeholder="请输入答案"
        :border="false"
        @confirm="handleSubmit"
      />

      <view class="verify-actions">
        <u-button text @click="handleCancel">取消</u-button>
        <u-button type="primary" @click="handleSubmit">验证</u-button>
      </view>
    </view>
  </u-popup>
</template>

<script>
export default {
  data() {
    return {
      visible: false,
      question: '',
      answer: '',
      correctAnswer: 0,
      resolve: null
    };
  },
  methods: {
    show() {
      this.question = `${this.randomNumber()} + ${this.randomNumber()} = ?`;
      this.correctAnswer = this.parseQuestion(this.question);
      this.answer = '';
      this.visible = true;

      return new Promise(resolve => {
        this.resolve = resolve;
      });
    },
    randomNumber() {
      return Math.floor(Math.random() * 20) + 1;
    },
    parseQuestion(q) {
      const match = q.match(/(\d+) \+ (\d+)/);
      return parseInt(match[1]) + parseInt(match[2]);
    },
    handleSubmit() {
      const isCorrect = parseInt(this.answer) === this.correctAnswer;
      this.visible = false;
      this.resolve(isCorrect);
    },
    handleCancel() {
      this.visible = false;
      this.resolve(false);
    }
  }
}
</script>

<style scoped lang="scss">
.parent-verify {
  width: 300px;
  padding: $spacing-xl;

  .verify-header {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-bottom: $spacing-lg;

    .verify-title {
      margin-top: $spacing-sm;
      font-size: 18px;
      font-weight: 600;
      color: $text-primary;
    }
  }

  .verify-question {
    margin-bottom: $spacing-lg;

    .question-text {
      font-size: 24px;
      font-weight: 600;
      color: $text-primary;
      text-align: center;
    }
  }

  .verify-actions {
    display: flex;
    justify-content: space-between;
    margin-top: $spacing-lg;
  }
}
</style>
```

---

## 四、动画规范

### 4.1 过渡动画

```scss
// 页面切换
.page-enter-active,
.page-leave-active {
  transition: all 0.3s ease-out;
}

.page-enter {
  transform: translateX(100%);
  opacity: 0;
}

.page-leave-to {
  transform: translateX(-100%);
  opacity: 0;
}

// 弹窗
.popup-enter-active,
.popup-leave-active {
  transition: all 0.25s ease-out;
}

.popup-enter {
  transform: scale(0.9);
  opacity: 0;
}

.popup-leave-to {
  transform: scale(1.05);
  opacity: 0;
}

// 列表项
.list-enter-active,
.list-leave-active {
  transition: all 0.3s ease;
  max-height: 100px;
  overflow: hidden;
}

.list-enter {
  transform: translateX(-20px);
  opacity: 0;
}

.list-leave-to {
  transform: translateX(20px);
  opacity: 0;
}
```

---

### 4.2 微交互动画

```scss
// 按钮点击
.btn-active {
  animation: btnPress 0.15s ease-in-out;
}

@keyframes btnPress {
  0% { transform: scale(1); }
  50% { transform: scale(0.95); }
  100% { transform: scale(1); }
}

// 收藏爱心
.heart-beat {
  animation: heartBeat 0.4s cubic-bezier(0.34, 1.56, 0.64, 1);
}

@keyframes heartBeat {
  0% { transform: scale(1); }
  25% { transform: scale(1.3); }
  50% { transform: scale(0.8); }
  75% { transform: scale(1.15); }
  100% { transform: scale(1); }
}

// 答题正确 - 彩带
.confetti {
  animation: confettiFall 0.6s ease-out;
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

// 答题错误 - 摇头
.shake-head {
  animation: shake 0.3s ease-in-out;
}

@keyframes shake {
  0%, 100% { transform: translateX(0); }
  25% { transform: translateX(-8px); }
  75% { transform: translateX(8px); }
}

// 下拉刷新
.refresh-spin {
  animation: refreshSpin 0.5s ease-out;
}

@keyframes refreshSpin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
```

---

## 五、主题切换

### 5.1 夜间模式

```scss
// 夜间模式变量
.dark-mode {
  --bg-primary: #1A1A2E;
  --bg-secondary: #16213E;
  --bg-tertiary: #0F3460;

  --text-primary: #E8E8E8;
  --text-secondary: #A0A0A0;
  --text-hint: #666666;

  // 降低饱和度
  --primary-500: #1D5A47;
  --secondary-500: #E67E22;
}

// 切换方法
.toggleDarkMode() {
  const isDark = !this.isDark;
  this.isDark = isDark;

  if (isDark) {
    document.body.classList.add('dark-mode');
  } else {
    document.body.classList.remove('dark-mode');
  }

  // 保存到本地
  uni.setStorageSync('darkMode', isDark);
}
```

---

## 六、响应式适配

### 6.1 媒体查询

```scss
// 手机竖屏 (375px - 428px)
@media screen and (min-width: 375px) and (max-width: 428px) {
  .container {
    padding: $page-padding;
  }
}

// 平板竖屏 (768px - 1024px)
@media screen and (min-width: 768px) and (max-width: 1024px) {
  .container {
    max-width: 708px;
    margin: 0 auto;
    padding: 24px;
  }

  .story-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}

// 桌面 (1024px+)
@media screen and (min-width: 1024px) {
  .container {
    max-width: 960px;
    margin: 0 auto;
    padding: 32px;
  }

  .story-grid {
    grid-template-columns: repeat(4, 1fr);
  }
}
```

---

## 版本历史

| 版本 | 日期 | 更新内容 |
|------|------|----------|
| v1.0 | 2026-03-05 | 初始版本，完成基础组件和业务组件规范 |
