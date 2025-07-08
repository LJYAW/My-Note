<template>
  <div class="tab_wrap">
    <ul>
      <li v-for="(item,index) in data" :key="index">
        <div>
          <p>{{item.name}}</p>
          <p>{{item.num}}</p>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: '',
  data() {
    return {
      data: [
        {
          name: '剪辑视频次数',
          num: 111111111111111,
        },
        {
          name: '下载视频次数',
          num: 111111111111111,
        },
        {
          name: '剪辑视频时长(min)',
          num: 111111111111111,
        },
      ],
    }
  },
  components: {},
  computed: {},
  watch: {},
  methods: {},
  created() {},
  mounted() {},
}
</script>

<style lang="scss" scoped>
.tab_wrap {
  ul {
    display: flex;
    margin-top: 20px;
    li {
      width: calc(100% / 3);
      height: 100px;
      background: #5b9bea 10000%;
      color: #ffffff;
      margin-right: 15px;
      text-align: center;
      border-radius: 3px;
      //   line-height: 100px;
      display: table;
      div {
        height: 100%;
        display: table-cell;
        vertical-align: middle;
        p {
          margin-bottom: 10px;
        }
      }
    }
  }
}
</style>
