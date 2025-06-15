package logic;

import java.util.ArrayList;

import gui.InventoryPane;
import gui.Slot;
import items.BaseItem;
//manage inventory
public class InventoryController {
	private static InventoryController instance;
	private InventoryPane inventoryPane;
	private ArrayList<BaseItem> itemList;
	private Slot currentSlot;
	
	public InventoryController() {
		this.itemList = new ArrayList<>();
		this.inventoryPane = new InventoryPane();
	}

	public InventoryPane getInventoryPane() {
		return inventoryPane;
	}
	//add item to the current slot
	public void addItem(BaseItem item) {
		this.getItemList().add(item);
		inventoryPane.getSlotsList().get(this.getItemList().size()-1).setItem(item);
	}
	//remove item from the current slot
	public void removeCurrentItem() {
		this.getItemList().remove(currentSlot.getItem());
		this.setCurrentSlot(null);
		for(int i=0;i<this.getItemList().size();i++) {
			inventoryPane.getSlotsList().get(i).setInitialSlot();
			inventoryPane.getSlotsList().get(i).setItem(this.getItemList().get(i));
		}
		inventoryPane.getSlotsList().get(this.getItemList().size()).setInitialSlot();
		inventoryPane.getSlotsList().get(this.getItemList().size()).setItem(null);
	}

	public ArrayList<BaseItem> getItemList() {
		return itemList;
	}
	
	public static InventoryController getInstance() {
		if(instance == null)
			instance = new InventoryController();
		return instance;
	}
	
	public boolean isCurrentItem(String itemName) {
		if(InventoryController.getInstance().getCurrentSlot() == null)
			return false;
		return InventoryController.getInstance().getCurrentSlot().getItem().getName().equals(itemName);
	}
	
	public void setCurrentSlot(Slot slot) {
		this.currentSlot = slot;
	}
	
	public Slot getCurrentSlot() {
		return this.currentSlot;
	}
	
	
}
