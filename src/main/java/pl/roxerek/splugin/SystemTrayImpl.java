package pl.roxerek.splugin;

import java.awt.*;

public class SystemTrayImpl {

    public SystemTrayImpl(){
        if(SystemTray.isSupported()){
            SystemTray tray = SystemTray.getSystemTray();
            Image img = Toolkit.getDefaultToolkit().getImage(Main.class.getClassLoader().getResource("icon.png"));

            PopupMenu menu = new PopupMenu();
            MenuItem exitItem = new MenuItem("Exit");
            exitItem.addActionListener(e -> {System.exit(0);});
            menu.add(exitItem);

            TrayIcon icon = new TrayIcon(img, "SpotyPlug", menu);

            try {
                tray.add(icon);
            }catch (Exception e){
                e.printStackTrace();
            }

        }else {
            System.err.println("[ERROR] SystemTray is not supported!");
        }
    }

}
