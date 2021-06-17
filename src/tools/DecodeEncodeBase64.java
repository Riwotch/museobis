package tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class DecodeEncodeBase64 {
    public static void main(String[] args) {
       /* String imagePath = "src/punisherWom.jpg";
        System.out.println("=================Encoder Image to Base 64!=================");
        String base64ImageString = encoder(imagePath);
        System.out.println("Base64ImageString = " + base64ImageString);

        System.out.println("=================Decoder Base64ImageString to Image!=================");
        createFolder("src/upload/");
        decoder(base64ImageString, "src/upload/punisherDec.jpg");

        System.out.println("DONE!");*/
        //createFolder("src/upload");
        // deleteDirectory(new File("src/upload'));
        //deleteFilesInFolder("src/upload");
    }

    public static void createFolder(String path){
        File uploadDir = new File(path);
        if (!uploadDir.exists()){
            uploadDir.mkdirs();
        }
    }

    public static void deleteFilesInFolder(String path) {
        File upload = new File("src/upload");
        if (upload.exists()) {
            String[]entries = upload.list();
            for(String s: entries){
                File currentFile = new File(upload.getPath(),s);
                currentFile.delete();
            }
        }
    }

    public static boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    public static String encoder(String imagePath) {
        String base64Image = "";
        File file = new File(imagePath);
        try (FileInputStream imageInFile = new FileInputStream(file)) {
            // Reading a Image file from file system
            byte[] imageData = new byte[(int) file.length()];
            imageInFile.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
        return base64Image;
    }

    public static void decoder(String base64Image, String pathFile) {
        try (FileOutputStream imageOutFile = new FileOutputStream(pathFile)) {
            // Converting a Base64 String into Image byte array
            byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
            imageOutFile.write(imageByteArray);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
    }
}