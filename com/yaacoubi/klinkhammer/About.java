package com.yaacoubi.klinkhammer;

import javax.swing.*;

@SuppressWarnings("serial")
public class About extends JDialog
{
	public About()
	{
		initComponents();
	}

	private void initComponents()
	{
        informationPanel			= new JPanel();
        ykilLogo					= new JLabel();
        labelYKILTitle				= new JLabel();
        labelDeveloper				= new JLabel();
        labelPhilip					= new JLabel();
        labelMansour				= new JLabel();
        labelCopyright				= new JLabel();

        setTitle("About YKIL");
        setMinimumSize(new java.awt.Dimension(500, 300));
        setPreferredSize(new java.awt.Dimension(500, 300));
        setResizable(false);

        ykilLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ykilLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yaacoubi/klinkhammer/images/ykillogo.png"))); // NOI18N
        ykilLogo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(ykilLogo, java.awt.BorderLayout.CENTER);
        setLocationRelativeTo(getParent());
        setModal(true);

        labelDeveloper.setText("Developers:");

        labelPhilip.setText("Philip Klinkhammer");

        labelMansour.setText("Mansour Yaacoubi");

        labelCopyright.setText("Copyright 2014 YKIL. All rights reserved.");

        javax.swing.GroupLayout informationPanelLayout = new javax.swing.GroupLayout(informationPanel);
        informationPanel.setLayout(informationPanelLayout);
        informationPanelLayout.setHorizontalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelDeveloper)
                .addGap(18, 18, 18)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelMansour)
                    .addComponent(labelPhilip))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, informationPanelLayout.createSequentialGroup()
                .addContainerGap(394, Short.MAX_VALUE)
                .addComponent(labelCopyright)
                .addContainerGap())
        );
        informationPanelLayout.setVerticalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDeveloper)
                    .addComponent(labelPhilip))
                .addGap(18, 18, 18)
                .addComponent(labelMansour)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(labelCopyright)
                .addContainerGap())
        );

        getContentPane().add(informationPanel, java.awt.BorderLayout.PAGE_END);

        labelYKILTitle.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelYKILTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelYKILTitle.setText("YKIL - Yaacoubi Klinkhammer Image Locator Version 1.1");
        getContentPane().add(labelYKILTitle, java.awt.BorderLayout.PAGE_START);
	}

    private JPanel informationPanel;
    private JLabel ykilLogo;
    private JLabel labelYKILTitle;
    private JLabel labelDeveloper;
    private JLabel labelPhilip;
    private JLabel labelMansour;
    private JLabel labelCopyright;
}
