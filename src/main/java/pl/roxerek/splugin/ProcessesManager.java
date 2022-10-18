package pl.roxerek.splugin;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary;


public class ProcessesManager {

    private static Pointer windowPointer;
    private static Pointer processPointer;

    public static interface User32 extends StdCallLibrary {
        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);

        interface WNDENUMPROC extends StdCallCallback {
            boolean callback(Pointer hWnd, Pointer arg);
        }

        boolean EnumWindows(WNDENUMPROC lpEnumFunc, Pointer userData);
        int GetWindowTextA(Pointer hWnd, byte[] lpString, int nMaxCount);
        int GetWindowThreadProcessId(Pointer hWnd, PointerByReference pointer);
    }

    public static interface Psapi extends StdCallLibrary{
        Psapi INSTANCE = (Psapi) Native.loadLibrary("Psapi", Psapi.class);

        WinDef.DWORD GetModuleBaseNameW(Pointer hProcess, Pointer hModule, char[] lpBaseName, int nSize);
    }

    public interface Kernel32 extends StdCallLibrary {
        Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("Kernel32", Kernel32.class);

        Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, Pointer pointer);
        boolean CloseHandle(Pointer hObject);

    }

    public static String getCurrentSong(){

        byte[] text = new byte[512];
        User32.INSTANCE.GetWindowTextA(windowPointer, text, 512);
        return Native.toString(text).trim();
    }

    public static boolean isProcessUpToDate(){
        char[] modName = new char[512];
        Pointer pointer = Kernel32.INSTANCE.OpenProcess(WinNT.SE_PRIVILEGE_USED_FOR_ACCESS, false, processPointer);
        Psapi.INSTANCE.GetModuleBaseNameW(pointer, new Pointer(0), modName, 512);
        Kernel32.INSTANCE.CloseHandle(pointer);
        String name = Native.toString(modName).trim();
        return name.equals("Spotify.exe");
    }

    public static void getSpotifyProcess() {
        User32.INSTANCE.EnumWindows(new User32.WNDENUMPROC() {
            final User32 user32 = User32.INSTANCE;

            @Override
            public boolean callback(Pointer hWnd, Pointer arg) {
                byte[] title = new byte[512];
                char[] modName = new char[512];
                PointerByReference pbr = new PointerByReference();
                user32.GetWindowThreadProcessId(hWnd, pbr);
                user32.GetWindowTextA(hWnd, title, 512);
                String windowTitle = Native.toString(title).trim();

                Pointer pointer = ProcessesManager.Kernel32.INSTANCE.OpenProcess(WinNT.SE_PRIVILEGE_USED_FOR_ACCESS, false, pbr.getValue());
                ProcessesManager.Psapi.INSTANCE.GetModuleBaseNameW(pointer, new Pointer(0), modName, 512);

                ProcessesManager.Kernel32.INSTANCE.CloseHandle(pointer);

                String moduName = Native.toString(modName).trim();

                if(moduName.contains("Spotify") && !windowTitle.isEmpty()){
                    if(!windowTitle.equals("Default IME") && !windowTitle.equals("MSCTFIME UI") && !windowTitle.contains("GDI+ Window")){
                        System.out.println("[INFO] Succesfully found Spotify process");
                        windowPointer = hWnd;
                        processPointer = pbr.getValue();
                    }
                }

                return true;
            }

        }, null);
    }

}
