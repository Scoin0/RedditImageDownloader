package scoin0.RedditImageDownloader;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * This is just a test area and doesn't really do anything.
 *
 */
public class App 
{
	public static String[] URL = {"https://www.reddit.com/r/awwnime/"};
	static Logger log;
	
	public static void urlCheck() {
		for(int i = 0; i < URL.length; i++) {
			System.out.println(URL[i]);
		}
	}
	
	public static void checkWeb() {
		for(int k = 0; k < URL.length; k++) {
			System.out.println("Now Looking through: " + URL[k]);
			Document doc;
			try {
				doc = Jsoup.connect(URL[k]).get();
			} catch (IOException e) {
				continue;
			}
			for(Element i : doc.select("a.title.may-blank.outbound")) {
				System.out.println(i.attr("href"));
				try {
					getImage(i.attr("href"));
				} catch (IOException e) {
					continue;
				}
			}	
		}
	}
	
	public static void getImage(String src) throws IOException {
		
		String folder = null;
		
		int indexName = src.lastIndexOf("/");
		
		if(indexName == src.length()) {
			src = src.substring(1, indexName);
		}
		
		
		indexName = src.lastIndexOf("/");
		String name = src.substring(indexName, src.length());
		name = name.replace("?1", "");
		
		System.out.println(name);
		
		URL url = new URL(src);
		InputStream input = url.openStream();
		OutputStream output = new BufferedOutputStream(new FileOutputStream("C:/Test" +  name));
		
		for(int i; (i = input.read()) != -1;) {
			output.write(i);
		}
		
		output.close();
		input.close();

	}
	
	public static void main(String[] args) throws Exception{
		//checkWeb();
	}
}
