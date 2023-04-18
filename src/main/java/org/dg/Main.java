package org.dg;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        CustomDetails customDetails = new CustomDetails();

        List<String> emails = Files.lines(Paths.get("src/main/resources/emailAddresses")).collect(Collectors.toList());

        String firstName = customDetails.firstName;
        String lastName = customDetails.lastName;
        String password = customDetails.password;
        String cellphoneNumber = customDetails.cellphone;

        HttpClient client = HttpClient.newBuilder().build();
        String url = "https://api.takealot.com/rest/v-1-10-0/customers/register";

        for (String email : emails) {
            String requestBody = String.format("{\"platform\":\"desktop\",\"sections\":[{\"section_id\":\"register_customer\",\"fields\":[{\"field_id\":\"first_name\",\"value\":\"%s\"},{\"field_id\":\"last_name\",\"value\":\"%s\"},{\"field_id\":\"email\",\"value\":\"%s\"},{\"field_id\":\"new_password\",\"value\":\"%s\"},{\"field_id\":\"mobile_country_code\",\"value\":\"ZA\"},{\"field_id\":\"mobile_national_number\",\"value\":\"%s\"},{\"field_id\":\"get_promotional_offers\",\"value\":\"true\"}]}]}", firstName, lastName, email, password, cellphoneNumber);
            System.out.println(requestBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("sec-ch-ua", "Chromium;v=\"110\", \"Not A(Brand\";v=\"24\", \"Brave\";v=\"110\"")
                    .header("accept", "application/json")
                    .header("content-type", "application/json")
                    .header("x-csrf-token", "995671ff")
                    .header("sec-ch-ua-mobile", "?0")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")
                    .header("sec-ch-ua-platform", "Windows")
                    .header("Sec-GPC", "1")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response status code: " + response.statusCode());
            System.out.println("Response body: " + response.body());

            // Add a delay between requests to avoid being blocked
            Thread.sleep(10000); // 10 seconds
        }
    }
}