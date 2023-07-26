package com.cqupt.knowtolearn.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqupt.knowtolearn.dao.user.IRoleDao;
import com.cqupt.knowtolearn.model.po.user.Role;
import com.cqupt.knowtolearn.service.user.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author Ray
* @date 2023-07-26
* @description  服务实现
*/
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<IRoleDao, Role> implements IRoleService {

}
