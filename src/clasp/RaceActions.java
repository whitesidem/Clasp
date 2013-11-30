package clasp;
import java.awt.*;
import javax.swing.*;

/**
 * Title:        Clasp Version 2
 * Description:  A Game of Two Enemies
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author M.Whiteside
 * @version 1.0
 */

public class RaceActions {

  private ClaspModel model;
  private ClaspGameStatus gameStatus;
  private int player;
  private int betAmount=0;
  private int selectedWorld=0;
  private int winner=0;

public RaceActions(ClaspModel model, ClaspGameStatus gameStatus, int player) {
  this.model=model;
  this.gameStatus=gameStatus;                             //All main units point to the main controller
  this.player=player;
  }

protected void setup(){
  gameStatus.getMainFrame().updateCtrlPanel();      //Show Current Scores etc
  getBets();
  if (betAmount<0) return;                         //No Race if negative bet - i.e. cancelled bet.
  gameStatus.getMainFrame().updateCtrlPanel();      //Show Current Scores etc after gamble amount removed
  gameStatus.getClaspView().paintFullViewArea();    //Paint newly cleared grid with correct background
  gameStatus.getMainFrame().repaint();
  for (int row=1; row<=gameStatus.getGridRows(); row++) {
    for (int col=1; col<=gameStatus.getGridCols(); col++) {
      if (row==1) {
        model.setPieceLocation(new Rider(col),row,col);     //Place Rider on Grid
        }
      else {
        model.setPieceLocation(new NullPiece(),row,col);     //Place Invisible piece on Grid
        }
      }   //Col Loop
    } //Row Loop
  beginRace();        //Perform Race
  //Give Winnings
  int shares=gameStatus.getTotalRider(player,winner);
  int winnings=0;
  if (winner==selectedWorld) {
    winnings=betAmount*7;
    if (shares>0) {
      winnings+=((shares*RiderPanel.SHARE_PRICE)*6);  //Add shares Winnings
      JOptionPane.showMessageDialog(gameStatus.getMainFrame(),"Excellent, you picked and have shares in \"Planet (" + GambleDlg.worldStrings[(winner-1)] + "\", your winnings total $" + winnings);
      }
    else JOptionPane.showMessageDialog(gameStatus.getMainFrame(),"Well Done, you picked \"Planet (" + GambleDlg.worldStrings[(winner-1)] + "\", your winnings total $" + winnings);
    gameStatus.increaseCashTotal(player,winnings);   //Add Bet amount 6/1 * bet amount + stake
    if ((shares>0)&&(gameStatus.getTotalTrophy2(player)<=0)) {  //If own shares in winning horse AND dont already have the owners trophy
      gameStatus.increaseTotalTrophy2(player);
      JOptionPane.showMessageDialog(gameStatus.getMainFrame(),((player==ClaspConstants.P1)?"Blue Team":"Red Team") + ", you have won the Owners Trophy...");
      WeaponInfo.showWeaponInfo(gameStatus.getMainFrame(), player, WeaponBuyDlg.TROPHY2);    //Show info if the weapon won
      gameStatus.setTotalTrophy2((player==ClaspConstants.P1?ClaspConstants.P2:ClaspConstants.P1),0);     //Remove from opposing player if they have it
      }
    //If horse not owned - but correct horse selected OR horse is owned but have owners trophy already
    else if (gameStatus.getTotalTrophy1(player)<=0) {  //If Dont own the Trophy Already
      gameStatus.increaseTotalTrophy1(player);
      JOptionPane.showMessageDialog(gameStatus.getMainFrame(),((player==ClaspConstants.P1)?"Blue Team":"Red Team") + ", you have won the Gamblers Trophy...");
      WeaponInfo.showWeaponInfo(gameStatus.getMainFrame(), player, WeaponBuyDlg.TROPHY1);    //Show info if the weapon won
      gameStatus.setTotalTrophy1((player==ClaspConstants.P1?ClaspConstants.P2:ClaspConstants.P1),0);     //Remove from opposing player if they have it
      }
    gameStatus.getMainFrame().updateCtrlPanel();      //Show Current Scores etc after gamble amount removed
   }
  else if (shares>0){    //Own Shares in the winner
    winnings=((shares*RiderPanel.SHARE_PRICE)*6); //Shares Winnings
    JOptionPane.showMessageDialog(gameStatus.getMainFrame(),"Congratulations, You have Shares in the winning \"Planet (" + GambleDlg.worldStrings[(winner-1)] + "\", your winnings total $" + winnings);
    gameStatus.increaseCashTotal(player,winnings);   //Add Bet amount 6/1 * bet amount + stake
    gameStatus.getMainFrame().updateCtrlPanel();      //Show Current Scores etc after gamble amount removed
    }
  else JOptionPane.showMessageDialog(gameStatus.getMainFrame(),"The Winner was \"Planet (" + GambleDlg.worldStrings[(winner-1)] + "\", but you selected \"Planet (" + GambleDlg.worldStrings[(selectedWorld-1)] + "\"");
  }

protected void beginRace(){
  ClaspBasePiece piece;
  while (winner==0) {     //Untill a winner has been found
    raceloop:
    for (int row=1; row<=gameStatus.getGridRows(); row++) {
      for (int col=1; col<=gameStatus.getGridCols(); col++) {
        piece=model.getGridCell(col-1,row-1);
        if (piece==null) continue;
        if (!piece.getClass().getName().equals("clasp.NullPiece")) continue;
        if (ClaspGridActions.rand.nextInt(22)!=0) continue;
         model.setGridCell(col-1,row-1,null);    //Remove Null Piece
         model.doGravity();
          //Check if piece fallen to floor yet
         piece=model.getGridCell(col-1,gameStatus.getGridRows()-1);
         if (piece==null) continue;
         if (piece.getClass().getName().equals("clasp.Rider")) {
          winner=col;       //We have a winner
          break raceloop;
         }
        }   //Col Loop
      } //Row Loop
    } //while winner loop
  }

private void getBets() {
  GambleDlg.initialize(gameStatus.getClaspView(),player,gameStatus);
  GambleDlg.showDialog(gameStatus.getClaspView());
  betAmount=GambleDlg.dialog.getRetGambleAmount();
  if (betAmount>0) {
    gameStatus.decreaseCashTotal(player,betAmount);
    selectedWorld=GambleDlg.dialog.getSelectedWorld();
    }
  GambleDlg.remove();
  }


}