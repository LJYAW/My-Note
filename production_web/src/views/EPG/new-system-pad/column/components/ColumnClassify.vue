<!--
 * @Author: your name
 * @Date: 2021-01-13 11:16:59
 * @LastEditTime: 2021-02-26 18:30:16
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /TM-prod-system/src/views/order_mgt/components/ColumnClassify.vue
-->
<template>
  <div class="column-type">
    <base-dialog :show="showCateDialog" append-to-body width="70%" class="column-dialog" :destroy-on-close="true" @close="close">
      <div>
        <div v-if="firstCheckList.length" class="column-list">
          <p class="column-name">已选分类：</p>
          <el-form label-width="80px">
            <el-form-item label="一级分类">
              <div class="box">
                <base-tag
                  v-for="(tag,index) in firstCheckList"
                  :key="tag.first_category_id +'_'+ index"
                  closable
                  @close="handleClose(1,tag)"
                >{{ tag.first_category_name }}</base-tag>
              </div>
            </el-form-item>

            <el-form-item v-if="secondCheckList.length" label="二级分类">
              <base-tag
                v-for="(tag,index) in secondCheckList"
                :key="index"
                closable
                @close="handleClose(2,tag)"
              >{{ tag.second_category_name }}</base-tag>
            </el-form-item>
          </el-form>

        </div>
        <!-- 一级分类部分 -->
        <div class="column-list">
          <p class="column-name">一级分类</p>
          <el-checkbox-group v-model="firstCheckList">
            <el-checkbox
              v-for="list in categoryList"
              :key="list.first_category_id"
              :label="list"
            >{{ list.first_category_name }}</el-checkbox>
          </el-checkbox-group>
        </div>

        <!-- 二级分类部分 -->
        <div v-if="firstCheckList.length" class="column-list">
          <p class="column-name">二级分类</p>

          <div class="flex-1">
            <div
              v-for="(item,index) in secondList"
              :key="index"
              ref="secondCategoryList"
              class="column-list"
            >
              <!-- 所属一级分类 -->
              <p class="column-name">{{ item.first_category_name }}</p>
              <!-- 二级分类 -->
              <el-checkbox-group v-model="secondCheckList" :class="[item.isOpen&&'more','column-group']">
                <el-checkbox
                  v-for="list in item.arr"
                  :key="list.second_category_id"
                  :label="list"
                >{{ list.second_category_name }}</el-checkbox>
              </el-checkbox-group>
              <div v-if="item.showMore" class="toggle-btn">
                <el-button type="text" size="default" @click="handelClik(index)">
                  <span v-if="item.isOpen">展开</span>
                  <span v-else>收起</span>
                </el-button>

              </div>
            </div>
          </div>

        </div>
      </div>
      <div class="dialog-footer">
        <el-button type="primary" @click="submit">确 定</el-button>
        <el-button @click="close">取 消</el-button>
      </div>
    </base-dialog>
  </div>
</template>

<script>
export default {
  name: '',
  components: {},
  props: {
    showCateDialog: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      firstCheckList: [], // 一级分类选中
      secondCheckList: [], // 二级分类选中
      secondList: [], // 展示二级分类
      categoryList: []
    }
  },
  computed: {
  },
  watch: {
    firstCheckList(val) {
      this.handleChange(val)
      this.showMoreBtn()
    }
  },
  created() {
    if (!this.categoryList.length) {
      this.getcategoryList()
    }
  },
  mounted() {},
  methods: {
    getcategoryList() {
      this.$get('/epg-task/category-list', {}).then((res) => {
        console.log('🚀 ~ file: ColumnClassify.vue ~ line 126 ~ this.$get ~ res', res)
        this.categoryList = res.data
      }).catch((error) => {
        this.$message.error(error.msg)
      })
    },
    // 关闭分类弹窗
    submit() {
      this.$emit('setClassData')
    },
    close() {
      this.$emit('close')
    },
    handleClose(type, tag) {
      if (type === 1) {
        const secondDelArr = this.firstCheckList.find(item => item.first_category_name === tag.first_category_name)
        const second_category_list = secondDelArr.second_category_list
        second_category_list.forEach(item => {
          this.secondCheckList = this.secondCheckList.filter(data => data.second_category_id !== item.second_category_id)
        })
        this.firstCheckList.splice(this.firstCheckList.indexOf(tag), 1)
      }
      if (type === 2) {
        this.secondCheckList.splice(this.secondCheckList.indexOf(tag), 1)
      }
    },
    // 展开收起功能
    handelClik(index) {
      this.$set(this.secondList[index], 'isOpen', !this.secondList[index].isOpen)
    },
    // 选中一级分类发生变化，重构二级分类
    handleChange(arr) {
      this.secondList = []
      this.categoryList.forEach(item => this.firstCheckList.find(objName => {
        if (item.first_category_name === objName.first_category_name) {
          this.secondList.push({ arr: item.second_category_list, showMore: false, isOpen: false, first_category_name: item.first_category_name })
        }
      }))
    },
    // 判断是否显示展开收起
    showMoreBtn() {
      this.$nextTick(() => {
        this.secondList.forEach((item, index) => {
          if (!this.$refs.secondCategoryList) return
          var rowNum = this.$refs.secondCategoryList[index].clientHeight / 40
          if (rowNum > 2) {
            this.$set(this.secondList[index], 'showMore', true)
            this.$set(this.secondList[index], 'isOpen', true)
          }
        })
      })
    },
    // 回显数据
    async setDefalutData(firstArr = [], secoendArr = []) {
      if (!this.categoryList.length) {
        await this.$store.dispatch('orderBase/getCategoryList')
      }
      this.firstCheckList = []
      this.secondCheckList = []

      this.categoryList.forEach(item => {
        firstArr.forEach(id => {
          if (item.first_category_id === Number(id)) {
            this.firstCheckList.push(item)
            this.handleChange(this.firstCheckList)
          }
        })
      })
      this.firstCheckList.forEach(item => {
        for (let i = 0; i < item.second_category_list.length; i++) {
          const obj = item.second_category_list[i]
          const objId = obj.second_category_id
          secoendArr.forEach(id => {
            if (objId === Number(id)) {
              this.secondCheckList.push(obj)
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">
.column-type {
  ::v-deep{
    .el-dialog__footer{
      text-align: center;
    }
    .el-dialog__body{
      padding: 30px;
    }

  }
  button {
    margin-bottom: 20px;
  }
}
.column-dialog{
  .column-list {
    display: flex;
    margin-bottom: 10px;
    border-bottom: 1px solid #eee;
    &:last-child{
      border: none!important;
    }
    &>.column-name {
      margin-right: 20px;
      width: 80px;
      min-width: 80px;
      line-height: 40px;
      .tag-list {
        .box {
          margin-bottom: 10px;
        }
      }
    }
    &>div{
      margin-bottom: 10px;
    }
    .column-list{
      border-bottom: 1px dotted #eee;
    }
    .column-group{
      height: auto;
    }
    .more{
      height: 90px!important;
      overflow: hidden;
    }
    .el-checkbox-group{
      flex:1;
      line-height: 40px;
    }
    .column-type {
      margin-right: 20px;
    }
    .toggle-btn{
      padding: 0 20px 0 5px;
      cursor: pointer;
    }
    .tag-list {
      margin-right: 20px;
      margin-top: 4px;
    }
    .flex-1{
      flex: 1;
    }
  }
  .dialog-footer{
    text-align: center;
  }
}
</style>
