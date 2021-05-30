package Utils;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

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

/*
To get the record list based on status
 */
    public Predicate predicate(String status) {

        Predicate<Map<String, String>> predicate = new Predicate<Map<String, String>>() {
            @Override
            public boolean test(Map<String, String> map) {
                if (map.get("name") != null) {
                    return ((map.get("name").equals("doggie")) && (map.get("status").equals("available"))) ;//&& map.get("status").equals(status)
                }
                return false;
            }
        };

        return predicate;
    }

}