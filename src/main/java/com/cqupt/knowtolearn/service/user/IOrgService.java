package com.cqupt.knowtolearn.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqupt.knowtolearn.model.dto.req.OrgReq;
import com.cqupt.knowtolearn.model.po.user.Org;
import com.cqupt.knowtolearn.model.vo.OrgVO;
import org.aspectj.weaver.ast.Or;

import java.util.List;
import java.util.Map;

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

    Map<String,Object> findOwnOrg(Integer userId);

    void updateOrgName(Integer userId, String orgName);

    void updateOrgIntro(Integer userId, String introduction);
}
