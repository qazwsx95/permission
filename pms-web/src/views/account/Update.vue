<template>
  <el-form>
    <el-form-item label="账号" prop="username">
      <el-input v-model="accountModel.username" disabled  placeholder="请输入账号"/>
<!--      <el-input v-model="props.modelValue.username" @update="update" placeholder="请输入账号"/>-->
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input v-model="accountModel.password" type="password" show-password placeholder="请输入密码"/>
    </el-form-item>
<!--    <el-form-item label="角色" prop="roleIds">-->
<!--      <el-input v-model="modelValue" placeholder="请选择角色"/>-->
<!--    </el-form-item>-->
    <el-form-item>
      <el-button type="primary" @click="save">保存</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import {defineProps, ref, defineEmits, watch, onMounted, reactive} from "vue";
import * as accountApi from '@api/account.api.js'
//接受一个对象进来
const props = defineProps({
  account:{
    type:Object,
    required:true
  }
})
let accountModel  = $ref({
  username:'',
  password:'',
  id:''
})
watch(props,(newVal)=>{
  accountModel = props.account
})
onMounted(()=>{
  accountModel = props.account
})
const emits = defineEmits(['close'])

function save(){
  accountApi.updateById(accountModel)
      .then(res=>{
        //发送close事件，关闭窗口
        if (res.code===200){
          emits('close',true)
        }
      })
}
</script>

<style scoped>










</style>