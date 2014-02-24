/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prim.business.estate;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * типы 
 * 
 * @author Rice Pavel
 */
public enum EstateTypes {
  
  STEAD(1, "Земля"), BUILDING(2, "Здание");
  
  private int id;
  
  private String name;
  
  public int getId() {
    return id;
  }
  
  public String getName() {
    return name;
  }
  
  EstateTypes(int id, String name) {
    this.id = id;
    this.name = name;
  }
  
  public static Map<Integer, String>  immovables() {
    Map<Integer, String> map = new LinkedHashMap();
    map.put(STEAD.id, STEAD.name);
    map.put(BUILDING.id, BUILDING.name);
    return map;
  }
  
  public static Map<Integer, String>  movables() {
    Map<Integer, String> map = new LinkedHashMap();
    return map;
  }
  
}
