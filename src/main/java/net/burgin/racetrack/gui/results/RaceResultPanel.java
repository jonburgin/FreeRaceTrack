package net.burgin.racetrack.gui.results;

import net.burgin.racetrack.domain.RaceEvent;
import net.burgin.racetrack.domain.RaceResult;
import net.burgin.racetrack.gui.actions.GenerateRaceResultsAction;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jonburgin on 1/21/16.
 */
public class RaceResultPanel extends JPanel {

    private JList<RaceResult> list;
    private ResultsListModel model;

    public RaceResultPanel(RaceEvent raceEvent){
        model = new ResultsListModel(raceEvent);
        buildGui();
    }

    private void buildGui() {
        setLayout(new BorderLayout());
        add(buildToolBar(), BorderLayout.NORTH);
        add(buildCenterPanel(), BorderLayout.CENTER);
    }

    private Component buildToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.add(new GenerateRaceResultsAction(model));
        return toolBar;
    }

    private Component buildCenterPanel(){
        JPanel panel = new JPanel(new BorderLayout());
        list = new JList<>(model);
        //todo add mouselistener for doing results presentation
        list.setCellRenderer(new ResultsCellRenderer());
        panel.add(new JScrollPane(list));
        return panel;
    }

    protected class ResultsCellRenderer extends DefaultListCellRenderer{
        @Override
        public Component getListCellRendererComponent(JList<?> jList, Object o, int i, boolean b, boolean b1) {
            return super.getListCellRendererComponent(jList, model.toString((RaceResult)o), i, b, b1);

        }
    }
}
