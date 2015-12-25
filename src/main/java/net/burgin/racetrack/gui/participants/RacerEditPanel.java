package net.burgin.racetrack.gui.participants;

import net.burgin.racetrack.RaceTrackResourceBundle;
import net.burgin.racetrack.domain.Car;
import net.burgin.racetrack.domain.Racer;
import net.burgin.racetrack.gui.editablelist.AbstractEditPanel;
import net.burgin.racetrack.gui.editablelist.DefaultEditableListModel;
import net.burgin.racetrack.gui.editablelist.EditableList;
import net.burgin.racetrack.gui.participants.CarEditPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by jonburgin on 12/14/15.
 */
public class RacerEditPanel extends AbstractEditPanel<Racer> {
    String firstNameLabel;
    String lastNameLabel;
    String title;
    private JTextField firstNameField = new JTextField(20);
    JTextField lastNameField = new JTextField(20);
    private String photoTitle;
    private String carTitle;
    private String nameTitle;
    ImageIcon personImageIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getSystemResource("images/duke.gif"));
    NotifierListener notifierListener = new NotifierListener();
    DefaultEditableListModel<Car> defaultEditableListModel
            = new DefaultEditableListModel<Car>(() -> new ArrayList<Car>());

    public RacerEditPanel() {
        createT();
        defaultEditableListModel.setSupplier(()->t.getCars());
        buildGui();
        setEnabled(false);
    }

    protected void populateFields(Racer racer){
        firstNameField.setText(racer.getFirstName());
        lastNameField.setText(racer.getLastName());
        defaultEditableListModel.setSupplier(()->racer.getCars());
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
        imagePanel.add(new JLabel(personImageIcon));
        return imagePanel;
    }

    private JComponent buildCarsPanel() {
        JPanel carPanel = new JPanel(new BorderLayout());
        carPanel.setBorder(BorderFactory.createTitledBorder(carTitle));
        CarEditPanel carEditPanel = new CarEditPanel();
        EditableList<Car> list
                = new EditableList<>(defaultEditableListModel,
                carEditPanel);
        JPanel panel2 = new JPanel(new BorderLayout());
        JButton jButton = new JButton("New...");
        jButton.addActionListener((ActionEvent)->list.update(new Car("Le Car","Tiger")));
        panel2.add(jButton, BorderLayout.NORTH);
        panel2.add(new JScrollPane(list),BorderLayout.CENTER);
        carPanel.add(panel2,BorderLayout.CENTER);
        carPanel.add(carEditPanel,BorderLayout.EAST);
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
        carTitle = RaceTrackResourceBundle.getInstance().getString("racerPanel.cartitle");
        nameTitle = RaceTrackResourceBundle.getInstance().getString("name");
    }

    protected void createT(){
        t = new Racer();

    }
    protected void populateT() {
        t.setFirstName(firstNameField.getText());
        t.setLastName(lastNameField.getText());
    }
}
