package netsFinal;

import org.jsoup.Jsoup;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.regex.Matcher;
import java.net.URL;
import java.net.URLConnection;

public class Time {

	String fbPostText = "";
	String baseUrl;
	Elements url;

	public Time() throws IOException {

		useCSVFile();

	}

	public Article getArticleFromLink(String baseUrl) throws IOException {
		Document doc = Jsoup.connect(baseUrl).get();
		Pattern link = Pattern.compile("u=https.*buff.*", Pattern.DOTALL);
		Pattern dpLink = Pattern.compile("thedp.com.*", Pattern.DOTALL);
		Matcher linkMatch = null; 
		Matcher dpMatch = null; 
		String dpFormat = "";
		Article a = null;
		Elements fbPost = doc.select("div").attr("class", "_6m3 _--6");
		
		for (Element e : fbPost) {
//			System.out.println(e.toString());
			linkMatch = link.matcher(e.toString());
			dpMatch = dpLink.matcher(e.toString());
			if (linkMatch.find()) {
				dpFormat = linkMatch.group();
				dpFormat = dpFormat.substring(2);
				dpFormat = formatLink(dpFormat);

			}
			else if (dpMatch.find()) {
				 dpFormat = "http://www."+dpMatch.group();
				 dpFormat = formatLink(dpFormat);
				
			}
		}
		System.out.println(dpFormat);
		return ArticleFinder.scrape(dpFormat);
		
	}
	
	public String formatLink(String dpFormat) {
		try {
			dpFormat = dpFormat.substring(0,dpFormat.indexOf("&"));
			 String link1 = URLDecoder.decode(dpFormat, "UTF-8");
			 if(link1.contains("Exception")) {
				 link1 = link1.substring(0, link1.indexOf("Exception"));
			 }
			return (link1);
	
		} catch (Exception r) {
			System.out.println("error");
			return "error";
		}
	}

	public void useCSVFile() throws IOException {
		HashSet<String> links = new HashSet<>();
		HashSet<Article> articles = new HashSet<>();
		
		String csvFile = "utbData.csv";
		String line = "";
		int i = 0;
			
		try {
			BufferedReader br = new BufferedReader(new FileReader(csvFile));

			while ((line = br.readLine()) != null) { 
				String[] row = line.split(",");
//				if(row[0].length() > 0) {
//					row[0] = row[0].substring(0, row[0].length() - 1);
//				}
				if (i > 0 && i < 500) {
					try {
						Article a = getArticleFromLink(row[0]);
						a.setLikes(Integer.parseInt(row[5]));
						a.setComments(Integer.parseInt(row[7]));
						a.setShares(Integer.parseInt(row[6]));
						a.setCaption(row[1]);	
						a.setPageViews(Integer.parseInt(row[4]));
						String stringOfDate = row[2];
						String[] dateTime = stringOfDate.split(" ");
						String[] monthDayYear = dateTime[0].split("/");
						String[] time = dateTime[1].split(":");
						Calendar c = new Calendar(Integer.parseInt(monthDayYear[2]), Integer.parseInt(monthDayYear[0]), Integer.parseInt(monthDayYear[1]),Integer.parseInt(time[0]),Integer.parseInt( time[1] ));
						a.setDate(c);
						articles.add(a);
						System.out.println(a.toString());
					}
					catch(Exception r) {
						System.out.println(i);
					}
					
				}
				i++;
			}
			getAuthorCorrelation(articles);

		} catch (Exception r) {
			r.printStackTrace();
		}
	}
	
	public void getAuthorCorrelation(HashSet<Article> arts) throws IOException {
		System.out.println("reached here");
		HashSet<Author> authors = new HashSet<>();
		HashSet<String> authorNames = new HashSet<>();
		
		// iterates through all articles and creates hash set of author objects
		
		// a = current article
		// artAuth = String author of the current article, a
		// au = current author 
		// author = name of new Author object
		for (Article a : arts) {
			String artAuth = a.getAuthor();
			System.out.println(artAuth);
			if (authorNames.contains(artAuth)) {
				for (Author au : authors) {
					if (au.getAuthorName().equals(artAuth)) {
						au.addArticle(a);
					}
				}
			} else {
				Author author = new Author(artAuth);
				author.addArticle(a);
				authors.add(author);
				authorNames.add(artAuth);
			}
		}
		
		double[] artNums = new double[authors.size()];
		double[] avgViews = new double[authors.size()];
		int index = 0;
		
		for (Author a : authors) {
			System.out.println("reached");
			System.out.println(a.getAuthorName() + "- Index: " +index + ", Num of Articles: " +a.getNumArticles() +
								", Avg views: " + a.getAvgViews());
			artNums[index] = a.getNumArticles();
			avgViews[index] = a.getAvgViews();
			System.out.println(artNums[index] +", " +avgViews[index]);
			index ++;
		}
		
		LinearRegression linreg = new LinearRegression(artNums, avgViews);
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter author first and last name:"); 
        String inputAuth = sc.nextLine();
        
        if (authorNames.contains(inputAuth)) {
			for (Author au : authors) {
				if (au.getAuthorName().equals(inputAuth)) {
					System.out.println("The average number of page views for " +inputAuth +" is " +au.getAvgViews());
					double pred = linreg.predict(au.getNumArticles());
					System.out.println("The estimated number of page views based on the number of articles written is: " +pred);
				}
			}
        } else {
        	System.out.println("Author cannot be found. Cannot predict page views.");
        }
        System.out.println("Slope of regression line is: " +linreg.slope());
        System.out.println(linreg);
	}
	
}
