/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prim.business.agreement;

import com.prim.business.estate.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * типы договоров
 *
 * @author Rice Pavel
 */
public enum EnsuringTypes {

  LEASING(1, "Лизинг"), PLEDGE(2, "Залог");
  private Integer id;
  private String name;

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  EnsuringTypes(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public static Map<String, Object> all() {
    Map<String, Object> map = new LinkedHashMap();
    for (EnsuringTypes type : EnsuringTypes.values()) {
      map.put(type.id.toString(), type.name);
    }
    return map;
  }

  public static Map<String, Object> immovables() {
    Map<String, Object> map = new LinkedHashMap();
    map.put(LEASING.id.toString(), LEASING.name);
    map.put(PLEDGE.id.toString(), PLEDGE.name);
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
