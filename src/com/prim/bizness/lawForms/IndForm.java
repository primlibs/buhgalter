/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prim.bizness.lawForms;

import com.prim.bizness.Jur;
import com.prim.bizness.Fiz;
import com.prim.bizness.Ind;

/**
 *
 * @author User
 */
public class IndForm implements Ind {
  private AbstractForm data;
  
  private IndForm(Integer type,String name,String fullName){
    data=AbstractForm.getInstance(type, name,  fullName);
  }
  
  public static IndForm getInstance(Integer type){
    IndForm reslut= new IndForm(type,"ИП", "Индивидуальный предприниматель");
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
  public Integer getType() {
    return data.getType();
  }
  
}
