package org.team4.maintaindb;

import java.io.FileWriter;
import java.util.ArrayList;

import org.team4.model.items.Item;
import org.team4.model.items.Magazine;
import org.team4.model.items.builder.MagazineBuilder;
import org.team4.model.items.decorator.PurchasableItemDecorator;
import org.team4.model.items.decorator.RentableItemDecorator;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class MaintainMagazine {
	private static MaintainMagazine instance;

	public ArrayList<Item> magazine;

	private static final String FILE_PATH = "database/magazines.csv";

	private MaintainMagazine() {
		this.magazine = new ArrayList<Item>();
		try {
			// LOAD ONLY ONCE WHEN DATABASE CREATED
			this.load();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to Initialize Magazine Database");
		}

	}
	
	/**
	 * Get current running instance
	 * @return
	 */
	public static MaintainMagazine getInstance() {
		if (instance == null) {
			// Extra check for Multi-threaded applications
			synchronized (MaintainMagazine.class) {
				if (instance == null) {
					instance = new MaintainMagazine();
				}
			}
		}
		
		return instance;
	}

	
	/**
	 * Load database into array. Called only once when instance created
	 * @throws Exception
	 */
	public void load() throws Exception {
		CsvReader reader = new CsvReader(FILE_PATH);
		reader.readHeaders();

		while (reader.readRecord()) {
			Item newMagazine = ((MagazineBuilder) new MagazineBuilder()
					.title(reader.get("title"))
					.yearPublished(Integer.parseInt(reader.get("yearPublished")))
					.ISBN(reader.get("ISBN")))
					.publisher(reader.get("publisher"))
					.issueNumber(Integer.parseInt(reader.get("issueNumber")))
					.price(Double.parseDouble(reader.get("price")))
					.quantity(Integer.parseInt(reader.get("quantity")))
					.build();
			if(Boolean.parseBoolean(reader.get("isPurchasable"))) {
                PurchasableItemDecorator purchasableDecorator = new PurchasableItemDecorator(newMagazine);
                newMagazine = purchasableDecorator.getItem();
            }
			if(Boolean.parseBoolean(reader.get("isRentable"))) {
                RentableItemDecorator rentableDecorator = new RentableItemDecorator(newMagazine);
                newMagazine = rentableDecorator.getItem();
            }
			
			
			magazine.add(newMagazine);
		}
	}
	
	/**
	 * Update database with magazines stored
	 * @throws Exception
	 */
	public void update() throws Exception{
		try {
			CsvWriter csvOutput = new CsvWriter(new FileWriter(FILE_PATH, false), ',');
			//email,password,name,type,validated

			//set header
			csvOutput.write("title");
			csvOutput.write("yearPublished");
			csvOutput.write("quantity");
			csvOutput.write("price");
			csvOutput.write("isRentable");
			csvOutput.write("isPurchasable");
			csvOutput.write("publisher");
			csvOutput.write("issueNumber");
			csvOutput.write("ISBN");
			csvOutput.endRecord();

			// write out records
			for(Item m: magazine){
				csvOutput.write(m.getTitle());
				csvOutput.write(String.valueOf(m.getYearPublished()));
				csvOutput.write(String.valueOf(m.getQuantity()));
				csvOutput.write(String.valueOf(m.getPrice()));
				csvOutput.write(String.valueOf(m.isRentable()));
				csvOutput.write(String.valueOf(m.isPurchasable()));
				csvOutput.write(((Magazine) m).getPublisher());
				csvOutput.write(String.valueOf(((Magazine) m).getIssueNumber()));
				csvOutput.write(m.getISBN());
				csvOutput.endRecord();
			}
			csvOutput.close();

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get list of magazines from storage
	 * @return
	 */
	public ArrayList<Item> getAllMagazines(){
    	return this.magazine;
    }
	
	/**
	 * Adds magazine to storage if it doesn't already exist
	 * @param magazine
	 */
	public void addMagazine(Magazine magazine) {
		boolean flag = false;
		for (Item m: this.magazine) {
			if (m.getTitle().equals(magazine.getTitle()) && ((Magazine) m).getIssueNumber() == magazine.getIssueNumber()) {
				System.out.println(magazine.toString() + " already exists");
				flag = true;
				break;
			}
		}
		if (flag == false) {
			this.magazine.add(magazine);
			System.out.println("new magazine " + magazine.toString() + " added");
		}
		try {
			update();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ArrayList<Magazine> searchMagazines(String query) {
		ArrayList<Item> magazines = getAllMagazines();
		ArrayList<Magazine> searchedMagazines = new ArrayList<>();

		for (Item item: magazines) {
			if (item.getTitle().toLowerCase().contains(query.toLowerCase())) {
				Magazine magazine = (Magazine)item;
				searchedMagazines.add(magazine);
			}
		}
		return searchedMagazines;
	}
	public void decreaseNumberOfCopies(Item item) {
		for (Item i: this.magazine) {
			if (i.equals(item)) {
				i.setQuantity(i.getQuantity() - 1);
				break;
			}
		}
		try {
			update();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to decrease quantity");
		}
	}

	public void increaseNumberOfCopies(Item item) {
		for (Item i : this.magazine) {
			if (i.equals(item)) {
				i.setQuantity(i.getQuantity() + 1);
				break;
			}
		}
		try {
			update();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to increase quantity");
		}
	}




	public Magazine searchExactMagazineByISBN(String isbn) {
		for (Item item : magazine) {
			if (item instanceof Magazine && item.getISBN().equals(isbn)) {
				return (Magazine) item;
			}
		}
		return null;
	}




}
