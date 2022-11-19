// 总入口
import {createRouter,createWebHistory} from 'vue-router'
import LoginRouter from "@router/LoginRouter"

//一级路由从外面导入，比如登录的路由：LoginRouter.js
const routes= [LoginRouter]
// const routes = [LoginRouter,
//     {...LoginRouter}
// ]
// routes.push(LoginRouter)
const router = createRouter({
    routes,
    history:createWebHistory()
})
export default router