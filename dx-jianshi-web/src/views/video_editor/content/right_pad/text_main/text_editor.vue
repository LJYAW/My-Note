<template>
  <div class="px-40px py-20px bg-white"
       @click.stop.prevent>
    <div class="d-flex">

      <el-select v-model="styleData.fontFamily"
                 placeholder="请选择字体"
                 class="mr-20px"
                 style="width:300px">
        <el-option v-for="item in font"
                   :key="item.value"
                   :label="item.label"
                   :value="item.value">
        </el-option>
      </el-select>
      <el-select v-model="styleData.fontSize"
                 placeholder="请选择字号"
                 style="width:100px">
        <el-option v-for="item in size"
                   :key="item.value"
                   :label="item.label"
                   :value="item.value">
        </el-option>

      </el-select>
      <a class="hove-c ml-auto"
         @click="cancel()"><i class="iconfont iconclose"></i></a>
    </div>
    <div class="d-flex align-items-center mt-10px">
      <span class="fc-999 fz-14px nowrap">样式</span>
      <div class="ml-30px d-flex">
        <div v-for="(item,index) in textStyle"
             :key="index">
          <el-tooltip class="item"
                      effect="dark"
                      :content="item.name"
                      :visible-arrow='false'
                      placement="top">
            <div @click="getTextStyle(item)"
                 :class="['textClass cp',{'textActive': item.check}]">
              <i :class="item.icon"
                 style="font-size:14px"></i>
            </div>
          </el-tooltip>

        </div>
      </div>
    </div>
    <div class="d-flex align-items-center mt-27px">
      <span class="fc-999 fz-14px nowrap">对齐</span>
      <div class="ml-30px d-flex">
        <div v-for="(item,index) in textAlignArr"
             :key="index">
          <el-tooltip class="item"
                      effect="dark"
                      :content="item.name"
                      :visible-arrow='false'
                      placement="top">
            <div @click="getTextAlignArr(item),activeIndex = index"
                 :class="['textClass cp',{'textActive': activeIndex == index}]">
              <i :class="item.icon"
                 style="font-size:14px"></i>
            </div>
          </el-tooltip>
        </div>
      </div>
    </div>
    <div class="d-flex align-items-center mt-10px">
      <span class="fc-999 fz-14px nowrap mr-28px">行间距</span>
      <el-slider :min="10"
                 :max="30"
                 :formatTooltip='lineHeightTooltip'
                 style='width:30%'></el-slider>
    </div>
    <div class="d-flex align-items-center mt-10px">
      <span class="fc-999 fz-14px nowrap mr-28px">字间距</span>
      <el-slider :min="10"
                 :max="50"
                 :formatTooltip='letterSpacingTooltip'
                 style='width:30%'></el-slider>
    </div>
    <div class="d-flex">
      <div class="d-flex align-items-center mt-18px">
        <span class="fc-999 fz-14px mr-26px nowrap">字体颜色</span>
        <el-color-picker v-model="styleData.color"
                         show-alpha
                         size="mini">
        </el-color-picker>
      </div>
      <div class="d-flex align-items-center mt-18px">
        <span class="fc-999 fz-14px ml-50px mr-26px nowrap">背景颜色</span>
        <el-color-picker v-model="styleData.backgroundColor"
                         show-alpha
                         size="mini">
        </el-color-picker>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: [],
  data() {
    return {
      font: [
        {
          value: 'PingFang SC',
          label: 'PingFang SC'
        },
        {
          value: 'Hiragino Sans GB',
          label: 'Hiragino Sans GB'
        },
        {
          value: '宋体',
          label: '宋体'
        },
        {
          value: '隶书',
          label: '隶书'
        }
      ],
      size: [
        {
          value: '20px',
          label: '20'
        },
        {
          value: '30px',
          label: '30'
        },
        {
          value: '50px',
          label: '50'
        }
      ],
      textStyle: [
        {
          icon: 'iconfont iconWAF--',
          name: '文字阴影',
          check: false
        },
        {
          icon: 'iconfont iconWAF--',
          name: '文字加粗',
          check: false
        },
        {
          icon: 'iconfont iconWAF--',
          name: '文字倾斜',
          check: false
        },
        {
          icon: 'iconfont iconWAF--',
          name: '文字下划线',
          check: false
        }
      ],
      activeIndex: 0,
      textAlignArr: [
        {
          icon: 'iconfont iconzuoduiqi',
          name: '左对齐',
          value: 'left'
        },
        {
          icon: 'iconfont iconjuzhongduiqi',
          name: '居中',
          value: 'center'
        },
        {
          icon: 'iconfont iconyouduiqi',
          name: '右对齐',
          value: 'right'
        }
      ],
      styleData: {
        color: 'pink',
        backgroundColor: 'rgba(0, 0, 0, 0)',
        fontSize: '20px',
        fontFamily: '微软雅黑',
        fontWeight: 0, // 文字加粗
        textDecoration: '', // 文字下划线  underline
        fontStyle: '', // 文字倾斜 oblique
        lineHeight: 1,
        letterSpacing: 1,
        textAlign: '',
        textShadow: '',
        width: '150px',
        height: '30px'
      }
    }
  },
  computed: {},

  watch: {
    styleData: {
      handler(newValue) {
        this.$store.commit('setTrackTextStyle', newValue)
      },
      deep: true
    }
  },

  methods: {
    cancel() {
      // this.$store.commit('setTextEditorActive', false)
      this.$store.commit('setActiveData', {
        type: '',
        index: -1
      })
    },
    getTextStyle(item) {
      item.check = !item.check
      if (item.check) {
        if (item.name == '文字阴影') {
          this.styleData.textShadow = 'rgb(0, 0, 0) 0px 0px 4px'
        }
        if (item.name == '文字加粗') {
          this.styleData.fontWeight = 700
        }
        if (item.name == '文字倾斜') {
          this.styleData.fontStyle = 'oblique'
        }
        if (item.name == '文字下划线') {
          this.styleData.textDecoration = 'underline'
        }
      } else {
        if (item.name == '文字阴影') {
          this.styleData.textShadow = ''
        }
        if (item.name == '文字加粗') {
          this.styleData.fontWeight = ''
        }
        if (item.name == '文字倾斜') {
          this.styleData.fontStyle = ''
        }
        if (item.name == '文字下划线') {
          this.styleData.textDecoration = ''
        }
      }
    },
    getTextAlignArr(item) {
      this.styleData.textAlign = item.value
    },
    lineHeightTooltip(val) {
      this.styleData.lineHeight = val / 10
      return this.styleData.lineHeight
    },
    letterSpacingTooltip(val) {
      this.styleData.letterSpacing = val / 10 + 'px'
      const newVal = val / 10
      return newVal
    }
  },

  created() { },

  mounted() { },

  components: {}
}
</script>

<style scoped lang="scss">
.textClass {
  width: 36px;
  height: 36px;
  border-radius: 3px;
  border: 1px solid rgba(208, 208, 208, 1);
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 5px;
}
.textActive {
  width: 36px;
  height: 36px;
  background: rgba(248, 183, 183, 1);
  border-radius: 3px;
  border: 1px solid rgba(197, 26, 26, 1);
}
</style>
