package com.cqupt.knowtolearn.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqupt.knowtolearn.dao.system.IStationMessageDao;
import com.cqupt.knowtolearn.dao.user.IOrgDao;
import com.cqupt.knowtolearn.model.po.system.StationMessage;
import com.cqupt.knowtolearn.model.po.user.Org;
import com.cqupt.knowtolearn.model.vo.StationMessageVO;
import com.cqupt.knowtolearn.service.system.IStationMessageService;
import com.cqupt.knowtolearn.service.user.IOrgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Ray
 * @date 2023/7/31 10:11
 * @description
 */
@Slf4j
@Service
public class StationMessageServiceImpl extends ServiceImpl<IStationMessageDao, StationMessage> implements IStationMessageService {

    @Resource
    private IStationMessageDao stationMessageDao;

    @Override
    @Transactional
    public List<StationMessageVO> listNoReadMessage(Integer userId) {
        List<StationMessageVO> data = stationMessageDao.selectNoReadMessage(userId);
        stationMessageDao.updateStationMessageRead(userId);
        return data;
    }
}
