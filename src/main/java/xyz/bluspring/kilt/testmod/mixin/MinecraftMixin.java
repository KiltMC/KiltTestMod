package xyz.bluspring.kilt.testmod.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.bluspring.kilt.testmod.KiltTestMod;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void ktm$testMixinFunctions(GameConfig p_91084_, CallbackInfo ci) {
        KiltTestMod.LOGGER.info("KiltTestMod is running!");
    }
}
