package com.pb.dashboard.vitrina.chart.data;

import com.vaadin.data.Item;
import com.vaadin.ui.Component;

import java.util.List;

public interface Sample<T> {
    /**
     * Get a Component wrapped to a desired layout. Can be a composite of
     * multiple components. The Sampler doesn't handle wrapping itself.
     */
    Component getWrappedComponent();

    /**
     * Get the bean whose properties are manipulated trough propertyeditor.<br/>
     * <br/>
     * If returns null, property editor will not be enabled for the sample.
     */
    T getComponentUsedByPropertyEditor();

    /**
     * Get the same item of the Component used by property editor and possible
     * extra control components
     */
    Item getSampleItem();

    /**
     * Get the property ids exluded in property editor.
     */
    List<?> getPropertyEditorExcludedList();

    /**
     * Get control Components/Fields for highlighted features for the property
     * editor.
     */
    List<Component> getExtraComponentsForPropertyEditor();

    /**
     * Invoked by Sampler when the sample is displayed.
     */
    void sampleOpened();

}