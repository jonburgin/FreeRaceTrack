package net.burgin.racetrack.gui;

import lombok.Data;
import net.burgin.racetrack.domain.*;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.*;
import java.util.function.Consumer;

/**
 * Created by jonburgin on 12/4/15.
 */
@Data
public class RaceEventTreeModel implements TreeModel, RaceEventChangeListener{
    RaceEvent raceEvent;
    private List<TreeModelListener>treeModelListeners = new ArrayList<>();
    private Map<Object, Object[]> pathMap = new HashMap<>();

    public RaceEventTreeModel(RaceEvent raceEvent){
        this.raceEvent = raceEvent;
        raceEvent.addRaceEventChangeListener(this);
    }

    @Override
    public Object getRoot() {
        return raceEvent;
    }

    @Override
    public Object getChild(Object parent, int i) {
        if(parent == raceEvent){
            return (i >= 0) && (i < raceEvent.getRaces().size())?raceEvent.getRaces().get(i):null;
        }
        if(!(parent instanceof Runoff))
            return null;
        List<Race> childRaces = ((Runoff) parent).getChildRaces();
        return (i >= 0) && (i < childRaces.size())? childRaces.get(i):null;
    }

    @Override
    public int getChildCount(Object parent) {
        if(parent == raceEvent)
            return raceEvent.getRaces().size();
        if(parent instanceof Runoff)
            return ((Runoff)parent).getChildRaces().size();
        return 0;
    }

    @Override
    public boolean isLeaf(Object o) {
        return o instanceof Race && !(o instanceof Runoff);
    }

    @Override
    public void valueForPathChanged(TreePath treePath, Object o) {
        //this should not happen....I think.
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        if(parent == null || child == null)
            return -1;
        if(parent == raceEvent)
            return raceEvent.getRaces().indexOf(child);
        if(parent instanceof Runoff && ((Runoff)parent).getRaceEvent() == raceEvent )
            return ((Runoff)parent).getChildRaces().indexOf(child);
        return -1;
    }

    @Override
    public void addTreeModelListener(TreeModelListener treeModelListener) {
        treeModelListeners.add(treeModelListener);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener treeModelListener) {
        treeModelListeners.remove(treeModelListener);
    }

    @Override
    public void raceAdded(Object parent, Race race, int index) {
        Object[] tempPath = pathMap.get(parent);
        Object[] parentPath = tempPath != null?tempPath:new Object[]{parent};
        pathMap.put(parent,parentPath);
        Object[] childPath = Arrays.copyOf(parentPath,parentPath.length+1);
        childPath[childPath.length - 1] = race;
        pathMap.put(race,childPath);
        TreeModelEvent treeModelEvent = new TreeModelEvent(this, parentPath, new int[]{index}, new Object[]{race});
        Consumer<TreeModelListener> c = l->l.treeNodesInserted(treeModelEvent);
        SwingUtilities.invokeLater(new TreeNotifierRunnable(c));
    }

    @Override
    public void raceRemoved(Object parent, Race race, int index) {
        Object[] parentPath = pathMap.get(parent);
        TreeModelEvent treeModelEvent = new TreeModelEvent(this, parentPath, new int[]{index}, new Object[]{race});
        Consumer<TreeModelListener> c = l->l.treeNodesRemoved(treeModelEvent);
        SwingUtilities.invokeLater(new TreeNotifierRunnable(c));
    }

    @Override
    public void raceChanged(Race race){
        Object[] childPath = pathMap.get(race);
        Object parent = childPath[childPath.length - 2];
        int index = ((RaceParent)parent).indexOf(race);
        Object[] parentPath = pathMap.get(parent);
        Consumer<TreeModelListener> c = l->l.treeNodesChanged(new TreeModelEvent(this, parentPath,new int[]{index},new Object[]{race}));
        SwingUtilities.invokeLater(new TreeNotifierRunnable(c));
    }
    private class TreeNotifierRunnable implements Runnable{
        private final Consumer<TreeModelListener> consumer;

        public TreeNotifierRunnable(Consumer<TreeModelListener> consumer){
            this.consumer = consumer;
        }

        @Override
        public void run() {
            treeModelListeners.stream().forEach(consumer);
        }
    }

}
