<!--
 * @Author: your name
 * @Date: 2020-11-13 14:24:08
 * @LastEditTime: 2020-11-19 14:57:42
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/components/table.vue
-->
<template>
  <div>
    <table class="table w-100">
      <thead>
        <tr>
          <th v-for='(value,key) in title' :key='key'>{{value}}</th>
          <th v-if='operate'>操作</th>
          <slot name='th'></slot>
        </tr>
      </thead>
      <tbody v-if='data.length'>
        <tr v-for="(item,index) in data"
          :key="index">
          <td v-for='(value,key) in title' :key='key'>
            <div class="img-wrap" v-if='key=="cover_pic"'>
              <img :src="item.cover_pic"
                width="80"
                height="45">
            </div>
            <span v-else-if='key=="status"&&item.status == "发布失败"' :class='[key=="status"&&item.status == "发布失败"&&"fail"]'>{{item.fail_msg}}</span>
            <span v-else>{{key=='total_amount_fen'?changePrice(item[key]):item[key]}}</span>
          </td>
          <td v-if='operate'>
            <a href="" @click='getData(item)'>{{operate_name}}</a>
          </td>
          <slot name='td'></slot>
        </tr>
      </tbody>
      <tbody v-else>
        <slot name='noData'></slot>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  props: ['data', 'title', 'operate', 'operate_name'],
  props: {
    data: {
      type: Array,
      default() {
        return []
      }
    },
    title: {
      type: Object,
      default() {
        return {}
      }
    },
    operate: {
      type: Boolean,
      default() {
        return false
      }
    },
    operate_name: {
      type: String,
      default() {
        return ''
      }
    }
  },
  data() {
    return {}
  },
  computed: {},
  watch: {},
  methods: {
    getData(val) {
      this.$emit('routerGo', val)
    },
    changePrice(val) {
      return '￥' + val * 0.01
    }
  },
  components: {},
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
table {
  position: relative;
  .no-data {
    position: absolute;
    top: 150px;
    left: 50%;
    transform: translateX(-50%);
  }
}
</style>
