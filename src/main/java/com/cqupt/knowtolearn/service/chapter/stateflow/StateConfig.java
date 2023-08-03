package com.cqupt.knowtolearn.service.chapter.stateflow;

import com.cqupt.knowtolearn.common.Constants;
import com.cqupt.knowtolearn.service.chapter.stateflow.event.ArraignmentState;
import com.cqupt.knowtolearn.service.chapter.stateflow.event.EditingState;
import com.cqupt.knowtolearn.service.chapter.stateflow.event.PassState;
import com.cqupt.knowtolearn.service.chapter.stateflow.event.PublishState;
import com.cqupt.knowtolearn.service.chapter.stateflow.event.RefuseState;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Ray
 * @date 2023/7/29 16:32
 * @description 状态变更配置
 */
public class StateConfig {

    @Resource
    private ArraignmentState arraignmentState;
    @Resource
    private EditingState editingState;
    @Resource
    private PassState passState;
    @Resource
    private RefuseState refuseState;
    @Resource
    private PublishState publishState;

    protected Map<Enum<Constants.MediaState>, AbstractState> stateGroup = new ConcurrentHashMap<>();

    @PostConstruct
    public void init(){
        stateGroup.put(Constants.MediaState.ARRAIGNMENT, arraignmentState);
        stateGroup.put(Constants.MediaState.PUBLISH,publishState);
        stateGroup.put(Constants.MediaState.EDIT, editingState);
        stateGroup.put(Constants.MediaState.PASS, passState);
        stateGroup.put(Constants.MediaState.REFUSE, refuseState);
    }

}
