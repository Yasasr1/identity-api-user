package org.wso2.carbon.identity.rest.api.user.association.v1;

import org.wso2.carbon.identity.rest.api.user.association.v1.dto.*;
import org.wso2.carbon.identity.rest.api.user.association.v1.UserIdApiService;
import org.wso2.carbon.identity.rest.api.user.association.v1.factories.UserIdApiServiceFactory;

import io.swagger.annotations.ApiParam;

import org.wso2.carbon.identity.rest.api.user.association.v1.dto.ErrorDTO;
import org.wso2.carbon.identity.rest.api.user.association.v1.dto.UserDTO;
import org.wso2.carbon.identity.rest.api.user.association.v1.dto.FederatedAssociationDTO;
import org.wso2.carbon.identity.rest.api.user.association.v1.dto.FederatedAssociationUserRequestDTO;

import java.util.List;

import java.io.InputStream;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import javax.ws.rs.core.Response;
import javax.ws.rs.*;

@Path("/{user-id}")
@Consumes({ "application/json" })
@Produces({ "application/json" })
@io.swagger.annotations.Api(value = "/{user-id}", description = "the {user-id} API")
public class UserIdApi  {

   private final UserIdApiService delegate = UserIdApiServiceFactory.getUserIdApi();

    @DELETE
    @Path("/associations")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Delete user's all user associations", notes = "This API is used to delete all associations of the user.<br>\n<b>Permission required:</b>\n    * /permission/admin/manage/identity/user/association/delete\n<b>Scope required:</b>\n    * internal_user_association_delete\n", response = void.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 204, message = "No content"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized request"),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Resource Forbidden"),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response userIdAssociationsDelete(@ApiParam(value = "",required=true ) @PathParam("user-id")  String userId)
    {
    return delegate.userIdAssociationsDelete(userId);
    }
    @GET
    @Path("/associations")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Get user's associations", notes = "This API is used to retrieve the associations of the user.<br>\n<b>Permission required:</b>\n    * /permission/admin/manage/identity/user/association/view\n<b>Scope required:</b>\n    * internal_user_association_view\n", response = UserDTO.class, responseContainer = "List")
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successful operation"),
        
        @io.swagger.annotations.ApiResponse(code = 204, message = "No content"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized request"),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Resource Forbidden"),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response userIdAssociationsGet(@ApiParam(value = "user id",required=true ) @PathParam("user-id")  String userId)
    {
    return delegate.userIdAssociationsGet(userId);
    }
    @DELETE
    @Path("/federated-associations")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Delete user's all user federated associations", notes = "This API is used to delete all federated associations of the user.<br>\n<b>Permission required:</b>\n    * /permission/admin/manage/identity/user/association/delete\n<b>Scope required:</b>\n    * internal_user_association_delete\n", response = void.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 204, message = "No content"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized request"),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Resource Forbidden"),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response userIdFederatedAssociationsDelete(@ApiParam(value = "",required=true ) @PathParam("user-id")  String userId)
    {
    return delegate.userIdFederatedAssociationsDelete(userId);
    }
    @GET
    @Path("/federated-associations")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Get user's federated associations", notes = "This API is used to retrieve the federated associations of the user.<br>\n<b>Permission required:</b>\n    * /permission/admin/manage/identity/user/association/view\n<b>Scope required:</b>\n    * internal_user_association_view\n", response = FederatedAssociationDTO.class, responseContainer = "List")
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successful operation"),
        
        @io.swagger.annotations.ApiResponse(code = 204, message = "No content"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized request"),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Resource Forbidden"),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response userIdFederatedAssociationsGet(@ApiParam(value = "user id",required=true ) @PathParam("user-id")  String userId)
    {
    return delegate.userIdFederatedAssociationsGet(userId);
    }
    @DELETE
    @Path("/federated-associations/{id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Delete user's federated association", notes = "This API is used to delete a federated association of the user.<br>\n<b>Permission required:</b>\n    * /permission/admin/manage/identity/user/association/delete\n<b>Scope required:</b>\n    * internal_user_association_delete\n", response = void.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 204, message = "No content"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized request"),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Resource Forbidden"),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response userIdFederatedAssociationsIdDelete(@ApiParam(value = "",required=true ) @PathParam("user-id")  String userId,
    @ApiParam(value = "",required=true ) @PathParam("id")  String id)
    {
    return delegate.userIdFederatedAssociationsIdDelete(userId,id);
    }
    @POST
    @Path("/federated-associations")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Add a federated association for a user", notes = "This API is used to add a federated association for the user.<br>\n<b>Permission required:</b>\n    * /permission/admin/manage/identity/user/association/update\n<b>Scope required:</b>\n    * internal_user_association_update\n", response = FederatedAssociationDTO.class, responseContainer = "List")
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 201, message = "Successful operation"),
        
        @io.swagger.annotations.ApiResponse(code = 204, message = "No content"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized request"),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Resource Forbidden"),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found"),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error") })

    public Response userIdFederatedAssociationsPost(@ApiParam(value = "user id",required=true ) @PathParam("user-id")  String userId,
    @ApiParam(value = "User details to be associated." ,required=true ) FederatedAssociationUserRequestDTO federatedAssociation)
    {
    return delegate.userIdFederatedAssociationsPost(userId,federatedAssociation);
    }
}

