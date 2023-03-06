/**
 * 
 */
package com.promineotech.jeep.service;

import com.promineotech.jeep.entity.Order;
import com.promineotech.jeep.entity.OrderRequest;

/**
 * @author jessicamillman
 *
 */
public interface JeepOrderService {

  /**
   * @param orderRequest
   * @return
   */
  Order createOrder(OrderRequest orderRequest);

}
