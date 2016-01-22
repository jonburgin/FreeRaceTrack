package net.burgin.racetrack.gui.heats;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.burgin.racetrack.domain.DefaultSimpleRace;
import net.burgin.racetrack.domain.Heat;
import net.burgin.racetrack.domain.Participant;
import net.burgin.racetrack.domain.Vehicle;
import net.burgin.racetrack.gui.RaceTrackImageUtils;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by jonburgin on 1/21/16.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ReplayPanel extends JPanel {
    Heat heat;
    Timer timer = new Timer(1200,(e)->nextImage());
    ImageIcon icon = new ImageIcon();
    int index;

    public ReplayPanel(Heat heat){
        setLayout(new GridBagLayout());
        this.heat = heat;
        timer.setInitialDelay(500);
        buildGui();
    }

    private void buildGui() {
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        JLabel component = new JLabel(icon);
        component.setBackground(Color.RED);
        add(component,c);
    }

    private void nextImage() {
        java.util.List<Image> photofinish = heat.getPhotofinish();
        if(index >= photofinish.size())
            index = 0;
        icon.setImage(photofinish.get(index++));
        repaint();
    }

    public void setHeat(Heat heat){
        timer.stop();
        this.heat = heat;
    }

    public void doReplay(){
        index = 0;
        timer.start();
    }

    //todo stop the timer if lose focus or something
    public void stop(){
        timer.stop();
    }

    public static void main(String[] args){
        Heat heat = new Heat(new DefaultSimpleRace("foo"),new ArrayList<>());
        List<Image> photofinish = heat.getPhotofinish();
        photofinish.add(RaceTrackImageUtils.getDefaultImage(new Vehicle()));
        photofinish.add(RaceTrackImageUtils.getDefaultImage(new Participant()));
        ReplayPanel panel = new ReplayPanel(heat);
        JFrame foo = new JFrame("Foo");
        foo.setPreferredSize(new Dimension(640,480));
        foo.add(panel);
        foo.pack();
        panel.doReplay();
        foo.setVisible(true);
    }
}
