package guru.springframework.controllers;

import guru.springframework.domain.Category;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class IndexController {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    private final RecipeService recipeService;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository,
                           RecipeService recipeService) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/"})
    public String getIndexPage(Model model) {
        log.debug("Getting index page");

        Optional<Category> categoryOptional = categoryRepository.findByDescription("Taco");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByUom("Milliliter");

        List<Recipe> recipes = recipeService.findAll();

        model.addAttribute("recipes", recipes);

        if(categoryOptional.isPresent()) {
            System.out.println("Cat Id is: " + categoryOptional.get().getId());
        }

        if(unitOfMeasureOptional.isPresent()) {
            System.out.println("UOM ID is: " + unitOfMeasureOptional.get().getId());
        }

        return "index";
    }
}
