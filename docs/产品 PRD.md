# 儿童互动故事小程序 - 产品需求文档 (PRD)

**版本：** 1.0
**更新日期：** 2026-03-05
**产品定位：** 面向 3-10 岁儿童的互动式故事阅读平台

---

## 一、产品概述

### 1.1 产品愿景
打造一个让孩子爱上阅读、让家长放心托管的互动故事平台，通过 AI 技术提供个性化阅读体验。

### 1.2 目标用户

| 用户类型 | 年龄段 | 核心需求 |
|---------|--------|----------|
| 儿童用户 | 3-6 岁 | 听故事、看动画、简单互动 |
| 儿童用户 | 7-10 岁 | 自主阅读、角色扮演、挑战答题 |
| 家长用户 | 25-40 岁 | 控制时长、内容筛选、成长记录 |

### 1.3 使用场景
- **睡前故事**：家长设置定时关闭，AI 朗读伴睡
- **碎片时间**：5-10 分钟短篇故事，等车/排队时阅读
- **亲子互动**：孩子配音、家长点评、共同完成故事挑战
- **学习成长**：通过答题解锁成就，培养阅读习惯

---

## 二、功能模块设计

### 2.1 故事库模块

#### 2.1.1 分类体系

```
一级分类          二级分类              示例
─────────────────────────────────────────────────
按年龄           3-4 岁 (启蒙)          《小熊宝宝》系列
                 5-6 岁 (幼儿)          《安徒生童话》
                 7-8 岁 (小学低)         《格林童话》
                 9-10 岁 (小学中)        《西游记》少儿版

按主题           童话故事               《灰姑娘》《睡美人》
                 科普知识               《为什么天会黑》
                 寓言故事               《伊索寓言》
                 历史故事               《三国演义》片段
                 成语故事               《守株待兔》
                 英文故事               《The Little Prince》

按时长           短篇 (5 分钟内)          早安故事
                 中篇 (10 分钟内)         经典童话
                 长篇 (20 分钟+)          连载故事
```

#### 2.1.2 每日推荐

**推荐逻辑：**
```
用户行为数据 → 特征提取 → 协同过滤 → 个性化推荐
     ↓
 - 阅读历史 (年龄、主题偏好)
 - 互动记录 (答题正确率、配音次数)
 - 停留时长 (完读率、重复播放)
 - 时间段 (睡前偏好、周末偏好)
```

**推荐位展示：**
- 首页顶部：「今日精选」3 个故事卡片
- 猜你喜欢：根据浏览历史推荐
- 热门榜单：周榜/月榜 TOP10

#### 2.1.3 音频 + 图文

**音频功能：**
| 功能 | 说明 | 技术实现 |
|------|------|----------|
| AI 朗读 | TTS 语音合成，支持多角色音色 | 腾讯云 TTS |
| 背景音效 | 根据场景自动切换 BGM | 音频混合 |
| 语速调节 | 0.5x/1.0x/1.5x/2.0x | 前端播放控制 |
| 定时关闭 | 5/10/15/30 分钟 | 前端定时器 |

**图文展示：**
- 每页文字：50-200 字（根据年龄调整）
- 插画风格：手绘风/卡通风/水彩风
- 动画效果：Lottie 动态插画（星星闪烁、云朵飘动）

---

### 2.2 互动模块

#### 2.2.1 问答互动

**题目类型：**
```
1. 选择题（单选/多选）
   例：小红帽应该相信大灰狼吗？
   A. 应该  B. 不应该  C. 看情况

2. 判断题
   例：白雪公主吃了毒苹果后死了。 (对/错)

3. 排序题
   例：将《丑小鸭》成长阶段排序
   被嘲笑 → 变成天鹅 → 离开农场 → 遇到天鹅群

4. 开放题 (AI 评分)
   例：如果你是故事主角，你会怎么做？
```

**题目生成方式：**
| 来源 | 比例 | 说明 |
|------|------|------|
| 预设题库 | 60% | 编辑团队预先编写 |
| AI 生成 | 40% | GPT 根据故事内容动态生成 |

**奖励机制：**
- 答对：+10 积分，解锁下一章
- 连续答对：额外 +5 积分/题
- 答错：显示解析，可重新尝试

#### 2.2.2 角色扮演

**配音功能流程：**
```
选择角色 → 录制音频 → AI 处理 → 生成专属版本 → 分享
    ↓          ↓          ↓          ↓          ↓
  故事中的   点击按钮   降噪/      将原声音   生成海报
  任意角色   开始录音   变声处理   轨替换     + 二维码
```

**角色音效预设：**
| 角色类型 | 音效 | 示例 |
|---------|------|------|
| 小孩 | 音调 +2 | 小红帽 |
| 老人 | 音调 -2 + 混响 | 外婆 |
| 动物 | 变声 + 回声 | 大灰狼 |
| 魔法师 | 回声 + 颤音 | 仙女 |

---

### 2.3 家长控制模块

#### 2.3.1 时长管理

**设置选项：**
```
每日总时长： 30min / 60min / 90min / 自定义
单次时长：   15min / 30min / 45min
禁用时段：   22:00-07:00 (可自定义)
休息提醒：   每 20 分钟提醒远眺
```

**超时处理：**
- 提前 5 分钟：温和提醒「还有 5 分钟哦」
- 时间到：播放结束音乐，自动暂停
- 强制退出：需家长密码才能继续使用

#### 2.3.2 内容过滤

**敏感词库：**
- 内置词库：暴力/恐怖/成人内容关键词（500+）
- 自定义词库：家长可添加屏蔽词
- AI 审核：故事上传前自动检测

**黑名单功能：**
- 屏蔽特定故事/作者/主题
- 仅允许「已收藏」内容
- 白名单模式（幼儿模式）

#### 2.3.3 成长报告

**周报/月报内容：**
```
📊 阅读数据
 - 阅读天数：5/7 天
 - 总时长：180 分钟
 - 完成故事：12 个

📚 偏好分析
 - 最爱主题：童话故事 (60%)
 - 最爱角色：公主系列
 - 答题正确率：85%

🏆 成就展示
 - 获得勋章：阅读小达人、答题王
 - 连续打卡：7 天
```

---

### 2.4 用户成长体系

#### 2.4.1 积分系统

| 行为 | 积分 | 说明 |
|------|------|------|
| 阅读故事 | +1/分钟 | 每阅读 1 分钟 |
| 完成答题 | +10/题 | 答对才有 |
| 连续打卡 | +50/天 | 连续 7 天额外 +100 |
| 配音分享 | +20/次 | 被点赞额外 +5/个 |
| 邀请好友 | +100/人 | 好友完成首次阅读 |

#### 2.4.2 勋章系统

```
📖 阅读类勋章
 - 入门读者：阅读 1 个故事
 - 阅读达人：阅读 50 个故事
 - 书香门第：累计阅读 100 小时

🎯 答题类勋章
 - 答题新手：答对 10 题
 - 答题王者：连续答对 20 题
 - 博学多才：所有故事答题 100%

🎤 配音类勋章
 - 小小配音员：录制 1 次配音
 - 金牌声优：配音被点赞 100 次

📅 打卡类勋章
 - 坚持之星：连续打卡 7 天
 - 月度全勤：整月每天阅读
```

#### 2.4.3 积分商城

**可兑换物品：**
| 物品 | 积分 | 说明 |
|------|------|------|
| 角色皮肤 | 200-500 | 解锁特殊音效 |
| 专属勋章 | 1000 | 限定版勋章 |
| 实体奖品 | 5000+ | 绘本、玩偶等 |
| VIP 会员 | 300/月 | 解锁全部内容 |

---

## 三、技术实现方案

### 3.1 技术架构

```
┌─────────────────────────────────────────────────────────┐
│                      前端层                              │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐      │
│  │ 微信小程序  │  │   H5 页面    │  │   App      │      │
│  │  (uni-app)  │  │  (uni-app)  │  │ (uni-app)  │      │
│  └─────────────┘  └─────────────┘  └─────────────┘      │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│                      网关层                              │
│              Nginx + WebSocket Proxy                     │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│                      服务层                              │
│  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐       │
│  │ API 服务  │ │AI 服务   │ │推送服务  │ │定时任务  │       │
│  │ Node.js │ │ Python  │ │ Node.js │ │ Node.js │       │
│  └─────────┘ └─────────┘ └─────────┘ └─────────┘       │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│                      数据层                              │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐      │
│  │    MySQL    │  │    Redis    │  │  对象存储    │      │
│  │  (主从复制)  │  │   (缓存)    │  │  (COS/OSS)  │      │
│  └─────────────┘  └─────────────┘  └─────────────┘      │
└─────────────────────────────────────────────────────────┘
```

### 3.2 前端技术栈

| 模块 | 技术选型 | 说明 |
|------|----------|------|
| 框架 | uni-app 3.x | 一套代码多端发布 |
| UI 库 | uView Plus | Material Design 3 风格 |
| 动画 | Lottie | JSON 格式动画 |
| 音频 | uni.createInnerAudioContext | 原生音频播放 |
| 录音 | uni.getRecorderManager | 录音管理 |
| 实时通信 | WebSocket | 互动答题 |

### 3.3 后端技术栈

| 模块 | 技术选型 | 说明 |
|------|----------|------|
| JDK 版本 | JDK 21 | LTS 长期支持版本 |
| API 框架 | Spring Boot 3.x | Java + Spring 生态 |
| AI 服务 | FastAPI | Python + LangChain |
| 数据库 | MySQL 8.0 | 主从复制 + 读写分离 |
| 缓存 | Redis 7.0 | 热点数据 + Session |
| 消息队列 | RabbitMQ | 异步任务 |
| 对象存储 | 腾讯云 COS | 音频/图片存储 |
| TTS | 腾讯云语音合成 | 多角色音色 |

### 3.4 数据库设计

#### 核心表结构

```sql
-- 用户表
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    openid VARCHAR(64) UNIQUE NOT NULL,      -- 微信 openid
    nickname VARCHAR(64),                     -- 昵称
    avatar VARCHAR(255),                      -- 头像
    age_group TINYINT,                        -- 年龄段：1=3-4 岁，2=5-6 岁，3=7-8 岁，4=9-10 岁
    parent_phone VARCHAR(20),                 -- 家长手机号
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_openid (openid)
);

-- 故事表
CREATE TABLE stories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(128) NOT NULL,              -- 标题
    description TEXT,                         -- 简介
    age_group TINYINT NOT NULL,               -- 适合年龄
    theme VARCHAR(32),                        -- 主题分类
    duration_seconds INT,                     -- 时长 (秒)
    word_count INT,                           -- 字数
    cover_image VARCHAR(255),                 -- 封面图
    audio_url VARCHAR(255),                   -- 音频 URL
    background_music VARCHAR(255),            -- 背景音乐
    status TINYINT DEFAULT 1,                 -- 状态：0=下架，1=上架
    view_count INT DEFAULT 0,                 -- 阅读量
    like_count INT DEFAULT 0,                 -- 点赞量
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_age_theme (age_group, theme),
    INDEX idx_status (status)
);

-- 故事章节表
CREATE TABLE story_chapters (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    story_id BIGINT NOT NULL,                 -- 故事 ID
    chapter_no INT NOT NULL,                  -- 章节序号
    title VARCHAR(128),                       -- 章节标题
    content TEXT NOT NULL,                    -- 文字内容
    illustrations JSON,                       -- 插画数组 [{"url":"","animation":""}]
    audio_url VARCHAR(255),                   -- 章节音频
    sort_order INT DEFAULT 0,                 -- 排序
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_story (story_id, sort_order)
);

-- 问答题表
CREATE TABLE quiz_questions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    story_id BIGINT NOT NULL,                 -- 所属故事
    chapter_id BIGINT,                        -- 所属章节
    type TINYINT NOT NULL,                    -- 题型：1=单选，2=多选，3=判断，4=排序，5=开放
    question TEXT NOT NULL,                   -- 题干
    options JSON,                             -- 选项 [{"key":"A","value":"选项内容"}]
    correct_answer JSON,                      -- 正确答案
    explanation TEXT,                         -- 答案解析
    points INT DEFAULT 10,                    -- 分值
    sort_order INT DEFAULT 0,                 -- 出现顺序
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_story_chapter (story_id, chapter_id)
);

-- 用户阅读记录表
CREATE TABLE user_reading_records (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,                  -- 用户 ID
    story_id BIGINT NOT NULL,                 -- 故事 ID
    chapter_id BIGINT,                        -- 当前章节
    progress DECIMAL(5,2),                    -- 进度百分比
    last_read_at TIMESTAMP,                   -- 最后阅读时间
    completed TINYINT DEFAULT 0,              -- 是否完成
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_story (user_id, story_id),
    INDEX idx_user (user_id)
);

-- 用户答题记录表
CREATE TABLE user_quiz_records (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    user_answer JSON,                         -- 用户答案
    is_correct TINYINT,                       -- 是否正确
    points_earned INT DEFAULT 0,              -- 获得积分
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user (user_id),
    INDEX idx_created (created_at)
);

-- 配音记录表
CREATE TABLE user_dubbing_records (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    story_id BIGINT NOT NULL,
    character_name VARCHAR(64),               -- 角色名
    audio_url VARCHAR(255) NOT NULL,          -- 配音音频
    duration_seconds INT,                     -- 时长
    like_count INT DEFAULT 0,                 -- 点赞数
    share_count INT DEFAULT 0,                -- 分享数
    status TINYINT DEFAULT 1,                 -- 状态
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user (user_id),
    INDEX idx_story (story_id)
);

-- 积分表
CREATE TABLE user_points (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL UNIQUE,
    total_points INT DEFAULT 0,               -- 累计积分
    available_points INT DEFAULT 0,           -- 可用积分
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user (user_id)
);

-- 积分流水表
CREATE TABLE user_point_logs (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    points INT NOT NULL,                      -- 变动积分 (+/-)
    source VARCHAR(32),                       -- 来源：reading/quiz/daily_share/invite
    description VARCHAR(255),                 -- 描述
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user (user_id),
    INDEX idx_created (created_at)
);

-- 勋章表
CREATE TABLE user_badges (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    badge_code VARCHAR(32) NOT NULL,          -- 勋章编码
    badge_name VARCHAR(64) NOT NULL,          -- 勋章名称
    earned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_badge (user_id, badge_code),
    INDEX idx_user (user_id)
);

-- 家长设置表
CREATE TABLE parent_settings (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL UNIQUE,
    daily_limit_minutes INT DEFAULT 60,       -- 每日时长限制
    single_limit_minutes INT DEFAULT 30,      -- 单次时长限制
    forbid_start_hour TINYINT DEFAULT 22,     -- 禁用开始时间
    forbid_end_hour TINYINT DEFAULT 7,        -- 禁用结束时间
    blackout_stories JSON,                    -- 黑名单故事 ID 列表
    custom_sensitive_words JSON,              -- 自定义敏感词
    white_list_mode TINYINT DEFAULT 0,        -- 白名单模式
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user (user_id)
);
```

---

## 四、交互设计要点

### 4.1 视觉设计规范

**色彩体系：**
```
主色调：
  - 森林绿：#2D6A4F  (导航栏、主要按钮)
  - 暖橙色：#FF8C42  (强调、勋章)
  - 天空蓝：#4ECDC4  (背景、装饰)

辅助色：
  - 奶油白：#FFFBF0  (卡片背景)
  - 柔和粉：#FFB5B5  (女孩主题)
  - 清新绿：#95D5B2  (成功状态)

文字色：
  - 主文字：#1B4332
  - 次文字：#52796F
  - 提示文字：#94B49F
```

**字体规范：**
```
标题字：PingFang SC Semibold / 24px
副标题：PingFang SC Medium / 18px
正文字：PingFang SC Regular / 16px
辅助字：PingFang SC Regular / 14px
```

### 4.2 页面布局

**首页布局：**
```
┌─────────────────────────────┐
│    搜索栏 + 消息通知         │
├─────────────────────────────┤
│    今日精选 (轮播卡片)       │
├─────────────────────────────┤
│  分类入口  │  分类入口  │... │
├─────────────────────────────┤
│    猜你喜欢 (瀑布流)         │
│   ┌───┐  ┌───┐             │
│   │故事│  │故事│             │
│   └───┘  └───┘             │
└─────────────────────────────┘
```

**阅读页布局：**
```
┌─────────────────────────────┐
│  ← 返回     进度     ♥收藏   │
├─────────────────────────────┤
│                             │
│      插画区域 (Lottie)       │
│                             │
├─────────────────────────────┤
│      故事文字内容            │
│      (可点击查词)            │
├─────────────────────────────┤
│  ◀◀  ▶/❚❚  ▶▶   [配音] [答题]│
│     播放控制条               │
└─────────────────────────────┘
```

### 4.3 交互动效

| 交互 | 动效 | 时长 |
|------|------|------|
| 页面切换 | 从右滑入 | 300ms |
| 卡片点击 | 缩放 0.95 | 150ms |
| 收藏按钮 | 爱心弹跳 | 400ms |
| 答题正确 | 彩带飘落 | 600ms |
| 答题错误 | 摇头抖动 | 300ms |
| 下拉刷新 | 星星旋转 | 500ms |

---

## 五、页面列表

### 5.1 页面结构

```
pages/
├── index/              # 首页
├── category/           # 分类页
├── story-detail/       # 故事详情页
├── reading/            # 阅读页
├── quiz/               # 答题页
├── dubbing/            # 配音页
├── user/               # 个人中心
├── parent/             # 家长中心
├── settings/           # 设置页
├── points-mall/        # 积分商城
└── achievement/        # 成就页
```

### 5.2 页面详细说明

#### 首页 (index)
**功能：**
- 搜索故事
- 今日精选轮播
- 分类导航入口
- 猜你喜欢推荐

**数据接口：**
- GET /api/stories/featured 获取精选
- GET /api/stories/recommend 获取推荐

#### 阅读页 (reading)
**功能：**
- 音频播放/暂停
- 文字展示（支持点击查词）
- Lottie 动画展示
- 章节切换
- 收藏/分享

**状态管理：**
```javascript
{
  currentStory: {...},
  currentChapter: {...},
  isPlaying: false,
  progress: 0.35,
  playbackSpeed: 1.0,
  timer: null  // 定时关闭
}
```

---

## 六、API 接口设计

### 6.1 接口规范

**基础 URL：**
```
生产环境：https://api.example.com
测试环境：https://test-api.example.com
```

**响应格式：**
```json
{
  "code": 0,
  "message": "success",
  "data": {},
  "timestamp": 1709567890
}
```

### 6.2 核心接口列表

| 模块 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 用户 | POST | /api/user/login | 微信登录 |
| 用户 | GET | /api/user/profile | 获取用户信息 |
| 用户 | POST | /api/user/age-group | 设置年龄段 |
| 故事 | GET | /api/stories | 故事列表 |
| 故事 | GET | /api/stories/:id | 故事详情 |
| 故事 | GET | /api/stories/:id/chapters | 章节列表 |
| 故事 | POST | /api/stories/:id/like | 点赞故事 |
| 阅读 | GET | /api/reading/record | 获取阅读进度 |
| 阅读 | POST | /api/reading/record | 上报阅读进度 |
| 答题 | GET | /api/quiz/questions | 获取题目 |
| 答题 | POST | /api/quiz/submit | 提交答案 |
| 配音 | POST | /api/dubbing/upload | 上传配音 |
| 积分 | GET | /api/points | 获取积分 |
| 积分 | GET | /api/points/logs | 积分流水 |
| 勋章 | GET | /api/badges | 获取勋章 |
| 家长 | GET | /api/parent/settings | 获取设置 |
| 家长 | POST | /api/parent/settings | 保存设置 |

---

## 七、开发计划

### 7.1 版本规划

| 版本 | 内容 | 周期 | 时间 |
|------|------|------|------|
| v0.1 | 技术选型 + 架构设计 | 1 周 | W1 |
| v0.5 | 核心功能开发 (故事库 + 阅读) | 3 周 | W2-4 |
| v0.8 | 互动功能 (答题 + 配音) | 2 周 | W5-6 |
| v1.0 | 家长控制 + 成长体系 | 2 周 | W7-8 |
| v1.1 | 测试 + 优化 + 上线 | 2 周 | W9-10 |

### 7.2 里程碑

```
M1 (W1): 完成技术选型，搭建开发环境
M2 (W4): 完成故事库和阅读功能，可播放音频
M3 (W6): 完成答题和配音功能，实现核心互动
M4 (W8): 完成家长控制和积分勋章系统
M5 (W10): 通过测试，正式上线微信小程序
```

### 7.3 人员配置

| 角色 | 人数 | 职责 |
|------|------|------|
| 产品经理 | 1 | 需求规划、原型设计 |
| UI 设计师 | 1 | 视觉设计、动效设计 |
| 前端开发 | 2 | uni-app 开发 |
| 后端开发 | 2 | API 开发、数据库设计 |
| 测试工程师 | 1 | 测试用例、质量保障 |

---

## 八、扩展功能（二期）

### 8.1 AR 功能
- 扫描实物触发 3D 故事场景
- AR 合影功能
- AR 绘本互动

### 8.2 家长社区
- 育儿心得分享
- UGC 原创故事上传
- 故事评价系统

### 8.3 AI 功能增强
- 智能对话伙伴（故事角色聊天）
- 个性化故事生成（孩子作为主角）
- 语音情感识别（根据情绪推荐故事）

---

## 九、数据指标

### 9.1 核心指标

| 指标 | 目标值 | 计算方式 |
|------|--------|----------|
| DAU | 10,000 | 日活跃用户数 |
| 留存率 D1/D7/D30 | 50%/30%/20% | 次日/7 日/30 日留存 |
| 人均时长 | 20 分钟 | 日总时长/DAU |
| 完读率 | 60% | 完成故事数/开始故事数 |
| 答题参与率 | 40% | 参与答题用户/总用户 |
| 付费转化率 | 5% | VIP 用户/总用户 |

### 9.2 数据埋点

**关键事件：**
```javascript
// 阅读事件
{
  event: 'story_read',
  properties: {
    story_id: 123,
    chapter_id: 5,
    duration: 180,
    completed: true
  }
}

// 答题事件
{
  event: 'quiz_submit',
  properties: {
    question_id: 456,
    is_correct: true,
    time_spent: 15
  }
}

// 配音事件
{
  event: 'dubbing_create',
  properties: {
    story_id: 123,
    character: '小红帽',
    duration: 30
  }
}
```

---

## 十、风险评估

| 风险 | 影响 | 应对措施 |
|------|------|----------|
| 内容版权 | 高 | 采购正版授权，建立原创内容库 |
| 儿童隐私 | 高 | 符合《儿童个人信息网络保护规定》 |
| 内容审核 | 中 | AI + 人工双重审核机制 |
| 技术风险 | 中 | 技术预研，准备备选方案 |
| 市场竞争 | 中 | 差异化定位，强化互动特色 |

---

## 附录

### A. 竞品分析
- 凯叔讲故事
- 宝宝巴士故事
- 洪恩故事

### B. 参考文档
- 微信小程序开发文档
- uni-app 官方文档
- Material Design 3 设计规范

### C. 术语表
| 术语 | 说明 |
|------|------|
| TTS | Text-to-Speech 文字转语音 |
| Lottie | AirBnb 开源的动画格式 |
| UGC | User Generated Content 用户生成内容 |
