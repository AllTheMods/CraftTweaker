package crafttweaker.mc1120.events;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.formatting.IFormattedText;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.tooltip.IngredientTooltips;
import crafttweaker.mc1120.formatting.IMCFormattedString;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.settings.*;
import net.minecraft.client.util.RecipeBookClient;
import net.minecraft.stats.RecipeBook;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.relauncher.*;
import org.objectweb.asm.*;

import java.io.*;
import java.util.Arrays;

public class ClientEventHandler {
    private static boolean alreadyChangedThePlayer = false;
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onItemTooltip(ItemTooltipEvent ev) {
        if(!ev.getItemStack().isEmpty()) {
            IItemStack itemStack = CraftTweakerMC.getIItemStack(ev.getItemStack());
            if(IngredientTooltips.shouldClearToolTip(itemStack)) {
                ev.getToolTip().clear();
            }
            for(IFormattedText tooltip : IngredientTooltips.getTooltips(itemStack)) {
                ev.getToolTip().add(((IMCFormattedString) tooltip).getTooltipString());
            }
            if(GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak)) {
                for(IFormattedText tooltip : IngredientTooltips.getShiftTooltips(itemStack)) {
                    ev.getToolTip().add(((IMCFormattedString) tooltip).getTooltipString());
                }
            }
        }
    }
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onGuiOpenEvent(GuiOpenEvent ev){
        
        if (Minecraft.getMinecraft().player != null && !alreadyChangedThePlayer){
            alreadyChangedThePlayer = true;
            
            RecipeBookClient.rebuildTable();
            CraftTweakerAPI.logInfo("Fixed the RecipeBook");
        }
    
        RecipeList test = new RecipeList();
        RecipeList test2 = new RecipeList();
    
    
        try {
            ClassReader cr = new ClassReader("net.minecraft.client.gui.recipebook.RecipeList");
            ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
            
            byte[] code = cw.toByteArray();
            
            
            OutputStream fos = new FileOutputStream("D:\\Users\\jonas\\Documents\\GitHub\\CraftTweaker\\CraftTweaker2-MC1120-Main\\src\\main\\java\\crafttweaker\\mc1120\\asmfun\\ExportedClassLater.class");
            fos.write(code);
            fos.close();
            
        } catch(IOException e) {
            e.printStackTrace();
        }
        System.out.println("test.getClass().getDeclaredMethods() = " + Arrays.toString(test.getClass().getDeclaredMethods()));
    
    
        System.out.println("test2.equals(test) = " + test2.equals(test));
        
        
    }
}
