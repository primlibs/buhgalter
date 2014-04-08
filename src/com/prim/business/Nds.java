/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prim.business;

/**
 *
 * @author Rice Pavel
 */
public class Nds {
  
  public static Double getPaymentWithoutNds(double payment) {
    return (payment / 118.0) * 100.0;
  }
  
  public static Double getPaymentWithoutNds(String payment) throws NumberFormatException {
    double paymentDouble = Double.parseDouble(payment);
    return (paymentDouble / 118.0) * 100.0;
  }
  
}
