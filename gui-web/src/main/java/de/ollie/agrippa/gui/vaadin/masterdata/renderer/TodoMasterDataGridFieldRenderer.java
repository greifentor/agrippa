package de.ollie.agrippa.gui.vaadin.masterdata.renderer;

import javax.inject.Inject;
import javax.inject.Named;

import de.ollie.agrippa.core.model.Todo;
import de.ollie.agrippa.core.model.TodoPriority;
import de.ollie.agrippa.core.model.TodoStatus;
import de.ollie.agrippa.gui.vaadin.component.ComponentFactory;
import de.ollie.agrippa.gui.vaadin.masterdata.MasterDataGridFieldRenderer;

import lombok.Generated;

/**
 * An implementation of the MasterDataGridFieldRenderer interface for todos.
 *
 * If necessary to override, remove 'GENERATED CODE ...' comment and annotation.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
public class TodoMasterDataGridFieldRenderer implements MasterDataGridFieldRenderer<Todo> {

	@Inject
	private ComponentFactory componentFactory;

	@Override
	public Object getHeaderString(String fieldName, Todo model) {
		if (Todo.PRIORITY.equals(fieldName)) {
			return componentFactory.getTodoPriorityItemLabelGenerator().apply(model.getPriority());
		}
		if (Todo.STATUS.equals(fieldName)) {
			return componentFactory.getTodoStatusItemLabelGenerator().apply(model.getStatus());
		}
		return null;
	}

	@Override
	public boolean hasRenderingFor(String fieldName) {
		if (Todo.PRIORITY.equals(fieldName) && (componentFactory.getTodoPriorityItemLabelGenerator() != null)) {
			return true;
		}
		if (Todo.STATUS.equals(fieldName) && (componentFactory.getTodoStatusItemLabelGenerator() != null)) {
			return true;
		}
		return false;
	}

}