/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.carbon.identity.rest.api.user.association.v1.core;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.api.user.common.ContextLoader;
import org.wso2.carbon.identity.api.user.common.error.APIError;
import org.wso2.carbon.identity.api.user.common.error.ErrorResponse;
import org.wso2.carbon.identity.api.user.common.function.UserToUniqueId;
import org.wso2.carbon.identity.application.common.model.IdentityProvider;
import org.wso2.carbon.identity.application.common.model.User;
import org.wso2.carbon.identity.application.common.util.IdentityApplicationConstants;
import org.wso2.carbon.identity.core.util.IdentityTenantUtil;
import org.wso2.carbon.identity.rest.api.user.association.v1.dto.AssociationUserRequestDTO;
import org.wso2.carbon.identity.rest.api.user.association.v1.dto.FederatedAssociationDTO;
import org.wso2.carbon.identity.rest.api.user.association.v1.dto.FederatedAssociationUserRequestDTO;
import org.wso2.carbon.identity.rest.api.user.association.v1.dto.IdpDTO;
import org.wso2.carbon.identity.rest.api.user.association.v1.dto.UserDTO;
import org.wso2.carbon.identity.rest.api.user.association.v1.util.TokenValidationUtils;
import org.wso2.carbon.identity.rest.api.user.association.v1.util.UserAssociationServiceHolder;
import org.wso2.carbon.identity.user.account.association.dto.UserAccountAssociationDTO;
import org.wso2.carbon.identity.user.account.association.exception.UserAccountAssociationClientException;
import org.wso2.carbon.identity.user.account.association.exception.UserAccountAssociationException;
import org.wso2.carbon.identity.user.profile.mgt.association.federation.exception.FederatedAssociationManagerClientException;
import org.wso2.carbon.identity.user.profile.mgt.association.federation.exception.FederatedAssociationManagerException;
import org.wso2.carbon.identity.user.profile.mgt.association.federation.model.FederatedAssociation;
import org.wso2.carbon.idp.mgt.IdentityProviderManagementException;
import org.wso2.carbon.idp.mgt.IdentityProviderManager;
import org.wso2.carbon.user.api.UserStoreException;
import org.wso2.carbon.user.core.UserRealm;
import org.wso2.carbon.user.core.common.AbstractUserStoreManager;
import org.wso2.carbon.user.core.service.RealmService;
import org.wso2.carbon.user.core.util.UserCoreUtil;
import org.wso2.carbon.utils.multitenancy.MultitenantConstants;
import org.wso2.carbon.utils.multitenancy.MultitenantUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import static org.wso2.carbon.identity.api.user.common.Constants.ERROR_CODE_DELIMITER;
import static org.wso2.carbon.identity.rest.api.user.association.v1.AssociationEndpointConstants.ASSOCIATION_ERROR_PREFIX;
import static org.wso2.carbon.identity.rest.api.user.association.v1.AssociationEndpointConstants.ERROR_MSG_DELIMITER;
import static org.wso2.carbon.identity.rest.api.user.association.v1.AssociationEndpointConstants.ErrorMessages.ERROR_CODE_PW_MANDATORY;

/**
 * This service is used to execute the association related APIs through the UserAccountConnector OSGI service.
 */
public class UserAssociationService {

    private static final Log log = LogFactory.getLog(UserAssociationService.class);

    public List<UserDTO> getAssociationsOfUser(String userId) {

        try {
            UserAccountAssociationDTO[] accountAssociationsOfUser = UserAssociationServiceHolder
                    .getUserAccountConnector().getAccountAssociationsOfUser(userId);
            return getUserAssociationsDTOs(accountAssociationsOfUser);
        } catch (UserAccountAssociationException e) {
            throw handleUserAccountAssociationException(e, "Error while getting associations of user: " + userId);
        }
    }

    public List<FederatedAssociationDTO> getFederatedAssociationsOfUser(String userId) {

        try {
            FederatedAssociation[] federatedAccountAssociationsOfUser = UserAssociationServiceHolder
                    .getFederatedAssociationManager().getFederatedAssociationsOfUser(getUser(userId));
            return getFederatedAssociationDTOs(federatedAccountAssociationsOfUser);
        } catch (FederatedAssociationManagerException e) {
            throw handleFederatedAssociationManagerException(e, "Error while getting associations of user: " + userId);
        }
    }

    public void createUserAccountAssociation(AssociationUserRequestDTO associationUserRequestDTO) {

        try {
            if (associationUserRequestDTO.getPassword() == null) {
                throw new UserAccountAssociationClientException(ERROR_CODE_PW_MANDATORY.getCode(),
                        ERROR_CODE_PW_MANDATORY.getDescription());
            }
            UserAssociationServiceHolder.getUserAccountConnector()
                    .createUserAccountAssociation(associationUserRequestDTO.getUserId(),
                            associationUserRequestDTO.getPassword().toCharArray());
        } catch (UserAccountAssociationException e) {
            throw handleUserAccountAssociationException(e,
                    "Error while adding associations of user: " + associationUserRequestDTO.getUserId());
        }
    }

    public boolean switchLoggedInUser(String userName)  {

        try {
            return UserAssociationServiceHolder.getUserAccountConnector().switchLoggedInUser(userName);
        } catch (UserAccountAssociationException e) {
            throw handleUserAccountAssociationException(e, "Error while switching user: " + userName);
        }
    }

    public void deleteUserAccountAssociation(String userId) {

        try {
            UserAssociationServiceHolder.getUserAccountConnector().deleteUserAccountAssociation(userId);
        } catch (UserAccountAssociationException e) {
            throw handleUserAccountAssociationException(e, "Error while deleting user association: " + userId);
        }
    }

    public void deleteAssociatedUserAccount(String ownerUserId, String associatedUserId) {

        try {
            UserAssociationServiceHolder.getUserAccountConnector().deleteAssociatedUserAccount(ownerUserId,
                    associatedUserId);
        } catch (UserAccountAssociationException e) {
            throw handleUserAccountAssociationException(e, "Error while deleting user association of the user: "
                    + ownerUserId + ", with the user: " + associatedUserId);
        }
    }

    public void deleteFederatedUserAccountAssociation(String userId, String federatedAssociationId) {

        try {
            UserAssociationServiceHolder.getFederatedAssociationManager()
                    .deleteFederatedAssociation(getUser(userId), federatedAssociationId);
        } catch (FederatedAssociationManagerException e) {
            throw handleFederatedAssociationManagerException(e, "Error while deleting federated user association: "
                    + userId);
        }
    }

    public void deleteFederatedUserAccountAssociation(String userId) {

        try {
            UserAssociationServiceHolder.getFederatedAssociationManager().deleteFederatedAssociation(getUser(userId));
        } catch (FederatedAssociationManagerException e) {
            throw handleFederatedAssociationManagerException(e, "Error while deleting federated user association: "
                    + userId);
        }
    }

    public void createFederatedUserAccountAssociation(String userId, FederatedAssociationUserRequestDTO
            federatedAssociationUserRequestDTO) {

        String username = federatedAssociationUserRequestDTO.getUsername();
        String password = federatedAssociationUserRequestDTO.getPassword();
        String tenantDomain = ContextLoader.getTenantDomainFromContext();
        try {
            if (username == null) {
                throw new FederatedAssociationManagerClientException("Username is missing in the request");
            }

            if (password == null) {
                throw new FederatedAssociationManagerClientException("Password is missing in the request");
            }

            if (federatedAssociationUserRequestDTO.getSubjectToken() == null) {
                throw new FederatedAssociationManagerClientException("Subject token is missing in the request");
            }

            JWTClaimsSet jwtClaimsSet = validateSubjectToken(federatedAssociationUserRequestDTO.getSubjectToken(),
                    tenantDomain);

            String jwtIssuer = jwtClaimsSet.getIssuer();
            String subject = jwtClaimsSet.getSubject();
            // Map<String, Object> customClaims = new HashMap<>(jwtClaimsSet.getClaims());

            // use the username and password and validate the local user account
            if (!validateLocalUser(username, password)) {
                throw new FederatedAssociationManagerClientException("Local user validation failed");
            }

            org.wso2.carbon.user.core.common.User localUser = getLocalUser(username);
            if (localUser == null) {
                throw new FederatedAssociationManagerException("Could not resolve local user: " + username);
            }

            UserAssociationServiceHolder.getFederatedAssociationManager().createFederatedAssociationWithIdpResourceId(
                new User(localUser), getIDP(jwtIssuer, tenantDomain).getResourceId(), subject);


        } catch (FederatedAssociationManagerException e) {
            throw handleFederatedAssociationManagerException(e, "Error while adding association for user:" + userId);
        }


    }

    private JWTClaimsSet validateSubjectToken(String token, String tenantDomain) throws
            FederatedAssociationManagerException {

        SignedJWT signedJWT;
        JWTClaimsSet jwtClaimsSet;
        try {
            signedJWT = SignedJWT.parse(token);
            jwtClaimsSet = signedJWT.getJWTClaimsSet();
            if (jwtClaimsSet == null) {
                throw new FederatedAssociationManagerClientException("Claim values are empty in the given token");
            }

        } catch (ParseException e) {
            throw new FederatedAssociationManagerException(e.getMessage());
        }

        TokenValidationUtils.validateTokenClaims(jwtClaimsSet);
        IdentityProvider idp = getIDP(jwtClaimsSet.getIssuer(), tenantDomain);
//        if (!TokenValidationUtils.validateTokenSignature(signedJWT, idp)) {
//            throw new FederatedAssociationManagerClientException("Token signature validation failed");
//        }

        if (TokenValidationUtils.isTokenExpired(jwtClaimsSet.getExpirationTime().getTime())) {
            throw new FederatedAssociationManagerClientException("Token is expired");
        }

        return jwtClaimsSet;
    }

    private boolean validateLocalUser(String username, String password) throws FederatedAssociationManagerException {

        AbstractUserStoreManager userStoreManager = getUserStoreManager();
        try {
            return userStoreManager.authenticate(username, password);
        } catch (org.wso2.carbon.user.core.UserStoreException e) {
            throw new FederatedAssociationManagerException("Local user validation failed", e);
        }
    }

    private org.wso2.carbon.user.core.common.User getLocalUser(String username)
            throws FederatedAssociationManagerException {

        org.wso2.carbon.user.core.common.User localUser = null;
        AbstractUserStoreManager userStoreManager = getUserStoreManager();
        try {
            if (userStoreManager.isExistingUser(username)) {
                localUser = userStoreManager.getUser(null, username);
            }
        } catch (UserStoreException e) {
            throw new FederatedAssociationManagerException(
                    "Error while resolving local user for username: " + username, e);
        }
        return localUser;
    }

    private AbstractUserStoreManager getUserStoreManager()
            throws FederatedAssociationManagerException {

        RealmService realmService = UserAssociationServiceHolder.getRealmService();
        String tenantDomain = ContextLoader.getTenantDomainFromContext();
        AbstractUserStoreManager userStoreManager;

        if (StringUtils.isEmpty(tenantDomain)) {
            tenantDomain = MultitenantConstants.SUPER_TENANT_DOMAIN_NAME;
        }
        int tenantId = IdentityTenantUtil.getTenantId(tenantDomain);

        try {
            UserRealm realm = (UserRealm) realmService.getTenantUserRealm(tenantId);

            if (realm.getUserStoreManager().getSecondaryUserStoreManager() != null) {
                userStoreManager = (AbstractUserStoreManager) realm.getUserStoreManager()
                        .getSecondaryUserStoreManager();
            } else {
                userStoreManager = (AbstractUserStoreManager) realm.getUserStoreManager();
            }
        } catch (UserStoreException e) {
            throw new FederatedAssociationManagerException("Error while getting user store manager: " + e.getMessage());
        }
        return userStoreManager;
    }

    public static IdentityProvider getIDP(String jwtIssuer, String tenantDomain)
            throws FederatedAssociationManagerException {

        // check if a registered IDP exists for the given issuer name. If not the request fails
        IdentityProvider identityProvider;
        try {
            identityProvider =
                    IdentityProviderManager.getInstance().getIdPByMetadataProperty(IdentityApplicationConstants
                            .IDP_ISSUER_NAME, jwtIssuer, tenantDomain, true);
            if (identityProvider == null) {
                identityProvider = IdentityProviderManager.getInstance().getIdPByName(
                        jwtIssuer, tenantDomain, true);
            }

        } catch (IdentityProviderManagementException e) {
            throw new FederatedAssociationManagerException("Error while getting the Federated Identity Provider", e);
        }
        if (identityProvider == null) {
            throw new FederatedAssociationManagerClientException("No IDP found for the JWT with issuer name "
                    + ":" + " " + jwtIssuer);
        }
        if (!identityProvider.isEnable()) {
            throw new FederatedAssociationManagerClientException("IDP is not enabled for the JWT with issuer name "
                    + ":" + " " + jwtIssuer);
        }
        return identityProvider;
    }

    private List<UserDTO> getUserAssociationsDTOs(UserAccountAssociationDTO[] accountAssociationsOfUser) {

        List<UserDTO> userDTOList = new ArrayList<>();

        for (UserAccountAssociationDTO userAccountAssociationDTO : accountAssociationsOfUser) {
            userDTOList.add(getUserDTO(userAccountAssociationDTO));
        }
        return userDTOList;
    }

    private List<FederatedAssociationDTO> getFederatedAssociationDTOs(FederatedAssociation[]
                                                                              federatedAssociations) {

        List<FederatedAssociationDTO> federatedAssociationDTOs = new ArrayList<>();
        for (FederatedAssociation federatedAssociation : federatedAssociations) {
            federatedAssociationDTOs.add(getFederatedAssociationDTO(federatedAssociation));
        }
        return federatedAssociationDTOs;
    }

    private UserDTO getUserDTO(UserAccountAssociationDTO userAccountAssociationDTO) {

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(getUniqueUserId(userAccountAssociationDTO));
        userDTO.setUsername(userAccountAssociationDTO.getUsername());
        userDTO.setUserStoreDomain(userAccountAssociationDTO.getDomain());
        userDTO.setTenantDomain(userAccountAssociationDTO.getTenantDomain());

        userDTO.setFirstName(userAccountAssociationDTO.getFirstName());
        userDTO.setLastName(userAccountAssociationDTO.getLastName());
        userDTO.setEmail(userAccountAssociationDTO.getEmail());
        return userDTO;
    }

    private FederatedAssociationDTO getFederatedAssociationDTO(FederatedAssociation federatedAssociation) {

        FederatedAssociationDTO federatedAssociationDTO = new FederatedAssociationDTO();
        federatedAssociationDTO.setId(federatedAssociation.getId());
        federatedAssociationDTO.setFederatedUserId(federatedAssociation.getFederatedUserId());

        IdpDTO idpDTO = new IdpDTO();
        idpDTO.setId(federatedAssociation.getIdp().getId());
        idpDTO.setName(federatedAssociation.getIdp().getName());
        if (federatedAssociation.getIdp().getDisplayName() == null) {
            idpDTO.setDisplayName(StringUtils.EMPTY);
        } else {
            idpDTO.setDisplayName(federatedAssociation.getIdp().getDisplayName());
        }
        if (federatedAssociation.getIdp().getImageUrl() == null) {
            idpDTO.setImageUrl(StringUtils.EMPTY);
        } else {
            idpDTO.setImageUrl(federatedAssociation.getIdp().getImageUrl());
        }

        federatedAssociationDTO.setIdp(idpDTO);
        return federatedAssociationDTO;
    }

    private APIError handleUserAccountAssociationException(UserAccountAssociationException e, String message) {

        Response.Status status;
        ErrorResponse errorResponse;

        if (e instanceof UserAccountAssociationClientException) {
            errorResponse = new ErrorResponse.Builder()
                    .withCode(e.getErrorCode())
                    .withMessage(message)
                    .build(log, e.getMessage());
            if (e.getErrorCode() != null) {
                String errorCode = e.getErrorCode();
                errorCode = errorCode.contains(ERROR_CODE_DELIMITER) ? errorCode : ASSOCIATION_ERROR_PREFIX
                        + errorCode;
                errorResponse.setCode(errorCode);
            }
            handleErrorDescription(e, errorResponse);
            status = Response.Status.BAD_REQUEST;
        } else {
            errorResponse = new ErrorResponse.Builder()
                    .withCode(e.getErrorCode())
                    .withMessage(message)
                    .build(log, e, e.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }
        return new APIError(status, errorResponse);
    }

    private APIError handleFederatedAssociationManagerException(FederatedAssociationManagerException e,
                                                                String message) {

        ErrorResponse errorResponse = new ErrorResponse.Builder()
                .withCode(e.getErrorCode())
                .withMessage(message)
                .build(log, e, e.getMessage());

        Response.Status status;

        if (e instanceof FederatedAssociationManagerClientException) {
            if (e.getErrorCode() != null) {
                String errorCode = e.getErrorCode();
                errorCode = errorCode.contains(ERROR_CODE_DELIMITER) ? errorCode : ASSOCIATION_ERROR_PREFIX
                        + errorCode;
                errorResponse.setCode(errorCode);
            }
            handleErrorDescription(e, errorResponse);
            status = Response.Status.BAD_REQUEST;
        } else {
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }
        return new APIError(status, errorResponse);
    }

    private void handleErrorDescription(UserAccountAssociationException e, ErrorResponse errorResponse) {

        handleCommonErrorDescription(e, errorResponse);
    }

    private void handleErrorDescription(FederatedAssociationManagerException e, ErrorResponse errorResponse) {

        handleCommonErrorDescription(e, errorResponse);
    }

    private void handleCommonErrorDescription(Exception e, ErrorResponse errorResponse) {

        if (e.getMessage() != null && e.getMessage().contains(ERROR_MSG_DELIMITER)) {
            String[] splittedMessage = e.getMessage().split(ERROR_MSG_DELIMITER);
            if (splittedMessage.length == 2) {
                errorResponse.setDescription(splittedMessage[1].trim());
            } else {
                errorResponse.setDescription(e.getMessage());
            }
        } else if (!e.getMessage().contains(ERROR_MSG_DELIMITER)) {
            errorResponse.setDescription(e.getMessage());
        }
    }

    private String getUniqueUserId(UserAccountAssociationDTO userAccountAssociationDTO) {

        User user = new User();
        user.setUserName(userAccountAssociationDTO.getUsername());
        user.setUserStoreDomain(userAccountAssociationDTO.getDomain());
        user.setTenantDomain(userAccountAssociationDTO.getTenantDomain());
        return new UserToUniqueId().apply(UserAssociationServiceHolder.getRealmService(), user);
    }

    private User getUser(String userId) {

        User user = new User();
        user.setTenantDomain(MultitenantUtils.getTenantDomain(userId));
        user.setUserStoreDomain(UserCoreUtil.extractDomainFromName(userId));
        user.setUserName(MultitenantUtils.getTenantAwareUsername(UserCoreUtil.removeDomainFromName(userId)));
        return user;
    }
}
