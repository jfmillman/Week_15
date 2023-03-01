/**
 * 
 */
package com.promineotech.jeep.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;
import com.promineotech.jeep.service.JeepSalesService;
import lombok.extern.slf4j.Slf4j;

//Tells spring boot that this controller implements the Jeep controller
@RestController
//WEEK 14: This means we can log something
@Slf4j
public class BasicJeepSalesController implements JeepSalesController {

  //WEEK 14: Tells Spring that we want an object to be injected here
  @Autowired
  private JeepSalesService jeepSalesService;
  
  
  @Override
  public List<Jeep> fetchJeeps(JeepModel model, String trim) {
    //WEEK 14:here we log our model and trim. The curly brackets will allow for any value
    log.debug("model={}, trim={}", model, trim);
    return jeepSalesService.fetchJeeps(model,trim);
  }

}
