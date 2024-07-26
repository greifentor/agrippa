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
 * A DBO for todos.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Accessors(chain = true)
@Data
@Generated
@Entity(name = "Todo")
@Table(name = "TODO")
public class TodoDBO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private long id;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "DUE_DATE")
	private LocalDateTime dueDate;
	@Enumerated(EnumType.STRING)
	@Column(name = "PRIORITY", nullable = false)
	private TodoPriorityDBO priority;
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false)
	private TodoStatusDBO status;
	@Column(name = "TITLE", nullable = false)
	private String title;

}