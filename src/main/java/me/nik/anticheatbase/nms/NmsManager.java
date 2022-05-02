package me.nik.anticheatbase.nms;

import org.bukkit.Bukkit;

/**
 * A simple NMS Manager class
 * <p>
 * NOTE: Obviously this is not done, You should implement every single nms version yourself
 * Inside the me.nik.anticheatbase.manager.managers.nms.impl package.
 * <p>
 * NMS Can improve perfomance by a LOT even when calling simple methods such as p.getAllowFlight();
 * YourKit profiler doesn't lie!
 */
public class NmsManager {

    private final NmsInstance nmsInstance;

    public NmsManager() {

        String version = Bukkit.getServer().getClass().getPackage().getName();

        version = version.substring(version.lastIndexOf('.') + 1);

        switch (version) {

            /*//----------------------------------------------\\

            case "v1_17_R1":
                this.nmsInstance = new Instance_1_17_R1();
                break;

            //----------------------------------------------\\

            case "v1_16_R3":
                this.nmsInstance = new Instance1_16_R3();
                break;

            case "v1_16_R2":
                this.nmsInstance = new Instance1_16_R2();
                break;

            case "v1_16_R1":
                this.nmsInstance = new Instance1_16_R1();
                break;

            //----------------------------------------------\\

            case "v1_15_R1":
                this.nmsInstance = new Instance1_15_R1();
                break;

            //----------------------------------------------\\

            case "v1_14_R1":
                this.nmsInstance = new Instance1_14_R1();
                break;

            //----------------------------------------------\\

            case "v1_13_R2":
                this.nmsInstance = new Instance1_13_R2();
                break;

            case "v1_13_R1":
                this.nmsInstance = new Instance1_13_R1();
                break;

            //----------------------------------------------\\

            case "v1_12_R1":
                this.nmsInstance = new Instance1_12_R1();
                break;

            //----------------------------------------------\\

            case "v1_11_R1":
                this.nmsInstance = new Instance1_11_R1();
                break;

            //----------------------------------------------\\

            case "v1_10_R1":
                this.nmsInstance = new Instance1_10_R1();
                break;

            //----------------------------------------------\\

            case "v1_9_R2":
                this.nmsInstance = new Instance1_9_R2();
                break;

            case "v1_9_R1":
                this.nmsInstance = new Instance1_9_R1();
                break;

            //----------------------------------------------\\

            case "v1_8_R3":
                this.nmsInstance = new Instance1_8_R3();
                break;

            case "v1_8_R2":
                this.nmsInstance = new Instance1_8_R2();
                break;

            case "v1_8_R1":
                this.nmsInstance = new Instance1_8_R1();
                break;*/

            //----------------------------------------------\\

            default:
                this.nmsInstance = new InstanceDefault();
                break;
        }
    }

    public NmsInstance getNmsInstance() {
        return nmsInstance;
    }
}