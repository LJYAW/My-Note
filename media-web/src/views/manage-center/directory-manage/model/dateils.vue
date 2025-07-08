<!--
 * @Author: your name
 * @Date: 2021-08-05 17:02:09
 * @LastEditTime: 2021-08-10 12:00:46
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/directory-manage/model/dateils.vue
-->
<template>
  <div class="dateils-warp">
    <div class="svg-box">
      <svg-icon icon-class="catalogue" class="svg-icon" />
    </div>
    <div class="title">详细信息</div>
    <div class="message-list">
      <ul v-if="daetilsData">
        <li>
          <!-- <iconpark-icon name="wenjianjiaxinxi" />          -->
          <!-- <svg-icon :icon-class="item.iconClass" class="svg-file" /> -->
          <svg-icon icon-class="catalogue" class="svg-file" />
          <div class="messge-box">
            <div class="label">{{ daetilsData.names }}</div>
            <div class="message">创建时间:{{ daetilsData.create_time }}</div>
          </div>
        </li>
        <li>
          <svg-icon icon-class="catalogue" class="svg-file" />
          <div class="messge-box">
            <div class="label">文件占用空间</div>
            <div class="message">{{ daetilsData.create_time }}</div>
          </div>
        </li>
        <li>
          <svg-icon icon-class="catalogue" class="svg-file" />
          <div class="messge-box">
            <div class="label">项目成员</div>
            <div class="message">{{ daetilsData.create_time }}人</div>
          </div>
        </li>
        <li>
          <svg-icon icon-class="catalogue" class="svg-file" />
          <div class="messge-box">
            <div class="label">创建人</div>
            <div class="message">{{ daetilsData.create_time }}</div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    id: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      daetilsData: {}
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.initData()
  },
  mounted() {

  },
  methods: {
    async initData() {
      const { err, res } = await this.$get(`/dirs/${this.id}`)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.daetilsData = res.data
    }
  }
}
</script>

<style scoped lang="scss">
.dateils-warp {

  .title {
    font-size: 12px;
    font-weight: 600;
    color: #404040;
    margin-top: 30px;
  }

  .svg-box {
    text-align: center;

    .svg-icon {
      width: 88px;
      height: 60px;
    }
  }

  .message-list {
    margin-top: 20px;

    ul {

      li {
        display: flex;
        margin-top: 18px;

        .svg-file {
          width: 12px;
          height: 15px;
          color: rgba(64, 64, 64, 1);
          margin-right: 20px;
        }

        .messge-box {

          .label {
            font-size: 14px;
            font-weight: 450;
            color: #404040;
          }

          .message {
            font-size: 12px;
            font-weight: 400;
            color: rgba(64, 64, 64, .5);
            margin-top: 5px;
          }
        }
      }
    }
  }
}
</style>
