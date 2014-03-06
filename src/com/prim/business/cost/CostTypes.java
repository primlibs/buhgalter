/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prim.business.cost;

import com.prim.business.estate.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * типы затрат
 * 
 * @author Rice Pavel
 */
public enum CostTypes {
  
  PERMANENT(1, "Постоянные"), MONTHLY(2, "Ежемесячные");
  
  private Integer id;
  
  private String name;
  
  public Integer getId() {
    return id;
  }
  
  public String getName() {
    return name;
  }
  
  CostTypes(int id, String name) {
    this.id = id;
    this.name = name;
  }
  
  public static Map<String, Object> all() {
    Map<String, Object> map = new LinkedHashMap();
    for (CostTypes type: CostTypes.values()) {
      map.put(type.id.toString(), type.name);
    }
    return map;
  }
  
  /**
   * недвижимость
   * @return 
   */
  public static Map<String, Object>  immovables() {
    Map<String, Object> map = new LinkedHashMap();
    map.put(PERMANENT.id.toString(), PERMANENT.name);
    map.put(MONTHLY.id.toString(), MONTHLY.name);
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
