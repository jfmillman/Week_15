/**
 * 
 */
package com.promineotech.jeep.service;

import java.util.List;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;


//WEEK 14: fetchJeeps used in Jeep sales to fetch a list of Jeeps
public interface JeepSalesService {

  List<Jeep> fetchJeeps(JeepModel model, String trim);

}
