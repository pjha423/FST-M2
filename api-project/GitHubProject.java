package LiveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class GitHubProject {

    RequestSpecification requestSpec;

    String sshKey;

    int sshKeyId;

    @BeforeClass
    public void setUp(){
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization","token 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")
                .setBaseUri("https://api.github.com")
                .build();

        sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDtKVJRD0hKh7yRT8BxmiIZvRq6B5HT/v285oS0/aONrreMZgGmojuBG5m6zbFot8PBFNgZVSeTQUlmFbB+TrRsTuSLuEXSaYbHLMQSZwv/z8voLQD7e23rwoPNVVq5CinCvs+uAO3HXljg8okUiHAPtZrKIV0j3fWlwdId0roeE/J7MS1pTWmsPUDlIA9b0EiHJhUWBHMIlVgJN2d1lBtGObKb2F5OOtrRKxG68+t6SNKWrDl9OZmlb30KXKd0GO4ppkmQQKOz9ZIEgeYpBfVJUng/ECgi3bcH4nIMMSM3okybqrY2IG+yiKmpeYWA+la3/EuEgpkFg30AogEo5BMX ";

    }

    @Test(priority = 1)
    public void addKeys(){
        String reqBody = "{\"title\":\"Test Key\",\"key\":\""+sshKey+"\"}";
        Response response = given().spec(requestSpec)
                .body(reqBody)
                .when().post("/user/keys");

        String resBody = response.getBody().asPrettyString();
        System.out.println(reqBody);
        sshKeyId = response.then().extract().path("id");

        response.then().statusCode(201);



    }
    @Test
    public void getKeys(){
        Response response = given().spec(requestSpec).log().all()
                .pathParam("KeyId",sshKeyId)
                .when().get("/user/keys/{keyId}");

        String resBody = response.getBody().asPrettyString();

        response.then().statusCode(200);



    }

    @Test(priority=3)
    public void deletePet() {
        Response response =
                given().contentType(ContentType.JSON).log().all() // Set headers
                        .when().pathParam("keyId", sshKeyId) // Set path parameter
                        .delete("/user/keys/{keyId}"); // Send DELETE request

        // Assertion
        response.then().statusCode(204);

    }
}
