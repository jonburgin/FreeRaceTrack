package net.burgin.racetrack.gui.results;

import net.burgin.racetrack.domain.Competitor;
import net.burgin.racetrack.domain.Participant;
import net.burgin.racetrack.domain.RaceEvent;
import net.burgin.racetrack.domain.RaceResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;

/**
 * Created by jonburgin on 1/21/16.
 */
public class ResultsListModel extends DefaultListModel<RaceResult>{
    private final RaceEvent raceEvent;
    List<RaceResult> raceResults = new ArrayList<>();

    public ResultsListModel(RaceEvent raceEvent) {
        this.raceEvent = raceEvent;
    }

    @Override
    public int getSize() {
        return raceResults.size();
    }

    @Override
    public RaceResult getElementAt(int i) {
        return raceResults.get(i);
    }

    protected String toString(RaceResult raceResult){
        if(raceResult.getCompetitors().size() < 1)
            return raceResult.getName() + " [No Results yet]";
        return raceResult.getCompetitors().stream()
                .map(this::toString)
                .collect(Collectors.joining("], [",raceResult.getName() + " [[", "]]"));
    }

    protected String toString(Competitor competitor){
        Participant participant = raceEvent.getRacerForVehicle(competitor.getVehicle());
        return String.format("%s %s:%s:%s",
                participant.getFirstName(),
                participant.getLastName(),
                competitor.getVehicle().getName(),
                competitor.getAverageTime());

    }

    public void generateResults(){
        raceResults = raceEvent.generateResults();
        this.fireContentsChanged(this,0,getSize());
    }
}
