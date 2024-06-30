package de.ollie.agrippa.core.service.impl;

import java.util.UUID;

import javax.inject.Named;

import com.auth0.jwt.interfaces.DecodedJWT;

import de.ollie.agrippa.core.model.AuthorizationUser;
import de.ollie.agrippa.core.service.AuthorizationUserService;

import lombok.RequiredArgsConstructor;

/**
 * Implementation for the AuthorizationUserService interface.
 * 
 * FEEL FREE TO OVERRIDE !!!
 */
@Named
@RequiredArgsConstructor
public class AuthorizationUserServiceImpl implements AuthorizationUserService {

//	private final UserService userService;

	@Override
	public AuthorizationUser findByGlobalIdOrCreate(DecodedJWT decodedJWT) {
//		return userService
//				.findByGlobalId(getGlobalIdFromClaimString(decodedJWT))
//				.orElseGet(() -> createUser(decodedJWT));
		return null;
	}

//	private UUID getGlobalIdFromClaimString(DecodedJWT decodedJWT) {
//		String globalId = getClaimAsString(decodedJWT, JWTService.CLAIM_NAME_USER_GLOBAL_ID);
//		return globalId;
//		return (globalId != null) && !globalId.isEmpty() ? UUID.fromString(globalId) : null;
//	}

//	private String getClaimAsString(DecodedJWT decodedJWT, String claimIdentifier) {
//		String globalId = getClaimAsString(decodedJWT, JWTService.CLAIM_NAME_USER_GLOBAL_ID);
//		return globalId;
//		return decodedJWT.getClaims().get(claimIdentifier).asString();
//	}

//	private User createUser(DecodedJWT decodedJWT) {
//		return userService
//				.update(
//						userService
//								.create(
//										new User()
//												.setGlobalId(getGlobalIdFromClaimString(decodedJWT))
//												.setName(getClaimAsString(decodedJWT, JWTService.CLAIM_NAME_USER_NAME))
//												.setToken(
//														getClaimAsString(
//																decodedJWT,
//																JWTService.CLAIM_NAME_USER_TOKEN))));
//	}

}
