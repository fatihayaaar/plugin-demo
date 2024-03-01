
import com.fayardev.plugindemo.plugin.adapter.FilePluginAdapter;

public class File implements FilePluginAdapter {

    @Override
    public boolean confirm(String filename) {
        return true;
    }

}