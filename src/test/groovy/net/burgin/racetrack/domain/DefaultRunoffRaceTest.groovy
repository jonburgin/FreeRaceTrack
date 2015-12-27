package net.burgin.racetrack.domain

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification


/**
 * Created by jonburgin on 12/11/15.
 */
class DefaultRunoffRaceTest extends Specification {
    def runoff
    def mapper = new ObjectMapper()

    def setup(){
        runoff = new DefaultRunoffRace("Championship",1)
        runoff.addRace(new DefaultSimpleRace("Race1", "foo", "bar"))
        runoff.addRace(new DefaultSimpleRace("Race2", "foo", "box"))
    }

    def "Json serialization is correct"(){
        when:
        def jsonString = mapper.writeValueAsString(runoff)
        def raceRead = mapper.readValue(jsonString, Race.class)
        then:
        raceRead == runoff
    }

    def "toString is correct"(){
        expect:
        runoff.toString().equals("Championship (bar,foo,box)");
    }

    def "getCarsForRunoffRace returns appropriate cars"(){
        def race1 = Mock(Race)
        def raceResults1 = Mock(RaceResult)
        def race2 = Mock(Race)
        def raceResults2 = Mock(RaceResult)
        def competitor = Mock(Competitor)
        def car = Mock(Vehicle)
        when:
        runoff.setRaces([race1,race2])
        runoff.setTakeNumber(2)
        def cars1 = runoff.getVehicles()
        runoff.setTakeNumber(1)
        def cars2 = runoff.getVehicles()
        then:
        race1.getRaceResult()>>raceResults1
        race2.getRaceResult()>>raceResults2
        raceResults1.getCompetitors()>>[[competitor,competitor],[competitor]]
        raceResults2.getCompetitors()>>[[competitor]]
        competitor.getVehicle()>>car
        cars1.size == 4
        cars2.size == 3
    }

}