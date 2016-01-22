package net.burgin.racetrack.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sun.istack.internal.Nullable;

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
    List<String> getCompetitionClasses();
    @Nullable
    List<Heat> getHeats();
    boolean hasHeats();
    boolean hasHeatsToRun();
    void setHeats(List<Heat> heats);
    @JsonIgnore
    List<RaceResult> getRaceResults();
}
