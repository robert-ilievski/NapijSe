package mk.napijse;

import mk.napijse.model.entities.Category;
import mk.napijse.model.entities.Comment;
import mk.napijse.model.entities.Recipe;
import mk.napijse.model.entities.User;
import mk.napijse.model.enumerations.Role;
import mk.napijse.service.CategoryService;
import mk.napijse.service.CommentService;
import mk.napijse.service.RecipeService;
import mk.napijse.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class NapijseApplicationTests {
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    RecipeService recipeService;

    @Autowired
    CommentService commentService;

    private static User u1;
    private static User u2;
    private static Category c1;
    private static Recipe r1;
    private static Comment comment1;
    private static boolean dataInitialized = false;

    @BeforeEach
    public void setup(WebApplicationContext wac){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        initData();
    }

    private void initData() {
        if (!dataInitialized) {
            c1 = categoryService.create("c1", "c1");
            categoryService.create("c2", "c2");

            u1 = userService.getUserById("user@mail.com");
            u2 = userService.getUserById("admin@admin.com");
//            String user = "user";
//            String admin = "admin";
//            u1 = userService.register(user+"1", user+"123!23", user+"123!23", user+"@mail1.com", user, user, Role.ROLE_USER);
//            u2 = userService.register(admin+"1", admin+"123!23", admin+"123!23", admin+"@mail1.com", admin, admin, Role.ROLE_ADMIN);

            r1 = new Recipe("name", "description", "ingredients",
                    u1, c1);
            recipeService.saveRecipe("name", "description", "ingredients",
                    (long)1, "user");

            dataInitialized = true;
        }
    }

    @Test
    void contextLoads() {
    }
    @Test
    public void testGetRecipes() throws Exception {
        MockHttpServletRequestBuilder recipeRequest = MockMvcRequestBuilders.get("/recipes");
        this.mockMvc.perform(recipeRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipes"))
                .andExpect(MockMvcResultMatchers.model().attribute("bodyContent", "recipes"))
                .andExpect(MockMvcResultMatchers.view().name("master-template"));

    }

}
