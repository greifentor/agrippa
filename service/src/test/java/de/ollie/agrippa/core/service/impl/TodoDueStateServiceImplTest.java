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

@ExtendWith(MockitoExtension.class)
class TodoDueStateServiceImplTest {

    private static final LocalDateTime NOW = LocalDateTime.now();

    @Mock
    private CurrentDateTimeService currentDateTimeService;

    @Mock
    private Todo todo;

    @InjectMocks
    private TodoDueStateServiceImpl unitUnderTest;

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
            when(todo.getDueDate()).thenReturn(NOW.plusYears(1));
            assertEquals(TodoDueStatus.NONE, unitUnderTest.getDueStatus(todo));
        }

        @Test
        void returnsWARN_passingATodoWithADueDate8OrLessHoursInTheFuture() {
            when(todo.getDueDate()).thenReturn(NOW.plusHours(8));
            assertEquals(TodoDueStatus.WARN, unitUnderTest.getDueStatus(todo));
        }

        @Test
        void returnsALERT_passingATodoWithADueDateInThePast() {
            when(todo.getDueDate()).thenReturn(NOW.minusSeconds(1));
            assertEquals(TodoDueStatus.ALERT, unitUnderTest.getDueStatus(todo));
        }

    }

}
