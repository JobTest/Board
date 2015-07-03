package com.pb.dashboard.monitoring.timings.chooser;

import com.vaadin.data.Property;
import com.vaadin.ui.ComboBox;

/**
 * Created by vlad
 * Date: 20.11.14_10:23
 */
public class Chooser extends ComboBox {

    public static final ChooserItem ITEM_DEFAULT = ChooserItem.ARITHMETIC;
    private ChooserListener listener;

    public Chooser() {
        init();
    }

    private void init() {
        removeAllItems();
        setNullSelectionAllowed(false);
        setImmediate(true);
        setTextInputAllowed(false);
        initItems();
        setValue(ITEM_DEFAULT);
        addValueChangeListener(createValueChangeListener());
    }

    private void initItems() {
        for (ChooserItem item : ChooserItem.values()) {
            addItem(item);
        }
    }

    private ValueChangeListener createValueChangeListener() {
        return new ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                ChooserItem value = (ChooserItem) getValue();
                if (listener != null) {
                    listener.change(value);
                }
            }
        };
    }

    public void setListener(ChooserListener listener) {
        this.listener = listener;
    }
}