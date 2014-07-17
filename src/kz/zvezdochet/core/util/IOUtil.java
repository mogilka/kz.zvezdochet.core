package kz.zvezdochet.core.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import kz.zvezdochet.core.tool.ExtensionFileFilter;


/**
 * Класс, предоставляющий методы для работы с файлами и потоками
 * @author Nataly Didenko
 */
public class IOUtil {

	/**
	 * Чтение текста из файла
	 * @param fileName текстовый файл
	 * @return текст в виде строки
	 */
	public static String getTextFromFile(String fileName) {
		FileInputStream is = null;
		byte buf[];
		try {
			
			is = new FileInputStream(fileName);
			buf = new byte[is.available()];
			is.read(buf);
			is.close();
			if (buf != null && buf.length > 0)
				return new String(buf);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (SecurityException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Создание файла
	 * @param fileName имя файла
	 * @param data данные
	 * @return новый файл
	 */
	public static File createFile(String fileName, String data) {
		File f = new File(fileName);
		try {
			//создание физического файла
			FileOutputStream fos = new FileOutputStream(f);
			//запись данных в файл
			DataOutputStream dos = new DataOutputStream(fos);
			for (int i = 0; i < 10; i++)
				dos.writeBytes(data);
			dos.close();
	
			System.out.println("Absolute path: " + f.getAbsolutePath());
			System.out.println("Canonical path: " + f.getCanonicalPath());
			System.out.println("Length: " + f.length());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f;
	}

	/**
	 * Переименование файла
	 * @param f файл
	 * @param fileName новое имя файла
	 * @throws Exception
	 */
	public static void renameFile(File f, String fileName) throws Exception {
		if(!f.renameTo(new File(fileName)))
		  throw new Exception("Rename failed");
	}
	
	/**
	 * Удаление файла
	 * @param f файл
	 * @throws Exception
	 */
	public static void deleteFile(File f) throws Exception {
		if(!f.delete())
		  throw new Exception("Delete failed");
	}

	/**
	 * Копирование файла
	 * @param oldName старое местонахождение
	 * @param newName новое местонахождение
	 */
	public static void copyFile(String oldName, String newName) {
		try {
			FileInputStream fis = new FileInputStream(oldName);
			FileOutputStream fos = new FileOutputStream(newName);
			fileCopy(fis, fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Физическое перемещение байтов из одного файла в другой
	 * @param is входной поток
	 * @param os выходной поток
	 * @throws IOException
	 */
	public static void fileCopy(InputStream is, OutputStream os) throws IOException	{
		int length;
		byte[] buf = new byte[4096];
		while ((length = is.read(buf)) > 0) 
			os.write(buf, 0, length);
		is.close();
		os.close();
	}

	/**
	 * Создание указанного файлового пути (включая подкаталоги)
	 * @param directiryPath путь к каталогу
	 * @throws Exception
	 */
	public static void createPath(String directiryPath) throws Exception {
		File dir = new File(directiryPath);
		if (!dir.exists())
			if (dir.mkdirs())
				throw new Exception("Path create failed");
	}

	/**
	 * Копирование файла.
	 * Более быстрый способ, но есть ограничения на размер файла
	 * @param in исходный файл
	 * @param out целевой файл
	 * @throws IOException
	 */
	public static void copyFile(File in, File out) throws IOException {
		FileChannel inChannel = new FileInputStream(in).getChannel();
		FileChannel outChannel = new FileOutputStream(out).getChannel();
		try {
			inChannel.transferTo(0, inChannel.size(), outChannel);
		} catch (IOException e) {
			throw e;
		} finally {
			if (inChannel != null) inChannel.close();
			if (outChannel != null) outChannel.close();
		}
	}

	/**
	 * Копирование директории вместе с содержимым
	 * @param sourceLocation исходное местоположение
	 * @param targetLocation новое местоположение
	 * @throws IOException
	 */
	public static void copyDirectory(File sourceLocation, File targetLocation) throws IOException {
        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) 
                targetLocation.mkdir();
            String[] children = sourceLocation.list();
            for (int i = 0; i < children.length; i++) 
                copyDirectory(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
        } else 
            fileCopy(new FileInputStream(sourceLocation), new FileOutputStream(targetLocation));
    }

	/**
	 * Поиск файлов с указанным расширением в директории
	 * @param path путь к директории
	 * @param extension расширение
	 */
	public static String[] getFiles(String path, String extension) {
		File directory = new File(path);
		System.out.println("directory: " + directory.getPath());
		FilenameFilter filter = new ExtensionFileFilter(extension);
		return directory.list(filter);
	}
}
