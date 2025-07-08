<template>
  <div>
    <model :id="'videoInteration'" ref="videoInteration" @close="close">
      <div slot="title">互动效果编辑</div>
      <div slot="body">
        <!-- 封面展示 -->
        <div class="coverBox">
          <div class="img_cover">
            <!-- <img src alt /> -->
            <img
              v-if="material.type.includes('image')"
              :src="material.resource_url"
              @error="imgError(material, index)"
            />
            <videoCut
              :key="material.resource_url"
              v-if="material.type == 'video'"
              :video_offset_ms="{
                start_ms: material.start_ms,
                end_ms: material.end_ms,
              }"
              :video_options="{
                width: '798',
                height: '350',
                src: material.resource_url,
              }"
            />
            <div class="coverBtns">
              <c-btn :text="item.text" v-for="(item, index) in btnList" :key="index"></c-btn>
            </div>
          </div>
        </div>
        <!-- from交互 -->
        <el-form ref="form" :model="form" :inline="true" class="demo-form-inline fromBox">
          <el-form-item label="按钮数量">
            <el-select v-model="btnAcount" @change="setbtnList()">
              <el-option
                :label="index + 1 + '个'"
                :value="index"
                v-for="(item, index) in 9"
                :key="index"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="按钮样式" style="margin: 0 60px">
            <c-btn text="123"></c-btn>
          </el-form-item>
          <el-form-item>
            <el-dropdown>
              <span class="el-dropdown-link">
                选择
                <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>
                  <div class="d-flex flex-wrap" style="width: 280px">
                    <c-btn
                      v-for="(item, index) in 10"
                      :key="index"
                      :text="'样式' + (index + 1)"
                      style="margin-top: 10px; margin-left: 10px"
                    ></c-btn>
                  </div>
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </el-form-item>
          <el-form-item label="触发事件">
            <el-radio v-model="radio_event" label="1">点击按钮后触发事件</el-radio>
          </el-form-item>
          <el-form-item label style="margin-left: 30px">
            <el-radio v-model="radio_event" label="2">自动</el-radio>
            <el-select v-model="play_auto">
              <el-option :label="item" :value="item" v-for="(item, index) in playArry" :key="index"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label>
            <div class="fromlist">
              <span style="margin-right: 4px">按钮设置</span>
              <div>
                <div class="d-flex mt10" v-for="(item, j) in btnList" :key="j">
                  <el-form-item label>
                    <!-- @handleClick="" -->
                    <el-popover placement="bottom" width="96" v-model="item.popoverVisible">
                      <div style="text-align: center; margin: 0">
                        <ul class="edit-text-popover-ul">
                          <li @click="handleEditText(item, j)">编辑文本</li>
                        </ul>
                      </div>
                      <div slot="reference">
                        <!-- 自定义按钮 -->
                        <c-btn v-if="!item.editable" :text="item.text"></c-btn>
                        <!-- 编辑按钮文本 -->
                        <div class="custom-btn w-120px" v-if="item.editable">
                          <input
                            class="edit-text-input"
                            @click="editTextInputFocus"
                            @blur="editTextInputBlur($event, item)"
                            type="text"
                          />
                        </div>
                      </div>
                    </el-popover>
                  </el-form-item>
                  <el-form-item label="点击后">
                    <div class="d-flex">
                      <el-select
                        v-model="item.eventFn"
                        style="width: 140px"
                        @change="fragmentChange()"
                      >
                        <el-option
                          :label="obj.name"
                          :value="obj.name"
                          v-for="(obj, key) in eventFnList"
                          :key="key"
                        ></el-option>
                      </el-select>
                      <div class="d-flex" v-if="item.eventFn == '跳转视频片段'">
                        <div class="ml10 d-flex">
                          <div style="width: 36px">至</div>
                          <el-select v-model="item.jumpTo" style="width: 130px">
                            <el-option
                              :label="en"
                              :value="en"
                              v-for="(en, index2) in fragment"
                              :key="index2"
                            ></el-option>
                          </el-select>
                        </div>
                        <div class="ml10">
                          <span class="mr10">播放完后</span>
                          <el-select v-model="item.overBreak" style="width: 120px">
                            <el-option
                              :label="item"
                              :value="item"
                              v-for="(item, index) in palyoverArry"
                              :key="index"
                            ></el-option>
                          </el-select>
                        </div>
                      </div>
                      <div class="d-flex" v-if="item.eventFn == '跳转链接'">
                        <div style="width: 45px; margin-left: 10px">链接</div>
                        <el-input v-model="item.eventParams" placeholder="请输入链接"></el-input>
                      </div>
                      <div class="d-flex" v-if="item.eventFn == '拨打电话'">
                        <div style="width: 45px; margin-left: 10px">电话</div>
                        <el-input v-model="item.eventParams" placeholder="请输入电话"></el-input>
                      </div>
                    </div>
                  </el-form-item>
                </div>
              </div>
            </div>
          </el-form-item>
        </el-form>
        <div class="modal-footer" style="justify-content: center; padding-bottom: 20px">
          <el-button type="primary" size="medium" round @click="determine_btn()">确定</el-button>
        </div>
      </div>
    </model>
  </div>
</template>

<script>
import cBtn from './custom_btn'
import btnAttr from './js/btn_attr'
import eventFnList from './js/btn_event'
import enLetter from './js/en_letter'

export default {
  name: 'videoInteration',
  props: {
    materials: {
      type: Array
    },
    interact_config_index: {
      type: Number
    },
    btnListFromEdit_pad: {
      type: Array
    }
  },
  data() {
    let _this = this
    return {
      material: {},
      btnList: [],
      btnAcount: 2,
      jumpTo: 'A',
      btnAttr: btnAttr(),
      eventFnList: eventFnList,
      playArry: ['自动播放上一段', '自动播放下一段'],
      // fragment: ['A', 'B', 'C'],
      fragment: (function() {
        // console.log(_this.materials.length)
        return enLetter.slice(0, _this.materials.length)
      })(),
      palyoverArry: ['暂停', '播放'],
      play_auto: '自动播放下一段',
      radio_event: '1',
      playover: '暂停',
      link: '',
      form: {}
    }
  },
  components: {
    cBtn
  },

  computed: {},

  watch: {
    // btnAcount() {
    //   this.setbtnList()
    // }
  },

  methods: {
    setbtnList() {
      this.btnList = []
      for (let i = 0; i < this.btnAcount + 1; i++) {
        let obj = btnAttr()
        obj.text += i + 1
        this.btnList.push(obj)
      }
    },
    handleEditText(item, j) {
      item.editable = true
      item.popoverVisible = false
      // this.$nextTick(() => {
      //   document.getElementsByClassName('edit-text-input')[j].focus()
      // })
    },
    // 编辑按钮文本聚焦
    editTextInputFocus(event) {
      // focus取消冒泡
      event.stopPropagation()
    },
    editTextInputBlur(event, item) {
      let value = event.target.value
      item.text = value || item.text
      item.editable = false
    },
    fragmentChange() {},
    close() {
      this.$emit('Modalclose', 'videoName')
    },
    determine_btn() {
      var params = {
        btnList: this.btnList,
        radio_event: this.radio_event,
        playArry: this.playArry
      }
      this.$emit('setInteractionData', params)
      this.close()
    }
  },

  created() {
    if (this.btnListFromEdit_pad.length) this.btnList = this.btnListFromEdit_pad
    else this.setbtnList()
    if (this.interact_config_index >= 0) this.material = this.materials[this.interact_config_index]
  },

  mounted() {}
}
</script>

<style scoped lang="scss">
#videoInteration {
  .custom-btn {
    width: 120px;
    height: 36px;
    background-color: #4c4c4c;
  }
  .el-dropdown-menu .custom-btn {
    background-color: #4c4c4c;
  }
  .edit-text-input {
    width: 100%;
    color: #fff;
    background-color: #4c4c4c;
    background: none;
    outline: none;
    border: none;
  }
  // footer
  .modal-footer {
    padding-bottom: 10px;
    > button {
      background-color: #4c4c4c;
      border-color: #4c4c4c;
    }
  }
}
/deep/ .modal .modal-wrap {
  min-width: 850px;
}
.ml10 {
  margin-left: 10px;
}
.mr10 {
  margin-right: 10px;
}
.mt10 {
  margin-top: 10px;
}
// header
.coverBox {
  position: relative;
  .img_cover {
    width: 100%;
    height: 350px;
    background: #ddd;
    .coverBtns {
      width: 100%;
      height: 36px;
      display: flex;
      position: absolute;
      bottom: 60px;
      left: 0;
      justify-content: space-evenly;
      box-sizing: border-box;
    }
    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
    .video-cut-wrap {
      height: 100%;
    }
  }
}
// content
.fromBox {
  padding-bottom: 10px;
  margin-top: 20px;
  .fromlist {
    display: flex;
  }
  /deep/ .el-radio__inner:hover {
    border-color: #215fd1;
  }

  /deep/ .el-radio__label {
    font-weight: normal !important;
  }

  /deep/ .el-radio__input.is-checked + .el-radio__label {
    color: #101010;
  }

  /deep/ .el-radio__input.is-checked .el-radio__inner {
    border-color: #215fd1;
    background: #215fd1;
  }
  /deep/ .el-dropdown {
    width: 200px;
    text-align: right;
  }
}
</style>
