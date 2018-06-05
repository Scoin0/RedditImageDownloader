package scoin0.RedditImageDownloader;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Launcher {
	
	public static final String subImages = "Images";
	public static final String subURL = "redditurls.txt";
	public static final Logger log = LoggerFactory.getLogger("Launcher");
	
	public static void main(String[] args) throws IOException {
		System.out.println(getLogo());
		createStorageFile();
		createURLFile();
		
		Downloader.start();
		
	}
	
	public static void createStorageFile() {
		File file = new File(subImages);
		if(!file.exists()) {
			log.warn("Storage File does not exist. Creating...");
			file.mkdirs();
		} else {
			log.info("Storage file found.");
			log.info("Storage path at " + file.getAbsolutePath());
		}
	}
	
	public static void createURLFile() {
		File file = new File(subURL);
		if(!file.exists()) {
			log.warn("URL File does not exist. Creating...");
			try {
				file.createNewFile();
			} catch (IOException e) {
				log.warn("Something unexpected happened. Details below:");
				e.printStackTrace();
			}
		} else {
			log.info("URL File found.");
			log.info("Storage path at " + file.getAbsolutePath());
		}
	}
	
	private static String getLogo() {
		return 	"	    __          _     _ _ _      _____                                 ___                    _                 _           \r\n" 			+ 
				"	   /__\\ ___  __| | __| (_) |_    \\_   \\_ __ ___   __ _  __ _  ___     /   \\_____      ___ __ | | ___   __ _  __| | ___ _ __ \r\n" 		+ 
				"	  / \\/// _ \\/ _` |/ _` | | __|    / /\\/ '_ ` _ \\ / _` |/ _` |/ _ \\   / /\\ / _ \\ \\ /\\ / / '_ \\| |/ _ \\ / _` |/ _` |/ _ \\ '__|\r\n" + 
				"	 / _  \\  __/ (_| | (_| | | |_  /\\/ /_ | | | | | | (_| | (_| |  __/  / /_// (_) \\ V  V /| | | | | (_) | (_| | (_| |  __/ |   \r\n" 		+ 
				"	 \\/ \\_/\\___|\\__,_|\\__,_|_|\\__| \\____/ |_| |_| |_|\\__,_|\\__, |\\___| /___,' \\___/ \\_/\\_/ |_| |_|_|\\___/ \\__,_|\\__,_|\\___|_| \n";
	}

}
