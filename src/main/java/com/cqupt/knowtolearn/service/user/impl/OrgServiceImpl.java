package com.cqupt.knowtolearn.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqupt.knowtolearn.common.Constants;
import com.cqupt.knowtolearn.dao.system.IStationMessageDao;
import com.cqupt.knowtolearn.dao.user.IOrgDao;
import com.cqupt.knowtolearn.dao.user.IUserDao;
import com.cqupt.knowtolearn.model.dto.req.OrgReq;
import com.cqupt.knowtolearn.model.po.system.StationMessage;
import com.cqupt.knowtolearn.model.po.user.Org;
import com.cqupt.knowtolearn.model.po.user.User;
import com.cqupt.knowtolearn.model.vo.OrgHomeVO;
import com.cqupt.knowtolearn.model.vo.OrgVO;
import com.cqupt.knowtolearn.model.vo.QueryOrgVO;
import com.cqupt.knowtolearn.service.system.IStationMessageService;
import com.cqupt.knowtolearn.service.user.IOrgService;
import com.cqupt.knowtolearn.utils.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
* @author Ray
* @date 2023-07-26
* @description  服务实现
*/
@Slf4j
@Service
public class OrgServiceImpl extends ServiceImpl<IOrgDao, Org> implements IOrgService {

    @Resource
    private IOrgDao orgDao;

    @Resource
    private IUserDao userDao;

    @Resource
    private IStationMessageDao stationMessageDao;

    @Resource
    private PasswordUtil passwordUtil;

    @Override
    public boolean apply(Integer userId, OrgReq req) {
        Org org = new Org();
        org.setName(req.getOrgName());
        org.setIntro(req.getIntroduction());
        org.setMaterials(req.getMaterials());
        org.setStatus(0);
        org.setCreateTime(LocalDateTime.now());
        org.setUserId(userId);
        return save(org);
    }

    @Override
    public List<OrgVO> getPendingOrgList() {
        return orgDao.selectPendingOrgList();
    }

    @Override
    @Transactional
    public void checkPass(Integer orgId) {
        Org org = orgDao.selectOne(new LambdaQueryWrapper<Org>().eq(Org::getId, orgId));
        org.setStatus(Constants.OrgState.PASS.getCode());
        int update = orgDao.updateById(org);
        if (update != 1) {
            throw new RuntimeException("机构状态未修改成功");
        }

        User user = new User();
        String username = generateUsername();
        user.setUsername(username);
        user.setNickname(org.getName());
        user.setCreateTime(LocalDateTime.now());
        user.setOrgId(orgId);
        user.setStatus(1);
        String password = passwordUtil.generateRandomPassword();
        String salt = passwordUtil.generateSalt();
        user.setPassword(passwordUtil.encryptPassword(password,salt));
        user.setSalt(salt);
        user.setRole("org");
        int userInsert = userDao.insert(user);
        if (userInsert!=1) {
            throw new RuntimeException("机构账户创建失败");
        }

        StationMessage stationMessage = new StationMessage();
        stationMessage.setUserId(org.getUserId());
        stationMessage.setTitle("机构申请已通过");
        stationMessage.setContent("您的机构申请已通过,已为您创建好账户。\n 用户名:" + username +"\n 密码:" + password + "\n 请妥善保管");
        stationMessage.setStatus(Constants.StationMessageState.NO_READ.getCode());
        stationMessage.setCreateTime(LocalDateTime.now());
        int insert = stationMessageDao.insert(stationMessage);
        if (insert!=1) {
            throw new RuntimeException("站内信发送失败");
        }
    }

    @Override
    @Transactional
    public void checkRefuse(Integer orgId) {
        Org org = orgDao.selectOne(new LambdaQueryWrapper<Org>().eq(Org::getId, orgId));
        org.setStatus(Constants.OrgState.REFUSE.getCode());
        int update = orgDao.updateById(org);
        if (update != 1) {
            throw new RuntimeException("机构状态未修改成功");
        }


        StationMessage stationMessage = new StationMessage();
        stationMessage.setUserId(org.getUserId());
        stationMessage.setTitle("机构申请已拒绝");
        stationMessage.setContent("您的机构申请已拒绝, 若有疑问请联系管理员");
        stationMessage.setStatus(Constants.StationMessageState.NO_READ.getCode());
        stationMessage.setCreateTime(LocalDateTime.now());
        int insert = stationMessageDao.insert(stationMessage);
        if (insert!=1) {
            throw new RuntimeException("站内信发送失败");
        }
    }

    private String generateUsername() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    @Override
    public Map<String, Object> findOwnOrg(Integer userId) {
        Org org = getOrg(userId);
        Map<String,Object> map = new HashMap<>();
        map.put("id",org.getId());
        map.put("name",org.getName());
        map.put("introduction",org.getIntro());
        map.put("materials",org.getMaterials());
        return map;
    }

    @Override
    public void updateOrgName(Integer userId, String orgName) {
        Org org = getOrg(userId);
        org.setName(orgName);
        int update = orgDao.updateById(org);
        if (update!=1) {
            throw new RuntimeException("修改机构名称失败");
        }
    }

    @Override
    public void updateOrgIntro(Integer userId, String introduction) {
        Org org = getOrg(userId);
        org.setIntro(introduction);
        int update = orgDao.updateById(org);
        if (update!=1) {
            throw new RuntimeException("修改机构介绍失败");
        }
    }

    @Override
    public OrgHomeVO getOrgHome(Integer orgId) {
        return orgDao.selectOrgCourse(orgId);
    }

    private Org getOrg(Integer userId) {
        User user = userDao.selectById(userId);
        Org org = orgDao.selectById(user.getOrgId());
        return org;
    }

    @Override
    public List<QueryOrgVO> selectOrgList(String key) {
        return orgDao.selectOrgList(key);
    }
}
