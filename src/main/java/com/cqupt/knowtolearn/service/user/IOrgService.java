package com.cqupt.knowtolearn.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqupt.knowtolearn.model.dto.req.OrgReq;
import com.cqupt.knowtolearn.model.po.user.Org;
import com.cqupt.knowtolearn.model.vo.OrgVO;

import java.util.List;

/**
* @author Ray
* @date 2023-07-26
* @description  服务接口
*/
public interface IOrgService extends IService<Org> {

    boolean apply(Integer userId, OrgReq req);

    List<OrgVO> getPendingOrgList();

    void checkPass(Integer orgId);

    void checkRefuse(Integer orgId);
}
