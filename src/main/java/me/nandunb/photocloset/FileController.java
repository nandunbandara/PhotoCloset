package me.nandunb.photocloset;

import me.nandunb.photocloset.helpers.AmazonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/")
    public String welcome(){
        return "Storage API v1.0";
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestPart(value = "file")MultipartFile file){
        return this.amazonClient.uploadFile(file);
    }
}
