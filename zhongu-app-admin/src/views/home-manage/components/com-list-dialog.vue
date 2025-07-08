<!--
 * @Author: your name
 * @Date: 2021-10-26 18:39:17
 * @LastEditTime: 2021-11-19 16:49:56
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/home-manage/components/select-com-btn.vue
-->
<template>
  <div class="select-com-btn">

    <BaseDialog v-if="show && !loading" :show.sync="show" title="产品选择" width="590px" @close="close">

      <div class="com-wrap">

        <div class="left">
          <p class="title">分类</p>
          <el-input
            v-model="classKeyword"
            suffix-icon="el-icon-search"
            placeholder="请输入内容 按回车键搜索"
            size="mini"
            clearable
            @change="searchClass"
          />
          <div class="class-list">
            <ul :class="['wrap']">
              <li
                v-for="(item,index) in classList"
                :key="item.id"
                :class="['item',{'active': activeIndex === index}]"
                @click="setCategoryList(item,index)"
              >
                {{ item.name }}
              </li>
            </ul>
          </div>
        </div>

        <div v-loading="categoryStatus" class="right">

          <p class="title">单品</p>
          <el-input
            v-model="singleKeyword"
            suffix-icon="el-icon-search"
            placeholder="请输入内容 按回车键搜索"
            size="mini"
            clearable
            @change="searchSingle"
          />

          <div v-infinite-scroll="load" class="class-list" style="overflow:auto">

            <div class="box">
              <el-radio-group v-model="checkboxGroup" class="li">
                <el-radio v-for="data in categoryList" :key="data.id" :label="data" class="li-checkbox">
                  <div class="list">
                    <BaseImage :src="data.main_img" class="banner" />

                    <div class="des-wrap">
                      <p class="name">{{ data.name }}</p>
                      <p class="des">ID: {{ data.id }}</p>
                      <p class="total">库存数量：{{ data.inventory }}</p>
                    </div>
                    <p class="price">￥{{ data.price }}</p>
                  </div>
                </el-radio>
              </el-radio-group>
            </div>
            <p v-if="goodTotal !=0 && goodTotal === categoryList.length" class="not-more">没有更多了~</p>
          </div>

          <div v-if="!categoryStatus && categoryList.length < 1" class="empty">
            暂无产品
          </div>
        </div>
      </div>

      <div class="btn-wrap">
        <el-button type="primary" size="mini" style="width: 254px" @click="submit">确认</el-button>
      </div>

    </BaseDialog>
  </div>
</template>

<script>
/**
 * @function getCategoryData
 */
import { mapState } from 'vuex'

export default {
  name: 'ClassDialog',
  components: {
  },
  props: {

  },
  data() {
    return {
      loading: true,
      classKeyword: '',
      singleKeyword: '',
      show: false,
      logindNumber: 0,
      keyword: '',
      activeIndex: 0,
      categoryList: [],
      categoryStatus: true,
      checkboxGroup: {},
      activeClassData: {},
      classList: [],
      goodTotal: 0,
      goodPage: 1,
      goodLimit: 10
    }
  },
  computed: {
    ...mapState('bdapp', []),
    showComDialog() {
      return this.$store.state.bdapp.showComDialog
    }
  },
  watch: {
    showComDialog(val) {
      this.show = val
      val && this.getClassData()
    },
    activeClassData: {
      handler() {
        this.checkboxGroup = {}
      },
      deep: true
    }
  },
  async created() {
    await this.getClassData()
    this.classList.length > 0 && this.getCategorList()
  },
  mounted() {

  },
  methods: {
    submit() {
      this.$bus.$emit('getActiveClassData', {
        activeClassData: this.activeClassData,
        checkboxGroup: this.checkboxGroup
      })
      this.close()
    },
    close() {
      this.show = false
      this.activeClassData = {}
      this.checkboxGroup = {}
      this.activeIndex = 0
      this.$store.commit('bdapp/SHOW_COM_DIALOG', false)
    },
    searchClass() {
      this.classList = []
      this.getClassData()
    },
    searchSingle() {
      this.logindNumber = 0
      this.categoryList = []
      this.getCategorList()
      this.getCategorList()
    },
    // 选择 单品
    selectHandel(data, i) {
      this.$set(this.categoryList[i], 'checked', !data.checked)
    },
    // 点击分类商品列表
    setCategoryList(item, index) {
      this.activeIndex = index
      this.activeClassData = this.classList[index]
      this.goodPage = 1
      this.categoryList = []
      this.getCategorList()
    },
    load() {
      this.goodPage += 1
      this.getCategorList()
    },
    // 获取分类商品接口
    async getClassData() {
      this.loading = true
      const params = {
        name: this.classKeyword,
        page: 1,
        limit: 1000,
        key: ''
      }
      const { err, res } = await this.$get('/admin/categories', params)
      this.classList = res.data.list
      this.activeClassData = this.classList[0]
      this.loading = false
    },
    // 获取单品
    async getCategorList() {
      this.logindNumber++
      this.logindNumber < 2 && (this.categoryStatus = true)
      const category_id = this.classList[this.activeIndex].id

      const params = {
        name: this.singleKeyword,
        page: this.goodPage,
        limit: this.goodLimit,
        category_id: category_id
      }
      const { err, res } = await this.$get('/admin/goods', params)

      const data = res.data.list.map(item => {
        item.checked = false
        return item
      })
      this.goodTotal = res.data.total
      this.categoryList = this.categoryList.concat(data)
      this.categoryStatus = false
    }
  }
}
</script>

<style scoped lang="scss">
::v-deep .el-dialog__title {
    font-size: 14px;
}

::v-deep .el-dialog {
  height: 70vh;
  min-height: 600px;
  display: flex;
  flex-direction: column;

  .el-dialog__body {
    height: 200px;
    flex: 1;
    padding-top: 0;
    display: flex;
    flex-direction: column;
  }
}

.com-wrap {
  flex: 1;
  display: flex;
  border-radius: 8px;
  overflow: hidden;
  border: 1px solid #5675E8;

  .wrap {
    height: 360px;
  }

  .left {
    border-right: 1px solid #5675E8;
  }

  .right, .left {
    flex: 1;
    display: flex;
    flex-direction: column;
    height: 100%;
    min-width: 50%;

    .title {
      background-color: #5675E8;
      color: #fff;
      font-size: 12px;
      padding: 8px 10px;
    }

    ::v-deep .el-input__inner {
      border-radius: 0px;
      border: none;
      border-bottom: 1px solid #5675E8;
    }
  }

}
.btn-wrap {
    width: 100%;
    text-align: center;
    margin-top: 30px;
  }

.class-list {
  height: 100%;
  overflow: hidden;
  overflow-y: auto;

  .li {
    width: 100%;
    cursor: pointer;

    .li-checkbox {
      width: 100%;
      display: flex;
      padding: 15px 10px;
      align-items: center;

      ::v-deep {
        .el-radio__label {
          flex: 1;
        }
      }

       &:hover {
        background-color: #F8F9FA;
      }
      &:nth-last-child(2n) {
        background-color: #F8F9FA;
      }
    }

  }
  .not-more {
    text-align: center;
    font-size: 12px;
    margin-bottom: 20px;
  }
  .item {
    height: 40px;
    padding-left: 10px;
    display: flex;
    align-items: center;
    transition: all 0.3s;
    cursor: pointer;

    &.active {
      background-color: #8FA6F9;
      color: #fff;
    }

     &:hover {
      background-color: #8FA6F9;
      color: #fff;
    }
  }
}
.box {
  display: flex;
  width: 100%;
  align-items: center;

  &:nth-child(2) {
    background-color: #404040;
  }

  .img {
    width: 45px;
    height: 45px;
    margin: 0 10px;
  }

  .list {
    display: flex;
    align-items: center;
    flex: 1;
    font-size: 12px;
    width: 100%;

    .banner {
      width: 48px;
      height: 48px;
      margin: 0 10px;
    }

    .name {
      color: #404040;
      width: 100%;
    }
    .price {
      margin-left: auto;
      font-size: 14px;
    }

    .des-wrap {
      flex: 1;
      width: 0;
      overflow: hidden;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
    }
  }
}
.empty {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
::v-deep .el-button--text {
    font-weight: 400;
    font-size: 12px;
  }
</style>
