package de.ollie.agrippa.gui.vaadin.component;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import de.ollie.agrippa.core.service.localization.ResourceManager;
import de.ollie.agrippa.gui.SessionData;
import lombok.Generated;

/**
 * A dialog to confirm data record remove actions.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public class RemoveConfirmDialog extends Dialog {

	public interface Observer {

		void confirmed();

	}

	private Button buttonCancel;
	private Button buttonConfirm;
	private Observer observer;

	public RemoveConfirmDialog(ButtonFactory buttonFactory, Observer observer, ResourceManager resourceManager,
			SessionData session) {
		this.observer = observer;
		buttonCancel =
				buttonFactory
						.createButton(
								resourceManager
										.getLocalizedString(
												"RemoveConfirmDialog.button.cancel.label",
												session.getLocalization()));
		buttonCancel.addClickListener(event -> cancel());
		buttonConfirm =
				buttonFactory
						.createButton(
								resourceManager
										.getLocalizedString(
												"RemoveConfirmDialog.button.confirm.label",
												session.getLocalization()));
		buttonConfirm.addClickListener(event -> confirmed());
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.add(buttonConfirm, buttonCancel);
		buttonLayout.setJustifyContentMode(JustifyContentMode.END);
		buttonLayout.setWidthFull();
		VerticalLayout layout = new VerticalLayout();
		layout.setWidthFull();
		layout
				.add(
						new Label(
								resourceManager
										.getLocalizedString("RemoveConfirmDialog.message.label", session.getLocalization())),
				new Label(""),
				buttonLayout);
		add(layout);
	}

	public void cancel() {
		close();
	}

	public void confirmed() {
		if (observer != null) {
			observer.confirmed();
		}
		close();
	}

}