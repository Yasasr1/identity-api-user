package org.wso2.carbon.identity.rest.api.user.association.v1.dto;


import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.NotNull;





@ApiModel(description = "")
public class FederatedAssociationUserRequestDTO  {
  
  
  
  private String username = null;
  
  
  private String password = null;
  
  
  private String subjectToken = null;

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("username")
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("subjectToken")
  public String getSubjectToken() {
    return subjectToken;
  }
  public void setSubjectToken(String subjectToken) {
    this.subjectToken = subjectToken;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class FederatedAssociationUserRequestDTO {\n");
    
    sb.append("  username: ").append(username).append("\n");
    sb.append("  password: ").append(password).append("\n");
    sb.append("  subjectToken: ").append(subjectToken).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
