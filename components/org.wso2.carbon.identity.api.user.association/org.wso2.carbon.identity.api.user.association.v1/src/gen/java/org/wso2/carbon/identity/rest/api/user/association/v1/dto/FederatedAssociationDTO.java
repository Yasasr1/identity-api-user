package org.wso2.carbon.identity.rest.api.user.association.v1.dto;

import org.wso2.carbon.identity.rest.api.user.association.v1.dto.IdpDTO;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.NotNull;





@ApiModel(description = "")
public class FederatedAssociationDTO  {
  
  
  
  private String id = null;
  
  
  private IdpDTO idp = null;
  
  
  private String federatedUserId = null;

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("id")
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("idp")
  public IdpDTO getIdp() {
    return idp;
  }
  public void setIdp(IdpDTO idp) {
    this.idp = idp;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("federatedUserId")
  public String getFederatedUserId() {
    return federatedUserId;
  }
  public void setFederatedUserId(String federatedUserId) {
    this.federatedUserId = federatedUserId;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class FederatedAssociationDTO {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  idp: ").append(idp).append("\n");
    sb.append("  federatedUserId: ").append(federatedUserId).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
