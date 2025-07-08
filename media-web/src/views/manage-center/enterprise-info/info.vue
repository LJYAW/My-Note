<!--
 * @Author: your name
 * @Date: 2021-08-04 10:30:04
 * @LastEditTime: 2021-09-01 11:43:58
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/enterprise-info/info.vue
-->
<template>
  <div class="enterprise-wrap">
    <title-bar title="企业信息" />
    <el-form class="info-wrap" :inline="true">
      <el-form-item label="公司名称">
        <span v-if="!isUpdate" class="text">{{ form.names||'无' }}</span>
        <el-input v-else v-model="form.names" size="mini" />
      </el-form-item>
      <el-form-item label="公司地址">
        <span v-if="!isUpdate" class="text">{{ form.address||'无' }}</span>
        <el-input v-else v-model="form.address" size="mini" />
      </el-form-item>
      <el-form-item label="营业执照">
        <span v-if="!isUpdate" class="text">{{ form.yyzz||'无' }}</span>
        <el-input v-else v-model="form.yyzz" size="mini" />
      </el-form-item>
      <el-form-item label="管理名称">
        <span v-if="!isUpdate" class="text">{{ form.admin_name ||'无' }}</span>
        <el-input v-else v-model="form.admin_name" size="mini" />
      </el-form-item>
      <el-form-item label="绑定邮箱">
        <span v-if="!isUpdate" class="text">{{ form.admin_email||'无' }}</span>
        <el-input v-else v-model="form.admin_email" size="mini" />
      </el-form-item>
      <el-form-item label="身份证号">
        <span v-if="!isUpdate" class="text">{{ form.admin_sfz||'无' }}</span>
        <el-input v-else v-model="form.admin_sfz" size="mini" />
      </el-form-item>
      <el-form-item label="绑定手机">
        <span v-if="!isUpdate" class="text">{{ form.admin_phone||'无' }}</span>
        <el-input v-else v-model="form.admin_phone" size="mini" />
      </el-form-item>
    </el-form>
    <div class="btn-wrap">
      <base-btn
        v-if="!isUpdate"
        type="info"
        class="update-btn"
        size="mini"
        @click="isUpdate=true"
      >编辑</base-btn>
      <div v-else>
        <base-btn size="mini" @click="save">保存</base-btn>
        <base-btn type="info" size="mini" @click="cancel">取消</base-btn>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      isUpdate: false,
      form: {}
    }
  },
  computed: {
    ...mapGetters(['companyInfo'])
  },
  watch: {

  },
  created() {
    this.form = JSON.parse(JSON.stringify(this.companyInfo))
  },
  mounted() {

  },
  methods: {
    // 取消
    cancel() {
      this.isUpdate = false
      this.form = JSON.parse(JSON.stringify(this.enterpriseInfo))
    },
    // 保存
    save() {
      this.$emit('update', this.form)
      this.isUpdate = false
    }
  }
}
</script>

<style scoped lang="scss">
.enterprise-wrap {
  position: relative;

  .info-wrap {
    margin-top: 30px;
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
    position: relative;

    .el-form-item {
      width: 33%;
      margin-bottom: 10px;
      margin-right: 0;

      ::v-deep .el-form-item__label {
        color: #a0a0a0;
      }

      ::v-deep .el-form-item__content {
        width: calc(100% - 90px);

        input {
          background: #f8f8f8;
          border-radius: 4px;
          font-size: 14px;
          height: 30px;
          line-height: 30px;
          border: none;
          color: #404040;
        }
      }

      .text {
        display: block;
        color: #404040;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
      }
    }
  }

  .btn-wrap {
    position: absolute;
    right: 0;
    top: 17px;
  }
}
</style>
