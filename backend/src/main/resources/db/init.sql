-- 故事森林数据库初始化脚本
-- MySQL 8.0+

-- 创建数据库
CREATE DATABASE IF NOT EXISTS story_forest DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE story_forest;

-- 用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    phone VARCHAR(11) NOT NULL UNIQUE COMMENT '手机号',
    password VARCHAR(255) NOT NULL COMMENT '密码（加密）',
    nickname VARCHAR(50) COMMENT '昵称',
    avatar VARCHAR(500) COMMENT '头像 URL',
    age_group VARCHAR(20) COMMENT '年龄段',
    is_vip TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否 VIP',
    points INT NOT NULL DEFAULT 0 COMMENT '积分',
    total_reading_minutes INT NOT NULL DEFAULT 0 COMMENT '总阅读时长（分钟）',
    completed_stories INT NOT NULL DEFAULT 0 COMMENT '已完成故事数量',
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT '账号状态',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    last_login_at DATETIME COMMENT '最后登录时间',
    INDEX idx_phone (phone),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 故事表
CREATE TABLE IF NOT EXISTS stories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL COMMENT '故事标题',
    subtitle VARCHAR(200) COMMENT '副标题',
    cover_image VARCHAR(500) COMMENT '封面图 URL',
    description TEXT COMMENT '故事简介',
    age_group VARCHAR(20) NOT NULL COMMENT '年龄段',
    category VARCHAR(50) NOT NULL COMMENT '主题分类',
    duration_type VARCHAR(20) NOT NULL COMMENT '时长分类',
    reading_minutes INT COMMENT '预计阅读时长',
    chapter_count INT DEFAULT 0 COMMENT '章节数量',
    play_count BIGINT NOT NULL DEFAULT 0 COMMENT '播放次数',
    favorite_count BIGINT NOT NULL DEFAULT 0 COMMENT '收藏次数',
    average_rating DECIMAL(3,2) COMMENT '平均评分',
    is_recommended TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否推荐',
    is_vip TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否 VIP 专享',
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT' COMMENT '状态',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_age_group (age_group),
    INDEX idx_category (category),
    INDEX idx_status (status),
    INDEX idx_recommended (is_recommended),
    INDEX idx_play_count (play_count)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='故事表';

-- 故事章节表
CREATE TABLE IF NOT EXISTS story_chapters (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    story_id BIGINT NOT NULL COMMENT '所属故事 ID',
    title VARCHAR(100) NOT NULL COMMENT '章节标题',
    chapter_order INT NOT NULL COMMENT '章节序号',
    content TEXT COMMENT '章节内容',
    audio_url VARCHAR(500) COMMENT '音频 URL',
    audio_duration INT COMMENT '音频时长（秒）',
    illustration_url VARCHAR(500) COMMENT '插图 URL',
    lottie_url VARCHAR(500) COMMENT 'Lottie 动画 URL',
    has_quiz TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否包含互动问题',
    play_count BIGINT NOT NULL DEFAULT 0 COMMENT '播放次数',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_story_id (story_id),
    INDEX idx_chapter_order (chapter_order)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='故事章节表';

-- 分类表
CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE COMMENT '分类名称',
    icon_url VARCHAR(500) COMMENT '分类图标 URL',
    description VARCHAR(200) COMMENT '分类描述',
    sort_order INT DEFAULT 0 COMMENT '排序',
    is_active TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否启用',
    age_group VARCHAR(20) DEFAULT 'all' COMMENT '年龄段',
    INDEX idx_active (is_active),
    INDEX idx_sort (sort_order)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分类表';

-- 用户收藏表
CREATE TABLE IF NOT EXISTS user_favorites (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '用户 ID',
    story_id BIGINT NOT NULL COMMENT '故事 ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
    UNIQUE KEY uk_user_story (user_id, story_id),
    INDEX idx_user_id (user_id),
    INDEX idx_story_id (story_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户收藏表';

-- 用户阅读历史表
CREATE TABLE IF NOT EXISTS user_reading_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '用户 ID',
    story_id BIGINT NOT NULL COMMENT '故事 ID',
    chapter_id BIGINT COMMENT '章节 ID',
    progress INT DEFAULT 0 COMMENT '阅读进度（百分比）',
    reading_time INT DEFAULT 0 COMMENT '阅读时长（秒）',
    last_read_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后阅读时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_user_id (user_id),
    INDEX idx_story_id (story_id),
    INDEX idx_last_read (last_read_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户阅读历史表';

-- 题目表
CREATE TABLE IF NOT EXISTS quizzes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    story_id BIGINT NOT NULL COMMENT '所属故事 ID',
    chapter_id BIGINT NOT NULL COMMENT '所属章节 ID',
    quiz_type VARCHAR(20) NOT NULL COMMENT '题目类型',
    question TEXT NOT NULL COMMENT '题目内容',
    option_a VARCHAR(200) COMMENT '选项 A',
    option_b VARCHAR(200) COMMENT '选项 B',
    option_c VARCHAR(200) COMMENT '选项 C',
    option_d VARCHAR(200) COMMENT '选项 D',
    correct_answer VARCHAR(100) NOT NULL COMMENT '正确答案',
    explanation TEXT COMMENT '答案解析',
    points_reward INT DEFAULT 10 COMMENT '奖励积分',
    quiz_order INT COMMENT '题目顺序',
    INDEX idx_story_id (story_id),
    INDEX idx_chapter_id (chapter_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='题目表';

-- 答题记录表
CREATE TABLE IF NOT EXISTS quiz_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '用户 ID',
    story_id BIGINT NOT NULL COMMENT '故事 ID',
    chapter_id BIGINT NOT NULL COMMENT '章节 ID',
    quiz_id BIGINT NOT NULL COMMENT '题目 ID',
    user_answer TEXT NOT NULL COMMENT '用户答案',
    is_correct TINYINT(1) COMMENT '是否正确',
    points_earned INT DEFAULT 0 COMMENT '获得积分',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '答题时间',
    INDEX idx_user_id (user_id),
    INDEX idx_story_id (story_id),
    INDEX idx_quiz_id (quiz_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='答题记录表';

-- 配音记录表
CREATE TABLE IF NOT EXISTS dubbing_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '用户 ID',
    story_id BIGINT NOT NULL COMMENT '故事 ID',
    character_name VARCHAR(50) NOT NULL COMMENT '角色名称',
    audio_url VARCHAR(500) COMMENT '录音 URL',
    audio_duration INT COMMENT '录音时长（秒）',
    score INT COMMENT '评分',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '配音时间',
    INDEX idx_user_id (user_id),
    INDEX idx_story_id (story_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='配音记录表';

-- 勋章表
CREATE TABLE IF NOT EXISTS achievements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL COMMENT '勋章名称',
    description VARCHAR(200) COMMENT '勋章描述',
    icon_url VARCHAR(500) COMMENT '勋章图标',
    condition_type VARCHAR(50) NOT NULL COMMENT '达成条件类型',
    condition_value INT NOT NULL COMMENT '达成条件值',
    points INT DEFAULT 0 COMMENT '奖励积分',
    is_active TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否启用',
    sort_order INT DEFAULT 0 COMMENT '排序',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_active (is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='勋章表';

-- 用户勋章表
CREATE TABLE IF NOT EXISTS user_achievements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '用户 ID',
    achievement_id BIGINT NOT NULL COMMENT '勋章 ID',
    achieved_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '达成时间',
    UNIQUE KEY uk_user_achievement (user_id, achievement_id),
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户勋章表';

-- 积分流水表
CREATE TABLE IF NOT EXISTS points_transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '用户 ID',
    amount INT NOT NULL COMMENT '积分变动（正数增加，负数减少）',
    balance INT NOT NULL COMMENT '变动后余额',
    type VARCHAR(20) NOT NULL COMMENT '类型：READ/QUIZ/DUBBING/EXCHANGE',
    description VARCHAR(200) COMMENT '描述',
    reference_id BIGINT COMMENT '关联 ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user_id (user_id),
    INDEX idx_type (type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='积分流水表';

-- 插入测试数据

-- 插入测试用户（密码：123456，BCrypt 加密）
INSERT INTO users (phone, password, nickname, age_group, is_vip, points, status) VALUES
('13800138001', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lqkkO9QS3TzCjH3rS', '快乐小书虫', '5-6 岁', 0, 100, 'ACTIVE'),
('13800138002', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lqkkO9QS3TzCjH3rS', '聪明小故事王', '7-8 岁', 1, 500, 'ACTIVE'),
('13800138003', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lqkkO9QS3TzCjH3rS', '可爱阅读家', '3-4 岁', 0, 200, 'ACTIVE');

-- 插入测试故事
INSERT INTO stories (title, subtitle, cover_image, description, age_group, category, duration_type, reading_minutes, chapter_count, play_count, favorite_count, is_recommended, status) VALUES
('小红帽', '经典童话故事', '/images/stories/little-red-riding-hood.jpg', '一个小女孩去看望外婆，路上遇到了大灰狼的故事...', '3-4 岁', '童话故事', 'short', 5, 3, 1000, 200, 1, 'PUBLISHED'),
('三只小猪', '团结力量大', '/images/stories/three-little-pigs.jpg', '三只小猪各自建造房子，面对大灰狼的考验...', '5-6 岁', '童话故事', 'medium', 10, 5, 800, 150, 1, 'PUBLISHED'),
('为什么天会黑', '科普知识启蒙', '/images/stories/why-night.jpg', '探索白天和黑夜的秘密，了解地球自转的奥秘...', '7-8 岁', '科普知识', 'short', 8, 4, 600, 100, 1, 'PUBLISHED'),
('守株待兔', '成语故事', '/images/stories/waiting-for-rabbit.jpg', '一个农夫偶然捡到撞死的兔子，从此不再耕作的故事...', '5-6 岁', '成语故事', 'short', 6, 2, 500, 80, 0, 'PUBLISHED'),
('西游记之大闹天宫', '经典名著改编', '/images/stories/havoc-in-heaven.jpg', '孙悟空大闹天宫，挑战天庭权威的传奇故事...', '9-10 岁', '历史故事', 'long', 20, 10, 2000, 500, 1, 'PUBLISHED');

-- 插入测试章节
INSERT INTO story_chapters (story_id, title, chapter_order, content, audio_url, audio_duration) VALUES
(1, '小红帽出发', 1, '从前，有一个可爱的小女孩，她叫小红帽。有一天，妈妈让她去看望生病的外婆...', '/audio/stories/1/chapter1.mp3', 120),
(1, '遇见大灰狼', 2, '小红帽在森林里遇到了一只大灰狼，她不知道大灰狼很坏...', '/audio/stories/1/chapter2.mp3', 150),
(1, '外婆家', 3, '大灰狼先到了外婆家，把外婆藏了起来，然后假扮成外婆...', '/audio/stories/1/chapter3.mp3', 180),
(2, '老大盖房', 1, '猪老大决定用稻草盖房子，因为它最省力...', '/audio/stories/2/chapter1.mp3', 100),
(2, '老二盖房', 2, '猪老二决定用木头盖房子，因为它比较结实...', '/audio/stories/2/chapter2.mp3', 100);

-- 插入测试勋章
INSERT INTO achievements (name, description, icon_url, condition_type, condition_value, points, is_active, sort_order) VALUES
('阅读新手', '完成第一个故事', '/images/achievements/newbie.png', 'COMPLETED_STORIES', 1, 50, 1, 1),
('阅读达人', '完成 10 个故事', '/images/achievements/reader.png', 'COMPLETED_STORIES', 10, 200, 1, 2),
('阅读大师', '完成 50 个故事', '/images/achievements/master.png', 'COMPLETED_STORIES', 50, 500, 1, 3),
('答题王', '答对 100 道题', '/images/achievements/quiz-king.png', 'QUIZ_CORRECT', 100, 300, 1, 4),
('配音演员', '完成 10 次配音', '/images/achievements/voice-actor.png', 'DUBBING_COUNT', 10, 250, 1, 5);

-- 插入测试分类
INSERT INTO categories (name, icon_url, description, sort_order, is_active, age_group) VALUES
('童话故事', '/images/categories/fairy-tale.png', '经典童话故事，培养孩子想象力', 1, 1, 'all'),
('科普知识', '/images/categories/science.png', '探索科学奥秘，启发好奇心', 2, 1, 'all'),
('寓言故事', '/images/categories/fable.png', '富有哲理的小故事', 3, 1, 'all'),
('成语故事', '/images/categories/idiom.png', '学习成语，了解传统文化', 4, 1, '5-6 岁'),
('历史故事', '/images/categories/history.png', "了解历史，增长见识", 5, 1, '7-8 岁'),
('英文故事', '/images/categories/english.png', '英语启蒙，国际视野', 6, 1, 'all');

-- 插入测试题目
INSERT INTO quizzes (story_id, chapter_id, quiz_type, question, option_a, option_b, option_c, option_d, correct_answer, explanation, points_reward, quiz_order) VALUES
(1, 1, 'CHOICE', '小红帽要去看望谁？', '外婆', '奶奶', '妈妈', '姐姐', 'A', '故事开头提到妈妈让小红帽去看望生病的外婆', 10, 1),
(1, 2, 'CHOICE', '小红帽在森林里遇到了谁？', '大灰狼', '小兔子', '小松鼠', '小鸟', 'A', '小红帽遇到了一只大灰狼', 10, 2),
(1, 3, 'JUDGE', '大灰狼把外婆吃掉了，对吗？', '对', '错', NULL, NULL, 'B', '大灰狼只是把外婆藏了起来，并没有吃掉', 10, 3),
(2, 1, 'CHOICE', '猪老大用什么材料盖房子？', '稻草', '木头', '砖头', '石头', 'A', '猪老大为了省力用稻草盖房子', 10, 1),
(2, 2, 'CHOICE', '猪老二用什么材料盖房子？', '稻草', '木头', '砖头', '石头', 'B', '猪老二用木头盖房子', 10, 2);
