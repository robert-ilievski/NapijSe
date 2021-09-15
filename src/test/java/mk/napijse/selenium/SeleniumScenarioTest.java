package mk.napijse.selenium;

import mk.napijse.model.entities.Category;
import mk.napijse.model.entities.Comment;
import mk.napijse.model.entities.Recipe;
import mk.napijse.model.entities.User;
import mk.napijse.service.CategoryService;
import mk.napijse.service.CommentService;
import mk.napijse.service.RecipeService;
import mk.napijse.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumScenarioTest {
    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    RecipeService recipeService;

    @Autowired
    CommentService commentService;

    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    private static User user;
    private static User admin;
    private static Category c1;
    private static Category c2;
    private static Recipe r1;
    private static Recipe r2;
    private static Comment comment1;
    private static boolean dataInitialized = false;

    @BeforeEach
    public void setup(){
        driver = getDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        initData();
    }

    private WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", "/Users/goras/chromedriver.exe");
        return new ChromeDriver();
    }

    @AfterEach
    public void destroy(){
        if(this.driver != null){
            this.driver.close();
        }
    }

    private void initData() {
        if (!dataInitialized) {
            c1 = categoryService.create("c1", "c1");
            c2 = categoryService.create("c2", "c2");

            user = userService.getUserById("user@mail.com");
            admin = userService.getUserById("admin@admin.com");
            r1 = new Recipe("Recipe1", "description", "ingredients",
                    user, c1);
            recipeService.saveRecipe("Recipe1", "description", "ingredients",
                    (long)1, "user");
            r2 = new Recipe("Recipe2", "description", "ingredients",
                    user, c1);
            recipeService.saveRecipe("Recipe2", "description", "ingredients",
                    (long)1, "user");

            comment1 = commentService.save(r1.getId(), user.getUsername(), "content");

            dataInitialized = true;
        }
    }

    @Test
    public void fullPageTest() {
        driver.get("http://localhost:8080/");
        driver.manage().window().setSize(new Dimension(974, 1040));
        driver.findElement(By.linkText("Најава")).click();
        driver.findElement(By.id("username")).sendKeys("gorast.angelovski");
        driver.findElement(By.id("password")).sendKeys("ga");
        driver.findElement(By.id("username")).click();
        driver.findElement(By.id("username")).click();
        {
            WebElement element = driver.findElement(By.id("username"));
            Actions builder = new Actions(driver);
            builder.doubleClick(element).perform();
        }
        driver.findElement(By.id("username")).click();
        driver.findElement(By.id("username")).sendKeys("user");
        driver.findElement(By.id("password")).sendKeys("user");
        driver.findElement(By.cssSelector(".fourth")).click();
        driver.findElement(By.linkText("Рецепти")).click();
        driver.findElement(By.linkText("2")).click();
        driver.findElement(By.linkText("3")).click();
        driver.findElement(By.id("recipeName")).click();
        driver.findElement(By.id("recipeName")).sendKeys("smuti");
        driver.findElement(By.cssSelector(".add-recipe:nth-child(5)")).click();
        driver.findElement(By.id("recipeName")).sendKeys("смути");
        driver.findElement(By.cssSelector(".add-recipe:nth-child(5)")).click();
        driver.findElement(By.cssSelector(".column:nth-child(2) form:nth-child(1) > .add-to-cart-btn")).click();
        driver.findElement(By.id("content")).click();
        driver.findElement(By.id("content")).sendKeys("добро смути :)");
        driver.findElement(By.cssSelector(".add-recipe:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".edit-balloon-btn")).click();
        driver.findElement(By.id("content")).click();
        driver.findElement(By.id("content")).click();
        {
            WebElement element = driver.findElement(By.id("content"));
            Actions builder = new Actions(driver);
            builder.doubleClick(element).perform();
        }
        driver.findElement(By.id("content")).sendKeys("ЛОшо смути :(");
        driver.findElement(By.id("submit")).click();
        driver.findElement(By.cssSelector(".delete-balloon-btn")).click();
        driver.findElement(By.linkText("Рецепти")).click();
        driver.findElement(By.cssSelector(".add-recipe:nth-child(1)")).click();
        driver.findElement(By.id("name")).click();
        driver.findElement(By.id("name")).sendKeys("Рецепта");
        driver.findElement(By.id("description")).sendKeys("Сечи, мачкај, јади");
        driver.findElement(By.id("ingredients")).sendKeys("леб, нутела");
        driver.findElement(By.id("submit")).click();
        driver.findElement(By.cssSelector(".column:nth-child(2) form:nth-child(2) > .add-to-cart-btn")).click();
        driver.findElement(By.linkText("Мои Рецепти")).click();
        driver.findElement(By.cssSelector(".column:nth-child(2) .delete-balloon-btn")).click();
        driver.findElement(By.linkText("Мои Рецепти")).click();
        driver.findElement(By.cssSelector(".column:nth-child(1) .edit-balloon-btn")).click();
        driver.findElement(By.cssSelector(".form-group:nth-child(2)")).click();
        driver.findElement(By.id("description")).sendKeys("нова рецепта");
        driver.findElement(By.id("submit")).click();
        driver.findElement(By.linkText("Омилени")).click();
        driver.findElement(By.cssSelector(".column:nth-child(2) .delete-from-favorites")).click();
        driver.findElement(By.linkText("Одјави се")).click();
        driver.findElement(By.id("username")).sendKeys("gorast.angelovski");
        driver.findElement(By.id("password")).sendKeys("ga");
        driver.findElement(By.id("username")).click();
        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("admin");
        driver.findElement(By.cssSelector(".fourth")).click();
        driver.findElement(By.id("username")).sendKeys("gorast.angelovski");
        driver.findElement(By.id("password")).sendKeys("ga");
        driver.findElement(By.cssSelector(".fourth")).click();
        driver.findElement(By.linkText("Рецепти")).click();
        driver.findElement(By.cssSelector(".column:nth-child(1) form:nth-child(1) > .add-to-cart-btn")).click();
        driver.findElement(By.cssSelector("tr:nth-child(3) .delete-balloon-btn")).click();
        driver.findElement(By.cssSelector("tr:nth-child(2) .delete-balloon-btn")).click();
        driver.findElement(By.cssSelector(".delete-balloon-btn")).click();
        driver.findElement(By.linkText("ADMIN")).click();
        driver.findElement(By.cssSelector(".row:nth-child(3) .admin-btn")).click();
        driver.findElement(By.cssSelector("tr:nth-child(3) .admin-btn")).click();
        driver.findElement(By.cssSelector("div:nth-child(7) .center")).click();
        driver.findElement(By.linkText("Одјави се")).click();
        driver.findElement(By.id("username")).sendKeys("gorast.angelovski");
        driver.findElement(By.id("password")).sendKeys("ga");
    }
}
