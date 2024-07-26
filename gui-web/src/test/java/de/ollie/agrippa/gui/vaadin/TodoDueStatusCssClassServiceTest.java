package de.ollie.agrippa.gui.vaadin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.ollie.agrippa.core.model.Todo;
import de.ollie.agrippa.core.service.TodoDueStatusService;
import de.ollie.agrippa.core.service.TodoDueStatusService.TodoDueStatus;

@ExtendWith(MockitoExtension.class)
class TodoDueStatusCssClassServiceTest {

    @Mock
    private Todo todo;

    @Mock
    private TodoDueStatusService todoDueStatusService;

    @InjectMocks
    private TodoDueStatusCssClassService unitUnderTest;

    @Nested
    class TestsOfMethod_getCssClassName_Todo {

        @ParameterizedTest
        @CsvSource({ "ALERT,grid-alert", "WARN,grid-warn", "NONE,grid-regular" })
        void returnsTheCorrectClassNameForThePassedTodoDueStatus(TodoDueStatus todoDueStatus, String expected) {
            when(todoDueStatusService.getDueStatus(todo)).thenReturn(todoDueStatus);
            assertEquals(expected, unitUnderTest.getCssClassName(todo));
        }

    }

}
