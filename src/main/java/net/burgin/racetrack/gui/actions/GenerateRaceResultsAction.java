package net.burgin.racetrack.gui.actions;

import net.burgin.racetrack.RaceTrackResourceBundle;
import net.burgin.racetrack.gui.heats.HeatListModel;
import net.burgin.racetrack.gui.results.ResultsListModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

/**
 * Created by jonburgin on 12/24/15.
 */
public class GenerateRaceResultsAction extends AbstractAction {
    private ResultsListModel model;

    public GenerateRaceResultsAction(ResultsListModel model) {
        this.model = model;
        ResourceBundle resourceBundle = RaceTrackResourceBundle.getInstance();
        this.putValue(Action.NAME, resourceBundle.getString("result.generate"));
        //this.putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, Integer.parseInt(resourceBundle.getString("car.new.action.mnemonic.index")));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        model.generateResults();
    }
}
