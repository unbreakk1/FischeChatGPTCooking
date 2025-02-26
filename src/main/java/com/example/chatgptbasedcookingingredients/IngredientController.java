package com.example.chatgptbasedcookingingredients;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/open")
@RequiredArgsConstructor
public class IngredientController
{
    private final IngredientService ingredientService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> categorizeIngredient(@RequestBody String ingredient)
    {
        String category = ingredientService.categorizeIngredient(ingredient);

        return Map.of("ingredient", ingredient, "category", category);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleException(RuntimeException ex)
    {
        return Map.of("error", ex.getMessage());
    }


}
