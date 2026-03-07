/**
 * 存储工具函数
 */

const STORAGE_KEY = {
  TOKEN: 'token',
  USER_INFO: 'userInfo',
  AGE_GROUP: 'ageGroup',
  SETTINGS: 'settings'
}

export const storage = {
  /**
   * 设置存储
   */
  set(key, value) {
    try {
      uni.setStorageSync(key, value)
      return true
    } catch (error) {
      console.error('存储失败:', error)
      return false
    }
  },

  /**
   * 获取存储
   */
  get(key, defaultValue = null) {
    try {
      const value = uni.getStorageSync(key)
      return value !== '' ? value : defaultValue
    } catch (error) {
      console.error('获取存储失败:', error)
      return defaultValue
    }
  },

  /**
   * 移除存储
   */
  remove(key) {
    try {
      uni.removeStorageSync(key)
      return true
    } catch (error) {
      console.error('移除存储失败:', error)
      return false
    }
  },

  /**
   * 清空所有存储
   */
  clear() {
    try {
      uni.clearStorageSync()
      return true
    } catch (error) {
      console.error('清空存储失败:', error)
      return false
    }
  },

  /**
   * 获取所有 keys
   */
  keys() {
    try {
      const info = uni.getStorageInfoSync()
      return info.keys || []
    } catch (error) {
      console.error('获取存储信息失败:', error)
      return []
    }
  }
}

/**
 * 认证相关便捷方法
 */
export const auth = {
  // 保存登录信息
  login(token, userInfo) {
    storage.set(STORAGE_KEY.TOKEN, token)
    storage.set(STORAGE_KEY.USER_INFO, userInfo)
  },

  // 获取 token
  getToken() {
    return storage.get(STORAGE_KEY.TOKEN)
  },

  // 获取用户信息
  getUserInfo() {
    return storage.get(STORAGE_KEY.USER_INFO)
  },

  // 是否已登录
  isLogin() {
    return !!this.getToken()
  },

  // 退出登录
  logout() {
    storage.remove(STORAGE_KEY.TOKEN)
    storage.remove(STORAGE_KEY.USER_INFO)
  },

  // 更新用户信息
  updateUserInfo(partialInfo) {
    const userInfo = this.getUserInfo()
    const updatedInfo = { ...userInfo, ...partialInfo }
    storage.set(STORAGE_KEY.USER_INFO, updatedInfo)
    return updatedInfo
  }
}

export default storage
