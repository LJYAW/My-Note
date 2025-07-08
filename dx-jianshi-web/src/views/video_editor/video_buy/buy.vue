<template>
  <div class="bg-white p-30px">

    <el-form ref="form"
             style="min-width: 500px"
             :model="form"
             label-width="80px">
      <el-form-item label="视频标题">
        <el-input v-model="form.name"></el-input>
      </el-form-item>

      <el-form-item label="活动形式">
        <el-input type="textarea"
                  v-model="form.desc"></el-input>
      </el-form-item>

      <el-form-item label="原创">
        <el-checkbox-group v-model="form.type">
          <el-checkbox label=""
                       name="type"></el-checkbox>
        </el-checkbox-group>
      </el-form-item>

      <el-form-item label="分类">
        <el-select v-model="form.region"
                   placeholder="请选择活动区域">
          <el-option label="区域一"
                     value="shanghai"></el-option>
          <el-option label="区域二"
                     value="beijing"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="发布渠道">

      </el-form-item>

    </el-form>

    <!-- 同意按钮 -->
    <div class="fc-gray-888 d-flex justify-content-between align-items-center mt-15px px-18px">
      <div class="fz-12px fc-999">
        <input type="checkbox"
               checked="checked">
        <span @click="getAgreen()">阅读并同意《版权授权剪辑使用协议》</span>
      </div>
      <button @click="buy()"
              class="btn btn-primary border-radius-30px"
              style="line-height: 1;">确认</button>
    </div>

    <videoAgreement />
  </div>
</template>

<script>
import videoAgreement from '@/components/model_forms/videoAgreement'

export default {
  props: [],
  data() {
    return {
      second: localStorage.getItem('user_account'),
      total_s: 0,
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      }
    }
  },
  components: {
    videoAgreement
  },

  computed: {},

  watch: {},

  methods: {
    getAgreen() {
      this.$store.commit('modalShow', 'videoAgreement')
    },
    buy() {},
    onSubmit() {
      console.log('submit!')
    }
  },

  created() {},

  mounted() {}
}
</script>

<style scoped lang="scss">
.table {
  font-size: 14px;
  tr {
    td:nth-of-type(2) {
      color: #333;
      line-height: 1.4;
    }
    td {
      border-bottom: #fff;
    }
  }
}
</style>
