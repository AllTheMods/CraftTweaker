package crafttweaker.mc1120.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Class with various helper function for normal MC itemstacks
 */
public class MCItemStackHelper {
    public static boolean matches(ItemStack thisItem, ItemStack otherItem, boolean isWildcardSize) {
        if(thisItem.hasTagCompound()) {
            return matchesExact(thisItem, otherItem);
        }
        return !otherItem.isEmpty() && !otherItem.isEmpty() && otherItem.getItem() == thisItem.getItem() && (isWildcardSize || otherItem.getCount() >= thisItem.getCount()) && (thisItem.getItemDamage() == OreDictionary.WILDCARD_VALUE || thisItem.getItemDamage() == otherItem.getItemDamage() || (!thisItem.getHasSubtypes() && !thisItem.getItem().isDamageable()));
    }
    
    public static boolean matchesExact(ItemStack thisStack, ItemStack otherItem) {
        if(otherItem.getTagCompound() != null && thisStack.getTagCompound() == null) {
            return false;
        }
        if(otherItem.getTagCompound() == null && thisStack.getTagCompound() != null) {
            return false;
        }
        if(otherItem.getTagCompound() == null && thisStack.getTagCompound() == null) {
            return thisStack.getItem() == otherItem.getItem() && (otherItem.getMetadata() == 32767 || thisStack.getMetadata() == otherItem.getMetadata());
        }
        if(otherItem.getTagCompound().getKeySet().equals(thisStack.getTagCompound().getKeySet())) {
            for(String s : otherItem.getTagCompound().getKeySet()) {
                if(!otherItem.getTagCompound().getTag(s).equals(thisStack.getTagCompound().getTag(s))) {
                    return false;
                }
            }
        }
        return thisStack.getItem() == otherItem.getItem() && (otherItem.getMetadata() == 32767 || thisStack.getMetadata() == otherItem.getMetadata());
    }

}
