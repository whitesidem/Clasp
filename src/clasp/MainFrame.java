package clasp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;   //Observer


/**
 * Title:        Clasp Main Frame
 * Description:  Main Window with menus that holds the View of the grid etc.
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author M.Whiteside
 * @version 1.0
 */

public class MainFrame extends JFrame implements ClaspConstants {
//Main Content Panel and Layout
  JPanel contentPane;
  BorderLayout borderLayout1 = new BorderLayout();
//Menu
  JMenuBar mainMenuBar = new JMenuBar();
  JMenu mainMenu = new JMenu();
  JMenuItem menuFileExit = new JMenuItem();
  JMenuItem menuOptions = new JMenuItem();
  JMenu menuHelp = new JMenu();
  JMenuItem menuHelpAbout = new JMenuItem();
//Status Bar
  ClaspStatusBar statusBar = new ClaspStatusBar();
  JPanel pnCtrl = new JPanel();
  BorderLayout ctrlPanelLayout  = new BorderLayout();
  Box ctrlBox = Box.createVerticalBox();

// Private members
  private ClaspGameStatus gameStatus;
  private ViewPlayerInfo[] vplayerInfoArray;

  JTextField ebMagnet = new JTextField();
  JTextField ebSch = new JTextField();
  JTextField ebAssassin = new JTextField();
  JTextField ebSniper = new JTextField();
  JTextField ebLeveller = new JTextField();
  JTextField ebJoker = new JTextField();
  JTextField ebTrophy1 = new JTextField();
  JTextField ebTrophy2 = new JTextField();

  static Random rand= new Random();
  static final int GRID_RAND=0;
  static final int GRID_RACE=1;
  static final int GRID_RACE_RESTORE=2;

  /**Construct the frame*/
  public MainFrame(ClaspGameStatus gameStatus) {
    this.gameStatus=gameStatus;
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**Component initialization*/
  private void jbInit() throws Exception  {
    setIconImage(Toolkit.getDefaultToolkit().createImage(MainFrame.class.getResource("skull.gif")));
    //Setup ContentPane Layout
    contentPane = (JPanel) this.getContentPane();
    contentPane.setLayout(borderLayout1);
    //Sizing of Frame
    //this.setResizable(false);
    this.setSize(new Dimension(700, 580));

//    this.setTitle("Zig/Zag/Zog - \"The Zog Matrix\"");


    this.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        this_keyReleased(e);
      }
    });



    //Main Menu Setup
    mainMenu.setText("File");
    menuFileExit.setText("Exit");
    menuFileExit.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        menuFileExit_actionPerformed(e);
      }
    });

    menuOptions.setText("Options...");
    menuOptions.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        menuOptions_actionPerformed(e);
      }
    });

    menuHelp.setText("Help");
    menuHelpAbout.setText("About");
    menuHelpAbout.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        menuHelpAbout_actionPerformed(e);
      }
    });
    mainMenu.add(menuOptions);
    mainMenu.add(menuFileExit);
    menuHelp.add(menuHelpAbout);
    mainMenuBar.add(mainMenu);
    mainMenuBar.add(menuHelp);
    this.setJMenuBar(mainMenuBar);

    //Create Control Panel
    pnCtrl.setPreferredSize(new Dimension(200, 0));
    pnCtrl.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    pnCtrl.setMinimumSize(new Dimension(200, 66));
    pnCtrl.setToolTipText("");
    pnCtrl.setLayout(ctrlPanelLayout);
    contentPane.add(pnCtrl, BorderLayout.EAST);

    vplayerInfoArray = new ViewPlayerInfo[2];
    vplayerInfoArray[0] = new ViewPlayerInfo(P1,gameStatus);
    vplayerInfoArray[1] = new ViewPlayerInfo(P2,gameStatus);
    ctrlBox.add(vplayerInfoArray[P1]);
    ctrlBox.add(vplayerInfoArray[P2]);
    dispPieces();       //Add piece Display
    pnCtrl.add(ctrlBox, BorderLayout.NORTH);



    //Add Status Bar to bottom
    statusBar.setPreferredSize(new Dimension(0, 30));
    contentPane.add(statusBar, BorderLayout.SOUTH);
  }

  //Update the scores panel with Player 1 and 2 stats
  public void updateCtrlPanel() {
    vplayerInfoArray[P1].updatePlayerInfo();
    vplayerInfoArray[P2].updatePlayerInfo();
  }

//========================Menu Actions========================
  /**File | Exit action performed*/
  public void menuFileExit_actionPerformed(ActionEvent e) {
    System.exit(0);
  }

  /**File | Options action performed*/
  public void menuOptions_actionPerformed(ActionEvent e) {
    if (gameStatus.getClaspModel().getLevelMode()==GM_RACE) return; //- Mid race dont call options
    PropertiesFrame options = new PropertiesFrame(this);
    options.setSize( new Dimension(360,200));
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = options.getSize();
    options.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    //Make a copy of the poperties before calling frame to change them
    ClaspProperties oldProps;
    ClaspProperties newProps;

    try {
      oldProps=(ClaspProperties)gameStatus.getClaspProperties().clone();   //Make a clone of Old
      newProps=(ClaspProperties)gameStatus.getClaspProperties().clone();   //Make a clone of New Properties
      options.setClaspProperties(newProps);  //pass properties object
      options.setModal(true);
      options.show();
      gameStatus.setClaspProperties(newProps);            //set Current Properties to new Properties
      if ((oldProps.getGridRows()!=newProps.getGridRows()) || (oldProps.getGridCols()!=newProps.getGridCols())) {
        restructureGridDisplay(oldProps);
        }
    }
    catch(CloneNotSupportedException exc){
      System.out.println(exc);
    }
  }

protected void restructureGridDisplay(ClaspProperties oldProps) {
  gameStatus.getClaspModel().restructureGrid(oldProps);            //Restructure from Old Properties to current properties
  setVisible(false);
  MainFrame.setVisualBounds(this);
  setVisible(true);
  }

//Change grid Shape if using random grids
protected void setGridShape(int mode){
  ClaspProperties oldProps;
  ClaspProperties newProps;
  int rows;
  int cols;
  if (mode==GRID_RACE) {                //Races - set to Race lines
    rows=9;
    cols=6;                           //Number of Riders
    }
  else  {
    if (!gameStatus.getClaspProperties().isRandGrid()) {             //Not random grids
      if (gameStatus.getClaspModel().needRaceRestore==false) return; //if no restore needed dont change anything
        rows=gameStatus.getClaspModel().storePreRaceRows;     //Just restoring grid after race
        cols=gameStatus.getClaspModel().storePreRaceCols;
      }
    else {                            //Do random size
      rows=6+rand.nextInt(5);
      cols=rows+rand.nextInt(2);
      }
    }
  try {
      oldProps=(ClaspProperties)gameStatus.getClaspProperties().clone();   //Make a clone of Current
      newProps=gameStatus.getClaspProperties();                            //Get Current Properties
      newProps.setGridRows(rows);                                             //Change Current info
      newProps.setGridCols(cols);
      restructureGridDisplay(oldProps);
    }
    catch(CloneNotSupportedException exc){
      System.out.println(exc);
    }
  }



  /**Help | About action performed*/
  public void menuHelpAbout_actionPerformed(ActionEvent e) {
    MainFrame_AboutBox dlg = new MainFrame_AboutBox(this);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.show();
  }

  /**Overridden so we can exit when window is closed*/
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      menuFileExit_actionPerformed(null);
    }
  }

  private void doLeft(){
    if (gameStatus.getClaspModel().isProcessing()) return;
    gameStatus.getClaspModel().selectFreeColumn(CLASP_LEFT);
  }
  private void doRight(){
    if (gameStatus.getClaspModel().isProcessing()) return;
    gameStatus.getClaspModel().selectFreeColumn(CLASP_RIGHT);
   }
  private void doDrop(){
    if (gameStatus.getClaspModel().isProcessing()) return;
    if (CheckChoosePiece()==false) return;  //Check if choose piece selected yet
    gameStatus.getClaspModel().performDrop();
  }

  //Check for KeyPresses
  void this_keyReleased(KeyEvent e) {
    if (gameStatus.getClaspModel().isProcessing()) return;
    int player=gameStatus.getCurrPlayer();

    switch (e.getKeyCode()) {
      case KeyEvent.VK_1:
        gameStatus.getClaspModel().switchToStdPiece();
        return;
      case KeyEvent.VK_2:
        if (gameStatus.getTotalMagnet(player)>0)
          gameStatus.getClaspModel().switchToMagnet();
        return;
      case KeyEvent.VK_3:
        if (gameStatus.getTotalSch(player)>0)
          gameStatus.getClaspModel().switchToSearchDestroy();
        return;
      case KeyEvent.VK_4:
        if (gameStatus.getTotalAssassin(player)>0)
          gameStatus.getClaspModel().switchToAssassin();
        return;
      case KeyEvent.VK_5:
        if (gameStatus.getTotalSniper(player)>0)
          gameStatus.getClaspModel().switchToSniper();
        return;
      case KeyEvent.VK_6:
        if (gameStatus.getTotalLeveller(player)>0)
          gameStatus.getClaspModel().switchToLeveller();
        return;
      case KeyEvent.VK_7:
        if (gameStatus.getTotalTrophy1(player)>0)
          gameStatus.getClaspModel().switchToTrophy1();
        return;
      case KeyEvent.VK_8:
        if (gameStatus.getTotalTrophy2(player)>0)
          gameStatus.getClaspModel().switchToTrophy2();
        return;
      case KeyEvent.VK_RIGHT:
        doRight();
        return;
      case KeyEvent.VK_LEFT:
        doLeft();
        return;
      case KeyEvent.VK_DOWN:
        doDrop();
        return;
    }
  }

protected Dimension getRequiredBounds(){
  Dimension d = pnCtrl.getPreferredSize();
  Dimension res = new Dimension();
  res.setSize((COL_SPACER*gameStatus.getGridCols())+(ClaspView.GRID_X_OFFSET*2)+((int)d.getWidth())+30, (ROW_SPACER*gameStatus.getGridRows())+(ClaspView.GRID_Y_OFFSET*2)+50);
  return(res);
  }

//Center the window to resolution screen size
protected static void setVisualBounds(MainFrame frame){
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setSize(frame.getRequiredBounds());
    Dimension frameSize = frame.getSize();
    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }

//Check if choose piece selected yet
//if not give warning message and return false
private boolean CheckChoosePiece(){
  ClaspBasePiece piece=gameStatus.getClaspModel().getChoosePiece();
  if (piece==null) return false; //Piece has been selected
  if (!piece.getClass().getName().equals("clasp.SelectPiece")) return true;
  JOptionPane.showMessageDialog(this,"You Must Select A piece number first");
  return false;
  }

//Add info to control panel about the number of pieces available
private void dispPieces(){
  Box bxPieces=Box.createVerticalBox();
  Box bxStd=Box.createHorizontalBox();
  Box bxMagnet=Box.createHorizontalBox();
  Box bxSch=Box.createHorizontalBox();
  Box bxAssassin=Box.createHorizontalBox();
  Box bxSniper=Box.createHorizontalBox();
  Box bxLeveller=Box.createHorizontalBox();
  Box bxJoker=Box.createHorizontalBox();
  Box bxTrophy1=Box.createHorizontalBox();
  Box bxTrophy2=Box.createHorizontalBox();
  //Std Piece
  JLabel txtStd = new JLabel("(1) Std",JLabel.RIGHT);
  txtStd.setPreferredSize(new Dimension(120, 17));
  txtStd.setMinimumSize(new Dimension(120, 17));
  bxStd.add(txtStd);
  bxStd.add(Box.createHorizontalStrut(2));  //Force padding
  bxStd.add(Box.createHorizontalStrut(4));  //Force padding
  bxStd.add(Box.createHorizontalStrut(2));  //Force padding
  bxStd.add(Box.createGlue()); //Pad to End

  //Magnet
  JLabel txtMagnet = new JLabel("(2) Magnet",JLabel.RIGHT);
  txtMagnet.setPreferredSize(new Dimension(120, 17));
  txtMagnet.setMinimumSize(new Dimension(120, 17));
  bxMagnet.add(txtMagnet);
  bxMagnet.add(Box.createHorizontalStrut(2));  //Force padding
  ebMagnet.setPreferredSize(new Dimension(30, 21));
  ebMagnet.setMinimumSize(new Dimension(30, 21));
  ebMagnet.setMaximumSize(new Dimension(30, 21));
  ebMagnet.setEditable(false);
  ebMagnet.setBackground(Color.black);
  ebMagnet.setForeground(Color.green);
  bxMagnet.add(ebMagnet);
  bxMagnet.add(Box.createHorizontalStrut(4));  //Force padding
  bxMagnet.add(Box.createHorizontalStrut(2));  //Force padding
  bxMagnet.add(Box.createGlue()); //Pad to End

  //Sch
  JLabel txtSch = new JLabel("(3) Search/Destroy",JLabel.RIGHT);
  txtSch.setPreferredSize(new Dimension(120, 17));
  txtSch.setMinimumSize(new Dimension(120, 17));
  bxSch.add(txtSch);
  bxSch.add(Box.createHorizontalStrut(2));  //Force padding
  ebSch.setPreferredSize(new Dimension(30, 21));
  ebSch.setMinimumSize(new Dimension(30, 21));
  ebSch.setMaximumSize(new Dimension(30, 21));
  ebSch.setEditable(false);
  ebSch.setBackground(Color.black);
  ebSch.setForeground(Color.green);
  bxSch.add(ebSch);
  bxSch.add(Box.createHorizontalStrut(4));  //Force padding
  bxSch.add(Box.createHorizontalStrut(2));  //Force padding
  bxSch.add(Box.createGlue()); //Pad to End

  //Assassin
  JLabel txtAssassin = new JLabel("(4) Assassin",JLabel.RIGHT);
  txtAssassin.setPreferredSize(new Dimension(120, 17));
  txtAssassin.setMinimumSize(new Dimension(120, 17));
  bxAssassin.add(txtAssassin);
  bxAssassin.add(Box.createHorizontalStrut(2));  //Force padding
  ebAssassin.setPreferredSize(new Dimension(30, 21));
  ebAssassin.setMinimumSize(new Dimension(30, 21));
  ebAssassin.setMaximumSize(new Dimension(30, 21));
  ebAssassin.setEditable(false);
  ebAssassin.setBackground(Color.black);
  ebAssassin.setForeground(Color.green);
  bxAssassin.add(ebAssassin);
  bxAssassin.add(Box.createHorizontalStrut(4));  //Force padding
  bxAssassin.add(Box.createHorizontalStrut(2));  //Force padding
  bxAssassin.add(Box.createGlue()); //Pad to End

  //Sniper
  JLabel txtSniper = new JLabel("(5) Sniper",JLabel.RIGHT);
  txtSniper.setPreferredSize(new Dimension(120, 17));
  txtSniper.setMinimumSize(new Dimension(120, 17));
  bxSniper.add(txtSniper);
  bxSniper.add(Box.createHorizontalStrut(2));  //Force padding
  ebSniper.setPreferredSize(new Dimension(30, 21));
  ebSniper.setMinimumSize(new Dimension(30, 21));
  ebSniper.setMaximumSize(new Dimension(30, 21));
  ebSniper.setEditable(false);
  ebSniper.setBackground(Color.black);
  ebSniper.setForeground(Color.green);
  bxSniper.add(ebSniper);
  bxSniper.add(Box.createHorizontalStrut(4));  //Force padding
  bxSniper.add(Box.createHorizontalStrut(2));  //Force padding
  bxSniper.add(Box.createGlue()); //Pad to End

  //Leveller
  JLabel txtLeveller = new JLabel("(6) Leveller",JLabel.RIGHT);
  txtLeveller.setPreferredSize(new Dimension(120, 17));
  txtLeveller.setMinimumSize(new Dimension(120, 17));
  bxLeveller.add(txtLeveller);
  bxLeveller.add(Box.createHorizontalStrut(2));  //Force padding
  ebLeveller.setPreferredSize(new Dimension(30, 21));
  ebLeveller.setMinimumSize(new Dimension(30, 21));
  ebLeveller.setMaximumSize(new Dimension(30, 21));
  ebLeveller.setEditable(false);
  ebLeveller.setBackground(Color.black);
  ebLeveller.setForeground(Color.green);
  bxLeveller.add(ebLeveller);
  bxLeveller.add(Box.createHorizontalStrut(4));  //Force padding
  bxLeveller.add(Box.createHorizontalStrut(2));  //Force padding
  bxLeveller.add(Box.createGlue()); //Pad to End
  //Trophy1
  JLabel txtTrophy1 = new JLabel("(7) Gambler Trophy",JLabel.RIGHT);
  txtTrophy1.setPreferredSize(new Dimension(120, 17));
  txtTrophy1.setMinimumSize(new Dimension(120, 17));
  bxTrophy1.add(txtTrophy1);
  bxTrophy1.add(Box.createHorizontalStrut(2));  //Force padding
  ebTrophy1.setPreferredSize(new Dimension(30, 21));
  ebTrophy1.setMinimumSize(new Dimension(30, 21));
  ebTrophy1.setMaximumSize(new Dimension(30, 21));
  ebTrophy1.setEditable(false);
  ebTrophy1.setBackground(Color.black);
  ebTrophy1.setForeground(Color.green);
  bxTrophy1.add(ebTrophy1);
  bxTrophy1.add(Box.createHorizontalStrut(4));  //Force padding
  bxTrophy1.add(Box.createHorizontalStrut(2));  //Force padding
  bxTrophy1.add(Box.createGlue()); //Pad to End

  //Trophy2
  JLabel txtTrophy2 = new JLabel("(8) Owner Trophy",JLabel.RIGHT);
  txtTrophy2.setPreferredSize(new Dimension(120, 17));
  txtTrophy2.setMinimumSize(new Dimension(120, 17));
  bxTrophy2.add(txtTrophy2);
  bxTrophy2.add(Box.createHorizontalStrut(2));  //Force padding
  ebTrophy2.setPreferredSize(new Dimension(30, 21));
  ebTrophy2.setMinimumSize(new Dimension(30, 21));
  ebTrophy2.setMaximumSize(new Dimension(30, 21));
  ebTrophy2.setEditable(false);
  ebTrophy2.setBackground(Color.black);
  ebTrophy2.setForeground(Color.green);
  bxTrophy2.add(ebTrophy2);
  bxTrophy2.add(Box.createHorizontalStrut(4));  //Force padding
  bxTrophy2.add(Box.createHorizontalStrut(2));  //Force padding
  bxTrophy2.add(Box.createGlue()); //Pad to End

  //Joker
  JLabel txtJoker = new JLabel("Joker",JLabel.RIGHT);
  txtJoker.setPreferredSize(new Dimension(120, 17));
  txtJoker.setMinimumSize(new Dimension(120, 17));
  bxJoker.add(txtJoker);
  bxJoker.add(Box.createHorizontalStrut(2));  //Force padding
  ebJoker.setPreferredSize(new Dimension(30, 21));
  ebJoker.setMinimumSize(new Dimension(30, 21));
  ebJoker.setMaximumSize(new Dimension(30, 21));
  ebJoker.setEditable(false);
  ebJoker.setBackground(Color.black);
  ebJoker.setForeground(Color.green);
  bxJoker.add(ebJoker);
  bxJoker.add(Box.createHorizontalStrut(4));  //Force padding
  bxJoker.add(Box.createHorizontalStrut(2));  //Force padding
  bxJoker.add(Box.createGlue()); //Pad to End



  bxPieces.add(bxStd);
  bxPieces.add(bxMagnet);
  bxPieces.add(bxSch);
  bxPieces.add(bxAssassin);
  bxPieces.add(bxSniper);
  bxPieces.add(bxLeveller);
  bxPieces.add(bxTrophy1);
  bxPieces.add(bxTrophy2);
  bxPieces.add(bxJoker);
  bxPieces.add(Box.createGlue()); //Pad to End
  ctrlBox.add(bxPieces);
  }

protected void updatePieceDisplay(){
  int player=gameStatus.getCurrPlayer();
  int amount;
  amount=gameStatus.getTotalMagnet(player);
  if (amount<0) amount=0;
  ebMagnet.setText(String.valueOf(amount));
  amount=gameStatus.getTotalSch(player);
  if (amount<0) amount=0;
  ebSch.setText(String.valueOf(amount));
  amount=gameStatus.getTotalAssassin(player);
  if (amount<0) amount=0;
  ebAssassin.setText(String.valueOf(amount));
  amount=gameStatus.getTotalSniper(player);
  if (amount<0) amount=0;
  ebSniper.setText(String.valueOf(amount));
  amount=gameStatus.getTotalLeveller(player);
  if (amount<0) amount=0;
  ebLeveller.setText(String.valueOf(amount));
  amount=gameStatus.getTotalJoker(player);
  if (amount<0) amount=0;
  ebJoker.setText(String.valueOf(amount));
  amount=gameStatus.getTotalTrophy1(player);
  if (amount<=0) ebTrophy1.setText("NO");
  else ebTrophy1.setText("YES");
  amount=gameStatus.getTotalTrophy2(player);
  if (amount<=0) ebTrophy2.setText("NO");
  else ebTrophy2.setText("YES");
  }

}