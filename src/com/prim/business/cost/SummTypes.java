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
 * виды сумм, по которым могут рассчитываться затраты в процентах
 * 
 * @author Rice Pavel
 */
public enum SummTypes {
  
  BALANCES(1, "По остаткам"), DOCUMENTS(2, "По документам");
  private Integer id;
  private String name;

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  SummTypes(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public static Map<String, Object> all() {
    Map<String, Object> map = new LinkedHashMap();
    for (SummTypes type : SummTypes.values()) {
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
