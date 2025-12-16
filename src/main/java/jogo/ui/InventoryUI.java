package jogo.ui;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector2f;
import com.jme3.texture.Texture;
import com.jme3.ui.Picture;
import jogo.appstate.HudAppState;
import jogo.appstate.InputAppState;
import jogo.appstate.PlayerAppState;
import jogo.systems.inventory.Inventory;
import jogo.systems.inventoryitem.InventoryItem;
import jogo.systems.inventoryitem.consumableitem.ConsumableItem;
import jogo.systems.inventoryitem.equipmentitem.EquipmentItem;

public class InventoryUI extends UserInterface{

    private InventoryIcon[] equipmentIcons;
    private ClickablePicture[] equipmentSlots;

    private InventoryIcon[] inventoryIcons;
    private ClickablePicture[] inventorySlots;

    private InventoryIcon draggedIcon;
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

        inventory = player.getInventory();
        inventoryIcons = new InventoryIcon[inventory.getInventoryItems().length];
        inventorySlots = new ClickablePicture[inventory.getInventoryItems().length];

        equipmentIcons = new InventoryIcon[inventory.getEquipmentItems().length];
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
            if (item == null) {
                continue;
            }
            InventoryIcon equipmentItem = new InventoryIcon("Inventory Item " + i, assetManager, item.getTexturePath(), item.getLevel());
            this.attachChild(equipmentItem);
            equipmentIcons[i] = equipmentItem;
            snapToSlot(equipmentItem, equipmentSlots[i]);
        }

        for (int i = 0; i < inventoryIcons.length; i++){
            InventoryItem item = inventory.getInventoryItems()[i];
            if (item == null) {
                continue;
            }
            InventoryIcon inventoryItem = new InventoryIcon("Inventory Item " + i, assetManager, item.getTexturePath(), item.getLevel());
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
            draggedIcon.setLocalTranslation(mouse_pos.x - draggedIcon.getPicture().getWidth() / 2f, mouse_pos.y - draggedIcon.getPicture().getHeight() / 2f, 0);
        }

        if (input.consumeUseRequested()){
            for (int i = 0; i < inventoryIcons.length; i++){
                if (inventoryIcons[i] == null){
                    continue;
                }
                if (!inventoryIcons[i].getPicture().checkClick(mouse_pos)){
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
                    if (inventoryIcons[i].getPicture().checkClick(mouse_pos) && inventorySlots[i].checkClick(mouse_pos)){
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
                    if (equipmentIcons[i].getPicture().checkClick(mouse_pos) && equipmentSlots[i].checkClick(mouse_pos)){
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

                        InventoryIcon target_icon = inventoryIcons[target_invenory_slot];

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
                            is_icon_moved = true;
                        }
                    }
                }

                if (!is_icon_moved){
                    System.out.println(mouse_pos.x);
                    if (
                            mouse_pos.x < 416 ||
                            mouse_pos.x > 864 ||
                            mouse_pos.y < 168 ||
                            mouse_pos.y > 552
                    )
                    {
                        draggedIcon.removeFromParent();

                        if(!isDraggedfromInventory){
                            player.dropItem(inventory.getEquipmentItem(draggedId));
                            inventory.removeEquipmentItemAt(draggedId);
                        } else {
                            player.dropItem(inventory.getInventoryItem(draggedId));
                            inventory.removeInventoryItemAt(draggedId);
                        }
                    } else {
                        snapDraggedBack();
                    }

                }

                draggedIcon = null;
                draggedId = -1;


            }
        }
    }

    private void equipItem(int inventoryPos, int equipmentPos){
        InventoryIcon equipmentIcon = equipmentIcons[equipmentPos];
        InventoryIcon inventoryIcon = inventoryIcons[inventoryPos];

        equipmentIcons[equipmentPos] = inventoryIcon;
        inventoryIcons[inventoryPos] = equipmentIcon;

        if (equipmentIcon != null) {
            snapToSlot(equipmentIcon, inventorySlots[inventoryPos]);
        }

        if (inventoryIcon != null) {
            snapToSlot(inventoryIcon, equipmentSlots[equipmentPos]);
        }

    }


    private void snapToSlot(InventoryIcon draggable, ClickablePicture slot){
        if (draggable == null){
            return;
        }
        draggable.setLocalTranslation(slot.getWorldTranslation().x + 8, slot.getWorldTranslation().y + 8, 0);
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