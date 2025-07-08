<template>
  <div class="back_wrap">
    <div class="top">
      <el-button type="primary" plain size="small" @click="film()">片尾</el-button>
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
import addFilm from '../models/add_film'
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
    film() {
      let obj = {
        content: addFilm,
        title: '添加片尾',
        width: '600px',
        height: '485px',
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
.back_wrap {
  .top {
    .tablist {
      display: flex;
      margin: 30px 0;
      li {
        margin: 0 10px;
      }
    }
  }
  .table {
    margin-top: 20px;
  }
}
</style>
