<!--
 * @Author: your name
 * @Date: 2021-05-27 15:33:02
 * @LastEditTime: 2021-05-28 15:16:00
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/view/videoAdmin/index.vue
-->
<!--  -->
<template>
  <div class="video-warp">
    <div class="num-list">
      <div v-for="(item,index) in numList" :key="index" class="item-num">
        <div>{{ item.label }}:</div>
        <div>{{ item.val }}个</div>
      </div>
    </div>
    <div class="video-count">
      <el-form :inline="true" :model="form">
        <el-form-item label="路径名称">
          <el-input v-model="form.name" placeholder="请输入视频名称" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" clearable filterable placeholder="请选择状态">
            <el-option
              v-for="item in statusList"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <base-btn class="search-btn" size="big" @click="searchData">查询</base-btn>
        <base-btn class="search-btn create-btn" size="big" @click="clearSearch">清除</base-btn>
      </el-form>
    </div>
    <div class="total">
      <div>当前条件下共 110000条数据</div>
      <base-btn type="text" @click="exportExcel()">导出EXCEL</base-btn>
    </div>
    <el-table :data="tableData" border>
      <el-table-column prop="id" width="80" label="ID" />
      <el-table-column prop="status" label="状态" />
      <el-table-column prop="name" label="路径名称" />
      <el-table-column prop="playtime" label="路径" />
      <el-table-column prop="introduction" label="URL规范" />
      <el-table-column prop="item_name" label="绑定栏目数" />
      <el-table-column prop="channel_name" label="成功视频数" />
      <el-table-column prop="fileName" label="失败视频数" />
      <el-table-column prop="fileName" label="失败率" />
      <el-table-column prop="fileName" label="最新爬取时间" />
      <el-table-column prop="fileName" label="解析人" />
      <el-table-column label="操作" width="300">
        <template>
          <base-btn type="text" @click="showDialog('解析字段','ParsingFields')">解析字段</base-btn>
          <base-btn type="text">重新解析</base-btn>
          <base-btn type="text">删除</base-btn>
          <base-btn type="text" @click="showDialog('查看解析字段','ParsingFields')">查看解析字段</base-btn>
          <base-btn type="text" @click="showDialog('输入解析人','SeeFields')">输入解析人</base-btn>
        </template>
      </el-table-column>
    </el-table>
    <base-pag :total="total" :limit="limit" :page="page" @current-change="handleCurrentChange" @size-change="handleSizeChange" />
    <!-- 弹窗 -->
    <BaseDialog
      :title="dialogTit"
      :show.sync="dialogVisible"
      width="600px"
      class="dialog"
    >
      <compontents
        :is="dialogCompomtentsName"
        @showDialog="showDialog"
        @close="close"
      />
    </BaseDialog>
  </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from '《组件路径》';
import ParsingFields from './model/ParsingFields'
import SeeFields from './model/SeeFields'
export default {
// import引入的组件需要注入到对象中才能使用
  components: { ParsingFields, SeeFields },
  data() {
    // 这里存放数据
    return {
      numList: [
        {
          label: '待解析开发',
          val: 20
        },
        {
          label: '已解析',
          val: 20
        },
        {
          label: '故障',
          val: 20
        },
        {
          label: '无法解析',
          val: 20
        }
      ],
      dialogTit: '',
      dialogVisible: false,
      dialogCompomtentsName: '',
      form: {
        name: '',
        status: ''
      },
      total: null,
      limit: 10,
      page: 1,
      statusList: ['2', '1'],
      channelData: ['1', '2'],
      itemData: [6, 9],
      tableData: [
        {
          status: 'sdmbjdvjkd'
        }
      ]
    }
  },
  // 监听属性 类似于data概念
  computed: {},
  // 监控data中的数据变化
  watch: {},
  // 生命周期 - 创建完成（可以访问当前this实例）
  created() {

  },
  // 生命周期 - 挂载完成（可以访问DOM元素）
  mounted() {

  },
  beforeCreate() {}, // 生命周期 - 创建之前
  beforeMount() {}, // 生命周期 - 挂载之前
  beforeUpdate() {}, // 生命周期 - 更新之前
  updated() {}, // 生命周期 - 更新之后
  beforeDestroy() {}, // 生命周期 - 销毁之前
  destroyed() {}, // 生命周期 - 销毁完成
  activated() {},
  // 方法集合
  methods: {
    searchData() {},
    clearSearch() {
      Object.keys(this.form).forEach(item => {
        this.form[item] = ''
      })
    },
    exportExcel() {},
    handleCurrentChange(val) {
      this.page = val
    },
    handleSizeChange(val) {
      this.limit = val
    },
    // 展示dialog
    showDialog(title, name) {
      this.dialogTit = title
      this.dialogCompomtentsName = name
      this.dialogVisible = true
    },
    close() {
      this.dialogVisible = false
    }
  } // 如果页面有keep-alive缓存功能，这个函数会触发
}
</script>
<style lang='scss' scoped>
//@import url(); 引入公共css类
.video-warp{
  padding: 20px;
  box-sizing: border-box;
  .num-list{
      display: flex;
      margin-bottom: 30px;
      font-size: 15px;
      color: #4485DB;
      .item-num{
         display: flex;
         margin-right: 30px;
         div:nth-child(2){
             margin-left: 6px;
         }
      }
  }
  .total{
    display: flex;
    justify-content: space-between;
    font-size: 14px;
    color: #101010;
    margin-bottom: 10px;
  }
}
</style>
