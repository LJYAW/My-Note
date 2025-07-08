<!--
 * @Author: your name
 * @Date: 2021-07-29 16:32:32
 * @LastEditTime: 2021-08-05 18:36:35
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/components/loading/index.vue
-->
<template>
  <div class="loading-wrap">
    <div class="loader">
      <svg class="circular" viewBox="25 25 50 50">
        <circle
          class="path"
          cx="50"
          cy="50"
          r="20"
          fill="none"
          stroke-width="3"
          stroke-miterlimit="10"
        />
      </svg>
    </div>
  </div>
</template>

<style scoped lang="scss">
.loading-wrap {
  width: 100%;
  height: 100%;
  background-color: rgba(0,0,0,.2);
  display: flex;
  justify-content: center;
  align-items: center;
}

.loader {
  position: relative;
  width: 80px;
  height: 80px;
}

.loader:before {
  content: "";
  display: block;
  padding-top: 100%;
}

.circular {
  animation: rotate 2s linear infinite;
  height: 100%;
  transform-origin: center center;
  width: 100%;
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  margin: auto;
}

.path {
  stroke-dasharray: 1, 200;
  stroke-dashoffset: 0;
  animation: dash 2s ease-in-out infinite, color 6s ease-in-out infinite;
  stroke-linecap: round;
}

.ani_opa,
.ani_opa_hide {
  animation-duration: .2s;
  animation-timing-function: ease-in-out;
  animation-fill-mode: both;
}

@keyframes rotate {

  100% {
    transform: rotate(360deg);
  }
}

@keyframes dash {

  0% {
    stroke-dasharray: 1, 200;
    stroke-dashoffset: 0;
  }

  50% {
    stroke-dasharray: 89, 200;
    stroke-dashoffset: -35;
  }

  100% {
    stroke-dasharray: 89, 200;
    stroke-dashoffset: -124;
  }
}

@keyframes color {

  0%,
  100% {
    stroke: #5675e8;
  }

  40% {
    stroke: #5675e8;
  }

  66% {
    stroke: #5675e8;
  }

  80%,
  90% {
    stroke: #5675e8;
  }
}

</style>
