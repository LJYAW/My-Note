<template>
  <div class="back_wrap">
    <div class="top">
      <ul class="tablist">
        <li>音乐分类:</li>
        <li v-for="(item,index) in tablist" :key="index">
          {{item.name}}
        </li>
      </ul>
      <el-button type="primary" plain size="small" @click="add_aduio()">添加音色</el-button>
    </div>
    <div class="table">
      <table class="table" style="table-layout:fixed;width:100%;">
        <thead>
          <tr>
            <th>背景音乐名称</th>
            <th>分类</th>
            <th>时长</th>
            <th>添加时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody class="tbody">
          <tr v-for="(item,index) in tableData" :key="index">
            <td>1</td>
            <td>2</td>
            <td>3</td>
            <td>3</td>
            <td>
              <a style="color:#2d86e4;margin-right:10px" @click="play(scope)">播放</a>
              <a style="color:#2d86e4;margin-right:10px" @click="play(scope)">修改</a>
              <a style="color:#2d86e4;margin-right:10px" @click="play(scope)">禁用</a>
              <a style="color:#2d86e4;margin-right:10px" @click="play(scope)">删除</a>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import addAduio from '../models/add_audio'
export default {
  name: '',
  data() {
    return {
      tableData: [],
      tablist: [
        {
          name: '全部',
        },
        {
          name: '新闻',
        },
        {
          name: '振奋',
        },
        {
          name: '甜美',
        },
        {
          name: '舒缓',
        },
        {
          name: '浪漫',
        },
        {
          name: '欢快',
        },
        {
          name: '悲伤',
        },
      ],
    }
  },
  components: {},
  computed: {},
  watch: {},
  methods: {
    // 添加音乐
    add_aduio() {
      let obj = {
        content: addAduio,
        title: '添加音乐',
        width: '600px',
        height: '310px',
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
