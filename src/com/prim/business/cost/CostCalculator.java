/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prim.business.cost;

import com.prim.business.payment.Periodicity;
import com.prim.support.FormatDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * класс вычисляет размер начислений
 *
 * @author Rice Pavel
 */
public class CostCalculator {

  /**
   * объект, который содержит список сумм для начисления, по месяцам и по годам 
   */
  private AmountList amountList;
  
  /**
   * базовая сумма для начисления
   */
  private final double amount;
  
  /**
   * нужно ли делать начисление
   */
  private boolean charged;
  
  /**
   * сумма начисления
   */
  private double chargeAmount;
  
  /**
   * тестовая информация
   */
  private String testInfo = "";

  /**
   * создает объект и сразу вычисляет сумму начисления
   * @param costDateFrom дата начала начисления
   * @param costDateTo дата конца начисления. Может равняться null
   * @param periodDateFrom дата начала периода, за который нужно рассчитать
   * @param periodDateTo дата конца периода, за который нужно рассчитать
   * @param amount сумма начисления
   * @param costType тип начисления
   * @param calculationDate дата, в которую нужно делать начисление
   * @throws Exception 
   */
  public CostCalculator(Date costDateFrom, Date costDateTo, Date periodDateFrom, Date periodDateTo, double amount, Periodicity costType, int calculationDate) throws Exception {

    this.amount = amount;
    
    if (costDateFrom != null  && periodDateFrom != null && periodDateTo != null && costType != null) {

      testInfo += "1";

      if (costDateTo == null) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(periodDateTo);
        cl.add(Calendar.DAY_OF_YEAR, 1);
        costDateTo = cl.getTime();
      }
      
      costDateFrom = FormatDate.getStartOfDate(costDateFrom);
      costDateTo = FormatDate.getStartOfDate(costDateTo);
      periodDateFrom = FormatDate.getStartOfDate(periodDateFrom);
      periodDateTo = FormatDate.getStartOfDate(periodDateTo);

      // найти период пересечения диапазонов
      Date diapasonDateFrom = costDateFrom.after(periodDateFrom) ? costDateFrom : periodDateFrom;

      Date diapasonDateTo = costDateTo.before(periodDateTo) ? costDateTo : periodDateTo;

      // проверить правильность найденного диапазона
      if ((costDateFrom.before(costDateTo) || costDateFrom.equals(costDateTo))
              && (periodDateFrom.before(periodDateTo) || periodDateFrom.equals(periodDateTo))
              && (diapasonDateFrom.before(diapasonDateTo) || diapasonDateFrom.equals(diapasonDateTo))) {

        testInfo += "2";

        if (costType.equals(Periodicity.ONETIME)) {
          chargeOneTime(costDateFrom, periodDateFrom, periodDateTo);
        } else if (costType.equals(Periodicity.EVERYDAY)) {
          chargeEveryDay(diapasonDateFrom, diapasonDateTo, costDateTo);
        } else if (costType.equals(Periodicity.MONTHLY)) {
          chargeMonthly(diapasonDateFrom, diapasonDateTo, calculationDate);
        } else if (costType.equals(Periodicity.QUARTERLY)) {
          chargeQuarterly(diapasonDateFrom, diapasonDateTo, calculationDate);
        } else if (costType.equals(Periodicity.YEARLY)) {
          chargeYearly(diapasonDateFrom, diapasonDateTo, calculationDate);
        }
      }
    }
  }
  
  public void setAmountList(AmountList amountList) {
    this.amountList = amountList;
  }
  
  /**
   * получить сумму
   * @param year год
   * @param month месяц от 1 до 12
   * @return 
   */
  private double getAmount(int year, int month) {
    if (amountList == null) {
      return amount;
    } else {
      return amountList.getAmount(year, month);
    }
  }

  private void chargeOneTime(Date costDateFrom, Date reportDateFrom, Date reportDateTo) {
    testInfo += "3";
    // если дата costDateFrom входит в диапазон
    if ((costDateFrom.after(reportDateFrom) || costDateFrom.equals(reportDateFrom))
            && (costDateFrom.before(reportDateTo) || costDateFrom.equals(reportDateTo))) {
      charged = true;
      Calendar cl = Calendar.getInstance();
      cl.setTime(costDateFrom);
      chargeAmount = getAmount(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH) + 1);
    }
  }
  


  private void chargeEveryDay(Date diapasonDateFrom, Date diapasonDateTo, Date costDateTo) {
    testInfo += "4";
    //  период не кончился
    boolean periodEnded = false;
    // первый день
    Calendar clDiapasonFrom = Calendar.getInstance();
    clDiapasonFrom.setTime(diapasonDateFrom);
    // конец диапазона
    Calendar clDiapasonTo = Calendar.getInstance();
    clDiapasonTo.setTime(diapasonDateTo);
    // день конца периода платежа
    Calendar clCostTo = Calendar.getInstance();
    clCostTo.setTime(costDateTo);
    // прибавить
    testInfo += " " + FormatDate.getDateInMysql(clDiapasonFrom.getTime()) + " " + FormatDate.getDateInMysql(clDiapasonTo.getTime()) + " " + FormatDate.getDateInMysql(clCostTo.getTime()) + "/";
    if (clDiapasonFrom.before(clDiapasonTo) || clDiapasonFrom.equals(clDiapasonTo)) {
      testInfo += "4.1";
      charged = true;
      chargeAmount += getAmount(clDiapasonFrom.get(Calendar.YEAR), clDiapasonFrom.get(Calendar.MONTH) + 1);
    }
    // пока период не кончился
    while (!periodEnded) {
      testInfo += "4.2";
      // прибавить день
      clDiapasonFrom.add(Calendar.DAY_OF_YEAR, 1);
      // если дата раньше чем конец периода и если это не день конца периода
      if ((clDiapasonFrom.before(clDiapasonTo) || clDiapasonFrom.equals(clDiapasonTo)) && clDiapasonFrom.before(clCostTo)) {
        testInfo += "4.3";
        charged = true;
        chargeAmount += getAmount(clDiapasonFrom.get(Calendar.YEAR), clDiapasonFrom.get(Calendar.MONTH) + 1);;
      } else {
        periodEnded = true;
      }
    }
  }

  private void chargeMonthly(Date diapasonDateFrom, Date diapasonDateTo, int calculationDate) {
    testInfo += "5";
    // период не кончился
    boolean periodEnded = false;
    // период первого месяца
    // начало диапазона
    Calendar clDiapasonFrom = Calendar.getInstance();
    clDiapasonFrom.setTime(diapasonDateFrom);
    // конец этого месяца
    Calendar clEndMonth = Calendar.getInstance();
    clEndMonth.setTime(diapasonDateFrom);
    clEndMonth.set(Calendar.DAY_OF_MONTH, clEndMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
    // конец диапазона
    Calendar clDiapasonTo = Calendar.getInstance();
    clDiapasonTo.setTime(diapasonDateTo);
    // конец периода в этом месяце
    Calendar clEnd = clDiapasonTo.before(clEndMonth) ? clDiapasonTo : clEndMonth;
    // получить число
    int day = calculationDate <= 31 ? calculationDate : clEndMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
    // если число входит в этот период - начислить
    if (day >= clDiapasonFrom.get(Calendar.DAY_OF_MONTH) && day <= clEnd.get(Calendar.DAY_OF_MONTH)) {
      charged = true;
      chargeAmount += getAmount(clDiapasonFrom.get(Calendar.YEAR), clDiapasonFrom.get(Calendar.MONTH) + 1);;
    }

    // пока период не кончился
    while (!periodEnded) {
      // период очередного месяца
      // начало - начало месяца
      clDiapasonFrom.add(Calendar.MONTH, 1);
      clDiapasonFrom.set(Calendar.DAY_OF_MONTH, 1);
      // проверка
      if (clDiapasonFrom.before(clDiapasonTo) || clDiapasonFrom.equals(clDiapasonTo)) {
        // конец месяца
        clEndMonth.add(Calendar.MONTH, 1);
        clEndMonth.set(Calendar.DAY_OF_MONTH, clEndMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
        // конец периода в этом месяце
        clEnd = clDiapasonTo.before(clEndMonth) ? clDiapasonTo : clEndMonth;
        // получить число
        day = calculationDate <= 31 ? calculationDate : clEndMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
        // если число входит в этот период - начислить
        if (day >= clDiapasonFrom.get(Calendar.DAY_OF_MONTH) && day <= clEnd.get(Calendar.DAY_OF_MONTH)) {
          charged = true;
          chargeAmount += getAmount(clDiapasonFrom.get(Calendar.YEAR), clDiapasonFrom.get(Calendar.MONTH) + 1);;
        }
      } else {
        periodEnded = true;
      }
    }

  }

  private void chargeQuarterly(Date diapasonDateFrom, Date diapasonDateTo, int calculationDate) {
    testInfo += "6";
    // период первого месяца
    // начало - начало диапазона
    Calendar clDiapasonFrom = Calendar.getInstance();
    clDiapasonFrom.setTime(diapasonDateFrom);
    // конец диапазона
    Calendar clDiapasonTo = Calendar.getInstance();
    clDiapasonTo.setTime(diapasonDateTo);
    // получить число
    int day = 1;
    List<Integer> months = new ArrayList();
    if (calculationDate == 1) {
      months.add(0);
      months.add(3);
      months.add(6);
      months.add(9);
    } else if (calculationDate == 2) {
      months.add(1);
      months.add(4);
      months.add(7);
      months.add(10);
    } else if (calculationDate == 3) {
      months.add(2);
      months.add(5);
      months.add(8);
      months.add(11);
    }
    // если число входи в этот период и если месяц один из определенных месяцев
    if (day >= clDiapasonFrom.get(Calendar.DAY_OF_MONTH) && months.contains(clDiapasonFrom.get(Calendar.MONTH))) {
      charged = true;
      chargeAmount += getAmount(clDiapasonFrom.get(Calendar.YEAR), clDiapasonFrom.get(Calendar.MONTH) + 1);;
    }

    // переменная - период не кончился
    boolean periodEnded = false;
    // пока период не кончился
    while (!periodEnded) {
      // период очередного месяца
      // начало - начало месяца
      clDiapasonFrom.add(Calendar.MONTH, 1);
      clDiapasonFrom.set(Calendar.DAY_OF_MONTH, 1);
      if (clDiapasonFrom.before(clDiapasonTo) || clDiapasonFrom.equals(clDiapasonTo)) {
        if (months.contains(clDiapasonFrom.get(Calendar.MONTH))) {
          charged = true;
          chargeAmount += getAmount(clDiapasonFrom.get(Calendar.YEAR), clDiapasonFrom.get(Calendar.MONTH) + 1);;
        }
      } else {
        periodEnded = true;
      }
    }
  }

  private void chargeYearly(Date diapasonDateFrom, Date diapasonDateTo, int calculationDate) {
    testInfo += "7";
    // период первого месяца
    // начало - начало диапазона
    Calendar clDiapasonFrom = Calendar.getInstance();
    clDiapasonFrom.setTime(diapasonDateFrom);
    // конец диапазона 
    Calendar clDiapasonTo = Calendar.getInstance();
    clDiapasonTo.setTime(diapasonDateTo);
    // получить число
    int day = 1;
    int month = calculationDate - 1;
    // если число входи в этот период и если месяц один из определенных месяцев
    if (day >= clDiapasonFrom.get(Calendar.DAY_OF_MONTH) && clDiapasonFrom.get(Calendar.MONTH) == month) {
      charged = true;
      chargeAmount += getAmount(clDiapasonFrom.get(Calendar.YEAR), clDiapasonFrom.get(Calendar.MONTH) + 1);;
    }
    // переменная - период не кончился
    boolean periodEnded = false;
    // пока период не кончился
    while (!periodEnded) {
      // период очередного месяца
      // начало - начало месяца
      clDiapasonFrom.add(Calendar.MONTH, 1);
      clDiapasonFrom.set(Calendar.DAY_OF_MONTH, 1);
      if (clDiapasonFrom.before(clDiapasonTo) || clDiapasonFrom.equals(clDiapasonTo)) {
        if (clDiapasonFrom.get(Calendar.MONTH) == month) {
          charged = true;
          chargeAmount += getAmount(clDiapasonFrom.get(Calendar.YEAR), clDiapasonFrom.get(Calendar.MONTH) + 1);;
        }
      } else {
        periodEnded = true;
      }
    }
  }

  public String getTestInfo() {
    return testInfo;
  }

  public boolean charged() {
    return charged;
  }

  public double getChargeAmount() {
    return chargeAmount;
  }
}
