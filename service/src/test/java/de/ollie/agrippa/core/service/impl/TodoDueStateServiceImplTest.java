package de.ollie.agrippa.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.ollie.agrippa.core.model.Todo;
import de.ollie.agrippa.core.service.CurrentDateTimeService;
import de.ollie.agrippa.core.service.TodoDueStatusService.TodoDueStatus;
import de.ollie.agrippa.core.service.configuration.TodoWarnLevelConfiguration;

@ExtendWith(MockitoExtension.class)
class TodoDueStateServiceImplTest {

	private static final LocalDateTime NOW = LocalDateTime.now();
	private static final int HOURS_OFFSET = 42;

	@Mock
	private CurrentDateTimeService currentDateTimeService;

	@Mock
	private Todo todo;

	@InjectMocks
	private TodoDueStateServiceImpl unitUnderTest;

	@Mock
	private TodoWarnLevelConfiguration todoWarnLevelConfiguration;

	@BeforeEach
	void beforeEach() {
		lenient().when(currentDateTimeService.now()).thenReturn(NOW);
	}

	@Nested
	class TestsOfMethod_getDueStatus_Todo {

		@Test
		void throwsException_passingANullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.getDueStatus(null));
		}

		@Test
		void returnsNONE_passingATodoWithDueDateNull() {
			when(todo.getDueDate()).thenReturn(null);
			assertEquals(TodoDueStatus.NONE, unitUnderTest.getDueStatus(todo));
		}

		@Test
		void returnsNONE_passingATodoWithADueDateInTheFuture() {
			when(todo.getDueDate()).thenReturn(NOW.plusYears(99));
			assertEquals(TodoDueStatus.NONE, unitUnderTest.getDueStatus(todo));
		}

		@Test
		void returnsWARN_LEVEL3_passingATodoWithADueDateLessMinutesInTheFuture_asConfiguredAsWarnLevel3() {
			when(todoWarnLevelConfiguration.getWarnLevel3OffsetInMinutes()).thenReturn(HOURS_OFFSET * 60);
			when(todo.getDueDate()).thenReturn(NOW.plusHours(HOURS_OFFSET));
			assertEquals(TodoDueStatus.WARN_LEVEL_3, unitUnderTest.getDueStatus(todo));
		}

		@Test
		void returnsWARN_LEVEL2_passingATodoWithADueDateLessMinutesInTheFuture_asConfiguredAsWarnLevel2() {
			when(todoWarnLevelConfiguration.getWarnLevel3OffsetInMinutes()).thenReturn(HOURS_OFFSET * 60);
			when(todoWarnLevelConfiguration.getWarnLevel2OffsetInMinutes()).thenReturn(HOURS_OFFSET * 120);
			when(todo.getDueDate()).thenReturn(NOW.plusHours(HOURS_OFFSET * 2));
			assertEquals(TodoDueStatus.WARN_LEVEL_2, unitUnderTest.getDueStatus(todo));
		}

		@Test
		void returnsWARN_LEVEL1_passingATodoWithADueDateLessMinutesInTheFuture_asConfiguredAsWarnLevel1() {
			when(todoWarnLevelConfiguration.getWarnLevel3OffsetInMinutes()).thenReturn(HOURS_OFFSET * 60);
			when(todoWarnLevelConfiguration.getWarnLevel2OffsetInMinutes()).thenReturn(HOURS_OFFSET * 120);
			when(todoWarnLevelConfiguration.getWarnLevel1OffsetInMinutes()).thenReturn(HOURS_OFFSET * 180);
			when(todo.getDueDate()).thenReturn(NOW.plusHours(HOURS_OFFSET * 3));
			assertEquals(TodoDueStatus.WARN_LEVEL_1, unitUnderTest.getDueStatus(todo));
		}

		@Test
		void returnsALERT_passingATodoWithADueDateInThePast() {
			when(todo.getDueDate()).thenReturn(NOW.minusSeconds(1));
			assertEquals(TodoDueStatus.ALERT, unitUnderTest.getDueStatus(todo));
		}

	}

}
