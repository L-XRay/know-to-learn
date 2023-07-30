package com.cqupt.knowtolearn.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqupt.knowtolearn.model.dto.req.OrgReq;
import com.cqupt.knowtolearn.model.po.user.Org;

/**
* @author Ray
* @date 2023-07-26
* @description  服务接口
*/
public interface IOrgService extends IService<Org> {

    boolean apply(Integer userId, OrgReq req);
}
