//创建根存储库
import {createPinia} from 'pinia'
import piniaPluginPersist from 'pinia-plugin-persist'

export default {
    //导出vue插件
    install(app){
        const pinia = createPinia();
        //使用持久化存储插件
        pinia.use(piniaPluginPersist);
        app.use(pinia)
    }
}