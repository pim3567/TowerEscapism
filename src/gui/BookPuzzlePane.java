package gui;

import java.util.ArrayList;
import java.util.Arrays;

import items.BookPlayingItem;
import javafx.scene.layout.HBox;
//display pane of book game
public class BookPuzzlePane extends HBox {
	private ArrayList<BookPlayingItem> bookList = new ArrayList<>();
	private static ArrayList<Integer> Order = new ArrayList<Integer>(
			Arrays.asList(10, 7, 2, 8, 1, 11, 4, 9, 3, 6, 12, 0, 5));
	public static ArrayList<String> imagePaths = new ArrayList<String>(
			Arrays.asList("images/book0.png", "images/book1.png", "images/book2.png", "images/book3.png",
					"images/book4.png", "images/book5.png", "images/book6.png", "images/book7.png", "images/book8.png",
					"images/book9.png", "images/book10.png", "images/book11.png", "images/book12.png"));
	//set layout and add 13 books to the book list and to the pane
	public BookPuzzlePane() {
		this.setLayoutX(config.Config.imageBackgroundWidth * 0.29);
		this.setLayoutY(config.Config.imageBackgroundHeight * 0.413);
		for (int i = 0; i < 13; i++) {
			BookPlayingItem book = new BookPlayingItem("Book", i, Order.get(i)); 
			bookList.add(book);
			this.getChildren().add(book);
		}
	}

	public ArrayList<BookPlayingItem> getBookList() {
		return this.bookList;
	}
}
