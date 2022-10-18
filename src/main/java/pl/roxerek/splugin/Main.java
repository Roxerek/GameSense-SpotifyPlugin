package pl.roxerek.splugin;


public class Main {

    public static void main(String[] args) {

        CorePropsManager corePropsManager = new CorePropsManager();
        ActionsHandler actionsHandler = new ActionsHandler(corePropsManager);

        new SystemTrayImpl();

        //Get port that application runs on
        final String port = corePropsManager.getPort();
        if(port != null){
            System.out.println("[INFO] Found GameSense working on port " + corePropsManager.getPort());
        }

        //Register GameSense event
        actionsHandler.registerGameEvent();

        //Fetch processes needed to receive data
        ProcessesManager.getSpotifyProcess();

        //Registering thread which controls song updates
        SongFetchThread thread = new SongFetchThread(actionsHandler);


    }


}
