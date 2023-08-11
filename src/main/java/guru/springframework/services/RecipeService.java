package guru.springframework.services;

import guru.springframework.domain.Recipe;

import java.util.List;


public interface RecipeService {
    public List<Recipe> findAll();
    public Recipe save(Recipe recipe);
}
