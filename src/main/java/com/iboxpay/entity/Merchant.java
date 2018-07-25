package com.iboxpay.entity;

enum Season {
  SPRING("春天");

  private final String name;

  private Season(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

}

public class Merchant {

  private int mid;
  private String mno;
  private String mname;

  public int getMid() {
    return mid;
  }

  public void setMid(int mid) {
    this.mid = mid;
  }

  public String getMno() {
    return mno;
  }

  public void setMno(String mno) {
    this.mno = mno;
  }

  public String getMname() {
    return mname;
  }

  public void setMname(String mname) {
    this.mname = mname;
  }

  @Override
  public String toString() {
    return "Merchant [mid=" + mid + ", mno=" + mno + ", mname=" + mname + "]";
  }

  public static void main(String[] args) throws Exception {
    
  }
}
