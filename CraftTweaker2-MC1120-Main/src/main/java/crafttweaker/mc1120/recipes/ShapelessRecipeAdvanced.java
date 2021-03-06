package crafttweaker.mc1120.recipes;

import crafttweaker.api.recipes.*;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import static crafttweaker.api.minecraft.CraftTweakerMC.getItemStack;

/**
 * @author Stan
 */
public class ShapelessRecipeAdvanced extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe, IMTRecipe {
    
    private final ShapelessRecipe recipe;
    
    public ShapelessRecipeAdvanced(ShapelessRecipe recipe) {
        this.recipe = recipe;
    }
    
    @Override
    public boolean matches(InventoryCrafting inventory, World world) {
        return recipe.matches(MCCraftingInventory.get(inventory));
    }
    
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        return getItemStack(recipe.getCraftingResult(MCCraftingInventory.get(inventory))).copy();
    }
    
    @Override
    public boolean canFit(int x, int y) {
        return x+y >= recipe.getSize();
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return getItemStack(recipe.getOutput());
    }
    
    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        return NonNullList.create();
    }
    
    @Override
    public ICraftingRecipe getRecipe() {
        return recipe;
    }
}
