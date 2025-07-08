/* eslint-disable no-extend-native */
/* eslint-disable eqeqeq */
/*
 * @Author: your name
 * @Date: 2021-03-02 15:54:33
 * @LastEditTime: 2021-03-02 15:58:18
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/utils/time.js
 */
/* eslint-disable no-sequences */

!(function(e) {
  var t = {}
  function n(r) {
    if (t[r]) return t[r].exports
    var o = (t[r] = { i: r, l: !1, exports: {}})
    return e[r].call(o.exports, o, o.exports, n), (o.l = !0), o.exports
  }
  (n.m = e),
  (n.c = t),
  (n.d = function(e, t, r) {
    n.o(e, t) || Object.defineProperty(e, t, { enumerable: !0, get: r })
  }),
  (n.r = function(e) {
    typeof Symbol !== 'undefined' &&
        Symbol.toStringTag &&
        Object.defineProperty(e, Symbol.toStringTag, { value: 'Module' }),
    Object.defineProperty(e, '__esModule', { value: !0 })
  }),
  (n.t = function(e, t) {
    if ((1 & t && (e = n(e)), 8 & t)) return e
    if (4 & t && typeof e === 'object' && e && e.__esModule) return e
    var r = Object.create(null)
    if (
      (n.r(r),
      Object.defineProperty(r, 'default', { enumerable: !0, value: e }),
      2 & t && typeof e !== 'string')
    ) {
      for (var o in e) {
        n.d(
          r,
          o,
          function(t) {
            return e[t]
          }.bind(null, o)
        )
      }
    }
    return r
  }),
  (n.n = function(e) {
    var t =
        e && e.__esModule
          ? function() {
            return e.default
          }
          : function() {
            return e
          }
    return n.d(t, 'a', t), t
  }),
  (n.o = function(e, t) {
    return Object.prototype.hasOwnProperty.call(e, t)
  }),
  (n.p = ''),
  n((n.s = 0))
})([
  function(e, t, n) {
    'use strict'
    var r = (function() {
      function e(e, t) {
        for (var n = 0; n < t.length; n++) {
          var r = t[n];
          (r.enumerable = r.enumerable || !1),
          (r.configurable = !0),
          'value' in r && (r.writable = !0),
          Object.defineProperty(e, r.key, r)
        }
      }
      return function(t, n, r) {
        return n && e(t.prototype, n), r && e(t, r), t
      }
    })()
    var o = (function() {
      function e() {
        !(function(e, t) {
          if (!(e instanceof t)) {
            throw new TypeError('Cannot call a class as a function')
          }
        })(this, e),
        (Date.prototype.Format = function(e) {
          var t = ''
          switch (this.getDay()) {
            case 0:
              t = '星期日'
              break
            case 1:
              t = '星期一'
              break
            case 2:
              t = '星期二'
              break
            case 3:
              t = '星期三'
              break
            case 4:
              t = '星期四'
              break
            case 5:
              t = '星期五'
              break
            case 6:
              t = '星期六'
          }
          var n = {
            'M+': this.getMonth() + 1,
            'd+': this.getDate(),
            'h+': this.getHours(),
            'm+': this.getMinutes(),
            's+': this.getSeconds(),
            w: t
          }
          for (var r in (/(y+)/.test(e) &&
              (e = e.replace(
                RegExp.$1,
                (this.getFullYear() + '').substr(4 - RegExp.$1.length)
              )),
          n)) {
            new RegExp('(' + r + ')').test(e) &&
                (e = e.replace(
                  RegExp.$1,
                  RegExp.$1.length == 1
                    ? n[r]
                    : ('00' + n[r]).substr(('' + n[r]).length)
                ))
          }
          return e
        })
      }
      return (
        r(e, [
          {
            key: 'convert',
            value: function(e) {
              var t =
                arguments.length > 1 && void 0 !== arguments[1]
                  ? arguments[1]
                  : 'y-m-d h:m:s'
              return t == 'y-m-d h:m:s'
                ? new Date(e).Format('yyyy-MM-dd hh:mm:ss')
                : t == 'y-m-d'
                  ? new Date(e).Format('yyyy-MM-dd')
                  : new Date(e).Format(t)
            }
          },
          {
            key: 'getAppointDate',
            value: function(e) {
              var t =
                arguments.length > 1 && void 0 !== arguments[1]
                  ? arguments[1]
                  : 1
              var n = arguments[2]
              var r = new Date()
              var o = this.convert(r, n)
              if (e == 'w') {
                var a = new Date(r - 6048e5 * t)
                o = this.convert(a, n)
              } else {
                e == 'm'
                  ? (r.setMonth(r.getMonth() - t), (o = this.convert(r, n)))
                  : e == 'y' &&
                    (r.setYear(r.getFullYear() - t), (o = this.convert(r, n)))
              }
              return o
            }
          }
        ]),
        e
      )
    })()
    window.dateConvert = o
  }
])

//    调用构造函数
const DC = window.dateConvert
//    实例化
const dateFun = new DC()

export default dateFun

//    常用方法的简写
/**
 *
    let dateVal1 = dateFun.convert(new Date(),'y-m-d');//2019-05-23
    let dateVal2 = dateFun.convert(new Date(),'y-m-d h:m:s');//2019-05-23 17:17:42
    //    常规方法示例
    let dateVal3 = dateFun.convert(1323122444323,'yyyy-MM-dd hh:mm:ss w');//2011-12-06 06:00:44 星期二
    let dateVal4 = dateFun.convert(new Date(),'yyyy-MM-dd hh:mm:ss w');//2019-05-23 17:17:42 星期四
    let dateVal5 = dateFun.convert(new Date(),'yyyy-MM-dd hh:mm:ss');//2019-05-23 17:17:42
    let dateVal6 = dateFun.convert(new Date(),'yyyy-MM-dd');//2019-05-23
    let dateVal7 = dateFun.convert(new Date(),'w');//星期四
    let dateVal8 = dateFun.convert(new Date(),'MM');//05月
    let dateVal9 = dateFun.convert(new Date(),'yyyy');//年份
    console.log(dateVal1);
    console.log(dateVal2);
    console.log(dateVal3);
    console.log(dateVal4);
    console.log(dateVal5);
    console.log(dateVal6);
    console.log(dateVal7);
    console.log(dateVal8);
    console.log(dateVal9);
 */

