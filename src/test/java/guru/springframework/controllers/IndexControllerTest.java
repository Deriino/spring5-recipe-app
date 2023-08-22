package guru.springframework.controllers;

import guru.springframework.domain.Category;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest {

    IndexController indexController;

    @Mock
    RecipeService recipeService;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Mock
    Model model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        indexController = new IndexController(categoryRepository, unitOfMeasureRepository, recipeService);

    }

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }


    @Test
    public void getIndexPage() {

        // eq wasn't taught yet;
        // i happen to know it anyway from prior test experience
        // and apparently i forgot to update IndexController, so its use is required here
        Category tacoCategory = new Category();
        when(categoryRepository.findByDescription(eq("Taco"))).thenReturn(Optional.of(tacoCategory));

        UnitOfMeasure milliliterUnit = new UnitOfMeasure();
        when(unitOfMeasureRepository.findByUom(eq("Milliliter"))).thenReturn(Optional.of(milliliterUnit));

        ArrayList<Recipe> emptyRecipes = new ArrayList<>();
        emptyRecipes.add(new Recipe());
        emptyRecipes.add(new Recipe());
        when(recipeService.findAll()).thenReturn(emptyRecipes);

        ArgumentCaptor<List<Recipe>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        // not using it; don't care
        when(model.addAttribute(eq("recipes"), eq(emptyRecipes))).thenReturn(null);

        String ret = indexController.getIndexPage(model);
        assertEquals("index", ret);

        // this is called exactly once -- oh and by the way, grab whatever was used here.
        // ok so where there are these specifiers in when/verify statements, there can be argument captors
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());

        List<Recipe> listInController = argumentCaptor.getValue();
        assertEquals(2, listInController.size());
    }
}