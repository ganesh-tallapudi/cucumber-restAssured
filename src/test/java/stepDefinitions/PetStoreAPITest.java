package stepDefinitions;

import PetStorePojo.Root;
import Utils.Helper;
import Utils.PetStatus;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.mapper.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class PetStoreAPITest {

    private ValidatableResponse VResponse;
    private Response response;
    private RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
    private RequestSpecification requestSpec;
    private final int validStatusCode = 200;
    private int obsPetCount = 0;
    private JSONArray jsonArray;
    protected Helper helper = new Helper();
    private static final Logger logger = LogManager.getLogger();

    @Given("The endpoint is up")
    public void theEndpointIsUp() {
        VResponse = RestAssured.given().when().get().then();
        VResponse.statusCode(validStatusCode);
    }

    @When("I perform post with to endpoint {string}")
    public void iPerformPostWithToEndpoint(String uri) {
        response = RestAssured.given().param("status", "available").when().get(uri);
        logger.info("Status Code " + response.getStatusCode());

        //  System.out.println(JsonPath.read(response.getBody().asString(), "$[?(@.name==\"doggie\")].name").toString());
    }

    @Then("I should see a response of {int}")
    public void iShouldSeeAResponseOf(int httpStatus) {
        Assert.assertEquals(response.getStatusCode(), httpStatus);
        logger.info("Response Status Code Matched");

    }

    @And("I should see a count with {int}")
    public void iShouldSeeACountWith(int expectedPetCount) {
        obsPetCount = helper.getCountfromResponse("$[?(@.name==\"doggie\")].name", response);
        Assert.assertEquals(expectedPetCount, obsPetCount);
        logger.info("Count of pets : " + obsPetCount);
    }

    @When("I perform get request to endpoint {string} with status {string}")
    public void iPerformGetRequestToEndpointWithStatus(String path, String status) {
        response = RestAssured.given().param("status", status).when().get(path);
        logger.info("Count of pets : " + response.getStatusCode());
    }


    @And("I should see a count for pet {string} with {int}")
    public void iShouldSeeACountForPetWith(String petName, int expectedPetCount) {
        obsPetCount = helper.getCountfromResponse("$..name", response, petName);
        Assert.assertEquals(expectedPetCount, obsPetCount);
        logger.info("Count of pets : " + obsPetCount);
    }

    @When("I read the Json response file")
    public void iReadTheJsonResponseFile() throws IOException, ParseException {
        jsonArray = helper.getJSONArray();
    }

    @Given("The swagger service is down for endpoint {string}")
    public void theSwaggerServiceIsDownForEndpoint(String endpoint) {
        //# Lets assume the service is down and commenting below code of assertion

        //Assert.assertEquals(response.getStatusCode(),404);

    }

    @Then("I should verify the count for pet {string} to be {int}")
    public void iShouldVerifyTheCountForPetToBe(String petName, int expCount) {
        int obsCount = 0;
        for (Object o : jsonArray) {
            try {
                JSONObject names = (JSONObject) o;
                String name = (String) names.get("name");
                if (name.equals(petName)) {
                    obsCount++;
                }
            } catch (Exception ex) {
            }
        }
        logger.info("Count of pets : " + obsCount);
        Assert.assertEquals(expCount, obsCount);
    }



    @When("I perform get request to endpoint {string} with status <status>")
    public void iPerformGetRequestToEndpointWithStatusStatus(String arg0) {

    }


    @And("I should see a count with {int} for pet {string}")
    public void iShouldSeeACountWithForPet(int expectedPetCount, String petName) {
        obsPetCount = helper.getCountfromResponse("$[?(@.name==\"" + petName + "\")].name", response);
        Assert.assertEquals(expectedPetCount, obsPetCount);
        logger.info("Count of pets : " + obsPetCount);
    }

    @Then("I should see a valid response with count {int};")
    public void iShouldSeeAValidResponseWithCount(int expCount){
          List<Object> response = Arrays.asList(jsonArray.stream().toArray());

          long observedCount =response.stream().filter(helper.predicate(PetStatus.Available.toString())).count();

             int obsCount = (int )observedCount;
             Assert.assertEquals(expCount,obsCount);

        }




        private void performObjectMappingFrmJson(){
           /* ObjectMapper om = new ObjectMapper();
            Root root = om.readValue(myJsonString), Root.class);*/
          Root petstorepojo =  new Gson().fromJson(response.getBody().asString(), Root.class);
            System.out.println(petstorepojo.category.name);
        }
}

