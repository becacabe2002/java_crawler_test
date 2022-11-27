import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Crawler{
    public static void main(String[] args){
        String url = "https://www.youtube.com/watch?v=0osGFFNTEas";
        crawl(1, url, new ArrayList<String>());
        
    }
    /**
     * Crawler
     * @param level - the number of layers crawler will visit
     * @param url - 
     * @param visited - keep track visited websites
     */
    public static void crawl(int level, String url, ArrayList<String> visited) {
        if(level <=5){
        	/**
        	 * Document extends Element
        	 * a HTML Document
        	 */
            Document doc = request(url, visited);
            
            if (doc != null){
            	// Element 	- consists of HTML elements
                for (Element link: doc.select("a[href]")){//find tag <a> with href attr in the doc
                    String next_link = link.absUrl("href");// get an absolute URL from a[href]
                    if (visited.contains(next_link)== false){
                        crawl(level++, next_link, visited);// crawler follows the link to a deeper level
                    }
                }
            }
        }
    }
    /**
     * 
     * @param url
     * @param v
     * @return Document that have
     */
    private static Document request(String url, ArrayList<String> v){
        try {
        	/**
        	 * Connection inferface - a HTTP client and session obj
        	 * to fetch content from the web
        	 * and parse them into Document
        	 */
            Connection con = Jsoup.connect(url);// create a new connection
            Document doc = con.get();// execute GET, the parse the result

            if(con.response().statusCode() == 200){ // status code for successful request
                System.out.println("Link:" + url);
                System.out.println(doc.title());
                v.add(url);// add url to ArrayList
                return doc;
            }
            return null;
        }
        catch(IOException e){
            return null;
        }
    }
}
