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

public class PropertiesFrame extends JDialog {

    //Constants for Max / Min
    public final static int MAX_COLS = 13;
    public final static int MIN_COLS = 4;
    public final static int MAX_ROWS = 12;
    public final static int MIN_ROWS = 3;
    public final static int MAX_FULL_LINE=MAX_COLS;
    public final static int MIN_FULL_LINE=4;

  JButton btOK = new JButton();

  private ClaspProperties claspProperties;
  private JFrame parentFrame;
  JButton btCancel = new JButton();
  JPanel pnEdits = new JPanel();
  JPanel pnButtons = new JPanel();
  JButton btDefault = new JButton();
  JTextField ebRows = new JTextField();
  JTextField ebCols = new JTextField();
  JTextField ebFullLine = new JTextField();
  JCheckBox cbRandGrid = new JCheckBox("Random Grid Sizes");
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout editLayout = new BorderLayout();


  public PropertiesFrame(JFrame frame) {
    try {
      parentFrame=frame;
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    this.setModal(true);
    this.setResizable(false);
    this.setTitle("Clasp Properties");
    this.getContentPane().setLayout(borderLayout1);
    pnEdits.setBorder(BorderFactory.createEtchedBorder());

    Dimension btSize= new Dimension(80,25);

    btDefault.setText("Default");
    btDefault.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        doDefault();
      }
    });
    btDefault.setPreferredSize(btSize);


    btOK.setText("OK");
    btOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        doOK();
      }
    });
    btOK.setPreferredSize(btSize);

    btCancel.setText("Cancel");
    btCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btCancel_actionPerformed(e);
      }
    });
    btCancel.setPreferredSize(btSize);



    ebRows.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        ebRows_focusLost(e);
      }
    });
    ebCols.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        ebCols_focusLost(e);
      }
    });
    ebFullLine.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        ebFullLine_focusLost(e);
      }
    });

    Box ebPane=Box.createVerticalBox();

    //Random Grid
    Box totRandGrid=Box.createHorizontalBox();
    totRandGrid.add(Box.createHorizontalStrut(2));  //Force padding
    totRandGrid.add(cbRandGrid);
    totRandGrid.add(Box.createGlue()); //Pad to End

    //Rows box
    Box totRows=Box.createHorizontalBox();
    JLabel txtRows = new JLabel("",JLabel.RIGHT);
    txtRows.setText("Rows:");
    txtRows.setPreferredSize(new Dimension(70, 17));
    txtRows.setMinimumSize(new Dimension(70, 17));
    totRows.add(txtRows);
    ebRows.setPreferredSize(new Dimension(50, 21));
    ebRows.setMinimumSize(new Dimension(50, 21));
    ebRows.setMaximumSize(new Dimension(50, 21));
    totRows.add(Box.createHorizontalStrut(2));  //Force padding
    totRows.add(ebRows);
    totRows.add(Box.createGlue()); //Pad to End


    Box totCols=Box.createHorizontalBox();
    JLabel txtCols = new JLabel("",JLabel.RIGHT);
    txtCols.setText("Columns:");
    txtCols.setPreferredSize(new Dimension(70, 17));
    txtCols.setMinimumSize(new Dimension(70, 17));
    totCols.add(txtCols);
    ebCols.setPreferredSize(new Dimension(50, 21));
    ebCols.setMinimumSize(new Dimension(50, 21));
    ebCols.setMaximumSize(new Dimension(50, 21));
    totCols.add(Box.createHorizontalStrut(2));  //Force padding
    totCols.add(ebCols);
    totCols.add(Box.createGlue()); //Pad to End

    Box totFullLine=Box.createHorizontalBox();
    JLabel txtFullLine = new JLabel("",JLabel.RIGHT);
    txtFullLine.setText("Full Line:");
    txtFullLine.setPreferredSize(new Dimension(70, 17));
    txtFullLine.setMinimumSize(new Dimension(70, 17));
    totFullLine.add(txtFullLine);
    ebFullLine.setPreferredSize(new Dimension(50, 21));
    ebFullLine.setMinimumSize(new Dimension(50, 21));
    ebFullLine.setMaximumSize(new Dimension(50, 21));
    ebFullLine.setNextFocusableComponent(btOK);
    totFullLine.add(Box.createHorizontalStrut(2));  //Force padding
    totFullLine.add(ebFullLine);
    totFullLine.add(Box.createGlue()); //Pad to End
    totFullLine.add(btDefault);
    totFullLine.add(Box.createHorizontalStrut(10));  //orce padding

    ebPane.add(Box.createVerticalStrut(10));
    ebPane.add(totRandGrid);
    ebPane.add(totRows);
    ebPane.add(totCols);
    ebPane.add(Box.createVerticalStrut(10));
    ebPane.add(totFullLine);
    ebPane.add(Box.createGlue()); //Pad to End

    pnEdits.setLayout(editLayout);
    pnEdits.add(ebPane,BorderLayout.CENTER);

    this.getContentPane().add(pnEdits,  BorderLayout.CENTER);

    pnButtons.add(btOK);
    pnButtons.add(btCancel);
    pnButtons.setPreferredSize(new Dimension(0,40));
    pnButtons.setMaximumSize(new Dimension(0,40));
    this.getContentPane().add(pnButtons,BorderLayout.SOUTH);
    getRootPane().setDefaultButton(btOK);

    }

  private void loadProperties() {                 //Load to screen from properties object
    cbRandGrid.setSelected(claspProperties.isRandGrid());
    ebRows.setText(String.valueOf(claspProperties.getGridRows()));
    ebCols.setText(String.valueOf(claspProperties.getGridCols()));
    ebFullLine.setText(String.valueOf(claspProperties.getFullLine()));
  }


  /**Overridden so we can exit when window is closed*/
  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      cancel();
    }
    super.processWindowEvent(e);
  }

  private void doOK(){
    claspProperties.setRandGrid(cbRandGrid.isSelected());
    claspProperties.setGridRows(Integer.parseInt(ebRows.getText()));
    claspProperties.setGridCols(Integer.parseInt(ebCols.getText()));
    claspProperties.setFullLine(Integer.parseInt(ebFullLine.getText()));
    ClaspProperties.writeObject(claspProperties);   //Store Changed Properties to File
    cancel();
  }

 /**Close the dialog*/
  void cancel() {
    dispose();
  }


  private void doDefault(){
    claspProperties.setDefaults();    //set properties back to default
    loadProperties();                 //Load to screen from preoperties object
  }

//Get&Set Methods
protected void setClaspProperties(ClaspProperties props){
    claspProperties=props;
    loadProperties();         //Load Preoperties to screen
   }

  void btCancel_actionPerformed(ActionEvent e) {
    cancel();
  }

  void ebRows_focusLost(FocusEvent e) {
    if (e.isTemporary()==true) return;
    checkEditRange(ebRows,MIN_ROWS,MAX_ROWS,"Rows");
  }

  void ebCols_focusLost(FocusEvent e) {
    if (e.isTemporary()==true) return;
    checkEditRange(ebCols,MIN_COLS,MAX_COLS,"Columns");
  }

  void ebFullLine_focusLost(FocusEvent e) {
    if (e.isTemporary()==true) return;
    checkEditRange(ebFullLine,MIN_FULL_LINE,MAX_FULL_LINE,"Full Line");
  }

  //Generic Range Checker with Error messaging for Validation
private void checkEditRange(JTextField ebox, final int min, final int max, String title){
    int value;
    try {
      value=Integer.parseInt(ebox.getText());
    }
    catch (Exception ex) {
      JOptionPane.showMessageDialog(getParent(),title + " must be a value between " + min + " and " + max, "Validation",JOptionPane.ERROR_MESSAGE);
      ebox.requestFocus();
      return;
    }
    if (value>max) {
      JOptionPane.showMessageDialog(getParent(),title + " can not Exceed " + max, "Validation",JOptionPane.ERROR_MESSAGE);
      ebox.setText(String.valueOf(max));
      ebox.requestFocus();
      return;
      }
    if (value<min) {
      JOptionPane.showMessageDialog(getParent(),title + " must be at least " + min, "Validation",JOptionPane.ERROR_MESSAGE);
      ebox.setText(String.valueOf(min));
      ebox.requestFocus();
      return;
      }
  }

}
