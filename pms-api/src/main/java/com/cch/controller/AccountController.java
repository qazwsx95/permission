package com.cch.controller;

import cn.dev33.satoken.annotation.SaCheckDisable;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.cch.constants.RoleConstant;
import com.cch.model.bean.Account;
import com.cch.model.vo.AccountVO;
import com.cch.model.vo.HttpResult;
import com.cch.service.AccountService;
import com.github.pagehelper.PageInfo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {
    //    什么权限或者角色可以访问
    @GetMapping
    @SaCheckDisable("account:list")
    @SaCheckPermission(value = "account:list",orRole = RoleConstant.ADMINISTRATOR)
    public HttpResult search(AccountVO accountVO,
                             @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "0") int pageSize){
        PageInfo<Account> pageInfo = accountService.findAll(accountVO, page, pageSize);
        Map<String,Object> pageInfoMap = new HashMap<String,Object>();
        pageInfoMap.put("total", pageInfo.getTotal());
        pageInfoMap.put("page", page);
        pageInfoMap.put("pageSize", pageSize);
        List<Account> list = pageInfo.getList();
//        去除密码等铭感信息
        list.forEach(account -> account.setPassword(null));
        return HttpResult.ok().put("list", list).put("pageInfo",pageInfoMap);
    }
    @Resource
    private AccountService accountService;
    /*
        模块名：功能
        account：login
     */
//      GET   /account  {username:string,createTime:Array,status:string,page:number,pageSize:number} 查询条件
//      POST  /account/change  data{id,status}
//      POST /account   data{id,password}

//      PUT /account    data{id,username,password}
    @PutMapping
//    前端传递的是data,数据在请求体里面
    /**
     * 新增账号
     */
    public HttpResult insert(@RequestBody @Validated(AccountVO.InsertGroup.class) AccountVO avo){
        accountService.insert(avo);
        return HttpResult.ok().put("id",avo.getId());
    }
//      DELETE /account/delete  params {Array}
    @DeleteMapping
    public HttpResult deleteByIds(int[] ids){
        accountService.deleteByIds(ids);
        return HttpResult.ok();
    }

    /**
     * 修改用户信息
     * @param accountVO 用户信息
     * @return
     */
    @PostMapping
    public HttpResult update(@Validated(AccountVO.UpdateGroup.class) @RequestBody AccountVO accountVO){
        accountService.updateById(accountVO);
        return HttpResult.ok();
    }
    /**
     * 修改用户信息
     * @param accountVO 用户信息
     * @return
     */
    @PostMapping("/change")
    public HttpResult updateStatus(@Validated(AccountVO.StatusGroup.class) @RequestBody AccountVO accountVO){
        accountService.updateById(accountVO);
        if (accountVO.getStatus()==1){
            StpUtil.disable(accountVO.getId(),"login",-1);
        }else {
            StpUtil.untieDisable(accountVO.getId(),"login");
        }
        return HttpResult.ok();
    }


















}
