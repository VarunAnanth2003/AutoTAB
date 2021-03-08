package com.flns.autotab;
import javax.swing.JFrame;

import java.awt.*;


public class PaintableFrame extends JFrame {
  /**
   * Serial ID
   */
  private static final long serialVersionUID = -7457914054783750166L;
    public PaintableFrame(String name) {
        super(name);
    }
    public void paint(Graphics g)
    {
      super.paint(g);
      Graphics2D g2d = (Graphics2D) g;
      g2d.setColor(Color.black);
      g2d.drawRoundRect(50, 80, 500, 250, 20, 20);
      g2d.drawString("Drag .mid here", 260, 200);
    }
}