package de.ollie.agrippa.gui.vaadin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.ollie.agrippa.core.model.Task;
import de.ollie.agrippa.core.model.TaskStatus;
import de.ollie.agrippa.core.model.Todo;
import de.ollie.agrippa.core.model.TodoPriority;
import de.ollie.agrippa.core.model.TodoStatus;
import de.ollie.agrippa.gui.vaadin.MainMenuView.TaskTodoData;

@ExtendWith(MockitoExtension.class)
class TaskTodoDataGridViewComparatorTest {

    private static final LocalDateTime NOW = LocalDateTime.now();

    private TaskTodoData ttd0;
    private TaskTodoData ttd1;

    @Mock
    private Task task0;

    @Mock
    private Task task1;

    @Mock
    private Todo todo0;

    @Mock
    private Todo todo1;

    @InjectMocks
    private TaskTodoDataGridViewComparator unitUnderTest;

    @BeforeEach
    void beforeEach() {
        ttd0 = new TaskTodoData(task0, todo0);
        ttd1 = new TaskTodoData(task1, todo1);
    }

    @Nested
    class TestsOfMethod_compare_TaskTodoData_TaskTodoData {

        @Test
        void returnsZero_passingTheSameTaskTodoDataTwice() {
            when(todo0.getPriority()).thenReturn(TodoPriority.LOW);
            when(todo0.getStatus()).thenReturn(TodoStatus.OPEN);
            when(task0.getTaskStatus()).thenReturn(TaskStatus.OPEN);
            assertEquals(0, unitUnderTest.compare(ttd0, ttd0));
        }

        @Nested
        class TestsFor_Priority {

            @Test
            void returnsGreaterThanZero_whenPriorityOfTtd0IsLessThanPriorityOfTtd1() {
                // Prepare
                when(todo0.getPriority()).thenReturn(TodoPriority.LOW);
                when(todo1.getPriority()).thenReturn(TodoPriority.HIGH);
                // Run & Check
                assertTrue(unitUnderTest.compare(ttd0, ttd1) > 0);
            }

            @Test
            void returnsLessThanZero_whenPriorityOfTtd0IsGreaterThanPriorityOfTtd1() {
                // Prepare
                when(todo0.getPriority()).thenReturn(TodoPriority.HIGH);
                when(todo1.getPriority()).thenReturn(TodoPriority.LOW);
                // Run & Check
                assertTrue(unitUnderTest.compare(ttd0, ttd1) < 0);
            }

            @Test
            void returnsZero_whenPriorityOfTtd0IsSameAsPriorityOfTtd1() {
                // Prepare
                when(todo0.getPriority()).thenReturn(TodoPriority.HIGH);
                when(todo1.getPriority()).thenReturn(TodoPriority.HIGH);
                when(todo0.getStatus()).thenReturn(TodoStatus.OPEN);
                when(todo1.getStatus()).thenReturn(TodoStatus.OPEN);
                when(task0.getTaskStatus()).thenReturn(TaskStatus.OPEN);
                when(task1.getTaskStatus()).thenReturn(TaskStatus.OPEN);
                // Run & Check
                assertEquals(0, unitUnderTest.compare(ttd0, ttd1));
            }

        }

        @Nested
        class TestsFor_TodoAndTaskStatus {

            @Test
            void returnsGreaterThanZero_whenPriorityIsEqual_butTodoAndTaskStatusOfTtd0IsLessThanTodoAndTaskStatusOfTtd1_taskStatusIsIrrelevant() {
                // Prepare
                when(todo0.getPriority()).thenReturn(TodoPriority.HIGH);
                when(todo1.getPriority()).thenReturn(TodoPriority.HIGH);
                when(todo0.getStatus()).thenReturn(TodoStatus.OPEN);
                when(todo1.getStatus()).thenReturn(TodoStatus.WIP);
                when(task0.getTaskStatus()).thenReturn(TaskStatus.OPEN);
                when(task1.getTaskStatus()).thenReturn(TaskStatus.OPEN);
                // Run & Check
                assertTrue(unitUnderTest.compare(ttd0, ttd1) > 0);
            }

            @Test
            void returnsLessThanZero_whenPriorityIsEqual_butTodoAndTaskStatusOfTtd0IsGreaterThanTodoAndTaskStatusOfTtd1_taskStatusIsIrrelevant() {
                // Prepare
                when(todo0.getStatus()).thenReturn(TodoStatus.WIP);
                when(todo1.getStatus()).thenReturn(TodoStatus.OPEN);
                when(task0.getTaskStatus()).thenReturn(TaskStatus.OPEN);
                when(task1.getTaskStatus()).thenReturn(TaskStatus.OPEN);
                // Run & Check
                assertTrue(unitUnderTest.compare(ttd0, ttd1) < 0);
            }

            @Test
            void returnsZero_whenPriorityIsEqual_butTodoAndTaskStatusOfTtd0IsEqualsToTodoAndTaskStatusOfTtd1_taskStatusIsEqual() {
                // Prepare
                when(todo0.getStatus()).thenReturn(TodoStatus.OPEN);
                when(todo1.getStatus()).thenReturn(TodoStatus.OPEN);
                when(task0.getTaskStatus()).thenReturn(TaskStatus.OPEN);
                when(task1.getTaskStatus()).thenReturn(TaskStatus.OPEN);
                // Run & Check
                assertEquals(0, unitUnderTest.compare(ttd0, ttd1));
            }

        }

        @Nested
        class TestsFor_DueDate {

            @Test
            void returnsZero_passingEqualTaskTodoData() {
                // Prepare
                when(todo0.getDueDate()).thenReturn(NOW);
                when(todo1.getDueDate()).thenReturn(NOW);
                when(todo0.getPriority()).thenReturn(TodoPriority.HIGH);
                when(todo1.getPriority()).thenReturn(TodoPriority.HIGH);
                when(todo0.getStatus()).thenReturn(TodoStatus.OPEN);
                when(todo1.getStatus()).thenReturn(TodoStatus.OPEN);
                when(task0.getTaskStatus()).thenReturn(TaskStatus.OPEN);
                when(task1.getTaskStatus()).thenReturn(TaskStatus.OPEN);
                // Run & Check
                assertEquals(0, unitUnderTest.compare(ttd0, ttd1));
            }

            @Test
            void returnsZero_passingEqualTaskTodoData_dueDateIsNull() {
                // Prepare
                when(todo0.getDueDate()).thenReturn(null);
                when(todo1.getDueDate()).thenReturn(null);
                when(todo0.getPriority()).thenReturn(TodoPriority.HIGH);
                when(todo1.getPriority()).thenReturn(TodoPriority.HIGH);
                when(todo0.getStatus()).thenReturn(TodoStatus.OPEN);
                when(todo1.getStatus()).thenReturn(TodoStatus.OPEN);
                when(task0.getTaskStatus()).thenReturn(TaskStatus.OPEN);
                when(task1.getTaskStatus()).thenReturn(TaskStatus.OPEN);
                // Run & Check
                assertEquals(0, unitUnderTest.compare(ttd0, ttd1));
            }

            @Test
            void returnsGreaterZero_passingTaskTodoData_withDueDate0IsNullOnly() {
                // Prepare
                when(todo0.getDueDate()).thenReturn(null);
                when(todo1.getDueDate()).thenReturn(NOW);
                // Run & Check
                assertEquals(1, unitUnderTest.compare(ttd0, ttd1));
            }

            @Test
            void returnsLessZero_passingTaskTodoData_withDueDate1IsNullOnly() {
                // Prepare
                when(todo0.getDueDate()).thenReturn(NOW);
                when(todo1.getDueDate()).thenReturn(null);
                // Run & Check
                assertEquals(-1, unitUnderTest.compare(ttd0, ttd1));
            }

            @Test
            void returnsLessZero_passingTaskTodoData_withDueDate0IsBeforeDueDate1() {
                // Prepare
                when(todo0.getDueDate()).thenReturn(NOW);
                when(todo1.getDueDate()).thenReturn(NOW.plusHours(1));
                // Run & Check
                assertEquals(-1, unitUnderTest.compare(ttd0, ttd1));
            }

            @Test
            void returnsGreaterZero_passingTaskTodoData_withDueDate0IsAfterDueDate1() {
                // Prepare
                when(todo0.getDueDate()).thenReturn(NOW.plusHours(1));
                when(todo1.getDueDate()).thenReturn(NOW);
                // Run & Check
                assertEquals(1, unitUnderTest.compare(ttd0, ttd1));
            }

        }
    }

}
