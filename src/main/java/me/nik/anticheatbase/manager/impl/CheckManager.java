package me.nik.anticheatbase.manager.impl;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import me.nik.anticheatbase.Anticheat;
import me.nik.anticheatbase.checks.annotations.Testing;
import me.nik.anticheatbase.checks.types.BukkitCheck;
import me.nik.anticheatbase.checks.types.PacketCheck;
import me.nik.anticheatbase.playerdata.Profile;
import me.nik.anticheatbase.utils.Initializer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A check manager class that we'll use in order to automatically register checks on startup
 * And load them directly in the player profile.
 */
public class CheckManager implements Initializer {

    private final List<Constructor<? extends PacketCheck>> packetCheckConstructors = new ArrayList<>();
    private final List<Constructor<? extends BukkitCheck>> bukkitCheckConstructors = new ArrayList<>();

    public CheckManager() {

        List<Class<PacketCheck>> packetCheckClasses;
        List<Class<BukkitCheck>> bukkitCheckClasses;

        /*
        We're using ClassGraph to loop through every class that extends our Check, Which gives us automated registration
        The reason we're checking for every single package and class is because if we're planning on obfuscating this later
        And we use any sort of name or package obfuscation we have to do it this way.
         */
        try (ScanResult scan = new ClassGraph().enableClassInfo().acceptPackages(Anticheat.class.getPackage().getName()).scan()) {

            packetCheckClasses = scan.getSubclasses(PacketCheck.class.getName()).loadClasses(PacketCheck.class);
            bukkitCheckClasses = scan.getSubclasses(BukkitCheck.class.getName()).loadClasses(BukkitCheck.class);
        }

        //We disable any other checks if we're currently having a check with the testing annotation
        boolean testing = false;

        //Register packet checks
        for (final Class<PacketCheck> clazz : packetCheckClasses) {

            //Testing annotation found
            if (clazz.isAnnotationPresent(Testing.class)) testing = true;

            try {

                this.packetCheckConstructors.add(clazz.getConstructor(Profile.class));

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            //We don't need to do any additional looping, We'll only use the testing annotation in one class
            if (testing) break;
        }

        //We don't want to start another loop if we already had a testing check registered already
        if (!testing) {

            //Register bukkit checks
            for (final Class<BukkitCheck> clazz : bukkitCheckClasses) {

                //Testing annotation found
                if (clazz.isAnnotationPresent(Testing.class)) testing = true;

                try {

                    this.bukkitCheckConstructors.add(clazz.getConstructor(Profile.class));

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

                //We don't need to do any additional looping, We'll only use the testing annotation in one class
                if (testing) break;
            }
        }

        /*
        This could be better, But it will only be used on a test server when testing new checks so we don't need to worry
        That much about efficiency in this one.
         */
        if (testing) {
            this.packetCheckConstructors.removeIf(constructor -> !constructor.getDeclaringClass().isAnnotationPresent(Testing.class));
            this.bukkitCheckConstructors.removeIf(constructor -> !constructor.getDeclaringClass().isAnnotationPresent(Testing.class));
        }
    }

    @Override
    public void init() {

    }

    public List<BukkitCheck> getBukkitChecks(Profile profile) {

        final List<BukkitCheck> checks = new LinkedList<>();

        for (final Constructor<? extends BukkitCheck> constructor : this.bukkitCheckConstructors) {

            try {

                final BukkitCheck check = constructor.newInstance(profile);

                if (!check.isEnabled()) continue;

                checks.add(check);

            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }

        return checks;
    }

    public List<PacketCheck> getPacketChecks(Profile profile) {
        final List<PacketCheck> checks = new LinkedList<>();

        for (final Constructor<? extends PacketCheck> constructor : this.packetCheckConstructors) {
            try {
                final PacketCheck check = constructor.newInstance(profile);

                if (!check.isEnabled()) continue;

                checks.add(check);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }

        return checks;
    }

    @Override
    public void shutdown() {
        this.packetCheckConstructors.clear();
        this.bukkitCheckConstructors.clear();
    }
}