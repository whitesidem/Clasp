package clasp;


import java.util.*;   //Observable
import javax.swing.*;   //JOptionPane Dialogs
import java.awt.*;


/**
 * Title:        Clasp Version 2
 * Description:  Deals with Grid Actions that effect all the pieces
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author M.Whiteside
 * @version 1.0
 */

public class ClaspGridActions extends Observable implements ClaspConstants {

  private static final int TARGET_ON = 1;
  private static final int TARGET_OFF = 2;
  private static final int TARGET_BOMB1 = 3;
  private static final int TARGET_BOMB2 = 4;
  private static final int TARGET_BOMB3 = 5;
  private static final int TARGET_REMOVE = 6;
  private static final int TARGET_SEARCHING=7;
  private static final int TARGET_SEARCHING2=8;
  private static final int TARGET_SEARCH_BOMB=9;
  private static final int TARGET_RESTORE = 10;

  private static final int STEP_DELAY = 500;
  private static final int SMALL_STEP_DELAY = 100;
  private static final int SPIN_DELAY = 250;
  private static final int FIRE_DELAY = 600;
  private static final int SMALL_FIRE_DELAY = 200;
  private static final int TINY_FIRE_DELAY = 10;
  private static final int HATCH_DELAY = 500;
  private static final int TELEPORT_DELAY = 150;
  private static final int BOOST_DELAY = 350;
  private static final int SHOW_END_RESULT_DELAY=500;
  static Random rand= new Random();


  private ClaspModel model;
  private ClaspGameStatus gameStatus;

public ClaspGridActions(ClaspModel model, ClaspGameStatus gameStatus) {
  this.model=model;
  this.gameStatus=gameStatus;                             //All main units point to the main controller
  }

//Randomly drop non player items - like metorites and money
protected void randomDrops(){
  dropBoost();
  dropMagnets();
  dropEggs();
  dropCash();
  dropRiders();
  dropMeteoriteTriggers();      //drop Meteorites
  dropTeleports();              //drop Teleports
  dropDanger();
  }

//Randomly drops Cash
//select a random column
private void dropCash(){
  int randNum=45;
  if (model.getLevelMode()==model.LM_RAND_DROPS) randNum=10;
  else if (gameStatus.getGameLevel()<=3) randNum=0;      //Only avail on certain levels if not special level
  if (randNum<=0) return;
  if (rand.nextInt(randNum)!=1) return;
  Cash cash=new Cash();
  int col=(rand.nextInt(gameStatus.getGridCols())+1);
  if (model.getGridCell(col-1,1)==null) { //only drop if column below first not already filled
    model.addPiece(cash,col);         //add/drop piece
    }
  }


//Randomly drops Teleports
//select a random column
private void dropTeleports(){
  int randNum=400;
  if (gameStatus.getGameLevel()<=2) randNum=0;                  //Only avail after certain levels
  else if (model.getLevelMode()==model.LM_RAND_DROPS) randNum=15;
  else if ((gameStatus.getGameLevel())%9==0)randNum=28;        //More Chance to cause havoc
  else if ((gameStatus.getGameLevel()-1)%4==0) randNum=45;      //Only avail on certain levels if not special level
  if (randNum<=0) return;
  if (rand.nextInt(randNum)!=1) return;
  Teleport teleport=new Teleport();
  int col=(rand.nextInt(gameStatus.getGridCols())+1);
  if (model.getGridCell(col-1,1)==null) { //only drop if column below first not already filled
    model.addPiece(teleport,col);         //add/drop piece
    }
  }

//Randomly drops Meteorite Triggers
//select a random column
private void dropMeteoriteTriggers(){
  int randNum=600;
  if (gameStatus.getGameLevel()<=3) randNum=0;     //Only avail after certain levels
  else if (gameStatus.getGameLevel()%4==0) randNum=80; //Only avail on certain levels
  if (randNum<=0) return;
  if (rand.nextInt(randNum)!=1) return;
  MeteoriteTrigger mTrigger=new MeteoriteTrigger();
  int col=(rand.nextInt(gameStatus.getGridCols())+1);
  if (model.getGridCell(col-1,1)==null) { //only drop if column below first not already filled
    model.addPiece(mTrigger,col);         //add/drop piece
    }
  }

//Randomly drops Danger Triggers
//select a random column
private void dropDanger(){
  int randNum=1000;
  if (gameStatus.getGameLevel()<=4) randNum=0;     //Only avail after certain levels
  else if (model.getLevelMode()==model.LM_RAND_DROPS) randNum=90;
  else if (gameStatus.getGameLevel()%8==0) randNum=150; //Only avail on certain levels
  else if (gameStatus.getGameLevel()%6==0) randNum=450;  //Only avail on certain levels
  if (randNum<=0) return;
  if (rand.nextInt(randNum)!=1) return;
  Danger danger=new Danger();
  int col=(rand.nextInt(gameStatus.getGridCols())+1);
  if (model.getGridCell(col-1,1)==null) { //only drop if column below first not already filled
    model.addPiece(danger,col);         //add/drop piece
    }
  }

//Randomly drops Magnets
//select a random column
private void dropMagnets(){
  int randNum=550;
  if (gameStatus.getGameLevel()<=3) randNum=0;     //Only avail after certain levels
  else if (model.getLevelMode()==model.LM_RAND_DROPS) randNum=40;
  else if (gameStatus.getGameLevel()%3==0) randNum=300; //Only avail on certain levels
  if (randNum<=0) return;
  if (rand.nextInt(randNum)!=1) return;
  Magnet magnet=new Magnet(P0);
  int col=(rand.nextInt(gameStatus.getGridCols())+1);
  if (model.getGridCell(col-1,1)==null) { //only drop if column below first not already filled
    model.addPiece(magnet,col);         //add/drop piece
    }
  }

//Randomly drops Eggs
//select a random column
private void dropEggs(){
  int randNum=300;
  if (gameStatus.getGameLevel()<=2) randNum=0; //Only avail after certain levels
  else if (model.getLevelMode()==model.LM_RAND_DROPS) randNum=11;
  else if ((gameStatus.getGameLevel())%7==0)randNum=28;         //More Chance to cause havoc
  else if ((gameStatus.getGameLevel()-2)%4==0) randNum=60;      //Only avail on certain levels if not special level
  if (randNum<=0) return;
  if (rand.nextInt(randNum)!=1) return;
  Egg egg=new Egg();
  int col=(rand.nextInt(gameStatus.getGridCols())+1);
  if (model.getGridCell(col-1,1)==null) { //only drop if column below top not already filled
    model.addPiece(egg,col);         //add/drop piece
    }
  }

//Randomly drops Riders
//select a random column
private void dropRiders(){
  int randNum=450;
  if (gameStatus.getGameLevel()<=4) randNum=0; //Only avail after certain levels
  else if (model.getLevelMode()==model.LM_RAND_DROPS) randNum=200;
  else if ((gameStatus.getGameLevel())%11==0)randNum=90;         //More Chance to cause havoc
  else if ((gameStatus.getGameLevel()-2)%5==0) randNum=130;      //Only avail on certain levels if not special level
  if (randNum<=0) return;
  if (rand.nextInt(randNum)!=1) return;
  Rider rider=new Rider((rand.nextInt(6)+1));     //Randomly drop different riders
  int col=(rand.nextInt(gameStatus.getGridCols())+1);
  if (model.getGridCell(col-1,1)==null) { //only drop if column below top not already filled
    model.addPiece(rider,col);            //add/drop piece
    }
  }

//Randomly drops Boosts
//select a random column
private void dropBoost(){
  int randNum=700;
  if (gameStatus.getGameLevel()<=3) randNum=0; //Only avail after certain levels
  else if (model.getLevelMode()==model.LM_RAND_DROPS) randNum=70;
  else if ((gameStatus.getGameLevel())%7==0)randNum=250;         //More Chance to cause havoc
  else if ((gameStatus.getGameLevel()-2)%6==0) randNum=190;      //Only avail on certain levels if not special level
  if (randNum<=0) return;
  if (rand.nextInt(randNum)!=1) return;
  Boost boost=new Boost();
  int col=(rand.nextInt(gameStatus.getGridCols())+1);
  if (model.getGridCell(col-1,2)==null) { //only drop if t rows down below  not already filled
    model.addPiece(boost,col);         //add/drop piece
    }
  }

//Check for actions on either side while dropping
protected void CheckSideActions(final int row, final int col){
  ClaspBasePiece piece;
  piece=model.getGridCell(col-1,row-1);
  int player = piece.getPlayer();
  if (player==P0) return;     //Dont shoot non player pieces
  if ((col-1) >= 1) {         //Left Check for Sniper
    doSniperSideAction(row,col,-1);
    piece=model.getGridCell(col-1,row-1);
    if (piece==null) return;   //piece was destroyed by sniper
    }
  if ((col+1) <= gameStatus.getGridCols()) {  //Right Check for Sniper
    doSniperSideAction(row,col,1);
    piece=model.getGridCell(col-1,row-1);
    if (piece==null) return;   //piece was destroyed by sniper
    }
  }

//Checks for a sniper on each side
//The actual position to check is calculated by add pieceInc to col
//if piece in is positive the check is to the right
//if piece in is negative the check is to the left
private void doSniperSideAction(final int row,final int col, final int pieceInc){
  ClaspBasePiece piece;
  ClaspBasePiece sniperPiece;
  String pieceClass;
  int sniperCol=(col+pieceInc);

  piece=model.getGridCell(col-1,row-1);
  int player = piece.getPlayer();
  if (player==P0) return;     //Dont shoot non player pieces

  sniperPiece=model.getGridCell(sniperCol-1,row-1);
  if (sniperPiece==null) return;   //no piece to check
  int sniperPlayer = sniperPiece.getPlayer();

  pieceClass=sniperPiece.getClass().getName();
  if (pieceClass.equals("clasp.Sniper")==false) return; //Not a sniper
  if (sniperPlayer==player) return;         //Same player - dont shoot


  if (pieceInc<0)((Sniper)sniperPiece).setDirection(CLASP_RIGHT);
  else ((Sniper)sniperPiece).setDirection(CLASP_LEFT);

  ((Sniper)sniperPiece).setAnimationStep(1);
  gameStatus.getClaspView().paintPiece(sniperPiece);
  ClaspView.pause(STEP_DELAY);  //Pause with current image before continuing

 ((Sniper)sniperPiece).setAnimationStep(2);
  gameStatus.getClaspView().paintPiece(sniperPiece);
  ClaspView.pause(SMALL_STEP_DELAY);  //Pause with current image before continuing

  ((Sniper)sniperPiece).setAnimationStep(3);
  gameStatus.getClaspView().paintPiece(sniperPiece);
  ClaspView.pause(SMALL_STEP_DELAY);  //Pause with current image before continuing

  int odds=((Sniper)sniperPiece).getOdds();
  if (rand.nextInt(odds)==0) {                //This will shoot
    ((Sniper)sniperPiece).nextOdds();          //make it less likely to shoot next time
    ((Sniper)sniperPiece).setFire(true);
    gameStatus.getClaspView().paintPiece(sniperPiece);
    ClaspView.pause(SMALL_STEP_DELAY);  //Pause with current image before continuing
    setTargettedState(row, col, TARGET_BOMB1); //remove meteor piece
    ClaspView.pause(SMALL_STEP_DELAY);  //Pause with current image before continuing
    setTargettedState(row, col, TARGET_BOMB2); //remove meteor piece
    ClaspView.pause(SMALL_STEP_DELAY);  //Pause with current image before continuing
    setTargettedState(row, col, TARGET_BOMB3); //remove meteor piece
    ClaspView.pause(SMALL_STEP_DELAY);  //Pause with current image before continuing
    setTargettedState(row, col, TARGET_REMOVE); //remove meteor piece
    }

 ((Sniper)sniperPiece).setFire(false);
  gameStatus.getClaspView().paintPiece(sniperPiece);
  ClaspView.pause(SMALL_STEP_DELAY);  //Pause with current image before continuing

 ((Sniper)sniperPiece).setAnimationStep(2);
  gameStatus.getClaspView().paintPiece(sniperPiece);
  ClaspView.pause(SMALL_STEP_DELAY);  //Pause with current image before continuing

  ((Sniper)sniperPiece).setAnimationStep(1);
  gameStatus.getClaspView().paintPiece(sniperPiece);
  ClaspView.pause(SMALL_STEP_DELAY);  //Pause with current image before continuing

  ((Sniper)sniperPiece).setAnimationStep(0);
  ((Sniper)sniperPiece).setDirection(CLASP_CENTER);
  gameStatus.getClaspView().paintPiece(sniperPiece);
  }


protected void doLandedAction(final int row, final int col){
  ClaspBasePiece piece;
  String pieceClass;
  piece=model.getGridCell(col-1,row-1);
  if (piece==null) return;   //piece was somehow destroyed on the way down
  int player= piece.getPlayer();

  pieceClass=piece.getClass().getName();
  //first check piece below to see if special action needed
  if (row < gameStatus.getGridRows()){
    ClaspBasePiece pieceBelow;
    String pieceBelowClass;
    pieceBelow=model.getGridCell(col-1,row);
    pieceBelowClass=pieceBelow.getClass().getName();

    if (!pieceClass.equals("clasp.Teleport")) {                //Not if Teleport Piece
      if (pieceBelowClass.equals("clasp.Teleport")) {     //Do Teleportation
        teleportPiece(piece,(Teleport)pieceBelow,row,col);
        return;
        }
      }
    if (pieceBelowClass.equals("clasp.Boost")) {     //Do Boost
      boostPiece(piece,(Boost)pieceBelow,row,col);
      return;
      }
    if (pieceBelowClass.equals("clasp.Cash")) {     //pick up cash
      revealCashPiece(piece,(Cash)pieceBelow,row,col);
      return;
      }
    if (pieceBelowClass.equals("clasp.MeteoriteTrigger")) {
      pickupMeteoriteTrigger(piece,row,col);
     return;       //return as piece has "now" not landed yet as we have removed piece below
     }
    if (pieceBelowClass.equals("clasp.Danger")) {
      dangerTriggered(piece,(Danger)pieceBelow,row,col);
     return;       //return as piece has "now" not landed yet as we have removed piece below
     }
    if (!pieceClass.equals("clasp.Rider")) {                //Not if Teleport Piece
      if (pieceBelowClass.equals("clasp.Rider")) {
       addRider(piece,(Rider)pieceBelow,row,col);
       return;       //return as piece has "now" not landed yet as we have removed piece below
       }
      }
    }
  if (pieceClass.equals("clasp.Sniper")) ((Sniper)piece).setLanded(true);
  else if (pieceClass.equals("clasp.AssassinPiece")) assassinate(row,col);
  else if (pieceClass.equals("clasp.Leveller")) leveller(row,col);
  else if (pieceClass.equals("clasp.Meteorite")) meteor(row,col);
  else if (pieceClass.equals("clasp.Magnet")) magnetiseSides(row,col);
  else if (pieceClass.equals("clasp.Trophy1")) doTrophy1((Trophy1)piece,row,col);
  else if (pieceClass.equals("clasp.Trophy2")) doTrophy2((Trophy2)piece,row,col);
  if (piece.getPlayer()!=P0) {
    if (((ClaspPiece)piece).isMeteoriteTriggered()) triggerMetoriteStorm((ClaspPiece)piece);
    }
  }

//Landed on Danger Piece
//Remove Danger Piece
//Change all the opponents pieses to sch Destroys
//(except Joker)
private void dangerTriggered(ClaspBasePiece piece, Danger danger, final int row, final int col){
  model.setGridCell(col-1,row,null);    //Remove Danger Piece from Grid
  gameStatus.getClaspView().paintPiece(danger);
  if (piece.getPlayer()==P0) return;      //Player 0 does not trigger anything
  int opponent=(piece.getPlayer()==P1?P2:P1);
  ClaspBasePiece checkPiece;
  for (int schcol=1; schcol<=gameStatus.getGridCols(); schcol++) {
    for (int schrow=1; schrow<=gameStatus.getGridRows(); schrow++) {
      checkPiece=model.getGridCell(schcol-1,schrow-1);
      if (checkPiece!=null) {   //Searching for Sch/Destroys
        if (checkPiece.getPlayer()!=opponent) continue;  //only if opponent piece
        if (checkPiece.isIndestructable()) continue;          //Not Indestructables
        model.setPieceLocation(new SearchDestroy(opponent),schrow,schcol);     //Place Piece on Grid
        }   //end of Check piece null check
      }  //row loop
    } //Column loop
}



//Landed on Teleport
//Move piece dropping to a random row at the top
//Randomly remove teleport itself
private void teleportPiece(ClaspBasePiece piece, Teleport teleport, final int row, final int col){
  model.setGridCell(col-1,row-1,null);    //piece is in teleport - so remove from grid
  gameStatus.getClaspView().paintPiece(piece);

  teleport.setOn(true);
  teleport.setAnimationStep(1);
  gameStatus.getClaspView().paintPiece(teleport);
  ClaspView.pause(TELEPORT_DELAY);  //Pause with current image before continuing
  teleport.setAnimationStep(2);
  gameStatus.getClaspView().paintPiece(teleport);
  ClaspView.pause(TELEPORT_DELAY);  //Pause with current image before continuing
  teleport.setAnimationStep(3);
  gameStatus.getClaspView().paintPiece(teleport);
  ClaspView.pause(TELEPORT_DELAY);  //Pause with current image before continuing
  teleport.setOn(false);
  teleport.setAnimationStep(0);
  if (rand.nextInt(3)==0) model.setGridCell(col-1,row,null);    //remove teleport piece
  gameStatus.getClaspView().paintPiece(teleport);
  ClaspView.pause(TELEPORT_DELAY);  //Pause with current image before continuing

  //Find a random column to drop newly teleported piece
  //Must be a free column
  boolean spaceFound=false;
  int newCol=0;
  while (spaceFound==false) {
    boolean freeSpace = false;
    for (int x=0;x<gameStatus.getGridCols();x++) {
      if (model.getGridCell(x,0)==null) {
        freeSpace=true;
        break;
        }
      }
    if (freeSpace==false) return;      //Should never Happen but just in case
    newCol=(rand.nextInt(gameStatus.getGridCols())+1);
    if (model.getGridCell(newCol-1,0)==null) spaceFound=true;
    }
  model.addPiece(piece,newCol);
  }



//Landed on Boost
//Create Duplicates of piece to top
//Then remove boost to let them all drop.
private void boostPiece(ClaspBasePiece piece, Boost boost, final int row, final int col){
  int player=piece.getPlayer();
  if (player==P0) {
    model.setGridCell(col-1,row,null);    //non player piece OR too near top - just remove boost
    return;
    }

  boost.setBoost(true);
  boost.setAnimationStep(1);
  gameStatus.getClaspView().paintPiece(boost);
  ClaspView.pause(BOOST_DELAY);  //Pause with current image before continuing
  boost.setAnimationStep(2);
  gameStatus.getClaspView().paintPiece(boost);
  ClaspView.pause(BOOST_DELAY);  //Pause with current image before continuing
  boost.setAnimationStep(3);
  gameStatus.getClaspView().paintPiece(boost);
  ClaspView.pause(BOOST_DELAY);  //Pause with current image before continuing

  ClaspPiece newPiece;
  for (int newRow=row-1;newRow>=1;newRow--) {
    newPiece= new ClaspPiece(player);
    model.setPieceLocation(newPiece,newRow,col);     //Place Piece on Grid
    gameStatus.getClaspView().paintPiece(newPiece);
    ClaspView.pause(BOOST_DELAY);  //Pause with current image before continuing
    }

  model.setGridCell(col-1,row,null);    //remove boost piece
  gameStatus.getClaspView().paintPiece(boost);
  }


//Trophy1 Landed
//Set all pieces below to same as this piece
//Then remove this piece
private void doTrophy1(Trophy1 trophy, final int row, final int col){
  int player=trophy.getPlayer();

  ClaspBasePiece piece;
  for (int newRow=row;newRow<=gameStatus.getGridRows();newRow++) {
    piece=model.getGridCell(col-1,newRow-1);    //remove boost piece
    if (piece==null) continue;
    if (piece.getPlayer()==P0) continue;      //dont change non pieces
    if (piece.getPlayer()==player) continue;  //already own piece
    if (piece.isIndestructable()) continue;          //Not Indestructables
    piece.setPlayer(player);                  //set to this player
    gameStatus.getClaspView().paintPiece(piece);
    ClaspView.pause(BOOST_DELAY);             //Pause with current image before continuing
    }
  model.setGridCell(col-1,row-1,null);        //remove shield
  gameStatus.getClaspView().paintPiece(trophy);
  }


//Trophy2 Landed
//Set all pieces oneither side of this piece to same side as this piece
//Then set this trophy to a standard piece
private void doTrophy2(Trophy2 trophy, final int row, final int col){
  int player=trophy.getPlayer();
  ClaspBasePiece piece;
  ClaspPiece newPiece;
  for (int newCol=1;newCol<=gameStatus.getGridCols();newCol++) {
    piece=model.getGridCell(newCol-1,row-1);    //remove boost piece
    if (piece==null) continue;
    if (piece.getPlayer()==P0) continue;      //dont change non pieces
    if (piece.getPlayer()==player) continue;  //already own piece
    if (piece.isIndestructable()) continue;          //Not Indestructables
    piece.setPlayer(player);                  //set to this player
    gameStatus.getClaspView().paintPiece(piece);
    ClaspView.pause(BOOST_DELAY);             //Pause with current image before continuing
    }
  newPiece= new ClaspPiece(player);
  model.setPieceLocation(newPiece,row,col);     //Place Piece on Grid
  gameStatus.getClaspView().paintPiece(newPiece);
  ClaspView.pause(BOOST_DELAY);                 //Pause with current image before continuing
  }


private void revealCashPiece(ClaspBasePiece piece, Cash cash, final int row, final int col){
  if (piece.getClass().getName().equals("clasp.Cash")) return;  //Stack Cash
  if (piece.getPlayer()!=P0) {    //Player 0 does not collect coins
    cash.setRevealed(true);
    gameStatus.increaseCashTotal(piece.getPlayer(),(long)cash.getCashAmount());
    gameStatus.getClaspView().paintPiece(cash);
    ClaspView.pause(STEP_DELAY);  //Pause with current image before continuing
    gameStatus.getMainFrame().updateCtrlPanel();      //Show Current Scores etc
    }
  model.setGridCell(col-1,row,null);    //remove special piece
  }

//Landed on Rider Piece
//Pick it up if player piece
private void addRider(ClaspBasePiece piece, Rider rider, final int row, final int col){
  int player=piece.getPlayer();
  model.setGridCell(col-1,row,null);    //remove rider
  gameStatus.getClaspView().paintPiece(rider);
  if (player==P0) return;    //Player 0 does not collect riders
  int riderNo=rider.getRiderNum();
  if (gameStatus.getTotalRider(player,riderNo)<=0) {  //If first piece of this type
    switch (riderNo) {
      case 1:
        WeaponInfo.showWeaponInfo(gameStatus.getMainFrame(), player, WeaponBuyDlg.RIDER1);    //Show info if the weapon reseached
        break;
      case 2:
        WeaponInfo.showWeaponInfo(gameStatus.getMainFrame(), player, WeaponBuyDlg.RIDER2);    //Show info if the weapon reseached
        break;
      case 3:
        WeaponInfo.showWeaponInfo(gameStatus.getMainFrame(), player, WeaponBuyDlg.RIDER3);    //Show info if the weapon reseached
        break;
      case 4:
        WeaponInfo.showWeaponInfo(gameStatus.getMainFrame(), player, WeaponBuyDlg.RIDER4);    //Show info if the weapon reseached
        break;
      case 5:
        WeaponInfo.showWeaponInfo(gameStatus.getMainFrame(), player, WeaponBuyDlg.RIDER5);    //Show info if the weapon reseached
        break;
      default:
        WeaponInfo.showWeaponInfo(gameStatus.getMainFrame(), player, WeaponBuyDlg.RIDER6);    //Show info if the weapon reseached
        break;
        }
      }
  gameStatus.increaseTotalRider(player,riderNo);    //Update Amount of riders owned
  RidersDlg.initialize(gameStatus.getClaspView(),player,gameStatus);
  RidersDlg.showDialog(gameStatus.getClaspView());
  }

//Landed on Meteorite trigger - remove it and set piece to trigger metorite
private void pickupMeteoriteTrigger(ClaspBasePiece piece, final int row, final int col){
  String pieceClass=piece.getClass().getName();
  model.setGridCell(col-1,row,null);    //remove special piece
  if ((piece.getPlayer()!=P0) && (pieceClass.equals("clasp.Meteorite")==false)) { //must not be a non player or a meteorite piece itself
    ((ClaspPiece)piece).setMeteoriteTriggered(true);  //set players piece to cause metorite when landed on firm ground
    }
  }


//set off Meteorite Storm
private void triggerMetoriteStorm(ClaspPiece piece) {
  piece.setMeteoriteTriggered(false);   //remove flag now
  int player=piece.getPlayer();

  ClaspBasePiece checkPiece;
  for (int col=1; col<=gameStatus.getGridCols(); col++) {
    if (model.getGridCell(col-1,0)!=null) continue;   //only drop where columns isnt full
    for (int row=1; row<=gameStatus.getGridRows(); row++) {
      checkPiece=model.getGridCell(col-1,row-1);
      if (checkPiece!=null) {   //only drop on cols that have a piece to destroy
        if (checkPiece.getPlayer()==P0) break;      //no Bombing of non player piece
        if (checkPiece.getPlayer()==player) break;  //no Bombing of same player piece
        if (piece.isIndestructable()) break;        //Dont kill indestructables
        model.addPiece(new Meteorite(player),col);         //add/drop piece
        break;                                      //check next column
        }   //found piece to destroy
      }  //row loop
    } //Column loop
  }


//Search Destory Action
//This Seeks out a like probe in its path
//It checks the entire grid to see if any probes now match
//Returns true if any have been found
protected boolean searchDestroy() {
  if (searchDestroyGridScan(TARGET_SEARCHING)!=true) return(false);
  ClaspView.pause(SMALL_FIRE_DELAY);  //Pause with current image before continuing
  searchDestroyGridScan(TARGET_SEARCHING2);
  ClaspView.pause(SMALL_FIRE_DELAY);  //Pause with current image before continuing
  searchDestroyGridScan(TARGET_SEARCH_BOMB);
  searchDestroyGridScan(TARGET_RESTORE);
  gameStatus.getClaspView().paintGrid();
  return (true);
  } //end function


//scan entire grid for probe matches passing states
//target_search will just set the matching probes to matched
//target_search2 will just set the matching probes to fire state
//target_remove will just set the matching probes back to normal counters
private boolean searchDestroyGridScan(int state){
  ClaspBasePiece piece;
  boolean foundMatch=false;

  for (int row=1; row<=gameStatus.getGridRows(); row++) {
    for (int col=1; col<=gameStatus.getGridCols(); col++) {
      piece=gameStatus.getClaspModel().getGridCell(col-1,row-1);
        if (piece != null) {
          if (piece.getClass().getName().equals("clasp.SearchDestroy")) {
            if (state==TARGET_RESTORE) {      //restore sch/destroy probes to be normal probes
              if (((SearchDestroy)piece).isFire()==true) { //Only if firing currently
                setTargettedState(row,col,TARGET_RESTORE);   //Turn First Off
                }
              }
            else if (findMatchingSearchDestroy(row,col,state)==true) {  //Found Matching Probe
              foundMatch=true;
                if (state!=TARGET_SEARCH_BOMB) {
                  setTargettedState(row,col,state);   //Turn First Off
                  }
              }
          }
        }
      } //col loop
    } //row loop
  return(foundMatch);          //no lined up Probes at all
  }



//Passed a single probe, find if any mathcing probe for it
//return true if match found
//Meanwhile set the firing states of the probes
private boolean findMatchingSearchDestroy(int row,int col,int state){
  boolean foundMatch=false;

  //Check for probes in all directions
  if(searcherDirection(row,col,state,0,1)==true) foundMatch=true; //search east
  if(searcherDirection(row,col,state,1,0)==true) foundMatch=true; //search south
  if(searcherDirection(row,col,state,1,1)==true) foundMatch=true; //search south east
  if(searcherDirection(row,col,state,1,-1)==true) foundMatch=true; //search south west

  //No matching probes dont continue
  if (foundMatch!=true) return(false);
  return(true);
  }


//do passed direction of search
//for like searcher piece
private boolean searcherDirection(final int row, final int col, int state, int rowInc, int colInc) {
  ClaspBasePiece piece;
  SearchDestroy searcher;
  searcher=(SearchDestroy) model.getGridCell(col-1,row-1);
  boolean pieceBetween=false;

  switch (state) {
    case TARGET_BOMB1:
    case TARGET_BOMB2:
    case TARGET_BOMB3:
      ClaspView.pause(TINY_FIRE_DELAY);  //Pause with current image before continuing
    }

  if (state!=TARGET_SEARCHING) {
    pieceBetween=true;  //We've already found a piece between in previous check
    }
  int schPlayer=searcher.getPlayer();
  int y=row;
  int x=col;
  while(true) {
    y+=rowInc;
    x+=colInc;
    if (y>gameStatus.getGridRows()) return(false);
    if (x>gameStatus.getGridCols()) return(false);
    if (x<1) return(false);
    if (y<1) return(false);
    piece=gameStatus.getClaspModel().getGridCell(x-1,y-1);
    if (piece != null) {
      if (piece.isIndestructable()) continue; //Dont kill indestructables
      if (piece.getClass().getName().equals("clasp.SearchDestroy")) {
        if (piece.getPlayer()==schPlayer) {   //Must be on same side
          //check for bomb line status
          switch (state) {
            case TARGET_SEARCH_BOMB:      //this status just checks if a matching probe exists - then triggers off explosion events
              searcherDirection(row,col,TARGET_BOMB1,rowInc,colInc);
              return(true);
            case TARGET_BOMB1:
              searcherDirection(row,col,TARGET_BOMB2,rowInc,colInc);
              return(true);
            case TARGET_BOMB2:
              searcherDirection(row,col,TARGET_BOMB3,rowInc,colInc);
              return(true);
            case TARGET_BOMB3:
              searcherDirection(row,col,TARGET_REMOVE,rowInc,colInc);
              return(true);
            case TARGET_REMOVE:
              return(true);
              }
          //must be setting a probe status
          if (pieceBetween==true) { //If found at least one piece between
            setTargettedState(y,x,state);   //set probe status
            return(true);
            }
          else return(false);
          }  //same player and probe
        } //is a probe
      else {              //is not a probe - we dont explode probes
        pieceBetween=true;    //There is at least one piece between to explode
        switch (state) {
          case TARGET_BOMB1:
          case TARGET_BOMB2:
          case TARGET_BOMB3:
          case TARGET_REMOVE:
            setTargettedState(y,x,state);   //set probe status
            break;
          }
        }
      } // piece exists
    }
  }

//Magnetise a piece to the left AND right
private void magnetiseSides(final int row, final int col){
    Magnet magnet;
    magnet=(Magnet)model.getGridCell(col-1,row-1);
    magnetise(row,col,-1);      //Magnetise to left
    magnetise(row,col,+1);      //Magnetise to Right
    ClaspView.pause(STEP_DELAY);
    model.setPieceLocation(new ClaspPiece(magnet.getPlayer()),row,col);  //Place Piece on Grid
    }


//Magnetise a piece to the left or right (depending on colInc)
//Bring that piece to be next to current piece
private void magnetise(final int row, final int col, final int colInc){
  ClaspBasePiece piece;
  int to_x=col;
  int from_x;
  while(true) {
    from_x=to_x;
    to_x+=colInc;
    if (to_x>gameStatus.getGridCols()) return;
    if (to_x<1) return;
    piece=model.getGridCell(to_x-1,row-1);
    if (piece==null) continue;                      //skip till a piece is found
//    Dont do this any more - unless problem
//    if (piece.getPlayer()==P0) return;              //Dont draw non Player pieces
    slidePiece(row,to_x,(colInc*-1));               //Slide piece towards nearest piece
    return;
    }
  }

//Used for Magnetise to slide piece to the right or left untill it hits a piece
//(or side for safety)
private void slidePiece(final int row, final int col, final int colInc){
  ClaspBasePiece piece;
  int to_x=col;
  int from_x;
  while(true) {
    from_x=to_x;
    to_x+=colInc;
    if (to_x>gameStatus.getGridCols()) return;   //past end of column
    if (to_x<1) return;                          //past start of column
    piece=model.getGridCell(to_x-1,row-1);
    if (piece!=null) return;                  //hit a piece
    try {
      gameStatus.getClaspView().doSlidePieceAnimation(row, from_x, colInc);    //Start Animation
      }
    finally {
      piece=model.getGridCell(from_x-1,row-1);
      if (piece==null) return;        //Just in case
      piece.endAnimation();           //End Animation Mode
      model.movePiece(piece,row,to_x);         //Physically move the piece in the grid
      }
    }
  }


//Meteor Action
//This Takes out the piece below it
private void meteor(final int row, final int col) {
  Meteorite meteor;
  meteor=(Meteorite)model.getGridCell(col-1,row-1);
  int player=meteor.getPlayer();
  setTargettedState(row, col, TARGET_REMOVE); //remove meteor piece

  ClaspBasePiece piece;
  if (row < gameStatus.getGridRows()){     //not landed on bottom row
    piece=model.getGridCell(col-1,row);    //get piece Below
    if (piece.getPlayer()==P0) return;     //no Bombing of non player piece
    if (piece.getPlayer()==player) return;  //no Bombing of same player piece
    if (piece.isIndestructable()) return;   //Dont kill indestructables
    setTargettedState(row+1, col, TARGET_BOMB1); //remove meteor piece
    ClaspView.pause(STEP_DELAY);  //Pause with current image before continuing
    setTargettedState(row+1, col, TARGET_BOMB2); //remove meteor piece
    ClaspView.pause(STEP_DELAY);  //Pause with current image before continuing
    setTargettedState(row+1, col, TARGET_BOMB3); //remove meteor piece
    ClaspView.pause(STEP_DELAY);  //Pause with current image before continuing
    setTargettedState(row+1, col, TARGET_REMOVE); //remove meteor piece
    gameStatus.getClaspView().paintGrid();
    }
  }


//Leveller Action
//This Takes out the entire row that it lands on
private void leveller(final int row, final int col) {
  Leveller leveller;
  leveller=(Leveller)model.getGridCell(col-1,row-1);

  //set leveller to fire image 1
  leveller.setFire(true);
  leveller.setAnimationStep(1);
  gameStatus.getClaspView().paintPiece(leveller);
  ClaspView.pause(FIRE_DELAY);  //Pause with current image before continuing

  //set leveller to fire image 2
  leveller.setAnimationStep(2);
  gameStatus.getClaspView().paintPiece(leveller);
  ClaspView.pause(FIRE_DELAY);  //Pause with current image before continuing

  //blast full line - icluding this piece
  ClaspBasePiece piece;
  for (int state=TARGET_BOMB1;state<=TARGET_REMOVE;state++){    //loop through explosion states
    for (int x=0; x<gameStatus.getGridCols(); x++) {
      setTargettedState(row,x+1,state);                         //set explosion state
      }
    ClaspView.pause(SMALL_STEP_DELAY);      //Pause with current image before continuing
    }
  gameStatus.getClaspView().paintGrid();
  ClaspView.pause(SHOW_END_RESULT_DELAY);
  }



//Assissanation action
//This randomly takes out either a cross OR plus section
private void assassinate(final int row, final int col) {
  boolean hitTarget=false;
  int spins=4+rand.nextInt(8);
  AssassinPiece piece;
  piece=(AssassinPiece)model.getGridCell(col-1,row-1);

  //Spin piece random number of times
  for (int i=0;i<spins;i++){
    if (piece.isCross()) piece.setCross(false);
    else piece.setCross(true);
    gameStatus.getClaspView().paintPiece(piece);
    ClaspView.pause(SPIN_DELAY);  //Pause with current image before continuing
    }

  if (piece.isCross()) {
  //Landed on Cross target the cross section
    if (targetCross(row,col,TARGET_ON,STEP_DELAY)==true) {
      hitTarget=true;
      targetCross(row,col,TARGET_OFF,STEP_DELAY);
      targetCross(row,col,TARGET_ON,STEP_DELAY);
      piece.setFire(true);
      gameStatus.getClaspView().paintPiece(piece);
      targetCross(row,col,TARGET_BOMB1,SMALL_FIRE_DELAY);
      targetCross(row,col,TARGET_BOMB2,SMALL_FIRE_DELAY);
      targetCross(row,col,TARGET_BOMB3,SMALL_FIRE_DELAY);
      targetCross(row,col,TARGET_REMOVE,0);
      gameStatus.getClaspView().paintGrid();
      ClaspView.pause(SHOW_END_RESULT_DELAY);
      }
    }
  else {
  //Landed on Plus target the Plus section
    if (targetPlus(row,col,TARGET_ON,STEP_DELAY)==true) {
      hitTarget=true;
      targetPlus(row,col,TARGET_OFF,STEP_DELAY);
      targetPlus(row,col,TARGET_ON,STEP_DELAY);
      piece.setFire(true);
      gameStatus.getClaspView().paintPiece(piece);
      targetPlus(row,col,TARGET_BOMB1,SMALL_FIRE_DELAY);
      targetPlus(row,col,TARGET_BOMB2,SMALL_FIRE_DELAY);
      targetPlus(row,col,TARGET_BOMB3,SMALL_FIRE_DELAY);
      targetPlus(row,col,TARGET_REMOVE,0);
      gameStatus.getClaspView().paintGrid();
      ClaspView.pause(SHOW_END_RESULT_DELAY);
      }
    }

  //Remove Assassin and replace with standard piece
  model.setPieceLocation(new ClaspPiece(piece.getPlayer()),row,col);     //Place Piece on Grid
  }

//Target a cross section around piece
//State identifies which image is being displayed
//ie. target on/off or explosion
private boolean targetCross(final int row, final int col, final int state, final int delay){
    boolean hitFound=false;
    //Set all pieces that need targetting /untagetting
    if ((row+1 <= gameStatus.getGridRows()) && (col+1 <= gameStatus.getGridCols())){
      if (setTargettedState(row+1,col+1,state)==true) hitFound=true;
    }
    if ((row+1 <= gameStatus.getGridRows()) && (col-1 >= 1)){
      if (setTargettedState(row+1,col-1,state)==true) hitFound=true;
    }
    if ((row-1 >= 1) && (col-1 >= 1)){
      if (setTargettedState(row-1,col-1,state)==true) hitFound=true;
    }
    if ((row-1 >= 1) && (col+1 <= gameStatus.getGridCols())){
      if (setTargettedState(row-1,col+1,state)==true) hitFound=true;
    }
  if (hitFound!=true) return(false);
  ClaspView.pause(delay);  //Pause with current image before continuing
  return(true);
  }

//Target a plus section around piece
//State identifies which image is being displayed
//ie. target on/off or explosion
private boolean targetPlus(final int row, final int col, final int state, final int delay){
    boolean hitFound=false;
    //Set all pieces that need targetting /untagetting
    if (row+1 <= gameStatus.getGridRows()){
      if (setTargettedState(row+1,col,state)==true) hitFound=true;
    }
    if (col-1 >= 1){
      if (setTargettedState(row,col-1,state)==true) hitFound=true;
    }
    if (row-1 >= 1){
      if (setTargettedState(row-1,col,state)==true) hitFound=true;
    }
    if (col+1 <= gameStatus.getGridCols()){
      if (setTargettedState(row,col+1,state)==true) hitFound=true;
    }
  if (hitFound!=true) return(false);
  ClaspView.pause(delay);  //Pause with current image before continuing
  return(true);
  }

private boolean setTargettedState(final int row, final int col, final int state){
  ClaspBasePiece piece;
  piece=model.getGridCell(col-1,row-1);
  if (piece==null) return(false);   //No hit found
  if (piece.isIndestructable()) return(false); //Dont kill indestructables
  switch (state) {
    case TARGET_ON:
      piece.setTargetted(true);
      break;
    case TARGET_OFF:
      piece.setTargetted(false);
      break;
    case TARGET_BOMB1:
      piece.setBombed(true);
      piece.setAnimationStep(1);
      break;
    case TARGET_BOMB2:
      piece.setAnimationStep(2);
      break;
    case TARGET_BOMB3:
      piece.setAnimationStep(3);
      break;
    case TARGET_REMOVE:
      model.setGridCell(col-1,row-1,null);    //remove piece
      return(true);                           //No display to update
    case TARGET_SEARCHING:                  //Will always be set to one of the probes
      ((SearchDestroy)piece).setFire(true);        //set it up
      piece.setAnimationStep(1);
      break;
    case TARGET_SEARCHING2:        //Will always be set to one of the probes
      piece.setAnimationStep(2);
      break;
    case TARGET_RESTORE:
      model.setPieceLocation(new ClaspPiece(piece.getPlayer()),row,col);     //Place Piece on Grid
      return(true);                           //No display to update
    }
  gameStatus.getClaspView().paintPiece(piece);
  return(true);
  }


//See if Research is finished
protected void checkResearch() {
  int player=gameStatus.getCurrPlayer();
  if (gameStatus.getTotalResearch(player)<=0) return;   //No Research currently being done by this player
  int lev=gameStatus.getResearchLevel(player);
  int randNum=lev*10;
  if (rand.nextInt(randNum)!=0) return;        //Research Random - checked once per players go
  JOptionPane.showMessageDialog(gameStatus.getMainFrame(),"Research has finished for Player " + ((player==P1)?"One":"Two") + "...");

  int classType=0;

  gameStatus.incResearchLevel(player);          //Move to next research level
  gameStatus.setTotalResearch(player,0);
  if (gameStatus.getTotalMagnet(player)<0) {
    gameStatus.setTotalMagnet(player,0);
    classType=WeaponBuyDlg.MAGNET;
    }
  else if (gameStatus.getTotalSch(player)<0) {
    gameStatus.setTotalSch(player,0);
    classType=WeaponBuyDlg.SCH;
    }
  else if (gameStatus.getTotalAssassin(player)<0) {
    gameStatus.setTotalAssassin(player,0);
    classType=WeaponBuyDlg.ASSASSIN;
    }
  else if (gameStatus.getTotalSniper(player)<0) {
    gameStatus.setTotalSniper(player,0);
    classType=WeaponBuyDlg.SNIPER;
    }
  else if (gameStatus.getTotalLeveller(player)<0) {
    gameStatus.setTotalLeveller(player,0);
    classType=WeaponBuyDlg.LEVELLER;
    }
  WeaponInfo.showWeaponInfo(gameStatus.getMainFrame(), player, classType);    //Show info if the weapon reseached
  }

//New Grid Actions like setting up a landscape
protected void NewGridSetup(){
  if (model.getLevelMode()!=LM_LANDSCAPE) return;
  model.setGridMode(GM_PERFORM_ACTION);                 //performing drop action mode

  ClaspBasePiece piece=null;
  int randNum = 3;      //Random chance for dropping a piece in a column
  int randType= rand.nextInt(2);
  //Do random drops of pieces
  for (int cntr=1; cntr<gameStatus.getGridRows(); cntr++) {
    for (int col=1; col<=gameStatus.getGridCols(); col++) {
      if (model.getGridCell(col-1,0)!=null) continue;   //only drop where columns isnt full
      if (rand.nextInt(randNum)!=0) continue;                 //random chance of dropping piece
      //Do Drops Of Whatever Piece
      if (randType==0) piece = new Cash();
      if (randType==1) piece = new Egg();
      model.addPiece(piece,col);                //add/drop piece
    }
  }
}

//Chance to Hatch Any eggs present
//Pass EHM_RND to randomly hatch eggs
//Pass EHM_ALL to hatch all eggs
protected void hatchEggs(int mode) {
  ClaspBasePiece piece;
  int player;
  for (int row=1; row<=gameStatus.getGridRows(); row++) {
    for (int col=1; col<=gameStatus.getGridCols(); col++) {
      piece=model.getGridCell(col-1,row-1);
      if (piece == null) continue;
      if (piece.getClass().getName().equals("clasp.Egg")==false) continue; //Only interested in eggs
      if (mode==Egg.EHM_RND) {
        if (rand.nextInt(Egg.EGG_HATCH_RAND)!=0) continue;        //Dont hatch yet
      }
      Egg egg=(Egg)piece;
      egg.setCrack(true);
      egg.setAnimationStep(1);
      gameStatus.getClaspView().paintPiece(egg);
      ClaspView.pause(HATCH_DELAY);  //Pause with current image before continuing
      piece.setAnimationStep(2);
      gameStatus.getClaspView().paintPiece(egg);
      ClaspView.pause(HATCH_DELAY);  //Pause with current image before continuing
      player=(rand.nextInt(2)==0?ClaspConstants.P1:ClaspConstants.P2);//Set to Hatch to random player
      egg.setHatchPlayer(player);
      piece.setAnimationStep(3);
      gameStatus.getClaspView().paintPiece(egg);
      ClaspView.pause(HATCH_DELAY);  //Pause with current image before continuing
      ClaspPiece newPiece= new ClaspPiece(player);
      model.setPieceLocation(newPiece,row,col);     //Place Piece on Grid
      gameStatus.getClaspView().paintPiece(newPiece);
      }
    }
 }



} //End of Class