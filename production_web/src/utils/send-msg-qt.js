/* eslint-disable no-unused-vars */
/*
 * @Author: your name
 * @Date: 2021-01-20 14:29:32
 * @LastEditTime: 2021-03-08 16:02:29
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/utils/openQweb.js
 */
/* eslint-disable */

import axios from "@/axios/request";
import { getToken } from "@/utils/auth"; // get token from cookie

export function createdColumn(status = false) {
  try {
    // 编辑栏目时的回调：
    // onServerAddEPGItemNotify(bool bOK);
    // bok=true时表示添加成功，false表示取消了。

    let bok = status

    var webChannel = new QWebChannel(
      qt.webChannelTransport, // 这里的webChannel是全局的变量，可以在其它位置访问
      function(channel) {
        window.TaskEditor = channel.objects.TaskEditor;
        window.TaskEditor.onServerAddEPGItemNotify(bok);
      }
    );

  } catch (error) {
    alert(error.message); // 接住抛出的自定义错误
  }
}
