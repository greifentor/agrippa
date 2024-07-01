package de.ollie.agrippa.gui.vaadin.masterdata;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import de.ollie.agrippa.core.model.Project;
import de.ollie.agrippa.core.model.Task;
import de.ollie.agrippa.core.model.TaskStatus;
import de.ollie.agrippa.core.service.localization.ResourceManager;
import de.ollie.agrippa.gui.SessionData;
import de.ollie.agrippa.gui.vaadin.component.AbstractMasterDataBaseLayout;
import de.ollie.agrippa.gui.vaadin.component.ButtonFactory;
import de.ollie.agrippa.gui.vaadin.component.ComponentFactory;
import de.ollie.agrippa.gui.vaadin.component.RemoveConfirmDialog;
import de.ollie.agrippa.gui.vaadin.component.ServiceProvider;
import de.ollie.agrippa.gui.vaadin.masterdata.layout.list.NoteListDetailsLayout;
import de.ollie.agrippa.gui.vaadin.masterdata.layout.list.TodoListDetailsLayout;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

/**
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@RequiredArgsConstructor
public class TaskDetailsLayout extends AbstractMasterDataBaseLayout {

	private final ButtonFactory buttonFactory;
	private final ComponentFactory componentFactory;
	private final Task model;
	private final ServiceProvider serviceProvider;
	private final MasterDataGUIConfiguration guiConfiguration;
	private final ResourceManager resourceManager;
	private final SessionData session;
	private final Observer observer;
	private final DetailsLayoutComboBoxItemLabelGenerator<Task> comboBoxItemLabelGenerator;

	private ComboBox<Project> comboBoxProject;
	private TextField textFieldTitle;
	private ComboBox<TaskStatus> comboBoxTaskStatus;
	private TextArea textAreaDescription;

	@Override
	public void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		createButtons();
		comboBoxProject = createComboBox("TaskDetailsLayout.field.project.label", model.getProject(), serviceProvider.getProjectService().findAll().toArray(new Project[0]));
		comboBoxProject
				.setItemLabelGenerator(
						project  -> comboBoxItemLabelGenerator != null
								? comboBoxItemLabelGenerator.getLabel(Task.PROJECT, project)
								: "" + project.getTitle());
		textFieldTitle = createTextField("TaskDetailsLayout.field.title.label", model.getTitle());
		comboBoxTaskStatus = createComboBox("TaskDetailsLayout.field.taskstatus.label", model.getTaskStatus(), TaskStatus.values(), componentFactory.getTaskStatusItemLabelGenerator());
		textAreaDescription = createTextArea("TaskDetailsLayout.field.description.label", model.getDescription());
		getStyle().set("-moz-border-radius", "4px");
		getStyle().set("-webkit-border-radius", "4px");
		getStyle().set("border-radius", "4px");
		getStyle().set("border", "1px solid #A9A9A9");
		getStyle()
				.set(
						"box-shadow",
						"10px 10px 20px #e4e4e4, -10px 10px 20px #e4e4e4, -10px -10px 20px #e4e4e4, 10px -10px 20px #e4e4e4");
		setMargin(false);
		setWidthFull();
        Accordion accordion = new Accordion();
        accordion.add(new AccordionPanel(
                resourceManager.getLocalizedString("TaskMaintenanceView.accordion.notes.label"),
                new NoteListDetailsLayout(
                        componentFactory, guiConfiguration, model, resourceManager, serviceProvider, session)));
        accordion.add(new AccordionPanel(
                resourceManager.getLocalizedString("TaskMaintenanceView.accordion.todos.label"),
                new TodoListDetailsLayout(
                        componentFactory, guiConfiguration, model, resourceManager, serviceProvider, session)));
        accordion.setWidthFull();
		add(
				textFieldTitle,
				comboBoxProject,
				comboBoxTaskStatus,
                textAreaDescription,
                accordion,
				getMasterDataButtonLayout(model.getId() > 0));
		textFieldTitle.focus();
	}

	@Override
	protected ButtonFactory getButtonFactory() {
		return buttonFactory;
	}

	@Override
	protected ResourceManager getResourceManager() {
		return resourceManager;
	}

	@Override
	protected SessionData getSessionData() {
		return session;
	}

	@Override
	protected void remove() {
		new RemoveConfirmDialog(buttonFactory, () -> {
			serviceProvider.getTaskService().delete(model);
			observer.remove();
		}, resourceManager, session).open();
	}

	@Override
	protected void save() {
		model.setTitle(textFieldTitle.getValue());
		model.setProject(comboBoxProject.getValue());
		model.setTaskStatus(comboBoxTaskStatus.getValue());
		model.setDescription(textAreaDescription.getValue());
		observer.save(serviceProvider.getTaskService().update(model));
	}

}