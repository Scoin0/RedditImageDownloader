package scoin0.RedditImageDownloader;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Downloader {
	
	public static Logger log = LoggerFactory.getLogger("Downloader");
	public static Scanner scanner = new Scanner(System.in);
	public static Scanner cscanner = new Scanner(System.in);
	public static List<String> URL = new ArrayList<String>();
	public static String tempURL;
	public static int choice;
	public static boolean isChecked = false;
	
	public static void start() {
		
		System.out.println(getSystemMenu());
		System.out.print(">");
		choice = cscanner.nextInt();
		while(true) {
			if(choice == 1) {
				try {
					System.out.print("Type the Url: ");
					insertURL(scanner.nextLine());
					isChecked = false;
				} catch (IOException e) {
					log.error("Exception: ");
					e.printStackTrace();
				}
				
				System.out.print("> ");
				choice = cscanner.nextInt();
				
			} else if(choice == 2) {
				try {
					readFromFile();
					checkWeb();
					URL.clear();
				} catch (FileNotFoundException e) {
					log.warn("File Not Found at " + Launcher.subImages);
					e.printStackTrace();
				}	
				
				System.out.print(">");
				choice = cscanner.nextInt();
				
			} else if(choice == 3) {
				
				readFromFile();

				String[] tempArray = URL.toArray(new String[0]);
				log.info("Here's whats in the file.");
				for(int k = 0; k < URL.size(); k++) {
					log.info(tempArray[k]);
				}
				
				URL.clear();
				choice = cscanner.nextInt();
				
			} else {
				log.warn("Invalid Choice. Please try again.");
				System.out.print(">");
				choice = cscanner.nextInt();
			}
		}
	}
	
	public static void insertURL(String url) throws IOException {
		File file = new File(Launcher.subURL);
		FileWriter writer = new FileWriter(file, true);
		BufferedWriter bW = new BufferedWriter(writer);
		bW.write(url);
		bW.newLine();
		bW.close();
	}
	
	
	public static void checkWeb() throws FileNotFoundException {
		
		String[] tempArray = URL.toArray(new String[0]);
		log.info("There's " + URL.size() + " URLS");
		for(int k = 0; k < URL.size(); k++) {
			log.info("Now Looking through: " + tempArray[k]);
			Document doc;
			try {
				doc = Jsoup.connect(tempArray[k]).get();
			} catch (IOException e) {
				continue;
			}
			for(Element i : doc.select("a.title.may-blank.outbound")) {
				log.info(i.attr("href"));
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
		
		log.info("Filename: " + name);
		
		URL url = new URL(src);
		InputStream input = url.openStream();
		OutputStream output = new BufferedOutputStream(new FileOutputStream(Launcher.subImages +  name));
		
		for(int i; (i = input.read()) != -1;) {
			output.write(i);
		}
		
		output.close();
		input.close();

	}
	
	public static void readFromFile() {
		
		try {
			Scanner readFile = new Scanner(new File(Launcher.subURL));
			while(readFile.hasNext()) {
				tempURL = readFile.next();
				URL.add(tempURL);
			}

			readFile.close();
			
		} catch (FileNotFoundException e) {
			log.error("File Not Found!");
		}
	}
	
	private static String getSystemMenu() {
		return 	"  __  __                        \r\n" + 
				" |  \\/  |                       \r\n" + 
				" | \\  / |   ___   _ __    _   _ \r\n" + 
				" | |\\/| |  / _ \\ | '_ \\  | | | |\r\n" + 
				" | |  | | |  __/ | | | | | |_| |\r\n" + 
				" |_|  |_|  \\___| |_| |_|  \\__,_|\r\n" + 
				"                               \n" +
				"1| Insert URLs\n" +
				"2| Download Images\n" +
				"3| Check Stored URLS\n";
	}

}
