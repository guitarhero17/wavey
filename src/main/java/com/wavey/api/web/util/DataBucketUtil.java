package com.wavey.api.web.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.wavey.api.exceptions.FileHasNoNameException;
import com.wavey.api.exceptions.FileWriteException;
import com.wavey.api.exceptions.GCPFileUploadException;
import com.wavey.api.exceptions.InvalidFileTypeException;
import com.wavey.api.web.dto.FileDto;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Random;


@Component
public class DataBucketUtil {
    private static final Logger log = LoggerFactory.getLogger(DataBucketUtil.class);

    @Value("${gcp.config.sa.file}")
    private String gcpSA;

    @Value("${gcp.project.id}")
    private String gcpProjectId;

    @Value("${gcp.bucket.id}")
    private String gcpBucketId;

    public FileDto uploadFile(MultipartFile multipartFile, String fileName, String contentType) {

        String[] fileNameAndExtension = splitFileNameAndExtension(fileName);
        checkFileExtension(fileNameAndExtension[1]);

        try {
            log.debug("Starting file uploading process on GCP...");
            byte[] fileData = FileUtils.readFileToByteArray(convertToFile(multipartFile));
            InputStream inputStream = new ClassPathResource(gcpSA).getInputStream();

            StorageOptions options = StorageOptions.newBuilder()
                    .setProjectId(gcpProjectId)
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .build();

            Storage storage = options.getService();
            Bucket bucket = storage.get(gcpBucketId, Storage.BucketGetOption.fields());

            String randomId = generateRandomAlphanumericString();
            String blobFileName =  "waves/" + fileNameAndExtension[0] + "-" + randomId + fileNameAndExtension[1];
            Blob blob = bucket.create(blobFileName, fileData, contentType);

            if (blob != null) {
                log.debug("File successfully uploaded to GCP!");
                return new FileDto(blob.getName(), blob.getMediaLink());
            } else {
                throw new Exception("Bucket blob is null");
            }
        } catch (Exception e) {
            log.error("An error occurred while uploading file to GCP. Exception: ", e);
            throw new GCPFileUploadException(e.getMessage());
        }
    }

    private InputStream decodeBase64(String encodedString) {
        return new ByteArrayInputStream(Base64.decodeBase64(encodedString));
    }

    private File convertToFile(MultipartFile file) {

        try {
            if (file.getOriginalFilename() == null){
                throw new FileHasNoNameException();
            }
            File convertedFile = new File(file.getOriginalFilename());
            FileOutputStream outputStream = new FileOutputStream(convertedFile);
            outputStream.write(file.getBytes());
            outputStream.close();
            log.debug("Converted multipart file : {}", convertedFile);
            return convertedFile;
        } catch (Exception e) {
            throw new FileWriteException();
        }
    }

    // Ref.: https://www.baeldung.com/java-random-string#java8-alphanumeric
    private String generateRandomAlphanumericString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    // Ref.: https://www.baeldung.com/java-file-extension#1-simple-string-handling-approach
//    public String getFileExtension(String filename) {
//        return
//    }

    private String[] splitFileNameAndExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.split("\\.");
        } else {
             throw new InvalidFileTypeException();
        }
    }

    private void checkFileExtension(String fileExtension) {
        String[] allowedExtensions = {".mp3", ".wav", ".aac", "m4a"};

        if (!Arrays.asList(allowedExtensions).contains(fileExtension)) {
            log.error("Provided file type is not allowed.");
            throw new InvalidFileTypeException();
        }
    }
}
