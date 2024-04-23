package org.team4.functionality.rent;
import org.team4.maintaindb.*;
import org.team4.model.items.*;
import org.team4.model.user.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SuppressWarnings("unused")
public class RentalService {
    private static final int MAX_RENTALS_PER_USER = 10;

    private static final double PENALTY_PER_DAY=0.5;
	

    private MaintainRent rentMaintain= MaintainDatabase.getInstance().getrenterDatabase();
	private MaintainBooks maintainDB= MaintainDatabase.getInstance().getBookDatabase();
    private MaintainBooks maintainBooks = MaintainDatabase.getInstance().getBookDatabase();
    private MaintainDVD maintainDVD = MaintainDatabase.getInstance().getDVDDatabase();
    private MaintainMagazine maintainMagazine = MaintainDatabase.getInstance().getMagazineDatabase();

    public boolean rentItem(User user, Item item) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 1);
        Date dueDate = new java.sql.Date(calendar.getTime().getTime());

        if (canRentItem(user, item)) {
            decreaseItemQuantity(item);
            RentedItem rental = new RentedItem(item.getTitle(),item.getISBN(), new Date(), dueDate);
            rentMaintain.addNewRentedItem(user.getEmail(), item.getTitle(),item.getISBN(), new java.sql.Date(new Date().getTime()), new java.sql.Date(dueDate.getTime()));
            rentMaintain.update();
            return true;
        }
        return false;
    }




    public boolean canRentItem(User user, Item item) throws Exception {
        if (!item.isRentable() || item.getQuantity() < 1) {
            throw new Exception("Item is not available for rent.");
        }
        if (rentMaintain.isAlreadyRentedByUser(user.getEmail(), new RentedItem(null,item.getISBN(), null, null))) {
            throw new Exception("You have already rented this item.");
        }
        if (getOverdueCount(user.getEmail()) > 3) {
            throw new Exception("You have more than 3 overdue items.");
        }
        if (rentMaintain.getNumberOfItemsRentedByUser(user.getEmail()) >= MAX_RENTALS_PER_USER) {
            throw new Exception("You cannot rent more than 10 items.");
        }
        return true;
    }

    public int getRentalCountForUser(String userEmail) {
        int userRentals = rentMaintain.getNumberOfItemsRentedByUser(userEmail);
        return userRentals;
    }
    private RentedItem createRental(User user, Item item) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date());
        calendar.add(Calendar.MONTH, 1);
        java.sql.Date dueDate = new java.sql.Date(calendar.getTime().getTime());
        return new RentedItem(item.getTitle(),item.getISBN(), new Date(), dueDate);
    }
    private void decreaseItemQuantity(Item item) throws Exception {

        if (item instanceof Book) {
            maintainBooks.decreaseNumberOfCopies((Book) item);
        } else if (item instanceof DVD) {
            maintainDVD.decreaseNumberOfCopies((DVD) item);
        } else if (item instanceof Magazine) {
            maintainMagazine.decreaseNumberOfCopies((Magazine) item);
        }
    }
    public List<RentedItem> checkOverdue(String userEmail) {
        List<RentedItem> overdueItems = new ArrayList<>();
        Date currentDate = new Date(System.currentTimeMillis());

        List<RentedItem> rentedItems = rentMaintain.getAllRenters().get(userEmail);
        if (rentedItems != null) {
            for (RentedItem item : rentedItems) {
                if (item.getDueDate().before(currentDate)) {
                    overdueItems.add(item);
                }
            }
        }
        return overdueItems;
    }


    private int getOverdueCount(String userEmail) {

        return checkOverdue(userEmail).size();
    }

    public double calculatePenaltyForItem(String userEmail, String itemISBN) {
        double penalty = 0.0;
        List<RentedItem> userRentedItems = rentMaintain.getAllRenters().get(userEmail);

        if (userRentedItems != null) {
            for (RentedItem rentedItem : userRentedItems) {
                if (rentedItem.getISBN().equals(itemISBN) && rentedItem.getDueDate().before(new Date())) {

                    long overdueDays = (new Date().getTime() - rentedItem.getDueDate().getTime()) / (24 * 60 * 60 * 1000);

                    penalty = overdueDays * PENALTY_PER_DAY;
                    System.out.println("Penalty for item " + itemISBN + ": " + penalty);
                    break;
                }
            }
        }

        return penalty;
    }
    public void printOverduePenalties(String userEmail, String itemISBN) {
        List<RentedItem> overdueItems = checkOverdue(userEmail);
        double penalty = calculatePenaltyForItem(userEmail,itemISBN);

        System.out.println("User " + userEmail + " has " + overdueItems.size() + " overdue items. Penalty $" + penalty);
    }
    public List<RentedItem> getApproachingOrOverdueItems(String userEmail) {
        List<RentedItem> approachingOrOverdueItems = new ArrayList<>();
        Date currentDate = new Date(System.currentTimeMillis());

        List<RentedItem> rentedItems = rentMaintain.getAllRenters().get(userEmail);
        if (rentedItems != null) {
            for (RentedItem item : rentedItems) {
                long timeDiff = item.getDueDate().getTime() - currentDate.getTime();
                long hoursLeft = timeDiff / (60 * 60 * 1000);
                if (hoursLeft < 24 || timeDiff < 0) {
                    approachingOrOverdueItems.add(item);
                }
            }
        }
        return approachingOrOverdueItems;
    }


}
