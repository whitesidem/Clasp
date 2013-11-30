package clasp;

import java.awt.*;
import javax.swing.JPanel;
import javax.swing.border.*;
import java.lang.Object.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Title:        Clasp Version 2
 * Description:  A Game of Two Enemies
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author M.Whiteside
 * @version 1.0
 */

public class RiderPanel extends JPanel {
  BorderLayout mainLayout = new BorderLayout();
  ClaspGameStatus gameStatus;
  int player;
  public static final int SHARE_PRICE=50;

  public JPanel pnRider1=new JPanel();
  public JPanel pnRider2=new JPanel();
  public JPanel pnRider3=new JPanel();
  public JPanel pnRider4=new JPanel();
  public JPanel pnRider5=new JPanel();
  public JPanel pnRider6=new JPanel();

  public RiderPanel(int player, ClaspGameStatus gameStatus) {
    this.player=player;
    this.gameStatus=gameStatus;
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  void jbInit() throws Exception {
    this.setLayout(mainLayout);
    this.setBackground(Color.black);
    this.setForeground(Color.white);
    Box bxRiders=Box.createHorizontalBox();
    pnRider1.setMaximumSize(new Dimension(100,100));
    pnRider1.setMinimumSize(new Dimension(100,100));
    pnRider1.setPreferredSize(new Dimension(100,100));
    pnRider2.setMaximumSize(new Dimension(100,100));
    pnRider2.setMinimumSize(new Dimension(100,100));
    pnRider2.setPreferredSize(new Dimension(100,100));
    pnRider3.setMaximumSize(new Dimension(100,100));
    pnRider3.setMinimumSize(new Dimension(100,100));
    pnRider3.setPreferredSize(new Dimension(100,100));
    pnRider4.setMaximumSize(new Dimension(100,100));
    pnRider4.setMinimumSize(new Dimension(100,100));
    pnRider4.setPreferredSize(new Dimension(100,100));
    pnRider5.setMaximumSize(new Dimension(100,100));
    pnRider5.setMinimumSize(new Dimension(100,100));
    pnRider5.setPreferredSize(new Dimension(100,100));
    pnRider6.setMaximumSize(new Dimension(100,100));
    pnRider6.setMinimumSize(new Dimension(100,100));
    pnRider6.setPreferredSize(new Dimension(100,100));

    pnRider1.setBackground(Color.black);
    pnRider2.setBackground(Color.black);
    pnRider3.setBackground(Color.black);
    pnRider4.setBackground(Color.black);
    pnRider5.setBackground(Color.black);
    pnRider6.setBackground(Color.black);

    pnRider1.setForeground(Color.white);
    pnRider2.setForeground(Color.white);
    pnRider3.setForeground(Color.white);
    pnRider4.setForeground(Color.white);
    pnRider5.setForeground(Color.white);
    pnRider6.setForeground(Color.white);

    bxRiders.add(pnRider1);
    bxRiders.add(pnRider2);
    bxRiders.add(pnRider3);
    bxRiders.add(pnRider4);
    bxRiders.add(pnRider5);
    bxRiders.add(pnRider6);

    pnRider1.setLayout(new BorderLayout());
    pnRider2.setLayout(new BorderLayout());
    pnRider3.setLayout(new BorderLayout());
    pnRider4.setLayout(new BorderLayout());
    pnRider5.setLayout(new BorderLayout());
    pnRider6.setLayout(new BorderLayout());

    JLabel lbrd1 = new JLabel(GambleDlg.worldStrings[0]);
    lbrd1.setForeground(Color.white);
    JLabel lbrd2 = new JLabel(GambleDlg.worldStrings[1]);
    lbrd2.setForeground(Color.white);
    JLabel lbrd3 = new JLabel(GambleDlg.worldStrings[2]);
    lbrd3.setForeground(Color.white);
    JLabel lbrd4 = new JLabel(GambleDlg.worldStrings[3]);
    lbrd4.setForeground(Color.white);
    JLabel lbrd5 = new JLabel(GambleDlg.worldStrings[4]);
    lbrd5.setForeground(Color.white);
    JLabel lbrd6 = new JLabel(GambleDlg.worldStrings[5]);
    lbrd6.setForeground(Color.white);

    pnRider1.add(lbrd1,BorderLayout.NORTH);
    pnRider2.add(lbrd2,BorderLayout.NORTH);
    pnRider3.add(lbrd3,BorderLayout.NORTH);
    pnRider4.add(lbrd4,BorderLayout.NORTH);
    pnRider5.add(lbrd5,BorderLayout.NORTH);
    pnRider6.add(lbrd6,BorderLayout.NORTH);

    JLabel blbrd1 = new JLabel(String.valueOf(gameStatus.getTotalRider(player,1)*SHARE_PRICE)+ " Shares");
    blbrd1.setForeground(Color.white);
    JLabel blbrd2 = new JLabel(String.valueOf(gameStatus.getTotalRider(player,2)*SHARE_PRICE)+ " Shares");
    blbrd2.setForeground(Color.white);
    JLabel blbrd3 = new JLabel(String.valueOf(gameStatus.getTotalRider(player,3)*SHARE_PRICE)+ " Shares");
    blbrd3.setForeground(Color.white);
    JLabel blbrd4 = new JLabel(String.valueOf(gameStatus.getTotalRider(player,4)*SHARE_PRICE)+ " Shares");
    blbrd4.setForeground(Color.white);
    JLabel blbrd5 = new JLabel(String.valueOf(gameStatus.getTotalRider(player,5)*SHARE_PRICE)+ " Shares");
    blbrd5.setForeground(Color.white);
    JLabel blbrd6 = new JLabel(String.valueOf(gameStatus.getTotalRider(player,6)*SHARE_PRICE)+ " Shares");
    blbrd6.setForeground(Color.white);

    pnRider1.add(blbrd1,BorderLayout.SOUTH);
    pnRider2.add(blbrd2,BorderLayout.SOUTH);
    pnRider3.add(blbrd3,BorderLayout.SOUTH);
    pnRider4.add(blbrd4,BorderLayout.SOUTH);
    pnRider5.add(blbrd5,BorderLayout.SOUTH);
    pnRider6.add(blbrd6,BorderLayout.SOUTH);


    JLabel txtlbGridsWon= new JLabel("Grids Won:",JLabel.RIGHT);
    this.add(bxRiders,BorderLayout.CENTER);

  }

 //===============Paint Ridersmethod for the grid==================
  public void paintRiders(Graphics g){
    Graphics2D g2D;
    g2D=(Graphics2D)pnRider1.getGraphics();
    g2D.drawImage(Rider.rider1Image,1,20,this);
    g2D=(Graphics2D)pnRider2.getGraphics();
    g2D.drawImage(Rider.rider2Image,1,20,this);
    g2D=(Graphics2D)pnRider3.getGraphics();
    g2D.drawImage(Rider.rider3Image,1,20,this);
    g2D=(Graphics2D)pnRider4.getGraphics();
    g2D.drawImage(Rider.rider4Image,1,20,this);
    g2D=(Graphics2D)pnRider5.getGraphics();
    g2D.drawImage(Rider.rider5Image,1,20,this);
    g2D=(Graphics2D)pnRider6.getGraphics();
    g2D.drawImage(Rider.rider6Image,1,20,this);
  }


}