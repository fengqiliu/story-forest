/**
 * Pinia Store 入口文件
 */
import { createPinia } from 'pinia'

// 导入所有 stores
import { useUserStore } from './user'
import { useStoryStore } from './story'
import { useAudioStore } from './audio'

// 创建 pinia 实例
const pinia = createPinia()

// 导出所有 stores 方便使用
export {
  pinia,
  useUserStore,
  useStoryStore,
  useAudioStore
}

export default pinia
