import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class RESTAssuredEmployeeJSONTests {
    private int empId;
    @Before
    public void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;
        empId = 9;

    }

    public Response getEmployeeList(){
        Response response = RestAssured.get("/employees");
        return response;
    }

    @Test
    public void givenEmployee_OnPost_ShouldReturnAddedEmployee(){
        Response response = RestAssured.given()
                                       .contentType(ContentType.JSON)
                                       .accept(ContentType.JSON)
                                       .body("{\"name\": \"Hayato\",\"salary\": \"3000\"}")
                                       .when()
                                       .post("/employees");
        String respAsStr = response.asString();
        JsonObject jsonObject = new Gson().fromJson(respAsStr, JsonObject.class);
        int id = jsonObject.get("id").getAsInt();
        response.then().body("id", Matchers.any(Integer.class));
        response.then().body("name", Matchers.is("Hayato"));
    }

    /*@Test
    public void givenEmployee_OnUpdate_ShouldReturnUpdatedEmployee(){
        Response response = RestAssured.given()
                            .contentType(ContentType.JSON)
                            .accept(ContentType.JSON)
                            .body("{\"name\": \"Lisa\", \"salary\": \"10000\"}")
                            .when()
                            .put("/employees/update/"+empId);
        String respAsStr = response.asString();
        response.then().body("id", Matchers.any(Integer.class));
        response.then().body("name", Matchers.is("Lisa"));
        response.then().body("salary", Matchers.is("10000"));
    }
     */
}
