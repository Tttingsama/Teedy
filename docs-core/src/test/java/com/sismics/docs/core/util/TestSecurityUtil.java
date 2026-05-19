package com.sismics.docs.core.util;

import com.sismics.docs.BaseTransactionalTest;
import com.sismics.docs.core.constant.AclTargetType;
import com.sismics.docs.core.dao.GroupDao;
import com.sismics.docs.core.dao.UserDao;
import com.sismics.docs.core.model.jpa.Group;
import com.sismics.docs.core.model.jpa.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TestSecurityUtil extends BaseTransactionalTest {

    @Test
    public void getTargetIdFromNameUserTest() throws Exception {
        User user = createUser("securityUtilTest");
        
        String targetId = SecurityUtil.getTargetIdFromName("securityUtilTest", AclTargetType.USER);
        
        Assert.assertNotNull(targetId);
        Assert.assertEquals(user.getId(), targetId);
    }

    @Test
    public void getTargetIdFromNameGroupTest() throws Exception {
        GroupDao groupDao = new GroupDao();
        Group group = new Group();
        group.setId(UUID.randomUUID().toString());
        group.setName("testGroup");
        groupDao.create(group, "admin");
        
        String targetId = SecurityUtil.getTargetIdFromName("testGroup", AclTargetType.GROUP);
        
        Assert.assertNotNull(targetId);
        Assert.assertEquals(group.getId(), targetId);
    }

    @Test
    public void getTargetIdFromNameNotFoundTest() {
        String targetId = SecurityUtil.getTargetIdFromName("nonExistent", AclTargetType.USER);
        Assert.assertNull(targetId);
        
        targetId = SecurityUtil.getTargetIdFromName("nonExistent", AclTargetType.GROUP);
        Assert.assertNull(targetId);
    }

    @Test
    public void skipAclCheckAdminTest() {
        List<String> targetIds = Arrays.asList("admin", "user1");
        boolean result = SecurityUtil.skipAclCheck(targetIds);
        Assert.assertTrue(result);
    }

    @Test
    public void skipAclCheckAdministratorsTest() {
        List<String> targetIds = Arrays.asList("user1", "administrators");
        boolean result = SecurityUtil.skipAclCheck(targetIds);
        Assert.assertTrue(result);
    }

    @Test
    public void skipAclCheckNormalUserTest() {
        List<String> targetIds = Arrays.asList("user1", "user2");
        boolean result = SecurityUtil.skipAclCheck(targetIds);
        Assert.assertFalse(result);
    }

    @Test
    public void skipAclCheckEmptyTest() {
        List<String> targetIds = Arrays.asList();
        boolean result = SecurityUtil.skipAclCheck(targetIds);
        Assert.assertFalse(result);
    }
}