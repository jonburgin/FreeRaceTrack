package net.burgin.racetrack.gui.editablelist;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.*;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by jonburgin on 12/15/15.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DefaultEditableListModel<T> extends AbstractListModel<T> implements EditableListModel<T> {
    Supplier<List<T>> supplier;

    public DefaultEditableListModel(Supplier<List<T>> supplier){
        this.supplier = supplier;
    }

    public void setSupplier(Supplier<List<T>> supplier){
        this.supplier = supplier;
        fireContentsChanged(this,0,supplier.get().size());
    }

    @Override
    public void update(T t) {
        List<T> tees = supplier.get();
        boolean isNewItem = !tees.contains(t);
        if(isNewItem){
            tees.add(t);
            SwingUtilities.invokeLater(()->fireIntervalAdded(this, tees.size() - 1, tees.size()));
            return;
        }
        int index = tees.indexOf(t);
        SwingUtilities.invokeLater(()->fireContentsChanged(this,index, index+1));
        //TODO elements by name or something

    }

    public void remove(T t){
        List<T> tees = supplier.get();
        int index = tees.indexOf(t);
        if(index == -1)
            return;
        tees.remove(t);
        SwingUtilities.invokeLater(()->fireIntervalRemoved(this,index,index+1));
    }

    @Override
    public int getSize() {
        return supplier.get().size();
    }

    @Override
    public T getElementAt(int i) {
        List<T> tees = supplier.get();
        if(i < 0 || i > tees.size())
            return null;
        return tees.get(i);
    }

}
