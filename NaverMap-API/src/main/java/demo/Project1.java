package demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Project1 {

	public static void main(String[] args) throws Exception {

		Scanner scanner = new Scanner(System.in);
		System.out.print("주소를 입력: ");
		String address = scanner.nextLine();
		scanner.close();

		StringBuilder urlBuilder = new StringBuilder(
				"https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query="); /* URL */
		urlBuilder.append(URLEncoder.encode(address, "UTF-8")); /* 주소를 url인코딩하여 붙임 */

		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "czr2gkaxus"); // 클라이언트 ID
		conn.setRequestProperty("X-NCP-APIGW-API-KEY", "xEYUx6Mcb7hB5xRDZgtKqB5hcdph234XTAR3cnnd"); // key값
		System.out.println("Response code: " + conn.getResponseCode());

		BufferedReader br;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		} else {
			br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}

		String result = br.readLine();

		br.close();
		conn.disconnect();

		// System.out.println(result);
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
		JSONArray arr = (JSONArray) jsonObject.get("addresses");

		for (Object one : arr) {
			JSONObject ob = (JSONObject) one;
			// System.out.println(employee.toString());
			System.out.println("address:" + ob.get("roadAddress"));
			System.out.println("jibunAddress:" + ob.get("jibunAddress"));
			System.out.println("경도:" + ob.get("x"));
			System.out.println("위도:" + ob.get("y"));
		}
	}
}
