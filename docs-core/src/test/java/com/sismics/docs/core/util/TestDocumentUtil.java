package com.sismics.docs.core.util;

import com.sismics.docs.BaseTransactionalTest;
import com.sismics.docs.core.constant.AclType;
import com.sismics.docs.core.constant.PermType;
import com.sismics.docs.core.dao.AclDao;
import com.sismics.docs.core.dao.dto.AclDto;
import com.sismics.docs.core.model.jpa.Document;
import com.sismics.docs.core.model.jpa.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class TestDocumentUtil extends BaseTransactionalTest {

    @Test
    public void createDocumentTest() throws Exception {
        User user = createUser("createDocumentTest");

        Document document = new Document();
        document.setUserId(user.getId());
        document.setTitle("Test Document");
        document.setDescription("Test Description");
        document.setLanguage("eng");
        document.setCreateDate(new Date());
        document.setUpdateDate(new Date());

        Document createdDocument = DocumentUtil.createDocument(document, user.getId());
        
        Assert.assertNotNull(createdDocument);
        Assert.assertEquals("Test Document", createdDocument.getTitle());
        
        AclDao aclDao = new AclDao();
        List<AclDto> acls = aclDao.getBySourceId(createdDocument.getId(), AclType.USER);
        
        Assert.assertEquals(2, acls.size());
        
        boolean hasReadAcl = false;
        boolean hasWriteAcl = false;
        for (AclDto acl : acls) {
            if (acl.getPerm() == PermType.READ && acl.getTargetId().equals(user.getId())) {
                hasReadAcl = true;
            }
            if (acl.getPerm() == PermType.WRITE && acl.getTargetId().equals(user.getId())) {
                hasWriteAcl = true;
            }
        }
        
        Assert.assertTrue("Read ACL should be created", hasReadAcl);
        Assert.assertTrue("Write ACL should be created", hasWriteAcl);
    }
}