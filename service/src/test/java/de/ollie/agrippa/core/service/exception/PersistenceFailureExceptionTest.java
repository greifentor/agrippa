package de.ollie.agrippa.core.service.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.ollie.agrippa.core.model.localization.LocalizationSO;
import de.ollie.agrippa.core.service.exception.PersistenceFailureException.PersistenceFailureValidationFailure;
import de.ollie.agrippa.core.service.exception.PersistenceFailureException.Reason;
import de.ollie.agrippa.core.service.localization.ResourceManager;

@ExtendWith(MockitoExtension.class)
class PersistenceFailureExceptionTest {

	private static final String ATTRIBUTE_NAME_0 = "attributeName0";
	private static final String ATTRIBUTE_NAME_1 = "attributeName1";
	private static final String ATTRIBUTE_NAMES = ATTRIBUTE_NAME_0 + ", " + ATTRIBUTE_NAME_1;
	private static final String CLASS_NAME = "className";
    private static final String GENERAL = "version {id} problem {className}";
    private static final long ID = 42;
	private static final LocalizationSO LOCALIZATION = LocalizationSO.DE;
	private static final Reason REASON = Reason.UNIQUE;
	private static final String NO_ERRORS_FOUND = "no errors found";
	private static final String NOT_BLANK_VIOLATION = "not blank {id} violation {className}";
    private static final String UNIQUE_VIOLATION = "unique {id} violation {className}";
    private static final String VERSION_VIOLATION = "version {id} violation";

	@Mock
	private ResourceManager resourceManager;

	@Nested
	class TestsOfConstructor_ViolationFailure_Reason_String_Strings {

        private PersistenceFailureValidationFailure createValidationFailure() {
            return new PersistenceFailureValidationFailure(REASON, CLASS_NAME, ATTRIBUTE_NAME_0, ATTRIBUTE_NAME_1);
		}

		@Test
		void setsTheAttributeNamesCorrectly() {
			assertEquals(List.of(ATTRIBUTE_NAME_0, ATTRIBUTE_NAME_1), createValidationFailure().getAttributeNames());
		}

		@Test
		void setsTheClassNameCorrectly() {
			assertEquals(CLASS_NAME, createValidationFailure().getClassName());
		}

		@Test
		void setsTheReasonCorrectly() {
			assertEquals(REASON, createValidationFailure().getReason());
		}

	}

	@Nested
	class TestsOfMethod_getLocalizedMessage_ResourceManager {

		@Test
		void returnsANotErrorsFoundMessage_passingAnExceptionWithNoValidationFailures() {
			// Prepare
			PersistenceFailureException underTest = new PersistenceFailureException(ID, List.of());
            String id = "PersistenceFailureException." + ServiceException.NO_ERROR + ".label";
			when(resourceManager.getLocalizedString(id, LOCALIZATION)).thenReturn(NO_ERRORS_FOUND);
			// Run & Check
			assertEquals(NO_ERRORS_FOUND, underTest.getLocalizedMessage(resourceManager, LOCALIZATION));
		}

		@Test
		void returnsACorrectMessage_passingAnExceptionWithNotNullConstraintViolation() {
			PersistenceFailureException underTest =
					new PersistenceFailureException(
							ID,
							List
									.of(
                                            new PersistenceFailureValidationFailure(
													Reason.NOT_NULL,
													CLASS_NAME,
													ATTRIBUTE_NAME_0,
													ATTRIBUTE_NAME_1)));
			String id = "PersistenceFailureException." + Reason.NOT_NULL + ".label";
			String resource = "{id} - {className} > {attributeNames}";
			when(resourceManager.getLocalizedString(id, LOCALIZATION)).thenReturn(resource);
			assertEquals(
					resource
                            .replace("{id}", "" + ID)
							.replace("{className}", CLASS_NAME)
							.replace("{attributeNames}", ATTRIBUTE_NAMES),
					underTest.getLocalizedMessage(resourceManager, LOCALIZATION));
		}

		@Test
		void returnsACorrectMessage_passingAnExceptionWithNotBlankConstraintViolation() {
			PersistenceFailureException underTest =
					new PersistenceFailureException(
							ID,
                            List.of(new PersistenceFailureValidationFailure(
                                    Reason.NOT_BLANK, CLASS_NAME, ATTRIBUTE_NAME_0)));
			String id = "PersistenceFailureException." + Reason.NOT_BLANK + ".label";
			when(resourceManager.getLocalizedString(id, LOCALIZATION)).thenReturn(NOT_BLANK_VIOLATION);
			assertEquals(
					NOT_BLANK_VIOLATION
                            .replace("{id}", "" + ID)
							.replace("{className}", CLASS_NAME)
							.replace("{attributeNames}", ATTRIBUTE_NAME_0),
					underTest.getLocalizedMessage(resourceManager, LOCALIZATION));
		}

		@Test
		void returnsACorrectMessage_passingAnExceptionWithUniqueConstraintViolation() {
			PersistenceFailureException underTest =
					new PersistenceFailureException(
							ID,
							List
									.of(
                                            new PersistenceFailureValidationFailure(
													Reason.UNIQUE,
													CLASS_NAME,
													ATTRIBUTE_NAME_1,
													ATTRIBUTE_NAME_0)));
			String id = "PersistenceFailureException." + Reason.UNIQUE + ".label";
			when(resourceManager.getLocalizedString(id, LOCALIZATION)).thenReturn(UNIQUE_VIOLATION);
			assertEquals(
					UNIQUE_VIOLATION
                            .replace("{id}", "" + ID)
							.replace("{className}", CLASS_NAME)
							.replace("{attributeNames}", ATTRIBUTE_NAMES),
					underTest.getLocalizedMessage(resourceManager, LOCALIZATION));
		}

		@Test
		void returnsACorrectMessage_passingAnExceptionWithVersionViolation() {
			PersistenceFailureException underTest =
                    new PersistenceFailureException(
                            ID, List.of(new PersistenceFailureValidationFailure(Reason.VERSION, CLASS_NAME)));
			String id = "PersistenceFailureException." + Reason.VERSION + ".label";
			when(resourceManager.getLocalizedString(id, LOCALIZATION)).thenReturn(VERSION_VIOLATION);
			assertEquals(
                    VERSION_VIOLATION.replace("{className}", CLASS_NAME).replace("{id}", "" + ID),
					underTest.getLocalizedMessage(resourceManager, LOCALIZATION));
		}

		@Test
		void returnsACorrectMessage_passingAnExceptionWithGeneralProblem() {
			PersistenceFailureException underTest =
                    new PersistenceFailureException(
                            ID, List.of(new PersistenceFailureValidationFailure(Reason.GENERAL, CLASS_NAME)));
			String id = "PersistenceFailureException." + Reason.GENERAL + ".label";
			when(resourceManager.getLocalizedString(id, LOCALIZATION)).thenReturn(GENERAL);
			assertEquals(
                    GENERAL.replace("{className}", CLASS_NAME).replace("{id}", "" + ID),
					underTest.getLocalizedMessage(resourceManager, LOCALIZATION));
		}

	}

}
