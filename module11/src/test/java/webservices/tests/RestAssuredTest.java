package webservices.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import webservices.model.User;

public class RestAssuredTest {
	Response response;

	@BeforeMethod(description = "Initialization of WebService, get response", groups = { "RestAssured" })
	public void init() {
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
		response = RestAssured.when().get("/users").andReturn();
	}

	@Test(description = "Verify an http status code", groups = { "RestAssured" })
	public void checkStatusCode() {
		Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test(description = "Verify an http response header", groups = { "RestAssured" })
	public void checkResponseHeader() {
		Response response = RestAssured.when().get("/users").andReturn();
// 		the string 29 can be removed, since these action are performed in @BeforeMethod
		Assert.assertEquals(response.getHeader("Content-Type"),
				"application/json; charset=utf-8");
	}

	@Test(description = "Verify an http response body", groups = { "RestAssured" })
	public void checkResponseBody() {
		Response response = RestAssured.when().get("/users").andReturn();
// 		the string 37 can be removed, since these action are performed in @BeforeMethod
		ResponseBody<?> responseBody = response.getBody();
		User[] users = responseBody.as(User[].class);
		Assert.assertEquals(users.length, 10);
	}

}
