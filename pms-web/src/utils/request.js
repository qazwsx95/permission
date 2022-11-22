import axios from "axios";
//弹窗
import {ElMessage} from "element-plus"
//方便在外面进行修改
const baseURL = import.meta.env.VITE_BASE_URL
//创建axios实例
const instance = axios.create({
    baseURL,
    timeout: 5000,
    //在其他地方使用就可以不加Content-Type
    headers: {'Content-Type': 'application/json'}
});
//开始导出
export default instance

//响应拦截器
instance.interceptors.response.use((response) => {
//    response拦截器，请求完成时做的事情
//    封装成promise对象
    return new Promise((resolve, reject) => {
        if (response.data.code === 200) {
            resolve(response.data)
        }else {
        //    就算响应码是200,也认为失败
            reject(response.data)
        }
    })
},(error)=>{
    // 请求报错时
    if (error.response.data){
        //对错误信息分组，有重复信息进行重复统计
        ElMessage.error({
            message: error.response.data.msg,
            //分组
            group: true
        })
    }else {
    //    否则直接弹窗
        ElMessage.error({
            message: error.message,
            group: true
        })
    }
})

/**
 * get请求
 * @param url 请求地址
 * @param params 请求参数
 * @returns {Promise<AxiosResponse<any>>}
 */
export function get(url ,params) {
    //返回promise  链式调用
    // `params` 是与请求一起发送的 URL 参数
    return instance.get(url,{
        params
    })
}

/**
 * post请求
 * @param url 请求地址
 * @param data 请求参数
 * @returns {Promise<AxiosResponse<any>>}
 */
export function post(url,data){
    console.log(url)
    // `data` 是作为请求体被发送的数据
    return instance.post(url,data)
}
/**
 * put请求
 * @param url 请求地址
 * @param data 请求参数
 * @returns {Promise<AxiosResponse<any>>}
 */
export function put (url ,data) {
    return instance.put(url,data);
}
/**
 * delete请求
 * @param url 请求地址
 * @param params 请求参数
 * @returns {Promise<AxiosResponse<any>>}
 */
export function remove (url ,params) {
    return instance.delete(url,{
        params
    })
}
