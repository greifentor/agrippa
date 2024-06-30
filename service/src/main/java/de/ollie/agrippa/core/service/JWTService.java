package de.ollie.agrippa.core.service;

import java.time.LocalDateTime;

import de.ollie.agrippa.core.model.AuthorizationUser;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * An interface for the JWT service.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public interface JWTService {

	public static final String CLAIM_NAME_APPLICATION_NAME = "applicationName";
	public static final String CLAIM_NAME_APPLICATION_RIGHTS = "applicationRights";
	public static final String CLAIM_NAME_LOGIN_DATE = "loginDate";
	public static final String CLAIM_NAME_USER_GLOBAL_ID = "userGlobalId";
	public static final String CLAIM_NAME_USER_NAME = "userName";
	public static final String CLAIM_NAME_USER_TOKEN = "userToken";

	@Accessors(chain = true)
	@AllArgsConstructor
	@EqualsAndHashCode
	@Getter
	@Generated
	public static class AuthorizationData {

		private String applicationName;
		private LocalDateTime loginDateTime;
		private AuthorizationUser user;
		private String[] applicationRights;

	}

	AuthorizationData getAuthorizationData(String jwt);

	LocalDateTime getLoginDate(String jwt);

}