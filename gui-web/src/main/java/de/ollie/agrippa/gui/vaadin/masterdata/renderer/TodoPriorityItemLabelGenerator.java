package de.ollie.agrippa.gui.vaadin.masterdata.renderer;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.ItemLabelGenerator;

import de.ollie.agrippa.core.model.TodoPriority;
import de.ollie.agrippa.core.service.localization.ResourceManager;
import lombok.Generated;
import lombok.Getter;

/**
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Named
@Getter
@Generated
public class TodoPriorityItemLabelGenerator implements ItemLabelGenerator<TodoPriority> {

	@Autowired
	private ResourceManager resourceManager;

	@Override
	public String apply(TodoPriority item) {
		return item != null ? resourceManager.getLocalizedString("TodoPriority." + item.name() + ".label") : "-";
	}

}