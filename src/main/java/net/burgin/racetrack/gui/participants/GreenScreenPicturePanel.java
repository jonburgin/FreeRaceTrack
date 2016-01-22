package net.burgin.racetrack.gui.participants;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamPicker;
import com.github.sarxos.webcam.WebcamResolution;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Created by jonburgin on 1/7/16.
 */
public class GreenScreenPicturePanel extends JPanel{
    WebcamPanel webcamPanel;
    JLabel pictureLabel;
    WebcamPicker picker;
    Webcam webcam;
    JSpinner spinner;

    public GreenScreenPicturePanel(){
        picker = new WebcamPicker();
        picker.setSelectedIndex(picker.getItemCount() - 1);
        webcam = picker.getSelectedWebcam();
        if (webcam == null) {
            System.out.println("No webcams found...");
            System.exit(1);
        }
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcamPanel = new WebcamPanel(webcam,true);
        buildGui();
    }

    static public BufferedImage takePhoto(JFrame parent){
        GreenScreenPicturePanel picturePanel = new GreenScreenPicturePanel();
        JOptionPane pane = new JOptionPane(picturePanel);
        pane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = pane.createDialog(parent, "Take Picture");
        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setVisible(true);
        picturePanel.webcamPanel.getWebcam().close();
        Object option = pane.getValue();
        boolean usePicture = option instanceof Integer && ((Integer)option).intValue() == JOptionPane.OK_OPTION;
        return usePicture?picturePanel.getImage():null;
    }

    public BufferedImage getImage(){
        return (BufferedImage)((ImageIcon)pictureLabel.getIcon()).getImage();
    }
    private void buildGui() {
        setLayout(new GridLayout(1,2));
        add(buildCameraPanel());
        add(buildPicturePanel());
    }

    private JComponent buildCameraPanel(){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(4, 4, 4, 4);
        panel.add(picker,c);
        panel.add(webcamPanel,c);
        return panel;
    }

    private JComponent buildPicturePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        JButton button = new JButton("Snap Picture");
        button.addActionListener(actionEvent -> pictureLabel.setIcon(new ImageIcon(takePicture())));
        panel.add(button,c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(spinner = new JSpinner(new SpinnerNumberModel(55,1,1000,1)),c);
        pictureLabel = new JLabel();
        c.weightx = c.weighty =1;
        panel.add(pictureLabel,c);
        return panel;
    }

    private Image takePicture(){
        BufferedImage image = webcam.getImage();
        image.createGraphics().drawRenderedImage(image,AffineTransform.getScaleInstance(.5,.5));
        image = image.getSubimage(0,0,image.getWidth()/2,image.getHeight()/2);
        int width = image.getWidth();
        int height = image.getHeight();
        int newWidth = width - height;
        image = image.getSubimage(newWidth/2,0,height,height);
        BufferedImage newImage = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_ARGB);
        for(int i=0; i < 3; i++) {
            image = greenScreen(image, newImage);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {


            }
        }
        return image;
    }

    private BufferedImage greenScreen(BufferedImage image, BufferedImage newImage) {
        int [] sampleRgb = getRGB(image.getRGB(image.getWidth()/2,0));
        float[] sampleHsb = Color.RGBtoHSB(sampleRgb[0], sampleRgb[1], sampleRgb[2], null);
        float sampleHue = sampleHsb[0];
        for(int x=0; x < image.getWidth(); x++)
            for(int y=0; y < image.getHeight(); y++){
                int newPixel = newImage.getRGB(x,y) & 0x00ffffff;
                if(newPixel != 0)
                    continue;
                int pixel = image.getRGB(x,y);
                int[] rgb = getRGB(pixel);
                float[] hsb = Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], null);
                float hue = hsb[0];
                boolean inHueRange = Math.abs(hue - sampleHue) < ((Integer)spinner.getValue())/1000f;
                boolean inBrightnessRange =  Math.abs(sampleHsb[2] - hsb[2]) < ((Integer)spinner.getValue())/1000f;
                boolean inSpectralRange = Math.abs(sampleHsb[1] - hsb[1]) < ((Integer)spinner.getValue())/1000f;
                pixel &= (inHueRange && inBrightnessRange && inSpectralRange)? 0x00000000 : 0xffffffff;
                newImage.setRGB(x,y,pixel);
            }
        return newImage;
    }

    private int[] getRGB(int pixel) {
        int[] colors = {pixel,pixel,pixel};
        colors[0] = (colors[0]&0x00ff0000)>>>16;
        colors[1] = (colors[1]&0x0000ff00)>>>8;
        colors[2] = (colors[2]&0x000000ff);
        return colors;
    }

    private float difference(int pixel1, int pixel2){
        int colors1[] = getRGB(pixel1);
        int colors2[] = getRGB(pixel2);
        //blue difference
        int blueDistance = Math.abs(colors1[2] - colors2[2]);
        //green difference
        int greenDistance = Math.abs(colors1[1] - colors2[1]);
        //red difference
        int redDistance = Math.abs(colors1[0] - colors2[0]);
        float min = (float) Math.min(Math.sqrt(blueDistance * blueDistance + greenDistance * greenDistance + redDistance * redDistance), 400);
        return min/400;
    }

    private boolean similar(int pixel1, int pixel2, int range){
        //blue difference
        boolean blueSimilar = Math.abs((pixel1 & 0xff) - (pixel2 & 0xff))<range;
        //green difference
        boolean greenSimilar = Math.abs((pixel1>>>8 & 0xff) - (pixel2>>>8 & 0xff)) < range;
        //red difference
        boolean redSimilar = Math.abs((pixel1>>>16 & 0xff) - (pixel2>>> 16 & 0xff)) < range;
        return blueSimilar && greenSimilar && redSimilar;
    }


    static public void main(String[] args){
        JFrame foo = new JFrame("Foo");
        foo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GreenScreenPicturePanel panel = new GreenScreenPicturePanel();
        foo.add(panel);
        foo.pack();
        foo.setVisible(true);
    }

}
