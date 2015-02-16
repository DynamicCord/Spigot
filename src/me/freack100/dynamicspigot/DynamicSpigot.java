/*
 *
 *  * Copyright © 2015 Paul Waslowski <freack1208@gmail.com>
 *  * This work is free. You can redistribute it and/or modify it under the
 *  * terms of the Do What The Fuck You Want To Public License, Version 2,
 *  * as published by Sam Hocevar. See the LICENSE file for more details.
 *
 */

package me.freack100.dynamicspigot;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.net.Socket;

public class DynamicSpigot extends JavaPlugin {

    @Override
    public void onEnable(){
        try {
            Socket socket = new Socket("127.0.0.1",1337);
            DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());

            File file = new File("dynamicBungee.yml");
            if(!file.exists()) file.createNewFile();
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

            String ip = configuration.getString("ip");
            String port = configuration.getString("port");
            String name = configuration.getString("name");

            outToServer.writeBytes("create;"
                    +name+";"
                    +ip+";"
                    +port+";"
            );

            outToServer.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable(){
        try {
            Socket socket = new Socket("127.0.0.1",1337);
            DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());

            File file = new File("dynamicBungee.yml");
            if(!file.exists()) file.createNewFile();
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

            String name = configuration.getString("name");

            outToServer.writeBytes("remove;"+name+";"
            );

            outToServer.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
