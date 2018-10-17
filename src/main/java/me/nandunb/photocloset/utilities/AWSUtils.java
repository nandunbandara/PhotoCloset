package me.nandunb.photocloset.utilities;

import com.amazonaws.Response;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import me.nandunb.photocloset.configs.AWSConfigs;
import me.nandunb.photocloset.exceptions.InvalidFileTypeException;
import me.nandunb.photocloset.exceptions.MissingFileException;
import me.nandunb.photocloset.preprocessors.ResponsePreprocessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Service
public class AWSUtils {

    private AmazonS3 s3client;
    private AWSConfigs config;

    @Autowired
    AWSUtils(AWSConfigs config){
        this.config = config;
    }

    @PostConstruct
    private void initialize(){

        String accessKey = config.getAccessKey();
        String secretKey = config.getSecretKey();

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        this.s3client = new AmazonS3Client(credentials);

    }

    public ResponseEntity<Object> uploadFile(MultipartFile multipartFile){

        String fileUrl = "";

        try{

            if(FileUtils.validateFileType(multipartFile)) {

                File file = FileUtils.convertMultiPartToFile(multipartFile);
                String fileName = FileUtils.generateFileName(multipartFile);

                fileUrl = config.getEndpointUrl()
                        + "/" + config.getBucketName()
                        + "/" + fileName;

                uploadFileToS3(fileName, file);
                file.delete();

            }



        } catch (IOException | InvalidFileTypeException e) {

            return ResponsePreprocessor.sendFailureResponse(e);

        }

        return ResponsePreprocessor.sendSuccessResponse(fileUrl);
    }

    //Upload file to the S3 Bucket
    private void uploadFileToS3(String fileName, File file){

        s3client.putObject(new PutObjectRequest(config.getBucketName(), fileName, file)
            .withCannedAcl(CannedAccessControlList.PublicRead));

    }
}
