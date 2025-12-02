package jogo.ui;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector2f;
import com.jme3.ui.Picture;
import jogo.appstate.HudAppState;
import jogo.appstate.InputAppState;
import jogo.appstate.PlayerAppState;
import jogo.systems.inventory.Inventory;
import jogo.systems.inventoryitem.InventoryItem;
import jogo.systems.inventoryitem.consumableitem.ConsumableItem;
import jogo.systems.inventoryitem.equipmentitem.EquipmentItem;

public class InventoryUI extends UserInterface{

    private ClickablePicture[] equipmentIcons;
    private ClickablePicture[] equipmentSlots;

    private ClickablePicture[] inventoryIcons;
    private ClickablePicture[] inventorySlots;

    private ClickablePicture draggedIcon;
    private int draggedId = -1;
    private boolean isDraggedfromInventory = false;
    private int itemsInARaw = 4;

    private float slotSize = 80f;
    private float slotMargine = 8f;

    private Inventory inventory;

    public InventoryUI(AssetManager assetManager, HudAppState hud, InputAppState input, PlayerAppState player){
        super("Inventory",assetManager,hud,input,player);
        input.setMouseCaptured(false);
        init();
    }

    public void init() {
        SimpleApplication sapp = (SimpleApplication) hud.getApplication();
        int x = sapp.getCamera().getWidth()/2;
        int y = sapp.getCamera().getHeight()/2;

        inventory = player.inventory;
        inventoryIcons = new ClickablePicture[inventory.getInventoryItems().length];
        inventorySlots = new ClickablePicture[inventory.getInventoryItems().length];

        equipmentIcons = new ClickablePicture[inventory.getEquipmentItems().length];
        equipmentSlots = new ClickablePicture[inventory.getEquipmentItems().length];

        Picture inventoryBackground = new Picture("Inventory Background");
        inventoryBackground.setImage(assetManager, "Interface/inventory_background.png", true);
        inventoryBackground.setHeight(448f);
        inventoryBackground.setWidth(384f);
        this.attachChild(inventoryBackground);
        inventoryBackground.setPosition(x - inventoryBackground.getWidth()/2f,y - inventoryBackground.getHeight()/2f);

        for (int i = 0; i < equipmentSlots.length; i++){
            ClickablePicture equipment_slot = new ClickablePicture("Equipment Slot " + i);
            equipment_slot.setImage(assetManager, "Interface/inventory_slot.png", true);
            equipment_slot.setHeight(slotSize);
            equipment_slot.setWidth(slotSize);
            this.attachChild(equipment_slot);
            equipmentSlots[i] = equipment_slot;

            float slot_posi_x = (i % itemsInARaw) * (slotSize + slotMargine);
            float slot_posi_y = -(int)(i / itemsInARaw) * (slotSize + slotMargine);
            float row_width = (itemsInARaw - 1) * (slotSize + slotMargine) + slotSize;
            float slots_start_x = x - (row_width/2);
            float slots_start_y = 460;

            equipment_slot.setPosition( slots_start_x + slot_posi_x,slots_start_y + slot_posi_y);
        }

        for (int i = 0; i < inventorySlots.length; i++){
            ClickablePicture inventory_slot = new ClickablePicture("Inventory Slot " + i);
            inventory_slot.setImage(assetManager, "Interface/inventory_slot.png", true);
            inventory_slot.setHeight(slotSize);
            inventory_slot.setWidth(slotSize);
            this.attachChild(inventory_slot);
            inventorySlots[i] = inventory_slot;

            float slot_posi_x = (i % itemsInARaw) * (slotSize + slotMargine);
            float slot_posi_y = -(int)(i / itemsInARaw) * (slotSize + slotMargine);
            float row_width = (itemsInARaw - 1) * (slotSize + slotMargine) + slotSize;
            float slots_start_x = x - (row_width/2);
            float slots_start_y = 360;

            inventory_slot.setPosition( slots_start_x + slot_posi_x,slots_start_y + slot_posi_y);
        }

        for (int i = 0; i < equipmentIcons.length; i++){
            InventoryItem item = inventory.getEquipmentItems()[i];
            System.out.println(item);
            if (item == null) {
                continue;
            }
            ClickablePicture equipmentItem = new ClickablePicture("Inventory Item " + i);
            equipmentItem.setImage(assetManager, item.getTexturePath(), true);
            equipmentItem.setHeight(64f);
            equipmentItem.setWidth(64f);
            this.attachChild(equipmentItem);
            equipmentIcons[i] = equipmentItem;
            snapToSlot(equipmentItem, equipmentSlots[i]);
        }

        for (int i = 0; i < inventoryIcons.length; i++){
            InventoryItem item = inventory.getInventoryItems()[i];
            if (item == null) {
                continue;
            }
            ClickablePicture inventoryItem = new ClickablePicture("Inventory Item " + i);
            inventoryItem.setImage(assetManager, item.getTexturePath(), true);
            inventoryItem.setHeight(64f);
            inventoryItem.setWidth(64f);
            this.attachChild(inventoryItem);
            inventoryIcons[i] = inventoryItem;
            snapToSlot(inventoryItem, inventorySlots[i]);
        }
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        Vector2f mouse_pos = input.getMousePosition();

        if (draggedIcon != null){
            draggedIcon.setPosition(mouse_pos.x - draggedIcon.getWidth()/2f, mouse_pos.y - draggedIcon.getHeight()/2f);
        }

        if (input.consumeUseRequested()){
            for (int i = 0; i < inventoryIcons.length; i++){
                if (inventoryIcons[i] == null){
                    continue;
                }
                if (!inventoryIcons[i].checkClick(mouse_pos)){
                    continue;
                }

                InventoryItem item = inventory.getInventoryItem(i);
                if (item!= null){
                    if (item instanceof EquipmentItem){
                        for (int j = 0; j < equipmentSlots.length; j++){
                            if (inventory.equipItem(i, j)){
                                equipItem(i,j);
                                break;
                            }
                        }
                    }
                    else if (item instanceof ConsumableItem){
                        player.useItem((ConsumableItem) item, player.getPlayer());
                        detachChild(inventoryIcons[i]);
                        inventoryIcons[i] = null;

                    }
                }
            }
        }

        if (input.consumeSelectRequested()){
            if (draggedIcon == null) {
                //dragging from inventory
                for (int i = 0; i < inventoryIcons.length; i++){
                    if (inventoryIcons[i] == null){
                        continue;
                    }
                    if (inventoryIcons[i].checkClick(mouse_pos) && inventorySlots[i].checkClick(mouse_pos)){
                        draggedIcon = inventoryIcons[i];
                        draggedId = i;
                        isDraggedfromInventory = true;
                        break;
                    }
                }

                //dragging from equipment
                for (int i = 0; i < equipmentIcons.length; i++){
                    if (equipmentIcons[i] == null){
                        continue;
                    }
                    if (equipmentIcons[i].checkClick(mouse_pos) && equipmentSlots[i].checkClick(mouse_pos)){
                        draggedIcon = equipmentIcons[i];
                        draggedId = i;
                        isDraggedfromInventory = false;
                        break;
                    }
                }

            } else {

                boolean is_icon_moved = false;
                int target_invenory_slot = -1;
                for (int i = 0; i < inventorySlots.length; i++){
                    if (inventorySlots[i].checkClick(mouse_pos)){
                        target_invenory_slot = i;
                        break;
                    }
                }

                int target_equipment_slot = -1;
                for (int i = 0; i < equipmentSlots.length; i++){
                    if (equipmentSlots[i].checkClick(mouse_pos)){
                        target_equipment_slot = i;
                        break;
                    }
                }

                //put into inventory

                if (target_invenory_slot != -1) {

                    //put into inventory from equipment
                    if (!isDraggedfromInventory) {
                        if (inventory.unequipItem(draggedId, target_invenory_slot)) {

                            equipItem(target_invenory_slot,draggedId);
                            is_icon_moved = true;
                        }
                    }

                    //put into inventory from inventory
                    else {
                        inventory.moveItem(draggedId,target_invenory_slot);

                        ClickablePicture target_icon = inventoryIcons[target_invenory_slot];

                        inventoryIcons[target_invenory_slot] = draggedIcon;
                        inventoryIcons[draggedId] = target_icon;

                        snapToSlot(draggedIcon, inventorySlots[target_invenory_slot]);
                        snapToSlot(target_icon, inventorySlots[draggedId]);

                        is_icon_moved = true;
                    }
                }

                //put into equipment

                else if (target_equipment_slot != -1) {
                    if (isDraggedfromInventory) {
                        if(inventory.equipItem(draggedId, target_equipment_slot)){
                            equipItem(draggedId,target_equipment_slot);
                            /*ClickablePicture target_icon = equipmentIcons[target_equipment_slot];

                            equipmentIcons[target_equipment_slot] = draggedIcon;
                            inventoryIcons[draggedId] = target_icon;

                            snapToSlot(draggedIcon, equipmentSlots[target_equipment_slot]);
                            snapToSlot(target_icon, inventorySlots[draggedId]);
*/
                            is_icon_moved = true;
                        }
                    }
                }

                if (!is_icon_moved){
                    snapDraggedBack();
                }

                draggedIcon = null;
                draggedId = -1;


            }
        }
    }

    private void equipItem(int inventoryPos, int equipmentPos){
        ClickablePicture equipmentIcon = equipmentIcons[equipmentPos];
        ClickablePicture inventoryIcon = inventoryIcons[inventoryPos];

        equipmentIcons[equipmentPos] = inventoryIcon;
        inventoryIcons[inventoryPos] = equipmentIcon;

        if (equipmentIcon != null) {
            snapToSlot(equipmentIcon, inventorySlots[inventoryPos]);
        }

        if (inventoryIcon != null) {
            snapToSlot(inventoryIcon, equipmentSlots[equipmentPos]);
        }

    }

    private void snapToSlot(ClickablePicture draggable, ClickablePicture slot){
        if (draggable == null){
            return;
        }
        draggable.setPosition(slot.getWorldTranslation().x + 8, slot.getWorldTranslation().y + 8);
    }

    private void snapDraggedBack(){
        ClickablePicture slotToSnap;

        if (isDraggedfromInventory){
            slotToSnap = inventorySlots[draggedId];
        } else {
            slotToSnap = equipmentSlots[draggedId];
        }

        snapToSlot(draggedIcon, slotToSnap);

    }

}