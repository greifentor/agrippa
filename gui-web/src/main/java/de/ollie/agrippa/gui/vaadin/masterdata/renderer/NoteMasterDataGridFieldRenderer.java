package de.ollie.agrippa.gui.vaadin.masterdata.renderer;

import javax.inject.Inject;
import javax.inject.Named;

import de.ollie.agrippa.core.model.Note;
import de.ollie.agrippa.core.model.NoteType;
import de.ollie.agrippa.gui.vaadin.component.ComponentFactory;
import de.ollie.agrippa.gui.vaadin.masterdata.MasterDataGridFieldRenderer;

import lombok.Generated;

/**
 * An implementation of the MasterDataGridFieldRenderer interface for notes.
 *
 * If necessary to override, remove 'GENERATED CODE ...' comment and annotation.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Named
public class NoteMasterDataGridFieldRenderer implements MasterDataGridFieldRenderer<Note> {

	@Inject
	private ComponentFactory componentFactory;

	@Override
	public Object getHeaderString(String fieldName, Note model) {
		if (Note.RELATEDTODO.equals(fieldName)) {
			return model.getRelatedTodo() != null ? model.getRelatedTodo().getTitle() : "-";
		}
		if (Note.TYPE.equals(fieldName)) {
			return componentFactory.getNoteTypeItemLabelGenerator().apply(model.getType());
		}
		return null;
	}

	@Override
	public boolean hasRenderingFor(String fieldName) {
		if (Note.RELATEDTODO.equals(fieldName)) {
			return true;
		}
		if (Note.TYPE.equals(fieldName) && (componentFactory.getNoteTypeItemLabelGenerator() != null)) {
			return true;
		}
		return false;
	}

}