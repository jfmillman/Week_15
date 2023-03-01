/**
 * 
 */
package com.promineotech.jeep.controller.support;

import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;

//WEEK 14: the expected list of Jeep
//WEEK 14: built all of the objects for Wrangler from JeepData.sql
public class fetchJeepTestSupport extends BaseTest{
  protected List<Jeep> buildExpected() {
    List<Jeep> list = new LinkedList<>();
    
    list.add(Jeep.builder()
        .modelId(JeepModel.WRANGLER)
        .trimLevel("Sport")
        .numDoors(2)
        .wheelSize(17)
        .basePrice(new BigDecimal("28475.00"))
        .build());
    
    list.add(Jeep.builder()
        .modelId(JeepModel.WRANGLER)
        .trimLevel("Sport")
        .numDoors(4)
        .wheelSize(17)
        .basePrice(new BigDecimal("31975.00"))
        .build());
    
    //WEEK 15: sort here so the order of jeep doesnt matter
    Collections.sort(list);
    return list;
  }
  
  protected void assertErrorMessageValid(Map<String, Object> error,  HttpStatus status) {
    assertThat(error)
      .containsKey("message")
      .containsEntry("status code", status.value())
      .containsKey("uri")
      .containsKey("timestamp")
      .containsEntry("reason", status.getReasonPhrase());
    
   assertThat(error.get("uri").toString()).contains("/jeeps"); 
  }
  
}


