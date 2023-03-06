/**
 * 
 */
package com.promineotech.jeep.controller.support;

/**
 * @author jessicamillman
 *
 */
public class CreateOrderTestSupport extends BaseTest {
  //WEEK 16: This is the JSON and we built it into a method to use in CreateOrderTest in the testCreateOrderTeturnsSuccess201 method
  protected String createOrderBody() {
    return  "{\n"
         + " \"customer\":\"MORISON_LINA\",\n"
         + " \"model\":\"WRANGLER\",\n"
         + " \"trim\":\"Sport Altitude\",\n"
         + " \"doors\":4,\n"
         + " \"color\":\"EXT_NACHO\",\n"
         + " \"engine\":\"2_0_TURBO\",\n"
         + " \"tire\":\"35_TOYO\",\n"
         + " \"options\":[\n"
         + "   \"DOOR_QUAD_4\",\n"
         + "   \"EXT_AEV_LIFT\",\n"
         + "   \"EXT_WARN_WINCH\",\n"
         + "   \"EXT_WARN_BUMPER_FRONT\",\n"
         + "   \"EXT_WARN_BUMPER_REAR\",\n"
         + "   \"EXT_ARB_COMPRESSOR\"\n"
         + " ]\n"
         + "}";
  }
}
