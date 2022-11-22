// 总入口
import {createRouter,createWebHistory} from 'vue-router'
import LoginRouter from "@router/LoginRouter"
import HomeRouter from "@router/HomeRouter.js";
import ErrorRouter from "@router/ErrorRouter.js";
import useLogin from '@store/LoginStore.js'


const routes = []
//当运行到这里，pinia还未激活，放到下面beforeEach即可
// const loginStore = useLogin()

//一级路由从外面导入，比如登录的路由：LoginRouter.js
// const routes= [LoginRouter]
// const routes = [LoginRouter,
//     {...LoginRouter}
// ]
routes.push(LoginRouter)
routes.push(HomeRouter)

routes.push(ErrorRouter)
const router = createRouter({
    routes,
    history:createWebHistory()
})

router.beforeEach((to,from)=>{
    //刷新直接清空pinia pinia存在于内存，需要持久化pinia 安装插件
    const loginStore = useLogin()
    if (to.name==='Login'){
        return true
    }
    //让isLogin返回一个函数就可以加括号
    if (loginStore.isLogin()){
        return true
    }
    return {name:'Login'}
})
export default router