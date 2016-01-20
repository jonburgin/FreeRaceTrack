package net.burgin.racetrack.gui.adapters;

import net.burgin.racetrack.domain.ImageHolder;
import net.burgin.racetrack.gui.GreenScreenPicturePanel;
import net.burgin.racetrack.gui.RaceTrackImageUtils;
import net.burgin.racetrack.gui.editablelist.Editor;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by jonburgin on 1/19/16.
 */
public class EditPanelPictureTakerAdapter extends MouseAdapter{
    private final JLabel label;
    JFrame parentFrame;
    Editor editor;

    public EditPanelPictureTakerAdapter(Editor editor, JLabel jLabel){
        this.editor = editor;
        this.label = jLabel;
        this.parentFrame = (JFrame)SwingUtilities.getWindowAncestor(jLabel);

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        BufferedImage image = GreenScreenPicturePanel.takePhoto(parentFrame);
        if(image != null){
            ((ImageHolder)editor.getT()).setImage(image);
            label.setIcon(new ImageIcon(image));
            try {
                RaceTrackImageUtils.saveImage((ImageHolder)editor.getT());
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(parentFrame,e.getMessage(),"Error saving file",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
