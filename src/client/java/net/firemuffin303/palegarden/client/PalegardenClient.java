package net.firemuffin303.palegarden.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.firemuffin303.palegarden.common.registry.ModBlocks;
import net.firemuffin303.palegarden.common.registry.ModEntities;
import net.firemuffin303.palegarden.common.registry.ModParticleTypes;
import net.firemuffin303.palegarden.renderer.CreakerModel;
import net.firemuffin303.palegarden.renderer.CreakerRenderer;
import net.minecraft.client.particle.CherryLeavesParticle;
import net.minecraft.client.particle.WaterSuspendParticle;
import net.minecraft.client.render.RenderLayer;

public class PalegardenClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PALE_GRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PALE_SAPLING, RenderLayer.getCutout());

        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.PALE_LEAVES, provider ->  (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> {
            return new PaleLeavesProvider(world,x,y,z,provider);
        });

        EntityRendererRegistry.register(ModEntities.CREAKER, CreakerRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(CreakerModel.CREAKER,CreakerModel::getTexturedModelData);
    }
}
