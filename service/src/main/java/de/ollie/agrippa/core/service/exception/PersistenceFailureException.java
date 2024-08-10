package de.ollie.agrippa.core.service.exception;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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
    public static class PersistenceFailureValidationFailure extends ValidationFailure<Reason> {

        private final List<String> attributeNames;

        public PersistenceFailureValidationFailure(Reason reason, String className, String... attributeNames) {
            super(reason);
            this.attributeNames = Arrays.asList(attributeNames);
            getProperties().put("className", className);
            getProperties().put("attributeNames",
                    Arrays.asList(attributeNames).stream().sorted().reduce((s0, s1) -> s0 + ", " + s1).orElse("-"));
		}

        public String getClassName() {
            return getProperties().get("className");
        }

	}

    private final List<PersistenceFailureValidationFailure> validationFailures;

    public PersistenceFailureException(Long id, List<PersistenceFailureValidationFailure> validationFailures) {
        super(Map.of("id", "" + id));
        this.validationFailures = validationFailures;
    }

    @Override
    public List<ValidationFailure<? extends Enum<?>>> getValidationFailures() {
        return validationFailures.stream()
                .map(vf -> (PersistenceFailureValidationFailure) vf)
                .collect(Collectors.toList());
    }

}
