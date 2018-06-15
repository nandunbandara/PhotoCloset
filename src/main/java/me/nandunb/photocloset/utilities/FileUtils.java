package me.nandunb.photocloset.utilities;

import me.nandunb.photocloset.exceptions.InvalidFileTypeException;
import me.nandunb.photocloset.exceptions.MissingFileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class FileUtils {

    private static final String[] VALID_EXTENSIONS = {".jpg", ".jpeg", ".png", ".gif", ".webp", ".svg", ".ai", ".eps"};

    public static boolean isNull(MultipartFile file) throws MissingFileException {
        if(file.isEmpty() || file == null)
            throw new MissingFileException();

        return false;
    }

    public static File convertMultiPartToFile(MultipartFile file) throws IOException {
        File output = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(output);
        fos.write(file.getBytes());
        fos.close();
        return output;
    }

    public static String generateFileName(MultipartFile file){
        return new Date().getTime() + "-" +
                file.getOriginalFilename().replace(" ", "_");
    }

    public static boolean validateFileType(MultipartFile file) throws InvalidFileTypeException {
        String fileName = file.getOriginalFilename();
        for (String extension: VALID_EXTENSIONS) {
            if(fileName.contains(extension))
                return true;
        }

        throw new InvalidFileTypeException(getFileExtension(fileName));
    }

    private static String getFileExtension(String fileName) {

        try {

            return fileName.substring(fileName.indexOf('.') + 1);

        } catch (Exception e) {

            return null;

        }

    }
}
