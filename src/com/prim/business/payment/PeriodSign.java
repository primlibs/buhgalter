/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prim.business.payment;

import com.prim.business.estate.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * признаки периода
 *
 * @author Rice Pavel
 */
public enum PeriodSign {

  THIS(1, "В этом периоде"), NEXT(2, "В следующем периоде");
  
  private final Integer id;
  private final String name;

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  private PeriodSign(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public static Map<String, Object> all() {
    Map<String, Object> map = new LinkedHashMap();
    for (PeriodSign type : PeriodSign.values()) {
      map.put(type.id.toString(), type.name);
    }
    return map;
  }

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
}
