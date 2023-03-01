/**
 * 
 */
package com.promineotech.jeep.entity;

import java.math.BigDecimal;
import java.util.Comparator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author jessicamillman
 *
 */
//WEEK 14: copied this info from the create table method in Jeep Schema
//WEEK 14: @data will give us the getters and setters, the toString to make sure it prints nicely, and the hash code
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Jeep implements Comparable<Jeep> {
  private Long modelPk;
  private JeepModel modelId;
  private String trimLevel;
  private int numDoors;
  private int wheelSize;
  private BigDecimal basePrice;
  
  //WEEK 15: override the get model Pk from Lombok so we can tell Jackson to ignore it for the test
  @JsonIgnore
  public Long getModelPk() {
    return modelPk;
  }

  //WEEK 15: this makes order not matter between the 2 jeep models the test will pass either way
  @Override
  public int compareTo(Jeep that) {
    return Comparator
        .comparing(Jeep::getModelId)
        .thenComparing(Jeep::getTrimLevel)
        .thenComparing(Jeep::getNumDoors)
        .compare(this,that);
   
  }

}


