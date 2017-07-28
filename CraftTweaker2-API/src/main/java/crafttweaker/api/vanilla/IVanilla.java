package crafttweaker.api.vanilla;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.annotations.*;

/**
 * @author Stan
 */
@ZenClass("vanilla.IVanilla")
@ZenRegister
public interface IVanilla {
    
    @ZenGetter("seeds")
    ISeedRegistry getSeeds();
}
