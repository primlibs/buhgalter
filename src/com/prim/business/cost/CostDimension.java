/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prim.business.cost;

import com.prim.business.agreement.EnsuringTypes;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * размерности, в которых указывается затрата
 * 
 * @author Rice Pavel
 */
public enum CostDimension {
  
  AMOUNT(1, "Сумма"), PERCENT(2, "Процент");
  private Integer id;
  private String name;

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  CostDimension(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public static Map<String, Object> all() {
    Map<String, Object> map = new LinkedHashMap();
    for (CostDimension type : CostDimension.values()) {
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
