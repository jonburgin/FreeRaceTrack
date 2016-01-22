package net.burgin.racetrack.gui.adapters;

import net.burgin.racetrack.detection.HotSpot;
import net.burgin.racetrack.detection.HotSpotDetector;
import net.burgin.racetrack.detection.HotSpotTrack;
import net.burgin.racetrack.gui.heats.RacetrackWebcamPanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by jonburgin on 12/2/15.
 */
public class RaceTrackEditorMouseAdapter extends MouseAdapter {

    RacetrackWebcamPanel racetrackWebcamPanel;
    private boolean hotSpotPositioning;
    private boolean trackPositioning;
    private boolean trackStretching;

    public RaceTrackEditorMouseAdapter(RacetrackWebcamPanel racetrackWebcamPanel){
        this.racetrackWebcamPanel = racetrackWebcamPanel;
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if(!(hotSpotPositioning|trackPositioning|trackStretching))
            return;
        RacetrackWebcamPanel.RacetrackPainter painter = (RacetrackWebcamPanel.RacetrackPainter) racetrackWebcamPanel.getPainter();
        Point mousePoint = new Point(painter.fromDisplayX(mouseEvent.getX()), painter.fromDisplayY(mouseEvent.getY()));
        HotSpotTrack hotSpotTrack = racetrackWebcamPanel.getHotSpotTrack();
        if(hotSpotPositioning){
            hotSpotTrack.setRaceStartPosition(mousePoint);
        }else if(trackPositioning){
            hotSpotTrack.setFinishLinePosition(mousePoint);
        }else if(trackStretching){
            hotSpotTrack.adjustWidth(mousePoint);
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        boolean insideHotspot = closeEnough(mouseEvent,racetrackWebcamPanel.getHotSpotTrack().getRaceStartPosition(),20);
        boolean insideTrackMove = closeEnough(mouseEvent,racetrackWebcamPanel.getHotSpotTrack().getMovePosition(),10);
        if(insideHotspot || insideTrackMove){
            setCursorType(Cursor.MOVE_CURSOR);
            return;
        }
        boolean insideTrackStretch = closeEnough(mouseEvent,racetrackWebcamPanel.getHotSpotTrack().getStretchPosition(), 20);
        if(insideTrackStretch){
            setCursorType(Cursor.E_RESIZE_CURSOR);
            return;
        }
        setCursorType(Cursor.DEFAULT_CURSOR);

    }

    private boolean closeEnough(MouseEvent mouseEvent, Point p, int diameter){
        RacetrackWebcamPanel.RacetrackPainter painter = (RacetrackWebcamPanel.RacetrackPainter) racetrackWebcamPanel.getPainter();
        int mouseX = painter.fromDisplayX(mouseEvent.getX());
        int mouseY = painter.fromDisplayY(mouseEvent.getY());
        return Math.abs(mouseX - p.x) < diameter && Math.abs(mouseY - p.y)< diameter;
    }

    private void setCursorType(int cursorType){
        if(racetrackWebcamPanel.getCursor().getType() != cursorType) {
            racetrackWebcamPanel.setCursor(new Cursor(cursorType));
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if(closeEnough(mouseEvent,racetrackWebcamPanel.getHotSpotTrack().getRaceStartPosition(),20)){
            hotSpotPositioning = true;
        }else if(closeEnough(mouseEvent,racetrackWebcamPanel.getHotSpotTrack().getMovePosition(), 10)){
            trackPositioning = true;
        }else if(closeEnough(mouseEvent,racetrackWebcamPanel.getHotSpotTrack().getStretchPosition(),10)){
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
        if(!(hotSpotPositioning|trackPositioning|trackStretching))
            return;
        hotSpotPositioning = trackPositioning = trackStretching = false;
        HotSpotDetector hotSpotDetector = racetrackWebcamPanel.getHotSpotDetector();
        HotSpotTrack hotSpotTrack = racetrackWebcamPanel.getHotSpotTrack();
        hotSpotDetector.clearHotSpots();
        hotSpotDetector.addHotSpot(hotSpotTrack.getRaceStartHotSpot());
        hotSpotTrack.getLanes().stream()
                .forEach(hs->hotSpotDetector.addHotSpot(hs));
        hotSpotDetector.setEnabled(true);
    }
}
