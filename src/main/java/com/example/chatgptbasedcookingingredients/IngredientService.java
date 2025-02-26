package com.example.chatgptbasedcookingingredients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

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
}
