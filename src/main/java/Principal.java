import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Principal {
    public static WebDriver driver;
    public static ArrayList<String> list = new ArrayList<>();
    public static String url = "https://www.infomoney.com.br/mercados/";
    public static Integer load = 2;
    public static Integer numPage = 1;
    public static String xPathTitle = "page-title-1";
    public static String xPathSubTitle = "article-lead";
    public static String xPathAuthor = "author-name";
    public static String xPathContent = "col-md-9";
    public static String xPathDate = "entry-date";
    public static Integer number = 1;

    public static void main(String[] args) {
        Acoes req = new Acoes();
        // set chrome driver
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        // set the cookies
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.cookies", 2);
        prefs.put("network.cookie.cookieBehavior", 2);
        prefs.put("profile.block_third_party_cookies", 1);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(url);
        Acoes.holdTime();
        // Carregando as 3 páginas
        while (load < 4) {
            req.loadMore();
        }
        load = 1;
        // Salvando as notícias das 3 páginas em um ArrayList
        while (load < 3) {
            req.saveNews();
        }
        numPage = 0;
        System.out.println("Espere até o programa terminar...");
        // Gravando as notícias em um arquivo externo
        req.saveFile();
        // Concluindo...
        driver.quit();
        PrintStream consoleStream = new PrintStream(new FileOutputStream(FileDescriptor.out));
        System.setOut(consoleStream);
        System.out.println("Concluído, verificar o arquivo news.txt na raiz do projeto.");
    }
}
