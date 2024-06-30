package de.ollie.agrippa.core.service;

import com.auth0.jwt.interfaces.DecodedJWT;

import de.ollie.agrippa.core.model.AuthorizationUser;
import lombok.Generated;

/**
 * An interface for the authorization user service.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public interface AuthorizationUserService {

	AuthorizationUser findByGlobalIdOrCreate(DecodedJWT decodedJWT);

}