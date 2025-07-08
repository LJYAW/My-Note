<!--
 * @Author: your name
 * @Date: 2021-05-27 18:27:25
 * @LastEditTime: 2021-05-28 11:45:10
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/view/task_mgt/model/analysisDialog.vue
-->
<template>
  <div class="analysis-dialog">
    <!-- 解析字段表单 -->
    <el-form :model="form" label-width="90px" label-position="left">

      <el-form-item label="视频名称" prop="videoName">
        <el-select v-model="form.videoName" placeholder="请选择页面" size="mini">
          <el-option v-for="item in videoType" :key="item" :label="item" :value="item" />
        </el-select>
        <el-form-item prop="videoName">
          <div contenteditable="true" class="contenteditable-wrap" />
        </el-form-item>
      </el-form-item>

      <el-form-item label="开播时间" prop="startTime">
        <el-select v-model="form.startTime" placeholder="请选择页面" size="mini">
          <el-option v-for="item in videoType" :key="item" :label="item" :value="item" />
        </el-select>
        <el-form-item>
          <div contenteditable="true" class="contenteditable-wrap" />
        </el-form-item>
      </el-form-item>

      <el-form-item label="集/期数" prop="num">
        <el-select v-model="form.num" placeholder="请选择页面" size="mini">
          <el-option v-for="item in videoType" :key="item" :label="item" :value="item" />
        </el-select>
        <el-form-item>
          <div contenteditable="true" class="contenteditable-wrap" />
        </el-form-item>
      </el-form-item>

      <el-form-item label="视频介绍" prop="desc">
        <el-select v-model="form.desc" placeholder="请选择页面" size="mini">
          <el-option v-for="item in videoType" :key="item" :label="item" :value="item" />
        </el-select>
        <el-form-item>
          <div contenteditable="true" class="contenteditable-wrap" />
        </el-form-item>
      </el-form-item>

      <el-form-item label="封面图" prop="banner">
        <el-select v-model="form.banner" placeholder="请选择页面" size="mini">
          <el-option v-for="item in videoType" :key="item" :label="item" :value="item" />
        </el-select>
        <el-form-item>
          <div contenteditable="true" class="contenteditable-wrap" />
        </el-form-item>
      </el-form-item>
    </el-form>

    <div v-if="title==='解析字段'" class="btn-wrap">
      <base-btn>确定</base-btn>
      <base-btn type="info">取消</base-btn>
    </div>

  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    title: {
      type: String,
      default() {
        return ''
      }
    }
  },
  data() {
    return {
      form: {
        videoName: '',
        startTime: '',
        num: '',
        desc: '',
        banner: ''
      },
      videoType: ['列表', '播放']
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.title === '查看解析字段' && this.getDialogData()
  },
  mounted() {

  },
  methods: {
    getDialogData() {
      console.log('获取查看解析字段数据')
    }
  }
}
</script>

<style scoped lang="scss">
.analysis-dialog{
  .el-form{
    height: 60vh;
    overflow-y: auto;
    .el-select{
      width: 100%;
    }
    .contenteditable-wrap{
      width: 100%;
      height: 80px;
      border: 1px solid rgba(187, 187, 187, 100);
      margin-top: 10px;
      padding: 10px;
    }
  }
  .btn-wrap{
    margin-top: 20px;
    text-align: center;
  }
}
</style>
