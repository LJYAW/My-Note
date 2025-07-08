<template>
  <div class="play_wrap">
    <div>
      <el-button type="primary" plain size="small" @click="add_host()">添加虚拟主持人</el-button>
    </div>
    <div class="table">
      <table class="table" style="table-layout:fixed;width:100%;">
        <thead>
          <tr>
            <th>样式</th>
            <th>名称</th>
            <th>添加时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody class="tbody">
          <tr v-for="(item,index) in tableData" :key="index">
            <td>1</td>
            <td>2</td>
            <td>3</td>
            <td>
              <a style="color:#2d86e4;margin-right:10px" @click="play(item)">播放</a>
              <a style="color:#2d86e4;margin-right:10px" @click="play(item)">禁用</a>
              <a style="color:#2d86e4;margin-right:10px" @click="play(item)">删除</a>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import addHost from '../models/add_host'
export default {
  name: '',
  data() {
    return {
      tableData: [],
    }
  },
  components: {},
  computed: {},
  watch: {},
  methods: {
    add_host() {
      let obj = {
        content: addHost,
        title: '添加虚拟主持人',
        width: '600px',
        height: '561px',
        data: {},
      }
      this.$layerIfream(obj)
    },
  },
  created() {},
  mounted() {},
}
</script>

<style lang="scss" scoped>
.play_wrap {
  .table {
    margin-top: 30px;
  }
}
</style>
