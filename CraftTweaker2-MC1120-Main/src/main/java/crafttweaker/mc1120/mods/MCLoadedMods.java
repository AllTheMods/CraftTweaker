package crafttweaker.mc1120.mods;

import crafttweaker.api.mods.*;
import net.minecraftforge.fml.common.*;

import java.util.*;

/**
 * @author Stan
 */
public class MCLoadedMods implements ILoadedMods {

    public MCLoadedMods() {
    }

    @Override
    public boolean contains(String name) {
        return Loader.isModLoaded(name);
    }

    @Override
    public IMod get(String name) {
        for(ModContainer mod : Loader.instance().getActiveModList()) {
            if(mod.getModId().equals(name)) {
                return new MCMod(mod);
            }
        }

        return null;
    }

    @Override
    public Iterator<IMod> iterator() {
        List<ModContainer> mods = Loader.instance().getActiveModList();
        List<IMod> iMods = new ArrayList<>();
        
        for(ModContainer mod : mods) {
            iMods.add(new MCMod(mod));
        }
        return iMods.iterator();
    }
}
