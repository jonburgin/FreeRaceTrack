/**
 * Created by jonburgin on 12/11/15.
 */
package net.burgin.racetrack

import com.fasterxml.jackson.databind.ObjectMapper
import net.burgin.racetrack.domain.Vehicle
import spock.lang.*

class VehicleTest extends Specification{
    @Shared
    def car = new Vehicle();
    def desiredJsonString = "{\"uuid\":\"bf51b8f0-7b11-4566-9680-57788c4a287e\",\"name\":\"Herbie\",\"competitionClass\":\"Tiger\"}"
    @Shared
    def mapper = new ObjectMapper();

    def setup(){
        car.setName("Herbie")
        car.setCompetitionClass("Tiger")
    }

    def "Json serialized/deserialized works"(){
        when:
        def carRead = mapper.readValue(mapper.writeValueAsString(car),Vehicle.class)
        then:
        carRead.equals(car);
//        carRead.name == "TestName"
//        carRead.competitionClass == "ClassName"

    }

    def "toString use name and class formatted correctly"(){
        expect:
        car.toString().equals("Herbie (Tiger)")
    }
}
