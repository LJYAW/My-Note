<!--
 * @Author: zll
 * @Date: 2020-12-03 11:42:51
 * @LastEditTime: 2020-12-03 18:01:24
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /weijian_cms_web/src/page/material/new/index.vue
-->
<template>
  <div class="material_wrap">
    <el-breadcrumb>
      <el-breadcrumb-item to="/material">素材管理</el-breadcrumb-item>
      <el-breadcrumb-item to="/material/tags_list">标签列表</el-breadcrumb-item>
      <el-breadcrumb-item>{{this.$route.query.name}}</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="top">
      <div class="top_item d-flex">
        <el-input placeholder="请输入内容" v-model="form.keywords">
          <el-button slot="append" icon="el-icon-search" class="btn"></el-button>
        </el-input>
        <el-input placeholder="请输入内容" v-model="form.author" class="ml-100px mr-100px">
          <el-button slot="append" icon="el-icon-search" class="btn"></el-button>
        </el-input>
        <div class="reset">
          <el-button type="primary" size="small">重置</el-button>
        </div>
      </div>
    </div>
    <div class="tab_list">
      <div v-if="tablist.length==0">暂无数据</div>
      <ul>
        <li v-for="(item,index) in tablist" :key="index">
          <div class="status" @click="status(item)">{{item.status}}</div>
          <img src='../../../assets/images/logo.png' alt="">
          <div class="title ellipsis">{{item.title}}</div>
          <div class="message ellipsis">{{item.tags}}</div>
          <div class="message">
            <span>作者:</span>
            <span>
              {{item.author}}
            </span>
          </div>
        </li>
      </ul>
    </div>
    <div class="paginationBox">
      <el-pagination
        background
        @current-change="handleCurrentChange"
        :current-page.sync="form.page"
        :page-size="form.limit"
        layout="prev, pager, next, jumper"
        :total="total"></el-pagination>
    </div>
  </div>
</template>

<script>
import adopt from './models/adopt'

export default {
  name: '',
  props: {},
  data() {
    return {
      total: null,
      form: {
        keywords: '',
        author: '',
        video_status: '全部',
        page: 1,
        limit: 20
      },
      tablist: [
        {
          status: '审核通过',
          title: '小鹏汽车收购广东福迪汽车...',
          tags: '标签1、标签2、标签3、标签4、标签5标签6、标签7、标签8、标签9...',
          author: '作者昵称',
          duration: '00:10:30'
        },
        {
          status: '待审核',
          title: '小鹏汽车收购广东福迪汽车...',
          tags: '标签1、标签2、标签3、标签4、标签5标签6、标签7、标签8、标签9...',
          author: '作者昵称',
          duration: '00:10:30'
        }
      ],
      statusArr: [
        {
          label: '全部',
          value: '全部'
        },
        {
          label: '审核通过',
          value: '审核通过'
        },
        {
          label: '待审核',
          value: '待审核'
        }
      ]
    }
  },
  methods: {
    //标签列表跳转
    tags() {
      this.$router.push({
        path: `/material/tags_list`,
        query: {}
      })
    },
    handleCurrentChange(val) {
      this.form.page = val
      //   this.getList()
    },
    status(item) {
      if (item.status == '待审核') {
        let obj = {
          content: adopt,
          title: '审核',
          width: '600px',
          height: '500px',
          data: {}
        }
        this.$layerIfream(obj)
      } else if (item.status == '审核通过') {
        this.$router.push({
          path: `/material/deatils`,
          query: {
            params: JSON.stringify(item)
          }
        })
      }
    }
  },
  watch: {},
  mounted() {},
  created() {},
  components: {
    adopt
  }
}
</script>

<style lang='scss' scoped>
.material_wrap {
  .top {
    background: #ffffff;
    padding: 15px;
    box-sizing: border-box;
    margin-top: 20px;
    .top_item {
      .tags {
        min-width: 80px;
        margin-top: 10px;
        font-size: 14px;
        color: #2b79df;
        cursor: pointer;
      }
      .label_title {
        min-width: 80px;
        margin-top: 10px;
      }
      .video_status {
        width: 65%;
      }
      .btn {
        height: 38px;
        background: #2b79df;
        color: #ffffff;
        font-size: 20px;
        box-sizing: border-box;
      }
    }
  }
  .tab_list {
    margin-top: 20px;
    ul {
      display: flex;
      flex-wrap: wrap;
      li {
        position: relative;
        width: calc(100% / 5 - 10px);
        background: #ffffff;
        padding: 10px;
        box-sizing: border-box;
        margin-top: 10px;
        margin-right: 10px;
        border-radius: 5px;
        .ellipsis {
          line-height: 20px;
          text-overflow: -o-ellipsis-lastline;
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-box-orient: vertical;
        }
        .status {
          position: absolute;
          right: 10px;
          top: 110px;
          font-size: 12px;
          cursor: pointer;
        }
        img {
          width: 100%;
          height: 130px;
        }
        .title {
          font-size: 14px;
          color: #202020;
          -webkit-line-clamp: 1;
          line-clamp: 1;
        }
        .message {
          color: #999999;
          font-size: 12px;
          margin-top: 10px;
          line-height: 17px;
          -webkit-line-clamp: 2;
          line-clamp: 2;
        }
      }
    }
  }
  .paginationBox {
    text-align: right;
    margin-top: 20px;
    margin-right: 10px;
  }
}
</style>