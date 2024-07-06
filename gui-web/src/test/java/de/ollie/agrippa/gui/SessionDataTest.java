package de.ollie.agrippa.gui;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import de.ollie.agrippa.gui.SessionData.ReturnUrlData;

@ExtendWith(MockitoExtension.class)
class SessionDataTest {

	private static final String URL = "url";
	private static final Map<String, List<String>> MAP = Map.of("IDENT", List.of("AString"));

	@InjectMocks
	private SessionData unitUnderTest;

	@Nested
	class TestsOfMethod_addReturnUrl {

		@Test
		void throwsAnException_passingANullValueAsUrl() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.addReturnUrl(null));
		}

		@Test
		void throwsAnException_passingAnEmptyStringAsUrl() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.addReturnUrl(""));
		}

		@Test
		void storesAReturnUrlDataObject_withNotParameters_passingAnUrlOnly() {
			// Prepare
			ReturnUrlData expected = new ReturnUrlData(URL, Map.of());
			// Run
			unitUnderTest.addReturnUrl(URL);
			// Check
			assertEquals(expected, unitUnderTest.getReturnUrl().get());
		}

	}

	@Nested
	class TestsOfMethod_addReturnUrl_String_MapOfStringList {

		@Test
		void throwsAnException_passingANullValueAsUrl() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.addReturnUrl(null, Map.of()));
		}

		@Test
		void throwsAnException_passingAnEmptyStringAsUrl() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.addReturnUrl("", Map.of()));
		}

		@Test
		void throwsAnException_passingANullValueAsParametersMap() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.addReturnUrl(URL, null));
		}

		@Test
		void storesAReturnUrlDataObject_withNotParameters_passingAnUrlOnly() {
			// Prepare
			ReturnUrlData expected = new ReturnUrlData(URL, Map.of());
			// Run
			unitUnderTest.addReturnUrl(URL, Map.of());
			// Check
			assertEquals(expected, unitUnderTest.getReturnUrl().get());
		}

		@Test
		void storesAReturnUrlDataObject_withNotParameters_passingUrlAndParameters() {
			// Prepare
			ReturnUrlData expected = new ReturnUrlData(URL, MAP);
			// Run
			unitUnderTest.addReturnUrl(URL, MAP);
			// Check
			assertEquals(expected, unitUnderTest.getReturnUrl().get());
		}

	}

	@Nested
	class TestOfMethod_getReturnUrl {

		@Test
		void returnsAnEmptyOptional_whenNoReturnUrlHasBeenAddedToTheSessionData() {
			assertTrue(unitUnderTest.getReturnUrl().isEmpty());
		}

		@Test
		void returnsTopOfStackOnceOnly() {
			// Prepare
			ReturnUrlData expected = new ReturnUrlData(URL, MAP);
			// Run
			unitUnderTest.addReturnUrl(URL, MAP);
			unitUnderTest.getReturnUrl().get();
			// Check
			assertTrue(unitUnderTest.getReturnUrl().isEmpty());
		}

	}

}
