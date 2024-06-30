package de.ollie.agrippa.gui.vaadin.component;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import com.vaadin.flow.component.UI;

import de.ollie.agrippa.gui.SessionData;
import lombok.Generated;

/**
 * Can be implemented to add buttons to the master data view.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public interface MasterDataViewButtonAdder {

	List<Button> createButtonsToAdd(SessionData session, Supplier<Optional<UI>> uiSupplier);

}