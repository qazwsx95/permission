import {get,post,put,remove} from "@utils/request.js";

/**
 * 获取用户信息
 * @param params {username:string,createTime:Array,status:string,page:number,pageSize:number} 查询条件
 * @returns {Promise<AxiosResponse<any>>}
 * 分页查询
 */
export const findAll =(params)=> {
    //      处理时间格式
            let queryString = []
            for (let name in params) {
                if (Array.isArray(params[name])) {
                    for (const value of params[name]) {
                        queryString.push(`${name}=${value}`)
                    }
                }else {
                    queryString.push(`${name}=${params[name]}`)
                }
            }
    return get('/account?'+queryString.join('&'))

}
/**
 * 修改用户状态
 * @param id
 * @param status
 * @returns {Promise<AxiosResponse<*>>}
 */
export const updateStatus=(id,status)=>{
    return post(`/account/change`,{id,status})
}
/**
 * 修改用户信息
 * @param account
 * @returns {Promise<AxiosResponse<*>>}
 */
export const updateById=({id,password})=>{
    return post('/account',{id,password})
}
/**
 * 新增用户信息
 * @param account
 * @returns {Promise<AxiosResponse<*>>}
 */
export const insertAccount=(account)=>{
    return put('/account',account)
}
/**
 * 批量删除账户
 * @param ids
 * @returns {Promise<AxiosResponse<*>>}
 */
export const deleteByIds = (ids)=> {
    return remove('/account',ids)
}