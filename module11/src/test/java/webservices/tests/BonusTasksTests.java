package webservices.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BonusTasksTests {
	
	@Test (description = "Test for method POST: create gist", groups = { "Bonus task" })
	public void testPost() {
		String randomString = String.format("%1$TH%1$TM%1$TS", new Date());

		Map<String, Object> fileDetails = new HashMap<String, Object>();
		Map<String, Object> file = new HashMap<String, Object>();
		
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.header("Authorization",
				"Bearer 4945972f825e34f55126fd136d2bb70a8e73c4a7");

		JSONObject requestBody = new JSONObject();
		fileDetails.put("content", "vra_test_content: " + randomString);
		file.put("vra_test" + randomString + ".txt", fileDetails);
		requestBody.put("description", "vra_test" + randomString);
		requestBody.put("public", true);
		requestBody.put("files", file);
		request.body(requestBody.toString());

		Response response = request.post("https://api.github.com/gists");

		Assert.assertEquals(response.getStatusCode(), 201);
	}

	@Test (description = "Test for method PUT, star gist", groups = { "Bonus task" })
	public void testPut() {
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.header("Authorization",
				"Bearer 4945972f825e34f55126fd136d2bb70a8e73c4a7");
		Response response = request
				.put("https://api.github.com/gists/1071b2c59088163b8c188eeb271d859a/star");
		
		Assert.assertEquals(response.getStatusCode(), 204);
	}

	@Test (description = "Test for method DELETE, delete gist", groups = { "Bonus task" })
	public void testDelete() {
		Map<String, Object> fileDetails = new HashMap<String, Object>();
		Map<String, Object> file = new HashMap<String, Object>();

		RequestSpecification requestCreation = RestAssured.given();
		requestCreation.header("Content-Type", "application/json");
		requestCreation.header("Authorization",
				"Bearer 4945972f825e34f55126fd136d2bb70a8e73c4a7");

		JSONObject requestBody = new JSONObject();
		fileDetails.put("content", "vra_test_content");
		file.put("vra_test.txt", fileDetails);
		requestBody.put("description", "vra_test");
		requestBody.put("public", true);
		requestBody.put("files", file);
		requestCreation.body(requestBody.toString());

		Response responseGistCreation = requestCreation
				.post("https://api.github.com/gists");
		String gistUrl = responseGistCreation.getHeaders().getValue("Location");

		
		RequestSpecification requestGistDeletion = RestAssured.given();
		requestGistDeletion.header("Content-Type", "application/json");
		requestGistDeletion.header("Authorization",
				"Bearer 4945972f825e34f55126fd136d2bb70a8e73c4a7");
		Response responseGistDeletion = requestGistDeletion.delete(gistUrl);

		Assert.assertEquals(responseGistDeletion.getStatusCode(), 204);
	}
	
	@Test (description = "Test for method PATCH, update gist", groups = { "Bonus task" })
	public void testPatch(){
		String randomString = String.format("%1$TH%1$TM%1$TS", new Date());

		Map<String, Object> fileDetails = new HashMap<String, Object>();
		Map<String, Object> file = new HashMap<String, Object>();

		RequestSpecification requestCreation = RestAssured.given();
		requestCreation.header("Content-Type", "application/json");
		requestCreation.header("Authorization",
				"Bearer 4945972f825e34f55126fd136d2bb70a8e73c4a7");
		JSONObject requestBody = new JSONObject();
		fileDetails.put("content", "vra_test_content_toUpdate");
		file.put("vra_test_toUpdate.txt", fileDetails);
		requestBody.put("description", "vra_test_toUpdate");
		requestBody.put("public", true);
		requestBody.put("files", file);
		requestCreation.body(requestBody.toString());

		Response responseGistCreation = requestCreation
				.post("https://api.github.com/gists");
		String gistUrl = responseGistCreation.getHeaders().getValue("Location");
		
		JSONObject updateRequestBody = new JSONObject();
		updateRequestBody.put("description", "vra_test_toUpdate: " + randomString);
		
		RequestSpecification requestUpdateGist = RestAssured.given();
		requestUpdateGist.header("Content-Type", "application/json");
		requestUpdateGist.header("Authorization",
				"Bearer 4945972f825e34f55126fd136d2bb70a8e73c4a7");
		requestUpdateGist.body(updateRequestBody.toString());
				
		Response responseGistUpdate = requestUpdateGist.patch(gistUrl);
		
		Assert.assertEquals(responseGistUpdate.getStatusCode(), 200);

	}

}
