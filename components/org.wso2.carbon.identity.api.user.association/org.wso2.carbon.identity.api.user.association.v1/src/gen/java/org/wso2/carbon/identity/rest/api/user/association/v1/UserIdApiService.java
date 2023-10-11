package org.wso2.carbon.identity.rest.api.user.association.v1;

import org.wso2.carbon.identity.rest.api.user.association.v1.*;
import org.wso2.carbon.identity.rest.api.user.association.v1.dto.*;

import org.wso2.carbon.identity.rest.api.user.association.v1.dto.ErrorDTO;
import org.wso2.carbon.identity.rest.api.user.association.v1.dto.UserDTO;
import org.wso2.carbon.identity.rest.api.user.association.v1.dto.FederatedAssociationDTO;
import org.wso2.carbon.identity.rest.api.user.association.v1.dto.FederatedAssociationUserRequestDTO;

import java.util.List;

import java.io.InputStream;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;

import javax.ws.rs.core.Response;

public abstract class UserIdApiService {
    public abstract Response userIdAssociationsDelete(String userId);
    public abstract Response userIdAssociationsGet(String userId);
    public abstract Response userIdFederatedAssociationsDelete(String userId);
    public abstract Response userIdFederatedAssociationsGet(String userId);
    public abstract Response userIdFederatedAssociationsIdDelete(String userId,String id);
    public abstract Response userIdFederatedAssociationsPost(String userId,FederatedAssociationUserRequestDTO federatedAssociation);
}

