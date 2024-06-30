package de.ollie.agrippa.gui.vaadin.masterdata.renderer;

import javax.inject.Inject;
import javax.inject.Named;

import de.ollie.agrippa.core.model.Task;
import de.ollie.agrippa.core.model.TaskStatus;
import de.ollie.agrippa.gui.vaadin.component.ComponentFactory;
import de.ollie.agrippa.gui.vaadin.masterdata.MasterDataGridFieldRenderer;

import lombok.Generated;

/**
 * An implementation of the MasterDataGridFieldRenderer interface for tasks.
 *
 * If necessary to override, remove 'GENERATED CODE ...' comment and annotation.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
public class TaskMasterDataGridFieldRenderer implements MasterDataGridFieldRenderer<Task> {

	@Inject
	private ComponentFactory componentFactory;

	@Override
	public Object getHeaderString(String fieldName, Task model) {
		if (Task.PROJECT.equals(fieldName)) {
			return model.getProject() != null ? model.getProject().getTitle() : "-";
		}
		if (Task.TASKSTATUS.equals(fieldName)) {
			return componentFactory.getTaskStatusItemLabelGenerator().apply(model.getTaskStatus());
		}
		return null;
	}

	@Override
	public boolean hasRenderingFor(String fieldName) {
		if (Task.PROJECT.equals(fieldName)) {
			return true;
		}
		if (Task.TASKSTATUS.equals(fieldName) && (componentFactory.getTaskStatusItemLabelGenerator() != null)) {
			return true;
		}
		return false;
	}

}