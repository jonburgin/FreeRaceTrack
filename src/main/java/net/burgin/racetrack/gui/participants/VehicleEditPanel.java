package net.burgin.racetrack.gui.participants;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.burgin.racetrack.RaceTrackResourceBundle;
import net.burgin.racetrack.domain.RaceEvent;
import net.burgin.racetrack.domain.Vehicle;
import net.burgin.racetrack.gui.GreenScreenPicturePanel;
import net.burgin.racetrack.gui.RaceTrackImageUtils;
import net.burgin.racetrack.gui.adapters.EditPanelPictureTakerAdapter;
import net.burgin.racetrack.gui.editablelist.AbstractEditPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by jonburgin on 12/16/15.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class VehicleEditPanel extends AbstractEditPanel<Vehicle> {
    private final RaceEvent raceEvent;
    private JTextField nameTextField;
    JComboBox<String> raceClassComboBox;
    String photoTitle;
    String nameTitle;
    JLabel vehicleImageLabel = new JLabel();
    private String vehicleTitle;
    private String vehicleRaceClassificationTitle;

    public VehicleEditPanel(RaceEvent raceEvent){
        this.raceEvent = raceEvent;
        createT();
        loadResources();
        buildGui();
        setEnabled(false);
    }

    void buildGui(){
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx=0;
        c.gridy=0;
        add(buildNamePanel(),c);
        c.gridx=0;
        c.gridy++;
        c.fill = GridBagConstraints.BOTH;
        c.weightx=1;
        c.weighty=1;
        vehicleImageLabel.setIcon(RaceTrackImageUtils.getImageIcon(t));
        vehicleImageLabel.addMouseListener(new EditPanelPictureTakerAdapter(this,vehicleImageLabel));
        add(vehicleImageLabel,c);
    }

    private JComponent buildNamePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3,3,3,3);
        c.gridx=0;
        c.gridy=0;
        panel.add(new JLabel(nameTitle),c);
        nameTextField = new JTextField(12);
        NotifierListener notifierListener = new NotifierListener();
        nameTextField.addActionListener(notifierListener);
        nameTextField.addFocusListener(notifierListener);
        c.gridx++;
        c.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(nameTextField,c);
        c.gridx=0;
        c.gridy++;
        c.gridwidth = GridBagConstraints.RELATIVE;
        panel.add(new JLabel(vehicleRaceClassificationTitle),c);
        c.gridx++;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        //todo dropdown for classification properly
        raceClassComboBox = new JComboBox<>(new Vector<>(raceEvent.getCompetitionClasses()));
        raceClassComboBox.addItemListener(itemEvent-> {
                if(itemEvent.getStateChange()==ItemEvent.SELECTED)
                    dirty = true;
                    t.setCompetitionClass(itemEvent.getItem().toString());
                    SwingUtilities.invokeLater(()->notifyEditUpdateListeners());
            });
        panel.add(raceClassComboBox,c);
        return panel;
    }

    void loadResources() {
        vehicleRaceClassificationTitle = RaceTrackResourceBundle.getInstance().getString("vehicle.class");
        photoTitle = RaceTrackResourceBundle.getInstance().getString("image");
        vehicleTitle = RaceTrackResourceBundle.getInstance().getString("vehicle");
        nameTitle = RaceTrackResourceBundle.getInstance().getString("nameLabel");
    }

    @Override
    protected void createT(){
        t = new Vehicle();
    }

    @Override
    protected void populateT() {
        t.setName(nameTextField.getText());
        t.setCompetitionClass(raceClassComboBox.getSelectedItem().toString());
    }

    @Override
    protected void populateFields(){
        vehicleImageLabel.setIcon(RaceTrackImageUtils.getImageIcon(t));
        nameTextField.setText(t.getName());
        String competitionClass = t.getCompetitionClass();
        if(competitionClass !=null && competitionClass.length()>0)
            raceClassComboBox.setSelectedItem(competitionClass);
    }

}
