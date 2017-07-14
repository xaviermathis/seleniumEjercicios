import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Ejercicios {
	public Ejercicios() {
	}
	public static void main(String[] args) {
		System.setProperty("webdriver.gecko.driver", "geckoDriverFirefox\\geckodriver.exe");
		System.out.println("Comienzo");

		ejercicio1();
		ejercicio2();
		ejercicio3();
		
		System.out.println("Fin");

	}
	
	public static void ejercicio1() {
		System.out.println("Ejercicio 1: ");
		System.out.println("Páginas que aparecen como anuncio:");
		WebDriver driver = new FirefoxDriver();
		String baseURL = "https://www.google.com.ar/webhp#safe=strict&q=";
		String search = "Salesforce+Argentina";
		driver.get(baseURL + search);
    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);//Wait time, to ensure that the site loads properly.

		List<WebElement> ads = getAdsFromSite(driver); //gets all the elements that have "ads-ad" for className
		for (WebElement ad : ads) {
			System.out.println(ad.findElement(By.tagName("cite")).getText());
		}
	}				
		
	public static void ejercicio2() {
		System.out.println("Ejercicio 2: ");
		WebDriver driver = new FirefoxDriver();
		String baseURL = "https://www.google.com.ar/webhp#safe=strict&q=";
		String search = "Salesforce+Argentina";
		driver.get(baseURL + search);
		System.out.println("Páginas argentinas:");
     
        for(int index=1; index<=5; index++){
			System.out.println("Página número: " + String.valueOf(index));		
    		List<WebElement> allResults = getListWElementsFromSite(driver); //gets all search results, ads, and organic.
    		
        	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    		allResults = getListWElementsFromSite(driver);
    		
    		for (WebElement currentWElement : allResults) {
    	        try {
        			if(currentWElement.findElement(By.tagName("cite")).getText().contains(".ar")){
    					System.out.println(currentWElement.findElement(By.tagName("cite")).getText());
        			}	
    	        } catch (org.openqa.selenium.NoSuchElementException e) {
    	            System.out.println(e.getMessage());
    	        }
    	            
    		}
    		//driver.findElement(By.xpath("//a[@aria-label='" + pIndex + "']")).click();
    		
    		String pIndex = "Page " + String.valueOf(index + 1);
    		
    		//finds element with aria-label = "Page n", and gets the site from its href.
    		driver.get(driver.findElement(By.xpath("//a[@aria-label='" + pIndex + "']")).getAttribute("href"));
        }
	}	
	
	public static void ejercicio3() {
        List<WebElement> allResults = new ArrayList<WebElement>();
		System.out.println("Ejercicio 3:");
		WebDriver driver = new FirefoxDriver();
		String baseURL = "https://www.google.com.ar/webhp#safe=strict&q=";
		String search = "Salesforce+Argentina";
		driver.get(baseURL + search);
        for(int index=1; index<=5; index++){
			System.out.println("Página número: " + String.valueOf(index));
    		
        	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);//Tiempo de espera para asegurar carga correcta de la página.

    		allResults = getListWElementsFromSite(driver);
            boolean found = false;
    		for (int i = 0; i < allResults.size(); i++) {
    			String toPrint = "";
    			WebElement currentWElement = allResults.get(i);
    			if(currentWElement.getText().contains("xappia.com")) {
    				found = true;
    				toPrint = "xappia.com aparece en la posición " + String.valueOf(i + 1);
    				if(currentWElement.getClass().getName().equals("ads-ad")){
    					toPrint = toPrint + " y es un anuncio."; 
        				System.out.println(toPrint);
    				}else {
    					toPrint = toPrint + " y es un resultado orgánico."; 
        				System.out.println(toPrint);
    				}
    			}

    		}
    		if (!found) {
				System.out.println("En esta página no aparece xappia.com");
    		}
    		//driver.findElement(By.xpath("//a[@aria-label='" + pIndex + "']")).click();
    		String pIndex = "Page " + String.valueOf(index + 1);

    		driver.get(driver.findElement(By.xpath("//a[@aria-label='" + pIndex + "']")).getAttribute("href"));
        }
	}
	
	public static List<WebElement> getListWElementsFromSite(WebDriver driver) {

        List<WebElement> allResults = new ArrayList<WebElement>();
        List<WebElement> topAds = new ArrayList<WebElement>();
        List<WebElement> organicResults = new ArrayList<WebElement>();
        List<WebElement> bottomAds = new ArrayList<WebElement>();
            
        try {
           	topAds = driver.findElement(By.id("tads")).findElements(By.className("ads-ad"));
        	for(WebElement toppAd : topAds) {
        		allResults.add(toppAd);
        	}
        		
        } catch (org.openqa.selenium.NoSuchElementException e) {
            //System.out.println("No top ads in this page."); //uncommented if you would like to print if there are top ads
            //System.out.println(e);
        }
            
    	organicResults = driver.findElements(By.className("g"));
    	
    	for(WebElement searchResult : organicResults) {
    		allResults.add(searchResult);
    	}
    		
        try {
        	bottomAds = driver.findElement(By.id("tadsb")).findElements(By.className("ads-ad"));
        	for(WebElement bottomAd : bottomAds) {
        		allResults.add(bottomAd);
        	}
        } catch (org.openqa.selenium.NoSuchElementException e) {
            //System.out.println("No bottom ads in this page.");//uncommented if you would like to print if there are bottom ads
            //System.out.println(e);
        }
    	return allResults;
    }
	
	public static List<WebElement> getAdsFromSite(WebDriver driver) {

        List<WebElement> ads = new ArrayList<WebElement>();
            
        try {
        	ads = driver.findElements(By.className("ads-ad"));
        }
        finally{}

    	return ads;

	}
	
}
	

