/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1102;

import minetweaker.*;
import minetweaker.api.loadstages.EnumLoadingStage;
import minetweaker.api.logger.FileLogger;
import minetweaker.mc1102.brackets.*;
import minetweaker.mc1102.client.MCClient;
import minetweaker.mc1102.formatting.MCFormatter;
import minetweaker.mc1102.furnace.*;
import minetweaker.mc1102.game.MCGame;
import minetweaker.mc1102.mods.MCLoadedMods;
import minetweaker.mc1102.network.*;
import minetweaker.mc1102.oredict.MCOreDict;
import minetweaker.mc1102.recipes.MCRecipeManager;
import minetweaker.mc1102.server.MCServer;
import minetweaker.mc1102.util.*;
import minetweaker.mc1102.vanilla.MCVanilla;
import minetweaker.runtime.*;
import minetweaker.runtime.providers.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.io.File;

/**
 * Main mod class. Performs some general logic, initialization of the API and
 * FML event handling.
 *
 * @author Stan Hebben
 */
@Mod(modid = MineTweakerMod.MODID, version = "3.0.17", name = MineTweakerMod.NAME, dependencies = "after:JEI@[3.14.2.398,)")
public class MineTweakerMod {
	
	public static final String MODID = "MineTweaker3";
	public static final String NAME = "MineTweaker3";
	public static final String MCVERSION = "1.10.2";
	
	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
	
	private static final String[] REGISTRIES = {"minetweaker.mods.jei.ClassRegistry", "minetweaker.mods.ic2.ClassRegistry"};
	private static final String[] REGISTRY_DESCRIPTIONS = {"JEI mod support", "IC2 mod support"};
	public static MinecraftServer server;
	@Mod.Instance(MODID)
	public static MineTweakerMod INSTANCE;
	
	static {
		NETWORK.registerMessage(MineTweakerLoadScriptsHandler.class, MineTweakerLoadScriptsPacket.class, 0, Side.CLIENT);
		NETWORK.registerMessage(MineTweakerOpenBrowserHandler.class, MineTweakerOpenBrowserPacket.class, 1, Side.CLIENT);
		NETWORK.registerMessage(MineTweakerCopyClipboardHandler.class, MineTweakerCopyClipboardPacket.class, 2, Side.CLIENT);
	}
	
	public final MCRecipeManager recipes;
	private final IScriptProvider scriptsGlobal;
	private final ScriptProviderCustom scriptsIMC;
	
	public MineTweakerMod() {
		MineTweakerImplementationAPI.init(new MCOreDict(), recipes = new MCRecipeManager(), new MCFurnaceManager(), MCGame.INSTANCE, new MCLoadedMods(), new MCFormatter(), new MCVanilla());
		
		MineTweakerImplementationAPI.logger.addLogger(new FileLogger(new File("minetweaker.log")));
		MineTweakerImplementationAPI.platform = MCPlatformFunctions.INSTANCE;
		
		File globalDir = new File("scripts");
		if(!globalDir.exists())
			globalDir.mkdirs();
		scriptsIMC = new ScriptProviderCustom("intermod");
		scriptsGlobal = new ScriptProviderDirectory(globalDir);
		MineTweakerImplementationAPI.setScriptProvider(scriptsGlobal);
	}
	
	// ##########################
	// ### FML Event Handlers ###
	// ##########################
	
	@EventHandler
	public void onIMCEvent(FMLInterModComms.IMCEvent event) {
		event.getMessages().stream().filter(imcMessage -> imcMessage.key.equalsIgnoreCase("addMineTweakerScript")).forEach(imcMessage -> {
			if(imcMessage.isStringMessage()) {
				scriptsIMC.add(imcMessage.getSender() + "::imc", imcMessage.getStringValue());
			} else if(imcMessage.isNBTMessage()) {
				NBTTagCompound message = imcMessage.getNBTValue();
				scriptsIMC.add(imcMessage.getSender() + "::" + message.getString("name"), message.getString("content"));
			}
		});
	}
	
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent ev) {
		MineTweakerAPI.logInfo("MineTweaker: Building registry for PreInit");
		ItemBracketHandler.rebuildItemRegistry();
		LiquidBracketHandler.rebuildLiquidRegistry();
		MineTweakerAPI.logInfo("MineTweaker: Sucessfully built item registry");
		GlobalRegistry.registerBracketHandler(new ItemBracketHandler());
		GlobalRegistry.registerBracketHandler(new LiquidBracketHandler());
		GlobalRegistry.registerBracketHandler(new OreBracketHandler());
		IScriptProvider cascaded = new ScriptProviderCascade(scriptsGlobal);
		
		MineTweakerImplementationAPI.setScriptProvider(cascaded);
//		MineTweakerImplementationAPI.onServerStart(new MCServer(ev.getServer()));
		
		MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
		MinecraftForge.EVENT_BUS.register(new FMLEventHandler());
		MineTweakerAPI.currentStage = EnumLoadingStage.PREINITIALIZATION;
//		MineTweakerImplementationAPI.reload();
	}
	
	@EventHandler
	public void onInit(FMLInitializationEvent ev) {
		MineTweakerAPI.logInfo("MineTweaker: Building registry for Init");
		ItemBracketHandler.rebuildItemRegistry();
		LiquidBracketHandler.rebuildLiquidRegistry();
		MineTweakerAPI.logInfo("MineTweaker: Sucessfully built item registry");
		GlobalRegistry.registerBracketHandler(new ItemBracketHandler());
		GlobalRegistry.registerBracketHandler(new LiquidBracketHandler());
		GlobalRegistry.registerBracketHandler(new OreBracketHandler());
		
		MineTweakerAPI.currentStage = EnumLoadingStage.INITIALIZATION;
//		MineTweakerImplementationAPI.reload();
	}
	
	@EventHandler
	public void onPostInit(FMLPostInitializationEvent ev) {
		MineTweakerAPI.logInfo("MineTweaker: Building registry for PostInit");
		ItemBracketHandler.rebuildItemRegistry();
		LiquidBracketHandler.rebuildLiquidRegistry();
		MineTweakerAPI.logInfo("MineTweaker: Sucessfully built item registry");
		GlobalRegistry.registerBracketHandler(new ItemBracketHandler());
		GlobalRegistry.registerBracketHandler(new LiquidBracketHandler());
		GlobalRegistry.registerBracketHandler(new OreBracketHandler());
		
		MineTweakerAPI.registerClassRegistry(MineTweakerRegistry.class);
		
		for(int i = 0; i < REGISTRIES.length; i++) {
			MineTweakerAPI.registerClassRegistry(REGISTRIES[i], REGISTRY_DESCRIPTIONS[i]);
		}
		FuelTweaker.INSTANCE.register();
		MineTweakerAPI.currentStage = EnumLoadingStage.POSTINITIALIZATION;
//		MineTweakerImplementationAPI.reload();
	}
	
	@EventHandler
	public void onComplete(FMLLoadCompleteEvent ev) {
		MineTweakerAPI.logInfo("MineTweaker: Building registry for LoadComplete");
		ItemBracketHandler.rebuildItemRegistry();
		LiquidBracketHandler.rebuildLiquidRegistry();
		MineTweakerAPI.logInfo("MineTweaker: Sucessfully built item registry");
		GlobalRegistry.registerBracketHandler(new ItemBracketHandler());
		GlobalRegistry.registerBracketHandler(new LiquidBracketHandler());
		GlobalRegistry.registerBracketHandler(new OreBracketHandler());
		
		MineTweakerAPI.currentStage = EnumLoadingStage.AVAILABLE;
//		MineTweakerImplementationAPI.reload();
	}
	
	@EventHandler
	public void onServerAboutToStart(FMLServerAboutToStartEvent ev) {
		MineTweakerAPI.currentStage = EnumLoadingStage.SERVER_ABOUT_TO_START;
//		MineTweakerImplementationAPI.reload();
	}
	
	@EventHandler
	public void onServerStarting(FMLServerStartingEvent ev) {
		server = ev.getServer();
		// starts before loading worlds
		// perfect place to start MineTweaker!
		
		if(MineTweakerPlatformUtils.isClient()) {
			MineTweakerAPI.client = new MCClient();
		}
		MineTweakerImplementationAPI.onServerStart(new MCServer(ev.getServer()));
		MineTweakerAPI.currentStage = EnumLoadingStage.SERVER_STARTING;
		MineTweakerImplementationAPI.reload();
	}
	
	@EventHandler
	public void onServerStarted(FMLServerStartedEvent ev) {
		MineTweakerAPI.currentStage = EnumLoadingStage.SERVER_STARTED;
//		MineTweakerImplementationAPI.reload();
	}
	
	@EventHandler
	public void onServerStopped(FMLServerStoppingEvent ev) {
		MineTweakerAPI.currentStage = EnumLoadingStage.SERVER_STOPPING;
//		MineTweakerImplementationAPI.reload();
	}
	
	@EventHandler
	public void onServerStopped(FMLServerStoppedEvent ev) {
		MineTweakerAPI.currentStage = EnumLoadingStage.SERVER_STOPPED;
//		MineTweakerImplementationAPI.reload();
		MineTweakerImplementationAPI.onServerStop();
		MineTweakerImplementationAPI.setScriptProvider(scriptsGlobal);
		server = null;
	}
}
