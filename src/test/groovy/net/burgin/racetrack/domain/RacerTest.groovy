/**
 * Created by jonburgin on 12/11/15.
 */
package net.burgin.racetrack.domain

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Shared
import spock.lang.Specification

class RacerTest extends Specification{
    def racer = new Racer("Jon", "Burgin")
    @Shared
    def mapper = new ObjectMapper()

    def setup(){
        racer.setCars(Arrays.asList(new Car("Herbie", "Tiger"), new Car("Dominator", "Web II")));
    }

    def "Json output is correct"(){
        when:
            def jsonString = mapper.writeValueAsString(racer)
        then:
            jsonString.equals("{\"firstName\":\"Jon\",\"lastName\":\"Burgin\",\"cars\":[{\"name\":\"Herbie\",\"competitionClass\":\"Tiger\"},{\"name\":\"Dominator\",\"competitionClass\":\"Web II\"}]}")
    }

    def "Json input is correct"(){
        when:
        def racerRead = mapper.readValue("{\"firstName\":\"Jon\",\"lastName\":\"Burgin\",\"cars\":[{\"name\":\"Herbie\",\"competitionClass\":\"Tiger\"},{\"name\":\"Dominator\",\"competitionClass\":\"Web II\"}]}", Racer.class)
        then:
        racerRead.firstName == "Jon"
        racerRead.lastName == "Burgin"
        racerRead.cars.size() == 2
    }
}
