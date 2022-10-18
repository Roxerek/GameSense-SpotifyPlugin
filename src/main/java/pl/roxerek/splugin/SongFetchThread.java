package pl.roxerek.splugin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SongFetchThread {

    private final ActionsHandler actionsHandler;

    public SongFetchThread(ActionsHandler actionsHandler){
        this.actionsHandler = actionsHandler;
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.scheduleAtFixedRate(() -> {
            if(ProcessesManager.isProcessUpToDate()){
                String song = ProcessesManager.getCurrentSong();
                String[] songInfo = song.contains(" - ") ? song.split(" - ") : new String[]{"Spotify status", "Idling..."};
                System.out.println("Sending " + songInfo[0] + " " + songInfo[1]);
                actionsHandler.sendGameEvent(GsonUtil.toJson("SPOTYPLUG", "UPDATE_SONG", songInfo[0], songInfo[1]));
            }else {
                System.out.println("[INFO] Process has changed");
            }
        }, 1, 5, TimeUnit.SECONDS);

    }

}
