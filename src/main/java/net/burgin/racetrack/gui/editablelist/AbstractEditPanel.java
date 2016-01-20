package net.burgin.racetrack.gui.editablelist;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

/**
 * Created by jonburgin on 12/16/15.
 */
@Data
@EqualsAndHashCode(callSuper = false)
abstract public class AbstractEditPanel<T> extends JPanel implements Editor<T> {

    protected boolean dirty;
    protected T t;
    protected java.util.List<EditUpdateListener> updateListeners = new ArrayList<>();

    @Override
    public void setEnabled(boolean enable){
        enableComponents(this, enable);
    }
    public void enableComponents(Container container, boolean enable) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            if(component instanceof Editor)
                continue;
            component.setEnabled(enable);
            if (component instanceof Container) {
                enableComponents((Container)component, enable);
            }
        }
    }

    @Override
    public void addEditUpdateListener(EditUpdateListener listener) {
        updateListeners.add(listener);
    }

    @Override
    public void removeEditUpdateListener(EditUpdateListener listener) {
        updateListeners.remove(listener);
    }

    protected abstract void populateT();
    protected abstract void populateFields();
    protected abstract void createT();

    @Override
    public void edit(T t) {
        notifyEditUpdateListeners();
        this.t = t;
        dirty = false;
        if(this.t == null) {
            createT();
        }
        populateFields();
    }

    protected void notifyEditUpdateListeners(){
        if(dirty == false)
            return;
        dirty = false;
        populateT();
        updateListeners.stream()
                .forEach(l->l.update(t));
    }

    public class NotifierListener implements FocusListener, ActionListener {
        @Override
        public void focusGained(FocusEvent focusEvent) {
            dirty = true;
        }

        @Override
        public void focusLost(FocusEvent focusEvent) {
            notifyEditUpdateListeners();
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            notifyEditUpdateListeners();
        }

    }
}
