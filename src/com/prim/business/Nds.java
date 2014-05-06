/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prim.business;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Rice Pavel
 */
public class Nds {
  
  public static Map<String, Object> getNdsMap() {
    Map<String, Object> map = new LinkedHashMap();
    map.put("", "без НДС");
    map.put("0.10", "10%");
    map.put("0.18", "18%");
    return map;
  }
  
  public static Double getPaymentWithoutNds(double payment) {
    return (payment / 118.0) * 100.0;
  }
  
  public static Double getPaymentWithoutNds(String payment) throws NumberFormatException {
    double paymentDouble = Double.parseDouble(payment);
    return (paymentDouble / 118.0) * 100.0;
  }
  
}
