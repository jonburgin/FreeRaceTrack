package net.burgin.racetrack.gui.actions;

import net.burgin.racetrack.RaceTrackResourceBundle;
import net.burgin.racetrack.domain.Heat;
import net.burgin.racetrack.domain.RaceEvent;
import net.burgin.racetrack.gui.heats.HeatListModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jonburgin on 12/24/15.
 */
public class GenerateRaceHeatsAction extends AbstractAction {
    private HeatListModel model;

    public GenerateRaceHeatsAction(HeatListModel model) {
        this.model = model;
        ResourceBundle resourceBundle = RaceTrackResourceBundle.getInstance();
        this.putValue(Action.NAME, resourceBundle.getString("heat.generate.simple.race"));
        //this.putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, Integer.parseInt(resourceBundle.getString("car.new.action.mnemonic.index")));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        model.generateHeats();
    }
}
