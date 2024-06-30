package de.ollie.agrippa.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UUIDFactoryServiceImplTest {

	@InjectMocks
	private UUIDFactoryServiceImpl unitUnderTest;

	@Nested
	class TestsOfMethod_createUUIDAsString {

		@Test
		void returnsAString() {
			assertNotNull(unitUnderTest.createUUIDAsString());
		}

		@Test
		void returnsADifferentStringOnEachCall() {
			// Run
			String returned0 = unitUnderTest.createUUIDAsString();
			String returned1 = unitUnderTest.createUUIDAsString();
			String returned2 = unitUnderTest.createUUIDAsString();
			// Check
			assertNotEquals(returned0, returned1);
			assertNotEquals(returned1, returned2);
			assertNotEquals(returned2, returned0);
		}

		@Test
		void returnedValidUUIDs() {
			assertDoesNotThrow(() -> UUID.fromString(unitUnderTest.createUUIDAsString()));
		}

	}

}
