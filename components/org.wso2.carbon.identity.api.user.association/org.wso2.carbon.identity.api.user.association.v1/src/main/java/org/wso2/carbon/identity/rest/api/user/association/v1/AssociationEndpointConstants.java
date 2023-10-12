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
package org.wso2.carbon.identity.rest.api.user.association.v1;

/**
 * This class contains the constants related to associations APIs.
 */
public class AssociationEndpointConstants {

    public static final String ASSOCIATION_ERROR_PREFIX = "UAA-";
    public static final String V1_API_PATH_COMPONENT = "/v1";
    public static final String USER_ASSOCIATIONS_PATH_COMPONENT = "/%s/associations";
    public static final String FEDERATED_USER_ASSOCIATIONS_PATH_COMPONENT = "/%s/federated-associations";
    public static final String ME_CONTEXT = "me";
    public static final String ERROR_MSG_DELIMITER = "-";
    public static final String JWKS_URI = "jwksUri";

    /**
     * Enum for error messages.
     */
    public enum ErrorMessages {

        ERROR_CODE_PW_MANDATORY("8900", "Invalid Inputs", "Password is a missing in the request");

        private final String code;
        private final String message;
        private final String description;

        ErrorMessages(String code, String message, String description) {

            this.code = code;
            this.message = message;
            this.description = description;
        }

        public String getCode() {
            return ASSOCIATION_ERROR_PREFIX + code;
        }

        public String getMessage() {
            return message;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return getCode() + " | " + getMessage() + " | " + getDescription();
        }

    }
}
