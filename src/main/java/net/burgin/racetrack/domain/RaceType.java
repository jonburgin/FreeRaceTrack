package net.burgin.racetrack.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Set;

/**
 * Created by jonburgin on 12/11/15.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property = "@CLASS")
public interface RaceType extends RaceEventChangeNotifier{
    Set<String> getCompetitionClasses();
}
