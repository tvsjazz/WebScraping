import org.openqa.selenium.By;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Acoes extends Principal {

    public void loadMore() {
        try {
            Acoes.holdTime();
            driver.findElement(By.xpath("//button[contains(text(),'Carregar mais')]")).click();
            System.out.println("Página " + load + " carregada");
            load = load + 1;
        } catch (Exception e) {
            System.out.println("Tentando encontrar o botão...");
        }
    }

    public void getNews() {
        if (numPage > list.size() - 1) {
            driver.quit();
        }
        try {
            Acoes.holdTime();
            System.out.println("Notícia " + number + ":");
            System.out.println("URL: \n " + list.get(numPage) + "\n");
            Acoes.holdTime();
            driver.get(list.get(numPage));
            Acoes.holdTime();
            // Título
            String title = driver.findElement(By.className(xPathTitle)).getText();
            System.out.println("Título: \n " + title + "\n");
            Acoes.holdTime();
            // Subtítulo
            String subtitle = driver.findElement(By.className(xPathSubTitle)).getText();
            System.out.println("Subtítulo: \n " + subtitle + "\n");
            Acoes.holdTime();
            // Autor
            String author = driver.findElement(By.className(xPathAuthor)).getText();
            author = author.substring(4, author.length());
            System.out.println("Autor: \n " + author + "\n");
            Acoes.holdTime();
            // Data
            String datePublish = driver.findElement(By.className(xPathDate)).getAttribute("datetime");
            SimpleDateFormat sdfOriginal = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            Date minhaDataEmDate = sdfOriginal.parse(datePublish);
            SimpleDateFormat sdfFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String dateFormated = sdfFormat.format(minhaDataEmDate);
            System.out.println("Data: \n " + dateFormated + "\n");
            Acoes.holdTime();
            // Conteúdo
            String content = driver.findElement(By.className(xPathContent)).getText();
            System.out.println("Conteúdo: \n " + content);
            Acoes.holdTime();
            number = number + 1;
            System.out.println("\n_______________________________________________________\n");
            numPage = numPage + 1;
        } catch (Exception e2) {
            System.out.println("Elemento indisponível na página");
            numPage = numPage +1;
            number = number + 1;
        }
    }

    public void saveNews() {
        try {
            Acoes.holdTime();
            String urlXpath = driver
                    .findElement(By.xpath(
                            "/html/body/div[4]/div[4]/div/div/div[" +  numPage + "]/div[2]/span[2]/a"))
                    .getAttribute("href");
            list.add(urlXpath);
            System.out.println("Noticia " + numPage + " salva");
            numPage = numPage + 1;
        }
        catch(Exception e) {
            System.out.println("Tentando salvar a notícia " + numPage + "...");
            load = load +1;
        }
    }

    public void saveFile() {
        System.out.println("Gravando em arquivo...");
        File file = new File("news.txt");
        PrintStream printStream = null;
        try {
            printStream = new PrintStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.setOut(printStream);
        while (numPage <= list.size() - 1) {
            getNews();
        }
    }

    public static void holdTime() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
}
