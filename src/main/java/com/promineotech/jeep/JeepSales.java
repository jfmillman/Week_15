/**
 * 
 */
package com.promineotech.jeep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.promineotech.ComponentScanMarker;

//This class starts up spring boot
//WEEK 14: added scanBasePackages to scan all base and sub packages/classes. For this, we also
//created the interface ComponentScanMarker
@SpringBootApplication (scanBasePackageClasses = {ComponentScanMarker.class})

public class JeepSales {


  public static void main(String[] args) {
    SpringApplication.run(JeepSales.class, args);
  }

}
