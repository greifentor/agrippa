package de.ollie.agrippa.gui.vaadin.masterdata;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.ollie.agrippa.core.model.Task;
import de.ollie.agrippa.core.model.User;
import de.ollie.agrippa.core.service.UserService;

@ExtendWith(MockitoExtension.class)
class TaskCreateNewModelModificationTest {

	@Mock
	private Task task;

	@Mock
	private User user;

	@Mock
	private UserService userService;

	@InjectMocks
	private TaskCreateNewModelModification unitUnderTest;

	@Nested
	class TestsOfMethod_modify_Task {

		@Test
		void returnsTheSameObjectAsPassed() {
			Task task = new Task();
			assertSame(task, unitUnderTest.modify(task));
		}

		@Test
		void setsTheDefaultUserForThePassedModel() {
			// Prepare
			when(userService.findDefaultUser()).thenReturn(Optional.of(user));
			// Run
			unitUnderTest.modify(task);
			// Check
			verify(task, times(1)).setUser(user);
		}

		@Test
		void setsANullValue_whenNoDefaultUserIsPresent() {
			// Prepare
			when(userService.findDefaultUser()).thenReturn(Optional.empty());
			// Run
			unitUnderTest.modify(task);
			// Check
			verify(task, times(1)).setUser(null);
		}

	}

}
