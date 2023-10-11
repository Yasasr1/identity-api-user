package org.wso2.carbon.identity.rest.api.user.association.v1;

import org.wso2.carbon.identity.rest.api.user.association.v1.dto.*;
import org.wso2.carbon.identity.rest.api.user.association.v1.MeApiService;
import org.wso2.carbon.identity.rest.api.user.association.v1.factories.MeApiServiceFactory;

import io.swagger.annotations.ApiParam;

import org.wso2.carbon.identity.rest.api.user.association.v1.dto.ErrorDTO;
import org.wso2.carbon.identity.rest.api.user.association.v1.dto.UserDTO;
import org.wso2.carbon.identity.rest.api.user.association.v1.dto.AssociationUserRequestDTO;
import org.wso2.carbon.identity.rest.api.user.association.v1.dto.FederatedAssociationDTO;

import java.util.List;

import java.io.InputStream;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import javax.ws.rs.core.Response;
import javax.ws.rs.*;

@Path("/me")
@Consumes({ "application/json" })
@Produces({ "application/json" })
@io.swagger.annotations.Api(value = "/me", description = "the me API")
public class MeApi  {

   private final MeApiService delegate = MeApiServiceFactory.getMeApi();

    @DELETE
    @Path("/associations/{associated-user-id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Delete given user account from my user associations", notes = "This API is used to delete the given user account from the associations of the authenticated user.<br>\n<b>Permission required:</b>\n    * None\n<b>Scope required:</b>\n    * internal_login\n", response = void.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 204, message = "No content"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Resource Forbidden"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response meAssociationsAssociatedUserIdDelete(@ApiParam(value = "",required=true ) @PathParam("associated-user-id")  String associatedUserId)
    {
    return delegate.meAssociationsAssociatedUserIdDelete(associatedUserId);
    }
    @DELETE
    @Path("/associations")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Delete all my user associations", notes = "This API is used to delete all associations of the authenticated user.<br>\n<b>Permission required:</b>\n    * None\n<b>Scope required:</b>\n    * internal_login\n", response = void.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 204, message = "No content"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Resource Forbidden"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response meAssociationsDelete()
    {
    return delegate.meAssociationsDelete();
    }
    @GET
    @Path("/associations")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Retrive the associations of the authenticated user.", notes = "This API is used to retrieve the associations of the authenticated user.<br>\n<b>Permission required:</b>\n    * None\n<b>Scope required:</b>\n    * internal_login\n", response = UserDTO.class, responseContainer = "List")
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successful operation"),
        
        @io.swagger.annotations.ApiResponse(code = 204, message = "No content"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Resource Forbidden"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response meAssociationsGet()
    {
    return delegate.meAssociationsGet();
    }
    @POST
    @Path("/associations")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Associate a user to the authenticated user\n", notes = "This API is used to associate a user to the authenticated user. For example, if it is required to associate my\nuser account to the user account of 'john', this endpoint can be used. The userId and the password are required to associate the accounts.<br>\n<b>Permission required:</b>\n    * None\n<b>Scope required:</b>\n    * internal_login\n", response = void.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 201, message = "Successfully created"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Resource Forbidden"),
        
        @io.swagger.annotations.ApiResponse(code = 409, message = "Conflict"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response meAssociationsPost(@ApiParam(value = "User details to be associated. userId should be the fully qualified username of the user." ,required=true ) AssociationUserRequestDTO association)
    {
    return delegate.meAssociationsPost(association);
    }
    @DELETE
    @Path("/federated-associations")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Delete all my federated user associations", notes = "This API is used to delete all the federated associations of the authenticated user.<br>\n<b>Permission required:</b>\n    * None\n<b>Scope required:</b>\n    * internal_login\n", response = void.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 204, message = "No content"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Resource Forbidden"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response meFederatedAssociationsDelete()
    {
    return delegate.meFederatedAssociationsDelete();
    }
    @GET
    @Path("/federated-associations")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Retrive the federated associations of the authenticated user.", notes = "This API is used to retrieve the federated associations of the authenticated user.<br>\n<b>Permission required:</b>\n    * None\n<b>Scope required:</b>\n    * internal_login\n", response = FederatedAssociationDTO.class, responseContainer = "List")
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successful operation"),
        
        @io.swagger.annotations.ApiResponse(code = 204, message = "No content"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized"),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Resource Forbidden"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response meFederatedAssociationsGet()
    {
    return delegate.meFederatedAssociationsGet();
    }
    @DELETE
    @Path("/federated-associations/{id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Delete my federated association", notes = "This API is used to delete a federated association of the authenticated user.<br>\n<b>Permission required:</b>\n    * None\n<b>Scope required:</b>\n    * internal_login\n", response = void.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 204, message = "No content"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized request"),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Resource Forbidden"),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response meFederatedAssociationsIdDelete(@ApiParam(value = "",required=true ) @PathParam("id")  String id)
    {
    return delegate.meFederatedAssociationsIdDelete(id);
    }
}

