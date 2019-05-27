package kz.zvezdochet.core.tool;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Фильтр файлов по расширению
 * @author Natalie Didenko
 *
 */
public class ExtensionFileFilter implements FilenameFilter {
    String substring;

    public ExtensionFileFilter(String ext){
        substring = "." + ext;
    }
    
	public boolean accept(File dir, String name) {
        return name.endsWith(substring);
	}
}
