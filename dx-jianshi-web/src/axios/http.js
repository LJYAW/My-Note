import axios from './index.js'
// sign

export function post(url, data) {
  return new Promise((resolve, reject) => {
    axios.post(url, data).then(
      (res) => {
        resolve(res)
      },
      (err) => {
        reject(err)
      }
    )
  })
}
export function get(url, data) {
  // var url = api_url_conf[url]

  return new Promise((resolve, reject) => {
    axios.get(url, data).then(
      (res) => {
        resolve(res)
      },
      (err) => {
        reject(err)
      }
    )
  })
}

export function deleteRun(url, data) {
  return new Promise((resolve, reject) => {
    axios.delete(url, data).then(
      (res) => {
        resolve(res)
      },
      (err) => {
        reject(err)
      }
    )
  })
}
