package crafttweaker.mc1120.asmfun;

import net.minecraft.launchwrapper.IClassTransformer;

public class CrTTransformerManager implements IClassTransformer {
    
    
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if ("net.minecraft.client.gui.recipebook.RecipeList".equals(name)) {
            CrTRecipeListTransformerHelper.transform(name, transformedName, basicClass);
        }
        
        return basicClass;
    }
}
