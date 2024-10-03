package delta.cion.modular;

import java.io.File;

public class ModuleCore {

    public static void ModuleInitialize() {
        try {
            File mainDir = new File("modules");
            if (!mainDir.exists()) mainDir.createNewFile();
        } catch (Exception ignored) {}
    }

    private void StartModule(File[] modules) {
        for (File module : modules) {
            module.getClass();
        }
    }
}
