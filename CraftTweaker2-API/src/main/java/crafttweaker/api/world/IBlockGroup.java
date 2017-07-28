package crafttweaker.api.world;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlock;
import crafttweaker.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.world.IBlockGroup")
@ZenRegister
public interface IBlockGroup {
    
    @ZenGetter("dimension")
    IDimension getDimension();
    
    @ZenMethod
    IBlock getBlock(int x, int y, int z);
}
