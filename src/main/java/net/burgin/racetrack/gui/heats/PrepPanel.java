package net.burgin.racetrack.gui.heats;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.burgin.racetrack.domain.*;
import net.burgin.racetrack.gui.RaceTrackImageUtils;

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
    private RaceEvent raceEvent;
    List<Vehicle> vehicles = new ArrayList<>();

    public PrepPanel(RaceEvent raceEvent, Heat heat){
        setLayout(new GridBagLayout());
        rebuild(raceEvent,heat);
    }

    public void rebuild(RaceEvent raceEvent, Heat heat){
        Arrays.asList(getComponents()).stream()
                .forEach(c ->remove(c));
        this.raceEvent = raceEvent;
        vehicles = heat.getCompetitors().stream()
                .map(competitor -> competitor.getVehicle())
                .collect(Collectors.toList());
        addVehiclePanels();
    }

    private void addVehiclePanels(){
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        IntStream.range(0, vehicles.size())
                .forEach(i -> add(buildPanel(vehicles.get(i),i+1),c));
    }

    private JPanel buildPanel(Vehicle vehicle, int i) {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(6,6,6,6);
        JLabel label = new JLabel("Lane " + i);
        label.setFont(label.getFont().deriveFont(24f));
        mainPanel.add(label, c);
        Participant participant = raceEvent.getRacerForVehicle(vehicle);
        mainPanel.add(new JLabel( RaceTrackImageUtils.getImageIcon(participant)),c);
        label = new JLabel(participant.getFirstName() + " " + participant.getLastName());
        label.setFont(label.getFont().deriveFont(24f));
        mainPanel.add(label,c);
        mainPanel.add(new JLabel( RaceTrackImageUtils.getImageIcon(vehicle)),c);
        label = new JLabel(vehicle.toString());
        label.setFont(label.getFont().deriveFont(24f));
        mainPanel.add(label, c);
        return mainPanel;
    }

    static public void main(String[] args){
        RaceEvent event = new RaceEvent();
        Participant r1 = new Participant("Freedom", "Burgin");
        Participant r2 = new Participant("Glory", "Burgin");
        Vehicle v1 = new Vehicle("Dominator", "Wolf");
        Vehicle v2 = new Vehicle("Killer", "Cow");
        r1.getVehicles().add(v1);
        r2.getVehicles().add(v2);
        event.setParticipants(Arrays.asList(r1,r2));
        Heat heat = new Heat(new DefaultSimpleRace(), Arrays.asList(v1,v2));
        JFrame foo = new JFrame("Foo");
        PrepPanel prepPanel = new PrepPanel(event,heat);
        foo.add(prepPanel);
        foo.pack();
        foo.setVisible(true);

    }
}
