/**
 * API 请求封装
 */

// 基础 API 地址
const BASE_URL = 'https://api.example.com'

// 请求拦截器
function requestInterceptor(config) {
  // 添加 token
  const token = uni.getStorageSync('token')
  if (token) {
    config.header = {
      ...config.header,
      'Authorization': `Bearer ${token}`
    }
  }
  return config
}

// 响应拦截器
function responseInterceptor(response) {
  const { statusCode, data } = response

  // HTTP 状态码处理
  if (statusCode !== 200) {
    handleError(statusCode)
    return Promise.reject(response)
  }

  // 业务状态码处理
  if (data.code !== 0) {
    uToast.error({ title: data.message || '请求失败' })

    // token 过期处理
    if (data.code === 401) {
      handleTokenExpired()
    }

    return Promise.reject(data)
  }

  return data
}

// 错误处理
function handleError(code) {
  const errorMessages = {
    400: '请求参数错误',
    401: '请先登录',
    403: '无权限访问',
    404: '请求的资源不存在',
    500: '服务器错误',
    502: '网关错误',
    503: '服务不可用'
  }

  const message = errorMessages[code] || `网络错误 (${code})`
  uToast.error({ title: message })
}

// Token 过期处理
function handleTokenExpired() {
  // 清除本地缓存
  uni.removeStorageSync('token')
  uni.removeStorageSync('userInfo')

  // 跳转到登录页
  setTimeout(() => {
    uni.reLaunch({
      url: '/pages/login/login'
    })
  }, 500)
}

/**
 * 封装请求方法
 */
function request(options) {
  const config = {
    ...options,
    url: options.url.startsWith('http') ? options.url : BASE_URL + options.url
  }

  // 请求拦截
  const interceptedConfig = requestInterceptor(config)

  return new Promise((resolve, reject) => {
    uni.request({
      ...interceptedConfig,
      success: (res) => {
        responseInterceptor(res)
          .then(resolve)
          .catch(reject)
      },
      fail: (err) => {
        // 网络错误
        uToast.error({ title: '网络连接失败，请检查网络' })
        reject(err)
      }
    })
  })
}

// 导出快捷方法
export const api = {
  get: (url, data, options = {}) => {
    return request({ url, method: 'GET', data, ...options })
  },

  post: (url, data, options = {}) => {
    return request({ url, method: 'POST', data, ...options })
  },

  put: (url, data, options = {}) => {
    return request({ url, method: 'PUT', data, ...options })
  },

  del: (url, data, options = {}) => {
    return request({ url, method: 'DELETE', data, ...options })
  }
}

export default request
