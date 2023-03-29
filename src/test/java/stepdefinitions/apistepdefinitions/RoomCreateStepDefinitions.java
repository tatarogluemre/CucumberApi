package stepdefinitions.apistepdefinitions;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.codehaus.jackson.map.ObjectMapper;
import pojos.Room;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static base_url.MedunnaBaseUrl.spec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class RoomCreateStepDefinitions {
    Response response;
    int roomNumber = Faker.instance().number().numberBetween(1000,1000000);
    Room expectedData;
    @Given("user sends the request for room data")
    public void user_sends_the_request_for_room_data() {
       //set the url
        spec.pathParams("first","api","second","rooms");

       //set the expected data
       expectedData = new Room("Boş Oda",2222,roomNumber,"TWIN",false);
        System.out.println("expectedData = " + expectedData);

        //send the request and get the response
        response = given(spec).contentType(ContentType.JSON).body(expectedData).when().post("/{first}/{second}");
        response.prettyPrint();

    }
    @Then("user gets the room data assert")
    public void user_gets_the_room_data_assert() throws IOException {
    assertEquals(201,response.statusCode());

        //1. Validation
        response.then().body("roomNumber",equalTo(roomNumber)).
                body("roomType",equalTo("TWIN")).
                body("status",equalTo(false)).
                body("price",equalTo(2222)).
                body("description",equalTo("Boş Oda"));

        //2.Validation
       JsonPath jsonPath =response.jsonPath();
       assertEquals((int)expectedData.getroomNumber(),jsonPath.getInt("roomNumber"));
       assertEquals(expectedData.getRoomType(),jsonPath.getString("roomType"));
       assertEquals(expectedData.getStatus(),jsonPath.getBoolean("status"));
       assertEquals((int)expectedData.getPrice(),jsonPath.getInt("price"));
       assertEquals(expectedData.getDescription(),jsonPath.getString("description"));

       //3.Validation
        Map<String,Object> actualData = response.as(HashMap.class);
        System.out.println("actualData = " + actualData);
        assertEquals((int)expectedData.getroomNumber(),actualData.get("roomNumber"));
        assertEquals(expectedData.getRoomType(),actualData.get("roomType"));
        assertEquals(expectedData.getStatus(),actualData.get("status"));
        assertEquals((int)expectedData.getPrice(),actualData.get("price"));
        assertEquals(expectedData.getDescription(),actualData.get("description"));

        //4.Validation
        Room actualDataPojo = response.as((Type) Room.class);
        assertEquals(expectedData.getroomNumber(),actualDataPojo.getroomNumber());
        assertEquals(expectedData.getRoomType(),actualDataPojo.getRoomType());
        assertEquals(expectedData.getStatus(),actualDataPojo.getStatus());
        assertEquals(expectedData.getPrice(),actualDataPojo.getPrice());
        assertEquals(expectedData.getDescription(),actualDataPojo.getDescription());

        //5.Validation
        Room actualDataObjectMapper = new ObjectMapper().readValue(response.asString(), Room.class);
        assertEquals(expectedData.getroomNumber(),actualDataObjectMapper.getroomNumber());
        assertEquals(expectedData.getRoomType(),actualDataObjectMapper.getRoomType());
        assertEquals(expectedData.getStatus(),actualDataObjectMapper.getStatus());
        assertEquals(expectedData.getPrice(),actualDataObjectMapper.getPrice());
        assertEquals(expectedData.getDescription(),actualDataObjectMapper.getDescription());

        //6. Validation
        Room actualDataGson = new Gson().fromJson(response.asString(), Room.class);

        assertEquals(expectedData.getroomNumber(), actualDataGson.getroomNumber());
        assertEquals(expectedData.getRoomType(), actualDataGson.getRoomType());
        assertEquals(expectedData.getStatus(), actualDataGson.getStatus());
        assertEquals(expectedData.getPrice(), actualDataGson.getPrice());
        assertEquals(expectedData.getDescription(), actualDataGson.getDescription());
        

    }

}
