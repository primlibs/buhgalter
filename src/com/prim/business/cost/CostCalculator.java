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

    if (costDateFrom != null && costDateTo != null && reportDateFrom != null && reportDateTo != null) {

      costDateFrom = FormatDate.getStartOfDate(costDateFrom);
      costDateTo = FormatDate.getStartOfDate(costDateTo);
      reportDateFrom = FormatDate.getStartOfDate(reportDateFrom);
      reportDateTo = FormatDate.getStartOfDate(reportDateTo);

      Integer calculationDate = Integer.parseInt(costDateStr);

      // найти период пересечения диапазонов
      Date diapasonDateFrom = costDateFrom.after(reportDateFrom) ? costDateFrom : reportDateFrom;
      Date diapasonDateTo = costDateTo.before(reportDateTo) ? costDateTo : reportDateTo;

      double amount = Double.parseDouble(amountStr);
      // проверить правильность найденного диапазона
      if (
              (costDateFrom.before(costDateTo) || costDateFrom.equals(costDateTo))
              && (reportDateFrom.before(reportDateTo) || reportDateFrom.equals(reportDateTo))
              && (diapasonDateFrom.before(diapasonDateTo) || diapasonDateFrom.equals(diapasonDateTo))) {
        // если тип - единоразовый
        if (costType.equals(Periodicity.ONETIME.getId().toString())) {
          // если дата costDateFrom входит в диапазон
          if ((costDateFrom.after(reportDateFrom) || costDateFrom.equals(reportDateFrom))
                  && (costDateFrom.before(reportDateTo) || costDateFrom.equals(reportDateTo))) {
            charged = true;
            chargeAmount = amount;
          }
        } else if (costType.equals(Periodicity.EVERYDAY.getId().toString())) {
          //  период не кончился
          boolean periodEnded = false;
          // первый день
          Calendar clDiapasonFrom = Calendar.getInstance();
          clDiapasonFrom.setTime(diapasonDateFrom);
          // конец диапазона
          Calendar clDiapasonTo = Calendar.getInstance();
          clDiapasonTo.setTime(diapasonDateTo);
          // день конца периода платежа
          // год конца периода платежа
          Calendar clCostTo = Calendar.getInstance();
          clCostTo.setTime(costDateTo);
          // прибавить
          if (clDiapasonFrom.before(clDiapasonTo) || clDiapasonFrom.equals(clDiapasonTo)) {
            charged = true;
            chargeAmount += amount;
          }
          // пока период не кончился
          while (!periodEnded) {
            // прибавить день
            clDiapasonFrom.add(Calendar.DAY_OF_YEAR, 1);
            // если дата раньше чем конец периода и если это не день конца периода
            if ((clDiapasonFrom.before(clDiapasonTo) || clDiapasonFrom.equals(clDiapasonTo)) && clDiapasonFrom.before(clCostTo)) {
              charged = true;
              chargeAmount += amount;
            } else {
              periodEnded = true;
            }
          }
        } else if (costType.equals(Periodicity.MONTHLY.getId().toString())) {
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
            chargeAmount += amount;
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
                chargeAmount += amount;
              }
            } else {
              periodEnded = true;
            }
          }
        } else if (costType.equals(Periodicity.QUARTERLY.getId().toString())) {
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
            chargeAmount += amount;
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
                chargeAmount += amount;
              }
            } else {
              periodEnded = true;
            }
          }
        } else if (costType.equals(Periodicity.YEARLY.getId().toString())) {
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
            chargeAmount += amount;
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
                chargeAmount += amount;
              }
            } else {
              periodEnded = true;
            }
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
