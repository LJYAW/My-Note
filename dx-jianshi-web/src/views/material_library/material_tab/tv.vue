<!--
 * @Author: zll
 * @Date: 2020-12-07 15:56:20
 * @LastEditTime: 2020-12-08 13:50:16
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/ material_library/index.vue
-->
<template>
  <div class="tv_wrap">
    <div class="top">
      <!-- <div class="tv">
        <el-select v-model="tv" filterable placeholder="请选择">
          <el-option
            v-for="item in tv_options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-input placeholder="请输入内容" v-model="input5" class="input-with-select">
          <el-button slot="append" icon="el-icon-search"></el-button>
        </el-input>
      </div> -->
    </div>
    <div class="list">
      <ul>
        <li v-for="(item,index) in tablist" :key="index">
          <img src='../../../assets/images/logo.png' alt="">
          <div class="title">{{item.title}}</div>
          <div class="d-flex justify-content-between times">
            <span>{{item.colums}}</span>
            <span>
              {{item.times}}
            </span>
          </div>
          <div class="tags">
            <p v-for="(tags,tagsindex) in item.tags" :key="tagsindex">
              {{tags.label}}
            </p>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  props: {},
  data() {
    return {
      tv: '电视台素材',
      tablist: [
        {
          title: '小鹏汽车收购广东福迪汽车...',
          tags: [
            {
              label: 'sdbcjksdv'
            },
            {
              label: 'sdbcjksdv'
            },
            {
              label: 'sdbcjksdv'
            }
          ],
          times: '00:10:30',
          colums: '安徽公共'
        },
        {
          title: '小鹏汽车收购广东福迪汽车...',
          tags: [
            {
              label: 'sdbcjksdv'
            },
            {
              label: 'sdbcjksdv'
            },
            {
              label: 'sdbcjksdv'
            }
          ],
          times: '00:10:30',
          colums: '安徽公共'
        },
        {
          title: '小鹏汽车收购广东福迪汽车...',
          tags: [
            {
              label: 'sdbcjksdv'
            },
            {
              label: 'sdbcjksdv'
            },
            {
              label: 'sdbcjksdv'
            }
          ],
          times: '00:10:30',
          colums: '安徽公共'
        },
        {
          title: '小鹏汽车收购广东福迪汽车...',
          tags: [
            {
              label: 'sdbcjksdv'
            },
            {
              label: 'sdbcjksdv'
            },
            {
              label: 'sdbcjksdv'
            }
          ],
          times: '00:10:30',
          colums: '安徽公共'
        },
        {
          title: '小鹏汽车收购广东福迪汽车...',
          tags: [
            {
              label: 'sdbcjksdv'
            },
            {
              label: 'sdbcjksdv'
            },
            {
              label: 'sdbcjksdv'
            }
          ],
          times: '00:10:30',
          colums: '安徽公共'
        },
        {
          title: '小鹏汽车收购广东福迪汽车...',
          tags: [
            {
              label: 'sdbcjksdv'
            },
            {
              label: 'sdbcjksdv'
            },
            {
              label: 'sdbcjksdv'
            }
          ],
          times: '00:10:30',
          colums: '安徽公共'
        }
      ],
      tv_options: [
        {
          label: '电视台素材',
          value: '电视台素材'
        }
      ]
    }
  },
  computed: {},
  watch: {},
  methods: {
    see(item) {}
  },
  components: {},
  mounted() {},
  created() {}
}
</script>

<style lang='scss' scoped>
.tv_wrap {
  ul {
    display: flex;
    flex-wrap: wrap;
    li {
      position: relative;
      width: calc(100% / 4 - 10px);
      background: #ffffff;
      padding: 10px;
      box-sizing: border-box;
      margin-top: 10px;
      margin-right: 10px;
      border-radius: 5px;
      padding-bottom: 20px;
      .ellipsis {
        line-height: 20px;
        text-overflow: -o-ellipsis-lastline;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-box-orient: vertical;
      }
      img {
        width: 100%;
        height: 130px;
      }
      .times {
        color: #999999;
        font-size: 14px;
        padding: 15px 0 15px 0;
        border-bottom: 1px solid #dddddd;
      }
      .title {
        font-size: 14px;
        color: #333333;
        -webkit-line-clamp: 1;
        line-clamp: 1;
        margin-top: 10px;
      }
      .tags {
        display: flex;
        flex-wrap: wrap;
        p {
          color: #333333;
          background: #f7f7f7;
          padding: 5px;
          font-size: 12px;
          margin-top: 10px;
          line-height: 17px;
          -webkit-line-clamp: 2;
          line-clamp: 2;
          border-radius: 8px;
          margin-right: 10px;
        }
      }
    }
  }
}
</style>
