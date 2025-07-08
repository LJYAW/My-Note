<!--
 * @Author: your name
 * @Date: 2021-10-11 11:15:47
 * @LastEditTime: 2021-10-20 10:00:22
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/hot-qa/index.vue
-->
<template>
  <div class="hot-qa-wrap">
    <div class="qa-header">
      <div class="content qa-banner">
        <div>
          <p class="title">使用帮助</p>
          <p class="sub-title">User support and help</p>
        </div>

        <div class="qa-img">
          <img src="@/assets/images/guide/hot-qa-header.png" style="width: 300px; height: 200px">
        </div>
      </div>
    </div>

    <div class="content">
      <div class="qa-list">
        <div class="slidebar">
          <ul>
            <li
              v-for="(item,key) in qaList"
              :key="key"
              :class="['list-key',{'active': activeKey===key}]"
              @click="activeKey = key"
            >
              <span class="text"> {{ key }}</span>
            </li>
          </ul>
        </div>

        <div class="qa-content">
          <ul>
            <li v-for="(item,index) in qaList[activeKey]" :key="index" class="qa-li">
              <p class="q"><span> Q：</span><span>{{ item.q }}</span></p>
              <p class="a"><span> A：</span><span>{{ item.a }}</span></p>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      qaList: {
        '视频管家': [
          {
            q: '如何进行视频分析',
            a: '在管理中心“视频管理”中选择某一视频进入详情页，在详情页底部点击“开始分析”，稍等即可。'
          },
          {
            q: '“智能分析”有什么用',
            a: '进行过智能分析的视频，可以识别出视频中出现的人物、物体、语音，字幕，关键标签等信息，这些内容能在搜索中更快更精准的找到想要的视频。'
          },
          {
            q: '什么是智能搜索',
            a: '智能搜索能够识别出搜索的内容为人物或物体，或其它信息，并列出与之相关的视频列表。'
          },
          {
            q: '如何创建目录',
            a: '在管理中心“目录管理”中点击“创建目录”按钮，即可在视频管家中创建一个空白的目录。'
          }
        ],
        '企业账号': [
          {
            q: '如何进行子账号管理',
            a: '升级为企业用户后，在管理中心“用户管理”中，管理员可以为其创建子账号，每个子账号允许分配独立的权限。'
          },
          {
            q: '子账号怎么样进行权限分配',
            a: '升级为企业用户后，在“用户管理”的“角色管理”中可以创建一个角色，并为角色分配操作权限。'
          },
          {
            q: '为用户分配某在一个目录下的权限',
            a: '升级为企业用户后，在“目录管理”中某一目录后点击“…”，再选择成员管理，添加您想要添加的用户，并为这个用户分配一个角色，即为该用户在此目录中赋予了对应角色的权限。'
          }
        ],
        '充值续费': [
          {
            q: '如何充值',
            a: '用户可在管理中心“购买续费”中选择金额进行能量点充值，能量点可用户各种创作和存储空间的购买。'
          },
          {
            q: '获得更大的存储空间',
            a: '用户可在管理中心“购买续费”中选择适合自己的空间大小，购买付并费即可扩充自己的存储空间。'
          },
          {
            q: '存储空间的有效期如何计算',
            a: '成功购买存储空间套餐后，扩展存储空间立即生效，使用有效期为一年（自然年），当购买多个存储空间套餐时，存储空间叠加，有效期则重新按一年开始计算。'
          }
        ],
        '快速创作工具': [
          {
            q: '什么是字幕创作',
            a: '字幕创作功能可以提取原视频中自带的字幕，或通过语音识别来生成字幕进行编辑创作，同时提供字幕翻译功能。'
          },
          {
            q: '如何进行字幕翻译',
            a: '在字幕创作时勾选“进行字幕翻译”，并选择输出的翻译语种，即可在识别字幕的同时进行翻译。'
          },
          {
            q: '什么是语音创作',
            a: '语音创作可以将一段音频文件识别成文本信息，搭配视频素材进行短视频快速创作。'
          },
          {
            q: '怎么把一篇文章转换成视频',
            a: '在“快速创作”工具中输入一段百家号或微信公众号的文章链接，即可快速识别文章中的图片与文本，最终生成短视频。'
          },
          {
            q: '怎么给视频添加马赛克或水印',
            a: '在“视频水印”工具中选择一个视频进行编辑，在视频任意位置，任意时间段内添加一个高斯模糊的范围，或上传一个图片贴纸，遮住想要遮挡的部分，最后生成视频。'
          }
        ]
      },
      activeKey: '视频管家'
    }
  },
  computed: {

  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {

  }
}
</script>

<style scoped lang="scss">
.hot-qa-wrap {
  font-family: PingFangSC-Regular, PingFang SC;

  .content {
    width: 1220px;
    margin: 0 auto;
  }

  .qa-banner {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 50px 0;
  }

  .qa-img {
    margin-right: 30%;
  }

  .qa-header {
    background-color: #f3f4ff;
  }

  .title {
    font-size: 48px;
    font-weight: 600;
    color: #333ed0;
  }

  .sub-title {
    font-size: 22px;
    font-weight: 400;
    color: #333ed0;
  }

  .qa-list {
    display: flex;
    margin-top: 30px;
    padding-bottom: 100px;

    .slidebar {
      background-color: white;
      min-width: 286px;
      max-width: 286px;
      margin-right: 32px;
      border-radius: 4px;
      height: 100%;

      .list-key {
        font-size: 18px;
        text-align: center;
        padding: 41px 0;
        transition: all .2s;
        cursor: pointer;

        .text {
          padding: 40px 10px;
          border-bottom: 1px solid #f0f0f0;
        }

        &.active {
          background: #5675e8;
          box-shadow: 0px 8px 16px 0px rgba(7, 25, 93, .2);
          border-radius: 4px;
          color: white;

          .text {
            padding: 42px 10px;
            border-bottom: 1px solid transparent;
          }
        }

      }
    }

    .qa-content {
      flex: 1;
      background-color: white;
      border-radius: 4px;
      padding: 50px;

      .qa-li {
        border-bottom: 1px solid #f0f0f0;
        margin-bottom: 30px;
      }

      .q {
        font-size: 14px;
        font-weight: 600;
        margin-bottom: 20px;
        color: #404040;
        display: flex;
      }

      .a {
        margin-bottom: 30px;
        font-size: 14px;
        font-weight: 400;
        line-height: 1.6;
        display: flex;
      }
    }

  }

}
</style>
