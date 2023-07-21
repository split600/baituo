import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ChatGptPlugin {

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/engines/davinci-codex/completions";
    private static final String API_KEY = "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";

    public String generateResponse(String input) {
        try {
            // Encode input text for API request
            String encodedInput = URLEncoder.encode(input, StandardCharsets.UTF_8.toString());

            // Create the API request URL
            URL url = new URL(OPENAI_API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Authorization", "Bearer " + API_KEY);

            // Set request parameters
            String data = "prompt=" + encodedInput + "&max_tokens=100";
            conn.setDoOutput(true);
            conn.getOutputStream().write(data.getBytes());

            // Get the API response
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                responseBuilder.append(line);
            }

            // Parse the JSON response to extract the model's reply
            // (This part depends on the specific API response format)

            // Close connections and return the response
            br.close();
            conn.disconnect();
            return "ChatGPT: " + "extracted_response_here";

        } catch (IOException e) {
            e.printStackTrace();
            return "Error: Unable to process the request.";
        }
    }

    // Main method to interact with the plugin
    public static void main(String[] args) {
        ChatGptPlugin chatGptPlugin = new ChatGptPlugin();
        System.out.println("Enter your message (type 'exit' to quit):");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            while (!(input = br.readLine()).equalsIgnoreCase("exit")) {
                String response = chatGptPlugin.generateResponse(input);
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
