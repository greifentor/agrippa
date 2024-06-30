package de.ollie.agrippa.core.service.impl;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import de.ollie.agrippa.core.model.User;
import de.ollie.agrippa.core.service.UUIDFactoryService;
import de.ollie.agrippa.core.service.UserService;

@ExtendWith(MockitoExtension.class)
class DefaultUserGlobalIdServiceImplTest {

	private static final String UUID_STR = UUID.randomUUID().toString();

	@Mock
	private User user;

	@Mock
	private UserService userService;

	@Mock
	private UUIDFactoryService uuidFactoryService;

	@InjectMocks
	private DefaultUserGlobalIdServiceImpl unitUnderTest;

	@Nested
	class TestsOfMethod_postConstruct {

		@Test
		void doesNotUpdateTheDefaultUserGlobalId_whenNoDefaultUserIsPresent() {
			// Prepare
			when(userService.findDefaultUser()).thenReturn(Optional.empty());
			// Run
			unitUnderTest.postConstruct();
			// Check
			verify(userService, never()).update(user);
		}

		@Test
		void doesNotUpdateTheDefaultUserGlobalId_whenADefaultUserIsPresent_withAnGlobalIdSet() {
			// Prepare
			when(user.getGlobalId()).thenReturn(UUID_STR);
			when(userService.findDefaultUser()).thenReturn(Optional.of(user));
			// Run
			unitUnderTest.postConstruct();
			// Check
			verify(userService, never()).update(user);
		}

		@Test
		void updateTheDefaultUserGlobalId_whenADefaultUserIsPresent_withNoGlobalIdSet() {
			// Prepare
			when(user.getGlobalId()).thenReturn(null);
			when(userService.findDefaultUser()).thenReturn(Optional.of(user));
			// Run
			unitUnderTest.postConstruct();
			// Check
			verify(userService, times(1)).update(user);
		}

		@Test
		void setsANewGlobalIdForTheDefaultUser_whenADefaultUserIsPresent_withNoGlobalIdSet() {
			// Prepare
			when(user.getGlobalId()).thenReturn(null);
			when(userService.findDefaultUser()).thenReturn(Optional.of(user));
			when(uuidFactoryService.createUUIDAsString()).thenReturn(UUID_STR);
			// Run
			unitUnderTest.postConstruct();
			// Check
			verify(user, times(1)).setGlobalId(UUID_STR);
		}

		@Test
		void setsANewGlobalIdAnUpdatesTheDefaultUser_inCorrectOrder() {
			// Prepare
			when(user.getGlobalId()).thenReturn(null);
			when(userService.findDefaultUser()).thenReturn(Optional.of(user));
			when(uuidFactoryService.createUUIDAsString()).thenReturn(UUID_STR);
			// Run
			unitUnderTest.postConstruct();
			// Check
			InOrder inOrder = Mockito.inOrder(user, userService);
			inOrder.verify(user, times(1)).setGlobalId(UUID_STR);
			inOrder.verify(userService, times(1)).update(user);
		}

	}

}
