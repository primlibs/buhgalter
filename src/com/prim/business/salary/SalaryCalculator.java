/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prim.business.salary;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * класс вычисляет составляющие заработной платы
 * 
 * @author Rice Pavel
 */
public class SalaryCalculator {
  
  /**
   * части зарплаты и налоги по ним
   */
  private Map<SalaryParts, Double> nalogs = new HashMap();
 
  private Map<SalaryParts, Double> values = new HashMap();
  
  // основная часть зарплаты
  private double main = 0;
  
  /**
   * 
   * @param month - месяц, от 1 до 12
   * @param year - год
   */
  public SalaryCalculator(int month, int year) {
    nalogs = SalaryPartCreater.create(month, year);
  }
  
  /**
   * установить основную часть зарплаты
   * @param main 
   */
  public void setMain(double main) {
    // установить основную часть
    // подсчитать каждый налог
    this.main = main;
    calculateNalogsByMain();
  }
  
  /**
   * установить составляющие или сумму составляющих
   * @param amount
   * @param parts 
   */
  public void set(double amount, SalaryParts ... parts ) {
    double summNalog = 0;
    boolean exists = false;
    for (SalaryParts part: parts) {
      if (nalogs.containsKey(part)) {
        exists = true;
        double nalog = nalogs.get(part);
        summNalog += nalog;
      }
    }
    if (summNalog > 0) {
      main = amount/summNalog;
      calculateNalogsByMain();
    } else {
      if (exists) {
        main = 0;
        calculateNalogsByMain();
      }
    }
    
  }
  
  /**
   * установить основную часть в сумме с составляющими
   * @param amount
   * @param parts 
   */
  public void setMainWithParts(double amount, SalaryParts ... parts ) {
    double summNalog = 0;
    for (SalaryParts part: parts) {
      if (nalogs.containsKey(part)) {
        double nalog = nalogs.get(part);
        summNalog += nalog;
      }
    }
    main = amount/(summNalog + 1);
    calculateNalogsByMain();
  }
  
  /**
   * установить полностью всю зарплату вместе со всеми налогами
   * @param amount 
   */
  public void setSumm(double amount) {
    double summNalog = 0;
    for (SalaryParts part: nalogs.keySet()) {
        double nalog = nalogs.get(part);
        summNalog += nalog;
    }
    main = amount/(summNalog + 1);
    calculateNalogsByMain();
  }
  
  public double getMain() {
    return main;
  }
  
  public double get(SalaryParts part) {
    double amount = 0;
    if (values.containsKey(part)) {
      amount = values.get(part);
    }
    return amount;
  }
  
  private void calculateNalogsByMain() {
    values.clear();
    for (SalaryParts part: SalaryParts.values()) {
      if (nalogs.containsKey(part)) {
        double nalog = nalogs.get(part);
        double value = main*nalog;
        values.put(part, value);
      }
    }
  }
  
}
