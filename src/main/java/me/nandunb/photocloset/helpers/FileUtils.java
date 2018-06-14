package me.nandunb.photocloset.helpers;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class FileUtils {

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
}
