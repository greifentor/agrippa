package de.ollie.agrippa.gui.vaadin.component;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.QueryParameters;

import de.ollie.agrippa.core.service.localization.ResourceManager;
import de.ollie.agrippa.gui.SessionData;
import de.ollie.agrippa.gui.vaadin.UserAuthorizationChecker;

import lombok.Generated;

/**
 * A base class for master data base layouts.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public abstract class AbstractMasterDataBaseLayout extends Scroller implements BeforeEnterObserver, HasUrlParameter<String> {

	public interface Observer {
		void save(Object model);

		void remove();
	}

	private static final Logger LOG = LogManager.getLogger(AbstractMasterDataBaseLayout.class);

	protected Button buttonRemove;
	protected Button buttonSave;
	protected VerticalLayout mainLayout;

	protected Map<String, List<String>> parametersMap;

	protected abstract ButtonFactory getButtonFactory();

	protected abstract ResourceManager getResourceManager();

	protected abstract SessionData getSessionData();

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
	}

	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		LOG.info("setParameter");
		Location location = event.getLocation();
		QueryParameters queryParameters = location.getQueryParameters();
		parametersMap = queryParameters.getParameters();
		doSetParameter(event);
	}

	protected void doSetParameter(BeforeEvent event) {
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		LOG.info("check for authorization");
		UserAuthorizationChecker.forwardToLoginOnNoUserSetForSession(getSessionData(), beforeEnterEvent);
		createButtons();
		doBeforeEnter(beforeEnterEvent);
	}

	protected void createButtons() {
		buttonRemove = getButtonFactory().createRemoveButton(getResourceManager(), event -> remove(), getSessionData());
		buttonSave = getButtonFactory().createSaveButton(getResourceManager(), event -> save(), getSessionData());
	}

	protected abstract void remove();

	protected abstract void save();

	protected void doBeforeEnter(BeforeEnterEvent beforeEnterEvent) {
	}

	protected Checkbox createCheckbox(String resourceId, Boolean fieldContent) {
		Checkbox checkBox =
				new Checkbox(getResourceManager().getLocalizedString(resourceId, getSessionData().getLocalization()));
		checkBox.setValue(fieldContent);
		checkBox.setWidthFull();
		return checkBox;
	}

	protected <T> ComboBox<T> createComboBox(String resourceId, T fieldContent, T[] valuesToSelect) {
		return createComboBox(resourceId, fieldContent, valuesToSelect, null);
	}

	protected <T> ComboBox<T> createComboBox(String resourceId, T fieldContent, T[] valuesToSelect,
			ItemLabelGenerator<T> itemLabelGenerator) {
		ComboBox<T> comboBox =
				new ComboBox<>(getResourceManager().getLocalizedString(resourceId, getSessionData().getLocalization()));
		comboBox.setItems(valuesToSelect);
		comboBox.setValue(fieldContent);
		comboBox.setWidthFull();
		if (itemLabelGenerator != null) {
			comboBox.setItemLabelGenerator(itemLabelGenerator);
		}
		return comboBox;
	}

	protected IntegerField createIntegerField(String resourceId, Integer fieldContent, Integer min, Integer max) {
		IntegerField integerField =
				new IntegerField(
						getResourceManager().getLocalizedString(resourceId, getSessionData().getLocalization()));
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

	protected IntegerField createIntegerField(String resourceId, Integer fieldContent, Integer min, Integer max,
			Integer step) {
		IntegerField integerField =
				new IntegerField(
						getResourceManager().getLocalizedString(resourceId, getSessionData().getLocalization()));
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

	protected NumberField createNumberField(String resourceId, Double fieldContent, Double min, Double max,
			Double step) {
		NumberField integerField =
				new NumberField(
						getResourceManager().getLocalizedString(resourceId, getSessionData().getLocalization()));
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

	protected TextField createTextField(String resourceId, String fieldContent) {
		TextField textField =
				TextFieldFactory
						.createTextField(
								getResourceManager()
										.getLocalizedString(resourceId, getSessionData().getLocalization()));
		textField.setValue(fieldContent != null ? fieldContent : "");
		textField.setWidthFull();
		return textField;
	}

	protected TextArea createTextArea(String resourceId, String fieldContent) {
		TextArea textArea =
				new TextArea(getResourceManager().getLocalizedString(resourceId, getSessionData().getLocalization()));
		textArea.setValue(fieldContent != null ? fieldContent : "");
		textArea.setWidthFull();
		return textArea;
	}

	protected Button[] getButtons(boolean couldBeRemoved) {
		Button[] buttons = new Button[1 + (couldBeRemoved ? 1 : 0)];
		buttons[0] = buttonSave;
		if (couldBeRemoved) {
			buttons[1] = buttonRemove;
		}
		return buttons;
	}

	protected MasterDataButtonLayout getMasterDataButtonLayout(boolean couldBeRemoved) {
		return new MasterDataButtonLayout(getButtons(couldBeRemoved));
	}

	protected void setMargin(boolean b) {
		if (mainLayout != null) {
			mainLayout.setMargin(b);
		}
	}

	protected void add(Component... components) {
		if (mainLayout == null) {
			mainLayout = new VerticalLayout();
			setContent(mainLayout);
		}
		mainLayout.add(components);
	}

}