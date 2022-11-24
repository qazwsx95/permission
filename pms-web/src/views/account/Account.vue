<template>
  <div class="account">
    <div class="search">
      <el-form inline >
        <el-form-item label="用户名">
          <el-input v-model="search.username" placeholder="请输入用户名" clearable></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="search.status" clearable placeholder="请选择状态">
            <el-option label="启用" :value="0"></el-option>
            <el-option label="禁用" :value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="注册时间">
          <el-date-picker
            v-model="search.createTime"
            type="daterange"
            start-placeholder="开始日期"
            range-separator="至"
            end-placeholder="结束日期"
            clearable
          value-format="YYYY-MM-DD"
          format="YYYY-MM-DD">
          </el-date-picker>
        </el-form-item>
      </el-form>
    </div>
    <div class="data">
      <div class="btns">
        <el-button @click="updateAccount.show=true;updateAccount.type='新增';updateAccount.data={}">新增用户</el-button>
        <el-button @click="batchDelete">批量删除</el-button>
      </div>
      <el-table ref="tableData" :data="accounts" :load="loading">
        <el-table-column type="selection" fixed="left"></el-table-column>
        <el-table-column prop="username" label="用户名"></el-table-column>
        <el-table-column prop="updateTime" label="最后修改时间"></el-table-column>
        <el-table-column prop="createTime" label="注册时间"></el-table-column>
        <el-table-column prop="status" label="状态">
          <template #default="{row}">
            <el-switch
                v-model="row.status"
                :active-value="0"
                :inactive-value="1"
                inline-prompt
                active-text="正常"
                inactive-text="封禁"
                style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
                @change="changeStatus(row)"
                :loading="row.statusLoading"
            />
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作">
          <template #default="{row}">
            <el-button :size="'small'"
                       @click="updateAccount.show=true;updateAccount.data=row;updateAccount.type='修改'">修改</el-button>
            <el-popconfirm
                @confirm="deleteAccount(row)"
                :title="`是否删除[${row.username}]?`">
              <template #reference>
                <el-button :size="'small'" type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
        <template #append>
          <el-pagination
              v-model:current-page="page.page"
              v-model:page-size="page.pageSize"
              :page-sizes="[10, 20, 30, 40]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="page.total"
              @size-change="loadData"
              @current-change="loadData"
          />
        </template>
      </el-table>
    </div>
    <el-dialog draggable :title="`${updateAccount.type}账号信息`" v-model="updateAccount.show">
<!--      <Update v-model="updateAccount" />-->
      <Update v-if="updateAccount.type==='修改'" :account="updateAccount.data" @close="updateAccount.show=false;loadData()" />
      <Insert v-if="updateAccount.type==='新增'" :account="updateAccount.data" @close="updateAccount.show=false;loadData()" />
    </el-dialog>
  </div>
</template>

<script setup>
import Update from '@views/account/Update.vue'
import * as accountApi from '@api/account.api.js'
import {Search} from '@element-plus/icons-vue'
import {ref, computed, reactive, watch} from 'vue'
import {deleteByIds} from "@api/account.api.js";
import Insert from '@views/account/Insert.vue'
import {ElMessage} from "element-plus";
//查询条件
const search = ref({
  username: '',
  status:'',
  //查询范围
  createTime:[]
})
const page = ref({
  page: 1,
  pageSize: 10,
  total:0
})
const accounts = ref([])
const loading = ref(false)
const updateAccount = ref({
  type:'',
  show:false,
  data:{}
})
const tableData = ref()
//没有查询按钮，直接监听search变化
watch(search,loadData,{deep:true})
//加载数据
function loadData(){
  //发送请求   两个对象合并成一个对象
  loading.value=true
  accountApi.findAll({
    ...search.value,
    ...page.value
  }).then(rs=>{
    //data里面的list作为数据传过来
    accounts.value=rs.data.list
    accounts.value.map(account=>account.statusLoading=false)
    console.log(accounts.value)
    page.value = rs.data.pageInfo
  }).finally(()=>loading.value=false)
}
function changeStatus(account){
  account.statusLoading=true
  accountApi.updateStatus(account.id,account.status)
      .then(rs=>{
        if (rs.code!==200){
          account.status=account.status==0?1:0
        }
  }).catch(()=>{
    account.statusLoading=false
  }).finally(()=>{
    //最后都要改回来
    account.statusLoading=false
  })
}
function deleteAccount(account){
  accountApi.deleteByIds([account.id])
      .then(rs=>{
        if (rs.code===200){
        //  没必要再次请求数据,过滤掉ids
          accounts.value= accounts.value.filter(item=>item.id!==account.id)
        }
      })
}
function batchDelete(){
  //获得选中的行
  const selectionRows = tableData.value.getSelectionRows();
  if (selectionRows.length===0){
    ElMessage.warning('请选择要删除的数据')
    return
  }
  //得到id
  let ids =selectionRows.map(item=>item.id)
  accountApi.deleteByIds(ids)
      .then(res=>{
        if (res.code===200){
          ElMessage.success('删除成功')
          accounts.value= accounts.value.filter(item=>!ids.includes(item.id))
        }
      })
}
loadData()
</script>

<style scoped>








</style>