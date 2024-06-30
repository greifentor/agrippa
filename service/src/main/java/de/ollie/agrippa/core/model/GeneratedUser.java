package de.ollie.agrippa.core.model;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

/**
 * A model for users.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Accessors(chain = true)
@Data
@Generated
public abstract class GeneratedUser<T extends User> {

	public static final String ID = "ID";
	public static final String DEFAULTUSER = "DEFAULTUSER";
	public static final String GLOBALID = "GLOBALID";
	public static final String NAME = "NAME";
	public static final String TOKEN = "TOKEN";

	private long id;
	private boolean defaultUser = false;
	private String globalId;
	private String name;
	private String token;

	protected abstract T self();

	public T setId(long id) {
		this.id = id;
		return self();
	}

	public T setDefaultUser(boolean defaultUser) {
		this.defaultUser = defaultUser;
		return self();
	}

	public T setGlobalId(String globalId) {
		this.globalId = globalId;
		return self();
	}

	public T setName(String name) {
		this.name = name;
		return self();
	}

	public T setToken(String token) {
		this.token = token;
		return self();
	}

}