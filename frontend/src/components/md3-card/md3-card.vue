<template>
  <div
    :class="[
      'md-card',
      `md-card--${variant}`,
      {
        'md-card--clickable': clickable,
        'md-card--selectable': selectable,
        'md-selected': selected
      }
    ]"
    @click="handleClick"
  >
    <!-- 媒体区域 -->
    <div v-if="$slots.media" :class="['md-card__media', `md-card__media--${mediaAspectRatio}`]">
      <slot name="media"></slot>
    </div>

    <!-- 头部（图标 + 标题） -->
    <div v-if="$slots.header" :class="['md-card__header', `md-card__header--${headerLayout}`]">
      <slot name="header"></slot>
    </div>

    <!-- 图标 -->
    <div v-if="icon" :class="['md-card__icon', { 'md-card__icon--small': iconSize === 'small' }]">
      <slot name="icon">
        <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
          <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-1 17.93c-3.95-.49-7-3.85-7-7.93 0-.62.08-1.21.21-1.79L9 15v1c0 1.1.9 2 2 2v1.93zm6.9-2.54c-.26-.81-1-1.39-1.9-1.39h-1v-3c0-.55-.45-1-1-1H8v-2h2c.55 0 1-.45 1-1V7h2c1.1 0 2-.9 2-2v-.41c2.93 1.19 5 4.06 5 7.41 0 2.08-.8 3.97-2.1 5.39z"/>
        </svg>
      </slot>
    </div>

    <!-- 内容区域 -->
    <div v-if="$slots.default" class="md-card__content">
      <!-- 标题 -->
      <h3 v-if="title" :class="['md-card__title', { 'md-card__title--large': titleSize === 'large' }]">
        {{ title }}
      </h3>

      <!-- 副标题 -->
      <p v-if="subtitle" class="md-card__subtitle">{{ subtitle }}</p>

      <!-- 支持文本 -->
      <p :class="['md-card__supporting-text', { 'md-card__supporting-text--large': supportingTextSize === 'large' }]">
        <slot></slot>
      </p>
    </div>

    <!-- 操作区域 -->
    <div v-if="$slots.actions" :class="['md-card__actions', { 'md-card__actions--vertical': actionsVertical }]">
      <slot name="actions"></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Md3Card',
  props: {
    // 卡片变体：outlined, elevated, filled, tonal
    variant: {
      type: String,
      default: 'outlined',
      validator: (value) => ['outlined', 'elevated', 'filled', 'tonal'].includes(value)
    },
    // 是否可点击
    clickable: {
      type: Boolean,
      default: false
    },
    // 是否可选择
    selectable: {
      type: Boolean,
      default: false
    },
    // 是否选中
    selected: {
      type: Boolean,
      default: false
    },
    // 标题
    title: {
      type: String,
      default: ''
    },
    // 标题尺寸
    titleSize: {
      type: String,
      default: 'medium',
      validator: (value) => ['medium', 'large'].includes(value)
    },
    // 副标题
    subtitle: {
      type: String,
      default: ''
    },
    // 支持文本尺寸
    supportingTextSize: {
      type: String,
      default: 'medium',
      validator: (value) => ['medium', 'large'].includes(value)
    },
    // 图标
    icon: {
      type: Boolean,
      default: false
    },
    // 图标尺寸
    iconSize: {
      type: String,
      default: 'medium',
      validator: (value) => ['small', 'medium'].includes(value)
    },
    // 媒体区域宽高比
    mediaAspectRatio: {
      type: String,
      default: 'wide',
      validator: (value) => ['square', 'wide', 'tall'].includes(value)
    },
    // 头部布局
    headerLayout: {
      type: String,
      default: 'horizontal',
      validator: (value) => ['horizontal', 'vertical'].includes(value)
    },
    // 操作区域是否垂直
    actionsVertical: {
      type: Boolean,
      default: false
    }
  },
  emits: ['click'],
  methods: {
    handleClick(event) {
      if (this.clickable || this.selectable) {
        this.$emit('click', event)
      }
    }
  }
}
</script>

<style lang="scss">
@import './md3-card.scss';
</style>
