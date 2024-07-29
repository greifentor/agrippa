package de.ollie.agrippa.persistence.converter;

import javax.inject.Named;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import de.ollie.agrippa.core.model.PageParameters;

import lombok.Generated;

/**
 * A converter to create a Pageable from a PageParameters object.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
public class PageParametersToPageableConverter {

	public Pageable convert(PageParameters pageParameters) {
		if (pageParameters == null) {
			return null;
		}
		if (pageParameters.getSort() != null) {
			return PageRequest
					.of(
							pageParameters.getPageNumber(),
							pageParameters.getEntriesPerPage(),
							getDirection(pageParameters.getSort().getDirection()),
							pageParameters.getSort().getFieldNames());
		}
		return PageRequest.of(pageParameters.getPageNumber(), pageParameters.getEntriesPerPage());
	}

	private Direction getDirection(PageParameters.Direction direction) {
		return direction == PageParameters.Direction.ASC ? Direction.ASC : Direction.DESC;
	}

}