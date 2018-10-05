package netsFinal;

public class Author {
	String author;
	int numArticles = 0;
	int avgPageViews;
	int totalPageViews = 0;
	
	public Author(String name) {
		author = name;
	}
	
	public void addArticle(Article art) {
		numArticles ++;
		totalPageViews += art.getPageViews();
	}
	
	public String getAuthorName() {
		return author;
	}
	
	public int getAvgViews () {
		return totalPageViews/numArticles;
	}
	
	public int getNumArticles() {
		return numArticles;
	}
}
