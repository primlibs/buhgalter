/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prim.bizness.lawForms;

/**
 *
 * @author User
 */
class AbstractForm implements LawForms{
  private final Integer type;
  private final String name;
  private final String fullName;
  
  private AbstractForm (Integer type,String name,String fullName){
    this.type=type;
    this.name=name;
    this.fullName=fullName;
  }

  public static AbstractForm getInstance(Integer type,String name,String fullName){
    return new AbstractForm(type, name, fullName);
  }
          
  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getFullName() {
    return fullName;
  }

  @Override
  public Integer getType() {
    return type;
  }
}
