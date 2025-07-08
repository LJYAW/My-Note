<!--
 * @Author: zll
 * @Date: 2020-12-07 15:56:20
 * @LastEditTime: 2020-12-30 17:56:18
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/ material_library/index.vue
-->
<template>
  <div class="tv_wrap">
    <div class="top">
      <div class="video">
        <!-- <el-select v-model="form.video" filterable placeholder="请选择">
          <el-option
            v-for="item in tv_options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select> -->
        <el-input
          placeholder="请输入关键词"
          v-model="form.keywords"
          @keyup.enter.native="search"
          class="search_input">
          <!-- <i slot="suffix" class="el-input__icon el-icon-search" style="color:#999999;font-size:20px;cursor: pointer;" @click="search()"></i> -->
        </el-input>
        <el-button
          type="primary"
          size="mini"
          round
          @click="search" class="search_btn">搜索</el-button>
      </div>
      <!-- <div class="d-flex recommend_box">
        <span>热力推荐</span>
        <div class="recommend_item">
          <p v-for="(recommend_item,recommend_index) in list" :key="recommend_index">
            {{recommend_item}}
          </p>
        </div>
        <div class="change" @click="refresh()">
          <img src="../../../assets/images/material_library/change.png" alt="">
          <div class="mt-3px ml-10px">换一批</div>
        </div>
      </div> -->
    </div>
    <div class="no_data" v-if="falg&&tablist.length==0">
      <img src="../../../assets/images/material_library/no_data.png" alt="">
      <p class="fc-999">很抱歉，未能找到所搜索的内容，换个词试试？</p>
    </div>
    <div class="video-list" v-else>
      <RecycleScroller
        class="scroller"
        :items="tablistTemp"
        :item-size="290"
        key-field="id"
        v-slot="{ item }">
        <div class="crad-warp">
          <div v-for="(obj,index) in item.arr" :key="index" class="crad-item" @click="deatils(obj)">
            <div class="img-wrap">
              <div class="duration-str">{{obj.duration_str}}</div>
              <img :src='obj.cover_pic_url'>
              <div class="iconfont palybtn">
                <vsvg icon="iconbofanganniu" class="fz-36px"></vsvg>
              </div>
            </div>
            <div class="p-15px">
              <div class="title ellipsis">{{obj.title}}</div>
              <div class="tags">
                <p v-for="(tags,tagsindex) in obj.tags" :key="tagsindex" :title="tags">
                  {{tags}}
                </p>
              </div>
            </div>
          </div>
        </div>
      </RecycleScroller>
    </div>

    <!-- <scroll class="search-list" ref="scroll" @scrollDown="scrollDown">
      <div class="list">
        <el-row :gutter="20" :offset="20">
          <el-col :span="6" v-for="(item,index) in tablist" :key="index" class="tablist">
            <div class="card-wrap" @click="deatils(item)">
              <div style="position: relative">
                <div class="duration_str">{{item.duration_str}}</div>
                <img :src='item.cover_pic_url' alt="">
                <div class="iconfont palybtn">
                  <vsvg icon="iconbofanganniu" class="fz-36px"></vsvg>
                </div>
              </div>
              <div class="p-15px">
                <div class="title ellipsis">{{item.title}}</div>
                <div class="tags">
                  <p v-for="(tags,tagsindex) in item.tags" :key="tagsindex">
                    {{tags}}
                  </p>
                </div>
              </div>
            </div>

          </el-col>
        </el-row> -->

    <!-- <ul>
          <li v-for="(item,index) in tablist" :key="index" @click="deatils(item)">
            <div style="position: relative">
              <div class="duration_str">{{item.duration_str}}</div>
              <img :src='item.cover_pic_url' alt="">
              <div class="iconfont palybtn">
                <vsvg icon="iconbofanganniu" class="fz-36px"></vsvg>
              </div>
            </div>
            <div class="p-15px">
              <div class="title ellipsis">{{item.title}}</div>
              <div class="tags">
                <p v-for="(tags,tagsindex) in item.tags" :key="tagsindex">
                  {{tags}}
                </p>
              </div>
            </div>
          </li>
        </ul> -->
    <!-- <loading v-if="loading_status" />
        <div class="no_data" v-if="falg&&tablist.length==0">
          <img src="../../../assets/images/material_library/no_data.png" alt="">
          <p class="fc-999">很抱歉，未能找到所搜索的内容，换个词试试？</p>
        </div>
      </div>
    </scroll> -->
    <!-- <div class="pageList">
      <el-pagination
        background
        @current-change="handleCurrentChange"
        :current-page.sync="form.page"
        :page-size="form.limit"
        layout="prev, pager, next, jumper"
        :total="total"></el-pagination>
    </div> -->
  </div>
</template>

<script>
const LIMIT = 10

export default {
  props: {},
  data() {
    return {
      tv: '电视台素材',
      total: null,
      falg: false,
      num: 0,
      form: {
        keywords: '',
        page: 1,
        limit: 100
      },
      keys_arr: [],
      data_list: [],
      loading_status: false,
      tablist: [],
      tabData: [],
      tv_options: [
        {
          label: '电视台素材',
          value: '电视台素材'
        }
      ]
    }
  },
  computed: {
    tablistTemp() {
      let num = Math.ceil(this.tablist.length / 4) // 2
      let temp = new Array(num)
      for (let i = 0; i < num; i++) {
        temp[i] = {
          id: i,
          arr: this.tablist.slice(i * 4, i * 4 + 4)
        }
      }
      return temp
    },
    list() {
      return this.data_list.slice(this.num, this.num + LIMIT)
    }
  },
  watch: {},
  methods: {
    scrollDown() {
      if (!this.tabData.length) return
      this.form.page++
      this.getList()
    },
    // getSplitdata(tags) {
    //   console.log(tags[0].split('，'))
    //   return tags[0].split('，')
    // },
    refresh() {
      this.num = LIMIT + this.num
      if (this.num >= this.data_list.length) {
        this.num = 0
      }
    },
    search() {
      this.form.page = 1
      this.tablist = []
      this.falg = false
      this.getList()
    },
    getHotkeys() {
      this.$get('/intelligent-creation/hot-keys', { params: {} }).then(res => {
        if (res.data.code == '0000') {
          let list = res.data.data
          list.forEach((item, index) => {
            item.forEach((keys_item, keys_index) => {
              this.data_list.push(keys_item[1])
            })
          })
        } else {
          this.$alertMsg(res.data.msg)
        }
      })
    },
    deatils(item) {
      let routeUrl = this.$router.resolve({
        path: '/material-deatils',
        query: {
          uuid: item.uuid
        }
      })
      window.open(routeUrl.href, '_blank')
    },
    getList() {
      this.loading_status = true
      this.tabData = []
      this.$get('/material-library/material-list', { params: this.form })
        .then(res => {
          if (res.data.code == '0000') {
            this.loading_status = false
            this.falg = true
            this.tabData = res.data.data.list
            // this.tablist = res.data.data.list
            this.tablist = this.tablist.concat(res.data.data.list)
            // this.total = res.data.data.total
          } else {
            this.$alertMsg(res.data.msg)
          }
        })
        .finally(() => {
          this.loading_status = false
        })
    },
    handleCurrentChange(val) {
      this.form.page = val
      this.getList()
    }
  },
  components: {},
  mounted() {},
  created() {
    this.getHotkeys()
    this.getList()
  }
}
</script>

<style lang='scss'>
.video-list {
  height: 800px;

  .scroller {
    height: 100%;
    .crad-warp {
      display: flex;
      justify-content: space-between;

      .crad-item {
        width: 285px;
        height: 255px;
        background: #fff;
        border-radius: 5px;
        overflow: hidden;
        cursor: pointer;
        transition: all 0.2s ease-in;

        &:hover {
          transform: translateY(-5px);
          box-shadow: 0px 2px 7px 0px rgba(0, 0, 0, 0.16);
        }
        .title {
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }
        .img-wrap {
          position: relative;
          height: 165px;
          background: #ccc;
          img {
            width: 100%;
            height: 100%;
            background: #ccc;
            object-fit: cover;
          }
          .palybtn {
            position: absolute;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
          }
          .duration-str {
            border-radius: 10px;
            position: absolute;
            bottom: 10px;
            left: 10px;
            background: rgba(0, 0, 0, 0.6);
            color: #fff;
            font-size: 12px;
            padding: 5px 10px;
          }
        }
        .tags {
          margin-top: 8px;
          display: flex;
          overflow: hidden;
          p {
            max-height: 28px;
            color: #333;
            background: #f7f7f7;
            padding: 5px;
            font-size: 12px;
            margin-top: 10px;
            line-height: 17px;
            border-radius: 8px;
            margin-right: 10px;
            word-break: keep-all;
          }
        }
      }
    }
  }
}

.tv_wrap {
  .top {
    height: 50px;
    // padding: 15px;
    // background: #ffffff;
    margin: 20px auto;
    .video {
      display: flex;
      .search_btn {
        width: 100px;
        height: 50px;
        border-radius: 0px;
        font-size: 16px;
      }
      .search_input {
        width: 100%;
        /deep/ input {
          height: 50px;
          border-radius: 5px !important;
          background: #ffffff;
          color: #999999;
          border: #f7f7f7;
        }
      }
    }
    .recommend_box {
      margin-top: 40px;
      padding-bottom: 20px;
      display: flex;
      justify-content: space-between;
      .change {
        width: 100px;
        display: flex;
        cursor: pointer;
        img {
          width: 20px;
          height: 20px;
        }
      }
      span {
        min-width: 100px;
        font-size: 16px;
        color: #000000;
      }
      .recommend_item {
        width: calc(100% - 200px);
        display: flex;
        flex-wrap: wrap;
        p {
          width: auto;
          height: 30px;
          background: #333333;
          color: #ffffff;
          border-radius: 50px;
          margin: 0 10px;
          line-height: 10px;
          padding: 10px;
          box-sizing: border-box;
          margin-bottom: 10px;
        }
      }
    }
  }
  .no_data {
    width: 100%;
    text-align: center;
    margin-top: 60px;
    img {
      width: 287px;
      height: 238px;
      object-fit: cover;
    }
  }
  .search-list {
    max-height: 800px;
    height: calc(100% - 70px);
    overflow: hidden;
    overflow-y: auto;
    .tablist {
      position: relative;
      margin-bottom: 20px;

      .card-wrap {
        background: #fff;
        transition: all 0.25s ease-in;

        border-radius: 5px;
        // background: #ccc;
        box-sizing: border-box;
        cursor: pointer;
        &:hover {
          transform: translateY(-5px);
          box-shadow: 0px 2px 7px 0px rgba(0, 0, 0, 0.16);
        }
      }
      .duration_str {
        position: absolute;
        top: 120px;
        left: 10px;
        width: 80px;
        height: 30px;
        background: #000000;
        color: #ffffff;
        z-index: 900;
        border-radius: 22px;
        text-align: center;
        line-height: 30px;
      }
      .palybtn {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
      }
      .ellipsis {
        text-overflow: -o-ellipsis-lastline;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 1;
        line-clamp: 1;
        -webkit-box-orient: vertical;
      }
      img {
        width: 100%;
        height: 166px;
        object-fit: cover;
        border-radius: 5px;
      }
      .message {
        color: #999999;
        font-size: 14px;
      }
      .times {
        padding: 10px 0 10px 0;
        border-bottom: 1px solid #dddddd;
      }
      .title {
        // height: 42px;
        font-size: 14px;
        color: #333333;
        line-height: 22px;
      }
      .tags {
        display: flex;
        // flex-wrap: wrap;
        flex: 1; //在可用的空间内100%填充
        overflow: hidden;
        text-overflow: ellipsis;
        //规定段落中的文本不进行换行
        white-space: nowrap;
        p {
          color: #333333;
          background: #f7f7f7;
          padding: 5px;
          font-size: 12px;
          margin-top: 10px;
          line-height: 17px;
          border-radius: 8px;
          margin-right: 10px;
        }
      }
    }
  }

  // ul {
  //   display: flex;
  //   flex-wrap: wrap;
  //   li {
  //     position: relative;
  //     width: calc(100% / 4 - 10px);
  //     border-radius: 5px;
  //     // height: 297px;
  //     background: #ffffff;
  //     // padding: 15px;
  //     box-sizing: border-box;
  //     margin-top: 10px;
  //     margin-right: 10px;
  //     border-radius: 5px;
  //     padding-bottom: 10px;
  //     cursor: pointer;
  //     transition: all 0.25s ease-in;
  //   }
  // }
  .pageList {
    width: 100%;
    text-align: right;
    margin-top: 20px;
  }
}
</style>
