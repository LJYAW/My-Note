<!--
 * @Author: your name
 * @Date: 2020-12-01 11:07:51
 * @LastEditTime: 2021-02-18 12:20:35
 * @LastEditors: your name
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/home_page/footer.vue
-->
<template>
  <footer class="footer-wrap">
    <div class="container">
      <div class="d-flex">
        <img src="../../assets/images/homepage/footer_qr.png"
          width="66"
          height="66">
        <div class="d-flex flex-column justify-content-around ml-30px">
          <ul class="d-flex fc-white mr-auto fz-12px">
            <li v-for="(item,index) in nav_list"
              :key="index"
              class="mr-15px">
              <a :href="item.link">
                {{item.name}}
              </a>
            </li>
          </ul>
          <p class="fc-999 d-flex">weijian.ai Copyright©️2019 天脉聚源（杭州）传媒科技有限公司
            <a href="https://beian.miit.gov.cn/" target="_blank" class="ml-10px"> 浙ICP备19020326号-2
          </p>
        </div>

        <div class="ml-auto">
          <img src="../../assets/images/homepage/TVM.png" style="width:100px;height:26px;" class="mt-20px">
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
  background: #343536;
  padding: 50px 0 60px 0;
}
</style>
