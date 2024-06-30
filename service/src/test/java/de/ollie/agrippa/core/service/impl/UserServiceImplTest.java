package de.ollie.agrippa.core.service.impl;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.ollie.agrippa.core.model.User;
import de.ollie.agrippa.core.service.port.persistence.UserPersistencePort;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	@Mock
	private User user;

	@Mock
	private UserPersistencePort userPersistencePort;

	@InjectMocks
	private UserServiceImpl unitUnderTest;

	@Nested
	class TestsOfMethod_findDefaultUser {

		@Test
		void returnsAnEmptyOptional_whenNoUserIsPresent() {
			// Prepare
			when(userPersistencePort.findAll()).thenReturn(List.of());
			// Check
			Optional<User> returned = unitUnderTest.findDefaultUser();
			// Run
			assertTrue(returned.isEmpty());
		}

		@Test
		void returnsAnEmptyOptional_whenNoDefaultUserIsPresent() {
			// Prepare
			when(user.isDefaultUser()).thenReturn(false);
			when(userPersistencePort.findAll()).thenReturn(List.of(user));
			// Check
			Optional<User> returned = unitUnderTest.findDefaultUser();
			// Run
			assertTrue(returned.isEmpty());
		}

		@Test
		void returnsAnOptionalWithTheDefaultUser_whenTheDefaultUserIsPresent() {
			// Prepare
			when(user.isDefaultUser()).thenReturn(true);
			when(userPersistencePort.findAll()).thenReturn(List.of(user));
			// Check
			Optional<User> returned = unitUnderTest.findDefaultUser();
			// Run
			assertSame(user, returned.get());
		}

	}

}
