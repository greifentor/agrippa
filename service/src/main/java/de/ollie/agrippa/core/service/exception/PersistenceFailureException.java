package de.ollie.agrippa.core.service.exception;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.ToString;

/**
 * An exception for persistence misbehavior.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public class PersistenceFailureException extends ServiceException {

	public enum Reason {
		GENERAL,
		NOT_BLANK,
		NOT_NULL,
		UNIQUE,
		VERSION;
	}

	@EqualsAndHashCode(callSuper = true)
	@Getter
	@ToString
	public static class ValidationFailure extends ServiceException.ValidationFailure<Reason> {

		private final List<String> attributeNames;

		public ValidationFailure(Reason reason, String className, String... attributeNames) {
			super(reason);
			this.attributeNames = Arrays.asList(attributeNames);
			getProperties().put("className", className);
			getProperties()
					.put(
							"attributeNames",
							Arrays
									.asList(attributeNames)
									.stream()
									.sorted()
									.reduce((s0, s1) -> s0 + ", " + s1)
									.orElse("-"));
		}

		public String getClassName() {
			return getProperties().get("className");
		}

	}

	private final List<ValidationFailure> validationFailures;

	public PersistenceFailureException(String id, List<ValidationFailure> validationFailures) {
		super(Map.of("id", id));
		this.validationFailures = validationFailures;
	}

	@Override
	public List<ServiceException.ValidationFailure<? extends Enum<?>>> getValidationFailures() {
		return validationFailures.stream().map(vf -> vf).collect(Collectors.toList());
	}

}