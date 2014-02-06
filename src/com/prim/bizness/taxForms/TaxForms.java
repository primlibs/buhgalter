/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prim.bizness.taxForms;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author User
 */
public enum TaxForms {

  OSNO("1", "ОСНО"), USN("2", "УСН"), ENVD("3", "ЕНВД");
  
  private String number;
  private String name;

  TaxForms(String number, String name) {
    this.number = number;
    this.name = name;
  }
  
  public String getNumber() {
    return number;
  }
  
  public String getName() {
    return name;
  }
  
  public static Map<String, Object> getComboMap() {
    Map<String, Object> map = new LinkedHashMap();
    for (TaxForms form: TaxForms.values()) {
      map.put(form.getNumber(), form.getName());
    }
    return map;
  }
  
  public static String getName(String nubmer) {
    for (TaxForms form: TaxForms.values()) {
      if (form.getNumber().equals(nubmer)) {
        return form.getName();
      }
    }
    return nubmer;
  }
  
}
