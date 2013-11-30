package clasp;

import javax.swing.JPanel;
import javax.swing.border.*;
import java.lang.Object.*;

/**
 * Title:        Clasp View of Player Info
 * Description:  View for Player Stats
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author M.Whiteside
 * @version 1.0
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;


public class ViewPlayerInfo extends JPanel implements ClaspConstants{

//private JTextField ebRows=new JTextField("");
//private JTextField ebCash=new JTextField("");
//private JTextField ebGridsWon=new JTextField("");
private JLabel txtRows= new JLabel("",JLabel.RIGHT);
private JLabel txtCash= new JLabel("",JLabel.RIGHT);
private JLabel txtGridsWon= new JLabel("",JLabel.RIGHT);
private int player;
private ClaspGameStatus gameStatus;

BorderLayout borderLayout1 = new BorderLayout();


  public ViewPlayerInfo(int player, ClaspGameStatus gameStatus) {
    this.player=player;
    this.gameStatus=gameStatus;           //All main units point to the main controller
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void updatePlayerInfo(){
    txtRows.setText(String.valueOf(gameStatus.getTotalLines(player)));
    txtCash.setText("£"+String.valueOf(gameStatus.getCashTotal(player)));
    txtGridsWon.setText(String.valueOf(gameStatus.getGridsWon(player)));
  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);

    Box pane=Box.createVerticalBox();
    Font boldFont = txtRows.getFont().deriveFont(Font.BOLD);

    //Grids Won
    Box totGridsWon=Box.createHorizontalBox();
    JLabel txtlbGridsWon= new JLabel("Grids Won:",JLabel.RIGHT);
    txtlbGridsWon.setPreferredSize(new Dimension(70, 17));
    txtlbGridsWon.setMinimumSize(new Dimension(70, 17));
    totGridsWon.add(txtlbGridsWon);
    txtGridsWon.setPreferredSize(new Dimension(50, 17));
    txtGridsWon.setMinimumSize(new Dimension(50, 17));
    txtGridsWon.setFont(boldFont);
    totGridsWon.add(Box.createHorizontalStrut(2));  //Force padding
    totGridsWon.add(txtGridsWon);
    totGridsWon.add(Box.createHorizontalStrut(999));  //Force padding
    pane.add(totGridsWon);


    //Rows
    Box totRows=Box.createHorizontalBox();
    JLabel txtlbRows= new JLabel("Rows Won:",JLabel.RIGHT);
    txtlbRows.setPreferredSize(new Dimension(70, 17));
    txtlbRows.setMinimumSize(new Dimension(70, 17));
    totRows.add(txtlbRows);
    txtRows.setPreferredSize(new Dimension(50, 17));
    txtRows.setMinimumSize(new Dimension(50, 17));
    txtRows.setFont(boldFont);
    totRows.add(Box.createHorizontalStrut(2));  //Force padding
    totRows.add(txtRows);
    totRows.add(Box.createHorizontalStrut(999));  //Force Padding
//    totRows.add(Box.createGlue()); - this should work but dosnt so I used struct instead
    pane.add(totRows);


    //Cash
    Box totCash=Box.createHorizontalBox();
    JLabel txtlbCash= new JLabel("Cash:",JLabel.RIGHT);
    txtlbCash.setPreferredSize(new Dimension(70, 17));
    txtlbCash.setMinimumSize(new Dimension(70, 17));
    totCash.add(txtlbCash);
    txtCash.setPreferredSize(new Dimension(50, 17));
    txtCash.setMinimumSize(new Dimension(50, 17));
    txtCash.setFont(boldFont);
    totCash.add(Box.createHorizontalStrut(2));  //Force padding
    totCash.add(txtCash);
    totCash.add(Box.createHorizontalStrut(999));  //Force padding
    pane.add(totCash);

    TitledBorder titleBorder=new TitledBorder(new EtchedBorder(),
    (player==P1?"Blue Team:":"Red Team:")
    );
    this.setBorder(titleBorder);


    this.add(pane,BorderLayout.NORTH);
  }
}


