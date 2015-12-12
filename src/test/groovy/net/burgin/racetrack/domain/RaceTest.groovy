package net.burgin.racetrack.domain

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification


/**
 * Created by jonburgin on 12/11/15.
 */
class RaceTest extends Specification {
    def race = new Race()
    def mapper = new ObjectMapper()
    def raceJson = "{\"@CLASS\":\"net.burgin.racetrack.domain.Race\",\"name\":\"Tiger Race\",\"competitionClasses\":[\"Tiger\",\"Wolf\",\"Cow\"]}"

    def setup(){
        race.name = "Tiger Race"
        race.competitionClasses = Arrays.asList("Tiger", "Wolf", "Cow")
    }

    def "multi parameter constructor initializes variable correctly" (){
        def race2 = new Race("name2", "one", "two")
        expect:
        race2.name.equals("name2")
        race2.competitionClasses.size() == 2
        race2.competitionClasses.contains("one")
        race2.competitionClasses.contains("two")
    }

    def "json output is correct"(){
        when:
        def jsonString = mapper.writeValueAsString(race)
        then:
        jsonString.equals(raceJson)
    }

    def "race change events are thrown on name change"(){
        def listener = Mock(RaceEventChangeListener)
        race.addRaceEventChangeListener(listener)
        when:
        race.setName("foo")
        then:
        1*listener.raceChanged(race)
    }

    def "toString returns name"(){
        expect:
        race.toString().equals("Tiger Race")
    }

    def "Json input is correct"(){
        when:
        def raceRead = mapper.readValue(raceJson, RaceType.class)
        then:
        raceRead.name == "Tiger Race"
        raceRead.competitionClasses.size() == 3
        raceRead.competitionClasses.contains("Tiger")
        raceRead.competitionClasses.contains("Wolf")
        raceRead.competitionClasses.contains("Cow")
    }
}