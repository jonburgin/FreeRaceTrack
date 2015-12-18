package net.burgin.racetrack.gui;

import lombok.Data;
import net.burgin.racetrack.RaceTrackResourceBundle;
import net.burgin.racetrack.domain.Car;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by jonburgin on 12/16/15.
 */
@Data
public class CarEditPanel extends AbstractEditPanel<Car>{
    private JTextField nameTextField;
    JComboBox<String> raceClassComboBox;
    String photoTitle;
    String nameTitle;
    ImageIcon carImageIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getSystemResource("images/pinewood-derby2.jpg"));
    private String carTitle;
    private String carClassTitle;

    public CarEditPanel(){
        createT();
        loadResources();
        buildGui();
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
        JLabel jLabel = new JLabel(carImageIcon);
        add(jLabel,c);
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
        panel.add(new JLabel(carClassTitle),c);
        c.gridx++;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        //todo dropdown for classification properly
        raceClassComboBox = new JComboBox<>(new String[]{"Tiger", "Wolf", "Web I", "Web II"});
        raceClassComboBox.addItemListener(itemEvent-> {
                if(itemEvent.getStateChange()==ItemEvent.SELECTED)
                    dirty = true;
                    t.setCompetitionClass(itemEvent.getItem().toString());
                    SwingUtilities.invokeLater(()->notifyEditUpdateListeners());
            });
        panel.add(raceClassComboBox,c);
        return panel;
    }

    JComponent buildImagePanel(){
        JPanel imagePanel = new JPanel();
        imagePanel.setBorder(BorderFactory.createTitledBorder(photoTitle));
        imagePanel.add(new JLabel(carImageIcon));
        return imagePanel;
    }

    void loadResources() {
        carClassTitle = RaceTrackResourceBundle.getInstance().getString("car.class");
        photoTitle = RaceTrackResourceBundle.getInstance().getString("image");
        carTitle = RaceTrackResourceBundle.getInstance().getString("car");
        nameTitle = RaceTrackResourceBundle.getInstance().getString("nameLabel");
    }

    @Override
    void createT(){
        t = new Car();
    }

    @Override
    void populateT() {
        t.setName(nameTextField.getText());
        t.setCompetitionClass(raceClassComboBox.getSelectedItem().toString());
    }

    @Override
    void populateFields(Car car){
        nameTextField.setText(car.getName());
        String competitionClass = car.getCompetitionClass();
        if(competitionClass !=null && competitionClass.length()>0)
            raceClassComboBox.setSelectedItem(competitionClass);
    }

}
