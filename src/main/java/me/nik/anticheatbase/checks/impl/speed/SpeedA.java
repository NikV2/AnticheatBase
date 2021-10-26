package me.nik.anticheatbase.checks.impl.speed;

import me.nik.anticheatbase.checks.annotations.Experimental;
import me.nik.anticheatbase.checks.enums.CheckType;
import me.nik.anticheatbase.checks.types.PacketCheck;
import me.nik.anticheatbase.playerdata.Profile;
import me.nik.anticheatbase.playerdata.data.MovementData;
import me.nik.anticheatbase.processors.Packet;

@Experimental
public class SpeedA extends PacketCheck {
    public SpeedA(Profile profile) {
        super(profile, CheckType.SPEED, "A", "Checks for speed");
    }

    @Override
    public void handle(Packet packet) {
        if (!packet.isMovement() || profile.isExempt().movement()) return;

        //Let's make an example check

        MovementData data = profile.getMovementData();

        if (data.getDeltaXZ() > 0D) {

            if (increaseBuffer() > 100) fail(
                    "FLAGGED BY THE BEST SPEED CHECK EVER IN EXISTENCE, Credits to Elon Musk"
            );

        } else decreaseBufferBy(Double.MIN_VALUE);
    }
}