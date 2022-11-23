import {get,post,put,remove} from "@utils/request.js";

/**
 * 获取用户信息
 * @param params {username:string,createTime:Array,status:string,page:number,pageSize:number} 查询条件
 * @returns {Promise<AxiosResponse<any>>}
 * 分页查询
 */
export const findAll =(params)=> {
    return get('/account',params);
}
/**
 * 修改用户状态
 * @param id
 * @param status
 * @returns {Promise<AxiosResponse<*>>}
 */
export const updateStatus=(id,status)=>{
    return get(`/account/change`,{id,status})
}
/**
 * 修改用户信息
 * @param account
 * @returns {Promise<AxiosResponse<*>>}
 */
export const updateById=(account)=>{
    return post('/account',account)
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