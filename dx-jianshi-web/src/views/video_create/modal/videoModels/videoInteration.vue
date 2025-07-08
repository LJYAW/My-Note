<template>
  <div>
    <model :id="'videoInteration'" ref="videoInteration" @close="close">
      <div slot="title">互动效果编辑</div>
      <div slot="body">
        <!-- 封面展示 -->
        <div class="coverBox">
          <div class="img_cover">
            <img src alt />
            <div class="coverBtns">
              <c-btn :text="item" v-for="(item, index) in btnsArry" :key="index"></c-btn>
            </div>
          </div>
        </div>
        <!-- from交互 -->
        <el-form ref="form" :model="form" :inline="true" class="demo-form-inline fromBox">
          <el-form-item label="按钮数量">
            <el-select v-model="form.num">
              <el-option
                :label="index + 1 + '个'"
                :value="index"
                v-for="(item, index) in 10"
                :key="index"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="按钮样式" style="margin:0 60px">
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
                  <div class="d-flex flex-wrap" style="width:280px">
                    <c-btn
                      v-for="(item, index) in 10"
                      :key="index"
                      :text="'样式' + (index + 1)"
                      style="margin-top:10px;margin-left:10px"
                    ></c-btn>
                  </div>
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </el-form-item>
          <el-form-item label="触发事件">
            <el-radio v-model="form.radio" label="1">点击按钮后跳转</el-radio>
          </el-form-item>
          <el-form-item label style="margin-left:30px">
            <el-radio v-model="form.radio" label="2">自动</el-radio>
            <el-select v-model="form.play_select">
              <el-option
                :label="item"
                :value="index + 1"
                v-for="(item, index) in playArry"
                :key="index"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label>
            <div class="fromlist">
              <span style="margin-right:4px">按钮设置</span>
              <div>
                <div class="d-flex" v-for="(item, index) in btnsArry" :key="index">
                  <el-form-item label>
                    <c-btn :text="item"></c-btn>
                  </el-form-item>
                  <el-form-item label="点击后跳转至片段">
                    <el-select v-model="form.fragment" style="width:100px">
                      <el-option
                        :label="item"
                        :value="index + 1"
                        v-for="(item, index) in fragmentArry"
                        :key="index"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item label="播放完后">
                    <el-select v-model="form.playover" style="width:120px">
                      <el-option
                        :label="item"
                        :value="index + 1"
                        v-for="(item, index) in palyoverArry"
                        :key="index"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                </div>
              </div>
            </div>
          </el-form-item>
        </el-form>
        <div class="modal-footer" style="justify-content: center;padding-bottom:20px">
          <el-button type="primary" size="medium" round @click="determine_btn()">确定</el-button>
        </div>
      </div>
    </model>
  </div>
</template>

<script>
import cBtn from './custom_btn'
export default {
  name: 'videoInteration',
  props: [],
  data() {
    return {
      btnsArry: ['去', '不去', '再看会'],
      playArry: ['自动播放上一段', '自动播放下一段'],
      fragmentArry: ['A', 'B', 'C', 'D'],
      palyoverArry: ['暂停', '播放', '跳转至A', '跳转至B'],
      form: {
        num: 2,
        play_select: 1,
        radio: '1',
        fragment: 1,
        playover: 1
      }
    }
  },
  components: {
    cBtn
  },

  computed: {},

  watch: {},

  methods: {
    close() {
      this.$emit('close')
    },
    determine_btn() {}
  },

  created() {},

  mounted() {}
}
</script>

<style scoped lang="scss">
// header
.coverBox {
  position: relative;
  .img_cover {
    width: 100%;
    height: 350px;
    background: #ddd;
    .coverBtns {
      width: 100%;
      display: flex;
      position: absolute;
      bottom: 60px;
      left: 0;
      justify-content: space-evenly;
    }
    img {
      width: 100%;
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
}
// footer
.modal-footer {
  padding-bottom: 10px;
}
</style>
