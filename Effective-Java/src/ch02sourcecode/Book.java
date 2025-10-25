package ch02sourcecode;

public class Book {
	private String name;
	private String author;
	private String publisher;

	public static Book createBookWithName(String name) {
		Book book = new Book();
		book.name = name;
		return book;
	}
	public static Book createBookWithAuthor(String author) {
		Book book = new Book();
		book.author = author;
		return book;
	}
}
