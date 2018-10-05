package netsFinal;
public class Article {
	private String text;
	private String author;
	private Calendar date;
	private int likes;
	private int comments;
	private int shares;
	private String caption;
	private int pageViews;
	

	public Article(String t, String a) {
		text = t;
		author = a;
		date = null;
		likes = -1;
		comments = -1;	
		shares = -1;
		caption = "";
	}
	
	public String getAuthor() {
		return author;
	}
	public String getText() {
		return text;
	}
	
	public int getPageViews() {
		return pageViews;
	}
	
	public void setDate(Calendar d) {
		date = d;
	}
	public void setCaption(String c) {
		caption = c;
	}
	public void setLikes(int l) {
		likes = l;
	}
	public void setShares(int l) {
		shares = l;
	}
	public void setComments(int c) {
		 comments = c;
	}
	public void setPageViews(int p) {
		pageViews = p;
	}
	
	public Calendar getDate() {
		return date;
	}
	public String getCaption() {
		return caption;
	}
	
	public int getLikes() {
		return likes;
	}
	public int getComments() {
		return comments;
	}
	public int getShares() {
		return shares;
	}
	public String toString() {
		return "author" + author + "likes" + likes + "caption" + caption;
	}

}
