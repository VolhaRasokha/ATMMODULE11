package webservices.tests;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import webservices.model.User;

import com.google.gson.Gson;

public class ApacheHttpClientTest {
	@Test(description = "Verify http status code", groups = { "ApacheHttpClient" })
	public void checkStatusCodeTest() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(
				"https://jsonplaceholder.typicode.com/users");
		CloseableHttpResponse response;
		try {
			response = httpclient.execute(httpGet);
			Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test(description = "Verify http status code", groups = { "ApacheHttpClient" })
	public void checkResponseHeader() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(
				"https://jsonplaceholder.typicode.com/users");
		CloseableHttpResponse response;
		try {
			response = httpclient.execute(httpGet);
			Assert.assertEquals(response.getEntity().getContentType().toString(),
					"Content-Type: application/json; charset=utf-8");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test(description = "Verify http response body", groups = { "ApacheHttpClient" })
	public void checkResponseBody() {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(
				"https://jsonplaceholder.typicode.com/users");
		CloseableHttpResponse httpResponse;

		try {
			httpResponse = httpclient.execute(httpGet);
			String response = EntityUtils.toString(httpResponse.getEntity());
			Gson gson = new Gson();
			User[] users = gson.fromJson(response, User[].class);

			Assert.assertEquals(users.length, 10);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
