package de.ollie.agrippa.gui.vaadin.masterdata.layout.list;

import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import de.ollie.agrippa.core.model.Note;
import de.ollie.agrippa.core.model.Task;
import de.ollie.agrippa.core.service.localization.ResourceManager;
import de.ollie.agrippa.gui.SessionData;
import de.ollie.agrippa.gui.vaadin.component.ComponentFactory;
import de.ollie.agrippa.gui.vaadin.component.ServiceProvider;
import de.ollie.agrippa.gui.vaadin.masterdata.MasterDataGUIConfiguration;
import de.ollie.agrippa.gui.vaadin.masterdata.dialog.NoteDetailsDialog;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

/**
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@RequiredArgsConstructor
public class NoteListDetailsLayout extends VerticalLayout {

    private final ComponentFactory componentFactory;
    private final MasterDataGUIConfiguration guiConfiguration;
    private final Task model;
    private final ResourceManager resourceManager;
    private final ServiceProvider serviceProvider;
    private final SessionData session;

    private Grid<Note> grid;

    @Override
    public void onAttach(AttachEvent attachEvent) {
        Button buttonAdd = componentFactory.createAddButton(event -> {
            new NoteDetailsDialog(componentFactory, guiConfiguration, (mmbr, newItem) -> {
                model.getNotes().add(mmbr);
                grid.setItems(model.getNotes());
            }, session, new Note(), serviceProvider, true, model).open();
        }, session);
        Button buttonDuplicate = componentFactory.createDuplicateButton(event -> {
            new NoteDetailsDialog(componentFactory, guiConfiguration, (mmbr, newItem) -> {
                model.getNotes().add(mmbr);
                grid.setItems(model.getNotes());
            }, session, grid.getSelectedItems().toArray(new Note[0])[0], serviceProvider, true, model).open();
        }, session);
        Button buttonEdit = componentFactory.createEditButton(event -> {
            new NoteDetailsDialog(componentFactory, guiConfiguration, (toEdit, newItem) -> {
                Note mmbr = grid.getSelectedItems().toArray(new Note[0])[0];
                mmbr.setTitle(toEdit.getTitle());
                mmbr.setCreationDate(toEdit.getCreationDate());
                mmbr.setUrl(toEdit.getUrl());
                mmbr.setType(toEdit.getType());
                mmbr.setRelatedTodo(toEdit.getRelatedTodo());
                mmbr.setDescription(toEdit.getDescription());
                grid.setItems(model.getNotes());
            }, session, grid.getSelectedItems().toArray(new Note[0])[0], serviceProvider, false, model).open();
        }, session);
        Button buttonRemove = componentFactory.createRemoveButton(event -> {
            Note mmbr = grid.getSelectedItems().toArray(new Note[0])[0];
            model.getNotes().remove(mmbr);
            grid.setItems(model.getNotes());
        }, session);
        HorizontalLayout buttons = new HorizontalLayout(buttonAdd, buttonEdit, buttonDuplicate, buttonRemove);
        grid = new Grid<>();
        grid.addColumn(model -> getCellString("TITLE", model, () -> model.getTitle()))
                .setHeader(resourceManager.getLocalizedString("NoteListDetailsLayout.grid.header.title.label",
                        session.getLocalization()))
                .setSortable(true);
        grid.addColumn(model -> getCellString("CREATIONDATE", model, () -> model.getCreationDate()))
                .setHeader(resourceManager.getLocalizedString("NoteListDetailsLayout.grid.header.creationdate.label",
                        session.getLocalization()))
                .setSortable(true);
        grid.addColumn(model -> getCellString("URL", model, () -> model.getUrl()))
                .setHeader(resourceManager.getLocalizedString("NoteListDetailsLayout.grid.header.url.label",
                        session.getLocalization()))
                .setSortable(true);
        grid.addColumn(model -> getCellString("TYPE", model, () -> model.getType()))
                .setHeader(resourceManager.getLocalizedString("NoteListDetailsLayout.grid.header.type.label",
                        session.getLocalization()))
                .setSortable(true);
        grid.addColumn(model -> getCellString("RELATEDTODO", model, () -> model.getRelatedTodo()))
                .setHeader(resourceManager.getLocalizedString("NoteListDetailsLayout.grid.header.relatedtodo.label",
                        session.getLocalization()))
                .setSortable(true);
        grid.addColumn(model -> getCellString("DESCRIPTION", model, () -> model.getDescription()))
                .setHeader(resourceManager.getLocalizedString("NoteListDetailsLayout.grid.header.description.label",
                        session.getLocalization()))
                .setSortable(true);
        if (!model.getNotes().isEmpty() && (model.getNotes().get(0) instanceof Comparable)) {
            grid.setItems(model.getNotes().stream().sorted().collect(Collectors.toList()));
        } else {
            grid.setItems(model.getNotes());
        }
        grid.setWidthFull();
        setMargin(false);
        setPadding(false);
        add(buttons, grid);
    }

    private Object getCellString(String fieldName, Note aTable, Supplier<?> f) {
        return componentFactory.getNoteMasterDataGridFieldRenderer() != null
                && componentFactory.getNoteMasterDataGridFieldRenderer().hasRenderingFor(fieldName)
                        ? componentFactory.getNoteMasterDataGridFieldRenderer().getHeaderString(fieldName, aTable)
                        : f.get();
    }

}