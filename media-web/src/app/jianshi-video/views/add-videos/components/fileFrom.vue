<!--
 * @Author: your name
 * @Date: 2021-09-10 14:46:40
 * @LastEditTime: 2021-09-30 15:38:11
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/add-videos/components/fileFrom.vue
-->
<template>
  <div class="file-from">
    <div class="title">内容选择</div>
    <div class="content">
      <div class="lable">字幕来源</div>
      <el-select
        v-model="source"
        value-key="id"
        placeholder=""
        filterable
      >
        <el-option
          v-for="item in sourceOptions"
          :key="item.id"
          :label="item.val"
          :value="item.id"
        />
      </el-select>
    </div>
    <div class="title">
      语言设置
      <el-checkbox v-model="translateCheckout" :indeterminate="false">进行字幕翻译</el-checkbox>
    </div>
    <div class="content">
      <div class="lable">视频内容语种</div>
      <el-select
        v-model="language"
        value-key="id"
        placeholder=""
        filterable
      >
        <el-option
          v-for="item in languageOptions"
          :key="item.id"
          :label="item.val"
          :value="item.id"
        />
      </el-select>

    </div>
    <div v-show="translateCheckout" class="content">
      <div class="lable">翻译语种</div>
      <el-select
        v-model="translate_language"
        value-key="id"
        placeholder="请选择要翻译的语种"
        filterable
      >
        <el-option
          v-for="item in tLanguageOptions"
          :key="item.id"
          :label="item.val"
          :value="item.id"
        />
      </el-select>

    </div>
    <div v-show="translateCheckout" class="content">
      <div class="lable">输出语种</div>
      <el-select
        v-model="output_language"
        value-key="id"
        placeholder="请选择要翻译的语种"
        filterable
      >
        <el-option
          v-for="item in outputOptions"
          :key="item.id"
          :label="item.val"
          :value="item.id"
        />
      </el-select>

    </div>
    <div class="title">语气词过滤</div>
    <div class="content">
      <el-checkbox v-model="filterCheckout" :indeterminate="false">自动过滤语气助词（啊、嗯、额、哦、等）</el-checkbox>
    </div>
    <div class="btn-wrap">
      <!-- <el-button type="primary" size="small" :loading="loading" @click="analysis">开始分析</el-button> -->
      <!-- <el-button type="info" size="small">取消</el-button> -->
    </div>

  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    loading: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      translateCheckout: false,
      filterCheckout: false,
      translate_language: '',
      source: 1,
      sourceOptions: [
        {
          id: 1,
          val: '语音'
        }
        // {
        //   id: 2,
        //   val: '视频字幕'
        // }
      ],
      language: 2,
      languageOptions: [
        {
          id: 1,
          val: '英文'
        },
        {
          id: 2,
          val: '中文'
        }
      ],
      output_language: 3,
      outputOptions: [
        {
          id: 3,
          val: '双语'
        },
        {
          id: 2,
          val: '中文'
        },
        {
          id: 1,
          val: '英文'
        }
      ]
    }
  },
  computed: {
    tLanguageOptions() {
      return this.languageOptions.filter(item => item.id !== this.language)
    }
  },
  watch: {
    tLanguageOptions: {
      handler: function(val) {
        this.translate_language = val[0].id
      },
      deep: true,
      immediate: true
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {
    analysis() {
      const params = {
        language: this.language,
        source: this.source,
        modal_flag: this.filterCheckout ? 1 : 0,
        translate_language: this.translateCheckout ? this.translate_language : 0,
        display_language: this.translateCheckout ? this.output_language : 0
      }
      this.$emit('analysis', params)
    }
  }
}
</script>

<style scoped lang="scss">
.file-from {
  flex: 1;
  background: #fff;
  margin-left: 24px;
  padding: 30px;
  position: relative;

  .title {
    font-size: 14px;
    font-weight: 600;
    color: #404040;
    line-height: 14px;
    margin-bottom: 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .content {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;

    .lable {
      flex-shrink: 0;
      font-size: 14px;
      font-weight: 400;
      color: #404040;
      width: 100px;
    }

    ::v-deep.el-select {
      flex: 1;
      height: 30px;

      .el-input ,
      .el-input__inner {
        width: 100%;
        height: 30px;}

      .el-input__icon {
        display: flex;
        justify-content: center;
        align-items: center;

        &::before {
          content: " ";
          width: 0;
          height: 0;
          border-left: 3px solid transparent;
          border-right: 3px solid transparent;
          border-top: 5px solid black;
        }
      }
    }
  }

  .btn-wrap {
    position: absolute;
    bottom: -50px;
    right: 0;
  }
}

</style>
