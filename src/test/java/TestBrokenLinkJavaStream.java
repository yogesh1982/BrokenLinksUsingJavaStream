//import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.net.HttpURLConnection;
import java.net.URL;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestBrokenLinkJavaStream {


    @Test
    void setup() {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://www.hdfcbank.com/");

        //Storing the links in a list and traversing through the links
        List<WebElement> links = driver.findElements(By.tagName("a"));

        // This line will print the number of links and the count of links.
        System.out.println("No of links are " + links.size());

        List<String> urlList = new ArrayList<String>();

        // Using for each loop to travers and get the elements of the list

        for (WebElement e : links) {
            String url = e.getAttribute("href");
            urlList.add(url); // Adding the urls to the list and
//            verifyLinks(url);  // Passing the captured url in loop to the verify method and checking
        }

           urlList.parallelStream().forEach(e -> verifyLinks(e));

    }

        public static void verifyLinks(String linkUrl)
        {
            try
            {
                URL url = new URL(linkUrl);

                //Now we will be creating url connection and getting the response code
                HttpURLConnection httpURLConnect=(HttpURLConnection)url.openConnection();
                httpURLConnect.setConnectTimeout(5000);
                httpURLConnect.connect();
                if(httpURLConnect.getResponseCode()>=400)
                {
                    System.out.println(linkUrl+" - "+httpURLConnect.getResponseMessage()+"is a broken link");
                }

                //Fetching and Printing the response code obtained
                else{
                    System.out.println(linkUrl+" - "+httpURLConnect.getResponseMessage());
                }
            }catch (Exception e) {
            }

    }

}
