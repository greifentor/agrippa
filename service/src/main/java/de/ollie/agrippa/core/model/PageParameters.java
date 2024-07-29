package de.ollie.agrippa.core.model;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

/**
 * A page parameters to limit page access.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Accessors(chain = true)
@Data
@Generated
public class PageParameters {

	public enum Direction {
		ASC,
		DESC;
	}

	@Accessors(chain = true)
	@Data
	public static class Sort {
		private Direction direction;
		private String[] fieldNames;
	}

	private int pageNumber;
	private int entriesPerPage;
	private Sort sort;

}