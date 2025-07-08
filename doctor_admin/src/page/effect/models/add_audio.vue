<template>
  <div class="addadiuo_wrap">
    <div class="div_item">
      <span>背景音乐:</span>
      <div>
        <upload :button_name="'上传音乐'"
          @getFileUrl="getbannerAudio"
          :file_type='file_type' />
        <audio :src="aduioUrl" controls='controls'></audio>
      </div>
    </div>
    <div class="div_item">
      <span class="mt-10px">音乐分类:</span>
      <el-select v-model="value" placeholder="选择音乐分类">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
    </div>
    <div class="btns">
      <el-button type="primary" round size="small" @click="determine()">确定</el-button>
    </div>
  </div>
</template>
<script>
export default {
  name: '',
  props: {
    layerid: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      value: '',
      aduioUrl: '',
      file_type: 'al',
      options: [
        {
          label: '网红',
          value: '网红',
        },
      ],
    }
  },
  components: {},
  computed: {},
  watch: {},
  methods: {
    getbannerAudio(url, file) {
      this.aduioUrl = url
    },
    determine() {
      this.$layer.close(this.layerid)
    },
  },
  created() {},
  mounted() {},
}
</script>

<style lang="scss" scoped>
.addadiuo_wrap {
  width: 100%;
  padding: 20px;
  .div_item {
    display: flex;
    margin-top: 20px;
    audio {
      width: 468px;
      height: 80px;
      margin-top: 5px;
    }
    span {
      min-width: 70px;
    }
  }
  .btns {
    margin-top: 30px;
    text-align: center;
    /deep/ .el-input {
      margin-top: 0;
      /deep/ .el-input__inner {
        height: 30px;
      }
    }
  }
}
</style>
