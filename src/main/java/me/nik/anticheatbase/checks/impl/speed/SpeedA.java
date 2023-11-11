package me.nik.anticheatbase.checks.impl.speed;

import me.nik.anticheatbase.checks.enums.CheckType;
import me.nik.anticheatbase.checks.types.Check;
import me.nik.anticheatbase.managers.profile.Profile;
import me.nik.anticheatbase.playerdata.data.impl.MovementData;
import me.nik.anticheatbase.processors.Packet;

public class SpeedA extends Check {
    public SpeedA(Profile profile) {
        super(profile, CheckType.SPEED, "A", "Checks for speed");
    }

    @Override
    public void handle(Packet packet) {
        if (!packet.isMovement() || profile.isExempt().movement()) return;

        //Let's make an example MEME check

        MovementData data = profile.getMovementData();

        if (data.getDeltaXZ() > Double.MIN_VALUE) {

            if (increaseBuffer() > 50) fail(
                    "FLAGGED BY THE BEST SPEED CHECK EVER IN EXISTENCE, Credits to Elon Musk."
            );

        } else decreaseBufferBy(Float.MIN_VALUE);
    }
}