package webservices.tests;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import webservices.model.User;

public class RestTemplateTest {
	ResponseEntity<User[]> response;

	@BeforeMethod(description = "Initialization of WebService, get response", groups = { "RestTemplate" })
	public void init() {
		RestTemplate restTemplate = new RestTemplate();
		response = restTemplate.getForEntity(
				"https://jsonplaceholder.typicode.com/users", User[].class);
	}

	@Test(description = "Verify http status code", groups = { "RestTemplate" })
	public void checkStatusCode() {
		Assert.assertEquals(response.getStatusCodeValue(), 200);
	}

	@Test(description = "Verify http response header", groups = { "RestTemplate" })
	public void checkResponseHeader() {
		Assert.assertEquals(response.getHeaders().getContentType().toString(),
				"application/json;charset=utf-8");
	}

	@Test(description = "Verify http response body", groups = { "RestTemplate" })
	public void checkResponseBody() {
		Assert.assertEquals(response.getBody().length, 10);
	}

}
