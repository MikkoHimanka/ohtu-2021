package ohtu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Tester {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:4567");
        
        sleep(2);

//        Onnistunut kirjautuminen
//        WebElement element = driver.findElement(By.linkText("login"));
//        element.click();
//
//        sleep(2);
//
//        element = driver.findElement(By.name("username"));
//        element.sendKeys("pekka");
//        element = driver.findElement(By.name("password"));
//        element.sendKeys("akkep");
//        element = driver.findElement(By.name("login"));
//
//        sleep(2);
//        element.submit();
//
//        sleep(3);

//        //Epäonnistunut kirjautuminen
//        WebElement element = driver.findElement(By.linkText("login"));
//        element.click();
//
//        sleep(2);
//
//        element = driver.findElement(By.name("username"));
//        element.sendKeys("pekka");
//        element = driver.findElement(By.name("password"));
//        element.sendKeys("wrong");
//        element = driver.findElement(By.name("login"));
//
//        sleep(2);
//        element.submit();
//
//        sleep(3);

//        //Uuden tunnuksen luominen
//        WebElement element = driver.findElement(By.linkText("register new user"));
//        element.click();
//
//        sleep(2);
//
//        element = driver.findElement(By.name("username"));
//        element.sendKeys("uusiukko");
//        element = driver.findElement(By.name("password"));
//        element.sendKeys("salainen2");
//        element = driver.findElement(By.name("passwordConfirmation"));
//        element.sendKeys("salainen2");
//        element = driver.findElement(By.name("signup"));
//
//        sleep(2);
//        element.submit();
//        sleep(3);

        //uuden käyttäjätunnuksen luomisen jälkeen tapahtuva ulkoskirjautuminen sovelluksesta
        WebElement element = driver.findElement(By.linkText("register new user"));
        element.click();

        sleep(2);

        element = driver.findElement(By.name("username"));
        element.sendKeys("uusiukkokakkonen");
        element = driver.findElement(By.name("password"));
        element.sendKeys("salainen2");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("salainen2");
        element = driver.findElement(By.name("signup"));

        sleep(2);
        element.submit();
        sleep(1);
        element = driver.findElement(By.linkText("continue to application mainpage"));
        sleep(1);
        element.click();
        sleep(1);
        element = driver.findElement(By.linkText("logout"));
        sleep(1);
        element.click();

        sleep(3);
        driver.quit();
    }
    
    private static void sleep(int n){
        try{
            Thread.sleep(n*1000);
        } catch(Exception e){}
    }
}
