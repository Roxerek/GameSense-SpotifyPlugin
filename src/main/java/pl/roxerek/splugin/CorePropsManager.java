package pl.roxerek.splugin;

import java.io.File;

public class CorePropsManager {

    public String getPort(){
        String path = "C:\\ProgramData\\SteelSeries\\GG";

        try {
            File file = new File(path + "\\coreProps.json");
            String result = GsonUtil.fromJson(FileUtil.readAll(file)).get("address");
            return result.substring(result.lastIndexOf(":")+1);
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("[ERROR] Couldn't get port of GameSense address!");
        }
        return null;
    }

}
