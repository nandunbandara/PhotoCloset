package me.nandunb.photocloset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/storage/")
public class FileController {

    private AmazonClient amazonClient;

    @Autowired
    FileController(AmazonClient amazonClient){
        this.amazonClient = amazonClient;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestPart(value = "file")MultipartFile file){
        return this.amazonClient.uploadFile(file);
    }
}
