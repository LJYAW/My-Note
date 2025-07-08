<!--
 * @Author: your name
 * @Date: 2021-12-23 17:47:15
 * @LastEditTime: 2021-12-23 17:48:55
 * @LastEditors: Please set LastEditors
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: /production_web/src/views/EPG/new-system-pad/right_pad_v2.vue
-->
<template>
  <div class="ad-edit-form">
    <div class="btn-wrap">
      <el-button type="warning" size="mini" @click="hangCenter">挂起</el-button>
      <el-button type="primary" size="mini" :disabled="btnLoading" @click="submitCenter">完成</el-button>
    </div>
    <el-form ref="ruleForm" :inline="true" :model="fromData" label-width="100px">
      <el-form-item label="标题名称:" prop="title">
        <el-autocomplete
          v-model="fromData.title"
          :disabled="disabled"
          type="textarea"
          :fetch-suggestions="querySearchAsync"
          placeholder="请输入标题名称"
          clearable
        />
      </el-form-item>
      <el-form-item label="栏目名称:" prop="item_id">
        <div class="category">
          <el-select
            v-if="!itemLoading"
            ref="searchData"
            v-model="fromData.item_id"
            filterable
            size="mini"
            clearable
            placeholder="请选择栏目名称"
            :disabled="disabled"
            @input.native="filterData"
            @clear="clear"
          >
            <el-option
              v-for="item in itemData"
              :key="item.item_id"
              size="mini"
              :label="item.name"
              :value="item.item_id"
              @click.native.prevent="change(item)"
            />
          </el-select>
          <base-btn size="mini" class="add-btn" @click="addCategory">新增</base-btn>
        </div>
      </el-form-item>
      <!-- 分类 -->
      <!-- <el-form-item label="" prop="" class="category-box">
        <div class="message">
          <div>一级分类:</div>
          <span>{{ form.first_category_names }}</span>
        </div>
        <div class="message">
          <div>二级分类:</div>
          <span>{{ form.second_category_names }}</span>
        </div>
      </el-form-item> -->
      <!-- 定制分类 -->
      <el-form-item label="" prop="category_id" class="category-box">
        <div class="title-box">
          <div class="title">定制分类</div>
          <base-btn size="mini" class="add-btn" type="" :disabled="disabled" @click="edit">编辑</base-btn>
          <span class="guide" @click="guide">?指南</span>
        </div>
        <div class="message">
          <div>一级分类:</div>
          <span>{{ fromData.first_customer_category_names }}</span>
        </div>
        <div class="message">
          <div>二级分类:</div>
          <span>{{ fromData.second_customer_category_names }}</span>
        </div>

      </el-form-item>
      <el-form-item label="">
        <el-checkbox v-model="fromData.is_prime_time" true-label="1" false-label="0" label="黄金时段" />
        <el-checkbox v-model="fromData.is_premiere" true-label="1" false-label="0" label="首播" />
        <div class="form-btn">
          <base-btn size="mini" :disabled="disabled" @click="saveData">保存</base-btn>
        </div>
      </el-form-item>

    </el-form>
    <div class="table-content">
      <el-table :data="tableData" :row-class-name="tableRowClassName" @row-click="rowClick">
        <el-table-column prop="type" label="类型">
          <template slot-scope="scope">
            <span :class="scope.row.type==='正片'?'positive':'advertisement'">{{ scope.row.position }}</span>
          </template>
        </el-table-column>
        <el-table-column label="切点" prop="date" width="110px">
          <template slot-scope="scope">
            <div class="date">{{ scope.row.timeStr }}</div>
          </template>
        </el-table-column>
        <el-table-column prop="title" label="标题" width="110px">
          <template slot-scope="scope">
            <div class="epg-title">{{ scope.row.title }}</div>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120px">
          <template slot-scope="scope">
            <base-btn type="text" @click.stop="deleteData(scope.$index)">删除</base-btn>
            <!-- <base-btn v-if="scope.row.type==='正片'&&scope.row.position==='开始'" type="text" @click.stop="editData(scope.$index,scope)">编辑</base-btn> -->
          </template>
        </el-table-column>
      </el-table>
    </div>
    <base-dialog :show="show" :title="title" width="800px" class="add" :close-on-click-modal="true" @close="close">
      <component
        :is="activeName"
        :item-list="itemList"
        :column-name="columnName"
        @createdItem="createdItem"
        @resetData="resetData"
        @closeData="closeData"
      />
    </base-dialog>
  </div>
</template>

<script>
import Channel from './column/index'
import EditCustom from './model/EditCustom'
import HangDialog from './model/Hang.vue'
import { mapGetters } from 'vuex'

const BASE_FROM_DATA = () => {
  return {
    title: '',
    item_id: '',
    is_premiere: '0', // 是否首播 0 否 1 是
    is_prime_time: '0', // 是否黄金时段 0 否 1是
    position: '', // 切点位置（枚举值：开始、结束
    start_time: '', // 开始时间戳（毫秒）
    type: '', // 类型 （枚举值：广告、正片)
    first_customer_category_ids: [],
    first_customer_category_names: '',
    second_customer_category_ids: [],
    second_customer_category_names: ''
  }
}
export default {
  components: {
    Channel,
    EditCustom,
    HangDialog
  },
  props: {

  },
  data() {
    return {
      fromData: BASE_FROM_DATA()
    }
  },
  computed: {

  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {

  }
}
</script>

<style lang="scss" scoped>
@import './scss/right-pad.scss';
</style>
