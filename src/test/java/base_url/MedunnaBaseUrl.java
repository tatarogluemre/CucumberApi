package base_url;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static utilities.MedunnaAuthentication.getToken;

public class MedunnaBaseUrl {

    public static RequestSpecification spec;

    public static void medunnaSetup(){
        spec = new RequestSpecBuilder().addHeader("Authorization","Bearer "+getToken()).setContentType(ContentType.JSON).setBaseUri("https://medunna.com").build();

    }

}
