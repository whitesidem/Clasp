package clasp;

import java.io.*;


/**
 * Title:        Clasp Version 2
 * Description:  Storage Vessel for game Stats Info
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author M.Whiteside
 * @version 1.0
 */

public class SaveGame implements java.io.Serializable, ClaspConstants {
  private static final String file = "ClaspSave.bin";

  private long p1_cashTotal;
  private int p1_totalLines;
  private int p1_gridsWon;
  private int p1_totalResearch;
  private int p1_totalMagnet;
  private int p1_totalSch;
  private int p1_totalAssassin;
  private int p1_totalSniper;
  private int p1_totalLeveller;
  private int p1_maxMagnet;
  private int p1_maxAssassin;
  private int p1_maxLeveller;
  private int p1_maxSch;
  private int p1_maxSniper;
  private int p1_researchLevel;
  private int p1_totalJoker;
  private int p1_totalTrophy1;
  private int p1_totalTrophy2;
  private int p1_totalRiders[];

  private long p2_cashTotal;
  private int p2_totalLines;
  private int p2_gridsWon;
  private int p2_totalResearch;
  private int p2_totalMagnet;
  private int p2_totalSch;
  private int p2_totalAssassin;
  private int p2_totalSniper;
  private int p2_totalLeveller;
  private int p2_maxMagnet;
  private int p2_maxAssassin;
  private int p2_maxLeveller;
  private int p2_maxSch;
  private int p2_maxSniper;
  private int p2_researchLevel;
  private int p2_totalJoker;
  private int p2_totalTrophy1;
  private int p2_totalTrophy2;
  private int p2_totalRiders[];

  private int gameLevel;                    //Level reached
  private int currPlayer;                   //Stores Current Player being used

  public SaveGame(){
    p1_totalRiders=new int[6];
    p2_totalRiders=new int[6];
    }

  public static void saveGame(ClaspGameStatus gameStatus){
    SaveGame save= new SaveGame();
    save.p1_cashTotal = gameStatus.getCashTotal(P1);
    save.p1_totalLines = gameStatus.getTotalLines(P1);
    save.p1_gridsWon= gameStatus.getGridsWon(P1);
    save.p1_totalResearch= gameStatus.getTotalResearch(P1);
    save.p1_totalMagnet= gameStatus.getTotalMagnet(P1);
    save.p1_totalSch= gameStatus.getTotalSch(P1);
    save.p1_totalAssassin= gameStatus.getTotalAssassin(P1);
    save.p1_totalSniper= gameStatus.getTotalSniper(P1);
    save.p1_totalLeveller= gameStatus.getTotalLeveller(P1);
    save.p1_maxMagnet= gameStatus.getMaxMagnet(P1);
    save.p1_maxAssassin= gameStatus.getMaxAssassin(P1);
    save.p1_maxLeveller= gameStatus.getMaxLeveller(P1);
    save.p1_maxSch= gameStatus.getMaxSch(P1);
    save.p1_maxSniper= gameStatus.getMaxSniper(P1);
    save.p1_researchLevel= gameStatus.getResearchLevel(P1);
    save.p1_totalJoker= gameStatus.getTotalJoker(P1);
    save.p1_totalTrophy1= gameStatus.getTotalTrophy1(P1);
    save.p1_totalTrophy2= gameStatus.getTotalTrophy2(P1);
    save.p1_totalRiders[0]= gameStatus.getTotalRider(P1,1);
    save.p1_totalRiders[1]= gameStatus.getTotalRider(P1,2);
    save.p1_totalRiders[2]= gameStatus.getTotalRider(P1,3);
    save.p1_totalRiders[3]= gameStatus.getTotalRider(P1,4);
    save.p1_totalRiders[4]= gameStatus.getTotalRider(P1,5);
    save.p1_totalRiders[5]= gameStatus.getTotalRider(P1,6);

    save.p2_cashTotal = gameStatus.getCashTotal(P2);
    save.p2_totalLines = gameStatus.getTotalLines(P2);
    save.p2_gridsWon= gameStatus.getGridsWon(P2);
    save.p2_totalResearch= gameStatus.getTotalResearch(P2);
    save.p2_totalMagnet= gameStatus.getTotalMagnet(P2);
    save.p2_totalSch= gameStatus.getTotalSch(P2);
    save.p2_totalAssassin= gameStatus.getTotalAssassin(P2);
    save.p2_totalSniper= gameStatus.getTotalSniper(P2);
    save.p2_totalLeveller= gameStatus.getTotalLeveller(P2);
    save.p2_maxMagnet= gameStatus.getMaxMagnet(P2);
    save.p2_maxAssassin= gameStatus.getMaxAssassin(P2);
    save.p2_maxLeveller= gameStatus.getMaxLeveller(P2);
    save.p2_maxSch= gameStatus.getMaxSch(P2);
    save.p2_maxSniper= gameStatus.getMaxSniper(P2);
    save.p2_researchLevel= gameStatus.getResearchLevel(P2);
    save.p2_totalJoker= gameStatus.getTotalJoker(P2);
    save.p2_totalTrophy1= gameStatus.getTotalTrophy1(P2);
    save.p2_totalTrophy2= gameStatus.getTotalTrophy2(P2);
    save.p2_totalRiders[0]= gameStatus.getTotalRider(P2,1);
    save.p2_totalRiders[1]= gameStatus.getTotalRider(P2,2);
    save.p2_totalRiders[2]= gameStatus.getTotalRider(P2,3);
    save.p2_totalRiders[3]= gameStatus.getTotalRider(P2,4);
    save.p2_totalRiders[4]= gameStatus.getTotalRider(P2,5);
    save.p2_totalRiders[5]= gameStatus.getTotalRider(P2,6);

    save.gameLevel = gameStatus.getGameLevel();
    save.currPlayer = gameStatus.getCurrPlayer();

   try
    {
      // Create the object output stream
      ObjectOutputStream objectOut =
                    new ObjectOutputStream(
                    new BufferedOutputStream(
                    new FileOutputStream(file)));
      objectOut.writeObject(save);            // Write first object
      objectOut.flush();                       //Force full write to take place now
      objectOut.close();                         // Close the output stream
    }
    catch(NotSerializableException e)
    {
      System.err.println(e);
    }
    catch(InvalidClassException e)
    {
      System.err.println(e);
    }
    catch(IOException e)
    {
      System.err.println(e);
    }
  }

  public static void loadGame(ClaspGameStatus gameStatus){
    SaveGame save= null;
    try
    {
      ObjectInputStream objectIn =
                     new ObjectInputStream(
                     new BufferedInputStream(
                     new FileInputStream(file)));

      save = (SaveGame)objectIn.readObject();
      objectIn.close();                          // Close the input stream
    }
    catch(IOException e)
    {
      save=null;
    }
    catch(ClassNotFoundException e)
    {
      save=null;
      System.err.println(e);
    }
  if (save==null) return;

  //Load Properties
    gameStatus.setCashTotal(P1,save.p1_cashTotal);
    gameStatus.setTotalLines(P1,save.p1_totalLines);
    gameStatus.setGridsWon(P1,save.p1_gridsWon);
    gameStatus.setTotalResearch(P1,save.p1_totalResearch);
    gameStatus.setTotalMagnet(P1,save.p1_totalMagnet);
    gameStatus.setTotalSch(P1,save.p1_totalSch);
    gameStatus.setTotalAssassin(P1,save.p1_totalAssassin);
    gameStatus.setTotalSniper(P1,save.p1_totalSniper);
    gameStatus.setTotalLeveller(P1,save.p1_totalLeveller);
    gameStatus.setMaxMagnet(P1,save.p1_maxMagnet);
    gameStatus.setMaxAssassin(P1,save.p1_maxAssassin);
    gameStatus.setMaxLeveller(P1,save.p1_maxLeveller);
    gameStatus.setMaxSch(P1,save.p1_maxSch);
    gameStatus.setMaxSniper(P1,save.p1_maxSniper);
    gameStatus.setResearchLevel(P1,save.p1_researchLevel);
    gameStatus.setTotalJoker(P1,save.p1_totalJoker);
    gameStatus.setTotalTrophy1(P1,save.p1_totalTrophy1);
    gameStatus.setTotalTrophy2(P1,save.p1_totalTrophy2);
    gameStatus.setTotalRider(P1,1,save.p1_totalRiders[0]);
    gameStatus.setTotalRider(P1,2,save.p1_totalRiders[1]);
    gameStatus.setTotalRider(P1,3,save.p1_totalRiders[2]);
    gameStatus.setTotalRider(P1,4,save.p1_totalRiders[3]);
    gameStatus.setTotalRider(P1,5,save.p1_totalRiders[4]);
    gameStatus.setTotalRider(P1,6,save.p1_totalRiders[5]);

    gameStatus.setCashTotal(P2,save.p2_cashTotal);
    gameStatus.setTotalLines(P2,save.p2_totalLines);
    gameStatus.setGridsWon(P2,save.p2_gridsWon);
    gameStatus.setTotalResearch(P2,save.p2_totalResearch);
    gameStatus.setTotalMagnet(P2,save.p2_totalMagnet);
    gameStatus.setTotalSch(P2,save.p2_totalSch);
    gameStatus.setTotalAssassin(P2,save.p2_totalAssassin);
    gameStatus.setTotalSniper(P2,save.p2_totalSniper);
    gameStatus.setTotalLeveller(P2,save.p2_totalLeveller);
    gameStatus.setMaxMagnet(P2,save.p2_maxMagnet);
    gameStatus.setMaxAssassin(P2,save.p2_maxAssassin);
    gameStatus.setMaxLeveller(P2,save.p2_maxLeveller);
    gameStatus.setMaxSch(P2,save.p2_maxSch);
    gameStatus.setMaxSniper(P2,save.p2_maxSniper);
    gameStatus.setResearchLevel(P2,save.p2_researchLevel);
    gameStatus.setTotalJoker(P2,save.p2_totalJoker);
    gameStatus.setTotalTrophy1(P2,save.p2_totalTrophy1);
    gameStatus.setTotalTrophy2(P2,save.p2_totalTrophy2);
    gameStatus.setTotalRider(P2,1,save.p2_totalRiders[0]);
    gameStatus.setTotalRider(P2,2,save.p2_totalRiders[1]);
    gameStatus.setTotalRider(P2,3,save.p2_totalRiders[2]);
    gameStatus.setTotalRider(P2,4,save.p2_totalRiders[3]);
    gameStatus.setTotalRider(P2,5,save.p2_totalRiders[4]);
    gameStatus.setTotalRider(P2,6,save.p2_totalRiders[5]);

    gameStatus.setGameLevel(save.gameLevel);
    gameStatus.setCurrPlayer(save.currPlayer);
  }

}