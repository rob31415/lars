/**
problem when having not a Set (but a List) in a BeanItemContainer for
AbstractSelect.

this supposed to be a workaround (for a vaadin from 2009).
as expected, it doesn't work.

http://www.vaadin.com/forum/-/message_boards/view_message/51462
http://www.vaadin.com/forum/-/message_boards/view_message/112684
*/

package com.vaadin.ui;

import java.util.*;
import com.vaadin.data.*;
import com.vaadin.data.Validator.*;
import com.vaadin.terminal.*;
import com.vaadin.terminal.gwt.client.ui.VTwinColSelect;


public class TwinColSelectEx extends TwinColSelect {

    private Class<?> type;
    private boolean isCommiting;

    public TwinColSelectEx(Class<?> type, String title) {
        super(title);
        this.type = type;
    }

    @Override
    public Object getValue() {
        final Object retValue = super.getValue();
        
        //System.out.println("TwinColSelectEx.getValue");

        // If the return value is not a set
        if (isCommiting && type != null && List.class.isAssignableFrom(type)) {
            if (retValue == null) {
                return new ArrayList(0);
            }
            if (retValue instanceof Set) {
                return Collections.unmodifiableList(new ArrayList((Set) retValue));
            } else if (retValue instanceof Collection) {
                return new ArrayList((Collection) retValue);
            } else {
                final List l = new ArrayList(1);
                if (items.containsId(retValue)) {
                    l.add(retValue);
                }
                return l;
            }
        }
        return retValue;
    }

    @Override
    public void commit() throws SourceException, InvalidValueException {
        isCommiting = true;
        try {
            super.commit();
        } finally {
            isCommiting = false;
        }
    }
}
