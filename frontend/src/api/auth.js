/**
 * 认证相关 API
 */

import { api } from '@/utils/request'

/**
 * 微信登录
 * @param {string} code - 微信登录 code
 */
export function loginByWechat(code) {
  return api.post('/auth/wechat-login', { code })
}

/**
 * 手机号登录
 * @param {string} phone - 手机号
 * @param {string} code - 验证码
 */
export function loginByPhone(phone, code) {
  return api.post('/auth/phone-login', { phone, code })
}

/**
 * 发送短信验证码
 * @param {string} phone - 手机号
 */
export function sendVerifyCode(phone) {
  return api.post('/auth/send-code', { phone })
}

/**
 * 注册账号
 * @param {Object} data - 注册信息
 * @param {string} data.phone - 手机号
 * @param {string} data.code - 验证码
 * @param {string} data.nickname - 昵称（可选）
 * @param {string} data.parentName - 家长称呼（可选）
 */
export function register(data) {
  return api.post('/auth/register', data)
}

/**
 * 退出登录
 */
export function logout() {
  return api.post('/auth/logout')
}

/**
 * 获取当前用户信息
 */
export function getCurrentUser() {
  return api.get('/user/current')
}

/**
 * 更新用户年龄分组
 * @param {string} ageGroup - 年龄分组 (3-4|5-6|7-8|9-10)
 */
export function updateUserAgeGroup(ageGroup) {
  return api.put('/user/age-group', { ageGroup })
}

/**
 * 更新用户信息
 * @param {Object} data - 用户信息
 * @param {string} data.nickname - 昵称
 * @param {string} data.avatar - 头像 URL
 */
export function updateUserInfo(data) {
  return api.put('/user/profile', data)
}
