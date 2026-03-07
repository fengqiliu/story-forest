/**
 * 故事数据状态管理 (Pinia)
 */
import { defineStore } from 'pinia'

export const useStoryStore = defineStore('story', {
  state: () => ({
    // 当前播放的故事
    currentStory: null,
    // 当前章节
    currentChapter: null,
    // 播放列表
    playlist: [],
    // 阅读历史记录
    readingHistory: [],
    // 收藏的故事
    favorites: [],
    // 最近播放
    recentPlayed: []
  }),

  getters: {
    // 是否正在播放
    isPlaying: (state) => !!state.currentStory,

    // 收藏的故事 IDs
    favoriteIds: (state) => state.favorites.map(s => s.id),

    // 是否已收藏
    isFavorite: (state) => (storyId) => {
      return state.favorites.some(s => s.id === storyId)
    }
  },

  actions: {
    // 设置当前播放的故事
    setCurrentStory(story, chapter) {
      this.currentStory = story
      this.currentChapter = chapter

      // 添加到最近播放
      this.addToRecentPlayed(story)
    },

    // 清除当前播放
    clearCurrentStory() {
      this.currentStory = null
      this.currentChapter = null
    },

    // 更新当前章节
    updateCurrentChapter(chapter) {
      this.currentChapter = chapter
    },

    // 添加到收藏
    addToFavorites(story) {
      if (!this.isFavorite(story.id)) {
        this.favorites.push(story)
        this.saveFavorites()
      }
    },

    // 从收藏移除
    removeFromFavorites(storyId) {
      const index = this.favorites.findIndex(s => s.id === storyId)
      if (index !== -1) {
        this.favorites.splice(index, 1)
        this.saveFavorites()
      }
    },

    // 切换收藏状态
    toggleFavorite(story) {
      if (this.isFavorite(story.id)) {
        this.removeFromFavorites(story.id)
        return false
      } else {
        this.addToFavorites(story)
        return true
      }
    },

    // 保存收藏到本地
    saveFavorites() {
      uni.setStorageSync('favorites', this.favorites)
    },

    // 加载收藏列表
    loadFavorites() {
      const favorites = uni.getStorageSync('favorites')
      if (favorites) {
        this.favorites = favorites
      }
    },

    // 添加到最近播放
    addToRecentPlayed(story) {
      // 移除已存在的
      const index = this.recentPlayed.findIndex(s => s.id === story.id)
      if (index !== -1) {
        this.recentPlayed.splice(index, 1)
      }

      // 添加到开头
      this.recentPlayed.unshift({
        ...story,
        playTime: Date.now()
      })

      // 限制只保留 20 条
      if (this.recentPlayed.length > 20) {
        this.recentPlayed.pop()
      }

      uni.setStorageSync('recentPlayed', this.recentPlayed)
    },

    // 加载最近播放
    loadRecentPlayed() {
      const recentPlayed = uni.getStorageSync('recentPlayed')
      if (recentPlayed) {
        this.recentPlayed = recentPlayed
      }
    },

    // 添加阅读记录
    addReadingRecord(record) {
      this.readingHistory.push({
        ...record,
        createTime: Date.now()
      })

      // 限制只保留 100 条
      if (this.readingHistory.length > 100) {
        this.readingHistory.shift()
      }

      uni.setStorageSync('readingHistory', this.readingHistory)
    },

    // 加载阅读历史
    loadReadingHistory() {
      const history = uni.getStorageSync('readingHistory')
      if (history) {
        this.readingHistory = history
      }
    },

    // 更新阅读进度
    updateReadingProgress(storyId, chapterId, progress) {
      const record = this.readingHistory.find(
        r => r.storyId === storyId && r.chapterId === chapterId
      )

      if (record) {
        record.progress = progress
        record.updateTime = Date.now()
      } else {
        this.addReadingRecord({
          storyId,
          chapterId,
          progress,
          updateTime: Date.now()
        })
      }

      uni.setStorageSync('readingHistory', this.readingHistory)
    }
  }
})
