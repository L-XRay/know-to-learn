package com.cqupt.knowtolearn.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqupt.knowtolearn.model.po.system.StationMessage;
import com.cqupt.knowtolearn.model.po.user.Org;
import com.cqupt.knowtolearn.model.vo.StationMessageVO;

import java.util.List;

/**
 * @author Ray
 * @date 2023/7/31 10:11
 * @description
 */
public interface IStationMessageService extends IService<StationMessage> {

    List<StationMessageVO> listMessage(Integer userId);

    Long getNoReadCount(Integer userId);
}
