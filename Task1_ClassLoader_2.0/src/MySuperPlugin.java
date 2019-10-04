import Plugin.Plugin;

public class MySuperPlugin implements Plugin {
    @Override
    public void doUseful(){
        System.out.println("MySuperPlugin in BrowserPlugins is doing something useful");
    };
}
