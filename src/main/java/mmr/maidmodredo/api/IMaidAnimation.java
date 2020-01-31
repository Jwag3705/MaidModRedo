package mmr.maidmodredo.api;

public interface IMaidAnimation {
    MaidAnimation NO_ANIMATION = MaidAnimation.create(0);

    int getAnimationTick();

    void setAnimationTick(int tick);

    MaidAnimation getAnimation();

    void setAnimation(MaidAnimation animation);

    MaidAnimation[] getAnimations();
}