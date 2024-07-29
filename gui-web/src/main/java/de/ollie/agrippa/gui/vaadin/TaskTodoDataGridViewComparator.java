package de.ollie.agrippa.gui.vaadin;

import java.time.LocalDateTime;
import java.util.Comparator;

import javax.inject.Named;

import de.ollie.agrippa.gui.vaadin.MainMenuView.TaskTodoData;

@Named
public class TaskTodoDataGridViewComparator implements Comparator<TaskTodoData> {

    @Override
    public int compare(TaskTodoData ttd0, TaskTodoData ttd1) {
        int i = compare(ttd0.getTodo().getDueDate(), ttd1.getTodo().getDueDate());
        if (i == 0) {
            i = ttd0.getPriorityStr().compareTo(ttd1.getPriorityStr());
            if (i == 0) {
                i = ttd0.getTodoAndTaskStatus().compareTo(ttd1.getTodoAndTaskStatus());
            }
        }
        return i;
    }

    private int compare(LocalDateTime ldt0, LocalDateTime ldt1) {
        if (ldt0 == ldt1) {
            return 0;
        } else if ((ldt0 == null) && (ldt1 != null)) {
            return 1;
        } else if ((ldt0 != null) && (ldt1 == null)) {
            return -1;
        }
        return ldt0.compareTo(ldt1);
    }

}
