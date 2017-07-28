package crafttweaker.api.recipes;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IDimension;
import crafttweaker.annotations.*;

/**
 * @author Stan Hebben
 */
@ZenClass("crafttweaker.recipes.ICraftingInfo")
@ZenRegister
public interface ICraftingInfo {
    
    @ZenGetter("inventory")
    ICraftingInventory getInventory();
    
    @ZenGetter("player")
    IPlayer getPlayer();
    
    @ZenGetter("dimension")
    IDimension getDimension();
}
