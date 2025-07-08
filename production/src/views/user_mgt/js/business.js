/*
 * @Author: your name
 * @Date: 2021-02-24 17:15:43
 * @LastEditTime: 2021-03-26 17:08:50
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/user_mgt/js/business.js
 */
export default [
  [
    {
      id: '整档编辑',
      name: '整档编辑'
    },
    {
      id: '整档编辑管理员',
      name: '整档编辑管理员'
    }
  ],
  [
    {
      id: '拆条编辑',
      name: '拆条编辑'
    },
    {
      id: '拆条编辑管理员',
      name: '拆条编辑管理员'
    }
  ],
  [
    {
      id: 'EPG编辑',
      name: 'EPG编辑',
      mutex: ['EPG审核', 'EPG审核管理员']
    },
    {
      id: 'EPG编辑管理员',
      name: 'EPG编辑管理员',
      mutex: ['EPG审核', 'EPG审核管理员']
    },
    {
      id: 'EPG审核',
      name: 'EPG审核',
      mutex: ['EPG编辑', 'EPG编辑管理员']
    },
    {
      id: 'EPG审核管理员',
      name: 'EPG审核管理员',
      mutex: ['EPG编辑', 'EPG编辑管理员']
    }
  ],
  [
    {
      id: '广告编辑',
      name: '广告编辑',
      mutex: ['广告审核', '广告审核管理员']
    },
    {
      id: '广告编辑管理员',
      name: '广告编辑管理员',
      mutex: ['广告审核', '广告审核管理员']
    },
    {
      id: '广告审核',
      name: '广告审核',
      mutex: ['广告编辑', '广告编辑管理员']
    },
    {
      id: '广告审核管理员',
      name: '广告审核管理员',
      mutex: ['广告编辑', '广告编辑管理员']
    }
  ]
]
