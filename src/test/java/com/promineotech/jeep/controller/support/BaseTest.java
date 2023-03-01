/**
 * 
 */
package com.promineotech.jeep.controller.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import lombok.Getter;

/**
 * @author jessicamillman
 *
 */
public class BaseTest {

  @LocalServerPort
  private int serverPort;
  
  //Tells Spring boot to ingest whatever copy of test rest template it has created for us
  @Autowired
  //Lombok imported getBaseURI for us even though we haven't created one
  @Getter
  private TestRestTemplate restTemplate;
  
  
 //retrieves the URI
  protected String getBaseUri() {
    return String.format("http://localhost:%d/jeeps", serverPort);
  }

}
