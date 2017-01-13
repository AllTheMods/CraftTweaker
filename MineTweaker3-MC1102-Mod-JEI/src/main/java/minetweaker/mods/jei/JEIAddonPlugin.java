package minetweaker.mods.jei;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.*;
import minetweaker.MineTweakerAPI;
import minetweaker.api.compat.DummyJEIRecipeRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


@mezz.jei.api.JEIPlugin
@Mod(modid = "crafttweakerjei", name = "CraftTweaker JEI Support", version = "1.0.0", dependencies = "required-before:MineTweaker3;")
public class JEIAddonPlugin implements IModPlugin {
	
	public static IJeiHelpers jeiHelpers;
	public static IIngredientRegistry itemRegistry;
	public static IRecipeRegistry recipeRegistry;
	
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent ev) {
		MineTweakerAPI.registerClass(JEI.class);
	}
	
	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
		
	}
	
	@Override
	public void registerIngredients(IModIngredientRegistration registry) {
		
	}
	
	@Override
	public void register(IModRegistry registry) {
		jeiHelpers = registry.getJeiHelpers();
		itemRegistry = registry.getIngredientRegistry();
		
		// The blacklist items must be registered here, otherwise the item filters are already created and
		// anything that is added doesn't have an effect
		//		JEI.onJEIStarted();
	}
	
	@Override
	public void onRuntimeAvailable(IJeiRuntime iJeiRuntime) {
		recipeRegistry = iJeiRuntime.getRecipeRegistry();
		if(MineTweakerAPI.getIjeiRecipeRegistry() instanceof DummyJEIRecipeRegistry) {
			MineTweakerAPI.setIjeiRecipeRegistry(new JEIRecipeRegistry(recipeRegistry));
		}
		
	}
	
	
}
