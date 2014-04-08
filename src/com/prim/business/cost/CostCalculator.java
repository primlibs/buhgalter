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

  private boolean charged;
  private double chargeAmount;

  public CostCalculator(String costDateFromStr, String costDateToStr, String reportDateFromStr, String reportDateToStr, String amountStr, String costType, String costDateStr) throws Exception {
    // проверить правильность диапазонов
    Date costDateFrom = FormatDate.getDateFromString(costDateFromStr);
    Date costDateTo = FormatDate.getDateFromString(costDateToStr);
    Date reportDateFrom = FormatDate.getDateFromString(reportDateFromStr);
    Date reportDateTo = FormatDate.getDateFromString(reportDateToStr);
    Integer costDate = Integer.parseInt(costDateStr);

    // найти период пересечения диапазонов
    Date diapasonDateFrom = costDateFrom.after(reportDateFrom) ? costDateFrom : reportDateFrom;
    Date diapasonDateTo = costDateTo.before(reportDateTo) ? costDateTo : reportDateTo;

    double amount = Double.parseDouble(amountStr);
    // проверить правильность найденного диапазона
    if (costDateFrom.before(costDateTo) && reportDateFrom.before(reportDateTo) && diapasonDateFrom.before(diapasonDateTo)) {
      if (costType.equals(Periodicity.ONETIME.getId().toString())) {
        // если тип - единоразовый
        // если дата costDateFrom входит в диапазон
        if ((costDateFrom.after(reportDateFrom) || costDateFrom.equals(reportDateFrom))
                && (costDateFrom.before(reportDateTo) || costDateFrom.equals(reportDateTo))) {
          charged = true;
          chargeAmount = amount;
        }
        // начислить
      } else if (costType.equals(Periodicity.EVERYDAY.getId().toString())) {

        // переменная - период не кончился
        boolean periodEnded = false;
        // первый день
        Calendar clStart = Calendar.getInstance();
        clStart.setTime(diapasonDateFrom);
        // конец диапазона
        Calendar clEndDiapason = Calendar.getInstance();
        clEndDiapason.setTime(diapasonDateTo);
        // день конца отчета платежа
        // год конца отчета платежа
        Calendar clCostTo = Calendar.getInstance();
        clCostTo.setTime(costDateTo);
        int dayCostTo = clCostTo.get(Calendar.DAY_OF_YEAR);
        int yearCostTo = clCostTo.get(Calendar.YEAR);
        // прибавить
        if (clStart.before(clEndDiapason)) {
          charged = true;
          chargeAmount += amount;
        }
        // пока период не кончился
        while (!periodEnded) {
          // прибавить день
          clStart.add(Calendar.DAY_OF_YEAR, 1);
          // если дата раньше чем конец периода и если это не день конца периода
          if (clStart.before(clEndDiapason) && !(clStart.get(Calendar.DAY_OF_YEAR) == dayCostTo && clStart.get(Calendar.YEAR) == yearCostTo)) {
            charged = true;
            chargeAmount += amount;
          } else {
            periodEnded = true;
          }
        }
      } else if (costType.equals(Periodicity.MONTHLY.getId().toString())) {
        // переменная - период не кончился
        boolean periodEnded = false;
        // период первого месяца
        // начало - начало диапазона
        Calendar clStart = Calendar.getInstance();
        clStart.setTime(diapasonDateFrom);
        // конец - конец диапазона либо конец месяца
        Calendar clEndMonth = Calendar.getInstance();
        clEndMonth.setTime(diapasonDateFrom);
        clEndMonth.set(Calendar.DAY_OF_MONTH, clEndMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
        Calendar clEndDiapason = Calendar.getInstance();
        clEndDiapason.setTime(diapasonDateTo);
        Calendar clEnd = clEndDiapason.before(clEndMonth) ? clEndDiapason : clEndMonth;
        // получить число
        int day = costDate <= 31 ? costDate : clEndMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
        // если число входи в этот период - начислить
        if (day >= clStart.get(Calendar.DAY_OF_MONTH) && day <= clEnd.get(Calendar.DAY_OF_MONTH)) {
          charged = true;
          chargeAmount += amount;
        }

        // пока период не кончился
        while (!periodEnded) {
          // период очередного месяца
          // начало - начало месяца
          clStart.add(Calendar.MONTH, 1);
          clStart.set(Calendar.DAY_OF_MONTH, 1);
          // проверка
          if (clStart.before(clEndDiapason)) {
            // конец - конец периода либо конец месяца
            clEndMonth.add(Calendar.MONTH, 1);
            clEndMonth.set(Calendar.DAY_OF_MONTH, clEndMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
            clEnd = clEndDiapason.before(clEndMonth) ? clEndDiapason : clEndMonth;
            // получить число
            day = costDate <= 31 ? costDate : clEndMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
            // если число входит в этот период - начислить
            if (day >= clStart.get(Calendar.DAY_OF_MONTH) && day <= clEnd.get(Calendar.DAY_OF_MONTH)) {
              charged = true;
              chargeAmount += amount;
            }
          } else {
            periodEnded = true;
          }
        }
      } else if (costType.equals(Periodicity.QUARTERLY.getId().toString())) {
        // период первого месяца
        // начало - начало диапазона
        Calendar clStart = Calendar.getInstance();
        clStart.setTime(diapasonDateFrom);
        // конец диапазона
        Calendar clEndDiapason = Calendar.getInstance();
        clEndDiapason.setTime(diapasonDateTo);
        // получить число
        int day = 1;
        List<Integer> months = new ArrayList();
        if (costDate == 1) {
          months.add(0);
          months.add(3);
          months.add(6);
          months.add(9);
        } else if (costDate == 2) {
          months.add(1);
          months.add(4);
          months.add(7);
          months.add(10);
        } else if (costDate == 3) {
          months.add(2);
          months.add(5);
          months.add(8);
          months.add(11);
        }
        // если число входи в этот период и если месяц один из определенных месяцев
        if (day >= clStart.get(Calendar.DAY_OF_MONTH) && months.contains(clStart.get(Calendar.MONTH))) {
          charged = true;
          chargeAmount += amount;
        }

        // переменная - период не кончился
        boolean periodEnded = false;
        // пока период не кончился
        while (!periodEnded) {
          // период очередного месяца
          // начало - начало месяца
          clStart.add(Calendar.MONTH, 1);
          clStart.set(Calendar.DAY_OF_MONTH, 1);
          if (clStart.before(clEndDiapason)) {
            if (months.contains(clStart.get(Calendar.MONTH))) {
              charged = true;
              chargeAmount += amount;
            }
          } else {
            periodEnded = true;
          }
        }
      } else if (costType.equals(Periodicity.YEARLY.getId().toString())) {
        // период первого месяца
        // начало - начало диапазона
        Calendar clStart = Calendar.getInstance();
        clStart.setTime(diapasonDateFrom);
        // конец диапазона 
        Calendar clEndDiapason = Calendar.getInstance();
        clEndDiapason.setTime(diapasonDateTo);
        // получить число
        int day = 1;
        int month = costDate - 1;
        // если число входи в этот период и если месяц один из определенных месяцев
        if (day >= clStart.get(Calendar.DAY_OF_MONTH) && clStart.get(Calendar.MONTH) == month) {
          charged = true;
          chargeAmount += amount;
        }
        // переменная - период не кончился
        boolean periodEnded = false;
        // пока период не кончился
        while (!periodEnded) {
          // период очередного месяца
          // начало - начало месяца
          clStart.add(Calendar.MONTH, 1);
          clStart.set(Calendar.DAY_OF_MONTH, 1);
          if (clStart.before(clEndDiapason)) {
            if (clStart.get(Calendar.MONTH) == month) {
              charged = true;
              chargeAmount += amount;
            }
          } else {
            periodEnded = true;
          }
        }
      }
    }
  }

  public boolean charged() {
    return false;
  }

  public double getChargeAmount() {
    return 0;
  }
}
