package org.wso2.carbon.identity.rest.api.user.association.v1;
//comment
public class ApiException extends Exception{
	private int code;
	public ApiException (int code, String msg) {
		super(msg);
		this.code = code;
	}
}
