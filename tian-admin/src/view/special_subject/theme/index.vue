<!--
 * @Author: your name
 * @Date: 2021-04-01 16:53:15
 * @LastEditTime: 2021-04-22 19:31:55
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /vue-beautiful-template/src/view/home/content/index.vue
-->
<template>
  <div ref="wrap" class="special">
    <div class="search-wrap">
      <div class="search-input">
        <el-input
          v-model="searchVal"
          size="normal"
          clearable
          placeholder="搜索专题名称or公司名称"
          @change="handleChange"
        />
        <div class="seachBtn" icon="el-icon-search">
          <i class="el-icon-search" style="color: white" />
        </div>
      </div>

      <div class="search-select">
        <span>状态：</span>
        <el-select v-model="state" value-key="" clearable filterable>
          <el-option
            v-for="item in selectOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </div>

      <div class="search-select">
        <span>发布端：</span>
        <el-select v-model="terminal" value-key="" clearable filterable>
          <el-option
            v-for="item in terminalOption"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </div>

      <el-button
        type="primary"
        size="default"
        @click="account = true"
      >新建专题</el-button>
    </div>

    <div class="table-wrap">
      <!-- :fit="false" -->
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="specialName" label="名称" />
        <el-table-column prop="companyName" label="公司名称" />
        <el-table-column prop="createTime" label="创建时间" />
        <el-table-column prop="endTime" label="到期时间" />
        <el-table-column prop="updataTime" label="更新时间" />
        <el-table-column prop="visitNum" label="访问数量" />
        <el-table-column prop="state" label="状态" />
        <el-table-column prop="terminal" label="发布端" />
        <el-table-column fixed="right" label="操作" style="position: relative">
          <template slot-scope="scope">
            <el-button
              type="text"
              size="small"
              @click="handleClick(scope.row)"
            >修改</el-button>
            <el-popover placement="bottom" width="40" trigger="click">
              <div class="content">
                <div class="red" @click="handleClick(scope.row)">下架</div>
                <div class="blue" @click="handleClick(scope.row)">复制</div>
                <div class="red">开通下载</div>
                <div class="blue">删除</div>
              </div>
              <i slot="reference" class="el-icon-setting" />
            </el-popover>
          </template>
        </el-table-column>

        <el-table-column prop="downLoad" label="下载权限" />
      </el-table>
    </div>
    <el-dialog
      title="新建账号"
      :visible.sync="account"
      center
      @close="account = false"
    >
      <el-form
        ref="form"
        :model="form"
        label-width="90px"
        :inline="false"
        class="addForm"
      >
        <el-form-item label="公司名称:">
          <el-input
            v-model="form.name"
            placeholder="请输入公司名称"
            size="medium"
            clearable
          />
        </el-form-item>
        <el-form-item label="手机号:">
          <el-input
            v-model="form.mobile"
            placeholder="请输入手机号"
            size="medium"
            clearable
          />
        </el-form-item>

        <el-form-item label="密码:">
          <el-input
            v-model="form.password"
            placeholder="请输入密码"
            size="medium"
            clearable
          />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button type="primary" @click="add">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'Special',
  components: {},
  props: {},
  data() {
    return {
      searchVal: '',
      selectOptions: [
        { lable: '全部', value: '全部' },
        { lable: '已发布', value: '已发布' },
        { lable: '已下架', value: '已下架' }
      ],
      terminalOption: [
        { lable: '全部', value: '全部' },
        { lable: 'web端', value: 'web端' },
        { lable: '手机端', value: '手机端' },
        { lable: '大屏端', value: '大屏端' }
      ],
      state: '全部',
      terminal: '全部',
      tableData: [
        {
          specialName: '专题名称1',
          companyName: '公司名称1',
          createTime: '创建时间1',
          endTime: '到期时间1',
          updataTime: '更新时间1',
          visitNum: '访问数量1',
          state: '状态1',
          terminal: '发布端1',
          downLoad: '已开通'
        }
      ],
      visible: false,
      account: false,
      form: {
        name: '',
        mobile: '',
        password: ''
      }
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    handleChange() {},
    handleClick(e) {
      console.log(e)
    },
    add() {
      this.$router.push({
        path: '/addTheme'
      })
    }
  }
}
</script>

<style scoped lang="scss">
.special {
  padding: 27px;
  .search-wrap {
    display: flex;
    position: relative;
    span {
      color: rgba(16, 16, 16, 100);
      font-size: 14px;
    }
    .search-input {
      width: 308px;
      height: 36px;
      display: flex;
      .seachBtn {
        position: relative;
        z-index: 2;
        margin-left: -8px;
        width: 56px;
        height: 40px;
        border-radius: 3px;
        background-color: rgba(42, 112, 193, 100);
        color: rgba(255, 255, 255, 100);
        font-size: 16px;
        text-align: center;
        line-height: 40px;
        box-shadow: 2px 5px 10px 0px rgba(0, 0, 0, 0.1);
      }
    }
    .search-select {
      margin-left: 31px;
    }
    .el-button--primary {
      background: rgba(42, 112, 193, 100);
      width: 100px;
      position: absolute;
      right: 0;
    }
  }
  .table-wrap {
    width: 100%;
    margin-top: 26px;
    .el-icon-setting {
      margin-left: 5px;
      color: #000;
      cursor: pointer;
      width: 5px;
      height: 5px;
    }
  }
}
::v-deep.el-popover {
  min-width: 96px;
}
::v-deep.content {
  div {
    text-align: center;
    min-height: 25px;
  }
  .red {
    color: rgba(191, 17, 17, 100);
  }
  .blue {
    color: rgba(0, 116, 231, 100);
  }
}
</style>
