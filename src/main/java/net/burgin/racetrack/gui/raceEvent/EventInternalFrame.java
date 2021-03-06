package net.burgin.racetrack.gui.raceEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.burgin.racetrack.RaceTrackResourceBundle;
import net.burgin.racetrack.domain.*;
import net.burgin.racetrack.gui.editablelist.DefaultEditableListModel;
import net.burgin.racetrack.gui.editablelist.EditableList;
import net.burgin.racetrack.gui.heats.HeatPanel;
import net.burgin.racetrack.gui.participants.ParticipantEditPanel;
import net.burgin.racetrack.gui.results.RaceResultPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jonburgin on 12/4/15.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EventInternalFrame extends JInternalFrame {
    String filePath;
    RaceEvent raceEvent;
    JList<Vehicle> vehicleList = new JList<>();
    JLabel vehicleNameLabel = new JLabel("Speedster");
    JLabel ownerNameLabel = new JLabel("Duke");
    JLabel vehicleClassLabel = new JLabel("Tiger");
    ImageIcon personImageIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getSystemResource("images/duke.gif"));
    ImageIcon vehicleImageIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getSystemResource("images/pinewood-derby.jpg"));

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
        pane.addTab(RaceTrackResourceBundle.getInstance().getString("participantTabTitle"), buldParticipantTab());
        pane.addTab(RaceTrackResourceBundle.getInstance().getString("raceTabTitle"), buildRacePanel());
        pane.addTab(RaceTrackResourceBundle.getInstance().getString("heatTabTitle"), buildHeatsPanel());
        pane.addTab(RaceTrackResourceBundle.getInstance().getString("resultTabTitle"), buildResultsPanel());
        return pane;
    }

    private Component buildHeatsPanel() {
        return new HeatPanel(raceEvent);
    }

    private Component buildResultsPanel() {
        return new RaceResultPanel(raceEvent);
    }

    private Component buldParticipantTab() {
        JPanel panel = new JPanel(new BorderLayout());
        ParticipantEditPanel participantEditPanel = new ParticipantEditPanel(raceEvent);
        EditableList<Participant> list
                = new EditableList<>(new DefaultEditableListModel(()->raceEvent.getParticipants()),
                participantEditPanel);

        JButton jButton = new JButton("New...");//// TODO: 12/26/15
        jButton.addActionListener(actionEvent -> list.update(new Participant("New","Racer")));//todo
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(jButton,BorderLayout.NORTH);
        listPanel.add(new JScrollPane(list), BorderLayout.CENTER);
        panel.add(listPanel, BorderLayout.CENTER);
        panel.add(participantEditPanel, BorderLayout.EAST);
        participantEditPanel.getVehicleEditPanel().addEditUpdateListener((car)->list.update(participantEditPanel.getT()));
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
        panel.add(buildVehicleListPanel(),gc);
        gc.gridx++;
        panel.add(buildVehicleDetailPanel(), gc);
        return panel;
    }

    private Component buildVehicleDetailPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(RaceTrackResourceBundle.getInstance().getString("vehicle.detail.title")));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(3,3,3,3);
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.gridx=0;
        gc.gridy=0;
        panel.add(new JLabel(RaceTrackResourceBundle.getInstance().getString("name")),gc);
        gc.gridx++;
        panel.add(vehicleNameLabel,gc);
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
        panel.add(vehicleClassLabel, gc);
        //-----
        gc.gridx = 0;
        gc.gridy++;
        gc.weightx = 1;
        gc.gridwidth=3;
        gc.fill = GridBagConstraints.BOTH;
        panel.add(new JLabel(vehicleImageIcon), gc);
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

    private Component buildVehicleListPanel() {
        Box box = Box.createVerticalBox();
        box.setBorder(BorderFactory.createTitledBorder(RaceTrackResourceBundle.getInstance().getString("vehicle.title")));
        box.add(new JScrollPane(vehicleList));
        return box;
    }

    private Component buildRaceListPanel() {
        Box box = Box.createVerticalBox();
        box.setBorder(BorderFactory.createTitledBorder(RaceTrackResourceBundle.getInstance().getString("race.title")));
      //  buildDefaultTemplate();
        JTree tree = new JTree(new RaceEventTreeModel(raceEvent));
        tree.setRootVisible(false);
        box.add(new JScrollPane(tree));
        return box;
    }
//
//    private void buildDefaultTemplate() {
//        DefaultRunoffRace championship = new DefaultRunoffRace("Grand Championship", 2);
//        championship.addRace(new DefaultSimpleRace("Tiger's and Wolves",true, "Tiger", "Wolf"));
//        championship.addRace(new DefaultSimpleRace("Bears", "Bear"));
//        championship.addRace(new DefaultSimpleRace("Webelos I", "Web I"));
//        championship.addRace(new DefaultSimpleRace("Webelos II", "Web II"));
//        championship.addRace(new DefaultSimpleRace("Open", "Open"));
//        raceEvent.addRace(championship);
//    }
}
