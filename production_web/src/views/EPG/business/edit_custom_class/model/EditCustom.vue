<!--
 * @Author: your name
 * @Date: 2021-01-14 14:19:38
 * @LastEditTime: 2021-04-21 22:13:54
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/views/full_business/task_mgt/models/assign_task.vue
-->
<template>
  <base-dialog
    :show="show"
    :title="dialogsObj.title"
    :width="dialogsObj.width"
    :center="dialogsObj.center"
    @close="close"
  >
    <div class="edit_custom">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="栏目:">
          <div>{{ name }}</div>
        </el-form-item>
        <el-form-item label="一级分类:">
          <el-radio-group v-model="form.first_category_id">
            <el-radio
              v-for="(item) in CusClassData.first_category"
              :key="item.category_id"
              :label="item.category_id"
              style="margin-top:15px;"
              @click.native.prevent="clickitem(item.category_id,'first_category_id')"
            >
              {{ item.category_name }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="二级分类:">
          <el-radio-group v-model="form.second_category_id">
            <el-radio
              v-for="(item) in CusClassData.second_category"
              :key="item.category_id"
              :label="item.category_id"
              style="margin-top:15px;"
              @click.native.prevent="clickitem(item.category_id,'second_category_id')"
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
  </base-dialog>
</template>
<script>
import { mapGetters } from 'vuex'
export default {
  name: 'EditCustom',
  components: {},
  props: {
    show: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      rowData: {},
      initData: [],
      name: '',
      form: {
        item_id: '',
        first_category_id: '',
        second_category_id: ''
      },
      dialogsObj: {
        width: '800px',
        title: '编辑客户定制分类',
        center: true
      }
    }
  },
  computed: {
    ...mapGetters(['CustomClassList', 'CusClassData'])
  },
  watch: {
    rowData(val) {
      if (val.channel_id) {
        Object.keys(this.form).forEach(item => {
          this.form[item] = val[item]
        })
        this.name = val.name
      }
    }
  },
  created() {
    this.$store.dispatch('channel/getCusList')
  },
  mounted() {
  },
  methods: {
    close() {
      this.$emit('close')
    },
    cancel() {
      this.close()
    },
    clickitem(e, item) {
      e === this.form[item] ? this.form[item] = '' : this.form[item] = e
    },
    submitForm() {
      this.$post('/epg-task/edit-category', this.form).then((res) => {
        this.$message({
          type: 'success',
          message: '提交成功'
        })
        this.$parent.getList()
        this.close()
      }).catch((error) => {
        this.$message.error(error.msg)
      })
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
