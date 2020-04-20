import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.testng.AssertJUnit.assertEquals;

public class ReqresApiTest {

    @Test(description = "LIST USERS")
    public void listUsers(){
        Response response = given()
        .when()
                .get("https://reqres.in/api/users?page={page}",2)
        .then()
                .statusCode(200)
                .extract().response();
        System.out.println(response.body().asString());
    }

    @Test(description = "SINGLE USER")
    public void singleUser(){
        String expected = "{\"data\":{\"id\":2,\"email\":\"janet.weaver@reqres.in\",\"first_name\":\"Janet\",\"last_name\":\"Weaver\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg\"},\"ad\":{\"company\":\"StatusCode Weekly\",\"url\":\"http://statuscode.org/\",\"text\":\"A weekly newsletter focusing on software development, infrastructure, the server, performance, and the stack end of things.\"}}";
        Response response = given()
        .when()
                .get("https://reqres.in/api/users/{id}",2)
        .then()
                .statusCode(200)
                .body("data.id", equalTo(2), "data.email", equalTo("janet.weaver@reqres.in"))
                .contentType(ContentType.JSON).extract().response();
        assertEquals(expected, response.asString().trim());
        System.out.println(response.body().asString());
    }

    @Test(description = "SINGLE USER NOT FOUND")
    public void singleUserNotFound(){
        Response response = given()
        .when()
                .get("https://reqres.in/api/users/{id}",23)
        .then()
                .statusCode(404)
                .extract().response();
        System.out.println(response.body().asString());
    }

    @Test(description = "LIST <RESOURCE>")
    public void listResource(){
        Response response = given()
        .when()
                .get("https://reqres.in/api/unknown")
        .then()
                .statusCode(200)
                .extract().response();
        System.out.println(response.body().asString());
    }

    @Test(description = "SINGLE <RESOURCE>")
    public void singleResource(){
        Response response = when()
                .get("https://reqres.in/api/unknown/{id}",2)
        .then()
                .statusCode(200)
                .extract().response();
        System.out.println(response.body().asString());
    }

    @Test(description = "SINGLE <RESOURCE> NOT FOUND")
    public void singleResourceNotFound(){
        Response response = given()
        .when()
                .get("https://reqres.in/api/unknown/{id}",23)
        .then()
                .statusCode(404)
                .extract().response();
        System.out.println(response.body().asString());
    }

    @Test(description = "CREATE")
    public void create(){
        Response response = given()
                .body("{\"name\": \"Sergey\", \"job\": \"QA Manager\"}")
        .when()
                .post("https://reqres.in/api/users/")
        .then()
                .statusCode(201)
                .extract().response();
        System.out.println(response.body().asString());
    }

    @Test(description = "Update")
    public void put(){
        Response response = given()
                .body("{\"name\": \"Sergey\", \"job\": \"QA Manager\"}")
                .when()
                .put("https://reqres.in/api/users/{id}",2)
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println(response.body().asString());
    }

    @Test(description = "Update")
    public void patch(){
        Response response = given()
                .body("{\"name\": \"Sergey\", \"job\": \"QA Manager\"}")
                .when()
                .patch("https://reqres.in/api/users/{id}",2)
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println(response.body().asString());
    }

    @Test(description = "Delete")
    public void delete(){
        Response response = given()
                .when()
                .delete("https://reqres.in/api/users/{id}",2)
                .then()
                .statusCode(204)
                .extract().response();
        System.out.println(response.body().asString());
    }

    @Test(description = "REGISTER - SUCCESSFUL")
    public void registerSucccessful(){
        Response response = given()
                .body("{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}")
                .header("Content-Type", "application/json")
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().ifError()
                .statusCode(200)
                .extract().response();
        System.out.println(response.body().asString());
    }

    @Test(description = "REGISTER - UNSUCCESSFUL")
    public void registerUnucccessful(){
        Response response = given()
                .body("{\"email\": \"sydney@fife\"}")
                .header("Content-Type", "application/json")
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().ifError()
                .statusCode(400)
                .extract().response();
        System.out.println(response.body().asString());
    }

    @Test(description = "LOGIN - SUCCESSFUL")
    public void loginSucccessful(){
        Response response = given()
                .body("{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}")
                .header("Content-Type", "application/json")
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().ifError()
                .statusCode(200)
                .body("token", equalTo("QpwL5tke4Pnpja7X4"))
                .extract().response();
        System.out.println(response.body().asString());
    }

    @Test(description = "LOGIN - UNSUCCESSFUL")
    public void loginUnsucccessful(){
        Response response = given()
                .body("{\"email\": \"peter@klaven\"")
                .header("Content-Type", "application/json")
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().ifError()
                .statusCode(400)
                .extract().response();
        System.out.println(response.body().asString());
    }





}
