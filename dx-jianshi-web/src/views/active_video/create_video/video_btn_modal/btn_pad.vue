<template>
  <div class="btn-set-modal">
    <el-form ref="form" :inline="true" class="demo-form-inline fromBox">
      <el-form-item label="按钮数量">
        <el-select v-model="DEAFULT_BTN_LEN" @change="setBtnListLength()" size="mini">
          <el-option :label="index + 1 + '个'" :value="index + 1" v-for="(item, index) in BTN_NUM" :key="index"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="按钮样式" class="btn-select-list">
        <div class="set-btn-style" style="display: inline-block;">
          <img :src="selectBtn.image_url" :alt="selectBtn.name">
        </div>

        <el-dropdown @command="handleCommand" class="btn-set-dropdown">
          <span class="el-dropdown-link">
            选择
            <i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item v-for="(item, index) in btnStyleList" :key="index" :command="item">
              <div class="d-flex flex-wrap dropdown-btn-style" style="width: 280px">
                <img :src="item.image_url" alt="">
                <p>{{item.name}}</p>
              </div>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-form-item>
      <br>

      <el-form-item label="触发事件">
        <el-radio v-model="radio_event" label="event">点击按钮后触发事件</el-radio>
      </el-form-item>

      <el-form-item label style="margin-left: 30px">
        <el-radio v-model="radio_event" label="auto">自动</el-radio>
        <el-select v-model="play_auto_btn" size="mini">
          <el-option
            :label="`按钮${index + 1}`"
            :value="index"
            :disabled="item.eventFn !== 'jump'"
            v-for="(item, index) in btnList"
            :key="index"></el-option>
        </el-select>
      </el-form-item>
    </el-form>

    <el-form>
      <el-form-item :label="`按钮${j+1}`" v-for="(item,j) in btnList" :key="j">
        <div class="defalut-btn">

          <div class="set-btn-style">
            <img :src="selectBtn.image_url" :alt="selectBtn.name">
          </div>

          <span class="tip">点击后</span>

          <el-select size="mini" v-model="item.eventFn" @change="item.eventParams = ''">
            <el-option :label="fn.name" :value="fn_name" v-for="(fn, fn_name) in eventFnList" :key="fn_name"></el-option>
          </el-select>

          <span class="tip">{{eventFnList[item.eventFn].activeName}}</span>

          <el-select v-model="item.eventParams" size="mini" v-if="eventFnList[item.eventFn].paramsType == 1">
            <el-option :label="item" :value="item" v-for="(item, index) in enLetter" :key="index"></el-option>
          </el-select>
          <el-input style="width: 200px" v-model="item.eventParams" v-if="eventFnList[item.eventFn].paramsType == 2" size="mini"></el-input>

        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { btnAttr, eventFnList } from './btn_attr'
import enLetter from '../js/en_letter'
export default {
  name: 'BtnPad',
  props: {},
  data() {
    return {
      BTN_NUM: 9,
      DEAFULT_BTN_LEN: 3,
      radio_event: 'event',
      play_auto: '', //自动播放
      play_auto_btn: 0,
      btnStyleList: [],
      btnList: [],
      eventFnList: eventFnList,
      eventParams: 'A',
      selectBtn: {}
    }
  },
  computed: {
    enLetter() {
      let en = JSON.parse(JSON.stringify(enLetter))
      return en.splice(0, this.$parent.DATA_LENTH)
    }
  },
  watch: {
    selectBtn: {
      // 暂时处理为 全部更改 统一按钮样式
      handler(val) {
        this.btnList.forEach(item => {
          this.$set(item, 'selectBtn', val)
        })
        this.$emit('setBtnList', this.btnList)
      },
      deep: true
    }
  },
  methods: {
    receiveMessage(event) {
      // 按钮位置大小改变
      let cmd = event.data.cmd
      if (!document.getElementById('videoEditIframe')) return
      let iframe = document.getElementById('videoEditIframe').contentWindow
      // 设置 BtnList
      if (cmd == 'setTrackBtnlist') {
        this.setTrackBtnlist(event.data.data)
      }
    },
    setTrackBtnlist(list) {
      for (let i = 0; i < list.length; i++) {
        let { x, y, w, h } = list[i]
        this.btnList[i].x = x
        this.btnList[i].y = y
        this.btnList[i].w = w
        this.btnList[i].h = h
      }
    },
    handleCommand(command) {
      this.selectBtn = command
    },
    setBtnListLength() {
      this.btnList = []

      for (let i = 0; i < this.DEAFULT_BTN_LEN; i++) {
        let obj = JSON.parse(JSON.stringify(btnAttr))
        obj.selectBtn = this.selectBtn
        this.btnList.push(obj)
      }
      this.$emit('setBtnList', this.btnList)
    },
    // 设置 BtnList 基础数据
    async setDefaultBtnList() {
      let active_video_data_list = JSON.parse(localStorage.getItem('active_video_data_list')) || {}
      let btn_effect = {}
      if (active_video_data_list[this.$parent.DATA_INDEX]) {
        btn_effect = active_video_data_list[this.$parent.DATA_INDEX].btn_effect
      }
      this.btnStyleList = await this.getBtnStyleList()

      if (btn_effect.btn_list) {
        this.DEAFULT_BTN_LEN = btn_effect.btn_list.length
        this.radio_event = btn_effect.radio_event
        this.play_auto_btn = btn_effect.play_auto_btn
        this.btnList = btn_effect.btn_list
        this.selectBtn = this.btnList[0].selectBtn
      } else {
        this.selectBtn = this.btnStyleList[0]
        for (let i = 0; i < this.DEAFULT_BTN_LEN; i++) {
          let obj = JSON.parse(JSON.stringify(btnAttr))
          obj.selectBtn = this.selectBtn
          this.btnList.push(obj)
        }
      }

      this.$emit('setBtnList', this.btnList)
    },
    // 获取按钮列表
    getBtnStyleList() {
      return new Promise((resolve, reject) => {
        this.$get('/interactive-video/button-list').then(res => {
          if (res.data.code == '0000') {
            resolve(res.data.data)
          }
        })
      })
    }
  },
  components: {},

  created() {
    this.setDefaultBtnList()
    this.getBtnStyleList()
    // this.setBtnListLength()
  },
  mounted() {
    window.addEventListener('message', this.receiveMessage, false)
  },
  beforeDestroy() {
    window.removeEventListener('message', this.receiveMessage, false)
  }
}
</script>

<style lang="scss">
.btn-set-modal {
  padding: 20px;
  .ml-auto {
    margin-left: 100px;
  }
}

.el-dropdown-menu {
  height: 400px;
  overflow: hidden;
  overflow-y: auto;
  background: #000;
  .el-dropdown-menu__item {
    &:hover,
    &:focus {
      background: #000;
      .dropdown-btn-style {
        border: 1px solid #fff;
      }
    }
  }
}
.btn-set-dropdown {
  margin-left: 20px;
}
.defalut-btn {
  display: flex;
  align-items: center;
  .tip {
    margin: 0 10px;
    width: 50px;
  }
}
.set-btn-style {
  width: 85px;
  height: 30px;
  background: #232323;
  position: relative;
  img {
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    object-fit: contain;
  }
}
.dropdown-btn-style {
  height: 80px;
  text-align: center;
  border: 1px solid #393939;
  border-radius: 5px;
  color: #fff;
  margin-bottom: 10px;
  overflow: hidden;

  img {
    width: 100%;
    height: 40px;
    object-fit: contain;
  }
  p {
    background: #292929;
    font-size: 12px;
    flex: 1;
  }
}
.btn-select-list {
  margin-left: auto;
}
</style>
