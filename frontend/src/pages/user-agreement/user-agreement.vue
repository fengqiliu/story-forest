<template>
  <view class="agreement-page">
    <view class="content-wrapper">
      <view class="header">
        <text class="title">用户协议</text>
        <text class="update-time">更新时间：2026 年 3 月 5 日</text>
      </view>

      <scroll-view scroll-y class="content-scroll">
        <view class="content">
          <view class="section">
            <text class="section-title">一、协议的接受与修改</text>
            <text class="section-content">
              1.1 欢迎您使用故事森林小程序。在使用本小程序服务之前，请您务必仔细阅读并理解本《用户协议》（以下简称"本协议"）。
            </text>
            <text class="section-content">
              1.2 当您使用本小程序服务时，即表示您已阅读、理解并同意接受本协议的全部内容。
            </text>
            <text class="section-content">
              1.3 本小程序保留随时修改本协议的权利。修改后的协议一旦公布即生效，恕不另行通知。
            </text>
          </view>

          <view class="section">
            <text class="section-title">二、服务内容</text>
            <text class="section-content">
              2.1 故事森林是一款面向 3-10 岁儿童的互动式故事阅读平台，提供以下服务：
            </text>
            <text class="section-content">
              - 海量优质儿童故事资源
            </text>
            <text class="section-content">
              - AI 个性化故事推荐
            </text>
            <text class="section-content">
              - 互动答题功能
            </text>
            <text class="section-content">
              - 角色扮演配音功能
            </text>
            <text class="section-content">
              - 家长管控和成长报告
            </text>
          </view>

          <view class="section">
            <text class="section-title">三、用户注册与账号</text>
            <text class="section-content">
              3.1 您需要注册并登录账号才能使用完整服务。
            </text>
            <text class="section-content">
              3.2 注册时，您需要提供真实、准确、完整的信息，包括但不限于手机号等。
            </text>
            <text class="section-content">
              3.3 您应妥善保管账号信息，不得将账号出借、转让给他人使用。
            </text>
            <text class="section-content">
              3.4 如发现账号被他人非法使用，请立即联系我们。
            </text>
          </view>

          <view class="section">
            <text class="section-title">四、用户行为规范</text>
            <text class="section-content">
              4.1 您在使用本服务时，不得进行以下行为：
            </text>
            <text class="section-content">
              - 发布违法、色情、暴力、恐怖内容
            </text>
            <text class="section-content">
              - 侵犯他人知识产权或合法权益
            </text>
            <text class="section-content">
              - 传播虚假信息或谣言
            </text>
            <text class="section-content">
              - 进行任何干扰或破坏服务的行为
            </text>
          </view>

          <view class="section">
            <text class="section-title">五、知识产权</text>
            <text class="section-content">
              5.1 本小程序提供的所有内容（包括但不限于文字、图片、音频、视频）均受知识产权保护。
            </text>
            <text class="section-content">
              5.2 未经书面授权，您不得以任何形式复制、传播、修改本小程序内容。
            </text>
          </view>

          <view class="section">
            <text class="section-title">六、隐私保护</text>
            <text class="section-content">
              6.1 我们重视用户隐私保护，详细规则请参阅《隐私政策》。
            </text>
            <text class="section-content">
              6.2 我们会采取合理措施保护您的个人信息安全。
            </text>
          </view>

          <view class="section">
            <text class="section-title">七、免责声明</text>
            <text class="section-content">
              7.1 因不可抗力导致的服务中断，我们不承担责任。
            </text>
            <text class="section-content">
              7.2 用户因不当使用本服务造成的损失，由用户自行承担。
            </text>
          </view>

          <view class="section">
            <text class="section-title">八、协议终止</text>
            <text class="section-content">
              8.1 您有权随时停止使用本服务。
            </text>
            <text class="section-content">
              8.2 如您违反本协议，我们有权终止向您提供服务。
            </text>
          </view>

          <view class="section">
            <text class="section-title">九、法律适用与争议解决</text>
            <text class="section-content">
              9.1 本协议适用中华人民共和国法律。
            </text>
            <text class="section-content">
              9.2 因本协议引起的任何争议，双方应友好协商解决；协商不成的，可向有管辖权的人民法院提起诉讼。
            </text>
          </view>

          <view class="section">
            <text class="section-title">十、联系方式</text>
            <text class="section-content">
              如您对本协议有任何疑问，请通过以下方式联系我们：
            </text>
            <text class="section-content">
              客服邮箱：support@storyforest.com
            </text>
          </view>
        </view>
      </scroll-view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {}
  }
}
</script>

<style lang="scss" scoped>
.agreement-page {
  min-height: 100vh;
  background: #FFFBF0;
}

.content-wrapper {
  padding: 24px;

  .header {
    margin-bottom: 24px;

    .title {
      display: block;
      font-size: 24px;
      font-weight: 600;
      color: #1B4332;
      margin-bottom: 8px;
    }

    .update-time {
      display: block;
      font-size: 14px;
      color: #94B49F;
    }
  }

  .content-scroll {
    height: calc(100vh - 120px);

    .content {
      .section {
        margin-bottom: 24px;

        .section-title {
          display: block;
          font-size: 16px;
          font-weight: 600;
          color: #1B4332;
          margin-bottom: 12px;
        }

        .section-content {
          display: block;
          font-size: 14px;
          color: #52796F;
          line-height: 2;
          margin-bottom: 8px;
        }
      }
    }
  }
}
</style>
