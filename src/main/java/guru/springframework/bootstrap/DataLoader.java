package guru.springframework.bootstrap;

import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.services.RecipeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;

@Component
public class DataLoader implements CommandLineRunner {

    private final RecipeService recipeService;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final CategoryRepository categoryRepository;

    public DataLoader(RecipeService recipeService, UnitOfMeasureRepository unitOfMeasureRepository, CategoryRepository categoryRepository) {
        this.recipeService = recipeService;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // i chose a different recipe than the one given on the course.
        // information on the recipe you see here is pulled directly from:
        // https://www.simplyrecipes.com/cajun-spiced-grilled-corn-recipe-6361297
        Recipe cajunSpiceGrilledCorn = new Recipe();
        cajunSpiceGrilledCorn.setDescription("Cajun Spice Grilled Corn");
        cajunSpiceGrilledCorn.setDifficulty(Difficulty.EASY);
        cajunSpiceGrilledCorn.setCookTime(25);
        cajunSpiceGrilledCorn.setIngredients(new HashSet<>());
        cajunSpiceGrilledCorn.setSource("Louisiana");
        cajunSpiceGrilledCorn.setServings(8);
        cajunSpiceGrilledCorn.setCategories(new HashSet<>());
        cajunSpiceGrilledCorn.getCategories().add(categoryRepository.findByDescription("Vegetables").get());

        Ingredient saltedButter = new Ingredient();
        saltedButter.setAmount(new BigDecimal("8"));
        saltedButter.setUnitOfMeasure(unitOfMeasureRepository.findByUom("Tablespoon").get());
        saltedButter.setDescription("Salted butter");
        cajunSpiceGrilledCorn.getIngredients().add(saltedButter);

        Ingredient earsOfCorn = new Ingredient();
        earsOfCorn.setAmount(new BigDecimal("8"));
        earsOfCorn.setUnitOfMeasure(unitOfMeasureRepository.findByUom("Count").get());
        earsOfCorn.setDescription("Ears of fresh corn");
        cajunSpiceGrilledCorn.getIngredients().add(earsOfCorn);

        Ingredient cajunSeasoning = new Ingredient();
        cajunSeasoning.setAmount(new BigDecimal("1"));
        cajunSeasoning.setUnitOfMeasure(unitOfMeasureRepository.findByUom("Tablespoon").get());
        cajunSeasoning.setDescription("Cajun seasoning");
        cajunSpiceGrilledCorn.getIngredients().add(cajunSeasoning);

        Ingredient cotijaCheese = new Ingredient();
        cotijaCheese.setAmount(new BigDecimal("0.25"));
        cotijaCheese.setUnitOfMeasure(unitOfMeasureRepository.findByUom("Cup").get());
        cotijaCheese.setDescription("Cotija cheese");
        cajunSpiceGrilledCorn.getIngredients().add(cotijaCheese);

        Ingredient finelyChoppedCilantro = new Ingredient();
        finelyChoppedCilantro.setAmount(new BigDecimal("0.25"));
        finelyChoppedCilantro.setUnitOfMeasure(unitOfMeasureRepository.findByUom("Cup").get());
        finelyChoppedCilantro.setDescription("Finely chopped cilantro");
        cajunSpiceGrilledCorn.getIngredients().add(finelyChoppedCilantro);

        cajunSpiceGrilledCorn.setDirections("Note, this is an abridged version of the actual recipe.\n" +
                "Preheat the grill to high, making sure the temperature reaches about 550℉.\n" +
                "Meanwhile, melt the butter.\n" +
                "Baste the corn with butter.\n" +
                "Season the corn.\n" +
                "Grill the corn.\n" +
                "Baste the corn with more butter, garnish, and serve.");

        // qwer Possible point of problem: not saving every ingredient to a repository
        recipeService.save(cajunSpiceGrilledCorn);

        System.out.println("created recipe");
    }
}
