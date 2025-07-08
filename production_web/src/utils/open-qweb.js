/* eslint-disable no-unused-vars */
/*
 * @Author: your name
 * @Date: 2021-01-20 14:29:32
 * @LastEditTime: 2021-03-08 16:02:43
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/utils/openQweb.js
 */
/* eslint-disable */
import axios from "@/axios/request";
import { getToken } from '@/utils/auth' // get token from cookie


//  var webChannel = new QWebChannel(
//    qt.webChannelTransport, //这里的webChannel是全局的变量，可以在其它位置访问
//    function(channel) {
//      window.TaskEditor = channel.objects.TaskEditor;
//    }
//  );
//  function startTast() {
//    if (window.TaskEditor) {
//      window.TaskEditor.onEditTask("958018");
//    }
//  }

export default function openQweb(taskId) {
  let token = getToken()
  let id = Number(taskId)

  axios.post("/production/start-task", { task_id: id })
    .then(res => {
      let { data } = res.data

      if (data.status) {
        try {
          var webChannel = new QWebChannel(
            qt.webChannelTransport, // 这里的webChannel是全局的变量，可以在其它位置访问
            function(channel) {
              window.TaskEditor = channel.objects.TaskEditor;
              window.TaskEditor.onEditTask(id, token);
            }
          );

        } catch (error) {
          alert(error.message); // 接住抛出的自定义错误
        }
      } else {
        alert(data.msg); // 接住抛出的自定义错误
      }
    })



  // }
  // var webChannel = new QWebChannel('qt.webChannelTransport', // 这里的webChannel是全局的变量，可以在其它位置访问
  //   function(channel) {
  //     var webobj = channel.objects.webobj
  //     // window.foo = webobj // 将此webobj赋给了window.foo，则可以在其他函数中访问该对象(其中foo是任意合法名称，表示给window增加了一个成员)
  //     // webobj.content = 'sdfef中文'
  //     webobj.TaskEditor.onEditTask('981011')
  //     // document.getElementById('ctext').innerHTML = webobj.content

  //     // webobj.contentChanged.connect(updateattribute)
  //   })

  // var updateattribute = function(text) {
  //   console.log('🚀 ~ file: index.vue ~ line 40 ~ edit ~ webChannel', webChannel)
  //   // document.write(text);
  //   // var webobj = webChannel.objects.webobj; //访问全局变量webChannel
  //   alert(window.foo.content) // 这里可以访问全局的window.foo，它就是我们注册的webobj
  //   // alert(webobj.content);
  // }

  // this.$router.push({ path: '/full-business/task-choice' })
  // this.$router.push({ path: '/full-business/task-choice' })
}
