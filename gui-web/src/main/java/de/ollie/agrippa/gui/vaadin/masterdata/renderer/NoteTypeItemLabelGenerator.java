package de.ollie.agrippa.gui.vaadin.masterdata.renderer;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.ItemLabelGenerator;

import de.ollie.agrippa.core.model.NoteType;
import de.ollie.agrippa.core.service.localization.ResourceManager;
import lombok.Generated;
import lombok.Getter;

/**
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Named
@Getter
@Generated
public class NoteTypeItemLabelGenerator implements ItemLabelGenerator<NoteType> {

	@Autowired
	private ResourceManager resourceManager;

	@Override
	public String apply(NoteType item) {
		return item != null ? resourceManager.getLocalizedString("NoteType." + item.name() + ".label") : "-";
	}

}