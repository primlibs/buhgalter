/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prim.business.estate;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * типы имущества
 * 
 * @author Rice Pavel
 */
public enum EstateTypes {
  
  STEAD(1, "Земля"), BUILDING(2, "Здание"), CONSTRUCTION(8, "Сооружения"), LIFTING_ENGINE(3, "Грузоподъемная техника"), 
  MOTORIZED_VEHICLES(4, "Самоходные машины"), CAR(5, "Автотранспорт"), SHIP(6, "Суда"), OTHER(7, "Другое");
  
  private Integer id;
  
  private String name;
  
  public Integer getId() {
    return id;
  }
  
  public String getName() {
    return name;
  }
  
  EstateTypes(int id, String name) {
    this.id = id;
    this.name = name;
  }
  
  public static Map<String, Object> all() {
    Map<String, Object> map = new LinkedHashMap();
    for (EstateTypes type: EstateTypes.values()) {
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
    map.put(STEAD.id.toString(), STEAD.name);
    map.put(BUILDING.id.toString(), BUILDING.name);
    map.put(CONSTRUCTION.id.toString(), CONSTRUCTION.name);
    return map;
  }
  
  /**
   * движимые типы имущества
   * @return 
   */
  public static Map<String, Object>  movables() {
    Map<String, Object> map = new LinkedHashMap();
    map.put(LIFTING_ENGINE.id.toString(), LIFTING_ENGINE.name);
    map.put(MOTORIZED_VEHICLES.id.toString(), MOTORIZED_VEHICLES.name);
    map.put(CAR.id.toString(), CAR.name);
    map.put(SHIP.id.toString(), SHIP.name);
    map.put(OTHER.id.toString(), OTHER.name);
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
