package ch.jeda.platformer;

import ch.jeda.physics.Body;

public class DestructibleBody extends Body {

    double fatigue;
    double fatigueThreshold;

    DestructibleBody() {
        this.fatigue = 0.0;
        this.fatigueThreshold = 0.5;
    }

    /**
     * Returns the current fatigue of this body. The fatigue is the sum of all impulses above the fatigue threshold that
     * have been acting on this body so far.
     *
     * @return the current fatigue of this body
     *
     * @since 1.6
     */
    public final double getFatigue() {
        return this.fatigue;
    }

    /**
     * Returns the fatigue threshold.
     *
     * @return the fatigue threshold
     *
     * @since 1.6
     */
    public double getFatigueThreshold() {
        return this.fatigueThreshold;
    }

    public final void setFatigue(final double fatigue) {
        this.fatigue = fatigue;
    }

    public final void setFatigueThreshold(final double fatigueThreshold) {
        this.fatigueThreshold = fatigueThreshold;
    }

}
