package de.ollie.agrippa.gui.vaadin.component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;

import de.ollie.agrippa.core.service.localization.ResourceManager;
import de.ollie.agrippa.gui.SessionData;

import lombok.Generated;

/**
 * A simple selection dialog.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public class SelectionDialog extends Dialog {

	public interface Observer {

		void select(Selectable selectable);

	}

	public interface Selectable {

		String getLabel();

	}

	private Button buttonCancel;
	private Button buttonSelect;
	private ComboBox<Selectable> comboBoxSelectable;
	private Observer observer;

	public SelectionDialog(ButtonFactory buttonFactory, Observer observer, ResourceManager resourceManager,
			SessionData session, Selectable... selectables) {
		this.observer = observer;
		buttonCancel =
				buttonFactory
						.createButton(
								resourceManager
										.getLocalizedString(
												"SelectionDialog.button.cancel.label",
												session.getLocalization()));
		buttonCancel.addClickListener(event -> cancel());
		buttonCancel.setWidthFull();
		buttonSelect =
				buttonFactory
						.createButton(
								resourceManager
										.getLocalizedString(
												"SelectionDialog.button.select.label",
												session.getLocalization()));
		buttonSelect.addClickListener(event -> select(comboBoxSelectable.getValue()));
		buttonSelect.setWidthFull();
		comboBoxSelectable = new ComboBox<Selectable>();
		comboBoxSelectable.setItemLabelGenerator(selectable -> selectable.getLabel());
		comboBoxSelectable.setItems(getItemsOrdered(selectables));
		comboBoxSelectable.setWidthFull();
		add(comboBoxSelectable, buttonCancel, buttonSelect);
	}

	private Selectable[] getItemsOrdered(Selectable... selectables) {
		return Stream
				.of(selectables)
				.sorted((s0, s1) -> s0.getLabel().compareTo(s1.getLabel()))
				.collect(Collectors.toList())
				.toArray(new Selectable[selectables.length]);
	}

	public void cancel() {
		close();
	}

	public void select(Selectable selected) {
		if (observer != null) {
			observer.select(selected);
		}
		close();
	}

}