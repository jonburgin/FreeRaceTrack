package net.burgin.racetrack.gui;

import sun.swing.DefaultLookup;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by jonburgin on 12/16/15.
 */
public class EditableListCellRenderer extends DefaultListCellRenderer {
    private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    private static final Border DEFAULT_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    public EditableListCellRenderer() {
        super();
    }

    @Override
    public Component getListCellRendererComponent(JList<?> jList, Object o, int index, boolean isSelected, boolean cellHasFocus) {
            this.setComponentOrientation(jList.getComponentOrientation());
            Color dropBackgroundColor = null;
            Color dropForegroundColor = null;
            JList.DropLocation dropLocation = jList.getDropLocation();
            if(dropLocation != null && !dropLocation.isInsert() && dropLocation.getIndex() == index) {
                dropBackgroundColor = DefaultLookup.getColor(this, this.ui, "List.dropCellBackground");
                dropForegroundColor = DefaultLookup.getColor(this, this.ui, "List.dropCellForeground");
                isSelected = true;
            }

            if(isSelected) {
                this.setBackground(dropBackgroundColor == null?jList.getSelectionBackground():dropBackgroundColor);
                this.setForeground(dropForegroundColor == null?jList.getSelectionForeground():dropForegroundColor);
            } else {
                this.setBackground(jList.getBackground());
                this.setForeground(jList.getForeground());
            }

            if(o instanceof Icon) {
                this.setIcon((Icon)o);
                this.setText("");
            } else {
                this.setIcon((Icon)null);
                this.setText(o == null?"<New...>":o.toString());
            }

            this.setEnabled(jList.isEnabled());
            this.setFont(jList.getFont());
            Border border = null;
            if(cellHasFocus) {
                if(isSelected) {
                    border = DefaultLookup.getBorder(this, this.ui, "List.focusSelectedCellHighlightBorder");
                }

                if(border == null) {
                    border = DefaultLookup.getBorder(this, this.ui, "List.focusCellHighlightBorder");
                }
            } else {
                border = this.getNoFocusBorder();
            }

            this.setBorder(border);
            return this;
        }

    private Border getNoFocusBorder() {
        Border border = DefaultLookup.getBorder(this, this.ui, "List.cellNoFocusBorder");
        return System.getSecurityManager() != null?(border != null?border:SAFE_NO_FOCUS_BORDER):(border == null || noFocusBorder != null && noFocusBorder != DEFAULT_NO_FOCUS_BORDER?noFocusBorder:border);
    }
}
