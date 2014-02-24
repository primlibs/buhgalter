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
public enum AgreementTypes {
  
  HYPOTHEC(1, "Ипотека"), PLEDGE(2, "Залог");
  
  private Integer id;
  
  private String name;
  
  public Integer getId() {
    return id;
  }
  
  public String getName() {
    return name;
  }
  
  AgreementTypes(int id, String name) {
    this.id = id;
    this.name = name;
  }
  
  public static Map<String, Object>  immovables() {
    Map<String, Object> map = new LinkedHashMap();
    map.put(HYPOTHEC.id.toString(), HYPOTHEC.name);
    map.put(PLEDGE.id.toString(), PLEDGE.name);
    return map;
  }
  

  
}
