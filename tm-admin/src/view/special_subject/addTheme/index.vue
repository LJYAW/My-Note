<!--
 * @Author: your name
 * @Date: 2021-04-01 17:10:59
 * @LastEditTime: 2021-04-27 17:05:10
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /vue-beautiful-template/src/view/home/add/index.vue
-->
<template>
  <div class="add-wrap">
    <div class="add-header">
      <input v-model="themeName" type="text" placeholder="请输入专题名称" class="themeName">
      <div class="btn-wrap">
        <el-button type="primary" size="default" @click="chooseTempDl = true">选择模板</el-button>
        <el-button type="primary" size="default">预览</el-button>
        <el-button type="primary" size="default" @click="publishDl = true">发布</el-button>
      </div>
    </div>

    <el-alert type="success">
      <div slot="title">
        <i class="el-icon-bell" />
        张XX 账号123455343 于2020-02-23 12：30更新了数据
      </div>
    </el-alert>

    <div class="add-content">
      <div class="tree">
        <el-tree :data="treeList" :props="defaultProps" :expand-on-click-node="false" default-expand-all>
          <span slot-scope="{ node }">
            <span>{{ node.label }}</span>

            <div v-if="node.level == 1" class="addNode">
              <input type="text" placeholder="二级分类">
              <span @click="add('增加二级')">添加</span>
            </div>
          </span>
        </el-tree>
        <div class="addNode">
          <input type="text" placeholder="一级分类">
          <span @click="add('增加一级')">添加</span>
        </div>
      </div>
      <div class="table">
        <!-- 关键字 -->
        <div class="keyword">
          <div><input type="text" placeholder="添加关键词"><span>确定</span></div>
          <div @click="keywordDl = true"><span>屏蔽关键词</span></div>
        </div>
        <div class="tableTab">
          <el-tabs v-model="keyWordActive">
            <el-tab-pane v-for="(item, i) in tab" :key="i" :label="item.name" />
          </el-tabs>
          <div>
            <el-button type="default" size="default" @click="localUpdataDl = true">本地上传</el-button>
            <el-button type="default" size="default">网络抓取</el-button>
          </div>
        </div>

        <el-alert title="关键词01新增 200 条内容" type="error" />
        <!-- 表格项状态筛选 -->
        <div class="state">
          <div class="videoState">
            <div class="title">视频状态：</div>
            <div class="select">
              <span :class="stateType == '未添加' ? 'active' : ''" @click="stateType = '未添加'">未添加</span>
              <span :class="stateType == '已添加' ? 'active' : ''" @click="stateType = '已添加'">已添加</span>
            </div>
          </div>
          <div class="showState">
            <div class="title">状态：</div>
            <el-select v-model="videoState" value-key="" clearable filterable>
              <el-option v-for="item in stateOptions" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
          </div>
          <div class="source">
            <div class="title">来源：</div>
            <el-select v-model="source" value-key="" clearable filterable>
              <el-option v-for="item in sourceOptions" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
          </div>

          <div class="checkState">
            <el-checkbox v-model="autoAdd" :indeterminate="false">新增内容自动添加</el-checkbox>
          </div>
        </div>
        <!-- 表格主体 -->
        <el-table :data="tableData" style="width: 100%">
          <el-table-column v-if="false" type="selection" />
          <el-table-column label="视频封面">
            <template slot-scope="scope">
              <img :src="scope.row.poster">
            </template>
          </el-table-column>
          <el-table-column prop="title" label="视频标题" />
          <el-table-column prop="source" label="视频来源" />
          <el-table-column prop="date" label="日期" />
          <el-table-column prop="status" label="状态" />
          <el-table-column fixed="right" label="操作" style="position: relative">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="handleClick(scope.row)">置顶</el-button>
              <el-button type="text" size="small" @click="handleClick(scope.row)">不显示</el-button>
              <el-button type="text" size="small" class="delete" @click="handleClick(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <!-- dialog  -->
    <!-- 本地上传 -->
    <el-dialog title="本地上传" :visible.sync="localUpdataDl" width="40%" center class="upload-dl" @close="localUpdataDl = false">
      <!-- action必选参数, 上传的地址 -->
      <el-upload class="video-uploader" action="http://jsonplaceholder.typicode.com/posts/" :show-file-list="false" :on-success="handleVideoSuccess" :before-upload="beforeUploadVideo" :on-progress="uploadVideoProcess">
        <video v-if="videoForm.Video&& !videoFlag" :src="videoForm.Video" controls="controls">您的浏览器不支持视频播放</video>
        <i v-else-if="!videoForm.Video && !videoFlag" class="el-icon-plus video-uploader-icon" />
        <el-progress v-if="videoFlag" type="circle" :percentage="videoUploadPercent" style="margin-top: 30px" />
      </el-upload>

      <span class="uploadAgain">重新上传</span>

      <div class="videoName">
        <span>视频名称：</span>
        <el-input v-model="videoName" placeholder="" size="normal" clearable />
      </div>
      <span slot="footer">
        <el-button type="primary">确定</el-button>
      </span>
    </el-dialog>
    <!-- 选择模板 -->
    <el-dialog
      :visible.sync="chooseTempDl"
      title="选择模板"
      width="56%"
      center
      class="temp-dl"
      @close="chooseTempDl = false"
    >
      <div class="temp-wrap">
        <div v-for="i in 10" :key="i" class="temp-item">
          <div class="temp-img" />
          <div class="temp-name">党建</div>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="centerDialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 屏蔽关键字 -->
    <el-dialog :visible.sync="keywordDl" class="keyword-dl" @close="keywordDl = false">
      <div class="shield"><input type="text" placeholder="屏蔽关键词"><span @click="addKeyword">确定</span></div>
      <div class="shield-keyword">
        <el-tag v-for="tag in dynamicTags" :key="tag" closable :disable-transitions="false" @close="handleClose(tag)">
          {{ tag }}
        </el-tag>
      </div>
    </el-dialog>
    <!-- 发布 -->
    <el-dialog :visible.sync="publishDl" title="发布" center class="publish-dl" width="28%" @close="publishDl = false">
      <div class="d-flex justify-content-center">
        <div>发布前端：</div>
        <el-checkbox-group v-model="checkList">
          <el-checkbox label="发布web内容" />
          <el-checkbox label="发布手机端内容" />
          <el-checkbox label="发布大屏端内容" />
        </el-checkbox-group>
      </div>

      <div class="d-flex align-items-center justify-content-center mt-40">
        <div>到期时间：</div>
        <el-date-picker v-model="endTime" type="datetime" placeholder="选择日期时间" />
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="centerDialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  components: {},
  props: {},
  data() {
    return {
      themeName: '',
      videoForm: {
        Video: ''
      },
      videoFlag: false,
      videoUploadPercent: 0,
      videoName: '',
      localUpdataDl: false,

      treeList: [
        { label: '一级', children: [{ label: '二级' }, { label: '二级' }] },
        { label: '一级', children: [{ label: '二级' }] }
      ],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      stateType: '已添加',
      tab: [{ name: '关键词1' }, { name: '关键词2' }, { name: '关键词3' }],
      stateOptions: [
        { lable: '全部', value: '全部' },
        { lable: '显示', value: '显示' },
        { lable: '不显示', value: '不显示' }
      ],
      sourceOptions: [
        { lable: '全部', value: '全部' },
        { lable: '微剪库', value: '微剪库' },
        { lable: '本地上传', value: '本地上传' },
        { lable: '网络抓取', value: '网络抓取' }
      ],
      videoState: '全部',
      source: '全部',
      autoAdd: false,
      keyWordActive: 0,
      tableData: [
        {
          status: '显示',
          date: '2016-05-02',
          title: '视频标题1',
          source: '视频来源1',
          poster: 'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png'
        }
      ],

      chooseTempDl: false,
      keywordDl: false,
      dynamicTags: ['标签一', '标签二', '标签三'],
      publishDl: false,
      checkList: [],
      endTime: ''
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    handleClick(scope) {
      console.log(scope)
    },
    add(message) {
      console.log(message)
    },

    // 验证方法：验证视频格式和视频大小
    beforeUploadVideo(file) {
      const isLt10M = file.size / 1024 / 1024 < 100
      if (['video/mp4', 'video/ogg', 'video/flv', 'video/avi', 'video/wmv', 'video/rmvb'].indexOf(file.type) === -1) {
        this.$message.error('请上传正确的视频格式')
        return false
      }
      if (!isLt10M) {
        this.$message.error('上传视频大小不能超过100MB哦!')
        return false
      }
    },
    uploadVideoProcess(event, file, fileList) {
      this.videoFlag = true
      this.videoUploadPercent = Number(file.percentage.toFixed(0))
    },

    handleVideoSuccess(res, file) {
      // 获取上传图片地址
      this.videoFlag = false
      this.videoUploadPercent = 0
      if (res.status === 200) {
        this.videoForm.videoUploadId = res.data.uploadId
        this.videoForm.Video = res.data.uploadUrl
      } else {
        this.$message.error('视频上传失败，请重新上传！')
      }
    },
    addKeyword() {
      this.dynamicTags.push()
    },
    handleClose(tag) {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1)
    }
  }
}
</script>

<style scoped lang="scss">
.add-wrap {
  padding: 27px;
  .add-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;

    .themeName {
      border: none;
      width: 354px;
      border-bottom: 2px solid #bbb;
      padding-bottom: 6px;
      color: rgba(153, 153, 153, 100);
      font-size: 16px;
      font-weight: bold;
    }
  }
  .add-content {
    margin-top: 32px;
    display: flex;
    height: 70vh;
    .tree {
      width: 200px;
      height: 100%;
      .addNode {
        display: inline-block;
        width: 100px;
        margin-left: 12px;
        input {
          width: 68px;
          margin-right: 4px;
          color: rgba(153, 153, 153, 100);
          font-size: 12px;
          border: 1px solid rgba(187, 187, 187, 100);
        }
        span {
          color: rgba(42, 112, 193, 100);
          font-size: 12px;
          border-bottom: 1px solid rgba(42, 112, 193, 100);
        }
      }
      ::v-deep.el-tree-node__content {
        height: auto;
      }
    }
    .table {
      width: 0;
      flex: 1;
      padding-left: 27px;
      border-left: 1px solid rgba(233, 233, 233, 100);
      .tableTab {
        @include flex($h: space-between);
        border-bottom: 1px solid rgba(233, 233, 233, 100);
        padding-bottom: 10px;
        margin-bottom: 20px;
      }
      .keyword {
        @include flex($h: space-between);
        cursor: pointer;
        color: rgba(42, 112, 193, 100);
        font-size: 14px;
        input {
          width: 100px;
          height: 37px;
          border: 1px solid rgba(187, 187, 187, 100);
          color: rgba(153, 153, 153, 100);
          font-size: 14px;
          text-align: center;
          margin-right: 10px;
        }
        span {
          display: inline-block;
          border-bottom: 1px solid rgba(42, 112, 193, 100);
        }
        .delete {
          color: red;
        }
      }
      .state {
        width: 100%;
        display: flex;
        align-items: center;
        margin-top: 20px;
        margin-bottom: 22px;
        position: relative;
        .title {
          color: rgba(16, 16, 16, 100);
          font-size: 14px;
        }
        .videoState {
          display: flex;
          align-items: center;
          border-right: 1px solid #e0e0e0;
          padding-right: 31px;
          span {
            display: inline-block;
            color: rgba(153, 153, 153, 100);
            font-size: 14px;
            width: 65px;
            height: 26px;
            line-height: 26px;
            text-align: center;
            cursor: pointer;
          }
          .active {
            color: white;
            background: #2a70c1;
            border-radius: 25px;
          }
        }
        .showState {
          display: flex;
          align-items: center;
          margin-left: 22px;
          margin-right: 32px;
        }
        .source {
          display: flex;
          align-items: center;
          justify-content: space-between;
        }
        .checkState {
          position: absolute;
          right: 0;
        }
        .el-select {
          width: 110px;
        }
      }
    }
  }
}

.upload-dl {
  .video-uploader {
    width: 100%;
    height: 200px;
    margin: 0 auto;
    margin-bottom: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    border: 1px solid #dcdfe6;
    border-radius: 10px;
    .video-uploader-icon {
      font-size: 30px;
      color: #dcdfe6;
    }
  }
  .uploadAgain {
    color: rgba(42, 112, 193, 100);
    font-size: 14px;
    border-bottom: 1px solid rgba(42, 112, 193, 100);
  }
  .videoName {
    width: 100%;
    display: flex;
    align-items: center;
    margin-top: 14px;
    span {
      display: block;
      width: 90px;
    }
  }
}

.temp-dl {
  .temp-wrap {
    display: flex;
    // justify-content: space-between;
    flex-wrap: wrap;
    .temp-item {
      margin-right: 20px;
      margin-top: 20px;
      .temp-img {
        width: 170px;
        height: 96px;
        background: #eee;
      }
      .temp-name {
        margin-top: 8px;
        color: rgba(16, 16, 16, 100);
        font-size: 14px;
        text-align: center;
      }
    }
  }
}

.keyword-dl {
  .shield {
    input {
      width: 100px;
      height: 37px;
      border: 1px solid rgba(187, 187, 187, 100);
      color: rgba(153, 153, 153, 100);
      font-size: 14px;
      text-align: center;
      margin-right: 10px;
    }
    span {
      cursor: pointer;
      color: rgba(42, 112, 193, 100);
      display: inline-block;
      border-bottom: 1px solid rgba(42, 112, 193, 100);
    }
  }
  .shield-keyword {
    margin-top: 12px;
    .el-tag {
      margin-right: 12px;
    }
  }
}
.publish-dl {
  .el-checkbox-group {
    width: 200px;
  }
  .el-checkbox {
    display: block;
    margin-bottom: 16px;
  }
  .el-date-editor {
    width: 200px;
  }
  .mt-40 {
    margin-top: 21px;
  }
}

::v-deep.el-table .cell {
  max-height: 100vh;
  display: flex;
  align-items: center;
  margin: 10px auto;
  img {
    width: 124px;
    height: 70px;
    object-fit: cover;
  }
}
::v-deep.el-tabs .el-tabs__active-bar {
  background: transparent;
}
</style>
