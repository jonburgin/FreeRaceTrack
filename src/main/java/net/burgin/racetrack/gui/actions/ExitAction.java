package net.burgin.racetrack.gui.actions;

import net.burgin.racetrack.RaceTrackResourceBundle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

/**
 * Created by jonburgin on 12/4/15.
 */
public class ExitAction extends AbstractAction {

    public ExitAction(){

        ResourceBundle resourceBundle = RaceTrackResourceBundle.getInstance();
        this.putValue(Action.NAME, resourceBundle.getString("exit.action.name"));
        this.putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, Integer.parseInt(resourceBundle.getString("exit.action.mnemonic.index")));
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //((JComponent)actionEvent.getSource()).getTopLevelAncestor()
        System.exit(0);
    }
}
