package com.sismics.docs.core.util;

import com.sismics.docs.BaseTransactionalTest;
import com.sismics.docs.core.constant.ConfigType;
import com.sismics.docs.core.dao.ConfigDao;
import org.junit.Assert;
import org.junit.Test;

import java.util.ResourceBundle;

public class TestConfigUtil extends BaseTransactionalTest {

    @Test
    public void getConfigBundleTest() {
        ResourceBundle bundle = ConfigUtil.getConfigBundle();
        Assert.assertNotNull(bundle);
    }

    @Test
    public void getConfigStringValueTest() throws Exception {
        ConfigDao configDao = new ConfigDao();
        configDao.update(ConfigType.THEME, "dark");
        
        String value = ConfigUtil.getConfigStringValue(ConfigType.THEME);
        Assert.assertEquals("dark", value);
    }

    @Test
    public void getConfigIntegerValueTest() throws Exception {
        ConfigDao configDao = new ConfigDao();
        configDao.update(ConfigType.SMTP_PORT, "587");
        
        int value = ConfigUtil.getConfigIntegerValue(ConfigType.SMTP_PORT);
        Assert.assertEquals(587, value);
    }

    @Test
    public void getConfigLongValueTest() throws Exception {
        ConfigDao configDao = new ConfigDao();
        configDao.update(ConfigType.SMTP_PORT, "587");
        
        long value = ConfigUtil.getConfigLongValue(ConfigType.SMTP_PORT);
        Assert.assertEquals(587L, value);
    }

    @Test
    public void getConfigBooleanValueTest() throws Exception {
        ConfigDao configDao = new ConfigDao();
        configDao.update(ConfigType.GUEST_LOGIN, "true");
        
        boolean value = ConfigUtil.getConfigBooleanValue(ConfigType.GUEST_LOGIN);
        Assert.assertTrue(value);
        
        configDao.update(ConfigType.GUEST_LOGIN, "false");
        value = ConfigUtil.getConfigBooleanValue(ConfigType.GUEST_LOGIN);
        Assert.assertFalse(value);
    }
}