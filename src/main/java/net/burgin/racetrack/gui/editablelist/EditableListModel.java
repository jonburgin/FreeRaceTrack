package net.burgin.racetrack.gui.editablelist;

/**
 * Created by jonburgin on 12/16/15.
 */
interface EditableListModel<T> extends javax.swing.ListModel<T> {
    public void update(T t);
    public void remove(T t);
}
