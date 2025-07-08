<!--
 * @Author: zll
 * @Date: 2020-12-03 15:33:40
 * @LastEditTime: 2020-12-03 18:00:48
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /weijian_cms_web/src/page/material/new/models/adopt.vue
-->
<template>
  <div class="tags_wrap">
    <el-breadcrumb>
      <el-breadcrumb-item to="/material">素材管理</el-breadcrumb-item>
      <el-breadcrumb-item>标签列表</el-breadcrumb-item>
    </el-breadcrumb>
    <div>
      <div class="top">
        <el-input placeholder="请输入标签" v-model="form.tags">
          <el-button slot="append" icon="el-icon-search" class="btn"></el-button>
        </el-input>
      </div>
    </div>
    <div class="tags_list">
      <ul class="tags_letter">
        <li v-for="(item,index) in tagsArr" :key="index">
          <div class="mt-10px">{{enLetter[index]}}、</div>
          <ul class="tags_ul">
            <li v-for="(tags_item,tags_index) in item.arr" :key="tags_index" @click="tagsSee(tags_item)">
              <div>{{tags_item.name}}</div>
            </li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import enLetter from './js/en_letter'
export default {
  name: '',
  data() {
    return {
      form: {
        tags: ''
      },
      enLetter: enLetter,
      tagsArr: [
        {
          arr: [
            {
              name: '安妮宝贝'
            },
            {
              name: '安妮宝贝'
            },
            {
              name: '安妮宝贝'
            }
          ]
        },
        {
          arr: [
            {
              name: '安妮宝贝'
            },
            {
              name: '安妮宝贝'
            },
            {
              name: '安妮宝贝'
            }
          ]
        },
        {
          arr: [
            {
              name: '安妮宝贝'
            },
            {
              name: '安妮宝贝'
            },
            {
              name: '安妮宝贝'
            }
          ]
        }
      ]
    }
  },
  components: {},
  computed: {},
  watch: {},
  methods: {
    tagsSee(item) {
      console.log('🚀 ~ file: tags_list.vue ~ line 95 ~ tagsSee ~ item', item)
      this.$router.push({
        path: `/material/tags_video`,
        query: {
          name: item.name
        }
      })
    }
  },
  created() {},
  mounted() {}
}
</script>

<style lang="scss" scoped>
.tags_wrap {
  width: 100%;
  height: 100%;
  padding: 15px;
  box-sizing: border-box;
  background: #ffffff;
  .top {
    margin-top: 20px;
    .el-input-group {
      width: 465px;
      height: 38px;
    }
    .btn {
      height: 38px;
      background: #2b79df;
      color: #ffffff;
      font-size: 20px;
      box-sizing: border-box;
    }
  }
  .tags_list {
    margin-top: 20px;
    .tags_letter {
      li {
        display: flex;
        margin-top: 10px;
        .tags_ul {
          display: flex;
          flex-wrap: wrap;
          li {
            margin-right: 10px;
            cursor: pointer;
          }
        }
      }
    }
  }
}
</style>
