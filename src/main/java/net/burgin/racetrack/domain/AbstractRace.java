package net.burgin.racetrack.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

/**
 * Created by jonburgin on 12/11/15.
 */
@Data
@EqualsAndHashCode(callSuper=false)
abstract public class AbstractRace extends AbstractRaceEventChangeNotifier implements Race {
    String name;
    UUID id = UUID.randomUUID();

    public void setName(String name){
        this.name = name;
        raceChanged((Race)this);
    }

    @Override
    public String toString(){
        return name;
    }

}
