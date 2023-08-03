package com.cqupt.knowtolearn.controller;

import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.model.dto.req.OrgReq;
import com.cqupt.knowtolearn.model.dto.res.CosRes;
import com.cqupt.knowtolearn.model.po.user.Org;
import com.cqupt.knowtolearn.model.vo.OrgHomeVO;
import com.cqupt.knowtolearn.service.system.impl.CosService;
import com.cqupt.knowtolearn.service.user.IOrgService;
import com.cqupt.knowtolearn.utils.UserHolder;
import com.qcloud.cos.http.HttpMethodName;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Ray
 * @date 2023/7/30 15:29
 * @description
 */
@RestController
@RequestMapping()
public class OrgController {

    @Resource
    private IOrgService orgService;

    @Resource
    private CosService cosService;

    @PostMapping("/user/org/apply")
    public Result applyOrg(HttpServletRequest request, @RequestBody OrgReq req) {
        boolean isSuccess = orgService.apply(UserHolder.getUser(), req);
        return isSuccess ? Result.success("申请机构成功,请等待管理员审核",true) : Result.fail("申请机构失败");
    }

    @PostMapping("/user/org/submit/materials")
    public Result getHomeCourse(HttpServletRequest request, @RequestBody Map<String,String> req) {
        String suffix = req.get("suffix");
        CosRes materialSignature = cosService.getOrgMaterialSignature(HttpMethodName.PUT, suffix);
        return Result.success("获取COS签名URL成功",materialSignature);
    }

    @GetMapping("/org/info")
    public Result getOrgInfo(HttpServletRequest request) {
        Map<String,Object> map = orgService.findOwnOrg(UserHolder.getUser());
        return Result.success("获取机构信息成功",map);
    }

    @PostMapping("/org/info/update/orgName")
    public Result updateOrgName(HttpServletRequest request,@RequestBody Map<String,String> req) {
        String orgName = req.get("orgName");
        orgService.updateOrgName(UserHolder.getUser(),orgName);
        return Result.success(200,"修改机构名称成功");
    }

    @PostMapping("/org/info/update/orgIntro")
    public Result updateOrgIntro(HttpServletRequest request,@RequestBody Map<String,String> req) {
        String introduction = req.get("orgIntro");
        orgService.updateOrgIntro(UserHolder.getUser(), introduction);
        return Result.success(200,"修改机构介绍成功");
    }

    @GetMapping("/user/org/{orgId}/home")
    public Result getOrgInfo(HttpServletRequest request,@PathVariable("orgId") Integer orgId) {
        OrgHomeVO data = orgService.getOrgHome(orgId);
        return Result.success("获取机构主页信息成功",data);
    }
}
