package de.ollie.agrippa.gui.vaadin.component;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.inject.Named;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.QueryParameters;

import de.ollie.agrippa.core.model.NoteType;
import de.ollie.agrippa.core.model.Period;
import de.ollie.agrippa.core.model.TaskStatus;
import de.ollie.agrippa.core.model.TodoStatus;
import de.ollie.agrippa.core.model.Note;
import de.ollie.agrippa.core.model.Project;
import de.ollie.agrippa.core.model.Task;
import de.ollie.agrippa.core.model.Todo;
import de.ollie.agrippa.core.model.User;
import de.ollie.agrippa.core.service.localization.ResourceManager;
import de.ollie.agrippa.gui.SessionData;
import de.ollie.agrippa.gui.vaadin.ApplicationStartView;
import de.ollie.agrippa.gui.vaadin.masterdata.MasterDataGridFieldRenderer;

import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * A component factory.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Getter
@Named
@RequiredArgsConstructor
public class ComponentFactory {

	private final ButtonFactoryConfiguration buttonFactoryConfiguration;
	private final ResourceManager resourceManager;

	@Autowired(required = false)
	private ItemLabelGenerator<NoteType> noteTypeItemLabelGenerator;
	@Autowired(required = false)
	private ItemLabelGenerator<Period> periodItemLabelGenerator;
	@Autowired(required = false)
	private ItemLabelGenerator<TaskStatus> taskStatusItemLabelGenerator;
	@Autowired(required = false)
	private ItemLabelGenerator<TodoStatus> todoStatusItemLabelGenerator;

	@Autowired(required = false)
	private ItemLabelGenerator<Project> projectItemLabelGenerator;
	@Autowired(required = false)
	private ItemLabelGenerator<Task> taskItemLabelGenerator;
	@Autowired(required = false)
	private ItemLabelGenerator<User> userItemLabelGenerator;

	@Autowired(required = false)
	private MasterDataGridFieldRenderer<Note> noteMasterDataGridFieldRenderer;
	@Autowired(required = false)
	private MasterDataGridFieldRenderer<Project> projectMasterDataGridFieldRenderer;
	@Autowired(required = false)
	private MasterDataGridFieldRenderer<Task> taskMasterDataGridFieldRenderer;
	@Autowired(required = false)
	private MasterDataGridFieldRenderer<Todo> todoMasterDataGridFieldRenderer;
	@Autowired(required = false)
	private MasterDataGridFieldRenderer<User> userMasterDataGridFieldRenderer;

	public Button createButton(String text) {
		Button button =
				new Button(text)
						.setBackgroundColor("white")
						.setBorder("solid 1px")
						.setBorderColor(buttonFactoryConfiguration.getButtonEnabledBorderColor())
						.setColor("black")
						.setBackgroundImage(buttonFactoryConfiguration.getButtonEnabledBackgroundFileName());
		return button;
	}

	public Button createAddButton(Consumer<ClickEvent<?>> action, SessionData sessionData) {
		return createResourcedButton("commons.button.add.text", action, sessionData);
	}

	public Button createBackButton(Supplier<Optional<UI>> uiSupplier, String urlBack, SessionData sessionData) {
		Button buttonBack =
				createButton(
						resourceManager.getLocalizedString("commons.button.back.text", sessionData.getLocalization()));
		buttonBack.addClickListener(event -> uiSupplier.get().ifPresent(ui -> ui.navigate(urlBack)));
		return buttonBack;
	}

	public Button createBackButton(Supplier<Optional<UI>> uiSupplier, String urlBack, QueryParameters parameters,
			SessionData sessionData) {
		Button buttonBack =
				createButton(
						resourceManager.getLocalizedString("commons.button.back.text", sessionData.getLocalization()));
		buttonBack.addClickListener(event -> uiSupplier.get().ifPresent(ui -> ui.navigate(urlBack, parameters)));
		return buttonBack;
	}

	public Button createCancelButton(Consumer<ClickEvent<?>> action, SessionData sessionData) {
		return createResourcedButton("commons.button.cancel.text", action, sessionData);
	}

	public Button createDuplicateButton(Consumer<ClickEvent<?>> action, SessionData sessionData) {
		return createResourcedButton("commons.button.duplicate.text", action, sessionData);
	}

	public Button createEditButton(Consumer<ClickEvent<?>> action, SessionData sessionData) {
		return createResourcedButton("commons.button.edit.text", action, sessionData);
	}

	public Button createLogoutButton(Supplier<Optional<UI>> uiSupplier, Logger logger, SessionData sessionData) {
		Button buttonLogout =
				createButton(
						resourceManager
								.getLocalizedString("commons.button.logout.text", sessionData.getLocalization()));
		buttonLogout.addClickListener(event -> uiSupplier.get().ifPresent(ui -> {
			ui.navigate(ApplicationStartView.URL);
		}));
		return buttonLogout;
	}

	public Button createRemoveButton(Consumer<ClickEvent<?>> action, SessionData sessionData) {
		return createResourcedButton("commons.button.remove.text", action, sessionData);
	}

	public Button createResourcedButton(String resourceId, Consumer<ClickEvent<?>> action, SessionData sessionData) {
		Button button = createButton(resourceManager.getLocalizedString(resourceId, sessionData.getLocalization()));
		button.addClickListener(action::accept);
		return button;
	}

	public Button createSaveButton(Consumer<ClickEvent<?>> action, SessionData sessionData) {
		return createResourcedButton("commons.button.save.text", action, sessionData);
	}

	public Checkbox createCheckbox(String resourceId, Boolean fieldContent, SessionData sessionData) {
		Checkbox checkBox = new Checkbox(resourceManager.getLocalizedString(resourceId, sessionData.getLocalization()));
		checkBox.setValue(fieldContent);
		checkBox.setWidthFull();
		return checkBox;
	}

	public <T> ComboBox<T> createComboBox(String resourceId, T fieldContent, T[] valuesToSelect,
			SessionData sessionData) {
		return createComboBox(resourceId, fieldContent, valuesToSelect, null, sessionData);
	}

	public <T> ComboBox<T> createComboBox(String resourceId, T fieldContent, T[] valuesToSelect,
			ItemLabelGenerator<T> itemLabelGenerator, SessionData sessionData) {
		ComboBox<T> comboBox =
				new ComboBox<>(resourceManager.getLocalizedString(resourceId, sessionData.getLocalization()));
		comboBox.setItems(valuesToSelect);
		comboBox.setValue(fieldContent);
		comboBox.setWidthFull();
		if (itemLabelGenerator != null) {
			comboBox.setItemLabelGenerator(itemLabelGenerator);
		}
		return comboBox;
	}

	public IntegerField createIntegerField(String resourceId, Integer fieldContent, Integer min, Integer max,
			SessionData sessionData) {
		IntegerField integerField =
				new IntegerField(resourceManager.getLocalizedString(resourceId, sessionData.getLocalization()));
		if (max != null) {
			integerField.setMax(max);
		}
		if (min != null) {
			integerField.setMin(min);
		}
		integerField.setHasControls(true);
		integerField.setValue(fieldContent);
		integerField.setWidthFull();
		return integerField;
	}

	public IntegerField createIntegerField(String resourceId, Integer fieldContent, Integer min, Integer max,
			Integer step, SessionData sessionData) {
		IntegerField integerField =
				new IntegerField(resourceManager.getLocalizedString(resourceId, sessionData.getLocalization()));
		if (max != null) {
			integerField.setMax(max);
		}
		if (min != null) {
			integerField.setMin(min);
		}
		if (step != null) {
			integerField.setStep(step);
		}
		integerField.setHasControls(true);
		integerField.setValue(fieldContent);
		integerField.setWidthFull();
		return integerField;
	}

	public NumberField createNumberField(String resourceId, Double fieldContent, Double min, Double max, Double step,
			SessionData sessionData) {
		NumberField integerField =
				new NumberField(resourceManager.getLocalizedString(resourceId, sessionData.getLocalization()));
		if (max != null) {
			integerField.setMax(max);
		}
		if (min != null) {
			integerField.setMin(min);
		}
		integerField.setHasControls(true);
		integerField.setValue(fieldContent);
		integerField.setWidthFull();
		if (step != null) {
			integerField.setStep(step);
		}
		return integerField;
	}

	public TextField createTextField(String resourceId, String fieldContent, SessionData sessionData) {
		TextField textField =
				TextFieldFactory
						.createTextField(resourceManager.getLocalizedString(resourceId, sessionData.getLocalization()));
		textField.setValue(fieldContent != null ? fieldContent : "");
		textField.setWidthFull();
		return textField;
	}

	public TextArea createTextArea(String resourceId, String fieldContent, SessionData sessionData) {
		TextArea textArea = new TextArea(resourceManager.getLocalizedString(resourceId, sessionData.getLocalization()));
		textArea.setValue(fieldContent != null ? fieldContent : "");
		textArea.setWidthFull();
		return textArea;
	}

}