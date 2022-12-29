package live.rashids.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
public class FileUploadController {
    
    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping(path = "upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file")MultipartFile file){

        try {
            fileUploadService.upload(file);            
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    private final String urlPrefix = "https://objectstorage.ap-melbourne-1.oraclecloud.com";


    @GetMapping(path = "file")
    public ResponseEntity<Object> getURl(){

        try {
           String accessUri = fileUploadService.getFileObject().getPreauthenticatedRequest().getAccessUri();
            return ResponseEntity.ok().body(urlPrefix+accessUri);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.internalServerError().build();
        }

    }
}
