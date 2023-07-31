package com.cqupt.knowtolearn.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.knowtolearn.model.po.user.Org;
import com.cqupt.knowtolearn.model.vo.OrgVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author Ray
 * @date 2023-07-26
 * @description  dao 接口
 */
@Mapper
public interface IOrgDao extends BaseMapper<Org> {

    List<OrgVO> selectPendingOrgList();
}
