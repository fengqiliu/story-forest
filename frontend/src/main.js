import App from './App.vue'
import uviewPlus from 'uview-plus'
import { createPinia } from 'pinia'

// #ifndef VITE_H5
import { createSSRApp } from 'vue'
// #endif

export function createApp() {
  const app = createSSRApp(App)
  const pinia = createPinia()

  // 使用 Pinia
  app.use(pinia)

  // 使用 uview-plus
  app.use(uviewPlus)

  return {
    app,
    pinia
  }
}
