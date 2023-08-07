package com.cqupt.knowtolearn.controller;

import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.dao.chapter.ICourseDetailsDao;
import com.cqupt.knowtolearn.dao.course.ICourseBaseDao;
import com.cqupt.knowtolearn.exception.KnowException;
import com.cqupt.knowtolearn.model.po.course.CourseBase;
import com.cqupt.knowtolearn.model.vo.CourseDetailVO;
import com.cqupt.knowtolearn.model.vo.OrgVO;
import com.cqupt.knowtolearn.service.chapter.ICourseDetailsService;
import com.cqupt.knowtolearn.service.course.ICourseBaseService;
import com.cqupt.knowtolearn.service.user.IOrgService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Ray
 * @date 2023/7/27 19:48
 * @description
 */
@RestController
public class SystemController {

    @Resource
    private IOrgService orgService;

    @Resource
    private ICourseBaseService courseBaseService;

    @Resource
    private ICourseDetailsService courseDetailsService;

    private static final Map<String, String> nameMap = new HashMap<>();
    static {
        nameMap.put("xr","夏瑞");
        nameMap.put("ysq", "易诗淇");
        nameMap.put("ltt", "李恬恬");
        nameMap.put("zly", "周玲瑶");
        nameMap.put("ljd","赖建东");
        nameMap.put("zyq", "曾艳琦");
    }

    @GetMapping("/chief/get")
    public Result getChief(HttpServletRequest request) {
//        List<String> fileNames = null;
//        try {
//            // 获取 static 目录的绝对路径
//            String staticPath = this.getClass().getResource("/static").toURI().getPath();
//
//            // 通过静态资源目录的绝对路径构建 Path 对象
//            Path staticDir = Paths.get(staticPath);
//
//            // 获取 static 目录下的所有文件名
//            fileNames = Files.walk(staticDir)
//                    .filter(Files::isRegularFile)
//                    .map(Path::getFileName)
//                    .map(Path::toString)
//                    .collect(Collectors.toList());
//        } catch (URISyntaxException | IOException e) {
//            throw new KnowException("无法获取主创人员信息");
//        }

        List<Map<String,String>> list = new ArrayList<>();

//        // 获取完整的URL
//        String url = request.getRequestURL().toString();
//
//        // 获取URI部分
//        String uri = request.getRequestURI();
//
//        // 获取去掉URI部分的URL
//        // http://127.0.0.1:8090/chief/
//        String baseUrl = url.substring(0, url.indexOf(uri) + uri.length()-3);

        List<String> fileNames = Arrays.asList("xr.jpg", "ysq.jpg", "ltt.jpg", "zly.jpg", "ljd.jpg", "zyq.jpg");

        // 打印所有文件名
        for (String fileName : fileNames) {
            String prefix = fileName.split("\\.")[0];
            String name = nameMap.getOrDefault(prefix, "未知"); // 如果前缀没有对应的名字，则使用默认值
            Map<String, String> map = new HashMap<>();
            map.put("name", name);
            map.put("avatar", "http://162.14.74.132:8090/chief/" + fileName);
            list.add(map);
        }
        return Result.success("获取主创人员信息成功",list);
    }

    @GetMapping("/admin/pending/org")
    public Result getPendingOrgList(HttpServletRequest request) {
        List<OrgVO> pendingOrgList = orgService.getPendingOrgList();
        if (pendingOrgList==null || pendingOrgList.isEmpty()) {
            return Result.success(200,"不存在未处理的机构申请");
        }
        return Result.success("获取未处理的机构申请成功",pendingOrgList);
    }

    @GetMapping("/admin/list/course")
    public Result getCourseList(HttpServletRequest request) {
        List<CourseBase> list = courseBaseService.list();
        return Result.success("获取所有课程信息成功",list);
    }

    @PutMapping("/admin/checkPass/org/{orgId}")
    public Result checkPassOrg(HttpServletRequest request,@PathVariable("orgId") Integer orgId) {
        orgService.checkPass(orgId);
        return Result.success("审核通过机构申请成功",true);
    }

    @PutMapping("/admin/checkRefuse/org/{orgId}")
    public Result checkRefuseOrg(HttpServletRequest request,@PathVariable("orgId") Integer orgId) {
        orgService.checkRefuse(orgId);
        return Result.success("审核拒绝机构申请成功",true);
    }

    @GetMapping("/admin/pending/media")
    public Result getPendingMediaList(HttpServletRequest request) {
        List<CourseDetailVO> pendingMediaList = courseDetailsService.getPendingMediaList();
        if (pendingMediaList==null || pendingMediaList.isEmpty()) {
            return Result.success(200,"不存在未处理的机构申请");
        }
        return Result.success("获取未处理的机构申请成功",pendingMediaList);
    }
}
