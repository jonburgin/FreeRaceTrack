package net.burgin.racetrack.gui.editablelist;

/**
 * Created by jonburgin on 12/16/15.
 */
public interface Editor<T> {
    void setEnabled(boolean enabled);
    void edit(T t);
    void addEditUpdateListener(EditUpdateListener listener);
    void removeEditUpdateListener(EditUpdateListener listener);
}
