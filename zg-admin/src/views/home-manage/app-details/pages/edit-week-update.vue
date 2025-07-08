<!--
 * @Author: your name
 * @Date: 2021-10-22 14:21:10
 * @LastEditTime: 2021-11-05 17:41:46
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/home-manage/app-details/pages/index.vue
-->
<template>
  <div class="week-upload-wrap">

    <div class="conteniner">

      <div class="box edit-box">
        <BaseTitle class="base-title" title="每周上新内容配置" />

        <p class="sub-title">单品配置 <span>{{ weekUpdate.list.length }}/{{ maxListLength }}</span></p>
        <div class="data-list-wrap">
          <div v-for="(item,j) in weekUpdate.list" :key="j" class="data-list">
            <div class="btn-card">
              <svg-icon
                v-if="weekUpdate.list.length>3"
                icon-class="delete"
                class="del-icon"
                @click="deleteWeek('list',j)"
              />
              <div v-if="item.group && item.group.name" class="group-info-wrap">
                <GroupInfo :group="item.group" />
                <el-button class="delete-btn" type="text" @click="deleteSingleGroup('list',j)">删除</el-button>
              </div>
              <div v-else class="com-btn btn" @click="selectCom('list',j)">选择单品</div>
            </div>
          </div>
        </div>
        <div class="btns">
          <el-button class="btn-w-100" size="small" @click="addSingleList">增加单品</el-button>
          <div class="e-btn">
            <el-button type="danger" size="small" @click="reloadData">取消</el-button>
            <!-- <el-button type="primary" size="small" @click="saveBanner">保存</el-button> -->
          </div>
        </div>
      </div>

      <div class="box edit-box">
        <BaseTitle class="base-title" title="分类内容配置" />
        <p class="sub-title">单品配置 <span>{{ weekUpdate.classification.length }}/{{ maxListLength }}</span></p>

        <div class="data-list-wrap">
          <div v-for="(item,j) in weekUpdate.classification" :key="j" class="data-list">
            <div class="btn-card">
              <svg-icon
                v-if="weekUpdate.classification.length>3"
                icon-class="delete"
                class="del-icon"
                @click="deleteWeek('classification',j)"
              />
              <div v-if="item.calssData && item.calssData.name" class="group-info-wrap">
                <!-- <GroupInfo :group="item.group" /> -->
                <div class="list">
                  <span class="name">{{ item.calssData.name }} </span> |
                  <span>ID: {{ item.calssData.id }} | </span>
                  <span>库存: {{ item.calssData.goods_num }}</span>
                </div>
                <el-button class="delete-btn" type="text" @click="deleteSingleGroup('classification',j)">删除</el-button>
              </div>
              <div v-else class="com-btn btn" @click="selectCom('classification',j)">选择分类</div>
            </div>
          </div>
        </div>
        <div class="btns">
          <el-button class="btn-w-100" size="small" @click="addClassList">增加分类</el-button>

          <div class="e-btn">
            <el-button type="danger" size="small" @click="reloadData">取消</el-button>
            <!-- <el-button type="primary" size="small" @click="saveBanner">保存</el-button> -->
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { baseWeekUpdate } from '../../base-com-data'
import ComBtn from '../../components/select-com-btn.vue'
import GroupInfo from '../../components/group-info.vue'

export default {
  components: {
    // ComBtn,
    GroupInfo
  },
  props: {

  },
  data() {
    return {
      // list: [],
      maxListLength: 10,
      classList: [],
      weekUpdate: {}
    }
  },
  computed: {
    ...mapState('bdapp', ['appData', 'appDataCopy'])
    // weekUpdateData() {
    //   return this.appData.WeekUpdate
    // }
  },
  watch: {
    weekUpdate: {
      handler(obj) {
        const data = this.appData
        data.WeekUpdate = obj
        this.$store.commit('bdapp/SET_ZHONGGU_APP_DATA', data)
      },
      deep: true
    }
  },
  created() {
    this.weekUpdate = this.$lodash.cloneDeep(this.appData.WeekUpdate)
    // this.list = this.$lodash.cloneDeep(this.appData.WeekUpdate.classification)
    // this.classList = this.appData.WeekUpdate.list
  },
  beforeDestroy() {
    this.$bus.$off('getActiveClassData')
  },
  mounted() {
    this.$bus.$on('getActiveClassData', (data) => {
      this.setBaseData(data)
    })
  },
  methods: {
    // 设置数据
    setBaseData(data) {
      const { activeClassData, checkboxGroup } = data
      const { key, index } = this.activeSingle
      // 未选择分类的时候
      if (key === 'list') {
        if (!data.checkboxGroup.id) {
          this.$message.error('请选择单品')
          return
        }
        !this.weekUpdate[key][index].calssData.id && (this.weekUpdate[key][index].calssData = activeClassData)
        this.weekUpdate[key][index]['group'] = checkboxGroup
      } else {
        !this.weekUpdate[key][index].calssData.id && (this.weekUpdate[key][index].calssData = activeClassData)
      }
    },
    // 弹起 单品分类弹框
    selectCom(key, index) {
      this.activeSingle = { key: key, index: index }
      this.$store.commit('bdapp/SHOW_COM_DIALOG', true)
    },
    // 添加单品
    addSingleList() {
      if (this.weekUpdate.list.length > this.maxListLength - 1) {
        this.$message(`最多添加${this.maxListLength}条`)
        return
      }
      const element = JSON.parse(JSON.stringify(baseWeekUpdate().list[0]))
      this.weekUpdate.list.push(element)
    },
    addClassList() {
      if (this.weekUpdate.classification.length > this.maxListLength - 1) {
        this.$message(`最多添加${this.maxListLength}条`)
        return
      }
      const element = JSON.parse(JSON.stringify(baseWeekUpdate().classification[0]))
      this.weekUpdate.classification.push(element)
    },
    // 删除 单品
    deleteSingleGroup(key, index) {
      if (key === 'list') {
        this.$set(this.weekUpdate[key][index], 'group', {})
      } else {
        this.$set(this.weekUpdate[key][index], 'calssData', {})
      }
    },
    deleteWeek(key, index) {
      this.weekUpdate[key].splice(index, 1)
    },
    reloadData() {
      this.$confirm('确认恢复到之前数据吗', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.weekUpdate = this.$lodash.cloneDeep(this.appDataCopy.WeekUpdate)
      })
    },
    saveBanner() {
      this.$store.dispatch('bdapp/PostZhongguAppData')
    }
  }
}
</script>

<style scoped lang="scss">

.week-upload-wrap {
  height : 100%;

  .select-btn {
    width : 100%;
    text-align : center;
    display : flex;
    flex-direction : column;
    align-items : center;
  }

  .conteniner {
    height : 100%;
    display : flex;
    flex-direction : column;

    .box {
      flex : 1;
      display : flex;
      flex-direction : column;
      max-height : 48%;
      margin-bottom : 20px;
      .data-list {
        margin : 0;
        .btn-card {
          position : relative;
          .del-icon {
            position : absolute;
            right : -4px;
            top : -4px;
            cursor : pointer;
          }
        }
      }

      .wrap {
        flex : 1;
        overflow : hidden;
        overflow-y : auto;
      }
    }
  }
}
.sub-title {
  margin :  5px;
}
.conteniner {
  padding : 20px;

  .banner-list-wrap {
    .week-list-wrap {
      display : flex;
      flex-direction : column;
      width : 100%;
      .item {
        background-color : #F8F8F8;
        margin-bottom : 20px;
        height : 50px;
        display : flex;
        align-items : center;
        flex-wrap : wrap;
        span {
          margin : 0 10px;
        }
        .blue {
          color : #5675E8;
          font-weight : 600;
        }

        .del-btn {
          margin-left : auto;
          margin-right : 20px;
          color : #F16B6B;
        }
      }
    }

    .content-wrap {
      height : 50px;
      background-color : #F8F8F8;
      border-radius : 4px;
    }

    .sub-title {
      margin-bottom : 20px;
      display : flex;

      .des {
        font-size : 12px;
        margin-left : auto;
        color : #404040;
      }
    }
  }
  .btns {
    display : flex;
    flex-direction : column;
    margin-top : 30px;

    .btn-w-100 {
      width : 100%;
    }

    .e-btn {
      margin-top : 20px;
      margin-left : auto;
    }

    .el-button {
      min-width : 100px;
    }
  }
}

.data-list-wrap {
  max-height: 180px;
  min-height: 180px;
  overflow-y: auto;
}

</style>
