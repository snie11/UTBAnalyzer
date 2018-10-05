package netsFinal;

import org.jsoup.Jsoup;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.regex.Matcher;

public class ArticleFinder {
  
	public static Article scrape(String baseUrl) throws IOException {
		Document doc = Jsoup.connect(baseUrl).get();
		String articleText = "";
		String authorName = "";
		try {
			Elements article = doc.select("article");
			Elements text = article.select("p");
			for (Element a : text) {
				if (!a.text().contains("Photo")) {
					articleText += a.text();		
				}
			}
			Elements author = article.select("aside").select("a");
			for (Element a : author) {
				authorName = a.text();
			}
			
//			System.out.println(articleText);
//			System.out.println();
		} catch (Exception r) {
			System.out.println("error");
		}
		return new Article(articleText, authorName);

	}

}
