<!--
 * @Author: your name
 * @Date: 2021-04-09 15:26:33
 * @LastEditTime: 2021-04-28 16:55:25
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /vue-beautiful-template/src/view/public_sentiment/monitor_channel/index.vue
-->
<template>
  <div class="add-wrap">
    <div class="add-project-name">
      <input
        v-model="monitorName"
        type="text"
        placeholder="请输入项目名称"
        class="monitorName"
      >
    </div>
    <!-- 添加监测频道 -->
    <div class="d-flex align-items-center add-item flex-wrap">
      <div class="lable">监测频道：</div>
      <el-select
        v-model="channel"
        placeholder="输入关键词搜索然后选择"
        clearable
        filterable
      >
        <el-option
          v-for="item in options"
          :key="item.id"
          :label="item.names"
          :value="item.names"
        />
      </el-select>
      <div class="add-btn" @click="addChannelTag">添加</div>
      <div class="tags-wrap">
        <el-tag
          v-for="tag in channelTags"
          :key="tag"
          closable
          :disable-transitions="false"
          @close="closeChannelTag(tag)"
        >
          {{ tag }}
        </el-tag>
      </div>
    </div>
    <!-- 添加重点栏目 -->
    <div class="d-flex align-items-center add-item flex-wrap">
      <div class="lable">重点栏目：</div>
      <el-select
        v-model="selectVal"
        placeholder="选择频道"
        clearable
        filterable
      >
        <el-option
          v-for="item in pointChannel"
          :key="item"
          :label="item"
          :value="item"
        />
      </el-select>
      <el-input v-model="program" placeholder="输入栏目" size="normal" clearable />
      <el-time-picker
        v-model="programDate"
        is-range
        value-format="HH:mm:ss"
        range-separator="至"
        start-placeholder="开始时间"
        end-placeholder="结束时间"
        placeholder="选择时间范围"
      />
      <div class="add-btn" @click="addProgramTag">添加</div>
      <div class="tags-wrap">
        <el-tag
          v-for="tag in programTags"
          :key="tag"
          closable
          :disable-transitions="false"
          @close="closeProgramTag(tag)"
        >
          {{ tag }}
        </el-tag>
      </div>
    </div>
    <!-- 选择监测时间 -->
    <div class="d-flex align-items-center add-item">
      <div class="lable">监测时间：</div>
      <el-time-picker
        v-model="monitorDate"
        is-range
        value-format="HH:mm:ss"
        range-separator="至"
        start-placeholder="开始时间"
        end-placeholder="结束时间"
        placeholder="选择时间范围"
      />
    </div>
    <!-- 选择监测频次 -->
    <div class="d-flex align-items-center add-item">
      <div class="lable">监测频次</div>
      <el-radio-group v-model="radio">
        <el-radio :label="1">每天</el-radio>
      </el-radio-group>
    </div>
    <!-- 监测实体 -->
    <Entity ref="entity" :list="list" />
    <!-- 特殊提报 -->
    <Report ref="report" />
    <!-- 确定 -->
    <div class="d-flex add-item justify-content-center">
      <el-button type="primary" size="default" @click="submit">确定</el-button>
    </div>
  </div>
</template>

<script>
import Entity from './components/entity'
import Report from './components/report'

import { toArray, toString } from './js/utils'
import { validatorFn } from '@/utils/validate.js'
import rules from './js/rules'

import { paramsMixin } from './js/params'
export default {
  components: {
    Report,
    Entity
  },
  mixins: [paramsMixin],
  props: {},
  data() {
    return {
      monitorId: '',
      monitorName: '',
      channel: '',
      channelTags: [],
      programTags: [],
      selectVal: '',
      options: [],
      program: '',
      programDate: null,
      monitorDate: null,
      radio: 1,
      list: {},
      reportList: {
        keyword: [],
        images: []
      }
    }
  },
  computed: {
    pointChannel() {
      return this.channelTags.map(item => {
        console.log(item.split('|')[0])
        return item.split('|')[0]
      })
    }
  },
  watch: {},
  created() {
    this.initChannels()
    this.$store.commit('monitor/SET_REPORTLIST', this.reportList)
  },
  mounted() {
    if (this.$route.query.id) {
      this.monitorId = this.$route.query.id
      this.initProject(this.monitorId)
    }
  },
  methods: {
    // 修改获取初始化信息
    async initProject(id) {
      const { res } = await this.$get('/api/projects/' + this.monitorId)
      const data = res.data
      this.monitorName = data.projectname
      this.channelTags = toArray(data.channels)
      this.programTags = toArray(data.programs)
      this.monitorDate = [data.monitortimestart, data.monitortimeend]
      this.radio = data.frequency
      this.list = {
        // 自身
        entityListSelf: {
          brandMap: toArray(data.mselfbrand),
          logoMap: toArray(data.mselflogo),
          personageMap: toArray(data.mselfname),
          avatarMap: toArray(data.mselfavatar)
        },
        // 竞品
        entityListCompetitor: {
          brandMap: toArray(data.mcompetitorbrand),
          logoMap: toArray(data.mcompetitorlogo),
          personageMap: toArray(data.mcompetitorname),
          avatarMap: toArray(data.mcompetitoravatar)
        },
        // 行业关键词
        professionalList: toArray(data.mselfavatar)
      }
      // 特殊提报
      this.reportList = {
        keyword: toArray(data.diffkeywords),
        images: toArray(data.diffimages)
      }
      this.$store.commit('monitor/SET_REPORTLIST', this.reportList)
    },
    async initChannels() {
      const { res } = await this.$get('/api/channels', { limit: 1000 })
      this.options = res.data
    },
    // 添加tags
    addChannelTag() {
      validatorFn({ channel: rules['channel'] }, { channel: this.channel }, () => {
        console.log(this.channelTags.includes(this.channel))

        this.options.forEach((item) => {
          if (item.names === this.channel) {
            const str = item.names + '|' + item.urls
            if (this.channelTags.includes(str)) {
              this.$message.error('不要重复添加')
              return
            }
            this.channelTags.push(str)
          }
        })
        // this.channelTags.push(this.channel);
      })
    },
    addProgramTag() {
      const program = { programName: this.selectVal, program: this.program, programDate: this.programDate }
      validatorFn(rules['programRules'], program, () => {
        const text = `${this.selectVal}|${this.program}|${this.programDate[0]}|${this.programDate[1]}`
        if (this.programTags.includes(text)) {
          this.$message.error('不要重复添加')
          return
        }
        this.programTags.push(text)
      })
    },
    // 删除tags
    closeChannelTag(tag) {
      this.channelTags.splice(this.channelTags.indexOf(tag), 1)
    },
    closeProgramTag(tag) {
      this.programTags.splice(this.programTags.indexOf(tag), 1)
    },

    // 提交
    async submit() {
      if (!this.monitorDate) {
        this.$message({
          type: 'error',
          message: '请选择监测时间'
        })
        return
      }
      const params = this.getParams()
      const { err, res } = this.monitorId ? await this.$put('/api/projects/' + this.monitorId, params) : await this.$post('/api/projects', params)
      if (!err) {
        this.$message({
          type: 'success',
          message: this.monitorId ? '修改成功' : '创建成功'
        })
        this.$router.push({
          path: '/public_sentiment/monitor_project'
        })
      } else {
        this.$message({
          type: 'error',
          message: err.msg
        })
      }
    }
  }
}
</script>

<style scoped lang="scss">
.add-wrap {
  padding: 27px;
  .add-project-name {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 28px;
    .monitorName {
      border: none;
      width: 354px;
      border-bottom: 2px solid #bbb;
      padding-bottom: 6px;
      color: rgba(153, 153, 153, 100);
      font-size: 16px;
      font-weight: bold;
    }
  }
  ::v-deep.add-item {
    margin-bottom: 36px;
    .lable {
      flex-shrink: 0;
      width: 100px;
      color: rgba(16, 16, 16, 100);
      font-size: 14px;
    }
    .el-input {
      flex-shrink: 0;
      width: 230px;
      margin-right: 10px;
    }
    .el-select {
      flex-shrink: 0;
    }
    .add-btn {
      flex-shrink: 0;
      margin-left: 5px;
      color: rgba(41, 113, 193, 100);
      font-size: 14px;
      border-bottom: 1px solid rgba(41, 113, 193, 100);
      cursor: pointer;
    }
    .tags-wrap {
      flex-shrink: 0;
      width: 100%;
      margin-top: 20px;
      padding-left: 100px;
      .el-tag {
        margin-right: 30px;
        margin-bottom: 10px;
      }
    }

    .content {
      width: 100%;
      display: flex;
      flex-wrap: wrap;
      justify-content: space-between;
      .dialog-btn {
        color: rgba(41, 113, 193, 100);
        font-size: 12px;
        span {
          margin-left: 10px;
          cursor: pointer;
        }
      }
    }
  }
  ::v-deep .img-item {
    min-width: 200px;
    min-height: 150px;
    padding: 5px;
  }
  ::v-deep .el-image {
    // margin-top: 10px;
    max-width: 120px !important;
    // max-width: 120px;
  }
}
</style>
