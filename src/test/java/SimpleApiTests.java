import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;


public class SimpleApiTests {

    @Test
    public void testTask() {
//      Get response from the Base API URL and make JSONObject
        Response response = RestAssured.get("https://swapi.dev/api/");
        JSONObject myObject = new JSONObject(response.asPrettyString());


//      Get response from 'films' key
//      Make JSONObject
//      Get the 'count' number and parse it to int variable
//      Use the int variable and loop the films record until find the correct movie
        response = RestAssured.get(myObject.getString("films"));
        myObject = new JSONObject(response.asPrettyString());
        int number = Integer.parseInt(myObject.getNumber("count").toString());
        int correctFilmId = 0;
        for (int i = 1; i <= number; i++) {
            response = RestAssured.get("https://swapi.dev/api/films/" + i + "/");
            myObject = new JSONObject(response.asPrettyString());
            if (myObject.getString("title").trim().equalsIgnoreCase("A New Hope")) {
                correctFilmId = i;
                break;
            }
        }

//      Get the response of the correct movie url
//      Make a JSONObject
//      Make a JSONArray from key 'characters'
//      Iterate the JSONArray until find the correct 'name' key
        response = RestAssured.get("https://swapi.dev/api/films/" + correctFilmId + "/");
        myObject = new JSONObject(response.asPrettyString());
        JSONArray people = myObject.getJSONArray("characters");
        for (Object person : people) {
            response = RestAssured.get(person.toString());
            myObject = new JSONObject(response.asPrettyString());
            if (myObject.getString("name").trim().equalsIgnoreCase("biggs darklighter")) {
                break;
            }
        }
//      Get the response of the correct movie url
//      Make a JSONObject
//      Make a JSONArray from key 'starships'
//      Iterate the JSONArray until find the correct 'starship_class' key
//      Assertion
        JSONArray starShips = myObject.getJSONArray("starships");
        for (Object starShip : starShips) {
            response = RestAssured.get(starShip.toString());
            myObject = new JSONObject(response.asPrettyString());
            if (myObject.getString("starship_class").trim().equalsIgnoreCase("Starfighter")) {
                Assert.assertEquals(myObject.getString("starship_class").trim(), "Starfighter");
                break;
            }
        }
//      Get the response of the correct movie url
//      Make a JSONObject
//      Make a JSONArray from key 'pilots'
//      Iterate the JSONArray until find the correct 'name' key
//      Assertion  
        JSONArray pilots = myObject.getJSONArray("pilots");
        for (Object pilot : pilots) {
            response = RestAssured.get(pilot.toString());
            myObject = new JSONObject(response.asPrettyString());
            if (myObject.getString("name").trim().equalsIgnoreCase("Luke Skywalker")) {
                Assert.assertEquals(myObject.getString("name").trim(), "Luke Skywalker");
                break;
            }
        }
    }

}
