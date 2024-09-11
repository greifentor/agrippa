package de.ollie.agrippa.gui.vaadin.masterdata.renderer;

import com.vaadin.flow.component.ItemLabelGenerator;

import de.ollie.agrippa.core.model.Todo;

public class TodoItemLabelGenerator implements ItemLabelGenerator<Todo> {

    @Override
    public String apply(Todo item) {
        return item != null ? item.getTitle() : "-";
    }

}
