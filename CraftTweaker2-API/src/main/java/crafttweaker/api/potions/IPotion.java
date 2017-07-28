package crafttweaker.api.potions;

import crafttweaker.annotations.*;

@ZenClass("crafttweaker.api.IPotion")
public interface IPotion {
    
    @ZenGetter("name")
    String name();
    
    @ZenGetter("badEffect")
    boolean isBadEffect();
    
    @ZenGetter("liquidColor")
    int getLiquidColor();
    
    Object getInternal();
}