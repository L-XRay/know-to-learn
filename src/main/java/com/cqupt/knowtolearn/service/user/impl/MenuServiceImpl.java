package com.cqupt.knowtolearn.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqupt.knowtolearn.dao.user.IMenuDao;
import com.cqupt.knowtolearn.model.po.user.Menu;
import com.cqupt.knowtolearn.service.user.IMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author Ray
* @date 2023-07-26
* @description  服务实现
*/
@Slf4j
@Service
public class MenuServiceImpl extends ServiceImpl<IMenuDao, Menu> implements IMenuService {

}
