<!--
 * @Author: your name
 * @Date: 2021-01-22 19:44:43
 * @LastEditTime: 2021-01-23 10:33:00
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/pdf/ColumnPdf.vue
-->
<template>
  <div>
    <pdf
      v-for="page in numPages"
      :key="page"
      :src="pdfUrl"
      :page="page"
      @num-pages="pageCount=$event"
    />
  </div>

</template>

<script>
import pdf from 'vue-pdf'
export default {
  components: {
    pdf
  },
  props: {

  },
  data() {
    return {
      currentPage: 0, // pdf文件页码
      pageCount: 0, // pdf文件总页数
      numPages: 0,
      pdfUrl: ''
    }
  },
  computed: {
    pdfName() {
      return this.$route.query.name || 'lanmu'
    }
    // pdfUrl() {
    //   const url = `/static/pdf/${this.pdfName}.pdf`
    //   // 有时PDF文件地址会出现跨域的情况,这里最好处理一下
    //   return pdf.createLoadingTask(url)
    // }
  },
  watch: {

  },
  created() {
  },
  mounted() {
    this.loadPdfHandler()
  },
  methods: {
    async loadPdfHandler() {
      // src为你服务器上存放pdf的路径
      const url = `/static/pdf/${this.pdfName}.pdf`
      this.pdfUrl = pdf.createLoadingTask(url)
      if (this.pdfUrl.promise) {
        this.pdfUrl.promise.then(pdf => {
          this.numPages = pdf.numPages
        })
      }
    }

  }
}
</script>

<style scoped lang="scss">

</style>
