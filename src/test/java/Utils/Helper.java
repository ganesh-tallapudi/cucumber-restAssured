package Utils;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Helper {


    public int getCountfromResponse(String jsonPathexpression, Response response) {
        String pets = JsonPath.read(response.getBody().asString(), jsonPathexpression).toString();
        return pets.length();
    }


    public int getCountfromResponse(String jsonPathexpression, Response response, String petName) {
        String pets = JsonPath.read(response.getBody().asString(), jsonPathexpression).toString();
        String[] petCountAllList = pets.split(",");
        int petcount = 0;
        for (String eachPet : petCountAllList) {
            eachPet.equals(petName);
            petcount++;
        }
        return petcount;

    }

    public JSONArray getJSONArray() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        return (JSONArray) jsonParser.parse(new FileReader("src/test/resources/response.json"));
    }

}
