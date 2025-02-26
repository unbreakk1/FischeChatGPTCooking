package com.example.chatgptbasedcookingingredients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientService
{
    private final RestClient restClient;

    public IngredientService(@Value("${OPENAI_KEY}") String key,RestClient.Builder restClient)
    {
        this.restClient = restClient
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader("Authorization", "Bearer " + key)
                .build();
    }

    public String categorizeIngredient(String ingredient)
    {
        try
        {
            Map<String, Object> payload = new HashMap<>();
            payload.put("model", "gpt-4");
            payload.put("messages", new Object[]{
                    Map.of("role", "user", "content", formatPrompt(ingredient))
            });

            OpenAiResponse response = restClient.post()
                    .body(payload)
                    .retrieve()
                    .body(OpenAiResponse.class);

            return extractCategoryFromResponse(response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException("Failed to categorize ingredient: " + e.getMessage());
        }
    }

    private String formatPrompt(String ingredient)
    {
        return String.format("What category is the ingredient '%s'? (vegan, vegetarian, regular)", ingredient);
    }

    private String extractCategoryFromResponse(OpenAiResponse response)
    {
        if (response.choices() == null || response.choices().length == 0)
            throw new IllegalArgumentException("No choices returned in the response");

        return response.choices()[0].message().content().trim();
    }


}
