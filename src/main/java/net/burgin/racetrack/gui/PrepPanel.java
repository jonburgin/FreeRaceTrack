package net.burgin.racetrack.gui;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.burgin.racetrack.RaceTrackResourceBundle;
import net.burgin.racetrack.domain.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by jonburgin on 1/6/16.
 *
 * The purpose of this panel is to show pictures of the vehicles in proper lane order. The facilitates setup.
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PrepPanel extends JPanel {
    private final RaceEvent raceEvent;
    List<Vehicle> vehicles = new ArrayList<>();

    public PrepPanel(RaceEvent raceEvent, Heat heat){
        this.raceEvent = raceEvent;
        vehicles = heat.getCompetitors().stream()
                .map(competitor -> competitor.getVehicle())
                .collect(Collectors.toList());
        buildGui();
    }

    private void buildGui() {
        IntStream.range(0, vehicles.size())
                .forEach(i -> addPanel(vehicles.get(i),i+1));
    }


    private void addPanel(Vehicle vehicle, int i) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(6,6,6,6);
        JLabel label = new JLabel("Lane " + i);
        label.setFont(label.getFont().deriveFont(24f));
        panel.add(label, c);
        Racer racer = raceEvent.getRacerForVehicle(vehicle);
        panel.add(new JLabel( RaceTrackImageUtils.getImage(racer)),c);
        label = new JLabel(racer.getFirstName() + " " + racer.getLastName());
        label.setFont(label.getFont().deriveFont(24f));
        panel.add(label,c);
        panel.add(new JLabel( RaceTrackImageUtils.getImage(vehicle)),c);
        label = new JLabel(vehicle.toString());
        label.setFont(label.getFont().deriveFont(24f));
        panel.add(label, c);
        add(panel);
    }

    static public void main(String[] args){
        RaceEvent event = new RaceEvent();
        Racer r1 = new Racer("Freedom", "Burgin");
        Racer r2 = new Racer("Glory", "Burgin");
        Vehicle v1 = new Vehicle("Dominator", "Wolf");
        Vehicle v2 = new Vehicle("Killer", "Cow");
        r1.getVehicles().add(v1);
        r2.getVehicles().add(v2);
        event.setRacers(Arrays.asList(r1,r2));
        Heat heat = new Heat(new DefaultSimpleRace(), Arrays.asList(v1,v2));
        JFrame foo = new JFrame("Foo");
        PrepPanel prepPanel = new PrepPanel(event,heat);
        foo.add(prepPanel);
        prepPanel.setVehicles(Arrays.asList(v1,v2));
        foo.pack();
        foo.setVisible(true);

    }
}
