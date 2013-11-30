package clasp;

import javax.swing.UIManager;
import java.awt.*;
import java.util.*; //Observer

/**
 * Title:        Clasp Version 2 Application
 * Description:  A Game of Two Enemies. Application creates the main
 *               window and the main grid view and the the data model.
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author M.Whiteside
 * @version 1.0
 */

public class ClaspApplication {
  boolean packFrame = false;
  Splash splash=new Splash();

  /**Main method*/
  public static void main(String[] args) {
    try {
      //Operating System Specific Look and feel
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    //Create the application
    claspApp = new ClaspApplication();
    claspApp.init();
  }

  public void init() {
    gameStatus = new ClaspGameStatus();
    mainFrame = new MainFrame(gameStatus);
    gameStatus.setMainFrame(mainFrame);

    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame) {
      mainFrame.pack();
    }
    else {
      mainFrame.validate();
    }

    splash.setSize( new Dimension(405,420));
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension splashSize = splash.getSize();
    splash.setLocation((screenSize.width - splashSize.width) / 2, (screenSize.height - splashSize.height) / 2);
    splash.show();

    //Center the window to resolution screen size
    MainFrame.setVisualBounds(mainFrame);

    //create and link model and view
    claspModel = new ClaspModel(gameStatus);
    gameStatus.setClaspModel(claspModel);

    claspView = new ClaspView(gameStatus);
    gameStatus.setClaspView(claspView);
    claspModel.addObserver((Observer)claspView);

    //Add View to the frame
    mainFrame.getContentPane().add(claspView,BorderLayout.CENTER);

    //Start Game
    gameStatus.initialiseGame();

  }


  //Private Members
  private static ClaspModel claspModel;
  private static ClaspView claspView;
  private static ClaspGameStatus gameStatus;
  private static MainFrame mainFrame;
  private static ClaspApplication claspApp;

}