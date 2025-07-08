<!--
 * @Author: your name
 * @Date: 2021-07-22 15:43:56
 * @LastEditTime: 2021-10-22 16:48:15
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoMaterial/components/MaterialForm.vue
-->
<template>
  <!-- 素材标题，链接 -->
  <div class="search-material">
    <div v-if="$route.query.type=='url'" class="material-url">
      <p class="input-label">链接信息：</p>
      <div class="input-wrap">
        <el-input
          v-model="material_url_temp"
          clearable
          placeholder="输入文本开始创作，或直接粘贴资讯链接：目前支持百家号及微信公众号的文章链接"
        />
        <base-btn size="mini" class="get-btn" @click="getMaterilaList">获取内容</base-btn>
      </div>
    </div>

    <div class="select-pad">
      <div class="video-config">
        <span class="video-config-label">视频比例：</span>
        <el-radio-group v-model="videoScale" @change="setRadio">
          <el-radio v-for="item in video_proportion" :key="item.type" class="" :label="item.type">
            <svg-icon :icon-class="videoScale==item.type?`${item.icon}-active`:item.icon" />
            <span>{{ item.name }} {{ item.type }}</span>
          </el-radio>
        </el-radio-group>
      </div>

      <div v-if="$route.query.type=='url'" class="video-config">
        <span class="video-config-label">视频时长：</span>
        <el-radio-group v-model="MAX_SIZE" @change="setMaxSize">
          <el-radio v-for="(ms, t) in text_select" :key="t" :label="ms.size">{{ ms.name }}</el-radio>
        </el-radio-group>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import * as check from '../js/check'
import * as edit from '../materialList/js/edit-pad'
import * as duration from '../materialList/js/set-duration'
import { AnalysisArticleByUrl } from '@/app/jianshi-video/api/script_creation/index.js'
const TEXT_MS = 4.5
export default {
  name: 'MaterialForm',
  components: {

  },
  props: {

  },
  data() {
    return {
      title: '',
      material_url_temp: '',
      videoScale: '16:9',
      MAX_SIZE: this.$store.state.jianshi.max_size,
      // 视频比例数据
      video_proportion: [{
        type: '16:9',
        name: '横版',
        icon: 'hengping'
      }, {
        type: '9:16',
        name: '竖版',
        icon: 'shuping'
      }],
      // 视频时长数据
      text_select: [
        {
          name: '自由创作',
          size: TEXT_MS * 60 * 3,
          des: '视频时长5分钟以内'
        },
        {
          name: '通用1分钟',
          size: TEXT_MS * 60,
          des: '适应多家平台时间限制'
        },
        {
          name: '简短2分钟',
          size: TEXT_MS * 60 * 2,
          des: '适合浏览，完播率高'
        }
      ],
      data_list: {},
      text_str: ''
    }
  },
  computed: {
    ...mapState('jianshi', [
      'material_tabname', 'material_data', 'material_url'
    ])
  },
  watch: {
    // 重新编辑回显数据
    material_data: {
      handler(val, oldVal) {
        this.init(val)
      },
      immediate: true
    }
    // material_url: {
    //   handler(val, oldVal) {
    //     this.material_url_temp = val
    //     if (val) this.getMaterilaList()
    //   },
    //   immediate: true
    // }
    // MAX_SIZE: {
    //   handler(val, oldVal) {
    //     console.log(val)
    //   },
    //   immediate: true
    // }
  },
  created() {
    if (this.$route.query.type === 'url') {
      this.material_url_temp = this.material_url
      this.getMaterilaList()
    }
  },
  mounted() {

  },
  methods: {
    // 重新编辑回显数据
    init(val) {
      if (val && this.$route.query.reset_edti_id) {
        this.title = val.title
        this.setTitle()
        this.videoScale = val.target_ratio
        this.setRadio()
      }
    },
    // 改变state->title
    setTitle() {
      this.$store.commit('jianshi/SET_TITLE', this.title)
    },
    // 改变state->target_ratio
    setRadio() {
      this.$store.commit('jianshi/SET_TARGET_RATIO', this.videoScale)
      if (this.videoScale === '9:16') {
        this.$store.commit('jianshi/SET_TEMPLATE', null)
        this.$store.commit('jianshi/SET_PERSONDEATIL', null)
      }
    },
    // 改变state->subTitle
    setSubTitle() {
      const sub_title = []
      // 子标题
      sub_title.push({
        title: '',
        show: false
      })
      this.$store.commit('jianshi/SET_SUBTITLE', sub_title)
    },
    // 改变state->textList
    setTextList() {
      const edtor_text_list = []
      // 文本素材

      edtor_text_list.push({
        text: this.text_str
      })
      this.$store.commit('jianshi/SET_TEXT_LIST', edtor_text_list)
    },
    // 改变state->imgList
    setImgList() {
      const edtor_img_list = []
      // 图片素材
      if (this.data_list.image_list.length < 1) {
        edtor_img_list.push({})
      } else {
        this.data_list.image_list.forEach((item, index) => {
          const text_str = edit.getText(this.text_str, this.data_list.image_list.length, index)
          const tex_duration = duration.getMs(text_str)
          edtor_img_list.push({
            type: 'image',
            resource_url: item.proxy_url,
            origin_url: item.origin_url,
            duration: tex_duration,
            text_str: text_str,
            source: '原文图库'
          })
        })
      }
      this.$store.commit('jianshi/SET_IMG_LIST', edtor_img_list)
    },
    // 改变state->max_size,切换size需要重新获取素材内容，所以存到state里面
    setMaxSize() {
      this.$store.commit('jianshi/SET_MAX_SIZE', this.MAX_SIZE)
      this.$store.commit('jianshi/SET_MAX_TIME', this.setMaxTime())
      if (this.material_url_temp) this.getMaterilaList()
    },
    // 改变state->max_time,用来判断是否超时
    setMaxTime() {
      let MAX_S = this.MAX_SIZE / TEXT_MS
      if (MAX_S >= 180) {
        MAX_S = 300
      }
      return MAX_S
    },
    // 获取素材
    async getMaterilaList() {
      // 判断是否有链接
      const errMsg = check.checkoutIsURL(this.material_url_temp)
      if (errMsg) {
        this.$message({
          type: 'warning',
          message: errMsg
        })
        return
      }
      this.$emit('loading', true)
      this.$store.commit('jianshi/SET_IMG_LIST', [{}])
      this.$store.commit('jianshi/SET_TEXT_LIST', [{ text: '' }])
      this.$store.commit('jianshi/SET_MATERIAL_URL', this.material_url_temp)
      const params = {
        url: this.material_url_temp,
        text_len: this.MAX_SIZE
      }
      const { err, res } = await AnalysisArticleByUrl(params)
      this.$emit('loading', false)
      if (err) {
        this.$message.error('获取内容失败')
        return
      }
      this.data_list = res.data
      this.$store.commit('jianshi/SET_DATA_LIST', this.data_list)
      this.text_str = this.data_list.news_summary
      // 回显标题
      this.title = this.data_list.title
      this.setTitle()
      this.setSubTitle()
      this.setTextList()
      this.setImgList()
      this.setSrcData()
    },
    // 设置弹框关键词
    setSrcData() {
      const obj = {
        image_list: this.data_list.image_list,
        lexer: this.data_list.lexer || []
      }
      this.$store.commit('jianshi/SET_SRC_DATA', obj)
    }
  }
}
</script>

<style scoped lang="scss">
.search-material {
  position : relative;
  margin-bottom : 20px;

  .input-label {
    font-size : 18px;
    font-weight : 600;
    color : #404040;
    line-height : 18px;
    margin-bottom : 20px;
  }

  .title {
    ::v-deep input {
      padding-right : 85px;
      background : #F7F8F9;
      border-radius : 4px;
      border : 0;
      padding-left : 20px;

      &::placeholder {
        color : #C2C4C7;
      }
    }

    ::v-deep .el-input__suffix {
      right : 20px;

      .el-input__count-inner {
        font-size : 14px;
        font-weight : 400;
        color : #5D5D5D;
        line-height : 14px;
        padding : 0;
        background : none;
      }
    }
  }

  .material-url {
    display : flex;
    flex-direction : column;
    margin-bottom : 20px;

    ::v-deep .input-wrap {
      display : flex;

      .el-input {
        margin-right : 20px;

        .el-input__suffix {
          margin-top : -5px;
        }

        input {
          background : #F7F8F9;
          border-radius : 4px;
          border : 0;
          height : 30px;
          line-height : 30px;
          font-size : 12px;
          color : #A0A0A0;
          padding-left : 20px;
          color : #404040;

          &::placeholder {
            color : #A0A0A0;
          }
        }
      }

      .get-btn {
        width : 180px;
      }
    }
  }

  .select-pad {
    display : flex;

    ::v-deep .video-config {
      font-size : 14px;
      margin-right : 30px;

      .video-config-label {
        color : #404040;
      }

      .el-radio__label {
        color : #404040;
        opacity : .6;
        font-weight : 400;

        svg {
          margin-right : 4px;
        }
      }

      .el-radio.is-checked {
        .el-radio__label {
          color : #5675E8;
          opacity : 1;
        }
      }
    }
  }
}

</style>
