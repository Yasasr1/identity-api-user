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
package org.wso2.carbon.identity.rest.api.user.association.v1.util;

import org.wso2.carbon.identity.user.account.association.UserAccountConnector;
import org.wso2.carbon.identity.user.profile.mgt.association.federation.FederatedAssociationManager;
import org.wso2.carbon.user.core.service.RealmService;

/**
 * Service holder class for User Associations.
 */
public class UserAssociationServiceHolder {

    private static UserAccountConnector userAccountConnector;
    private static FederatedAssociationManager federatedAssociationManager;
    private static RealmService realmService;

    public static FederatedAssociationManager getFederatedAssociationManager() {

        return federatedAssociationManager;
    }

    public static void setFederatedAssociationManager(FederatedAssociationManager federatedAssociationManager) {

        UserAssociationServiceHolder.federatedAssociationManager = federatedAssociationManager;
    }

    public static UserAccountConnector getUserAccountConnector() {

        return userAccountConnector;
    }

    public static void setUserAccountConnector(UserAccountConnector userAccountConnector) {

        UserAssociationServiceHolder.userAccountConnector = userAccountConnector;
    }

    public static RealmService getRealmService() {

        return realmService;
    }

    public static void setRealmService(RealmService realmService) {

        UserAssociationServiceHolder.realmService = realmService;
    }
}
