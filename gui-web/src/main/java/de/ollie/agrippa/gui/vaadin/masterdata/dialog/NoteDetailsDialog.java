package de.ollie.agrippa.gui.vaadin.masterdata.dialog;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import de.ollie.agrippa.core.model.Note;
import de.ollie.agrippa.core.model.NoteType;
import de.ollie.agrippa.gui.SessionData;
import de.ollie.agrippa.gui.vaadin.component.Button;
import de.ollie.agrippa.gui.vaadin.component.ComponentFactory;
import de.ollie.agrippa.gui.vaadin.component.ServiceProvider;
import de.ollie.agrippa.gui.vaadin.masterdata.MasterDataGUIConfiguration;
import lombok.Generated;

/**
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public class NoteDetailsDialog extends Dialog {

	public interface Observer {

		void changed(Note model, boolean newItem);

	}

	private final ComponentFactory componentFactory;
	private final MasterDataGUIConfiguration guiConfiguration;
	private final Observer observer;
	private final ServiceProvider serviceProvider;
	private final SessionData session;

	private Button buttonCancel;
	private Button buttonSave;
	private TextField textFieldTitle;
	private DateTimePicker dateTimePickerCreationDate;
	private ComboBox<NoteType> comboBoxType;
	private TextArea textAreaDescription;
	private VerticalLayout mainLayout;

	private Note model = new Note();
	private boolean newItem;

	public NoteDetailsDialog(ComponentFactory componentFactory, MasterDataGUIConfiguration guiConfiguration,
			Observer observer, SessionData session, Note model, ServiceProvider serviceProvider, boolean newItem) {
		this.componentFactory = componentFactory;
		this.guiConfiguration = guiConfiguration;
		this.newItem = newItem;
		this.observer = observer;
		this.serviceProvider = serviceProvider;
		this.session = session;
		if (model != null) {
			this.model.setTitle(model.getTitle());
			this.model.setCreationDate(model.getCreationDate());
			this.model.setType(model.getType());
			this.model.setDescription(model.getDescription());
		}
		mainLayout = new VerticalLayout();
		addComponents();
		buttonCancel = componentFactory.createCancelButton(event -> close(), session);
		buttonCancel.setWidthFull();
		buttonSave = componentFactory.createSaveButton(event -> save(), session);
		buttonSave.setWidthFull();
		mainLayout.add(buttonCancel, buttonSave);
		setWidth("90%");
		add(mainLayout);
		updateSaveButton();
	}

	private void addComponents() {
		textFieldTitle = componentFactory.createTextField("NoteDetailsLayout.field.title.label", model.getTitle(), session);
		textFieldTitle.addValueChangeListener(event -> {
			model.setTitle(event.getValue());
			updateSaveButton();
		});
        dateTimePickerCreationDate = componentFactory.createDateTimePicker("NoteDetailsLayout.field.creationdate.label",
                session.getLocalization(),
                model.getCreationDate(),
                event -> {
                });
		dateTimePickerCreationDate.addValueChangeListener(event -> {
			model.setCreationDate(event.getValue());
			updateSaveButton();
		});
		comboBoxType = componentFactory.createComboBox("NoteDetailsLayout.field.type.label", model.getType(), NoteType.values(), componentFactory.getNoteTypeItemLabelGenerator(), session);
		comboBoxType.addValueChangeListener(event -> {
			model.setType(event.getValue());
			updateSaveButton();
		});
		textAreaDescription = componentFactory.createTextArea("NoteDetailsLayout.field.description.label", model.getDescription(), session);
		textAreaDescription.addValueChangeListener(event -> {
			model.setDescription(event.getValue());
			updateSaveButton();
		});
		mainLayout.add(
				textFieldTitle,
				dateTimePickerCreationDate,
				comboBoxType,
				textAreaDescription
		);
	}

	private void updateSaveButton() {
		setButtonEnabled(buttonSave,
				(textFieldTitle.getValue() != null) &&
				(dateTimePickerCreationDate.getValue() != null) &&
				(comboBoxType.getValue() != null)
		);
	}

	private void setButtonEnabled(Button button, boolean enabled) {
		button.setEnabled(enabled);
		if (enabled) {
			button.setBackgroundImage(guiConfiguration.getButtonEnabledBackgroundFileName());
			button.setBorderColor(guiConfiguration.getButtonEnabledBorderColor());
		} else {
			button.setBackgroundImage(guiConfiguration.getButtonDisabledBackgroundFileName());
			button.setBorderColor(guiConfiguration.getButtonDisabledBorderColor());
		}
	}

	private void save() {
		if (observer != null) {
			observer.changed(model, newItem);
		}
		close();
	}
}