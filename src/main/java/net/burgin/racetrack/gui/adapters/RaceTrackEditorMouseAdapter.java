package net.burgin.racetrack.gui.adapters;

import net.burgin.racetrack.detection.HotSpot;
import net.burgin.racetrack.detection.HotSpotDetector;
import net.burgin.racetrack.detection.HotSpotTrack;
import net.burgin.racetrack.gui.RacetrackWebcamPanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by jonburgin on 12/2/15.
 */
public class RaceTrackEditorMouseAdapter extends MouseAdapter {

    RacetrackWebcamPanel racetrackWebcamPanel;
    boolean hotSpotPositioning;
    private boolean trackPositioning;
    private boolean trackStretching;

    public RaceTrackEditorMouseAdapter(RacetrackWebcamPanel racetrackWebcamPanel){
        this.racetrackWebcamPanel = racetrackWebcamPanel;
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        RacetrackWebcamPanel.MyPainter painter = (RacetrackWebcamPanel.MyPainter) racetrackWebcamPanel.getPainter();
        if(hotSpotPositioning){
            HotSpot hotSpot = racetrackWebcamPanel.getHotSpotTrack().getRaceStartHotSpot();
            hotSpot.getPosition().move(painter.fromDisplayX(mouseEvent.getX()),painter.fromDisplayY(mouseEvent.getY()));
        }
        if(trackPositioning){
            racetrackWebcamPanel.getHotSpotTrack()
                    .setFinishLinePosition(new Point(painter.fromDisplayX(mouseEvent.getX()), painter.fromDisplayY(mouseEvent.getY())));
        }
        if(trackStretching){
            racetrackWebcamPanel.getHotSpotTrack()
                    .adjustWidth(new Point(painter.fromDisplayX(mouseEvent.getX()), painter.fromDisplayY(mouseEvent.getY())));
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        HotSpot hotSpot = racetrackWebcamPanel.getHotSpotTrack().getRaceStartHotSpot();
        boolean insideHotspot = closeEnough(mouseEvent,hotSpot.getPosition(),20);
        Point trackMovePoint = racetrackWebcamPanel.getHotSpotTrack().getMoveHotSpot();
        boolean insideTrackMove = closeEnough(mouseEvent,trackMovePoint,10);
        if(insideHotspot || insideTrackMove){
            setCursorType(Cursor.MOVE_CURSOR);
            return;
        }
        Point trackStretchPoint = racetrackWebcamPanel.getHotSpotTrack().getStretchHotSpot();
        boolean insideTrackStretch = closeEnough(mouseEvent,trackStretchPoint, 20);
        if(insideTrackStretch){
            setCursorType(Cursor.E_RESIZE_CURSOR);
            return;
        }
        setCursorType(Cursor.DEFAULT_CURSOR);

    }

    private boolean closeEnough(MouseEvent mouseEvent, Point p, int diameter){
        RacetrackWebcamPanel.MyPainter painter = (RacetrackWebcamPanel.MyPainter) racetrackWebcamPanel.getPainter();
        //todo need to do reverse transposition
        return Math.abs(painter.fromDisplayX(mouseEvent.getX()) - p.x) < diameter && Math.abs(painter.fromDisplayY(mouseEvent.getY()) - p.y)< diameter;
    }

    private void setCursorType(int cursorType){
        if(racetrackWebcamPanel.getCursor().getType() != cursorType) {
            racetrackWebcamPanel.setCursor(new Cursor(cursorType));
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        HotSpot hotSpot = racetrackWebcamPanel.getHotSpotTrack().getRaceStartHotSpot();
        if(closeEnough(mouseEvent,hotSpot.getPosition(),20)){
            hotSpotPositioning = true;
        }else if(closeEnough(mouseEvent,racetrackWebcamPanel.getHotSpotTrack().getMoveHotSpot(), 10)){
            trackPositioning = true;
        }else if(closeEnough(mouseEvent,racetrackWebcamPanel.getHotSpotTrack().getStretchHotSpot(),10)){
            trackStretching = true;
        }
        if(trackPositioning || trackStretching || hotSpotPositioning){
            racetrackWebcamPanel.getHotSpotDetector().setEnabled(false);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        quitEditing();
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        quitEditing();
    }

    protected void quitEditing(){
        hotSpotPositioning = false;
        trackPositioning = false;
        trackStretching = false;
        HotSpotDetector hotSpotDetector = racetrackWebcamPanel.getHotSpotDetector();
        HotSpotTrack hotSpotTrack = racetrackWebcamPanel.getHotSpotTrack();
        hotSpotDetector.setHotSpots(new ArrayList<>(hotSpotTrack.getLanes().values()));
        hotSpotDetector.addHotSpot(hotSpotTrack.getRaceStartHotSpot());
        hotSpotDetector.setEnabled(true);
    }
}
