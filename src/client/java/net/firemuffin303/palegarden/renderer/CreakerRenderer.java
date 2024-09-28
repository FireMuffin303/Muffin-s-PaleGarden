package net.firemuffin303.palegarden.renderer;

import net.firemuffin303.palegarden.Palegarden;
import net.firemuffin303.palegarden.common.entity.CreakerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class CreakerRenderer extends MobEntityRenderer<CreakerEntity,CreakerModel<CreakerEntity>> {
    private static final Identifier TEXTURE = Identifier.of(Palegarden.MOD_ID,"textures/entity/creaker.png");

    public CreakerRenderer(EntityRendererFactory.Context context) {
        super(context, new CreakerModel<>(context.getPart(CreakerModel.CREAKER)), 0.9f);
    }

    @Override
    public Identifier getTexture(CreakerEntity entity) {
        return TEXTURE;
    }
}
