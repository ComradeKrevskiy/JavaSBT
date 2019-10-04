import java.net.URL;
import java.net.URLClassLoader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import Plugin.Plugin;

public class PluginManager {
    private final String pluginRootDirectory;

    PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
    }

    Plugin load(String pluginName, String pluginClassName) throws IOException, ClassNotFoundException, NoClassDefFoundError, NoSuchMethodException, IllegalAccessException, ClassCastException, InstantiationException,  InvocationTargetException {
        URL[] urlArray = new URL[1]; // URLClassLoader принимает только массив
        urlArray[0] = new URL(pluginRootDirectory);
        URLClassLoader urlLoader = new URLClassLoader(urlArray);

        Plugin plugin = (Plugin) urlLoader.loadClass(pluginName + "." + pluginClassName).newInstance();
        return plugin;
    }
}

