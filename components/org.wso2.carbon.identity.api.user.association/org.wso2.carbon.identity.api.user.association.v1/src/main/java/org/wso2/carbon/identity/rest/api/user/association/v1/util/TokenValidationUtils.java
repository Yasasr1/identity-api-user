package org.wso2.carbon.identity.rest.api.user.association.v1.util;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.identity.application.common.model.IdentityProvider;
import org.wso2.carbon.identity.application.common.model.IdentityProviderProperty;
import org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration;
import org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception;
import org.wso2.carbon.identity.oauth2.validators.jwt.JWKSBasedJWTValidator;
import org.wso2.carbon.identity.user.profile.mgt.association.federation.exception.FederatedAssociationManagerClientException;
import org.wso2.carbon.identity.user.profile.mgt.association.federation.exception.FederatedAssociationManagerException;

import static org.wso2.carbon.identity.rest.api.user.association.v1.AssociationEndpointConstants.JWKS_URI;

/**
 * This class contains utility methods used for token validation.
 */
public class TokenValidationUtils {

    /**
     * Method to validate the signature of the provided JWT token.
     *
     * @param signedJWT    signed JWT whose signature is to be verified
     * @param idp          Identity provider who issued the signed JWT
     * @return true if the signature is valid, false otherwise
     * @throws FederatedAssociationManagerException if an error occurs while validating the signature
     */
    public static boolean validateTokenSignature(SignedJWT signedJWT, IdentityProvider idp)
            throws FederatedAssociationManagerException {

        String jwksUri = getJWKSUri(idp);
        if (jwksUri == null) {
            throw new FederatedAssociationManagerException("JWKS URI is not configured for the Identity Provider: " +
                    idp.getIdentityProviderName());
        }

        //TODO - Validate using configured certificate of idp
        JWKSBasedJWTValidator jwksBasedJWTValidator = new JWKSBasedJWTValidator();
        try {
            return jwksBasedJWTValidator.validateSignature(signedJWT.getParsedString(), jwksUri,
                    signedJWT.getHeader().getAlgorithm().getName(), null);
        } catch (IdentityOAuth2Exception e) {
            throw new FederatedAssociationManagerException(e.getMessage());
        }
    }

    public static boolean isTokenExpired(long expirationTimeMilliseconds) {

        long currentTimeMilliseconds = System.currentTimeMillis();
        long timingSkewMilliseconds = OAuthServerConfiguration.getInstance().getTimeStampSkewInSeconds();
        return currentTimeMilliseconds + timingSkewMilliseconds > expirationTimeMilliseconds;
    }

    private static String getJWKSUri(IdentityProvider idp) {

        String jwksUri = null;

        IdentityProviderProperty[] identityProviderProperties = idp.getIdpProperties();
        if (!ArrayUtils.isEmpty(identityProviderProperties)) {
            for (IdentityProviderProperty identityProviderProperty : identityProviderProperties) {
                if (StringUtils.equals(identityProviderProperty.getName(), JWKS_URI)) {
                    jwksUri = identityProviderProperty.getValue();
                    break;
                }
            }
        }

        return jwksUri;
    }

    public static void validateTokenClaims(JWTClaimsSet claims) throws FederatedAssociationManagerException {

        if (StringUtils.isBlank(claims.getIssuer())) {
            throw new FederatedAssociationManagerClientException("issuer is missing in the given token");
        }

        if (StringUtils.isBlank(claims.getSubject())) {
            throw new FederatedAssociationManagerClientException("subject is missing in the given token");
        }
    }

}
