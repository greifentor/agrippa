package de.ollie.agrippa.gui.vaadin.masterdata.layout.list;

import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import de.ollie.agrippa.core.model.Task;
import de.ollie.agrippa.core.model.Todo;
import de.ollie.agrippa.gui.vaadin.component.ComponentFactory;
import de.ollie.agrippa.gui.vaadin.masterdata.dialog.TodoDetailsDialog;
import de.ollie.agrippa.core.service.localization.ResourceManager;
import de.ollie.agrippa.gui.vaadin.masterdata.MasterDataGUIConfiguration;
import de.ollie.agrippa.gui.vaadin.masterdata.MasterDataGridFieldRenderer;
import de.ollie.agrippa.gui.vaadin.component.ServiceProvider;
import de.ollie.agrippa.gui.SessionData;

import lombok.Generated;
import lombok.RequiredArgsConstructor;

/**
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@RequiredArgsConstructor
public class TodoListDetailsLayout extends VerticalLayout {

	private final ComponentFactory componentFactory;
	private final MasterDataGUIConfiguration guiConfiguration;
	private final Task model;
	private final ResourceManager resourceManager;
	private final ServiceProvider serviceProvider;
	private final SessionData session;

	private Grid<Todo> grid;

	@Override
	public void onAttach(AttachEvent attachEvent) {
		Button buttonAdd = componentFactory.createAddButton(event -> {
			new TodoDetailsDialog(componentFactory, guiConfiguration, (mmbr, newItem) -> {
				model.getTodos().add(mmbr);
				grid.setItems(model.getTodos());
			}, session, new Todo(), serviceProvider, true).open();
		}, session);
		Button buttonDuplicate = componentFactory.createDuplicateButton(event -> {
			new TodoDetailsDialog(componentFactory, guiConfiguration, (mmbr, newItem) -> {
				model.getTodos().add(mmbr);
				grid.setItems(model.getTodos());
			}, session, grid.getSelectedItems().toArray(new Todo[0])[0], serviceProvider, true).open();
		}, session);
		Button buttonEdit = componentFactory.createEditButton(event -> {
			new TodoDetailsDialog(componentFactory, guiConfiguration, (toEdit, newItem) -> {
				Todo mmbr = grid.getSelectedItems().toArray(new Todo[0])[0];
				mmbr.setTitle(toEdit.getTitle());
				mmbr.setDescription(toEdit.getDescription());
				grid.setItems(model.getTodos());
			}, session, grid.getSelectedItems().toArray(new Todo[0])[0], serviceProvider, false).open();
		}, session);
		Button buttonRemove = componentFactory.createRemoveButton(event -> {
			Todo mmbr = grid.getSelectedItems().toArray(new Todo[0])[0];
			model.getTodos().remove(mmbr);
			grid.setItems(model.getTodos());
		}, session);
		HorizontalLayout buttons = new HorizontalLayout(buttonAdd, buttonEdit, buttonDuplicate, buttonRemove);
		grid = new Grid<>();
		grid
				.addColumn(model -> getCellString("TITLE", model, () -> model.getTitle()))
				.setHeader(
						resourceManager
								.getLocalizedString(
										"TodoListDetailsLayout.grid.header.title.label",
										session.getLocalization()))
				.setSortable(true);
		grid
				.addColumn(model -> getCellString("DESCRIPTION", model, () -> model.getDescription()))
				.setHeader(
						resourceManager
								.getLocalizedString(
										"TodoListDetailsLayout.grid.header.description.label",
										session.getLocalization()))
				.setSortable(true);
		if (!model.getTodos().isEmpty() && (model.getTodos().get(0) instanceof Comparable)) {
			grid.setItems(model.getTodos().stream().sorted().collect(Collectors.toList()));
		} else {
			grid.setItems(model.getTodos());
		}
		grid.setWidthFull();
		add(buttons, grid);
	}

	private Object getCellString(String fieldName, Todo aTable, Supplier<?> f) {
		return componentFactory.getTodoMasterDataGridFieldRenderer() != null
				&& componentFactory.getTodoMasterDataGridFieldRenderer().hasRenderingFor(fieldName)
						? componentFactory
								.getTodoMasterDataGridFieldRenderer()
								.getHeaderString(fieldName, aTable)
						: f.get();
	}

}