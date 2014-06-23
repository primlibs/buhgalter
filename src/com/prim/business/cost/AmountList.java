/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prim.business.cost;

/**
 *
 * список сумм по годам и месяцам
 * 
 * @author Rice Pavel
 */
public interface AmountList {
  
  /**
   * получить сумму
   * @param year год
   * @param month месяц, от 1 до 12
   * @return 
   */
  public double getAmount(int year, int month);
  
}
