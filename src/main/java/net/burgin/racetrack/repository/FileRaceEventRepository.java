package net.burgin.racetrack.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.burgin.racetrack.domain.RaceEvent;

import java.io.*;

/**
 * Created by jonburgin on 1/18/16.
 */
public class FileRaceEventRepository implements RaceEventRepository {
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void save(RaceEvent raceEvent, String context) throws IOException {
        File file = (context!=null)?new File(context):new File(raceEvent.getName() +raceEvent.getId());
        if(!file.getName().toLowerCase().endsWith("rte")){
            file = new File(file.getAbsolutePath() + ".rte");
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(raceEvent));
            fileWriter.close();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }

    @Override
    public RaceEvent read(String name) throws IOException{
        File file = new File(name);
        return mapper.readValue(file, RaceEvent.class);
    }

}
