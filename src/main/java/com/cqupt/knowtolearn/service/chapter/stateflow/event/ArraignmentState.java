package com.cqupt.knowtolearn.service.chapter.stateflow.event;

import com.cqupt.knowtolearn.common.Constants;
import com.cqupt.knowtolearn.common.Result;
import com.cqupt.knowtolearn.model.po.chapter.CourseDetails;
import com.cqupt.knowtolearn.model.po.course.CourseBase;
import com.cqupt.knowtolearn.model.po.system.StationMessage;
import com.cqupt.knowtolearn.model.po.user.User;
import com.cqupt.knowtolearn.service.chapter.stateflow.AbstractState;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Ray
 * @date 2023/7/29 15:51
 * @description 提审状态
 */
@Component
public class ArraignmentState extends AbstractState {

    @Override
    public Result editing(Integer mediaId, Enum<Constants.MediaState> currentState) {
        return Result.fail("待审核状态不可编辑");
    }

    @Override
    public Result arraignment(Integer mediaId, Enum<Constants.MediaState> currentState) {
        return Result.fail("待审核状态不可重复提审");
    }

    @Override
    public Result checkRevoke(Integer mediaId, Enum<Constants.MediaState> currentState) {
        boolean isSuccess = courseDetailsService.alterStatus(mediaId, currentState, Constants.MediaState.EDIT);
        return isSuccess ? Result.success(200, "媒资撤审完成") : Result.fail("媒资状态变更失败");
    }

    @Override
    public Result checkPass(Integer mediaId, Enum<Constants.MediaState> currentState) {
        boolean isSuccess = courseDetailsService.alterStatus(mediaId, currentState, Constants.MediaState.PASS);
        if (isSuccess) {
            CourseDetails courseDetails = courseDetailsService.getById(mediaId);
            CourseBase courseBase = courseBaseService.selectOneById(courseDetails.getCourseId());
            User user = userService.findOrgUser(courseBase.getOrgId());
            StationMessage stationMessage = new StationMessage();
            stationMessage.setUserId(user.getId());
            stationMessage.setTitle("课程章节视频审核通过");
            stationMessage.setContent("课程["+courseBase.getName()+"]的章节视频"+"["+courseDetails.getChapterName()+"]"+"已审核通过，请及时发布。");
            stationMessage.setStatus(Constants.StationMessageState.NO_READ.getCode());
            stationMessage.setCreateTime(LocalDateTime.now());
            boolean insert = stationMessageService.save(stationMessage);
            if (!insert) {
                throw new RuntimeException("站内信发送失败");
            }
            return Result.success(200, "媒资审核通过完成");
        }
        return Result.fail("媒资状态变更失败");
    }

    @Override
    public Result checkRefuse(Integer mediaId, Enum<Constants.MediaState> currentState) {
        boolean isSuccess = courseDetailsService.alterStatus(mediaId, currentState, Constants.MediaState.REFUSE);
        if (isSuccess) {
            CourseDetails courseDetails = courseDetailsService.getById(mediaId);
            CourseBase courseBase = courseBaseService.selectOneById(courseDetails.getCourseId());
            User user = userService.findOrgUser(courseBase.getOrgId());
            StationMessage stationMessage = new StationMessage();
            stationMessage.setUserId(user.getId());
            stationMessage.setTitle("课程章节视频审核拒绝");
            stationMessage.setContent("课程["+courseBase.getName()+"]的章节视频"+"["+courseDetails.getChapterName()+"]"+"已审核拒绝，请及时修改。");
            stationMessage.setStatus(Constants.StationMessageState.NO_READ.getCode());
            stationMessage.setCreateTime(LocalDateTime.now());
            boolean insert = stationMessageService.save(stationMessage);
            if (!insert) {
                throw new RuntimeException("站内信发送失败");
            }
            return Result.success(200, "媒资审核拒绝完成");
        }
        return Result.fail("媒资状态变更失败");
    }

    @Override
    public Result publish(Integer mediaId, Enum<Constants.MediaState> currentState) {
        return Result.fail("待审核媒资不可发布");
    }

    @Override
    public Result publishRevoke(Integer mediaId, Enum<Constants.MediaState> currentState) {
        return Result.fail("待审核媒资不可取消发布");
    }
}
