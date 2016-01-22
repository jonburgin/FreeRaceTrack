package net.burgin.racetrack.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jonburgin on 12/24/15.
 */
@Data
public class DefaultRaceResult implements RaceResult{
    @JsonIgnore
    private final Race race;
    List<Competitor> competitors;

    public DefaultRaceResult(Race race, List<Competitor> competitors){
        this.race = race;
        this.competitors = competitors;
    }

    public String getName(){
        return race.getName();
    }

    static List<RaceResult> determineRaceResults(Race race){
        List<List<Competitor>> listList = getCompetitors(race);
        return listList.stream()
                .map(list->new DefaultRaceResult(race, list))
                .collect(Collectors.toList());
    }

    static List<List<Competitor>> getCompetitors(Race race){
        if(race.hasHeatsToRun()) {
            return new ArrayList<>(new ArrayList<>());
        }
        List<Competitor> list =
                race.getHeats().stream()
                        .peek(DefaultRaceResult::normalizeRaceTime)
                        .map(Heat::getCompetitors)
                        .flatMap(List::stream)
                        .collect(Collectors.groupingBy(Competitor::getVehicle))
                        .values().stream()
                        .map(DefaultRaceResult::computeAverageTime)
                        .collect(Collectors.toList());
        if(race instanceof DefaultSimpleRace && ((DefaultSimpleRace)race).isByClassification())
            return new ArrayList(resultsByCompetitionClass(list));
        else
            return Arrays.asList(list);

    }

    static Collection<List<Competitor>> resultsByCompetitionClass(List<Competitor> list){
        Map<String, List<Competitor>> collect = list.stream()
                .collect(Collectors.groupingBy(competitor -> competitor.getVehicle().getCompetitionClass()));
        return collect.values();
    }

    static Competitor computeAverageTime(List<Competitor> competitors){
        Competitor competitor = new Competitor(competitors.get(0).getVehicle());
        competitor.setAverageTime( competitors.stream()
                .mapToLong(Competitor::getRelativeTime)
                .average().getAsDouble());
        return competitor;
    }

    static Heat normalizeRaceTime(Heat heat) {
        long startTime = heat.getStartTime();
        heat.getCompetitors().stream()
                .forEach(competitor -> competitor.setRelativeTime(competitor.getRaceTime() - startTime));
        return heat;
    }
}
