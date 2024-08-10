package de.ollie.agrippa.core.service.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.ollie.agrippa.core.model.localization.LocalizationSO;
import de.ollie.agrippa.core.service.localization.ResourceManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public abstract class ServiceException extends RuntimeException {

    protected static final String NO_ERROR = "NO_ERROR";

    @Getter
    @RequiredArgsConstructor
    protected static class ValidationFailure<T extends Enum<?>> {

        private final T reason;

        private Map<String, String> properties = new HashMap<>();

    }

    @Getter(AccessLevel.PROTECTED)
    private Map<String, String> properties = new HashMap<>();

    protected abstract List<ValidationFailure<? extends Enum<?>>> getValidationFailures();

    protected ServiceException(Map<String, String> passedProperties) {
        passedProperties.forEach((k, v) -> properties.put(k, v));
    }

    public String getLocalizedMessage(ResourceManager resourceManager, LocalizationSO localization) {
        if (getValidationFailures().isEmpty()) {
            return resourceManager.getLocalizedString(getClass().getSimpleName() + "." + NO_ERROR + ".label",
                    localization);
        }
        return getValidationFailures().stream()
                .map(vf -> toLocalizedMessage(vf, resourceManager, localization))
                .reduce((s0, s1) -> s0 + "\n" + s1)
                .orElse("-");
    }

    private String toLocalizedMessage(ValidationFailure<? extends Enum<?>> vf, ResourceManager resourceManager,
            LocalizationSO localization) {
        String s = resourceManager.getLocalizedString(
                getClass().getSimpleName() + "." + vf.getReason().name() + ".label",
                LocalizationSO.DE);
        for (String k : vf.getProperties().keySet()) {
            s = s.replace("{" + k + "}", vf.getProperties().get(k));
        }
        for (String k : getProperties().keySet()) {
            s = s.replace("{" + k + "}", getProperties().get(k));
        }
        return s;
    }
}
