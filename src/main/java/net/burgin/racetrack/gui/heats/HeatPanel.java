package net.burgin.racetrack.gui.heats;

import net.burgin.racetrack.domain.Heat;
import net.burgin.racetrack.domain.RaceEvent;
import net.burgin.racetrack.gui.actions.GenerateRaceHeatsAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by jonburgin on 12/24/15.
 */
public class HeatPanel extends JPanel {
    private final RaceEvent raceEvent;
    HeatListModel model;
    HeatRunner runner;
    JList<Heat> jList;

    public HeatPanel(RaceEvent raceEvent) {
        this.raceEvent = raceEvent;
        model = new HeatListModel(raceEvent);
        buildGui();
    }

    private void buildGui() {
        setLayout(new BorderLayout());
        add(buildToolBar(), BorderLayout.NORTH);
        add(buildCenterPanel(), BorderLayout.CENTER);
    }

    private Component buildToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.add(new GenerateRaceHeatsAction(model));
        return toolBar;
    }

    private Component buildCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        jList = new JList<>(model);
        jList.addMouseListener(new HeatMouseListener());
        jList.setCellRenderer(new HeatCellRenderer());
        panel.add(new JScrollPane(jList));
        return panel;
    }

    private void doRunner(Heat heat){
        if(runner == null){
            runner = new HeatRunner(raceEvent,heat);
        }else{
            runner.run(raceEvent,heat);
        }
    }

    private class HeatMouseListener extends MouseAdapter {


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
            jList.setSelectedIndex(jList.locationToIndex(mouseEvent.getPoint()));
            JPopupMenu menu = new JPopupMenu();
            if(jList.getSelectedValue().getStartTime() == 0) {
                JMenuItem menuItem = new JMenuItem("Run");//todo action and resource
                menu.add(menuItem);
                menuItem.addActionListener((event) -> doRunner(jList.getSelectedValue()));
            }else{
                JMenuItem menuItem = new JMenuItem("Reset");//todo action and resource
                menu.add(menuItem);
                menuItem.addActionListener((event) -> jList.getSelectedValue().reset());
            }
            menu.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
        }

    }

    private class HeatCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> jList, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel)super.getListCellRendererComponent(jList, value, index, isSelected, cellHasFocus);
            Heat heat = (Heat)value;
            if(heat.getStartTime() != 0 ){
                label.setText("\u2713 " + label.getText());
                label.setForeground(Color.lightGray);
            }
            return label;
        }
    }
}
