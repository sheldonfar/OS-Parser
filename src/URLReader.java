import java.util.*;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;


public class URLReader {
	private String shop_url = "http://www.newegg.com/All-Laptops-Notebooks/SubCategory/ID-32/Page-";
	private int desired_count = 100;
	
    public void parseURL() throws Exception {
    	System.out.println("------------------------Starting parsing data from website!-------------------------------");  
    	int found_items = 0, page_count = 1;
    	Elements product_name = new Elements();
    	Elements product_price = new Elements();
    	Elements product_desc = new Elements();
    	
    	
    	ArrayList<ShopItem> result = new ArrayList<ShopItem>();
    	int i;
    	while(found_items <= desired_count) {
    		Document page = Jsoup.connect(shop_url + page_count).timeout(10*1000).get();
        	product_name = page.select("span.itemDescription[style=display:inline]");
        	product_price = page.select("li.price-current");
        	
        	String n[] = new String[product_name.size()];
        	String p[] = new String[product_price.size()];
        	String h[] = new String[product_name.size()];
        	
        	i = 0;
        	for(Element el : product_name){
        	    h[i] = el.parent().attr("href");
        		n[i++] = el.text().replace("\"", "");
        	    
        	}
        	i = 0;
        	for(Element el : product_price){
        	    p[i] = el.text().replace(" –", "");    
        	    if(p[i].isEmpty()) p[i] = "|NOT AVAILABLE|";
        	    i++;
        	}
        	i = 0;	
        	
        	for(int k = 0; k < n.length; k++) {
        		ArrayList<String> description = new ArrayList<String>();
        		Document product_page = Jsoup.connect(h[k]).timeout(10*1000).get();
        		product_desc = product_page.select("div.grpBullet ul.itemColumn > li.item");
        		
        		for(Element el : product_desc){
        			description.add(el.text());
        		}
        		result.add(new ShopItem(n[k], p[k], description, h[k]));
        	}
        	
        	page_count++;
        	found_items += product_name.size();
    	}
    	System.out.println("------------------------Data parsed successfully!-----------------------------------------");  
    	DataBase db = new DataBase();
    	db.addDataToDB(result);
    }
}