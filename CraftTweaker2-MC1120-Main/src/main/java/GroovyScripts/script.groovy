package GroovyScripts

import crafttweaker.CraftTweakerAPI
import crafttweaker.api.item.IIngredient
import crafttweaker.mc1120.brackets.BracketHandlerOre
import crafttweaker.runtime.GlobalFunctions

import static crafttweaker.mc1120.brackets.BracketHandlerItem.getItem

println "hello, I am alive"

GlobalFunctions.print("Hey")

def wood = BracketHandlerOre.getOre("plankWood")
def iron = getItem("minecraft:iron_ingot", 0)

CraftTweakerAPI.recipes.removeShaped(getItem("minecraft:wooden_pressure_plate", 0), (IIngredient[][])null)
def ingred = [[wood, wood, wood], [null, wood, null], [wood, wood, wood]] as IIngredient[][]

CraftTweakerAPI.recipes.addShaped("blub", getItem("minecraft:coal", 0).withAmount(3), ingred, null, null)


/*
CraftTweakerAPI.recipes.addShaped(getItem("minecraft:diamond", 0).withAmount(3), new IIngredient[][]{{null, wood, null}, {null, wood, null}, {wood, wood, wood}}, (IRecipeFunction)null, (IRecipeAction)null);
CraftTweakerAPI.recipes.addShapeless(getItem("minecraft:diamond", 0).withDisplayName("lel"), new IIngredient[]{iron, iron, iron, iron, iron, iron, iron, iron, iron}, (IRecipeFunction)null, (IRecipeAction)null);
CraftTweakerAPI.recipes.addShaped(getItem("minecraft:wooden_pressure_plate", 0).withAmount(3), new IIngredient[][]{{wood, wood, wood}, {null, wood, null}, {wood, wood, wood}}, (IRecipeFunction)null, (IRecipeAction)null);
 */

// def infusedEgg = getItem("minecraft:dragon_egg", 0).withTag('{display:{Name:"§rInfused Dragon Egg",Lore:["This Egg got infused with the", "remainings of his servants."]}, tpye: "InfusedDragonEgg"}');

/*
IItemStack var10000 = getItem("minecraft:dragon_egg", 0);
HashMap var10001 = new HashMap();
HashMap var10004 = new HashMap();
    var10004.put("Name", ExpandString.asData("§rInfused Dragon Egg"));
    var10004.put("Lore", ExpandAnyArray.asData(new IData[]{ExpandString.asData("This Egg got infused with the"), ExpandString.asData("remainings of his servants.")}));
    var10001.put("display", ExpandAnyDict.asData(var10004));
    var10001.put("tpye", ExpandString.asData("InfusedDragonEgg"));
    IItemStack var5 = var10000.withTag(ExpandAnyDict.asData(var10001));
*/
/*
CraftTweakerAPI.recipes.addShapeless(var5, new IIngredient[]{iron, iron, iron, iron}, (IRecipeFunction)null, (IRecipeAction)null);
CraftTweakerAPI.recipes.addShapeless(getItem("minecraft:dragon_egg", 0).withDisplayName("lel"), new IIngredient[]{iron, iron, iron}, (IRecipeFunction)null, (IRecipeAction)null);
CraftTweakerAPI.recipes.addShapeless(CraftTweakerAPI.itemUtils.createPotion(new Object[][]{{BracketHandlerPotion.getPotion("minecraft:strength"), Integer.valueOf(10), Integer.valueOf(600)}, {BracketHandlerPotion.getPotion("minecraft:luck"), Integer.valueOf(30), Integer.valueOf(600)}}).withDisplayName("§4§kCool Potion").withLore(new String[]{"", "§r§2Lol", "§r§3ROFL"}), new IIngredient[]{wood, wood, wood}, (IRecipeFunction)null, (IRecipeAction)null);
*/