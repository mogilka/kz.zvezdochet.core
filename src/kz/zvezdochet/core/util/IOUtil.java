package kz.zvezdochet.core.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import kz.zvezdochet.core.tool.ExtensionFileFilter;

/**
 * Утилита для работы с файлами и потоками
 * @author Natalie Didenko
 */
public class IOUtil {

	/**
	 * Чтение текста из файла
	 * @param fileName текстовый файл
	 * @return текст в виде строки
	 */
	public static String getTextFromFile(String fileName) {
		try {
			String encoding = StandardCharsets.UTF_8.name();
			byte[] encoded = Files.readAllBytes(Paths.get(fileName));
			return new String(encoded, encoding);
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
	public static void createFile(String fileName, String data) {
		try (PrintWriter out = new PrintWriter(fileName)) {
		    out.println(data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
	@SuppressWarnings("resource")
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

	/**
	 * Получение содержимого URI с сервера через GET-метод
	 * @param uri URI в строковом представлении с GET-параметрами
	 * @return содержимое URI в строковом представлении
	 * @throws IOException
	 */
	public static String getUriContent(final String uri) throws IOException {
        HttpEntity entity = null;
        InputStream input = null;
        DataInputStream dis = null;
        try {
        	HttpClient httpClient = HttpClientBuilder.create().build();
        	final HttpResponse res = httpClient.execute(new HttpGet(uri));
            entity = res.getEntity();
            final BufferedHttpEntity bufferedEntity = new BufferedHttpEntity(entity);
            input = bufferedEntity.getContent();
            
            ByteArrayOutputStream buff = new ByteArrayOutputStream();
            dis = new DataInputStream(input);
            byte[] stuff = new byte[1024];
            int read = 0;
            while ((read = dis.read(stuff)) != -1)
            	buff.write(stuff, 0, read);
            return new String(buff.toByteArray());
   		} catch (Exception e) {
    		System.out.println(e.getMessage());
        } finally {
        	if (dis != null)
        		dis.close();
        	if (input != null)
        		input.close();
        }
        return null;
    }

	/**
	 * Проверка наличия файла
	 * @param path путь к файлу
	 * @return true - файл по указанному пути существует и не является директорией
	 */
	public static boolean isFileExists(String path) {
		File f = new File(path);
		return f.exists() && !f.isDirectory();
	}
}
