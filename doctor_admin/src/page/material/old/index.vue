<template>
  <div class="material_wrap">
    <div class="columnList">
      <column :channelData='channelData' :columnData="columnData"></column>
    </div>
    <div class="searchBox">
      <div class="d-flex">
        <div class="message">
          <el-input
            placeholder="输入用户昵称或手机号搜索"
            v-model="title"
            clearable>
          </el-input>
          <div class="search_img">
            <img src="../../assets/images/search.png" alt="">
          </div>
        </div>
        <div class="message">
          <el-input
            placeholder="输入用户昵称或手机号搜索"
            v-model="video"
            clearable>
          </el-input>
          <div class="search_img">
            <img src="../../assets/images/search.png" alt="">
          </div>
        </div>
        <div class="message">
          <el-input
            placeholder="输入用户昵称或手机号搜索"
            v-model="name"
            clearable>
          </el-input>
          <div class="search_img">
            <img src="../../assets/images/search.png" alt="">
          </div>
        </div>
      </div>
      <div class="topList">
        <div class="time">
          <span class="labelTitle">剪辑时间:</span>
          <el-date-picker
            v-model="date"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>
        </div>
        <div class="btn">
          <el-button round size="small">重置</el-button>
          <el-button type="primary" round size="small">导出表格</el-button>
        </div>
      </div>
    </div>
    <tabList></tabList>
    <div class="tableBox">
      <el-table :data="tableData">
        <el-table-column prop="name" label="原视频标题" width="180" fixed="left"></el-table-column>
        <el-table-column prop="telephone" label="栏目" width="180"></el-table-column>
        <el-table-column prop="number" label="频道" width="120"></el-table-column>
        <el-table-column prop="video" label="信息" width="180"></el-table-column>
        <el-table-column prop="down_video" label="时长" width="130"></el-table-column>
        <el-table-column prop="release" label="剪辑时间" width="180"></el-table-column>
        <el-table-column prop="number" label="用户昵称" width="120"></el-table-column>
        <el-table-column prop="video" label="用户账号" width="120"></el-table-column>
        <el-table-column prop="down_video" label="生成视频标题" width="180"></el-table-column>
        <el-table-column prop="release" label="发布至账号" width="120"></el-table-column>
        <el-table-column prop="option" label="操作" width="120">
          <template slot-scope="scope">
            <a style="color:#2d86e4;margin-right:10px" @click="play(scope)">播放</a>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import column from './column'
import tabList from './tabList'
export default {
  name: '',
  data() {
    return {
      title: '',
      video: '',
      name: '',
      date: '',
      channelData: [{ name: 'nsbvsbvksv' }],
      columnData: [{ name: 'mnsvbvbkvbk' }],
      tableData: [],
    }
  },
  components: {
    column,
    tabList,
  },
  computed: {},
  watch: {},
  methods: {},
  created() {},
  mounted() {},
}
</script>

<style lang="scss" scoped>
@media screen and (max-width: 768px) {
  .material_wrap {
    max-width: 1000px;
    min-width: 1000px;
  }
}
@media screen and (max-width: 1200px) and (min-width: 768px) {
  .material_wrap {
    max-width: 1200px;
    min-width: 1200px;
  }
}
.material_wrap {
  // width: 100%;
  height: 100%;
  background: #ffffff;
  padding: 15px;
  box-sizing: border-box;
  .searchBox {
    .message {
      position: relative;
      display: flex;
      margin: 0 10px;
      margin-top: 10px;
      /deep/ .el-input__inner {
        height: 36px;
        min-width: 350px;
        font-size: 12px;
      }
      .search_img {
        width: 56px;
        height: 36px;
        border-radius: 3px;
        background-color: rgba(43, 121, 223, 100);
        text-align: center;
        line-height: 36px;
        img {
          width: 24px;
          height: 24px;
        }
      }
    }
    .topList {
      display: flex;
      .time {
        margin-top: 20px;
        margin-right: 100px;
        display: flex;
        .labelTitle {
          min-width: 80px;
          margin-top: 10px;
        }
        /deep/ .el-date-editor--daterange.el-input,
        .el-date-editor--daterange.el-input__inner,
        .el-date-editor--timerange.el-input,
        .el-date-editor--timerange.el-input__inner {
          width: auto;
          min-width: 100px;
        }
      }
      .btn {
        margin-top: 20px;
        display: flex;
      }
    }
  }
  .tableBox {
    margin-top: 20px;
  }
}
</style>
