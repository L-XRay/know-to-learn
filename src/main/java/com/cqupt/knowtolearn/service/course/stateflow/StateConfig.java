//package com.cqupt.knowtolearn.service.course.stateflow;
//
//import com.cqupt.knowtolearn.common.Constants;
//import com.cqupt.knowtolearn.service.course.stateflow.event.*;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @author Ray
// * @date 2023/7/29 16:32
// * @description 状态变更配置
// */
//public class StateConfig {
//
//    @Resource
//    private ArraignmentState arraignmentState;
//    @Resource
//    private EditingState editingState;
//    @Resource
//    private PassState passState;
//    @Resource
//    private RefuseState refuseState;
//    @Resource
//    private PublishState publishState;
//
//    protected Map<Enum<Constants.CourseState>,AbstractState> stateGroup = new ConcurrentHashMap<>();
//
//    @PostConstruct
//    public void init(){
//        stateGroup.put(Constants.CourseState.ARRAIGNMENT, arraignmentState);
//        stateGroup.put(Constants.CourseState.PUBLISH,publishState);
//        stateGroup.put(Constants.CourseState.EDIT, editingState);
//        stateGroup.put(Constants.CourseState.PASS, passState);
//        stateGroup.put(Constants.CourseState.REFUSE, refuseState);
//    }
//
//}
