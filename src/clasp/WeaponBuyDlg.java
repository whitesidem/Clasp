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

public class WeaponBuyDlg extends JDialog {

  protected final static int RESEARCH = 0;
  protected final static int MAGNET = 1;
  protected final static int SCH = 2;
  protected final static int ASSASSIN = 3;
  protected final static int SNIPER = 4;
  protected final static int LEVELLER = 5;
  protected final static int JOKER = 6;
  protected final static int TROPHY1 = 7;
  protected final static int TROPHY2 = 8;
  protected final static int RIDER1 = 9;
  protected final static int RIDER2 = 10;
  protected final static int RIDER3 = 11;
  protected final static int RIDER4 = 12;
  protected final static int RIDER5 = 13;
  protected final static int RIDER6 = 14;

  final static int MAX_RESEARCH = 4;
  final static int MAX_MAGNET = 4;
  final static int MAX_SCH = 6;
  final static int MAX_ASSASSIN = 4;
  final static int MAX_SNIPER = 2;
  final static int MAX_LEVELLER = 2;
  private final static long RESEARCH_COST = 300;
  private final static long MAGNET_COST = 100;
  private final static long SCH_COST = 150;
  private final static long ASSASSIN_COST = 300;
  private final static long SNIPER_COST = 400;
  private final static long LEVELLER_COST = 600;

  private boolean initialised = false;

  ClaspGameStatus gameStatus;

  int player;
  long researchCost=0;
  JPanel pnEdits = new JPanel();
  JPanel pnButtons = new JPanel();
  JPanel mainPanel = new JPanel();
  BorderLayout borderLayoutMain = new BorderLayout();
  BorderLayout editLayout = new BorderLayout();
  FlowLayout buttonLayout = new FlowLayout(FlowLayout.CENTER);

  JTextField ebCash = new JTextField();
  JButton btOK = new JButton("OK");

  JLabel txtResearch = new JLabel("Research",JLabel.RIGHT);
  JTextField ebResearch = new JTextField();
  JSlider slResearch = new JSlider();

  JLabel txtMagnet = new JLabel("Magnet",JLabel.RIGHT);
  JTextField ebMagnet = new JTextField();
  JButton btMagnet;
  JSlider slMagnet = new JSlider();
  JLabel txtMagnetCash = new JLabel("£"+String.valueOf(MAGNET_COST),JLabel.RIGHT);

  JLabel txtSch = new JLabel("Search/Destroy",JLabel.RIGHT);
  JTextField ebSch = new JTextField();
  JButton btSch;
  JSlider slSch = new JSlider();
  JLabel txtSchCash = new JLabel("£"+String.valueOf(SCH_COST),JLabel.RIGHT);

  JLabel txtAssassin = new JLabel("Assasin",JLabel.RIGHT);
  JTextField ebAssassin = new JTextField();
  JButton btAssassin;
  JSlider slAssassin = new JSlider();
  JLabel txtAssassinCash = new JLabel("£"+String.valueOf(ASSASSIN_COST),JLabel.RIGHT);

  JLabel txtSniper = new JLabel("Sniper",JLabel.RIGHT);
  JTextField ebSniper = new JTextField();
  JButton btSniper;
  JSlider slSniper = new JSlider();
  JLabel txtSniperCash = new JLabel("£"+String.valueOf(SNIPER_COST),JLabel.RIGHT);

  JLabel txtLeveller = new JLabel("Leveller",JLabel.RIGHT);
  JTextField ebLeveller = new JTextField();
  JButton btLeveller;
  JSlider slLeveller = new JSlider();
  JLabel txtLevellerCash = new JLabel("£"+String.valueOf(LEVELLER_COST),JLabel.RIGHT);

  public WeaponBuyDlg(Frame frame, int player, ClaspGameStatus gameStatus) {
    super(frame, ((player==ClaspConstants.P1)?"Blue Team Weapon Shop":"Red Team Weapon Shop"), true);
    this.player=player;
    this.gameStatus=gameStatus;
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
   }

  void jbInit() throws Exception {
    mainPanel.setLayout(borderLayoutMain);
    getContentPane().add(mainPanel);
    researchCost = (gameStatus.getResearchLevel(player)*RESEARCH_COST);
    Box bxPane=Box.createVerticalBox();

    //Research
    Box bxResearch=Box.createHorizontalBox();
    JLabel txtResearchCash = new JLabel("£"+String.valueOf(researchCost),JLabel.RIGHT);
    txtResearch.setPreferredSize(new Dimension(90, 17));
    txtResearch.setMinimumSize(new Dimension(90, 17));
    bxResearch.add(txtResearch);
    bxResearch.add(Box.createHorizontalStrut(2));  //Force padding
    slResearch.addChangeListener(new SliderListener(RESEARCH));
    slResearch.setPreferredSize(new Dimension(60,30));
    slResearch.setMaximumSize(new Dimension(60,30));
    slResearch.setMinimumSize(new Dimension(60,30));
    slResearch.setMinimum(0);
    slResearch.setMaximum(5);
    slResearch.setSnapToTicks(true);
    slResearch.setMinorTickSpacing(1);
    slResearch.setPaintTicks(true);
    slResearch.setValue(0);
    if (gameStatus.getTotalMagnet(player)>=0) slResearch.setNextFocusableComponent(slMagnet);
    else slResearch.setNextFocusableComponent(btOK);
    bxResearch.add(slResearch);
    bxResearch.add(Box.createHorizontalStrut(2));  //Force padding
    ebResearch.setPreferredSize(new Dimension(30, 21));
    ebResearch.setMinimumSize(new Dimension(30, 21));
    ebResearch.setMaximumSize(new Dimension(30, 21));
    ebResearch.setEditable(false);
    ebResearch.setBackground(Color.black);
    ebResearch.setForeground(Color.green);
    bxResearch.add(ebResearch);
    bxResearch.add(Box.createHorizontalStrut(2));  //Force padding
    txtResearchCash.setPreferredSize(new Dimension(40, 17));
    txtResearchCash.setMinimumSize(new Dimension(40, 17));
    bxResearch.add(txtResearchCash);
    bxResearch.add(Box.createHorizontalStrut(4));  //Force padding
    bxResearch.add(Box.createGlue()); //Pad to End

    //Magnet
    Box bxMagnet=Box.createHorizontalBox();
    txtMagnet.setPreferredSize(new Dimension(90, 17));
    txtMagnet.setMinimumSize(new Dimension(90, 17));
    bxMagnet.add(txtMagnet);
    bxMagnet.add(Box.createHorizontalStrut(2));  //Force padding
    slMagnet.addChangeListener(new SliderListener(MAGNET));
    slMagnet.setPreferredSize(new Dimension(60,30));
    slMagnet.setMaximumSize(new Dimension(60,30));
    slMagnet.setMinimumSize(new Dimension(60,30));
    slMagnet.setMinimum(0);
    slMagnet.setMaximum(gameStatus.getMaxMagnet(player));
    slMagnet.setSnapToTicks(true);
    slMagnet.setMinorTickSpacing(1);
    slMagnet.setPaintTicks(true);
    slMagnet.setValue(0);
    if (gameStatus.getTotalSch(player)>=0) slMagnet.setNextFocusableComponent(slSch);
    else slMagnet.setNextFocusableComponent(btOK);
    bxMagnet.add(slMagnet);
    bxMagnet.add(Box.createHorizontalStrut(2));  //Force padding
    ebMagnet.setPreferredSize(new Dimension(30, 21));
    ebMagnet.setMinimumSize(new Dimension(30, 21));
    ebMagnet.setMaximumSize(new Dimension(30, 21));
    ebMagnet.setEditable(false);
    ebMagnet.setBackground(Color.black);
    ebMagnet.setForeground(Color.green);
    bxMagnet.add(ebMagnet);
    bxMagnet.add(Box.createHorizontalStrut(2));  //Force padding
    txtMagnetCash.setPreferredSize(new Dimension(40, 17));
    txtMagnetCash.setMinimumSize(new Dimension(40, 17));
    bxMagnet.add(txtMagnetCash);
    bxMagnet.add(Box.createHorizontalStrut(4));  //Force padding
    ImageIcon mag = new ImageIcon(Magnet.getTinyImage(player));
    btMagnet = new JButton("Info",mag);
    bxMagnet.add(btMagnet);
    btMagnet.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        WeaponInfo.showWeaponInfo(gameStatus.getMainFrame(), player, WeaponBuyDlg.MAGNET);    //Show info if the weapon reseached
      }
    });
    bxMagnet.add(Box.createHorizontalStrut(2));  //Force padding
    bxMagnet.add(Box.createGlue()); //Pad to End

    //Search and Destroy
    Box bxSch=Box.createHorizontalBox();
    txtSch.setPreferredSize(new Dimension(90, 17));
    txtSch.setMinimumSize(new Dimension(90, 17));
    bxSch.add(txtSch);
    bxSch.add(Box.createHorizontalStrut(2));  //Force padding
    slSch.addChangeListener(new SliderListener(SCH));
    slSch.setPreferredSize(new Dimension(60,30));
    slSch.setMaximumSize(new Dimension(60,30));
    slSch.setMinimumSize(new Dimension(60,30));
    slSch.setSnapToTicks(true);
    slSch.setMinorTickSpacing(1);
    slSch.setPaintTicks(true);
    slSch.setMinimum(0);
    slSch.setMaximum(gameStatus.getMaxSch(player));
    slSch.setValue(0);
    if (gameStatus.getTotalAssassin(player)>=0) slSch.setNextFocusableComponent(slAssassin);
    else slSch.setNextFocusableComponent(btOK);
    bxSch.add(slSch);
    ebSch.setPreferredSize(new Dimension(30, 21));
    ebSch.setMinimumSize(new Dimension(30, 21));
    ebSch.setMaximumSize(new Dimension(30, 21));
    ebSch.setEditable(false);
    ebSch.setBackground(Color.black);
    ebSch.setForeground(Color.green);
    bxSch.add(Box.createHorizontalStrut(2));  //Force padding
    bxSch.add(ebSch);
    bxSch.add(Box.createHorizontalStrut(2));  //Force padding
    txtSchCash.setPreferredSize(new Dimension(40, 17));
    txtSchCash.setMinimumSize(new Dimension(40, 17));
    bxSch.add(txtSchCash);
    bxSch.add(Box.createHorizontalStrut(4));  //Force padding
    ImageIcon sch = new ImageIcon(SearchDestroy.getTinyImage(player));
    btSch = new JButton("Info",sch);
    bxSch.add(btSch);
    btSch.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        WeaponInfo.showWeaponInfo(gameStatus.getMainFrame(), player, WeaponBuyDlg.SCH);    //Show info if the weapon reseached
      }
    });
    bxSch.add(Box.createHorizontalStrut(2));  //Force padding
    bxSch.add(Box.createGlue()); //Pad to End

    //Assasin
    Box bxAssassin=Box.createHorizontalBox();
    txtAssassin.setPreferredSize(new Dimension(90, 17));
    txtAssassin.setMinimumSize(new Dimension(90, 17));
    bxAssassin.add(txtAssassin);
    bxAssassin.add(Box.createHorizontalStrut(2));  //Force padding
    slAssassin.addChangeListener(new SliderListener(ASSASSIN));
    slAssassin.setPreferredSize(new Dimension(60,30));
    slAssassin.setMaximumSize(new Dimension(60,30));
    slAssassin.setMinimumSize(new Dimension(60,30));
    slAssassin.setMinorTickSpacing(1);
    slAssassin.setSnapToTicks(true);
    slAssassin.setPaintTicks(true);
    slAssassin.setMinimum(0);
    slAssassin.setMaximum(gameStatus.getMaxAssassin(player));
    slAssassin.setValue(0);
    if (gameStatus.getTotalSniper(player)>=0) slAssassin.setNextFocusableComponent(slSniper);
    else slAssassin.setNextFocusableComponent(btOK);
    bxAssassin.add(slAssassin);
    ebAssassin.setPreferredSize(new Dimension(30, 21));
    ebAssassin.setMinimumSize(new Dimension(30, 21));
    ebAssassin.setMaximumSize(new Dimension(30, 21));
    ebAssassin.setEditable(false);
    ebAssassin.setBackground(Color.black);
    ebAssassin.setForeground(Color.green);
    bxAssassin.add(Box.createHorizontalStrut(2));  //Force padding
    bxAssassin.add(ebAssassin);
    bxAssassin.add(Box.createHorizontalStrut(2));  //Force padding
    txtAssassinCash.setPreferredSize(new Dimension(40, 17));
    txtAssassinCash.setMinimumSize(new Dimension(40, 17));
    bxAssassin.add(txtAssassinCash);
    bxAssassin.add(Box.createHorizontalStrut(4));  //Force padding
    ImageIcon Assassin = new ImageIcon(AssassinPiece.getTinyImage(player));
    btAssassin = new JButton("Info",Assassin);
    btAssassin.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        WeaponInfo.showWeaponInfo(gameStatus.getMainFrame(), player, WeaponBuyDlg.ASSASSIN);    //Show info if the weapon reseached
      }
    });
    bxAssassin.add(btAssassin);
    bxAssassin.add(Box.createHorizontalStrut(2));  //Force padding
    bxAssassin.add(Box.createGlue()); //Pad to End

    //Sniper
    Box bxSniper=Box.createHorizontalBox();
    txtSniper.setPreferredSize(new Dimension(90, 17));
    txtSniper.setMinimumSize(new Dimension(90, 17));
    bxSniper.add(txtSniper);
    bxSniper.add(Box.createHorizontalStrut(2));  //Force padding
    slSniper.addChangeListener(new SliderListener(SNIPER));
    slSniper.setPreferredSize(new Dimension(60,30));
    slSniper.setMaximumSize(new Dimension(60,30));
    slSniper.setMinimumSize(new Dimension(60,30));
    slSniper.setMinimum(0);
    slSniper.setMaximum(gameStatus.getMaxSniper(player));
    slSniper.setSnapToTicks(true);
    slSniper.setMinorTickSpacing(1);
    slSniper.setPaintTicks(true);
    slSniper.setValue(0);
    if (gameStatus.getTotalLeveller(player)>=0) slSniper.setNextFocusableComponent(slLeveller);
    else slSniper.setNextFocusableComponent(btOK);
    bxSniper.add(slSniper);
    bxSniper.add(Box.createHorizontalStrut(2));  //Force padding
    ebSniper.setPreferredSize(new Dimension(30, 21));
    ebSniper.setMinimumSize(new Dimension(30, 21));
    ebSniper.setMaximumSize(new Dimension(30, 21));
    ebSniper.setEditable(false);
    ebSniper.setBackground(Color.black);
    ebSniper.setForeground(Color.green);
    bxSniper.add(ebSniper);
    bxSniper.add(Box.createHorizontalStrut(2));  //Force padding
    txtSniperCash.setPreferredSize(new Dimension(40, 17));
    txtSniperCash.setMinimumSize(new Dimension(40, 17));
    bxSniper.add(txtSniperCash);
    bxSniper.add(Box.createHorizontalStrut(4));  //Force padding
    ImageIcon snip = new ImageIcon(Sniper.getTinyImage(player));
    btSniper = new JButton("Info",snip);
    bxSniper.add(btSniper);
    btSniper.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        WeaponInfo.showWeaponInfo(gameStatus.getMainFrame(), player, WeaponBuyDlg.SNIPER);    //Show info if the weapon reseached
      }
    });
    bxSniper.add(Box.createHorizontalStrut(2));  //Force padding
    bxSniper.add(Box.createGlue()); //Pad to End

    //Leveller
    Box bxLeveller=Box.createHorizontalBox();
    txtLeveller.setPreferredSize(new Dimension(90, 17));
    txtLeveller.setMinimumSize(new Dimension(90, 17));
    bxLeveller.add(txtLeveller);
    bxLeveller.add(Box.createHorizontalStrut(2));  //Force padding
    slLeveller.addChangeListener(new SliderListener(LEVELLER));
    slLeveller.setPreferredSize(new Dimension(60,30));
    slLeveller.setMaximumSize(new Dimension(60,30));
    slLeveller.setMinimumSize(new Dimension(60,30));
    slLeveller.setMinimum(0);
    slLeveller.setMaximum(gameStatus.getMaxLeveller(player));
    slLeveller.setSnapToTicks(true);
    slLeveller.setMinorTickSpacing(1);
    slLeveller.setPaintTicks(true);
    slLeveller.setValue(0);
    slLeveller.setNextFocusableComponent(btOK);
    bxLeveller.add(slLeveller);
    bxLeveller.add(Box.createHorizontalStrut(2));  //Force padding
    ebLeveller.setPreferredSize(new Dimension(30, 21));
    ebLeveller.setMinimumSize(new Dimension(30, 21));
    ebLeveller.setMaximumSize(new Dimension(30, 21));
    ebLeveller.setEditable(false);
    ebLeveller.setBackground(Color.black);
    ebLeveller.setForeground(Color.green);
    bxLeveller.add(ebLeveller);
    bxLeveller.add(Box.createHorizontalStrut(2));  //Force padding
    txtLevellerCash.setPreferredSize(new Dimension(40, 17));
    txtLevellerCash.setMinimumSize(new Dimension(40, 17));
    bxLeveller.add(txtLevellerCash);
    bxLeveller.add(Box.createHorizontalStrut(4));  //Force padding
    ImageIcon lev = new ImageIcon(Leveller.getTinyImage(player));
    btLeveller = new JButton("Info",lev);
    bxLeveller.add(btLeveller);
    btLeveller.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        WeaponInfo.showWeaponInfo(gameStatus.getMainFrame(), player, WeaponBuyDlg.LEVELLER);    //Show info if the weapon reseached
      }
    });
    bxLeveller.add(Box.createHorizontalStrut(2));  //Force padding
    bxLeveller.add(Box.createGlue()); //Pad to End

    //Player Cash Total
    Box bxCash=Box.createHorizontalBox();
    bxCash.add(Box.createGlue()); //Pad to End
    bxCash.add(new JLabel("Total Cash Available:"));
    bxCash.add(Box.createHorizontalStrut(2));  //Force padding
    ebCash.setPreferredSize(new Dimension(70, 21));
    ebCash.setMinimumSize(new Dimension(70, 21));
    ebCash.setMaximumSize(new Dimension(70, 21));
    ebCash.setEditable(false);
    bxCash.add(ebCash);
    ebCash.setText(String.valueOf(gameStatus.getCashTotal(player)));

    bxPane.add(Box.createVerticalStrut(10));
    if (gameStatus.getResearchLevel(player)<=5) {
      bxPane.add(bxResearch);
      bxPane.add(Box.createVerticalStrut(1));
      }
    if (gameStatus.getTotalMagnet(player)>=0) {
      bxPane.add(bxMagnet);
      bxPane.add(Box.createVerticalStrut(1));
      }
    if (gameStatus.getTotalSch(player)>=0) {
      bxPane.add(bxSch);
      bxPane.add(Box.createVerticalStrut(1));
      }
    if (gameStatus.getTotalAssassin(player)>=0) {
      bxPane.add(bxAssassin);
      bxPane.add(Box.createVerticalStrut(1));
      }
    if (gameStatus.getTotalSniper(player)>=0) {
      bxPane.add(bxSniper);
      bxPane.add(Box.createVerticalStrut(1));
      }
    if (gameStatus.getTotalLeveller(player)>=0) {
      bxPane.add(bxLeveller);
      bxPane.add(Box.createVerticalStrut(10));
      }
    bxPane.add(bxCash);
    bxPane.add(Box.createVerticalStrut(10));
    bxPane.add(Box.createGlue()); //Pad to End

    pnEdits.setLayout(editLayout);
    pnEdits.add(bxPane,BorderLayout.CENTER);
    pnEdits.setBorder(BorderFactory.createEtchedBorder());

    if (player==ClaspConstants.P1) pnButtons.setBackground(Color.blue);
    else pnButtons.setBackground(Color.red);
    pnButtons.setLayout(buttonLayout);
    pnButtons.add(btOK);
    btOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });


    getRootPane().setDefaultButton(btOK);

    mainPanel.add(pnEdits, BorderLayout.CENTER);
    mainPanel.add(pnButtons, BorderLayout.SOUTH);

    //Load Totals
    loadTotals();
    initialised = true;     //Ok to let sliders change the values.
    btOK.requestFocus();  //this dosnt seem to work - made button first comp added instead
  }



  //Inner class for catching slider changes - moving amounts up and down
   class SliderListener implements ChangeListener {
      int type;

      //Constructor catches which slider it is.
       public SliderListener(int type){
        this.type=type;
        }

        public void stateChanged(ChangeEvent e) {
            int diff;
            if (!initialised) return;
            JSlider source = (JSlider)e.getSource();
            if (!source.getValueIsAdjusting()) {
               int newValue = (int)source.getValue();
               if (type==RESEARCH) {
                  if (gameStatus.getTotalResearch(player)!=newValue){
                    diff=(gameStatus.getTotalResearch(player)-newValue);
                    gameStatus.increaseCashTotal(player,(diff*(long)researchCost));
                    gameStatus.setTotalResearch(player,newValue);
                    loadTotals();
                    }
                  }
                if (type==MAGNET) {
                  if (gameStatus.getTotalMagnet(player)!=newValue){
                    diff=(gameStatus.getTotalMagnet(player)-newValue);
                    gameStatus.increaseCashTotal(player,(diff*(long)MAGNET_COST));
                    gameStatus.setTotalMagnet(player,newValue);
                    loadTotals();
                    }
                  }
                if (type==SCH) {
                  if (gameStatus.getTotalSch(player)!=newValue){
                    diff=(gameStatus.getTotalSch(player)-newValue);
                    gameStatus.increaseCashTotal(player,(diff*(long)SCH_COST));
                    gameStatus.setTotalSch(player,newValue);
                    loadTotals();
                  }
                }
                if (type==ASSASSIN) {
                  if (gameStatus.getTotalAssassin(player)!=newValue){
                    diff=(gameStatus.getTotalAssassin(player)-newValue);
                    gameStatus.increaseCashTotal(player,(diff*(long)ASSASSIN_COST));
                    gameStatus.setTotalAssassin(player,newValue);
                    loadTotals();
                  }
                }
                if (type==SNIPER) {
                  if (gameStatus.getTotalSniper(player)!=newValue){
                    diff=(gameStatus.getTotalSniper(player)-newValue);
                    gameStatus.increaseCashTotal(player,(diff*(long)SNIPER_COST));
                    gameStatus.setTotalSniper(player,newValue);
                    loadTotals();
                  }
                }
                if (type==LEVELLER) {
                  if (gameStatus.getTotalLeveller(player)!=newValue){
                    diff=(gameStatus.getTotalLeveller(player)-newValue);
                    gameStatus.increaseCashTotal(player,(diff*(long)LEVELLER_COST));
                    gameStatus.setTotalLeveller(player,newValue);
                    loadTotals();
                  }
                }
            }
        }
    }

private void loadTotals(){
  ebResearch.setText(String.valueOf(gameStatus.getTotalResearch(player)));
  slResearch.setValue(gameStatus.getTotalResearch(player));
  ebMagnet.setText(String.valueOf(gameStatus.getTotalMagnet(player)));
  slMagnet.setValue(gameStatus.getTotalMagnet(player));
  ebSch.setText(String.valueOf(gameStatus.getTotalSch(player)));
  slSch.setValue(gameStatus.getTotalSch(player));
  ebAssassin.setText(String.valueOf(gameStatus.getTotalAssassin(player)));
  slAssassin.setValue(gameStatus.getTotalAssassin(player));
  ebSniper.setText(String.valueOf(gameStatus.getTotalSniper(player)));
  slSniper.setValue(gameStatus.getTotalSniper(player));
  ebLeveller.setText(String.valueOf(gameStatus.getTotalLeveller(player)));
  slLeveller.setValue(gameStatus.getTotalLeveller(player));
  slSch.setMaximum(gameStatus.getMaxSch(player));
  slAssassin.setMaximum(gameStatus.getMaxAssassin(player));
  slSniper.setMaximum(gameStatus.getMaxSniper(player));
  slLeveller.setMaximum(gameStatus.getMaxLeveller(player));

  int avail;
  int max;
  long totalCash = gameStatus.getCashTotal(player);
  ebCash.setText("£"+String.valueOf(totalCash));


  avail = (int)(totalCash / researchCost);
  if (avail<0) avail=0;
  max= (avail+gameStatus.getTotalResearch(player));
  if (max>1) slResearch.setMaximum(1);
  else slResearch.setMaximum(max);

  if (gameStatus.getTotalMagnet(player)>=0) {
    avail = (int)(totalCash / MAGNET_COST);
    if (avail<0) avail=0;
    max= (avail+gameStatus.getTotalMagnet(player));
    if (max>gameStatus.getMaxMagnet(player)) slMagnet.setMaximum(gameStatus.getMaxMagnet(player));
    else slMagnet.setMaximum(max);
    }

  if (gameStatus.getTotalSch(player)>=0) {
    avail = (int)(totalCash / SCH_COST);
    if (avail<0) avail=0;
    max= (avail+gameStatus.getTotalSch(player));
    if (max>gameStatus.getMaxSch(player)) slSch.setMaximum(gameStatus.getMaxSch(player));
    else slSch.setMaximum(max);
    }

  if (gameStatus.getTotalAssassin(player)>=0) {
    avail = (int)(totalCash / ASSASSIN_COST);
    if (avail<0) avail=0;
    max= (avail+gameStatus.getTotalAssassin(player));
    if (max>gameStatus.getMaxAssassin(player)) slAssassin.setMaximum(gameStatus.getMaxAssassin(player));
    else slAssassin.setMaximum(max);
    }

  if (gameStatus.getTotalLeveller(player)>=0) {
    avail = (int)(totalCash / LEVELLER_COST);
    if (avail<0) avail=0;
    max= (avail+gameStatus.getTotalLeveller(player));
    if (max>gameStatus.getMaxLeveller(player)) slLeveller.setMaximum(gameStatus.getMaxLeveller(player));
    else slLeveller.setMaximum(max);
    }

  if (gameStatus.getTotalSniper(player)>=0) {
    avail = (int)(totalCash / SNIPER_COST);
    if (avail<0) avail=0;
    max= (avail+gameStatus.getTotalSniper(player));
    if (max>gameStatus.getMaxSniper(player)) slSniper.setMaximum(gameStatus.getMaxSniper(player));
    else slSniper.setMaximum(max);
    }
 }

}