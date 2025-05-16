package gdg.whowantit.service.PostService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gdg.whowantit.dto.PostDto.ItemDTO;
import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.RequestBody;
import okhttp3.RequestBody;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import java.util.Map;
import java.util.List;
//import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Service
@RequiredArgsConstructor
public class GeminiVisionServiceImpl implements GeminiVisionService {

    //@Value("${gemini.api-key}")
    private static String API_KEY = "AIzaSyAgX-XTficiJ3C0Dqj-2l8h8x-HkUAf5Vo";
    private static final String ENDPOINT = "https://generativelanguage.googleapis.com/v1/models/gemini-1.5-pro-002:generateContent?key=" + API_KEY;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean verifyItemsWithImage(String imageUrl, List<ItemDTO> donatedItems) {
        try {
            // 이미지 base64 인코딩
            String base64Image = encodeImageToBase64(imageUrl);

            // 요청 JSON 생성
            String requestJson = objectMapper.writeValueAsString(buildVerificationRequest(base64Image, donatedItems));

            // 요청 전송
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(requestJson, MediaType.parse("application/json"));
            Request request = new Request.Builder().url(ENDPOINT).post(body).build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new RuntimeException("Gemini Vision API 호출 실패: " + response);
            }

            // 응답 파싱
            String responseJson = response.body().string();
            return parseGeminiVerificationResponse(responseJson);

        } catch (Exception e) {
            throw new RuntimeException("Gemini API 처리 중 오류 발생", e);
        }
    }

    private Object buildVerificationRequest(String base64Image, List<ItemDTO> donatedItems) {
        String donatedItemsJson;
        try {
            donatedItemsJson = objectMapper.writeValueAsString(donatedItems);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("donatedItems 직렬화 실패", e);
        }

        String prompt = """
                다음은 기부 물품 리스트입니다 (JSON 형식).
                이 이미지에 있는 물품과 같은지 확인해주세요.
                같으면 'YES'라고만 답하고, 다르면 'NO'라고만 답해주세요.

                물품 리스트:
                """ + donatedItemsJson;

        Map<String, Object> textPart = Map.of("text", prompt);
        Map<String, Object> imagePart = Map.of(
                "inline_data", Map.of(
                        "mime_type", "image/png",
                        "data", base64Image
                )
        );

        return Map.of("contents", List.of(
                Map.of("parts", List.of(textPart, imagePart))
        ));
    }

    public String encodeImageToBase64(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        try (InputStream in = url.openStream()) {
            byte[] imageBytes = in.readAllBytes();
            return Base64.getEncoder().encodeToString(imageBytes);
        }
    }

    private boolean parseGeminiVerificationResponse(String json) throws JsonProcessingException {
        System.out.println("🔵 Gemini API Raw Response JSON:");
        System.out.println(json);
        JsonNode root = objectMapper.readTree(json);
        String content = root.path("candidates").get(0)
                .path("content")
                .path("parts").get(0)
                .path("text").asText()
                .trim()
                .toUpperCase();
        System.out.println("🟡 Extracted Text:");
        System.out.println(content);

        boolean result = content.contains("YES");

        return result;
    }
}

