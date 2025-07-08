<!--
 * @Author: your name
 * @Date: 2021-08-04 10:31:55
 * @LastEditTime: 2021-09-15 16:52:26
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/enterprise-info/content.vue
-->
<template>
  <div class="content-wrap">
    <title-bar title="内容设置" />
    <div class="img-wrap">
      <div v-for="(item,index) in imgData" :key="item.title" class="img-item">
        <span class="title">{{ item.title }}</span>
        <div class="upload-wrap">
          <base-upload
            :button-name="item.btn_name"
            file-type="image/png, image/jpeg, image/jpg"
            @getFileUrl="getFileUrl(arguments,index)"
          >
            <div class="img-upload">
              <base-image :src="item.src" />
              <div v-if="activeIndex===index&&showMark" class="mark">
                <i class="el-icon-loading" />
              </div>
            </div>
          </base-upload>
          <!-- <img src="@/assets/images/tip.png" alt="" class="tip-img"> -->
          <p class="tip-msg">
            <span class="tip-size">建议图片尺寸：{{ item.size }}px</span>文件大小{{ item.store }}M以内
            <span v-if="index===2">PNG</span>
            <span v-else>PNG或JPG</span>格式<span v-if="index===2">(透明背景)</span>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BaiduSDK from '@/utils/sdk.js'
import { mapGetters } from 'vuex'
export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      src: '',
      imgData: [{
        title: '企业logo',
        src: '',
        btn_name: '点击上传LOGO',
        type: 'PNG,JPG',
        size: '135*30',
        store: 1
      }, {
        title: '首页背景',
        src: '',
        btn_name: '点击上传页面背景',
        type: 'PNG,JPG',
        size: '1920*350',
        store: 2
      }, {
        title: '视频水印',
        src: '',
        btn_name: '点击上传视频水印',
        type: 'PNG,JPG',
        size: '200*90',
        store: 1
      }],
      showMark: false,
      activeIndex: 0
    }
  },
  computed: {
    ...mapGetters(['companyInfo']),
    imgContent() {
      const { logo_url, banner_url, shuiyin_url } = this.companyInfo
      return [logo_url, banner_url, shuiyin_url]
    }
  },
  watch: {
    imgContent(val) {
      if (val) {
        val.forEach((item, index) => {
          this.imgData[index].src = item
        })
      }
    }
  },
  created() {
    this.imgContent.forEach((item, index) => {
      this.imgData[index].src = item
    })
  },
  mounted() {
    const s = document.createElement('script')
    s.type = 'text/JavaScript'
    s.src =
  'https://code.bdstatic.com/npm/@baiducloud/sdk@1.0.0-rc.30/dist/baidubce-sdk.bundle.min.js'
    document.body.appendChild(s)
  },
  methods: {
    getFileUrl(arr, index) {
      if (arr[1].size > this.imgData[index].store * 1024 * 1024) {
        this.$message({
          type: 'warning',
          message: `图片大小超过${this.imgData[index].store}M,请重新选择`
        })
      } else {
        this.showMark = true
        this.upload(arr[1], index)
      }
      // 校验图片尺寸
      // CheckImgSize(arr[0]).then((res) => {
      // const size = this.imgData[index].size.split('*')
      // if (Number(size[0]) === res.w && Number(size[1]) === res.h) {
      //   this.upload(arr[1], index)
      // } else {
      //   this.$message.error('上传图片尺寸不符，请重新上传')
      // }
      // }).catch((error) => {
      //   this.$message.error(error)
      //   return false
      // })
    },
    async upload(url, index) {
      await this.getStsToken('pic')
      const uploader = new BaiduSDK(this.sdkParams)
      this.activeIndex = index
      uploader.startPartUpload(url, this.uploadImg)
    },
    // 获取STS权限
    async getStsToken(type) {
      const params = {
        media_type: type
      }
      const { err, res } = await this.$get('/util/bos-sts', params)
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.sdkParams = res.data
    },
    async uploadImg(e) {
      const enterprise_info = JSON.parse(JSON.stringify(this.companyInfo))
      if (e) {
        enterprise_info.cover_bos_key = e.body.key
      }
      let paramsName = ''
      switch (this.activeIndex) {
        case 0:
          paramsName = 'logo'
          break
        case 1:
          paramsName = 'banner'
          break
        case 2:
          paramsName = 'shuiyin'
          break
      }
      enterprise_info[paramsName] = e.body.key
      this.showMark = false
      // this.$emit('update', enterprise_info)
    }
  }
}
</script>

<style scoped lang="scss">
.content-wrap {
  margin-top: 30px;

  .img-wrap {
    display: flex;
    margin-top: 30px;

    .img-item {
      flex: 1;
      display: flex;
      margin-right: 24px;

      &:last-child {
        margin-right: 0;
      }

      .title {
        color: #a0a0a0;
        font-size: 14px;
      }

      .upload-wrap {
        margin-left: 20px;
        flex: 1;
        width: 0;
        position: relative;

        .upload {
          position: relative;

          .img-upload {
            height: 190px;
            padding-bottom: 30px;
            background: #f2f2f2;
            border-radius: 4px 4px 0px 0px;
            text-align: center;

            .mark {
              position: absolute;
              width: 100%;
              height: 100%;
              background: rgba(0, 0, 0, .4);
              top: 0;
              display: flex;
              align-items: center;
              justify-content: center;
              // opacity: 0;

              i {
                color: #fff;
                font-size: 20px;
              }
            }

            .img-error {
              width: auto;
              height: auto;
              margin-top: 50px;
            }

            ::v-deep img {
              width: auto;
              height: auto;
              max-height: 100%;
              max-width: 100%;
            }
          }

          ::v-deep .btn-style {
            position: absolute;
            bottom: 0;
            width: 100%;
            color: #fff;
            font-size: 12px;
            line-height: 12px;
            padding: 9px 0;
            text-align: center;
            background: #5675e8;
            border-radius: 0px 0px 4px 4px;
          }
        }

        .tip-msg {
          background: #f8f8f8;
          border-radius: 4px;
          color: rgba(64,64,64,.4);
          padding: 10px;
          margin-top: 10px;
          font-size: 10px;

          .tip-size {
            margin-right: 22px;
          }
        }

      }

    }
  }
}

</style>
