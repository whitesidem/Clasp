package clasp;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * Title:        Clasp Status Bar
 * Description:  Displays Current Status of game
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author M.Whiteside
 * @version 1.0
 */

public class ClaspStatusBar extends JPanel implements ClaspConstants{

  //Constructor
  public ClaspStatusBar() {
    setLayout(new FlowLayout(FlowLayout.LEFT, 10, 3));
    setBackground(Color.lightGray);
    setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    add(levelPane);
    add(messagePane);
    messagePane.setPreferredSize(new Dimension(300,20));
  }

  public void setLevelText(int level) {
    levelPane.setText("Level: " + String.valueOf(level));
  }

  public void setMessageText(String message) {
    messagePane.setText("Type: " + message);
  }

  private StatusPane levelPane = new StatusPane("1");
  private StatusPane messagePane = new StatusPane("Standard");

  //Inner class to define status panes
  class StatusPane extends JLabel {
    public StatusPane(String text) {
      setHorizontalAlignment(CENTER);
      setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
      setPreferredSize(new Dimension(100,20));
      setText(text);
      }
  }

}




