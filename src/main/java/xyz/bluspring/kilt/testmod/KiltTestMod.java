package xyz.bluspring.kilt.testmod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(KiltTestMod.MODID)
public class KiltTestMod {
    static {
        System.out.println("am i here?");
        System.out.println("if that worked i should be here.");
    }

    // Define mod id in a common place for everything to reference
    public static final String MODID = "kilt_test_mod";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "KiltTestMod" namespace
    public static final DeferredRegister<Block> BLOCK_DEFERRED_REGISTER  = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);;

    // Create a Deferred Register to hold Items which will all be registered under the "KiltTestMod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    // Creates a new Block with the id "KiltTestMod:example_block", combining the namespace and path
    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCK_DEFERRED_REGISTER.register("example_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    // Creates a new BlockItem with the id "KiltTestMod:example_block", combining the namespace and path
    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties()));

    public KiltTestMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCK_DEFERRED_REGISTER.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }

        @SubscribeEvent
        public static void onCreativeTabBuild(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
                event.accept(EXAMPLE_BLOCK_ITEM);
            }
        }
    }
}
