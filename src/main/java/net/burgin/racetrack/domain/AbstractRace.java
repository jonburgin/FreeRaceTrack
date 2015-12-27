package net.burgin.racetrack.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by jonburgin on 12/11/15.
 */
@Data
@EqualsAndHashCode(callSuper=false, exclude = "id")
abstract public class AbstractRace extends AbstractRaceEventChangeNotifier implements Race {
    String name;
    UUID id = UUID.randomUUID();
    List<Heat> heats = new ArrayList<>();

    public void setName(String name){
        this.name = name;
        raceChanged(this);
    }

    @Override
    public String toString(){
        return name;
    }

    public boolean hasHeatsToRun(){
        return heats.stream().filter(heat -> heat.startTime == 0).findFirst().isPresent();
    }

    public boolean hasHeats(){
        return heats.size()>0;
    }

    public RaceResult getRaceResult(){
        return new DefaultRaceResult(this);
    }
}
