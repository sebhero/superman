package com.heroes.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;


/**
 * Created by Sebastian Boreback on 2017-02-07.
 */
@CrossOrigin(maxAge = 3600, origins = "*")
@Controller
public class FileUploadController {

    private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);
    // The Environment object will be used to read parameters from the
    // application.properties configuration file
    @Autowired
    private Environment env;

    @RequestMapping(value = "/testa")
    public @ResponseBody void testa(Byte[] data) {

        try {
            log.info("got " + data);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/image", headers = "Accept=image/jpeg, image/jpg, image/png, image/gif", method = RequestMethod.PUT)
    public @ResponseBody
    BufferedImage getImage() {
        try {
            InputStream inputStream = this.getClass().getResourceAsStream("myimage.jpg");
            return ImageIO.read(inputStream);

            //            BufferedImage im = ImageIO.read(url);
//            ByteArrayOutputStream baos=new ByteArrayOutputStream();
//            ImageIO.write(im, "jpg", baos);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * Show the index page containing the form for uploading a file.
     */
//    @RequestMapping("/")
//    public String index() {
//        return "index4.html";
//    }




    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> upload(@RequestParam() String filename) {
        System.out.println(filename);
//        @RequestParam(value = "name", defaultValue = "World") String name

//        try {
//            // Get the filename and build the local file path
//
//            String filename = uploadfile.getAbsoluteFile().getAbsolutePath();
//            String directory = env.getProperty("superman.paths.uploadedFiles");
//            String filepath = Paths.get(directory, filename).toString();
//
//            // Save the file locally
//            BufferedOutputStream stream =
//                    new BufferedOutputStream(new FileOutputStream(new File(filepath)));
//            stream.write(uploadfile.getBytes());
//            stream.close();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }

        return new ResponseEntity<>(HttpStatus.OK);
    } // method uploadFile


    /**
     * POST /uploadFile -> receive and locally save a file.
     *
     * @param uploadfile The uploaded file as Multipart file parameter in the
     *                   HTTP request. The RequestParam name must be the same of the attribute
     *                   "name" in the input tag with type file.
     * @return An http OK status in case of success, an http 4xx status in case
     * of errors.
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> uploadFile(
            @RequestParam("filename") MultipartFile uploadfile) {

        try {
            // Get the filename and build the local file path
            String filename = uploadfile.getOriginalFilename();
            String directory = env.getProperty("superman.paths.uploadedFiles");
            String filepath = Paths.get(directory, filename).toString();

            // Save the file locally
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(filepath)));
            stream.write(uploadfile.getBytes());
            stream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    } // method uploadFile


}
//
//    private final StorageService storageService;
//
//    @Autowired
//    public FileUploadController(StorageService storageService) {
//        this.storageService = storageService;
//    }
//
//    @GetMapping("/upload")
//    public String listUploadedFiles(Model model) throws IOException {
//
//        model.addAttribute("files", storageService
//                .loadAll()
//                .map(path ->
//                        MvcUriComponentsBuilder
//                                .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
//                                .build().toString())
//                .collect(Collectors.toList()));
//
//        return "uploadForm";
//    }
//
//    @GetMapping("/files/{filename:.+}")
//    @ResponseBody
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//
//        Resource file = storageService.loadAsResource(filename);
//        return ResponseEntity
//                .ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
//                .body(file);
//    }
//
//    @PostMapping("/upload")
//    public String handleFileUpload(@RequestParam("file") MultipartFile file,
//                                   RedirectAttributes redirectAttributes) {
//
//        storageService.store(file);
//        redirectAttributes.addFlashAttribute("message",
//                "You successfully uploaded " + file.getOriginalFilename() + "!");
//
//        return "redirect:/upload";
//    }
//
//    @ExceptionHandler(StorageFileNotFoundException.class)
//    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
//        return ResponseEntity.notFound().build();
//    }
//
//}
