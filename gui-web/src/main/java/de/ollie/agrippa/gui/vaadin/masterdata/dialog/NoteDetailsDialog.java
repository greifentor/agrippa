package de.ollie.agrippa.gui.vaadin.masterdata.dialog;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import de.ollie.agrippa.core.model.Note;
import de.ollie.agrippa.core.model.NoteType;
import de.ollie.agrippa.core.model.Task;
import de.ollie.agrippa.core.model.Todo;
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
    private TextField textFieldUrl;
    private ComboBox<NoteType> comboBoxType;
    private ComboBox<Todo> comboBoxRelatedTodo;
    private TextArea textAreaDescription;
    private VerticalLayout mainLayout;

    private Note model = new Note();
    private Task task;
    private boolean newItem;

    public NoteDetailsDialog(ComponentFactory componentFactory, MasterDataGUIConfiguration guiConfiguration,
            Observer observer, SessionData session, Note model, ServiceProvider serviceProvider, boolean newItem,
            Task task) {
        this.componentFactory = componentFactory;
        this.guiConfiguration = guiConfiguration;
        this.newItem = newItem;
        this.observer = observer;
        this.serviceProvider = serviceProvider;
        this.session = session;
        this.task = task;
        if (model != null) {
            this.model.setTitle(model.getTitle());
            this.model.setCreationDate(model.getCreationDate());
            this.model.setUrl(model.getUrl());
            this.model.setType(model.getType());
            this.model.setRelatedTodo(model.getRelatedTodo());
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
        textFieldTitle = componentFactory
                .createTextField("NoteDetailsLayout.field.title.label", model.getTitle(), session);
        textFieldTitle.addValueChangeListener(event -> {
            model.setTitle(event.getValue());
            updateSaveButton();
        });
        dateTimePickerCreationDate = componentFactory.createDateTimePicker("NoteDetailsLayout.field.creationdate.label",
                session.getLocalization(),
                model.getCreationDate(),
                event -> {
                    model.setCreationDate(event.getValue());
                    updateSaveButton();
                });
        textFieldUrl = componentFactory.createTextField("NoteDetailsLayout.field.url.label", model.getUrl(), session);
        textFieldUrl.addValueChangeListener(event -> {
            model.setUrl(event.getValue());
            updateSaveButton();
        });
        comboBoxType = componentFactory.createComboBox("NoteDetailsLayout.field.type.label",
                model.getType(),
                NoteType.values(),
                componentFactory.getNoteTypeItemLabelGenerator(),
                session);
        comboBoxType.addValueChangeListener(event -> {
            model.setType(event.getValue());
            updateSaveButton();
        });
        comboBoxRelatedTodo = componentFactory.createComboBox("NoteDetailsLayout.field.relatedtodo.label",
                model.getRelatedTodo(),
                task.getTodos().toArray(new Todo[task.getTodos().size()]),
                session);
        comboBoxRelatedTodo.setItemLabelGenerator(value -> componentFactory.getTodoItemLabelGenerator() != null
                ? componentFactory.getTodoItemLabelGenerator().apply(value)
                : "" + value.getTitle());
        comboBoxRelatedTodo.addValueChangeListener(event -> {
            model.setRelatedTodo(event.getValue());
            updateSaveButton();
        });
        textAreaDescription = componentFactory
                .createTextArea("NoteDetailsLayout.field.description.label", model.getDescription(), session);
        textAreaDescription.addValueChangeListener(event -> {
            model.setDescription(event.getValue());
            updateSaveButton();
        });
        mainLayout.add(textFieldTitle,
                dateTimePickerCreationDate,
                textFieldUrl,
                comboBoxType,
                comboBoxRelatedTodo,
                textAreaDescription);
    }

    private void updateSaveButton() {
        setButtonEnabled(buttonSave,
                (textFieldTitle.getValue() != null) && (dateTimePickerCreationDate.getValue() != null)
                        && (comboBoxType.getValue() != null));
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