package utilities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static base_url.MedunnaBaseUrl.spec;
import static io.restassured.RestAssured.given;

public class MedunnaAuthentication {

       public static String getToken(){
        String url = "https://www.medunna.com/api/authenticate";
        Map<String,Object> token = new HashMap<>();
        token.put("password","Batch.103");
        token.put("remenberMe",true);
        token.put("username","batch_yuzuc");
        Response response = given().contentType(ContentType.JSON).when().body(token).post(url);
        return response.jsonPath().getString("id_token");
    }


}
