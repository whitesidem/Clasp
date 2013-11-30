package clasp;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

/**
 * Title:        Weapon Info Dialog
 * Description:  A Game of Two Enemies
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author M.Whiteside
 * @version 1.0
 */

public class WeaponInfo extends JDialog {

  JPanel pnButtons = new JPanel();
  JPanel pnMain = new JPanel();
  JPanel pnPicture = new JPanel();
  JPanel pnTitle = new JPanel();
  JPanel pnPassword = new JPanel();
  JPanel pnImage = new JPanel();
  JPanel pnInfo = new JPanel();

  private int weaponType=0;
  private String strTitle= new String();
  private int player=0;
  private Image img=null;

  protected static final String WP_P1_1 = new String("SPIDER");
  protected static final String WP_P2_1 = new String("CACTUS");
  protected static final String WP_P1_2 = new String("OAK");
  protected static final String WP_P2_2 = new String("IRON");
  protected static final String WP_P1_3 = new String("MARS");
  protected static final String WP_P2_3 = new String("OXYGEN");
  protected static final String WP_P1_4 = new String("JUSTICE");
  protected static final String WP_P2_4 = new String("PRIDE");
  protected static final String WP_P1_5 = new String("DARKNESS");
  protected static final String WP_P2_5 = new String("INSANITY");
  protected static final String WP_ALL = new String("OBLIVION");
  protected static final String WP_FULL = new String("HADES");

  BorderLayout mainLayout = new BorderLayout();
  BorderLayout pictureLayout = new BorderLayout();
  BorderLayout infoLayout = new BorderLayout();
  FlowLayout buttonLayout = new FlowLayout(FlowLayout.CENTER);
  JButton btOK = new JButton("OK");
  JTextArea mmInfo = new JTextArea();

  public WeaponInfo(Frame frame, int player, int type) {
    super(frame, ((player==ClaspConstants.P1)?"Blue Team Weapon":"Red Team Weapon"),true);
    this.weaponType=type;
    this.player=player;
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    this.setResizable(false);
    String password=null;
    mmInfo.setLineWrap(true);
    mmInfo.setWrapStyleWord(true);

    if (weaponType==WeaponBuyDlg.MAGNET) {
      strTitle="Magnet";
      mmInfo.setText("When this piece has landed it will draw the nearest piece on either sides towards it. Tip: Can disrupt a finely planned structure");
      if (player==ClaspConstants.P1){
        img = Magnet.p1MagnetImage;
        password=WP_P1_1;
        }
      else {
        img = Magnet.p2MagnetImage;
        password=WP_P2_1;
        }
      }
    else if (weaponType==WeaponBuyDlg.SCH) {
      strTitle="Search/Destroy";
      mmInfo.setText("When two pieces are settled and lined up, most pieces between them will be zapped by the lazer. Tip: More than two can be aligned at one time ");
      if (player==ClaspConstants.P1){
        img = SearchDestroy.p1SchImage;
        password=WP_P1_2;
        }
      else {
        img = SearchDestroy.p2SchImage;
        password=WP_P2_2;
        }
      }
    else if (weaponType==WeaponBuyDlg.ASSASSIN) {
      strTitle="Assassin";
      mmInfo.setText("This Assassin piece will destroy all pieces around it when it lands (in either a cross or plus formation). The actual cannon formation used can not controlled. Tip: Be aware of what damage can be done by both formations");
      if (player==ClaspConstants.P1){
        img = AssassinPiece.p1CrossImage;
        password=WP_P1_3;
        }
      else {
        img = AssassinPiece.p2CrossImage;
        password=WP_P2_3;
        }
      }
    else if (weaponType==WeaponBuyDlg.SNIPER) {
      strTitle="Sniper";
      mmInfo.setText("This piece will scan for any opponent pieces that drop by its side then attempt to shoot that piece. Tip: Place this on a high single column tower for maximum usage. Sniper piece's aim deteriorates with each use");
      if (player==ClaspConstants.P1){
        img = Sniper.p1SniperImage;
        password=WP_P1_4;
        }
      else {
        img = Sniper.p2SniperImage;
        password=WP_P2_4;
        }
      }
    else if (weaponType==WeaponBuyDlg.LEVELLER) {
      strTitle="Leveller";
      mmInfo.setText("This piece will destroy most of the pieces in the row it lands on, including itself. Tip: Think about chain reaction from removing a full line");
      if (player==ClaspConstants.P1){
        img = Leveller.p1LevImage;
        password=WP_P1_5;
        }
      else {
        img = Leveller.p2LevImage;
        password=WP_P2_5;
        }
      }
    else if (weaponType==WeaponBuyDlg.JOKER) {
      strTitle="Joker";
      mmInfo.setText("This piece can only be placed on the first move of each grid. If placed on a grid and you win the grid you will get a cash multiplier bonus. Tip: If your Joker makes up part of a winning line, the Multiplier will be far greater.");
      if (player==ClaspConstants.P1){
        img = Joker.p1JokerImage;
        }
      else {
        img = Joker.p2JokerImage;
        }
      }
    else if (weaponType==WeaponBuyDlg.TROPHY1) {
      strTitle="Gamblers Trophy";
      mmInfo.setText("When this piece has landed it will turn all opponents pieces under it to your side. Tip: This Trophy can only be owned by one person at a time, so use it before your opponent wins it from you.");
      img = Trophy1.trophyImage;
      }
    else if (weaponType==WeaponBuyDlg.TROPHY2) {
      strTitle="Owners Trophy";
      mmInfo.setText("When this piece has landed it will turn all opponents pieces on the same row to your side. Tip: This Trophy can only be owned by one person at a time, so use it before your opponent wins it from you.");
      img = Trophy2.trophyImage;
      }
    else if (weaponType==WeaponBuyDlg.RIDER1) {
      strTitle="("+GambleDlg.worldStrings[0];
      mmInfo.setText("You have won some shares in this Planet. You will now get winnings if this planet wins whilst you are at the World Races");
      img = Rider.rider1Image;
      }
    else if (weaponType==WeaponBuyDlg.RIDER2) {
      strTitle="("+GambleDlg.worldStrings[1];
      mmInfo.setText("You have won some shares in this Planet. You will now get winnings if this planet wins whilst you are at the World Races");
      img = Rider.rider2Image;
      }
    else if (weaponType==WeaponBuyDlg.RIDER3) {
      strTitle="("+GambleDlg.worldStrings[2];
      mmInfo.setText("You have won some shares in this Planet. You will now get winnings if this planet wins whilst you are at the World Races");
      img = Rider.rider3Image;
      }
    else if (weaponType==WeaponBuyDlg.RIDER4) {
      strTitle="("+GambleDlg.worldStrings[3];
      mmInfo.setText("You have won some shares in this Planet. You will now get winnings if this planet wins whilst you are at the World Races");
      img = Rider.rider4Image;
      }
    else if (weaponType==WeaponBuyDlg.RIDER5) {
      strTitle="("+GambleDlg.worldStrings[4];
      mmInfo.setText("You have won some shares in this Planet. You will now get winnings if this planet wins whilst you are at the World Races");
      img = Rider.rider5Image;
      }
    else if (weaponType==WeaponBuyDlg.RIDER6) {
      strTitle="("+GambleDlg.worldStrings[5];
      mmInfo.setText("You have won some shares in this Planet. You will now get winnings if this planet wins whilst you are at the World Races");
      img = Rider.rider6Image;
      }

    Box bxPane=Box.createVerticalBox();
    pnMain.setLayout(mainLayout);
    getContentPane().add(pnMain,BorderLayout.CENTER);

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

    //Info Setup
    pnInfo.setBorder(BorderFactory.createEtchedBorder());
    pnInfo.setLayout(infoLayout);
    pnInfo.add(mmInfo, BorderLayout.CENTER);

    //Password Setup

    JLabel txtPassword = new JLabel(((player==ClaspConstants.P1)?"Blue Team":"Red Team") + " Weapon Level Unlock Password:");
    JLabel txtPassword2 = new JLabel(password);
    Font boldFont = txtPassword.getFont().deriveFont(Font.BOLD);
    txtPassword2.setFont(boldFont);
    pnPassword.setBorder(BorderFactory.createRaisedBevelBorder());
    pnPassword.add(txtPassword, null);
    pnPassword.add(txtPassword2, null);
    if ((weaponType!=WeaponBuyDlg.JOKER)
      && ((weaponType!=WeaponBuyDlg.RIDER1))
      && ((weaponType!=WeaponBuyDlg.RIDER2))
      && ((weaponType!=WeaponBuyDlg.RIDER3))
      && ((weaponType!=WeaponBuyDlg.RIDER4))
      && ((weaponType!=WeaponBuyDlg.RIDER5))
      && ((weaponType!=WeaponBuyDlg.RIDER6))
      && ((weaponType!=WeaponBuyDlg.TROPHY1))
      && ((weaponType!=WeaponBuyDlg.TROPHY2)))
      pnInfo.add(pnPassword,BorderLayout.SOUTH);  //Add password info - if weapon has a password
    //Picture Setup
    JLabel txtTitle = new JLabel(strTitle);
    pnTitle.add(txtTitle);
    pnTitle.setBorder(BorderFactory.createRaisedBevelBorder());
    pnImage.setBackground(Color.black);
    pnPicture.setBorder(BorderFactory.createEtchedBorder());
    pnPicture.setLayout(pictureLayout);
    pnPicture.add(pnTitle,BorderLayout.NORTH);
    pnPicture.add(pnImage,BorderLayout.CENTER);
    pnPicture.setMaximumSize(new Dimension(100,100));
    pnPicture.setMinimumSize(new Dimension(100,100));
    pnPicture.setPreferredSize(new Dimension(100,100));

    pnMain.add(pnButtons, BorderLayout.SOUTH);
    pnMain.add(pnPicture, BorderLayout.WEST);
    pnMain.add(pnInfo, BorderLayout.CENTER);
    getRootPane().setDefaultButton(btOK);

  }

public static void showWeaponInfo(JFrame parent, int player, int type){
  WeaponInfo dialog = new WeaponInfo(parent,player,type);
  dialog.setSize( new Dimension(450,180));
  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  Dimension frameSize = dialog.getSize();
  dialog.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
  dialog.show();
  }

  //===============Paint method for the grid==================
  public void paint(Graphics g){
    super.paint(g);
    Graphics2D g2D=(Graphics2D)pnImage.getGraphics();
    g2D.drawImage(img,20,20,this);
  }



}