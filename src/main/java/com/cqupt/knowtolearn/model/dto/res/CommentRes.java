package com.cqupt.knowtolearn.model.dto.res;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ray
 * @date 2023/8/1 17:53
 * @description
 */
public class CommentRes implements Serializable {

    private Integer id;

    private Integer pid;

    private String content;

    private Integer userId;

    private String userPicture;

    private List<CommentRes> commentDetailsListChild;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public List<CommentRes> getCommentDetailsListChild() {
        return commentDetailsListChild;
    }

    public void setCommentDetailsListChild(List<CommentRes> commentDetailsListChild) {
        this.commentDetailsListChild = commentDetailsListChild;
    }
}


