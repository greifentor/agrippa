package de.ollie.agrippa.core.service.exception;

import java.util.ArrayList;
import java.util.List;

import de.ollie.agrippa.core.model.localization.LocalizationSO;
import de.ollie.agrippa.core.service.localization.ResourceManager;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
public class PersistenceFailureException extends RuntimeException {

	static final String NO_ERROR_SUFFIX = "NO_ERROR_FOUND";

	static final String GENERAL_RESOURCE_ID = "persistence.failure.general.label";
	static final String NO_ERRORS_FOUND_RESOURCE_ID = "persistence.failure.noErrorsFound.label";
	static final String NOT_BLANK_CONSTRAINT_VIOLATION_RESOURCE_ID =
			"persistence.failure.notBlankConstraintViolation.label";
	static final String NOT_NULL_CONSTRAINT_VIOLATION_RESOURCE_ID =
			"persistence.failure.notNullConstraintViolation.label";
	static final String UNIQUE_CONSTRAINT_VIOLATION_RESOURCE_ID = "persistence.failure.uniqueConstraintViolation.label";
	static final String VERSION_VIOLATION_RESOURCE_ID = "persistence.failure.versionViolation.label";

	public enum Reason {
		GENERAL,
		NOT_BLANK,
		NOT_NULL,
		UNIQUE,
		VERSION;
	}

	@EqualsAndHashCode
	@Getter
	@ToString
	public static class ValidationFailure {

		public ValidationFailure(Reason reason) {
			this.reason = reason;
		}

		public ValidationFailure(Reason reason, String className, String... attributeNames) {
			this(reason);
			this.className = className;
			for (String an : attributeNames) {
				this.attributeNames.add(an);
			}
		}

		private Reason reason;
		private String className;
		private List<String> attributeNames = new ArrayList<>();

	}

	private final String id;
	private final List<ValidationFailure> validationFailures;

	public String getLocalizedMessage(ResourceManager resourceManager, LocalizationSO localization) {
		String resourceIdPrefix = getClass().getSimpleName() + ".";
		if (validationFailures.isEmpty()) {
			return resourceManager.getLocalizedString(resourceIdPrefix + NO_ERROR_SUFFIX + ".label", localization);
		}
		return validationFailures
				.stream()
				.map(vf -> toLocalizedString(vf, resourceManager, localization))
				.reduce((s0, s1) -> s0 + "\n" + s1)
				.orElse("n/a");
	}

	private String toLocalizedString(ValidationFailure validationFailure, ResourceManager resourceManager,
			LocalizationSO localization) {
		String id = "PersistenceFailureException." + validationFailure.getReason().name() + ".label";
		return resourceManager
				.getLocalizedString(id, localization)
				.replace("{id}", "" + getId())
				.replace("{className}", validationFailure.getClassName())
				.replace(
						"{attributeNames}",
						validationFailure
								.getAttributeNames()
								.stream()
								.sorted((s0, s1) -> s0.compareTo(s1))
								.reduce((s0, s1) -> s0 + ", " + s1)
								.orElse("n/a"));
	}

}
