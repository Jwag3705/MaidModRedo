package mmr.maidmodredo.api;

public class MaidAnimation {
    private int duration;

    public MaidAnimation(int duration) {
        this.duration = duration;
    }

    public static MaidAnimation create(int duration) {
        return new MaidAnimation(duration);
    }

    public int getDuration() {
        return duration;
    }
}
