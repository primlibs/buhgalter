/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prim.business.lawForms;

import com.prim.business.Jur;
import com.prim.business.Fiz;

/**
 *
 * @author User
 */
public class JurForm implements Jur{
  private AbstractForm data;
  
  private JurForm(Integer type,String name,String fullName){
    data=AbstractForm.getInstance(type, name,  fullName);
  }
  
  public static JurForm getInstance(Integer type){
    JurForm reslut= new JurForm(type,"Юр. лицо", "Юридическое лицо");
    return reslut;
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
  public String getEgrul() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public String setEgrul() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Integer getType() {
    return data.getType();
  }
  
}
