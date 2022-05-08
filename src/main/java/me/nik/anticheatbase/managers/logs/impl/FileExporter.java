package me.nik.anticheatbase.managers.logs.impl;

import me.nik.anticheatbase.Anticheat;
import me.nik.anticheatbase.managers.logs.LogExporter;
import me.nik.anticheatbase.managers.logs.PlayerLog;
import me.nik.anticheatbase.utils.ChatUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FileExporter extends LogExporter {

    public FileExporter(Anticheat plugin) {
        super(plugin);
    }

    @Override
    public void initialize() {

        CompletableFuture.runAsync(() -> {

            try {

                if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();

                File logsFile = new File(plugin.getDataFolder(), "data.yml");

                logsFile.createNewFile();

                //---CHECK FOR OLD LOGS---\\

                File tempFile = new File(plugin.getDataFolder(), "data_temp.yml");

                tempFile.createNewFile();

                BufferedReader reader = new BufferedReader(new FileReader(logsFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                Date currentDate = new Date(System.currentTimeMillis());

                String currentLine;

                while ((currentLine = reader.readLine()) != null) {

                    String[] data = currentLine.split(",");

                    if (Math.abs(format.parse(data[5]).getTime() - currentDate.getTime()) > DELETE_DAYS) continue;

                    writer.write(currentLine + System.lineSeparator());
                }

                writer.close();
                reader.close();

                logsFile.delete();

                tempFile.renameTo(logsFile);

            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void shutdown() {
    }

    @Override
    public void logMultiple(Collection<PlayerLog> logs) {
        try {
            File saveTo = new File(plugin.getDataFolder(), "data.yml");

            if (!saveTo.exists()) saveTo.createNewFile();

            FileWriter fw = new FileWriter(saveTo, true);

            PrintWriter pw = new PrintWriter(fw);

            for (PlayerLog log : logs) pw.println(log.toString());

            pw.flush();

            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void log(PlayerLog log) {
        try {

            File saveTo = new File(plugin.getDataFolder(), "data.yml");

            saveTo.createNewFile();

            FileWriter fw = new FileWriter(saveTo, true);

            PrintWriter pw = new PrintWriter(fw);

            pw.println(log.toString());

            pw.flush();

            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PlayerLog> getLogs() {

        final File file = new File(plugin.getDataFolder(), "data.yml");

        if (!file.exists()) return new ArrayList<>();

        CompletableFuture<List<PlayerLog>> future = CompletableFuture.supplyAsync(() -> {

            List<PlayerLog> logs = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {

                for (String line; (line = br.readLine()) != null; ) {

                    String[] data = line.split(",");

                    logs.add(new PlayerLog(data[0], data[1], data[2], data[3], data[4], data[5]));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return logs;
        });

        try {
            return future.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {

            ChatUtils.log("Took more than 5 seconds to load the player logs!");

            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public List<PlayerLog> getLogsForPlayer(String player) {

        final File file = new File(plugin.getDataFolder(), "data.yml");

        if (!file.exists()) return new ArrayList<>();

        CompletableFuture<List<PlayerLog>> future = CompletableFuture.supplyAsync(() -> {

            List<PlayerLog> logs = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {

                for (String line; (line = br.readLine()) != null; ) {

                    String[] data = line.split(",");

                    if (!data[1].equalsIgnoreCase(player)) continue;

                    logs.add(new PlayerLog(data[0], data[1], data[2], data[3], data[4], data[5]));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return logs;
        });

        try {

            return future.get(5, TimeUnit.SECONDS);

        } catch (InterruptedException | ExecutionException | TimeoutException e) {

            ChatUtils.log("Took more than 5 seconds to load the player logs!");

            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}