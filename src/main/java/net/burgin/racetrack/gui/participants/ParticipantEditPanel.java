package net.burgin.racetrack.gui.participants;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.burgin.racetrack.RaceTrackResourceBundle;
import net.burgin.racetrack.domain.RaceEvent;
import net.burgin.racetrack.domain.Vehicle;
import net.burgin.racetrack.domain.Participant;
import net.burgin.racetrack.gui.GreenScreenPicturePanel;
import net.burgin.racetrack.gui.RaceTrackImageUtils;
import net.burgin.racetrack.gui.adapters.EditPanelPictureTakerAdapter;
import net.burgin.racetrack.gui.editablelist.AbstractEditPanel;
import net.burgin.racetrack.gui.editablelist.DefaultEditableListModel;
import net.burgin.racetrack.gui.editablelist.EditableList;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jonburgin on 12/14/15.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ParticipantEditPanel extends AbstractEditPanel<Participant> {
    private final RaceEvent raceEvent;
    String firstNameLabel;
    String lastNameLabel;
    String title;
    private JTextField firstNameField = new JTextField(20);
    JTextField lastNameField = new JTextField(20);
    private String photoTitle;
    private String carTitle;
    private String nameTitle;
    private JLabel personImageLabel = new JLabel();
    NotifierListener notifierListener = new NotifierListener();
    DefaultEditableListModel<Vehicle> defaultEditableListModel
            = new DefaultEditableListModel<>(() -> new ArrayList<>());
    EditableList<Vehicle> carList;
    VehicleEditPanel vehicleEditPanel;

    public ParticipantEditPanel(RaceEvent raceEvent) {
        this.raceEvent = raceEvent;
        createT();
        defaultEditableListModel.setSupplier(()->t.getVehicles());
        defaultEditableListModel.addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent listDataEvent) {

            }

            @Override
            public void intervalRemoved(ListDataEvent listDataEvent) {

            }

            @Override
            public void contentsChanged(ListDataEvent listDataEvent) {
                DefaultEditableListModel source = (DefaultEditableListModel) listDataEvent.getSource();
                if(source.getSize() == 0 || carList.getSelectedValue() == null) {
                    vehicleEditPanel.createT();
                    vehicleEditPanel.populateFields();
                }else{
                    vehicleEditPanel.setT(carList.getSelectedValue());
                    vehicleEditPanel.populateFields();
                }
            }
        });
        buildGui();
        setEnabled(false);
    }

    private void buildGui() {
        getResources();
        setBorder(BorderFactory.createTitledBorder(RaceTrackResourceBundle.getInstance().getString("racer")));
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(2,2,2,2);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.fill=GridBagConstraints.BOTH;
        add(buildNamePanel(),gbc);
        gbc.gridx = 1;
        gbc.fill=GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(buildImagePanel(),gbc);
        gbc.gridheight = GridBagConstraints.RELATIVE;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.weighty++;
        add(buildCarsPanel(),gbc);
    }

    JComponent buildImagePanel(){
        JPanel imagePanel = new JPanel();
        imagePanel.setBorder(BorderFactory.createTitledBorder(photoTitle));
        personImageLabel.setIcon(RaceTrackImageUtils.getImageIcon(t));
        imagePanel.add(personImageLabel);
        personImageLabel.addMouseListener(new EditPanelPictureTakerAdapter(this,personImageLabel));
        return imagePanel;
    }

    private JComponent buildCarsPanel() {
        JPanel carPanel = new JPanel(new BorderLayout());
        carPanel.setBorder(BorderFactory.createTitledBorder(carTitle));
        vehicleEditPanel = new VehicleEditPanel(raceEvent);
        carList = new EditableList<>(defaultEditableListModel,
                vehicleEditPanel);
        JPanel panel2 = new JPanel(new BorderLayout());
        JButton jButton = new JButton("New...");
        jButton.addActionListener((ActionEvent)-> carList.update(new Vehicle("Vehicle Name","Tiger")));
        panel2.add(jButton, BorderLayout.NORTH);
        panel2.add(new JScrollPane(carList),BorderLayout.CENTER);
        carPanel.add(panel2,BorderLayout.CENTER);
        carPanel.add(vehicleEditPanel,BorderLayout.EAST);
        return carPanel;
    }

    JComponent buildNamePanel(){
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(nameTitle));
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(new GridBagLayout());
        gbc.insets = new Insets(3,3,3,3);
        gbc.gridx=0;
        gbc.gridy=0;
        panel.add(new JLabel(firstNameLabel), gbc);
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.gridx=1;
        gbc.weightx=1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        firstNameField.addFocusListener(notifierListener);
        firstNameField.addActionListener(notifierListener);
        panel.add(firstNameField,gbc);
        gbc.weightx=0;
        gbc.gridx=0;
        gbc.gridwidth = GridBagConstraints.RELATIVE;
        gbc.gridy++;
        gbc.fill=GridBagConstraints.NONE;
        lastNameField.addFocusListener(notifierListener);
        lastNameField.addActionListener(notifierListener);
        panel.add(new JLabel(lastNameLabel), gbc);
        gbc.gridx=1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(lastNameField, gbc);
        gbc.gridy++;
        gbc.fill=GridBagConstraints.VERTICAL;
        gbc.weighty=1;
        panel.add(new JPanel(), gbc);
        return panel;
    }

    public void getResources() {
        firstNameLabel = RaceTrackResourceBundle.getInstance().getString("racerPanel.firstname");
        lastNameLabel = RaceTrackResourceBundle.getInstance().getString("racerPanel.lastname");
        title = RaceTrackResourceBundle.getInstance().getString("racerPanel.title");
        photoTitle = RaceTrackResourceBundle.getInstance().getString("image");
        carTitle = RaceTrackResourceBundle.getInstance().getString("racerPanel.vehicle.title");
        nameTitle = RaceTrackResourceBundle.getInstance().getString("name");
    }

    @Override
    protected void createT(){
        t = new Participant();

    }

    @Override
    protected void populateT() {
        t.setFirstName(firstNameField.getText());
        t.setLastName(lastNameField.getText());
    }

    @Override
    protected void populateFields(){
        personImageLabel.setIcon(RaceTrackImageUtils.getImageIcon(t));
        firstNameField.setText(t.getFirstName());
        lastNameField.setText(t.getLastName());
        defaultEditableListModel.setSupplier(()-> t.getVehicles());
    }
}
