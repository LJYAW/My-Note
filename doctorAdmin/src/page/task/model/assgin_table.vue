<!--
 * @Author: your name
 * @Date: 2020-12-23 18:09:52
 * @LastEditTime: 2021-02-09 18:43:48
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /doctor_admin/src/page/task/model/add_problem.vue
-->
<template>
  <div class="assginBox">
    <div class="assginTable">
      <span class="span_item">指定医生:</span>
      <div style="margin-top:32px;width:100%">
        <!-- <el-select v-model="form.user_id" placeholder="请选择医生名称" style="width:100%">
          <el-option
            v-for="item in doctorData"
            :key="item.id"
            :label="item.join_name"
            :value="item.id">
          </el-option>
        </el-select> -->
        <div class="search-tags">
          <div>
            <div class="tag_list" style="background:#fff;padding:10px 0 10px 0" v-if="JSON.stringify(tags_item) !== '{}'">
              <div class="d-flex flex-wrap">
                <div class="ml-10px item-tags item-box" style="background:#f5f5f5;padding:10px">
                  <div class="doctorImg">
                    <!-- <img src="../../../assets/images/logo.png" alt=""> -->
                    <img :src="tags_item.avatar_url" alt="" v-if="tags_item.avatar_url">
                    <img src="../../../assets/images/noImg.jpg" alt="" v-else>
                  </div>
                  <div class="tags_item">
                    <span>{{tags_item.nickname}} - {{tags_item.department}}</span>
                    <div>{{tags_item.company_name}}</div>
                  </div>
                  <i class="el-icon-close closeIcon" @click="tagsClose()"></i>
                </div>
              </div>
            </div>
            <div>
              <el-input v-model="keywords" style="width:300px" placeholder="请输入指定医生"></el-input>
            </div>
            <div class="tag_list mt-10px" style="height:215px">
              <div class="d-flex flex-wrap">
                <div v-for="(item,index) in tempTags" :key="index" class="ml-10px item-box" @click="tagsTick(item)">
                  <div class="doctorImg">
                    <!-- <img src="../../../assets/images/logo.png" alt=""> -->
                    <img :src="item.avatar_url" alt="" v-if="item.avatar_url">
                    <img src="../../../assets/images/noImg.jpg" alt="" v-else>

                  </div>
                  <div class="tags_item" :class="item.id==tags_item.id?'isSelect':''">
                    <span>{{item.nickname}} - {{item.department}}</span>
                    <div>{{item.company_name}}</div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="btns">
      <el-button type="primary" round @click="determine()">确定</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'addProblem',
  props: {
    this: '',
    task_question_id: {
      type: Array,
      default: () => {
        return []
      }
    },
    doctorData: {
      type: Array,
      default: () => {
        return []
      }
    },
    layerid: {
      type: String,
      default: ''
    },
    lydata: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data() {
    return {
      keywords: '',
      tags_item: {},
      tempTags: [],
      tags: [],
      form: {
        user_id: ''
      }
    }
  },
  components: {},
  computed: {
    tempTaskArr() {
      const arr = []
      this.task_question_id.forEach(item => {
        arr.push(item.question_id)
      })
      return arr
    }
  },
  watch: {
    keywords(val) {
      if (val) {
        this.tempTags = this.tempTags.filter(item => item.nickname.includes(val))
      } else {
        this.tempTags = JSON.parse(JSON.stringify(this.doctorData))
      }
    }
  },
  methods: {
    // 删除指定企业
    tagsClose() {
      this.tags_item = {}
      this.$emit('tasList', this.tags_item)
    },
    tagsTick(item) {
      let obj = {
        company_name: item.company_name,
        id: item.id,
        nickname: item.nickname,
        department: item.department,
        avatar_url: item.avatar_url
      }
      let index = this.tempTags.findIndex(data => data.id == this.tags_item.id)
      if (index > -1) {
        Object.keys(this.tags_item).forEach(item => {
          delete this.tags_item[item]
        })
      } else {
        this.tags_item = obj
        this.form.user_id = obj.id
      }
      this.$emit('tasList', this.tags_item)
    },
    tasList(val) {},
    determine() {
      let params = {
        task_question_ids: this.tempTaskArr,
        user_id: this.form.user_id
      }
      this.$post('/admin/task/assigned', params).then(
        res => {
          if (res.data.code == '0000') {
            this.$message({
              message: '指派成功',
              type: 'success'
            })
            this.lydata._this.getList()
            this.$layer.close(this.layerid)
          } else {
            this.$message({
              message: res.data.msg,
              type: 'warning'
            })
          }
        },
        err => {}
      )
    }
  },
  created() {
    this.tempTags = JSON.parse(JSON.stringify(this.doctorData))
  },
  mounted() {}
}
</script>

<style lang="scss" scoped>
.btns {
  text-align: center;
  margin-top: 30px;
}
.assginBox {
  width: 100% !important;
}
.assginTable {
  width: 100%;
  display: flex;
  padding: 20px;
  .span_item {
    min-width: 80px;
    margin-top: 40px;
  }
  .doctorImg {
    width: 40px;
    height: 40px;
    img {
      width: 100%;
      height: 100%;
      object-fit: contain;
      border-radius: 50%;
    }
  }

  .search-tags {
    width: 100%;
    .tag_list {
      width: 100%;
      padding: 10px;
      background: #f5f5f5;
      overflow: auto;
      .item-tags {
        width: 300px !important;
        margin-left: 0;
      }
      .item-box {
        position: relative;
        width: calc(100% / 3 - 20px);
        display: flex;
        padding: 5px;
        cursor: pointer;
        font-size: 12px;
        .closeIcon {
          position: absolute;
          top: 10px;
          right: 10px;
          font-size: 16px;
        }
      }
      .tags_item {
        line-height: 20px;
        margin-left: 10px;
        margin-top: 10px;
      }
      p {
        cursor: pointer;
      }
      .isSelect {
        color: #2a79df;
      }
    }
  }
}
</style>
