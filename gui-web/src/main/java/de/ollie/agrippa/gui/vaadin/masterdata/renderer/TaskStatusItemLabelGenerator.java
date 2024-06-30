package de.ollie.agrippa.gui.vaadin.masterdata.renderer;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.ItemLabelGenerator;

import de.ollie.agrippa.core.model.TaskStatus;
import de.ollie.agrippa.core.service.localization.ResourceManager;
import lombok.Generated;
import lombok.Getter;

/**
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Named
@Getter
@Generated
public class TaskStatusItemLabelGenerator implements ItemLabelGenerator<TaskStatus> {

	@Autowired
	private ResourceManager resourceManager;

	@Override
	public String apply(TaskStatus item) {
		return item != null ? resourceManager.getLocalizedString("TaskStatus." + item.name() + ".label") : "-";
	}

}