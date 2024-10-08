package de.ollie.agrippa.persistence.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

/**
 * A DBO for notes.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Accessors(chain = true)
@Data
@Generated
@Entity(name = "Note")
@Table(name = "NOTE")
public class NoteDBO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private long id;
	@JoinColumn(name = "RELATED_TODO", referencedColumnName = "ID")
	@ManyToOne(fetch = FetchType.EAGER)
	private TodoDBO relatedTodo;
	@Column(name = "CREATION_DATE", nullable = false)
	private LocalDateTime creationDate;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "TITLE", nullable = false)
	private String title;
	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE", nullable = false)
	private NoteTypeDBO type;
	@Column(name = "URL")
	private String url;

}