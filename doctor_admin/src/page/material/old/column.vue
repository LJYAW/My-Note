<template>
  <div class="column_wrap">
    <ul>
      <li>
        <div class="d-flex column_ref">
          <div class="channelTitle">
            <span>频道:</span>
            <span class="all">全部频道</span>
          </div>
          <div class="channelList" ref="channelList" :class="channel.hidetext==true?'hidetext':''">
            <div v-for="(channel_item,channel_index) in channelData" :key="channel_index">{{channel_item.name}}</div>
          </div>
          <div class="collapse" v-if="channel.nomore" @click="channelLookmore()">{{channel.moretext}}</div>
        </div>
      </li>
      <li>
        <div class="d-flex column_ref">
          <div class="channelTitle">
            <span>栏目:</span>
            <span class="all">全部栏目</span>
          </div>
          <div class="channelList" ref="columnList" :class="column.hidetext==true?'hidetext':''">
            <div v-for="(column_item,cloumn_index) in columnData" :key="cloumn_index">{{column_item.name}}</div>
          </div>
          <div class="collapse" v-if="column.nomore" @click="columnLookmore()">{{column.moretext}}</div>
        </div>
      </li>
    </ul>
    <div class="column"></div>
  </div>
</template>
<script>
export default {
  name: '',
  props: ['columnData', 'channelData'],
  data() {
    return {
      channel: {
        moretext: '展开',
        nomore: true,
        hidetext: false,
      },
      column: {
        moretext: '展开',
        nomore: true,
        hidetext: false,
      },
    }
  },
  components: {},
  computed: {},
  watch: {},
  methods: {
    channelLookmore() {
      this.channel.hidetext = !this.channel.hidetext
      this.channel.moretext = this.channel.hidetext == true ? '展开' : '收起'
    },
    columnLookmore() {
      this.column.hidetext = !this.column.hidetext
      this.column.moretext = this.column.hidetext == true ? '展开' : '收起'
    },
  },
  created() {},
  mounted() {
    //通过ref获取对应dom节点的高度，注意40为单行时的高度
    let channel_hei = this.$refs.channelList.clientHeight,
      column_hei = this.$refs.columnList.clientHeight
    //当获取的高度大于40时，即当前文本为多行，设置添加收起文本的class，
    //当获取的高度等于40时，隐藏查看更多按钮
    if (channel_hei > 20 && column_hei > 20) {
      this.channel.hidetext = true
      this.channel.nomore = true
      this.column.hidetext = true
      this.column.nomore = true
    } else if (channel_hei == 20 && column_hei == 20) {
      this.column.nomore = false
      this.channel.nomore = false
    }
  },
}
</script>

<style lang="scss" scoped>
.column_wrap {
  ul {
    .active {
      overflow: hidden;
    }
    li {
      width: 100%;
      overflow: visible;
      margin-bottom: 10px;
      .column_ref {
        position: relative;
        .channelTitle {
          width: 130px;
          height: 20px;
          line-height: 20px;
          .all {
            margin: 0 10px;
          }
        }
        .hidetext {
          position: relative;
          height: 20px;
          overflow: hidden;
        }
        .channelList {
          width: calc(100% - 200px);
          display: flex;
          letter-spacing: 0.5px;
          line-height: 20px;
          position: relative;
          flex-wrap: wrap;
          div {
            margin-right: 10px;
            color: #999999;
            font-size: 14px;
          }
        }
      }
      .collapse {
        position: absolute;
        top: 0;
        right: 0;
        width: 100px;
        text-align: center;
        height: 20px;
        line-height: 20px;
      }
    }
  }
}
</style>
