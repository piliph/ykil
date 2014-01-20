package com.yaacoubi.klinkhammer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class SplashScreen {

    private JDialog dialog;
    private YKILUI app;
    private JProgressBar progress;

    public SplashScreen()
    {
    	app = new YKILUI();
    }
    protected void initUI() {
    	showSplashScreen();
    	app.setVisible(true);
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {

            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i < 100; i++) {
                    Thread.sleep(20);// Simulate loading
                    publish(i);// Notify progress
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                progress.setValue(chunks.get(chunks.size() - 1));
            }

            @Override
            protected void done() {
                hideSplashScreen();
            }

        };
        worker.execute();
    }

    protected void hideSplashScreen() {
        dialog.setVisible(false);
        dialog.dispose();
    }

    protected void showSplashScreen() {
        dialog = new JDialog((Frame) null);
        dialog.setModal(false);
        dialog.setAlwaysOnTop(true);
        dialog.setUndecorated(true);
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/com/yaacoubi/klinkhammer/images/splash.png")));
        background.setLayout(new BorderLayout());
        dialog.add(background);
        JLabel text = new JLabel("Loading, please wait...");
        text.setForeground(Color.YELLOW);
        text.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));
        background.add(text);
        progress = new JProgressBar();
        background.add(progress, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}