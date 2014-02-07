/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prim.business.lawForms;

import com.prim.business.Fiz;

/**
 *
 * @author User
 */
public class FizForm implements Fiz {
  
  private AbstractForm data;
  
  private FizForm(Integer type, String name, String fullName) {
    data = AbstractForm.getInstance(type, name, fullName);
  }
  
  public static FizForm getInstance(Integer type) {
    FizForm reslut = new FizForm(type, "Физ Лицо", "Физическое лицо");
    return reslut;
  }
  
  @Override
  public String getEgrip() {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  @Override
  public String setEgrip() {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  @Override
  public String getName() {
    return data.getName();
  }
  
  @Override
  public String getFullName() {
    return data.getFullName();
  }
  
  @Override
  public Integer getType() {
    return data.getType();
  }
}
