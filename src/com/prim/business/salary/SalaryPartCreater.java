/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prim.business.salary;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * определяет, какие части содержит заработная плата
 *
 * @author Rice Pavel
 */
public class SalaryPartCreater {

  private SalaryPartCreater() {
  }

  /**
   * возвращает Map, который определяет части заработной платы, и величину налога  по
   * каждой части. Формат возвращаемого Map: ключи - части зарплаты, значения - величина налога.
   * Налоги исчисляются относительно основной часи зарплаты, выдаваемой на
   * руки сотруднику
   *
   * @param month месяц, с 1 до 12
   * @param year год
   * @return
   */
  public static Map<SalaryParts, Double> create(int month, int year) {
    Map<SalaryParts, Double> partMap = new HashMap();
    partMap.put(SalaryParts.NDFL, 0.13);
    partMap.put(SalaryParts.FOMS, 1.13 * 0.051);
    partMap.put(SalaryParts.PFR, 1.13 * 0.22);
    partMap.put(SalaryParts.FSS, 1.13 * 0.029);
    partMap.put(SalaryParts.NS, 1.13 * 0.002);
    return partMap;
  }
}
