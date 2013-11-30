package clasp;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Title:        Clasp Version 2
 * Description:  A Game of Two Enemies
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author M.Whiteside
 * @version 1.0
 */

public class Splash extends JDialog {
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel pnMain = new JPanel();
  private Image splashImage;

  JButton btOK = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  public Splash(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public Splash() {
    this(null, "Zig/Zag/Zog", true);
  }

  void jbInit() throws Exception {
    this.setResizable(false);
    splashImage = new ImageIcon(ClaspView.class.getResource("Splash.gif")).getImage();
    pnMain.setLayout(gridBagLayout1);
    btOK.setToolTipText("");
    btOK.setText("New Game...");
    btOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btOK_actionPerformed(e);
      }
    });
    getContentPane().add(pnMain,BorderLayout.CENTER);
    pnMain.add(btOK,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(264, 72, 9, 222), 1, 0));
  }

  //===============Paint method for the splash==================
  public void paint(Graphics g){
    super.paint(g);
    Graphics2D g2D=(Graphics2D)pnMain.getGraphics();
    g2D.drawImage(splashImage,0,0,this);
    btOK.repaint();
  }

  void btOK_actionPerformed(ActionEvent e) {
    this.dispose();
  }


}