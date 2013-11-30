package clasp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


/**
 * Title:        Gamble Dialog
 * Description:  A Game of Two Enemies
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author M.Whiteside
 * @version 1.0
 */

public class GambleDlg extends JDialog {
  public static GambleDlg dialog;
  private int gambleAmount=ClaspConstants.CLASP_LINE_SCORE;
  private int retGambleAmount=-1;
  private int selectedWorld=-1;
  private static int MAX_GAMBLE=3000;
  private int totalCash=0;
  private RiderPanel ridersPanel;
  private int player;
  private ClaspGameStatus gameStatus;
  private boolean initialised = false;
  private boolean includeRidersPanel=false;

  JPanel pnEdits = new JPanel();
  JPanel pnButtons = new JPanel();
  JPanel mainPanel = new JPanel();

  BorderLayout borderLayoutMain = new BorderLayout();
  BorderLayout editLayout = new BorderLayout();
  FlowLayout buttonLayout = new FlowLayout(FlowLayout.CENTER);

  final JButton btOK = new JButton("RACE");
  JButton btCancel = new JButton("Cancel");

  JTextField ebGamble = new JTextField();
  JSlider slGamble = new JSlider();

  JTextField ebCash = new JTextField();

  public static String[] worldStrings = { "1) Espirit", "2) Cypher", "3) Mobius", "4) Donut", "5) Zogg","6) Cloude" };

  JComboBox cbWorld = new JComboBox(worldStrings);

    public static void remove() {
       dialog.dispose();
       dialog=null;
    }

    /**
     * Set up the dialog.  The first argument can be null,
     * but it really should be a component in the dialog's
     * controlling frame.
     */
    public static void initialize(Component comp, int player, ClaspGameStatus gameStatus) {
        Frame frame = JOptionPane.getFrameForComponent(comp);
        dialog = new GambleDlg(frame, player, gameStatus);
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
            dialog.setSelectedWorld(1);
            dialog.setVisible(true);
        } else {
            System.err.println("ListDialog requires you to call initialize "
                               + "before calling showDialog.");
        }
    }

    private void setSelectedWorld(int newValue) {
       selectedWorld = newValue;
       cbWorld.setSelectedIndex(newValue-1);  //Set item in combo box
    }


  public GambleDlg(Frame frame, int player, ClaspGameStatus gameStatus) {
    super(frame, ((player==ClaspConstants.P1)?"Blue Team ":"Red Team ")+"at the World Races", true);
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
    totalCash=(int)gameStatus.getCashTotal(player);
    if (totalCash>MAX_GAMBLE) totalCash=MAX_GAMBLE;


    mainPanel.setLayout(borderLayoutMain);
    getContentPane().add(mainPanel);
    Box bxPane=Box.createVerticalBox();

   //Worlds
    Box bxWorld=Box.createHorizontalBox();
    JLabel txtworld = new JLabel("Select Planet:",JLabel.RIGHT);
    txtworld.setPreferredSize(new Dimension(110, 17));
    txtworld.setMinimumSize(new Dimension(110, 17));
    txtworld.setMaximumSize(new Dimension(110, 17));
    bxWorld.add(txtworld);
    bxWorld.add(Box.createHorizontalStrut(2));  //Force padding
    cbWorld.setPreferredSize(new Dimension(100, 21));
    cbWorld.setMinimumSize(new Dimension(100, 21));
    cbWorld.setMaximumSize(new Dimension(100, 21));
    cbWorld.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                selectedWorld = (cb.getSelectedIndex()+1);
            }
        });
    bxWorld.add(cbWorld);
    bxWorld.add(Box.createHorizontalStrut(4));  //Force padding
    bxWorld.add(Box.createGlue()); //Pad to End

   //Gamble Amount
    Box bxGamble=Box.createHorizontalBox();
    JLabel txtGamble = new JLabel("Total to Bet:",JLabel.RIGHT);
    txtGamble.setPreferredSize(new Dimension(110, 17));
    txtGamble.setMinimumSize(new Dimension(110, 17));
    txtGamble.setMaximumSize(new Dimension(110, 17));
    bxGamble.add(txtGamble);
    bxGamble.add(Box.createHorizontalStrut(2));  //Force padding
    slGamble.addChangeListener(new SliderListener());
    slGamble.setPreferredSize(new Dimension(280,30));
    slGamble.setMaximumSize(new Dimension(280,30));
    slGamble.setMinimumSize(new Dimension(280,30));
    slGamble.setMinimum(0);
    slGamble.setMaximum((int)gameStatus.getCashTotal(player));
    slGamble.setMinorTickSpacing(10);
    slGamble.setMajorTickSpacing(100);
    slGamble.setPaintTicks(true);
    slGamble.setPaintTrack(true);
    slGamble.setPaintLabels(true);
    slGamble.setValue(gambleAmount);
    bxGamble.add(slGamble);
    slGamble.setNextFocusableComponent(btOK);
    bxGamble.add(Box.createHorizontalStrut(2));  //Force padding
    ebGamble.setPreferredSize(new Dimension(50, 21));
    ebGamble.setMinimumSize(new Dimension(50, 21));
    ebGamble.setMaximumSize(new Dimension(50, 21));
    ebGamble.setEditable(false);
    ebGamble.setBackground(Color.black);
    ebGamble.setForeground(Color.green);
    bxGamble.add(ebGamble);
    bxGamble.add(Box.createHorizontalStrut(4));  //Force padding
    bxGamble.add(Box.createGlue()); //Pad to End

   //Cash Amount
    Box bxCash=Box.createHorizontalBox();
    JLabel txtCash = new JLabel("Available Cash:",JLabel.RIGHT);
    txtCash.setPreferredSize(new Dimension(110, 17));
    txtCash.setMinimumSize(new Dimension(110, 17));
    txtCash.setMaximumSize(new Dimension(110, 17));
    bxCash.add(txtCash);
    bxCash.add(Box.createHorizontalStrut(2));  //Force padding
    ebCash.setPreferredSize(new Dimension(50, 21));
    ebCash.setMinimumSize(new Dimension(50, 21));
    ebCash.setMaximumSize(new Dimension(50, 21));
    ebCash.setEditable(false);
    ebCash.setBackground(Color.black);
    ebCash.setForeground(Color.green);
    bxCash.add(ebCash);
    bxCash.add(Box.createHorizontalStrut(4));  //Force padding
    bxCash.add(Box.createGlue()); //Pad to End

    //Add to Pane
    bxPane.add(Box.createVerticalStrut(10));
    bxPane.add(bxWorld);
    bxPane.add(Box.createVerticalStrut(10));
    bxPane.add(bxGamble);
    bxPane.add(Box.createVerticalStrut(10));
    bxPane.add(bxCash);
    bxPane.add(Box.createVerticalStrut(10));
    bxPane.add(Box.createGlue()); //Pad to End

    pnEdits.setLayout(editLayout);
    pnEdits.add(bxPane,BorderLayout.CENTER);
    pnEdits.setBorder(BorderFactory.createEtchedBorder());

    //Add Buttons
    if (player==ClaspConstants.P1) pnButtons.setBackground(Color.blue);
    else pnButtons.setBackground(Color.red);
    pnButtons.setLayout(buttonLayout);
    pnButtons.add(btOK);
    pnButtons.add(btCancel);
    btOK.setMaximumSize(btCancel.getPreferredSize());
    btOK.setMinimumSize(btCancel.getPreferredSize());
    btOK.setPreferredSize(btCancel.getPreferredSize());

    btOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
      retGambleAmount=gambleAmount;     //Set gamble amount to use
      GambleDlg.dialog.setVisible(false);
      }
    });

    btCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        GambleDlg.dialog.setVisible(false);
      }
    });
    getRootPane().setDefaultButton(btOK);

    //Only Add Rider Display if at least one rider share owned
    for (int rider=1;rider<=6;rider++) {
      if (gameStatus.getTotalRider(player,rider)>0) {
        includeRidersPanel=true;        //Indicate its being shown
        mainPanel.add(ridersPanel, BorderLayout.NORTH);
        break;
      }
    }

    mainPanel.add(pnEdits, BorderLayout.CENTER);
    mainPanel.add(pnButtons, BorderLayout.SOUTH);

    //Load Totals
    loadTotals();
    initialised = true;     //Ok to let sliders change the values.
    btOK.requestFocus();  //this dosnt seem to work - made button first comp added instead

  }

  //Inner class for catching slider changes - moving amounts up and down
   class SliderListener implements ChangeListener {

        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider)e.getSource();
            if (!source.getValueIsAdjusting()) {
            int newValue = (int)source.getValue();
            loadTotals();
            }
        }
    }

private void loadTotals(){
  gambleAmount=slGamble.getValue();
  ebGamble.setText(String.valueOf(gambleAmount));
  ebCash.setText(String.valueOf((totalCash-gambleAmount)));
 }

public int getRetGambleAmount(){
  return retGambleAmount;
  }
public int getSelectedWorld(){
  return selectedWorld;
  }

//===============Paint method for the grid==================
  public void paint(Graphics g){
    super.paint(g);
    if (includeRidersPanel==true)ridersPanel.paintRiders(g);   //Paint Riders
    }


}