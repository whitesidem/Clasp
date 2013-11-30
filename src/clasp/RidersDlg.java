package clasp;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

/**
 * Title:        Clasp Version 2
 * Description:  A Game of Two Enemies
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author M.Whiteside
 * @version 1.0
 */

public class RidersDlg extends JDialog {
  JPanel mainPanel = new JPanel();
  JPanel pnButtons = new JPanel();
  BorderLayout mainLayout1 = new BorderLayout();
  RiderPanel ridersPanel;
  public static RidersDlg dialog;
  private int player;
  private ClaspGameStatus gameStatus;
  FlowLayout buttonLayout = new FlowLayout(FlowLayout.CENTER);
  JButton btOK = new JButton("OK");

  public RidersDlg(Frame frame, int player, ClaspGameStatus gameStatus) {
    super(frame, ((player==ClaspConstants.P1)?"Blue Team ":"Red Team ")+"Planet Shares", true);
    this.player=player;
    this.gameStatus=gameStatus;
    ridersPanel = new RiderPanel(player,gameStatus);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    mainPanel.setLayout(mainLayout1);
    //Button Setup
    if (player==ClaspConstants.P1) pnButtons.setBackground(Color.blue);
    else pnButtons.setBackground(Color.red);
    pnButtons.setLayout(buttonLayout);
    pnButtons.add(btOK);
    btOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });

    mainPanel.add(ridersPanel, BorderLayout.CENTER);
    mainPanel.add(pnButtons, BorderLayout.SOUTH);
    getContentPane().add(mainPanel);
    getRootPane().setDefaultButton(btOK);

  }
      /**
     * Set up the dialog.  The first argument can be null,
     * but it really should be a component in the dialog's
     * controlling frame.
     */
    public static void initialize(Component comp, int player, ClaspGameStatus gameStatus) {
        Frame frame = JOptionPane.getFrameForComponent(comp);
        dialog = new RidersDlg(frame, player, gameStatus);
        dialog.pack();
    }

    /**
     * Show the initialized dialog.  The first argument should
     * be null if you want the dialog to come up in the center
     * of the screen.  Otherwise, the argument should be the
     * component on top of which the dialog should appear.
     */
    public static void showDialog(Component comp) {
        if (dialog != null) {
            dialog.setLocationRelativeTo(comp);
            dialog.setVisible(true);
        } else {
            System.err.println("ListDialog requires you to call initialize "
                               + "before calling showDialog.");
        }
    }

 //===============Paint method for the grid==================
  public void paint(Graphics g){
    super.paint(g);
    ridersPanel.paintRiders(g);   //Paint Riders
    }
}