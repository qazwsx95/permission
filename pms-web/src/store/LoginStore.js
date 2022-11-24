//定义存储库   先创建根存储库
import {defineStore} from "pinia";
// 存储信息 定义需要保存的信息
const state = {
    token: '',
    permission: [],
    roles: [],
}

export default defineStore('login',{
    //存储信息 state是一个函数，返回上面定义的state
    state:()=>state,
    //setter actions是一个对象
    actions:{
        //注销  登录不应携带token等
        logout(){
            this.token='';
            this.permission=[];
            this.roles=[];
        },
        //定义一个函数设置登录状态
        //data:{需要的数据}
        setLoginStatus(data){
            console.log(this)
            //这里this指向loginStore
            this.token = data.token
            this.permission = data.permissions
            this.roles = data.roles
        }
    },
    getters:{
        //获取用户登录状态
        isLogin(){
            //两次取反，转换成boolean类型
            return ()=>!!this.token
        }
    },
    persist:{
        enabled:true
    }
})