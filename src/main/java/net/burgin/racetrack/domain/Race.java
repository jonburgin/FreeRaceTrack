package net.burgin.racetrack.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by jonburgin on 12/11/15.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property = "@CLASS")
public interface Race extends RaceEventChangeNotifier{
    String getName();
    Set<String> getCompetitionClasses();
    List<Heat> getHeats();
    boolean hasHeats();
    boolean hasHeatsToRun();
    void setHeats(List<Heat> heats);
}
