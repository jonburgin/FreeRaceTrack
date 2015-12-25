package net.burgin.racetrack.gui.actions;

import net.burgin.racetrack.RaceTrackResourceBundle;
import net.burgin.racetrack.domain.Heat;
import net.burgin.racetrack.domain.RaceEvent;
import net.burgin.racetrack.gui.heats.HeatListModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Created by jonburgin on 12/24/15.
 */
public class GenerateRunoffHeatsAction extends AbstractAction {
    private HeatListModel model;

    public GenerateRunoffHeatsAction(HeatListModel model) {
        this.model = model;
        ResourceBundle resourceBundle = RaceTrackResourceBundle.getInstance();
        this.putValue(Action.NAME, resourceBundle.getString("heat.generate.runoff.race"));
        //this.putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, Integer.parseInt(resourceBundle.getString("car.new.action.mnemonic.index")));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
//        List<Heat> heats = raceEvent.getHeatGenerator().generateAllRunoffRaceHeats();
//        HashMap<UUID,Heat> heatsToAdd = new HashMap<>(heats.size());
//        heats.stream()
//                .forEach(heat->heatsToAdd.put(heat.getRaceId(),heat));
//        raceEvent.getHeats().putAll(heatsToAdd);
    }
}
