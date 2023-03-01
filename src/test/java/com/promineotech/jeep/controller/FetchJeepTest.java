/**
 * 
 */
package com.promineotech.jeep.controller;


import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import com.promineotech.jeep.Constants;
import com.promineotech.jeep.controller.support.fetchJeepTestSupport;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;
import com.promineotech.jeep.service.JeepSalesService;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.doThrow;


class FetchJeepTest {
  
  @Nested
//this extends the junit framework so the junit is in control of the test, specify to use a web
//environment, and use random port so the tests dont run on top of each other
@SpringBootTest (webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles ("test")
//WEEK 14: SQL annotation with 2 scripts and it's going to run a test for us 
@Sql(scripts = {"classpath:flyway/migrations/V1.0__Jeep_Schema.sql", "classpath:flyway/migrations/V1.1__Jeep_Data.sql"},
config = @SqlConfig(encoding = "utf-8"))
  class TestsThatDoNotPolluteTheApplicationContext extends fetchJeepTestSupport {
    @Test
    void testThatJeepsAreReturnedWhenAValidModelAndTrimAreSupplied() {
      //Given: a valid model, trim, and URI
      JeepModel model = JeepModel.WRANGLER;
      String trim = "Sport";
      String uri = String.format("%s?model=%s&trim=%s", getBaseUri(), model, trim);
      
      //When: a connection is made to the URI
      //WEEK 14: modified test to return a list of Jeep instead of one Jeep
     ResponseEntity<List<Jeep>> response = getRestTemplate().exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
   
     
      //Then: a valid success (OK - 200) status code is returned
     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
     
   
     //WEEK 14: And: the actual list returned is the same as the expected list
     //WEEK 14: buildExpected method is in fetchJeepTestSupport.java
     List<Jeep> actual = response.getBody();
     List<Jeep> expected = buildExpected();
     
     
     assertThat(actual).isEqualTo(expected);
    }

  //WEEK 15: 404 Error message for when an invalid trim is supplied
  @Test
  void testThatAnErrorMessageIsReturnedWhenAnUnknownTrimIsSupplied() {
    //Given: a valid model, trim, and URI
    JeepModel model = JeepModel.WRANGLER;
    String trim = "Unknown Value";
    String uri = String.format("%s?model=%s&trim=%s", getBaseUri(), model, trim);
    
    //When: a connection is made to the URI
   ResponseEntity<Map<String, Object>> response = getRestTemplate().exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

   
    //Then: a not found (404) status code is returned
   assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
   

   //And: an error message is returned
  Map<String, Object> error = response.getBody();

  assertErrorMessageValid(uri, error, HttpStatus.NOT_FOUND);
  }

  //WEEK 15: test that an invalid value is supplied
  @ParameterizedTest
  @MethodSource("com.promineotech.jeep.controller.FetchJeepTest#parametersForInvalidInput")
  void testThatAnErrorMessageIsReturnedWhenAnInvalidValueIsSupplied(String model, String trim, String reason) {
    //Given: a URI
    String uri = String.format("%s?model=%s&trim=%s", getBaseUri(), model, trim);
    
    //When: a connection is made to the URI
   ResponseEntity<Map<String, Object>> response = getRestTemplate().exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

   
    //Then: a not found (400) status code is returned
   assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
   

   //And: an error message is returned
  Map<String, Object> error = response.getBody();

  assertErrorMessageValid(uri, error, HttpStatus.BAD_REQUEST);
  }
  }
  
//WEEK 15: when the trim input is invalid. This method is used in the Method Source annotation for testThatAnErrorMessageIsReturnedWhenAnInvalidValueIsSupplied(
static Stream<Arguments> parametersForInvalidInput () {
  return Stream.of(
      arguments("WRANGLER", "*(&(*$)$*", "Trim contains non-alpha-numeric characters"),
      arguments("WRANGLER", "C".repeat(Constants.TRIM_MAX_LENGTH + 1), "Trim length too long"),
      arguments("INVALID", "Sport", "Model is not enum value")
      );
}
  
  @Nested
//this extends the junit framework so the junit is in control of the test, specify to use a web
//environment, and use random port so the tests dont run on top of each other
@SpringBootTest (webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles ("test")
//WEEK 14: SQL annotation with 2 scripts and it's going to run a test for us 
@Sql(scripts = {"classpath:flyway/migrations/V1.0__Jeep_Schema.sql", "classpath:flyway/migrations/V1.1__Jeep_Data.sql"},
config = @SqlConfig(encoding = "utf-8"))
  class TestsThatPolluteTheApplicationContext extends fetchJeepTestSupport {
    //WEEK 15: Mock Bean added here - creates this bean as a mock object and puts that bean in the bean registry and replaces that bean by any name that was there
    @MockBean
    private JeepSalesService jeepSalesService;
    //WEEK 15: tests for a 500 status result (Internal Server Error)
    @Test
    void testThatAnUnplannedErrorResultsInA500Status() {
      //Given: a valid model, trim, and URI
      JeepModel model = JeepModel.WRANGLER;
      String trim = "Invalid";
      String uri = String.format("%s?model=%s&trim=%s", getBaseUri(), model, trim);
      
      doThrow(new RuntimeException("Ouch!")).when(jeepSalesService).fetchJeeps(model, trim);
      
      //When: a connection is made to the URI
     ResponseEntity<Map<String, Object>> response = getRestTemplate().exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

     
      //Then: an internal server error (500) status code is returned
     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
     

     //And: an error message is returned
    Map<String, Object> error = response.getBody();

    assertErrorMessageValid(uri, error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }


}

