package crafttweaker.mc1120.asmfun;


import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import javax.annotation.Nullable;
import java.util.Map;

@IFMLLoadingPlugin.SortingIndex(1001)
@IFMLLoadingPlugin.TransformerExclusions("crafttweaker.mc1120.asmfun")
@IFMLLoadingPlugin.MCVersion("1.12.2")
public class CrTLoadingPlugin implements IFMLLoadingPlugin {
    
    @Override
    public String[] getASMTransformerClass() {
        return new String[] {CrTTransformerManager.class.getName()};
    }
    
    @Override
    public String getModContainerClass() {
        return null;
    }
    
    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }
    
    @Override
    public void injectData(Map<String, Object> data) {
    
    }
    
    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
