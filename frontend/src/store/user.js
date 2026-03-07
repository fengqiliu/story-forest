/**
 * 用户状态管理 (Pinia)
 */
import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({
    userInfo: null,
    token: '',
    isLogin: false,
    ageGroup: null
  }),

  getters: {
    // 是否已设置年龄段
    hasAgeGroup: (state) => !!state.ageGroup,

    // 是否 VIP 用户
    isVip: (state) => state.userInfo?.isVip || false,

    // 用户昵称
    nickname: (state) => state.userInfo?.nickname || '小书虫',

    // 用户头像
    avatar: (state) => state.userInfo?.avatar || ''
  },

  actions: {
    // 初始化用户状态
    init() {
      const token = uni.getStorageSync('token')
      const userInfo = uni.getStorageSync('userInfo')

      if (token && userInfo) {
        this.token = token
        this.userInfo = userInfo
        this.isLogin = true
        this.ageGroup = userInfo.ageGroup
      }
    },

    // 设置用户信息
    setUserInfo(userInfo, token) {
      this.userInfo = userInfo
      this.token = token
      this.isLogin = true
      this.ageGroup = userInfo.ageGroup

      // 保存到本地存储
      uni.setStorageSync('token', token)
      uni.setStorageSync('userInfo', userInfo)
    },

    // 更新用户信息
    updateUserInfo(partialInfo) {
      if (this.userInfo) {
        this.userInfo = { ...this.userInfo, ...partialInfo }
        uni.setStorageSync('userInfo', this.userInfo)

        if (partialInfo.ageGroup) {
          this.ageGroup = partialInfo.ageGroup
        }
      }
    },

    // 设置年龄段
    setAgeGroup(ageGroup) {
      this.ageGroup = ageGroup
      if (this.userInfo) {
        this.userInfo.ageGroup = ageGroup
        uni.setStorageSync('userInfo', this.userInfo)
      }
    },

    // 退出登录
    logout() {
      this.userInfo = null
      this.token = ''
      this.isLogin = false
      this.ageGroup = null

      uni.removeStorageSync('token')
      uni.removeStorageSync('userInfo')
    },

    // 清除本地用户信息 (用于切换账号)
    clearLocalUserInfo() {
      uni.removeStorageSync('token')
      uni.removeStorageSync('userInfo')
    }
  }
})
