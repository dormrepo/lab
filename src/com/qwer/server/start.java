package com.qwer.server;

import com.qwer.API.Plugin;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class start extends javax.swing.JFrame {

    public start() {
        try {
            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400,300);
        getContentPane().setLayout(new FlowLayout());
        createDesktop();
    }

    private void createDesktop() {
        ArrayList<Plugin> plugins = PluginFactory.getPlugins();
        for (Plugin p : plugins) {
            this.getContentPane().add(p.getButton());
        }
    }


    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new start().setVisible(true);
            }
        });
    }
}
