package me.nandunb.photocloset;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Service
public class AmazonClient {

    private AmazonS3 s3client;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @Value("${amazonProperties.accessKey}")
    private String accessKey;

    @Value("${amazonProperties.secretKey")
    private String secretKey;

    @PostConstruct
    private void initialize(){
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = new AmazonS3Client(credentials);
    }

    public String uploadFile(MultipartFile multipartFile){

        String fileUrl = "";

        try{
            File file = FileUtils.convertMultiPartToFile(multipartFile);
            String fileName = FileUtils.generateFileName(multipartFile);
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileToS3(fileName, file);
            file.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileUrl;
    }

    //Upload file to the S3 Bucket
    private void uploadFileToS3(String fileName, File file){
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
            .withCannedAcl(CannedAccessControlList.PublicRead));

    }
}
