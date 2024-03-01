
import com.fayardev.plugindemo.plugin.adapter.UserPluginAdapter;

public class User implements UserPluginAdapter {

    @Override
    public boolean confirm(String username, String password) {
        return true;
    }

}