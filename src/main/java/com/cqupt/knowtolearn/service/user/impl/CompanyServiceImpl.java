package com.cqupt.knowtolearn.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqupt.knowtolearn.dao.user.ICompanyDao;
import com.cqupt.knowtolearn.model.po.user.Company;
import com.cqupt.knowtolearn.service.user.ICompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author Ray
* @date 2023-07-26
* @description  服务实现
*/
@Slf4j
@Service
public class CompanyServiceImpl extends ServiceImpl<ICompanyDao, Company> implements ICompanyService {

}
