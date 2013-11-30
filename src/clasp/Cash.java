package clasp;
import java.awt.*;  //Graphics
import javax.swing.*; //Jcomponent
import java.util.*;   //Observable

/**
 * Title:        Clasp Version 2
 * Description:  A Game of Two Enemies
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author M.Whiteside
 * @version 1.0
 */



public class Cash extends ClaspBasePiece {

static private ImageIcon imgCash;
static private ImageIcon imgCash30;
static private ImageIcon imgCash60;
static private ImageIcon imgCash90;
static protected Image cashImage;
static protected Image cash30Image;
static protected Image cash60Image;
static protected Image cash90Image;
static Random rand= new Random();

private int cashAmount;
private boolean revealed=false;

//Static Initialisation block - ran only once.
static {
  imgCash = new ImageIcon(Cash.class.getResource("Money.gif"));
  imgCash30 = new ImageIcon(Cash.class.getResource("Money30.gif"));
  imgCash60 = new ImageIcon(Cash.class.getResource("Money60.gif"));
  imgCash90 = new ImageIcon(Cash.class.getResource("Money90.gif"));
  cashImage = imgCash.getImage();
  cash30Image = imgCash30.getImage();
  cash60Image = imgCash60.getImage();
  cash90Image = imgCash90.getImage();
  }

  public Cash() {
    super();
    //Setup Player
    this.player=P0;
    revealed=false;
    //Setup Cash Value for this piece
    switch (rand.nextInt(3)) {
      case 0:
        cashAmount=30;
        break;
      case 1:
        cashAmount=60;
        break;
      case 2:
        cashAmount=90;
        break;
      }
  }

 //Returns the Player One Image
  protected Image getP0Image(){
    if (isRevealed()==true) {
      switch (getCashAmount()) {
       case 30:
          return cash30Image;
        case 60:
          return cash60Image;
        case 90:
          return cash90Image;
        }
      }
    return cashImage;     //Unrevealed image - still a mystery
  }

protected int getCashAmount(){
  return cashAmount;
  }

protected void setRevealed(boolean status){
  revealed=status;
  }

protected boolean isRevealed(){
  return revealed;
  }

}