package de.ollie.agrippa.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

/**
 * A DBO for users.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Accessors(chain = true)
@Data
@Generated
@Entity(name = "User")
@Table(name = "USER")
public class UserDBO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private long id;
	@Column(name = "DEFAULT_USER", nullable = false)
	private boolean defaultUser;
	@Column(name = "GLOBAL_ID")
	private String globalId;
	@Column(name = "NAME", nullable = false)
	private String name;
	@Column(name = "TOKEN", nullable = false)
	private String token;

}