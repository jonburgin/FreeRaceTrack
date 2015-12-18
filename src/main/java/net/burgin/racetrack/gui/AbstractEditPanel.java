package net.burgin.racetrack.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

/**
 * Created by jonburgin on 12/16/15.
 */
abstract public class AbstractEditPanel<T> extends JPanel implements Editor<T> {

    boolean dirty;
    T t;
    java.util.List<EditUpdateListener> updateListeners = new ArrayList<>();

    public void Edit(T t){
        this.t = t;
        if(t == null)
            grabFocus();
    }

    @Override
    public void addEditUpdateListener(EditUpdateListener listener) {
        updateListeners.add(listener);
    }

    @Override
    public void removeEditUpdateListener(EditUpdateListener listener) {
        updateListeners.remove(listener);
    }

    abstract void populateT();
    abstract void populateFields(T t);
    abstract void createT();

    public void edit(T t) {
        notifyEditUpdateListeners();
        this.t = t;
        dirty = false;
        if(this.t == null) {
            createT();
        }
        populateFields(this.t);
    }

    void notifyEditUpdateListeners(){
        if(dirty == false)
            return;
        dirty = false;
        populateT();
        updateListeners.stream()
                .forEach(l->l.update(t));
    }


    class NotifierListener implements FocusListener, ActionListener {
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
