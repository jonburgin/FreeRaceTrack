package net.burgin.racetrack.domain

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification


/**
 * Created by jonburgin on 12/11/15.
 */
class RunoffTest extends Specification {
    def runoff
    def mapper = new ObjectMapper()
    def runoffJson = "{\"@CLASS\":\"net.burgin.racetrack.domain.Runoff\",\"name\":\"Championship\",\"races\":[{\"@CLASS\":\"net.burgin.racetrack.domain.Race\",\"name\":\"Race1\",\"competitionClasses\":[\"bar\",\"foo\"]},{\"@CLASS\":\"net.burgin.racetrack.domain.Race\",\"name\":\"Race2\",\"competitionClasses\":[\"foo\",\"box\"]}],\"take\":1}"

    def setup(){
        runoff = new Runoff("Championship",1)
        runoff.addRace(new Race("Race1", "foo", "bar"))
        runoff.addRace(new Race("Race2", "foo", "box"))
    }

    def "json output is correct"(){
        when:
        def jsonString = mapper.writeValueAsString(runoff)
        //System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(runoff))
        then:
        jsonString.equals(runoffJson)
    }

    def "Json input is correct"(){
        when:
        def raceRead = mapper.readValue(runoffJson, RaceType.class)
        then:
        raceRead.name == "Championship"
        raceRead.races.size() == 2;
    }

    def "toString is correct"(){
        expect:
        runoff.toString().equals("Championship (bar,foo,box)");
    }
}