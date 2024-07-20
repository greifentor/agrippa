package de.ollie.agrippa.gui.vaadin.masterdata;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class PopupNotification {

	public static void showError(String errorText) {
		Notification notification = new Notification();
		notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
		Div text = createText(errorText);
		Button closeButton = createCloseButton(notification);
		HorizontalLayout layout = new HorizontalLayout(text, closeButton);
		layout.setAlignItems(Alignment.CENTER);
		notification.add(layout);
		notification.open();
	}

	private static Div createText(String errorText) {
		Div div = new Div();
		div.getElement().setProperty("innerHTML", "<span>" + errorText + "</span>");
		return div;
	}

	private static Button createCloseButton(Notification notification) {
		Button closeButton = new Button(new Icon("lumo", "cross"));
		closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
		// V24 closeButton.setAriaLabel("Close");
		closeButton.getElement().setAttribute("aria-label", "Close");
		closeButton.addClickListener(event -> {
			notification.close();
		});
		return closeButton;
	}

}
