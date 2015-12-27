/**
 * Created by jonburgin on 12/11/15.
 */
package net.burgin.racetrack.domain

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Shared
import spock.lang.Specification

class RacerTest extends Specification{
    @Shared
    def racer = new Racer("Jon", "Burgin")
    @Shared
    def mapper = new ObjectMapper()

    def setup(){
        racer.setVehicles(Arrays.asList(new Vehicle("Herbie", "Tiger"), new Vehicle("Dominator", "Web II")));
    }

    def "Json serializatin/deserialization is correct"(){
        when:
        def racerRead = mapper.readValue(mapper.writeValueAsString(racer), Racer.class)
        then:
        racerRead.firstName == "Jon"
        racerRead.lastName == "Burgin"
        racerRead.vehicles.size() == 2
    }
}
