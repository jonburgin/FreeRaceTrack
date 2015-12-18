package net.burgin.racetrack.gui;

import net.burgin.racetrack.RaceTrackResourceBundle;
import net.burgin.racetrack.domain.*;
import net.burgin.racetrack.gui.actions.AddCarAction;
import net.burgin.racetrack.gui.actions.AddRacerAction;
import net.burgin.racetrack.gui.actions.AddRaceAction;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jonburgin on 12/4/15.
 */
public class EventInternalFrame extends JInternalFrame {
    RaceEvent raceEvent;
    JList<Car> carsList = new JList<>();
    JLabel carNameLabel = new JLabel("Speedster");
    JLabel ownerNameLabel = new JLabel("Duke");
    JLabel carClassLabel = new JLabel("Tiger");
    ImageIcon personImageIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getSystemResource("images/duke.gif"));
    ImageIcon carImageIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getSystemResource("images/pinewood-derby.jpg"));

    public EventInternalFrame(RaceEvent raceEvent){
        if(raceEvent == null)throw new IllegalArgumentException("RaceEvent may not be null!");
        this.raceEvent = raceEvent;
        setTitle(raceEvent.getName());
        setResizable(true);
        setIconifiable(true);
        setClosable(true);
        buildGui();
    }

    private void buildGui() {
        this.setLayout(new BorderLayout());
        add(buildCenterPanel(), BorderLayout.CENTER);
        pack();
    }

    private Component buildCenterPanel() {
        JTabbedPane pane = new JTabbedPane();
        pane.addTab(RaceTrackResourceBundle.getInstance().getString("participantTabTitle"), buildRacerTab());
        pane.addTab(RaceTrackResourceBundle.getInstance().getString("raceTabTitle"), buildRacePanel());
        return pane;
    }

    private Component buildRacerTab() {
        JPanel panel = new JPanel(new BorderLayout());
        RacerEditPanel racerEditPanel = new RacerEditPanel();
        EditableList<Racer> list
                = new EditableList<>(new DefaultEditableListModel(()->raceEvent.getRacers()),
                racerEditPanel);
        JButton jButton = new JButton("New...");
        jButton.addActionListener(actionEvent -> list.update(new Racer("New","Racer")));
        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.add(jButton,BorderLayout.NORTH);
        panel2.add(new JScrollPane(list), BorderLayout.CENTER);
        panel.add(panel2, BorderLayout.CENTER);
        panel.add(racerEditPanel, BorderLayout.EAST);
        return panel;
    }


    private Component buildRacePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.BOTH;
        gc.gridx=0;
        gc.gridy=0;
        panel.add(buildRaceListPanel(),gc);
        gc.gridx++;
        panel.add(buildCarListPanel(),gc);
        gc.gridx++;
        panel.add(buildCarDetailPanel(), gc);
        return panel;
    }

    private Component buildCarDetailPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(RaceTrackResourceBundle.getInstance().getString("car.detail.title")));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(3,3,3,3);
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.gridx=0;
        gc.gridy=0;
        panel.add(new JLabel(RaceTrackResourceBundle.getInstance().getString("name")),gc);
        gc.gridx++;
        panel.add(carNameLabel,gc);
        gc.gridx++;
        gc.gridwidth=GridBagConstraints.REMAINDER;
        gc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JPanel());
        gc.fill = GridBagConstraints.NONE;
        gc.gridwidth = GridBagConstraints.RELATIVE;
        //----
        gc.gridx = 0;
        gc.gridy++;
        panel.add(new JLabel(RaceTrackResourceBundle.getInstance().getString("class")),gc);
        gc.gridx++;
        panel.add(carClassLabel, gc);
        //-----
        gc.gridx = 0;
        gc.gridy++;
        gc.weightx = 1;
        gc.gridwidth=3;
        gc.fill = GridBagConstraints.BOTH;
        panel.add(new JLabel(carImageIcon), gc);
        gc.fill = GridBagConstraints.NONE;
        gc.gridwidth=1;
        gc.weightx = 0;
        //----
        gc.gridx=0;
        gc.gridy++;
        panel.add(new JLabel(RaceTrackResourceBundle.getInstance().getString("racer")),gc);
        gc.gridx++;
        panel.add(ownerNameLabel,gc);
        gc.weightx = 1;
        gc.gridwidth = GridBagConstraints.RELATIVE;
        //---
        gc.gridx=0;
        gc.gridy++;
        gc.gridwidth=3;
        gc.fill = GridBagConstraints.BOTH;
        panel.add(new JLabel(personImageIcon), gc);
        //--
        gc.gridy++;
        gc.weighty = 1;
        gc.gridheight=GridBagConstraints.REMAINDER;
        panel.add(new JPanel(),gc);
        return panel;
    }

    private Component buildCarListPanel() {
        Box box = Box.createVerticalBox();
        box.setBorder(BorderFactory.createTitledBorder(RaceTrackResourceBundle.getInstance().getString("car.title")));
        box.add(new JScrollPane(carsList));
        return box;
    }

    private Component buildRaceListPanel() {
        Box box = Box.createVerticalBox();
        box.setBorder(BorderFactory.createTitledBorder(RaceTrackResourceBundle.getInstance().getString("race.title")));
        buildDefaultTemplate();
        JTree tree = new JTree(new RaceEventTreeModel(raceEvent));
        tree.setRootVisible(false);
        box.add(new JScrollPane(tree));
        return box;
    }

    private void buildDefaultTemplate() {
        Runoff championship = new Runoff("Grand Championship", 2);
        raceEvent.addRace(championship);
        championship.addRace(new Race("Tiger's and Wolves", "Tigers", "Wolves"));
        championship.addRace(new Race("Bears", "Bears"));
        championship.addRace(new Race("Webelos I", "Webelos I"));
        championship.addRace(new Race("Webelos II", "Webelos II"));
        championship.addRace(new Race("Open", "Open"));
    }
}
