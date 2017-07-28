package crafttweaker.api.vanilla;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.annotations.*;

@ZenClass("crafttweaker.vanilla.ILootFunction")
@ZenRegister
public interface ILootFunction {
    
    @ZenMethod
    String getName();
    
    @ZenSetter
    void setName();
    
    
}
