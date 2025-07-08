<!--
 * @Author: your name
 * @Date: 2021-01-14 14:19:38
 * @LastEditTime: 2021-12-22 16:51:14
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/views/full_business/task_mgt/models/assign_task.vue
-->
<template>
  <div class="edit_custom">
    <el-form ref="form" v-loading="loading" :model="form" label-width="100px" :rules="rules">
      <el-form-item label="栏目:">
        <div>{{ columnName }}</div>
      </el-form-item>
      <el-form-item label="一级分类:" prop="first_category_id">
        <el-radio-group v-model="form.first_category_id">
          <el-radio
            v-for="(item,index) in categoryList"
            :key="item.category_id"
            :label="item.category_id"
            style="margin-top:15px;"
            @click.native.prevent="clickitem(item.category_id,item.category_name,'first_category_id','first_category_name',index)"
          >
            {{ item.category_name }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="二级分类:">
        <el-radio-group v-if="activeIndex > -1" v-model="form.second_category_id">
          <el-radio
            v-for="(item) in categoryList[activeIndex].child"
            :key="item.category_id"
            :label="item.category_id"
            style="margin-top:15px;"
            @click.native.prevent="clickitem(item.category_id,item.category_name,'second_category_id','second_category_name')"
          >
            {{ item.category_name }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item class="btns">
        <el-button type="primary" @click="submitForm()">确定</el-button>
        <el-button @click="cancel()">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
export default {
  name: 'EditCustom',
  components: {},
  props: {
    itemList: {
      type: Object,
      default: () => {
        return {}
      }
    },
    columnName: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      loading: false,
      activeIndex: -1,
      form: {
        item_id: '',
        first_category_id: '',
        second_category_id: '',
        first_category_name: '',
        second_category_name: ''
      },
      categoryList: [],
      rules: {
        first_category_id: [{
          required: true,
          message: '请选择一级分类',
          trigger: 'change'
        }]
        // 客户定制分类
        // second_category_id: [{
        //   required: true,
        //   message: '请选择耳机分类',
        //   trigger: 'change'
        // }]
      }
    }
  },
  computed: {
    ...mapGetters(['CusClassData'])
  },
  watch: {
    itemList: {
      handler(newName, oldName) {
        if (newName.item_id) {
          Object.keys(this.form).forEach(item => {
            this.form[item] = newName[item]
          })
        }
      },
      immediate: true
    }
  },
  async created() {
    this.loading = true
    // await this.$store.dispatch('channel/getCusList')
    await this.getNewCustomerCategoryList()
    this.loading = false
  },
  mounted() {
  },
  methods: {
    async getNewCustomerCategoryList() {
      const res = await this.$get('/epg-task/new-customer-category-list')
      if (res.code === '0000') {
        this.categoryList = res.data
      }
    },
    close() {
      this.$emit('resetData', this.form)
    },
    cancel() {
      this.close()
    },
    clickitem(e, name, item, item1, index) {
      e === this.form[item] ? this.form[item] = '' : this.form[item] = e
      name === this.form[item1] ? this.form[item1] = '' : this.form[item1] = name
      index > -1 && (this.activeIndex = index)
    },
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.close()
        } else {
          console.log('error submit!!')
          return false
        }
      })
      // this.$post('/epg-task/edit-category', this.form).then((res) => {
      //   this.$message({
      //     type: 'success',
      //     message: '提交成功'
      //   })
      //   this.close()
      // }).catch((error) => {
      //   this.$message.error(error.msg)
      // })
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
