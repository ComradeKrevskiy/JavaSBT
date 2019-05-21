//this class depends only from

import Plugin.Plugin;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
            MySuperPlugin plugin_ = new MySuperPlugin();
            plugin_.getClass().getClassLoader().loadClass("MySuperPlugin").getMethod("doUseful").invoke(plugin_);

            PluginManager manager = new PluginManager("file:\\C:\\Users\\kremi\\Desktop\\Java2019\\untitled\\out\\production\\MyPlugins\\");
            Plugin plugin1 = manager.load("MySuperPlugin", "MySuperPlugin");
            plugin1.doUseful();
    }
}