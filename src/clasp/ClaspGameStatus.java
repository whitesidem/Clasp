package clasp;

import javax.swing.*;   //JOptionPane Dialogs


/**
 * Title:        Game Status Controler
 * Description:  Stores status of entire game and controls the game
 * An inner class stores the details of a single player.
 * And an array points to both players
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author M.Whiteside
 * @version 1.0
 */

public class ClaspGameStatus implements ClaspConstants{

//Private members
private static ClaspModel claspModel;         //Main Model containing grid and grid logic
private static ClaspView claspView;           //View of grid and presentation logic
private static MainFrame mainFrame;           //Main application frame
private int gameLevel = 1;                    //Level reached
private int currPlayer;                       //Stores Current Player being used
private static ClaspProperties claspProperties;
private ClaspPlayer players[] = new ClaspPlayer[2];
protected boolean view_initialised=false;

public ClaspGameStatus(){
  claspProperties = new ClaspProperties();    //Create the Properties object
  ClaspProperties props =ClaspProperties.readObject();  //Create and load to temporary props Object
  if (props!=null) claspProperties=props;     //If Load succeeded, use the loaded properties
  players[0]= new ClaspPlayer();
  players[1]= new ClaspPlayer();
}

//First Method called by Application
//Must contrain cleanup for new game methods
protected void initialiseGame(){
  cleanupGame();                                   //CleanUp for new game
  getClaspModel().startNewGrid();                  //Start next grid
}

private void cleanupGame(){
  gameLevel = 0;                                    //Level reached
}

//Get Weapon Unlock passwords from each player
//Pass player
protected void getWeaponPassword(final int player){
  String password;
  boolean valid=false;
  int level=0;
  while (!valid) {
    password=(String)JOptionPane.showInputDialog(getMainFrame(),
      ((player==ClaspConstants.P1)?"Blue Team ":"Red Team ") + "Enter your Weapon Level Unlock Password Now",
      "Weapon Level Unlock Dialog",
      JOptionPane.PLAIN_MESSAGE,
      null,
      null,
      null);
    if (password==null) {
      valid=true;
      }
    else password=password.toUpperCase();   //Uppercase password if not null

    if (valid==true);     //If not Null Password continue
    else if (password.equals("")) {
        valid=true;
      }
    else if ((player==P1)&&(password.equals(WeaponInfo.WP_P1_1))) {
        level=1;
        JOptionPane.showMessageDialog(getMainFrame(),"Level One Weapons Unlocked");
        valid=true;
      }
    else if ((player==P1)&&(password.equals(WeaponInfo.WP_P1_2))) {
        level=2;
        JOptionPane.showMessageDialog(getMainFrame(),"Level Two Weapons Unlocked");
        valid=true;
      }
    else if ((player==P1)&&(password.equals(WeaponInfo.WP_P1_3))) {
        level=3;
        JOptionPane.showMessageDialog(getMainFrame(),"Level Three Weapons Unlocked");
        valid=true;
      }
    else if ((player==P1)&&(password.equals(WeaponInfo.WP_P1_4))) {
        level=4;
        JOptionPane.showMessageDialog(getMainFrame(),"Level Four Weapons Unlocked");
        valid=true;
      }
    else if ((player==P1)&&(password.equals(WeaponInfo.WP_P1_5))) {
        level=5;
        JOptionPane.showMessageDialog(getMainFrame(),"Level Five Weapons Unlocked");
        valid=true;
      }
    else if ((player==P2)&&(password.equals(WeaponInfo.WP_P2_1))) {
        level=1;
        JOptionPane.showMessageDialog(getMainFrame(),"Level One Weapons Unlocked");
        valid=true;
      }
    else if ((player==P2)&&(password.equals(WeaponInfo.WP_P2_2))) {
        level=2;
        JOptionPane.showMessageDialog(getMainFrame(),"Level Two Weapons Unlocked");
        valid=true;
      }
    else if ((player==P2)&&(password.equals(WeaponInfo.WP_P2_3))) {
        level=3;
        JOptionPane.showMessageDialog(getMainFrame(),"Level Three Weapons Unlocked");
        valid=true;
      }
    else if ((player==P2)&&(password.equals(WeaponInfo.WP_P2_4))) {
        level=4;
        JOptionPane.showMessageDialog(getMainFrame(),"Level Four Weapons Unlocked");
        valid=true;
      }
    else if ((player==P2)&&(password.equals(WeaponInfo.WP_P2_5))) {
        level=5;
        JOptionPane.showMessageDialog(getMainFrame(),"Level Five Weapons Unlocked");
        valid=true;
      }
    else if (password.equals(WeaponInfo.WP_ALL)) {
        level=98;
        JOptionPane.showMessageDialog(getMainFrame(),"You Cheat");
        valid=true;
      }
    else if (password.equals(WeaponInfo.WP_FULL)) {
        level=99;
        JOptionPane.showMessageDialog(getMainFrame(),"Time to get Nasty");
        valid=true;
      }
    else if (password.equals("LEYLAND")) {
        JOptionPane.showMessageDialog(getMainFrame(),"Cool City, but not the password");
      }
    else if (password.equals("MICK")) {
        JOptionPane.showMessageDialog(getMainFrame(),"Its not that Easy!!");
      }
    else if (password.equals("GINNY")) {
        JOptionPane.showMessageDialog(getMainFrame(),"No Zog names accepted");
      }
    else {
        JOptionPane.showMessageDialog(getMainFrame(),"Nope, Your Just Guessing!");
      }
   }
  //Set start weapons Levels for this player
  switch(level){
    case 99:
      setMaxMagnet(player,10);
      setMaxSch(player,10);
      setMaxAssassin(player,10);
      setMaxSniper(player,10);
      setMaxLeveller(player,10);
      setTotalJoker(player,2);
    case 98:
      setTotalLeveller(player,getMaxLeveller(player));
      setTotalSniper(player, getMaxSniper(player));
      setTotalAssassin(player, getMaxAssassin(player));
      setTotalSch(player, getMaxSch(player));
      setTotalMagnet(player, getMaxMagnet(player));
      break;
    case 5:
      setTotalLeveller(player, 0);
    case 4:
      setTotalSniper(player, 0);
    case 3:
      setTotalAssassin(player, 0);
    case 2:
      setTotalSch(player, 0);
    case 1:
      setTotalMagnet(player, 0);
    }
  setResearchLevel(player,(level+1));
  }

//Move onto Next Level returning new level
protected int nextGameLevel(){
  gameLevel++;                            //increment level
  getMainFrame().statusBar.setLevelText(gameLevel); //Set Status Bar text
  return(gameLevel);
  }


protected int switchPlayer(){
  setCurrPlayer((getCurrPlayer()==P1)?P2:P1);
  return currPlayer;
  }


//Dumb Get & Set Methods
protected MainFrame getMainFrame() {
  return mainFrame;
  }
protected void setMainFrame(MainFrame mainFrame) {
  this.mainFrame=mainFrame;
  }
protected ClaspModel getClaspModel() {
  return claspModel;
  }
protected void setClaspModel(ClaspModel claspModel) {
  this.claspModel=claspModel;
  }
protected ClaspView getClaspView() {
  return claspView;
  }
protected void setClaspView(ClaspView claspView) {
  this.claspView=claspView;
  }
protected int getGameLevel(){
  return gameLevel;
  }
protected void setGameLevel(int level){
  gameLevel=level;
  }
protected int getCurrPlayer() {
  return currPlayer;
  }
protected void setCurrPlayer(int player) {
  this.currPlayer=player;;
  }
public int getGridRows(){
  return(claspProperties.getGridRows());
}
public int getGridCols(){
  return(claspProperties.getGridCols());
}
protected int getFullLine(){
  return(claspProperties.getFullLine());
}
protected ClaspProperties getClaspProperties(){
  return(claspProperties);
  }
protected void setClaspProperties(ClaspProperties props){
  claspProperties=props;
  }
protected long getCashTotal(int player) {
  return players[player].getCashTotal();
  }
protected void setCashTotal(int player, long total) {
  players[player].setCashTotal(total);
  }
protected void increaseCashTotal(int player, long value) {
  players[player].setCashTotal(players[player].getCashTotal()+value);
  }
protected void decreaseCashTotal(int player, long value) {
  players[player].setCashTotal(players[player].getCashTotal()-value);
  }
protected int getTotalLines(int player) {
  return players[player].getTotalLines();
  }
protected void setTotalLines(int player,int lines) {
  players[player].setTotalLines(lines);
  }
protected void increaseTotalLines(int player, int lines) {
  players[player].setTotalLines(players[player].getTotalLines()+lines);
  }
protected int getGridsWon(int player) {
  return players[player].getGridsWon();
  }
protected void setGridsWon(int player, int grids) {
  players[player].setGridsWon(grids);
  }
protected void increaseGridsWon(int player) {
  players[player].setGridsWon(players[player].getGridsWon()+1);
  }
protected int getTotalMagnet(int player) {
  return players[player].getTotalMagnet();
  }
protected void setTotalMagnet(int player, int value){
  players[player].setTotalMagnet(value);
  }
protected void decTotalMagnet(int player){
  players[player].setTotalMagnet(players[player].getTotalMagnet()-1);
  }
protected int getTotalSch(int player) {
  return players[player].getTotalSch();
  }
protected void setTotalSch(int player, int value){
  players[player].setTotalSch(value);
  }
protected void decTotalSch(int player){
  players[player].setTotalSch(players[player].getTotalSch()-1);
  }
protected int getTotalAssassin(int player) {
  return players[player].getTotalAssassin();
  }
protected void setTotalAssassin(int player, int value){
  players[player].setTotalAssassin(value);
  }
protected void decTotalAssassin(int player){
  players[player].setTotalAssassin(players[player].getTotalAssassin()-1);
  }
protected int getTotalSniper(int player) {
  return players[player].getTotalSniper();
  }
protected void setTotalSniper(int player, int value){
  players[player].setTotalSniper(value);
  }
protected void decTotalSniper(int player){
  players[player].setTotalSniper(players[player].getTotalSniper()-1);
  }
protected int getTotalLeveller(int player) {
  return players[player].getTotalLeveller();
  }
protected void setTotalLeveller(int player, int value){
  players[player].setTotalLeveller(value);
  }
protected void decTotalLeveller(int player){
  players[player].setTotalLeveller(players[player].getTotalLeveller()-1);
  }

public void setMaxMagnet(int player,int maxMagnet) {
    players[player].setMaxMagnet(maxMagnet);
  }
public int getMaxMagnet(int player) {
    return players[player].getMaxMagnet();
  }
public void setMaxAssassin(int player,int maxAssassin) {
    players[player].setMaxAssassin(maxAssassin);
  }
public int getMaxAssassin(int player) {
    return players[player].getMaxAssassin();
  }
public void setMaxLeveller(int player, int maxLeveller) {
    players[player].setMaxLeveller(maxLeveller);
  }
public int getMaxLeveller(int player) {
    return players[player].getMaxLeveller();
  }
public void setMaxSch(int player,int maxSch) {
    players[player].setMaxSch(maxSch);
  }
public int getMaxSch(int player) {
    return players[player].getMaxSch();
  }
public void setMaxSniper(int player,int maxSniper) {
    players[player].setMaxSniper(maxSniper);
  }
public int getMaxSniper(int player) {
    return players[player].getMaxSniper();
  }
protected int getTotalResearch(int player) {
  return players[player].getTotalResearch();
  }
protected void setTotalResearch(int player, int value){
  players[player].setTotalResearch(value);
  }
protected int getTotalJoker(int player) {
  return players[player].getTotalJoker();
  }
protected void setTotalJoker(int player, int value){
  players[player].setTotalJoker(value);
  }
protected void decTotalJoker(int player){
  players[player].setTotalJoker(players[player].getTotalJoker()-1);
  }
protected void increaseTotalJoker(int player){
  players[player].setTotalJoker(players[player].getTotalJoker()+1);
  }
protected int getResearchLevel(int player) {
  return players[player].getResearchLevel();
  }
protected void setResearchLevel(int player, int value){
  players[player].setResearchLevel(value);
  }
protected void incResearchLevel(int player){
  players[player].setResearchLevel(players[player].getResearchLevel()+1);
  }
protected void decTotalTrophy1(int player){
  players[player].setTotalTrophy1(players[player].getTotalTrophy1()-1);
  }
protected void increaseTotalTrophy1(int player){
  players[player].setTotalTrophy1(players[player].getTotalTrophy1()+1);
  }
protected int getTotalTrophy1(int player) {
  return players[player].getTotalTrophy1();
  }
protected void setTotalTrophy1(int player, int value){
  players[player].setTotalTrophy1(value);
  }
protected void decTotalTrophy2(int player){
  players[player].setTotalTrophy2(players[player].getTotalTrophy2()-1);
  }
protected void increaseTotalTrophy2(int player){
  players[player].setTotalTrophy2(players[player].getTotalTrophy2()+1);
  }
protected int getTotalTrophy2(int player) {
  return players[player].getTotalTrophy2();
  }
protected void setTotalTrophy2(int player, int value){
  players[player].setTotalTrophy2(value);
  }
protected void decTotalRider(int player, int num){
  players[player].setTotalRider((num-1),players[player].getTotalRider((num-1))-1);
  }
protected void increaseTotalRider(int player, int num){
  players[player].setTotalRider((num-1),players[player].getTotalRider((num-1))+1);
  }
protected int getTotalRider(int player, int num) {
  return players[player].getTotalRider((num-1));
  }
protected void setTotalRider(int player, int num, int value){
  players[player].setTotalRider((num-1), value);
  }


//Stream Object to Property File
protected void saveGame(){
  SaveGame.saveGame(this);        //Save all relevant properties to file
  }

//Read Property File into Object
protected void loadGame() {
  SaveGame.loadGame(this);        //Load all relevant properties from file
  }




}