<!--
 * @Author: zzx
 * @Date: 2020-07-29 15:12:07
 * @LastEditTime: 2020-11-27 11:56:46
 * @FilePath: /video_edit/README.md
-->

## 视频剪辑

```
 // 发送视频数据  src 是必选字段
        let playerOptions = {
          height: "360",
          autoplay: true,
          muted: true,
          language: "en",
          playbackRates: [0.7, 1.0, 1.5, 2.0],
          fluid: true,
          src:
            "https://cdn-magic.weijian.video/storage/mnt/online/interactive_video/video/2020-11-10/NFZbPMLn35JBL2Tj7hiLoxxp6yBJZANDnB1z7aEG.mp4",
          poster: "",
        };

        let iframe = document.getElementById("videoEditiframe").contentWindow;
        iframe.postMessage({ cmd: "setVideoOptions", data: playerOptions },"*");



        // let activeBtnList = [{"eventFn":"跳转视频片段","jumpTo":"A","img":"","text":"选项1","eventParams":"","popoverVisible":false,"editable":false,"style":{"width":"120px","height":"36px","backgroundImage":"https://cdn-magic.weijian.video/storage/mnt/online/intelligent_writing/caption_background/redianxinwen.png"}},{"eventFn":"跳转视频片段","jumpTo":"A","img":"","text":"选项2","eventParams":"","popoverVisible":false,"editable":false,"style":{"width":"120px","height":"36px","backgroundImage":"https://cdn-magic.weijian.video/storage/mnt/online/intelligent_writing/caption_background/huanqiucaijing.png"}},{"eventFn":"跳转视频片段","jumpTo":"A","img":"","text":"选项3","eventParams":"","popoverVisible":false,"editable":false,"style":{"width":"120px","height":"36px","backgroundImage":""}}]
        let activeBtnList = [
          {
            h: 30,
            imgUrl: "https://res-1300249927.file.myqcloud.com/media/79/79/image/3799578837347725/source.png",
            w: 100
          },
          {
            h: 30,
            imgUrl: "https://res-1300249927.file.myqcloud.com/media/79/79/image/3799578837347725/source.png",
            w: 100
          },
          {
            h: 30,
            imgUrl: "https://res-1300249927.file.myqcloud.com/media/79/79/image/3799578837347725/source.png",
            w: 100
          },
          {
            h: 30,
            imgUrl: "https://res-1300249927.file.myqcloud.com/media/79/79/image/3799578837347725/source.png",
            w: 100
          },
        ]
        function iframeMsg (event) {
          // console.log("videoLoad -> event", event)
          if (event.data.cmd == "playerLoadeddata") {
            // console.log('playerLoadeddata');
            // 轨道生成生命周期
             // 发送剪辑选框数据
            let cutData = {
              start_ms: 10000, // 开始时间 必传
              end_ms: 20000, // 结束时间 必传
              max_ms: 30000, // 选框最大时间 可不填 不填默认的就是 视频总时长的宽度
            };
            // iframe.postMessage({ cmd: "setCutTrackDetails", data: cutData }, "*");
            iframe.postMessage({ cmd: "setActiveBtnList", data: activeBtnList }, "*");

            <!-- 添加马赛克数据 -->
            let activeBtnList = [
                {
                  end_ms: 17938,
                  height: 55,
                  start_ms: 1272,
                  width: 111,
                  x: 8,
                  y: 11
                }
              ]
            iframe.postMessage({ cmd: "setMosaicList", data: activeBtnList }, "*");

          }
          // 点击剪辑按钮的返回
          if (event.data.cmd == "exportcutDetails") {
            // console.log(event.data.data)
          }
          // 拖拽的返回
          if (event.data.cmd == "onDrag") {
            // console.log(event.data.data)
          }
          // 改变大小的返回
          if (event.data.cmd == "onResize") {
            // console.log(event.data.data)
          }
          //
          // 获取当前 每像素多少毫秒
          if (event.data.cmd == "setPerPxMs") {
            // console.log(event.data.data)
          }
          // 获取当前 cut 数据
          if (event.data.cmd == "getCutData") {
            // console.log(event.data.data)
          }
        }
        window.addEventListener('message', iframeMsg, false)
```
