/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prim.business.payment;

import com.prim.business.estate.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * периодичность начислений
 *
 * @author Rice Pavel
 */
public enum Periodicity {

  ONETIME(1, "Единоразовые"), EVERYDAY(2, "Ежесуточные"), MONTHLY(3, "Ежемесячные"),
  QUARTERLY(4, "Ежеквартальные"), YEARLY(5, "Ежегодные");
  
  private final Integer id;
  private final String name;

  /**
   * возвращает ИД
   * @return 
   */
  public Integer getId() {
    return id;
  }

  /**
   * возвращает название
   * @return 
   */
  public String getName() {
    return name;
  }

  private Periodicity(int id, String name) {
    this.id = id;
    this.name = name;
  }

  /**
   * возвращает Map, в котором ключами являются ИД, а значениями - названия
   * @return 
   */
  public static Map<String, Object> all() {
    Map<String, Object> map = new LinkedHashMap();
    for (Periodicity type : Periodicity.values()) {
      map.put(type.id.toString(), type.name);
    }
    return map;
  }

  /**
   * возвращает имя по ИД
   * @param typeId
   * @return 
   */
  public static String getNameById(Object typeId) {
    String name = "";
    if (typeId != null) {
      Map<String, Object> all = all();
      String typeIdString = typeId.toString();
      if (all.get(typeIdString) != null) {
        name = all.get(typeIdString).toString();
      }
    }
    return name;
  }
  
  public static Periodicity getById(int id) {
    for (Periodicity p:Periodicity.values()) {
      if (p.getId() == id) {
        return p;
      }
    }
    return null;
  }
  
}
