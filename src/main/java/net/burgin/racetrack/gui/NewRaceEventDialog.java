package net.burgin.racetrack.gui;

import com.sun.xml.internal.ws.util.StringUtils;
import net.burgin.racetrack.RaceTrackResourceBundle;
import net.burgin.racetrack.domain.RaceEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

/**
 * Created by jonburgin on 12/4/15.
 */
public class NewRaceEventDialog extends JDialog {
    String nameString;
    RaceEvent raceEvent;
    JTextField nameTextField;

    public NewRaceEventDialog(Window window, String title, boolean modal) {
        super(window, title, modal? ModalityType.APPLICATION_MODAL:ModalityType.MODELESS);
        getResources();
        buildGui();
    }

    private void buildGui() {
        raceEvent = new RaceEvent();
        setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.add(new JLabel(nameString));
        nameTextField = new JTextField(20);
        nameTextField.addActionListener(new TextFieldEntryAction());
        panel.add(nameTextField);
        add(panel,BorderLayout.CENTER);
        panel = new JPanel();
        JButton button = new JButton(new OKAction());
        panel.add(button);
        panel.add(new JButton(new CancelAction()));
        add(panel, BorderLayout.SOUTH);
        //add(new JLabel("TODO datepicker"));
        pack();
    }

    public void getResources() {
        nameString = RaceTrackResourceBundle.getInstance().getString("name");
    }

    protected void ok(String eventName){
        if(eventName == null || eventName.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    RaceTrackResourceBundle.getInstance().getString("race.warning.badtitle"),
                    RaceTrackResourceBundle.getInstance().getString("race.warning.badtitle.description"),
                    JOptionPane.WARNING_MESSAGE);
        }
        raceEvent.setName(eventName);
        NewRaceEventDialog.this.setVisible(false);
        FreeRaceTrack.getInstance().addRaceEvent(raceEvent);
    }
    private class TextFieldEntryAction extends AbstractAction{
        public TextFieldEntryAction(){
            ResourceBundle resourceBundle = RaceTrackResourceBundle.getInstance();
            this.putValue(Action.NAME, resourceBundle.getString("name"));
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
                ok(actionEvent.paramString());
        }
    }

    private class OKAction extends AbstractAction{
        public OKAction(){
            ResourceBundle resourceBundle = RaceTrackResourceBundle.getInstance();
            this.putValue(Action.NAME, resourceBundle.getString("ok.action.name"));
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ok(nameTextField.getText());
        }
    }
    private class CancelAction extends AbstractAction{
        public CancelAction(){
            ResourceBundle resourceBundle = RaceTrackResourceBundle.getInstance();
            this.putValue(Action.NAME, resourceBundle.getString("cancel.action.name"));
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            NewRaceEventDialog.this.setVisible(false);
        }
    }

}
