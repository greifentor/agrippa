package de.ollie.agrippa.gui.vaadin.masterdata.layout.list;

import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import de.ollie.agrippa.core.model.Project;
import de.ollie.agrippa.core.model.ProjectLink;
import de.ollie.agrippa.gui.vaadin.component.ComponentFactory;
import de.ollie.agrippa.gui.vaadin.masterdata.dialog.ProjectLinkDetailsDialog;
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
public class ProjectLinkListDetailsLayout extends VerticalLayout {

	private final ComponentFactory componentFactory;
	private final MasterDataGUIConfiguration guiConfiguration;
	private final Project model;
	private final ResourceManager resourceManager;
	private final ServiceProvider serviceProvider;
	private final SessionData session;

	private Grid<ProjectLink> grid;

	@Override
	public void onAttach(AttachEvent attachEvent) {
		Button buttonAdd = componentFactory.createAddButton(event -> {
			new ProjectLinkDetailsDialog(componentFactory, guiConfiguration, (mmbr, newItem) -> {
				model.getProjectLinks().add(mmbr);
				grid.setItems(model.getProjectLinks());
			}, session, new ProjectLink(), serviceProvider, true).open();
		}, session);
		Button buttonDuplicate = componentFactory.createDuplicateButton(event -> {
			new ProjectLinkDetailsDialog(componentFactory, guiConfiguration, (mmbr, newItem) -> {
				model.getProjectLinks().add(mmbr);
				grid.setItems(model.getProjectLinks());
			}, session, grid.getSelectedItems().toArray(new ProjectLink[0])[0], serviceProvider, true).open();
		}, session);
		Button buttonEdit = componentFactory.createEditButton(event -> {
			new ProjectLinkDetailsDialog(componentFactory, guiConfiguration, (toEdit, newItem) -> {
				ProjectLink mmbr = grid.getSelectedItems().toArray(new ProjectLink[0])[0];
				mmbr.setTitle(toEdit.getTitle());
				mmbr.setUrl(toEdit.getUrl());
				mmbr.setDescription(toEdit.getDescription());
				grid.setItems(model.getProjectLinks());
			}, session, grid.getSelectedItems().toArray(new ProjectLink[0])[0], serviceProvider, false).open();
		}, session);
		Button buttonRemove = componentFactory.createRemoveButton(event -> {
			ProjectLink mmbr = grid.getSelectedItems().toArray(new ProjectLink[0])[0];
			model.getProjectLinks().remove(mmbr);
			grid.setItems(model.getProjectLinks());
		}, session);
		HorizontalLayout buttons = new HorizontalLayout(buttonAdd, buttonEdit, buttonDuplicate, buttonRemove);
		grid = new Grid<>();
		grid
				.addColumn(model -> getCellString("TITLE", model, () -> model.getTitle()))
				.setHeader(
						resourceManager
								.getLocalizedString(
										"ProjectLinkListDetailsLayout.grid.header.title.label",
										session.getLocalization()))
				.setSortable(true);
		grid
				.addColumn(model -> getCellString("URL", model, () -> model.getUrl()))
				.setHeader(
						resourceManager
								.getLocalizedString(
										"ProjectLinkListDetailsLayout.grid.header.url.label",
										session.getLocalization()))
				.setSortable(true);
		grid
				.addColumn(model -> getCellString("DESCRIPTION", model, () -> model.getDescription()))
				.setHeader(
						resourceManager
								.getLocalizedString(
										"ProjectLinkListDetailsLayout.grid.header.description.label",
										session.getLocalization()))
				.setSortable(true);
		if (!model.getProjectLinks().isEmpty() && (model.getProjectLinks().get(0) instanceof Comparable)) {
			grid.setItems(model.getProjectLinks().stream().sorted().collect(Collectors.toList()));
		} else {
			grid.setItems(model.getProjectLinks());
		}
		grid.setWidthFull();
		setMargin(false);
		setPadding(false);
		add(buttons, grid);
	}

	private Object getCellString(String fieldName, ProjectLink aTable, Supplier<?> f) {
		return componentFactory.getProjectLinkMasterDataGridFieldRenderer() != null
				&& componentFactory.getProjectLinkMasterDataGridFieldRenderer().hasRenderingFor(fieldName)
						? componentFactory
								.getProjectLinkMasterDataGridFieldRenderer()
								.getHeaderString(fieldName, aTable)
						: f.get();
	}

}