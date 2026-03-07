<script>
export default {
  onLaunch: function() {
    console.log('App Launch')

    // 初始化全局数据
    this.globalData = {
      userInfo: null,
      isLogin: false,
      ageGroup: null
    }

    // 检查登录状态
    this.checkLoginStatus()
  },
  onShow: function() {
    console.log('App Show')
  },
  onHide: function() {
    console.log('App Hide')
  },
  methods: {
    checkLoginStatus() {
      // 获取本地存储的 token 和用户信息
      const token = uni.getStorageSync('token')
      const userInfo = uni.getStorageSync('userInfo')

      if (token && userInfo) {
        this.globalData.isLogin = true
        this.globalData.userInfo = userInfo
        this.globalData.ageGroup = userInfo.ageGroup

        console.log('用户已登录:', userInfo)
      } else {
        this.globalData.isLogin = false
        console.log('用户未登录')
      }
    }
  }
}
</script>

<style lang="scss">
/* 每个页面公共 css */
@import '@/uni.scss';

page {
  background-color: #FFFBF0;
  font-size: 14px;
  line-height: 1.5;
}

/* 全局字体 */
view, text, button {
  font-family: 'PingFang SC', 'Helvetica Neue', Helvetica, 'microsoft yahei', arial, sans-serif;
}

/* 通用工具类 */
.flex {
  display: flex;
}

.flex-center {
  display: flex;
  align-items: center;
  justify-content: center;
}

.flex-between {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.flex-column {
  display: flex;
  flex-direction: column;
}

.flex-1 {
  flex: 1;
}

.text-primary {
  color: #1B4332;
}

.text-secondary {
  color: #52796F;
}

.text-hint {
  color: #94B49F;
}

.text-accent {
  color: #FF8C42;
}

.mt-1 { margin-top: 4px; }
.mt-2 { margin-top: 8px; }
.mt-3 { margin-top: 12px; }
.mt-4 { margin-top: 16px; }
.mt-5 { margin-top: 24px; }

.mb-1 { margin-bottom: 4px; }
.mb-2 { margin-bottom: 8px; }
.mb-3 { margin-bottom: 12px; }
.mb-4 { margin-bottom: 16px; }
.mb-5 { margin-bottom: 24px; }

.ml-1 { margin-left: 4px; }
.ml-2 { margin-left: 8px; }
.ml-3 { margin-left: 12px; }
.ml-4 { margin-left: 16px; }
.ml-5 { margin-left: 24px; }

.mr-1 { margin-right: 4px; }
.mr-2 { margin-right: 8px; }
.mr-3 { margin-right: 12px; }
.mr-4 { margin-right: 16px; }
.mr-5 { margin-right: 24px; }

.p-2 { padding: 8px; }
.p-3 { padding: 12px; }
.p-4 { padding: 16px; }

/* 安全区域 */
.safe-area-bottom {
  padding-bottom: constant(safe-area-inset-bottom);
  padding-bottom: env(safe-area-inset-bottom);
}
</style>
