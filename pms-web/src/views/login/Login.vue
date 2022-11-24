<template>
  <div class="login">
  <el-form ref="form" :model="userInfo" :rules="rules">
    <el-form-item class="center">
      <el-badge :value="'v1.0.0'" type="success">
      <h1>权限管理系统</h1>
      </el-badge>
    </el-form-item>
    <el-form-item label="账号" prop="username">
      <el-input v-model="userInfo.username" clearable :autocomplete="false" placeholder="请输入手机号"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input v-model="userInfo.password" type="password" clearable show-password placeholder="请输入密码"></el-input>
    </el-form-item>
    <el-form-item label="验证码" prop="code">
      <el-input v-model="userInfo.code" clearable  :placeholder="codeBtnInfo.placeholder">
        <template #append>
          <el-button
              size="small"
              :loading="codeBtnInfo.loading"
              :disabled="codeBtnInfo.disabled"
              @click="getCode"
          >{{ codeBtnInfo.text }}</el-button>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item class="center">
      <el-button type="primary"  @click="login">登录</el-button>
      <el-button @click="login">注册</el-button>
    </el-form-item>
  </el-form>
  </div>
</template>
<script setup>
//没有导出default,即export default的写法
import * as loginApi from "@api/login.api.js"
import {ElMessage} from "element-plus";
import  {useRouter} from "vue-router"
import useStore from "@store/LoginStore.js"
const userInfo = $ref({
  username: "",
  password: "",
  code: "",
})
const codeBtnInfo= $ref({
  text:'获取验证码',
  placeholder:'请输入验证码',
  disabled:false,
  loading:false,
})
const rules = $ref({
  username:[{required:true,message:'请输入账号',trigger:'blur'}],
  password:[{required:true,message:'请输入密码',trigger:'blur'}],
  code:[{required:true,message:'请输入验证码',trigger:'blur'}],
})
const form = $ref(null)
const router = useRouter()
//调用useStore 函数得到存储库
const loginStore = useStore()



function login(){
  form.validate((isValid,errors)=>{
    if(isValid){
      //注销
      loginStore.logout()
      //表单校验通过
      loginApi.login(userInfo).then(res=>{
        ElMessage.success('登录成功')
        loginStore.setLoginStatus(res.data)
        router.push({name:'Home'})

      }).catch(data=>{
        console.log(data)
        let msg = data;
        if(data.msg){
          msg = data.msg
        }
        ElMessage.error({
          message: msg,
          group: true
        })
      })
    }
  })
}



// function login(){
//   form.validate((isValid,errors)=>{
//     if(isValid){
//       //todo 发送登录请求
//       loginApi.login(userInfo).then(res=>{
//         //登录成功
//         ElMessage.success('登录成功')
//         console.log(res.data);
//         // loginStore.setLoginStatus(res.data)
//       //  跳转到Home
//         router.push({name:'Home'})
//       }).catch(err=>{
//         //  否则  登录不成功   data是Promise.reject(data)
//         console.log(err)
//         let msg = err
//         if (err.msg){
//           msg= err.msg
//           console.log(err);
//         }
//         ElMessage.error({
//           message:msg,
//           group:true
//         })
//       })
//     }
//     // else{
//     //   // console.log(errors)  errors=>验证规则报错信息
//     // }
//   })
// }


function  getCode(){
  codeBtnInfo.loading=true;
  codeBtnInfo.text="正在获取验证码"
  codeBtnInfo.disabled=true

  setTimeout(() =>{
    codeBtnInfo.loading=false;
    codeBtnInfo.placeholder='请输入验证码，序号：40'
    let time = 60
    let  index = setInterval(() =>{
      codeBtnInfo.text=time+"s后重新获取"
      time--
      if (time===0){
        codeBtnInfo.text="获取验证码"
        codeBtnInfo.disabled=false
      //  index用于取消setinterval
        clearInterval(index)
      }
    },1000)
    codeBtnInfo.text='重新获取验证码（60）'
  },3000)
}
</script>
<style scoped>
.login{
  height: 100%;
  width: 100%;
  background:url("@assets/1.jpg") no-repeat center fixed;
  background-size: cover;
}
.el-form{
  max-width: 400px;
  width:60vw;
  position: absolute;
  top:50%;
  left:50%;
  transform: translate(-50%, -50%);
  box-shadow: 0 0 10px rgba(0,0,0,0.2);
  padding: 20px;
  border-radius: 5px;
  transition: all .5s;
  animation: breath 2s infinite alternate;
  animation-direction: reverse;
/*  添加背景毛玻璃*/
  background: rgba(0,0,0,0.1);
  backdrop-filter: blur(10px);
}
/*.el-form:hover{*/
/*  box-shadow: 0 0 20px rgba(0,0,0,.6);*/
/*  transition: all .5s;*/
/*}*/
@keyframes breath {
  /*倒转实现*/
  from{
    box-shadow: 0 0 10px rgba(255,255,255,.2);
  }
  to{
    box-shadow: 0 0 10px rgba(255,255,255,.6)
  }


  /*0%{*/
  /*  box-shadow: 0 0 10px rgba(0,0,0,.2);*/
  /*}*/
  /*50%{*/
  /*  box-shadow: 0 0 20px rgba(0,0,0,.6);*/
  /*}*/
  /*100%{*/
  /*  box-shadow: 0 0 10px rgba(255,255,255,.2);*/
  /*}*/
}
.center :deep(.el-form-item__content){
justify-content: center;
}
/*改elment-plus的变量*/
.el-form-item{
  --el-text-color-regular: #000;
}
</style>