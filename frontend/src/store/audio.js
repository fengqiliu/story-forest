/**
 * 音频播放管理 (Pinia)
 */
import { defineStore } from 'pinia'

export const useAudioStore = defineStore('audio', {
  state: () => ({
    // 音频播放器实例
    player: null,
    // 是否正在播放
    isPlaying: false,
    // 是否已暂停
    isPaused: false,
    // 当前播放时间
    currentTime: 0,
    // 音频总时长
    duration: 0,
    // 播放进度 (0-100)
    progress: 0,
    // 播放速度
    playSpeed: 1.0,
    // 定时关闭时间 (分钟)
    timerMinutes: 0,
    // 定时器
    timer: null,
    // 定时器剩余时间 (秒)
    timerRemaining: 0
  }),

  getters: {
    // 格式化当前时间
    formattedCurrentTime: (state) => {
      return state.formatTime(state.currentTime)
    },

    // 格式化总时长
    formattedDuration: (state) => {
      return state.formatTime(state.duration)
    },

    // 是否已播放完成
    isFinished: (state) => {
      return state.currentTime >= state.duration - 1
    }
  },

  actions: {
    // 初始化播放器
    initPlayer() {
      if (!this.player) {
        this.player = uni.createInnerAudioContext()

        // 监听播放
        this.player.onPlay(() => {
          this.isPlaying = true
          this.isPaused = false
        })

        // 监听暂停
        this.player.onPause(() => {
          this.isPaused = true
        })

        // 监听停止
        this.player.onStop(() => {
          this.isPlaying = false
          this.isPaused = false
          this.currentTime = 0
          this.progress = 0
        })

        // 监听结束
        this.player.onEnded(() => {
          this.isPlaying = false
          this.currentTime = 0
          this.progress = 0
          this.clearTimer()
        })

        // 监听错误
        this.player.onError((err) => {
          console.error('音频播放错误:', err)
          this.isPlaying = false
        })

        // 监听时间更新
        this.player.onTimeUpdate(() => {
          this.currentTime = this.player.currentTime
          this.duration = this.player.duration
          this.progress = (this.currentTime / this.duration) * 100
        })
      }
    },

    // 播放音频
    play(url) {
      if (!this.player) {
        this.initPlayer()
      }

      this.player.src = url
      this.player.play()
    },

    // 暂停播放
    pause() {
      if (this.player && this.isPlaying) {
        this.player.pause()
      }
    },

    // 恢复播放
    resume() {
      if (this.player && this.isPaused) {
        this.player.play()
      }
    },

    // 切换播放/暂停
    togglePlay() {
      if (this.isPlaying && !this.isPaused) {
        this.pause()
      } else {
        this.resume()
      }
    },

    // 停止播放
    stop() {
      if (this.player) {
        this.player.stop()
        this.clearTimer()
      }
    },

    // 跳转进度
    seek(position) {
      if (this.player) {
        this.player.seek(position)
      }
    },

    // 设置播放速度
    setPlaySpeed(speed) {
      this.playSpeed = speed
      // 注意：uni-app 的 InnerAudioContext 不支持变速播放
      // 需要使用第三方库或后端处理
    },

    // 设置定时关闭
    setTimer(minutes) {
      this.timerMinutes = minutes

      if (this.timer) {
        this.clearTimer()
      }

      if (minutes > 0) {
        const seconds = minutes * 60
        this.timerRemaining = seconds

        this.timer = setInterval(() => {
          this.timerRemaining--

          if (this.timerRemaining <= 0) {
            this.stop()
            uni.showToast({
              title: '定时关闭已停止播放',
              icon: 'none'
            })
          }
        }, 1000)
      }
    },

    // 清除定时关闭
    clearTimer() {
      if (this.timer) {
        clearInterval(this.timer)
        this.timer = null
        this.timerRemaining = 0
      }
    },

    // 获取定时关闭剩余时间文本
    getTimerText() {
      if (this.timerRemaining <= 0) return ''

      const minutes = Math.floor(this.timerRemaining / 60)
      const seconds = this.timerRemaining % 60

      if (minutes > 0) {
        return `${minutes}分${seconds}秒`
      }
      return `${seconds}秒`
    },

    // 格式化时间
    formatTime(seconds) {
      if (!seconds) return '00:00'

      const min = Math.floor(seconds / 60)
      const sec = Math.floor(seconds % 60)

      return `${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}`
    },

    // 销毁播放器
    destroy() {
      if (this.timer) {
        this.clearTimer()
      }

      if (this.player) {
        this.player.stop()
        this.player.destroy()
        this.player = null
      }

      this.isPlaying = false
      this.isPaused = false
      this.currentTime = 0
      this.duration = 0
      this.progress = 0
    }
  }
})
