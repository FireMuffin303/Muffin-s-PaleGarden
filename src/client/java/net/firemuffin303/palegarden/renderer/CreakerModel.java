package net.firemuffin303.palegarden.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.firemuffin303.palegarden.Palegarden;
import net.firemuffin303.palegarden.common.entity.CreakerEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CreakerModel<T extends CreakerEntity> extends SinglePartEntityModel<T> {
    public static final EntityModelLayer CREAKER = new EntityModelLayer(Identifier.of(Palegarden.MOD_ID,"creaker"),"main");

    private final ModelPart body;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart left_hand;
    private final ModelPart right_hand;
    public CreakerModel(ModelPart root) {
        this.body = root.getChild("body");
        this.neck = body.getChild("neck");
        this.head = neck.getChild("head");
        this.left_hand = body.getChild("left_hand");
        this.right_hand = body.getChild("right_hand");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-9.0F, -11.0F, -15.0F, 18.0F, 22.0F, 30.0F, new Dilation(0.0F))
                .uv(0, 52).cuboid(-8.0F, -7.0F, -2.0F, 16.0F, 22.0F, 30.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 7.0F, 1.0F));

        ModelPartData neck = body.addChild("neck", ModelPartBuilder.create().uv(76, 88).cuboid(-10.0F, -21.5F, -13.0F, 18.0F, 23.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, -1.5F, -18.0F));

        ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(72, 28).cuboid(-9.0F, -21.0F, -1.0F, 18.0F, 22.0F, 24.0F, new Dilation(0.0F))
                .uv(64, 111).cuboid(-9.0F, -21.0F, 23.0F, 0.0F, 16.0F, 16.0F, new Dilation(0.0F))
                .uv(44, 88).cuboid(9.0F, -21.0F, 23.0F, 0.0F, 16.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, -22.5F, -3.0F));

        ModelPartData left_hand = body.addChild("left_hand", ModelPartBuilder.create().uv(124, 107).cuboid(-6.0F, -5.0F, -20.0F, 12.0F, 12.0F, 20.0F, new Dilation(0.0F))
                .uv(66, 0).cuboid(1.0F, 2.0F, -22.0F, 6.0F, 7.0F, 8.0F, new Dilation(0.0F))
                .uv(0, 52).cuboid(-7.0F, 2.0F, -22.0F, 6.0F, 7.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(6.0F, 7.0F, -15.0F));

        ModelPartData right_hand = body.addChild("right_hand", ModelPartBuilder.create().uv(0, 104).cuboid(-6.0F, -5.0F, -20.0F, 12.0F, 12.0F, 20.0F, new Dilation(0.0F))
                .uv(0, 15).cuboid(1.0F, 2.0F, -22.0F, 6.0F, 7.0F, 8.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-7.0F, 2.0F, -22.0F, 6.0F, 7.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(-7.0F, 7.0F, -15.0F));
        return TexturedModelData.of(modelData, 256, 256);
    }

    @Override
    public void setAngles(CreakerEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.neck.pitch = headPitch * 0.017453292F;
        this.neck.yaw = headYaw * 0.017453292F;

        this.updateAnimation(entity.idleAnimationState,CreakerAnimation.IDLE,animationProgress);
        this.updateAnimation(entity.attackingAnimationState,CreakerAnimation.ATTACK,animationProgress);
        this.updateAnimation(entity.roarAnimationState,CreakerAnimation.ROAR,animationProgress);
    }

    @Override
    public ModelPart getPart() {
        return this.body;
    }

    public static class CreakerAnimation{
        public static final Animation IDLE = Animation.Builder.create(0.0F)
                .addBoneAnimation("left_hand", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(5.0F, -10.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("right_hand", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(5.0F, 7.5F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .build();

        public static final Animation ROAR = Animation.Builder.create(1.75F)
                .addBoneAnimation("neck", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.25F, AnimationHelper.createRotationalVector(-12.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.375F, AnimationHelper.createRotationalVector(-12.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.625F, AnimationHelper.createRotationalVector(15.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.75F, AnimationHelper.createRotationalVector(12.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.875F, AnimationHelper.createRotationalVector(15.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.0F, AnimationHelper.createRotationalVector(12.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.0833F, AnimationHelper.createRotationalVector(15.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.2083F, AnimationHelper.createRotationalVector(12.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.3333F, AnimationHelper.createRotationalVector(15.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.4583F, AnimationHelper.createRotationalVector(12.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.5833F, AnimationHelper.createRotationalVector(15.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.7083F, AnimationHelper.createRotationalVector(12.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.75F, AnimationHelper.createRotationalVector(15.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("neck", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 2.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.375F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 2.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.625F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.75F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("left_hand", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.625F, AnimationHelper.createRotationalVector(15.0F, -12.5F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("right_hand", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.625F, AnimationHelper.createRotationalVector(17.5F, 7.5F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.25F, AnimationHelper.createRotationalVector(17.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.375F, AnimationHelper.createRotationalVector(17.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.625F, AnimationHelper.createRotationalVector(-20.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.75F, AnimationHelper.createRotationalVector(-12.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.875F, AnimationHelper.createRotationalVector(-20.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.0F, AnimationHelper.createRotationalVector(-12.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.0833F, AnimationHelper.createRotationalVector(-20.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.2083F, AnimationHelper.createRotationalVector(-12.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-20.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.4583F, AnimationHelper.createRotationalVector(-12.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.5833F, AnimationHelper.createRotationalVector(-20.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.7083F, AnimationHelper.createRotationalVector(-12.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.75F, AnimationHelper.createRotationalVector(-20.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .build();

        public static final Animation ATTACK = Animation.Builder.create(0.4583F)
                .addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(-30.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.0833F, AnimationHelper.createRotationalVector(-30.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.2083F, AnimationHelper.createRotationalVector(2.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("neck", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(25.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.125F, AnimationHelper.createRotationalVector(25.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.2917F, AnimationHelper.createRotationalVector(20.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("left_hand", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(-16.7336F, -37.1219F, 3.2312F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.125F, AnimationHelper.createRotationalVector(-16.7336F, -37.1219F, 3.2312F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.2917F, AnimationHelper.createRotationalVector(40.205F, -19.8218F, 14.7346F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("left_hand", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(5.0F, 8.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.125F, AnimationHelper.createTranslationalVector(5.0F, 8.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(-1.0F, 3.0F, -4.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("right_hand", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(20.0F, 27.5F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.2083F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.4167F, AnimationHelper.createRotationalVector(2.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .build();
    }

}
