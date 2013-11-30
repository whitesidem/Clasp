package clasp;

/**
 * Title:        Clasp Version 2
 * Description:  A Game of Two Enemies
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author M.Whiteside
 * @version 1.0
 */

public class ClaspPlayer {

  public ClaspPlayer() {
    cashTotal=0L;
//    cashTotal=20000L;
    totalLines=0;
    gridsWon=0;
    researchLevel=1;
    //The Totals Weapon variables are set to -1 untill they are available for usage
    totalResearch=0;
    totalMagnet=-1;
    totalSch=-1;
    totalAssassin=-1;
    totalSniper=-1;
    totalLeveller=-1;
    totalJoker=0;
    totalTrophy1=0;
    totalTrophy2=0;
    totalRiders=new int[6];
    totalRiders[0]=0;
    totalRiders[1]=0;
    totalRiders[2]=0;
    totalRiders[3]=0;
    totalRiders[4]=0;
    totalRiders[5]=0;

    maxMagnet=WeaponBuyDlg.MAX_MAGNET;
    maxAssassin=WeaponBuyDlg.MAX_ASSASSIN;
    maxSch=WeaponBuyDlg.MAX_SCH;
    maxLeveller=WeaponBuyDlg.MAX_LEVELLER;
    maxSniper=WeaponBuyDlg.MAX_SNIPER;
  }

  private long cashTotal;
  private int totalLines;
  private int gridsWon;
  private int totalResearch;
  private int totalMagnet;
  private int totalSch;
  private int totalAssassin;
  private int totalSniper;
  private int totalLeveller;
  private int maxMagnet;
  private int maxAssassin;
  private int maxLeveller;
  private int maxSch;
  private int maxSniper;
  private int researchLevel;
  private int totalJoker;
  private int totalTrophy1;
  private int totalTrophy2;
  private int totalRiders[];

  public long getCashTotal() {
    return cashTotal;
  }
  public void setCashTotal(long cashTotal) {
    this.cashTotal = cashTotal;
  }
  public void setTotalLines(int totalLines) {
    this.totalLines = totalLines;
  }
  public int getTotalLines() {
    return totalLines;
  }
  public void setGridsWon(int gridsWon) {
    this.gridsWon = gridsWon;
  }
  public int getGridsWon() {
    return gridsWon;
  }
  public void setTotalMagnet(int totalMagnet) {
    this.totalMagnet = totalMagnet;
  }
  public int getTotalMagnet() {
    return totalMagnet;
  }
  public void setTotalSch(int totalSch) {
    this.totalSch = totalSch;
  }
  public int getTotalSch() {
    return totalSch;
  }
  public void setTotalAssassin(int totalAssassin) {
    this.totalAssassin = totalAssassin;
  }
  public int getTotalAssassin() {
    return totalAssassin;
  }
  public void setTotalSniper(int totalSniper) {
    this.totalSniper = totalSniper;
  }
  public int getTotalSniper() {
    return totalSniper;
  }
  public void setTotalLeveller(int totalLeveller) {
    this.totalLeveller = totalLeveller;
  }
  public int getTotalLeveller() {
    return totalLeveller;
  }
  public void setMaxMagnet(int maxMagnet) {
    this.maxMagnet = maxMagnet;
  }
  public int getMaxMagnet() {
    return maxMagnet;
  }
  public void setMaxAssassin(int maxAssassin) {
    this.maxAssassin = maxAssassin;
  }
  public int getMaxAssassin() {
    return maxAssassin;
  }
  public void setMaxLeveller(int maxLeveller) {
    this.maxLeveller = maxLeveller;
  }
  public int getMaxLeveller() {
    return maxLeveller;
  }
  public void setMaxSch(int maxSch) {
    this.maxSch = maxSch;
  }
  public int getMaxSch() {
    return maxSch;
  }
  public void setMaxSniper(int maxSniper) {
    this.maxSniper = maxSniper;
  }
  public int getMaxSniper() {
    return maxSniper;
  }
  public void setTotalResearch(int totalResearch) {
    this.totalResearch = totalResearch;
  }
  public int getTotalResearch() {
    return totalResearch;
  }
  public void setResearchLevel(int researchLevel) {
    this.researchLevel = researchLevel;
  }
  public int getResearchLevel() {
    return researchLevel;
  }
  public void setTotalJoker(int totalJoker) {
    this.totalJoker = totalJoker;
  }
  public int getTotalJoker() {
    return totalJoker;
  }
  public void setTotalTrophy1(int totalTrophy1) {
    this.totalTrophy1 = totalTrophy1;
  }
  public int getTotalTrophy1() {
    return totalTrophy1;
  }
  public void setTotalTrophy2(int totalTrophy2) {
    this.totalTrophy2 = totalTrophy2;
  }
  public int getTotalTrophy2() {
    return totalTrophy2;
  }
  public int getTotalRider(int num){
    return totalRiders[num];
  }
  public void setTotalRider(int num, int val){
    totalRiders[num]=val;
  }

}