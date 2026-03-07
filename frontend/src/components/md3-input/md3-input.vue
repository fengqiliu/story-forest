<template>
  <div :class="['md-text-field', `md-text-field--${variant}`, `md-text-field--${size}`]">
    <!-- 标签 -->
    <label v-if="label" class="md-text-field__label">{{ label }}</label>

    <!-- 输入框容器 -->
    <div class="md-input-wrapper">
      <!-- 前置图标 -->
      <span v-if="prefixIcon" class="md-input__icon md-input__icon--prefix" @click="handlePrefixClick">
        <slot name="prefix-icon"></slot>
      </span>

      <!-- 输入框 -->
      <input
        v-if="type !== 'textarea'"
        :type="type"
        :value="modelValue"
        :placeholder="placeholder"
        :disabled="disabled"
        :readonly="readonly"
        class="md-input"
        :class="{ 'md-input--error': error }"
        @input="handleInput"
        @focus="$emit('focus', $event)"
        @blur="$emit('blur', $event)"
      />

      <!-- 文本域 -->
      <textarea
        v-else
        :value="modelValue"
        :placeholder="placeholder"
        :disabled="disabled"
        :readonly="readonly"
        class="md-input md-textarea"
        :class="{ 'md-input--error': error }"
        :rows="rows"
        @input="handleInput"
        @focus="$emit('focus', $event)"
        @blur="$emit('blur', $event)"
      />

      <!-- 浮动标签 -->
      <span v-if="label" class="md-input__label">{{ label }}</span>

      <!-- 后置图标 -->
      <span v-if="suffixIcon" class="md-input__icon md-input__icon--suffix" @click="handleSuffixClick">
        <slot name="suffix-icon"></slot>
      </span>

      <!-- 清除按钮 -->
      <span
        v-if="clearable && modelValue"
        class="md-input__icon md-input__icon--suffix"
        @click="handleClear"
      >
        <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
          <path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z"/>
        </svg>
      </span>
    </div>

    <!-- 辅助文字 -->
    <div v-if="helperText || counter" class="md-input__helper" :class="{ 'md-input__helper--error': error }">
      <span v-if="error && errorMessage">{{ errorMessage }}</span>
      <span v-else-if="helperText">{{ helperText }}</span>
      <span v-if="counter" class="md-input__helper--counter">
        {{ modelValue ? modelValue.length : 0 }}/{{ counter }}
      </span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Md3Input',
  props: {
    modelValue: {
      type: String,
      default: ''
    },
    type: {
      type: String,
      default: 'text'
    },
    label: {
      type: String,
      default: ''
    },
    placeholder: {
      type: String,
      default: ''
    },
    disabled: {
      type: Boolean,
      default: false
    },
    readonly: {
      type: Boolean,
      default: false
    },
    error: {
      type: Boolean,
      default: false
    },
    errorMessage: {
      type: String,
      default: ''
    },
    helperText: {
      type: String,
      default: ''
    },
    prefixIcon: {
      type: Boolean,
      default: false
    },
    suffixIcon: {
      type: Boolean,
      default: false
    },
    clearable: {
      type: Boolean,
      default: false
    },
    counter: {
      type: [Boolean, Number],
      default: false
    },
    rows: {
      type: Number,
      default: 4
    },
    variant: {
      type: String,
      default: 'outlined',
      validator: (value) => ['outlined', 'filled'].includes(value)
    },
    size: {
      type: String,
      default: 'medium',
      validator: (value) => ['small', 'medium', 'large'].includes(value)
    }
  },
  emits: ['update:modelValue', 'input', 'focus', 'blur', 'prefix-click', 'suffix-click', 'clear'],
  methods: {
    handleInput(event) {
      this.$emit('update:modelValue', event.target.value)
      this.$emit('input', event.target.value)
    },
    handlePrefixClick() {
      this.$emit('prefix-click')
    },
    handleSuffixClick() {
      this.$emit('suffix-click')
    },
    handleClear() {
      this.$emit('update:modelValue', '')
      this.$emit('clear')
    }
  }
}
</script>

<style lang="scss">
@import './md3-input.scss';
</style>
