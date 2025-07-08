<!--
 * @Author: your name
 * @Date: 2021-04-16 16:47:54
 * @LastEditTime: 2021-04-22 12:06:03
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/edit_custom_class/index.vue
-->
<template>
  <div class="edit_custom_class">
    <el-form :inline="true">
      <el-form-item label="栏目名称">
        <el-input v-model="form.name" placeholder="请输入栏目名称" clearable />
      </el-form-item>
      <el-form-item label="频道名称">
        <el-select v-model="form.channel_id" filterable clearable placeholder="请选择频道" style="width:100%">
          <el-option
            v-for="item in channelData"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="是否有分类">
        <el-select v-model="form.class" placeholder="请选择是否有客户定制分类">
          <el-option label="区域一" value="shanghai" />
        </el-select>
      </el-form-item> -->
      <el-form-item label="客户定制分类">
        <el-select v-model="form.first_category_id" placeholder="一级分类" clearable>
          <el-option v-for="(item,index) in CusClassData.first_category" :key="index" :label="item.category_name" :value="item.category_id" />
        </el-select>
        <el-select v-model="form.second_category_id" placeholder="二级分类" style="margin-left:10px" clearable>
          <el-option v-for="(item,index) in CusClassData.second_category" :key="index" :label="item.category_name" :value="item.category_id" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <base-btn size="big" @click.native="search">查询</base-btn>
        <base-btn size="big" @click.native="clear">清除</base-btn>
      </el-form-item>
    </el-form>
    <div class="total">当前条件下共{{ total }}条数据</div>
    <div>
      <el-table v-loading="loading" :data="CustomClassList.list" border>
        <el-table-column prop="name" label="栏目名称" />
        <el-table-column prop="channel_name" label="频道名称" />
        <el-table-column prop="first_category_name" label="一级分类" />
        <el-table-column prop="second_category_name" label="二级分类" />
        <el-table-column label="操作">
          <template slot-scope="scope">
            <base-btn type="text" @click.native="editCustomClass(scope.row)">编辑客户定制分类</base-btn>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="page-list">
      <base-pag :total="total" :limit="limit" :page="page" @current-change="handleCurrentChange" @size-change="handleSizeChange" />
    </div>
    <EditCustom ref="EditCustom" :show="EditCustomShow" @close="close" />
  </div>
</template>

<script>
import EditCustom from './model/EditCustom'
import { mapGetters } from 'vuex'
export default {
  name: 'EditCustomClass',
  components: { EditCustom },
  props: {},
  data() {
    return {
      EditCustomShow: false,
      loading: false,
      total: null,
      limit: 10,
      page: 1,
      form: {
        name: '',
        channel_id: '',
        class: '',
        first_category_id: '',
        second_category_id: ''
      }
    }
  },
  computed: {
    ...mapGetters(['channelData', 'CustomClassList', 'CusClassData'])
  },
  watch: {},
  mounted() {},
  created() {
    this.$store.dispatch('channel/getChannelData')
    this.$store.dispatch('channel/getCusList')
    this.getList()
  },
  methods: {
    setParams() {
      const params = {
        name: this.form.name,
        channel_id: this.form.channel_id,
        page: this.page,
        limit: this.limit,
        first_category_id: this.form.first_category_id,
        second_category_id: this.form.second_category_id
      }
      return params
    },
    getList() {
      this.loading = true
      this.$store.dispatch('channel/getEditCustomList', this.setParams())
        .then(() => {
          this.total = this.CustomClassList.total
          this.loading = false
        })
        .catch((res) => {
          this.$message({
            message: res.msg,
            type: 'error'
          })
        })
        .finally(() => {
          this.loading = false
        })
    },
    close() {
      this.EditCustomShow = false
      this.$refs.EditCustom.initData = []
      this.$refs.EditCustom.rowData = {}
    },
    search() {
      this.getList()
    },
    clear() {
      Object.keys(this.form).forEach(item => {
        this.form[item] = ''
      })
      this.getList()
    },
    editCustomClass(item) {
      this.EditCustomShow = true
      this.$refs.EditCustom.initData = this.CustomClassList.list
      this.$refs.EditCustom.rowData = item
    },
    handleCurrentChange(val) {
      this.page = val
      this.getList()
    },
    handleSizeChange(val) {
      this.limit = val
      this.getList()
    }
  }
}
</script>

<style lang='scss'>
    @import './scss/index.scss'
</style>
