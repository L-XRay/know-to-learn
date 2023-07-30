package com.cqupt.knowtolearn.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqupt.knowtolearn.dao.user.IOrgDao;
import com.cqupt.knowtolearn.model.dto.req.OrgReq;
import com.cqupt.knowtolearn.model.po.user.Org;
import com.cqupt.knowtolearn.service.user.IOrgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
* @author Ray
* @date 2023-07-26
* @description  服务实现
*/
@Slf4j
@Service
public class OrgServiceImpl extends ServiceImpl<IOrgDao, Org> implements IOrgService {

    @Override
    public boolean apply(Integer userId, OrgReq req) {
        Org org = new Org();
        org.setName(req.getOrgName());
        org.setIntro(req.getIntroduction());
        org.setMaterials(req.getMaterials());
        org.setStatus(0);
        org.setCreateTime(LocalDateTime.now());
        org.setUserId(userId);
        return save(org);
    }
}
