package com.cqupt.knowtolearn.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.knowtolearn.model.po.user.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Ray
 * @date 2023-07-26
 * @description  dao 接口
 */
@Mapper
public interface IUserDao extends BaseMapper<User> {

}
