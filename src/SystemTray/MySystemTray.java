package SystemTray;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.awt.AWTException;
import java.awt.Component;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Administrator
 */
public class MySystemTray {

    private final Component component;
    private TrayIcon trayIcon;
    private SystemTray systemTray;
    private final PopupMenu menu;

    public MySystemTray(Component ui) {
        this.component = ui;
        this.menu = new PopupMenu("Menu");
        this.menu.addSeparator();
        this.menu.add(showMenuItem());
        this.menu.add(hideMenuItem());
        this.menu.addSeparator();
    } 

    public boolean initSystemTray() {
        if (!SystemTray.isSupported()) {
            return false;
        }
        this.systemTray = SystemTray.getSystemTray();
        return this.systemTray != null;
    }

    public boolean initTrayIcon(String imagePath, String title) {
        if (imagePath == null || title == null) {
            return false;
        }
        Image image = Toolkit.getDefaultToolkit().createImage(imagePath);
        this.trayIcon = new TrayIcon(image, title, menu);
        mouseEvent();
        return this.systemTray != null && this.trayIcon != null;
    }

    public void apply() throws AWTException {
        if (isExsits()) {
            return;
        }
        trayIcon.setImageAutoSize(true);
        this.systemTray.add(trayIcon);
    }
    
    public void reject(){
        if (isExsits()) {
            this.systemTray.remove(trayIcon);
        }
    }

    public void addMenuItem(MenuItem menuItem) {
        if (menuItem == null) {
            return;
        }
        this.menu.add(menuItem);
    }

    public void clearMenu() {
        this.menu.removeAll();
    }

    private MenuItem showMenuItem() {
        MenuItem menuItem = new MenuItem("Show");
        menuItem.addActionListener((ActionEvent e) -> {
            this.component.setVisible(true);
        });
        return menuItem;
    }

    private MenuItem hideMenuItem() {
        MenuItem menuItem = new MenuItem("Hide");
        menuItem.addActionListener((ActionEvent e) -> {
            this.component.setVisible(false);
        });
        return menuItem;
    }

    private boolean isExsits() {
        if (this.systemTray == null || this.trayIcon == null) {
            return false;
        }
        for (TrayIcon tray : this.systemTray.getTrayIcons()) {
            if (tray.equals(this.trayIcon)) {
                return true;
            }
        }
        return false;
    }
    
    private void mouseEvent() {
        this.trayIcon.addMouseListener( new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 1 && e.getButton() == MouseEvent.BUTTON1) {
                    component.setVisible(true);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

}
