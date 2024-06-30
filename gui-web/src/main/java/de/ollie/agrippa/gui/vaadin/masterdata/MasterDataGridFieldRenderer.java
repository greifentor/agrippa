package de.ollie.agrippa.gui.vaadin.masterdata;

import lombok.Generated;

/**
 * An interface for master data grid field renderers.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
public interface MasterDataGridFieldRenderer<T> {

	Object getHeaderString(String fieldName, T t);

	boolean hasRenderingFor(String fieldName);

}