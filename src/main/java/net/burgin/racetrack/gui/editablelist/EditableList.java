package net.burgin.racetrack.gui.editablelist;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by jonburgin on 12/16/15.
 */
public class EditableList<T> extends JList<T> implements EditUpdateListener<T> {
    private final Editor<T> editor;
    EditableListModel<T> editableListModel;
    public EditableList(EditableListModel<T> model, Editor<T> editor){
        super((ListModel)model);
        this.editor = editor;
        editor.addEditUpdateListener(this);
        setModel(model);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       // setCellRenderer(new EditableListCellRenderer());
        addDefaultListeners();
        setSelectedIndex(0);
    }

    private void addDefaultListeners() {

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if(mouseEvent.isPopupTrigger()){
                    doPopup(mouseEvent);
                }
            }
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                if(mouseEvent.isPopupTrigger()){
                    doPopup(mouseEvent);
                }
            }
            void doPopup(MouseEvent mouseEvent){
                int index = locationToIndex(mouseEvent.getPoint());
                setSelectedIndex(index);
                JPopupMenu menu = new JPopupMenu();
                JMenuItem menuItem = new JMenuItem("Delete");
                menu.add(menuItem);
                menuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        editableListModel.remove(getSelectedValue());
                    }
                });
                menu.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
            }
        });

        addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if(listSelectionEvent.getFirstIndex() != -1)
                    SwingUtilities.invokeLater(()->{
                        editor.edit(getSelectedValue());
                        editor.setEnabled(true);
                    });
                else
                    SwingUtilities.invokeLater(()->editor.setEnabled(false));
            }
        });
    }

    @Override
    public void setModel(ListModel<T> listModel) {
        editableListModel =(EditableListModel<T>)listModel;
        super.setModel(listModel);
    }

    public void update(T t){
        editableListModel.update(t);
        SwingUtilities.invokeLater(()->setSelectedValue(t,true));
    }

    public void remove(T t){
        editableListModel.remove(t);
    }
}
