package hooks;



import io.cucumber.java.Before;

import static base_url.MedunnaBaseUrl.medunnaSetup;

public class Hooks {
    @Before("@RoomCreate")
    public void beforeApi(){
        medunnaSetup();
    }
}
