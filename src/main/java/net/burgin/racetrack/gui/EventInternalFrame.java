package net.burgin.racetrack.gui;

import net.burgin.racetrack.RaceTrackResourceBundle;
import net.burgin.racetrack.domain.Car;
import net.burgin.racetrack.domain.Runoff;
import net.burgin.racetrack.domain.Race;
import net.burgin.racetrack.domain.RaceEvent;
import net.burgin.racetrack.gui.actions.AddCarAction;
import net.burgin.racetrack.gui.actions.AddOwnerAction;
import net.burgin.racetrack.gui.actions.AddRaceAction;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by jonburgin on 12/4/15.
 */
public class EventInternalFrame extends JInternalFrame {
    RaceEvent raceEvent;

    JList<Car> carsList = new JList<>();
    JLabel carNameLabel = new JLabel("Speedster");
    JLabel ownerNameLabel = new JLabel("Duke");
    ImageIcon carImageIcon = new ImageIcon("images/duke.gif");

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
        add(buildEventToolBar(), BorderLayout.NORTH);
        add(buildCenterPanel(), BorderLayout.CENTER);
        pack();
    }

    private Component buildCenterPanel() {
        Box box = Box.createHorizontalBox();
        box.add(buildRaceListPanel());
        box.add(buildCarListPanel());
        box.add(buildCarDetailPanel());
        return box;
    }

    private Component buildCarDetailPanel() {
        Box nameBox = Box.createHorizontalBox();
        nameBox.add(new JLabel(RaceTrackResourceBundle.getInstance().getString("name")));
        nameBox.add(Box.createHorizontalStrut(5));
        carNameLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        nameBox.add(carNameLabel);
        nameBox.add(Box.createGlue());
        nameBox.setAlignmentY(TOP_ALIGNMENT);
        Box ownerBox = Box.createHorizontalBox();
        ownerBox.add(new JLabel(RaceTrackResourceBundle.getInstance().getString("owner")));
        ownerBox.add(Box.createHorizontalStrut(5));
        ownerNameLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        ownerBox.add(ownerNameLabel);
        ownerBox.add(Box.createGlue());
        ownerBox.setAlignmentY(TOP_ALIGNMENT);
        Box box = Box.createVerticalBox();
        box.setBorder(BorderFactory.createTitledBorder(RaceTrackResourceBundle.getInstance().getString("car.detail.title")));
        box.add(nameBox);
        box.add(ownerBox);
        box.add(new JLabel(carImageIcon));
        box.add(Box.createVerticalGlue());
        return box;
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

    private Component buildEventToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.add(new AddRaceAction());
        toolBar.add(new AddOwnerAction());
        toolBar.add(new AddCarAction());
        return toolBar;
    }
}
