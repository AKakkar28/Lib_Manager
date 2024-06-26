package org.team4.maintaindb;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import org.team4.model.items.Book;
import org.team4.model.items.builder.BookBuilder;

import org.team4.model.items.Item;
import org.team4.model.items.decorator.PurchasableItemDecorator;
import org.team4.model.items.decorator.RentableItemDecorator;
import java.io.FileWriter;
import java.util.ArrayList;


public class MaintainBooks {
	private static MaintainBooks instance;
	
	public ArrayList<Item> books;
	
    private static final String FILE_PATH = "database/books.csv";

    private MaintainBooks(){
    	this.books = new ArrayList<Item>();
    	try {
    		//LOAD ONLY ONCE WHEN DATABASE CREATED
			this.load();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to Initialize Book Database");
		}
    	
    }
    
    public static MaintainBooks getInstance() {
		if (instance == null) {
			// Extra check for Multi-threaded applications
			synchronized (MaintainBooks.class) {
				if (instance == null) {
					instance = new MaintainBooks();
				}
			}
		}
		
		return instance;
	}

    public void load() throws Exception{
		CsvReader reader = new CsvReader(FILE_PATH);
		reader.readHeaders();

		while (reader.readRecord()) {
			Item newBook = ((BookBuilder) new BookBuilder()
                    .title(reader.get("title"))
                    .yearPublished(Integer.parseInt(reader.get("yearPublished")))
                    .price(Double.parseDouble(reader.get("price")))
                    .ISBN(reader.get("ISBN"))
                    .quantity(Integer.parseInt(reader.get("quantity"))))
                    .noOfPages(Integer.parseInt(reader.get("noOfPages")))
                    .author(reader.get("author"))
                    .publisher(reader.get("publisherName"))
                    .edition(Integer.parseInt(reader.get("edition")))
                    .genre(reader.get("genre"))
                    .hasHardCopy(Boolean.parseBoolean(reader.get("hardcopy")))
                    .hasSoftCopy(Boolean.parseBoolean(reader.get("softcopy")))
                    .build();
			if(Boolean.parseBoolean(reader.get("isPurchasable"))) {
                PurchasableItemDecorator purchasableDecorator = new PurchasableItemDecorator(newBook);
                newBook = purchasableDecorator.getItem();
            }
            if(Boolean.parseBoolean(reader.get("isRentable"))) {
                RentableItemDecorator rentableDecorator = new RentableItemDecorator(newBook);
                newBook = rentableDecorator.getItem();
            }
			
            books.add(newBook);
		}
	}
    
    public void update() throws Exception{
		try {
			CsvWriter csvOutput = new CsvWriter(new FileWriter(FILE_PATH, false), ',');
			//email,password,name,type,validated

			//set header
			csvOutput.write("title");
			csvOutput.write("yearPublished");
			csvOutput.write("isRentable");
			csvOutput.write("isPurchasable");
			csvOutput.write("genre");
			csvOutput.write("noOfPages");
			csvOutput.write("author");
			csvOutput.write("ISBN");
			csvOutput.write("publisherName");
			csvOutput.write("edition");
			csvOutput.write("quantity");
			csvOutput.write("hardcopy");
			csvOutput.write("softcopy");
			csvOutput.write("price");
			csvOutput.endRecord();

			// write out records
			for(Item b: books){
				csvOutput.write(b.getTitle());
				csvOutput.write(String.valueOf(b.getYearPublished()));
				csvOutput.write(String.valueOf(b.isRentable()));
				csvOutput.write(String.valueOf(b.isPurchasable()));
				csvOutput.write(((Book) b).getGenre());
				csvOutput.write(String.valueOf(((Book) b).getNoOfPages()));
				csvOutput.write(((Book) b).getAuthor());
				csvOutput.write(((Book) b).getISBN());
				csvOutput.write(((Book) b).getPublisher());
				csvOutput.write(String.valueOf(((Book) b).getEdition()));
				csvOutput.write(String.valueOf(((Book) b).getQuantity()));
				csvOutput.write(String.valueOf(((Book) b).hasHardCopy()));
				csvOutput.write(String.valueOf(((Book) b).hasSoftCopy()));
				csvOutput.write(String.valueOf(((Book) b).getPrice()));
				csvOutput.endRecord();
			}
			csvOutput.close();

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    public ArrayList<Item> getAllBooks(){
    	return this.books;

    }
    
    public void decreaseNumberOfCopies(Item item) {
    	for (Item i: this.books) {
    		if (i.equals(item)) {
    			i.setQuantity(i.getQuantity()-1);
    		}
    	}
    	try {
			update();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to decrease quantity");
		}
    	
    }
    
    public void increaseNumberOfCopies(Item item) {
    	for (Item i: this.books) {
    		if (i.equals(item)) {
    			i.setQuantity(i.getQuantity()+1);
    		}
    	}
    	try {
			update();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to increase quantity");
		}
    	
    }

    public boolean searchExactBook(String ISBN) {
        ArrayList<Item> books = getAllBooks();
        boolean found = false;
        for (Item item: books) {
        	Book book = (Book)item;
            if (book.getISBN().equalsIgnoreCase(ISBN)) {
                found = true;
            }
        }
        return found;
    }
    
    public Book searchExactBookByISBN(String ISBN) {
    	for (Item i : this.books) {
    		if (i.getISBN().equals(ISBN)) {
    			return (Book) i;
    		}
    	}
    	return null;
    }
	public Book searchExactBookByTitle(String title) {
		for (Item item : this.books) {
			if (item instanceof Book && ((Book) item).getTitle().equalsIgnoreCase(title)) {
				return (Book) item;
			}
		}
		return null;
	}


    public void addBook(Book book) {
		boolean flag = false;
		for (Item d: this.books) {
			if (((Book) d).getISBN().equals(book.getISBN())) {
				System.out.println(book.toString() + " already exists");
				flag = true;
				break;
			}
		}
		if (flag == false) {
			this.books.add(book);
			System.out.println("new book " + book.toString() + " added");
		}
		try {
			update();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Couldn't add book: " + book);
		}
	}

}