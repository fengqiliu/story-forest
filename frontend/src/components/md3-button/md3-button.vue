<template>
  <button
    :class="[
      'md-button',
      `md-button--${variant}`,
      `md-button--${size}`,
      {
        'md-disabled': disabled,
        'md-loading': loading,
        'md-full-width': fullWidth
      }
    ]"
    :disabled="disabled || loading"
    @click="handleClick"
  >
    <!-- 加载指示器 -->
    <span v-if="loading" class="md-button__loading-spinner"></span>

    <!-- 图标（前置） -->
    <span v-if="icon && !loading" class="md-button-icon">
      <slot name="icon"></slot>
    </span>

    <!-- 按钮内容 -->
    <span class="md-button__content">
      <slot></slot>
    </span>

    <!-- 图标（后置） -->
    <span v-if="iconAfter && !loading" class="md-button-icon">
      <slot name="icon-after"></slot>
    </span>
  </button>
</template>

<script>
export default {
  name: 'Md3Button',
  props: {
    // 按钮变体：filled, outlined, text, contained
    variant: {
      type: String,
      default: 'filled',
      validator: (value) => ['filled', 'outlined', 'text', 'contained'].includes(value)
    },
    // 按钮尺寸：small, medium, large
    size: {
      type: String,
      default: 'medium',
      validator: (value) => ['small', 'medium', 'large'].includes(value)
    },
    // 禁用状态
    disabled: {
      type: Boolean,
      default: false
    },
    // 加载状态
    loading: {
      type: Boolean,
      default: false
    },
    // 是否显示图标（前置）
    icon: {
      type: Boolean,
      default: false
    },
    // 是否显示图标（后置）
    iconAfter: {
      type: Boolean,
      default: false
    },
    // 是否全宽
    fullWidth: {
      type: Boolean,
      default: false
    }
  },
  emits: ['click'],
  methods: {
    handleClick(event) {
      if (!this.disabled && !this.loading) {
        this.$emit('click', event)
      }
    }
  }
}
</script>

<style lang="scss">
@import './md3-button.scss';

.md-button {
  // 全宽模式
  &.md-full-width {
    width: 100%;
  }
}
</style>
