/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prim.business.lawForms;

import com.prim.business.Fiz;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author User
 */
public class lawFormFabric {

  // физлицо
  public static final Integer FIZ = 1;
  // юрлицо
  public static final Integer JUR = 2;
  // индивидуальный предприниматель
  public static final Integer IND = 3;

  public static LawForms getForm(Integer i) {
    if (i == FIZ) {
      return FizForm.getInstance(FIZ);
    } else if (i == JUR) {
      return JurForm.getInstance(JUR);
    } else if (i == IND) {
      return IndForm.getInstance(IND);
    }
    return null;
  }

  public static Map<String, Object> getFormWithName() {
    Map<Integer, String> result = new LinkedHashMap();
    setNameToMap(result,JUR,getForm(JUR));
    setNameToMap(result,IND,getForm(IND));
    setNameToMap(result,FIZ,getForm(FIZ));
    Map<String, Object> result2 = new LinkedHashMap();
    for (Integer key: result.keySet()) {
      result2.put(key.toString(), result.get(key));
    }
    return result2;
  }
  
  public static Map<Integer, String> getFormWithFullName() {
    Map<Integer, String> result = new HashMap();
    setFullNameToMap(result,FIZ,getForm(FIZ));
    setFullNameToMap(result,JUR,getForm(JUR));
    setFullNameToMap(result,IND,getForm(IND));
    return result;
  }
  
  public static Map<Integer, LawForms> getForms() {
    Map<Integer, LawForms> result = new HashMap();
    result.put(FIZ, getForm(FIZ));
    result.put(JUR, getForm(JUR));
    result.put(IND, getForm(IND));
    return result;
  }
  
  private static Map<Integer, String> setNameToMap (Map<Integer, String> mp,Integer i,LawForms nm){
    if(i!=null&&nm!=null){
      mp.put(i, nm.getName());
      return mp;
    }else{
      return mp;
    }
  }
  
  private static Map<Integer, String> setFullNameToMap (Map<Integer, String> mp,Integer i,LawForms nm){
    if(i!=null&&nm!=null){
      mp.put(i, nm.getFullName());
      return mp;
    }else{
      return mp;
    }
  }
  

  
}
