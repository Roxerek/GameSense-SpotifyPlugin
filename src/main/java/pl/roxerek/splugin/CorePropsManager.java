package pl.roxerek.splugin;

import java.io.File;
import java.util.HashMap;

public class CorePropsManager {

    public String getPort(){
        String path = "C:\\ProgramData\\SteelSeries\\GG";

        try {
            File file = new File(path + "\\coreProps.json");
            HashMap<String, String> res = GsonUtil.fromJson(FileUtil.readAll(file), HashMap.class);
            String result = res.get("address");
            return result.substring(result.lastIndexOf(":")+1);
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("[ERROR] Couldn't get port of GameSense address!");
        }
        return null;
    }

}
