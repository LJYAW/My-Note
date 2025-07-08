<!--
 * @Author: your name
 * @Date: 2021-05-28 16:27:01
 * @LastEditTime: 2021-05-28 16:50:19
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/view/task_mgt/model/addPathDialog.vue
-->
<template>
  <el-form :model="form" :rules="rules" label-width="120px" label-position="left">

    <el-form-item label="路径来源" prop="pathSource">
      <el-input v-model="form.pathSource" placeholder="请输入路径来源" />
    </el-form-item>

    <el-form-item label="路径" prop="path">
      <el-checkbox-group v-model="form.path">
        <el-checkbox v-for="item in pathData" :key="item" :label="item" />
      </el-checkbox-group>
    </el-form-item>

    <el-form-item v-if="form.path.includes('列表页')" label="列表页爬取地址" prop="listUrl">
      <el-input v-model="form.listUrl" placeholder="请输入列表页 URL" />
    </el-form-item>

    <el-form-item v-if="form.path.includes('播放页')" label="播放页爬取地址" prop="playUrl">
      <el-input v-model="form.playUrl" placeholder="请输入播放页 URL" />
    </el-form-item>

    <div class="btn-wrap">
      <base-btn @click="submit">确定</base-btn>
      <base-btn type="info" @click="close">取消</base-btn>
    </div>

  </el-form>
</template>

<script>
import rules from '../js/addPathRules'
export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      rules: rules,
      form: {
        pathSource: '',
        path: []
      },
      pathData: ['列表页', '播放页']
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
    close() {
      this.$emit('close')
    },
    submit() {
      console.log('新增路径来源')
      this.$emit('close')
    }
  }
}
</script>

<style scoped lang="scss">
.btn-wrap{
    margin-top: 40px;
    text-align: center;
}
</style>
