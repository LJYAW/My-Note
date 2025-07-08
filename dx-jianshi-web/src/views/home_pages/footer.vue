<!--
 * @Author: zll
 * @Date: 2020-12-04 10:59:24
 * @LastEditTime: 2021-02-18 12:20:00
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/home_pages/footer.vue
-->
<template>
  <footer class="footer-wrap">
    <div class="container">
      <div class="d-flex">
        <img src="../../assets/images/homepage/footer_qr.png"
          width="66"
          height="66">
        <div class="ml-30px">
          <ul class="d-flex fc-white mr-auto fz-12px">
            <li v-for="(item,index) in nav_list"
              :key="index"
              class="mr-20px">
              <a :href="item.link">
                {{item.name}}
              </a>
            </li>
          </ul>
          <p class="fc-999 mt-20px d-flex" style="font-size:12px">weijian.video Copyright©️2020 天脉聚源（杭州）传媒科技有限公司
            <a href="https://beian.miit.gov.cn/" target="_blank" class="ml-10px"> 浙ICP备19020326号-2
            </a>
          </p>
        </div>
        <div class="ml-auto">
          <img src="../../assets/images/home_page/tvm.png" style="width:56px;height:56px;" class="mt-20px">
        </div>
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  props: {},
  data() {
    return {
      nav_list: [
        {
          name: '关于我们',
          link: '/',
        },
        {
          name: '资源上架',
          link: '/',
        },
        {
          name: '版权声明',
          link: '/',
        },
        {
          name: '联系我们',
          link: '/',
        },
        {
          name: '使用帮助',
          link: '/',
        },
      ],
    }
  },
  computed: {},
  watch: {},
  methods: {},
  components: {},
  created() {},
  mounted() {},
}
</script>

<style scoped lang="scss">
.footer-wrap {
  background: #ffffff;
  padding: 50px 0 60px 0;
  ul {
    margin-top: 10px;
    li {
      color: #333333;
      font-size: 14px;
    }
  }
}
</style>
