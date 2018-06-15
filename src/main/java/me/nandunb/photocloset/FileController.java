package me.nandunb.photocloset;

import me.nandunb.photocloset.utilities.AWSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/storage")
public class FileController {

    private final AWSUtils awsUtils;

    @Autowired
    FileController(AWSUtils awsUtils){
        this.awsUtils = awsUtils;
    }

    @GetMapping("/")
    public String welcome(){
        return "Storage API v1.0";
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestPart(value = "file")MultipartFile file){
        return this.awsUtils.uploadFile(file);
    }
}
