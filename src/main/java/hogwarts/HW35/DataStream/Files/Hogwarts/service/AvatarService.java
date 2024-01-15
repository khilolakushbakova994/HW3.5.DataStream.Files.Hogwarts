package hogwarts.HW35.DataStream.Files.Hogwarts.service;

import hogwarts.HW35.DataStream.Files.Hogwarts.model.Avatar;
import hogwarts.HW35.DataStream.Files.Hogwarts.model.Student;
import hogwarts.HW35.DataStream.Files.Hogwarts.repository.AvatarRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.beans.Transient;


import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {


    @Value("${students.image.dir.path}")
    private String imageDir;

    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    public void uploadImage(Long studentId, MultipartFile file) throws IOException {
        Student student = studentService.find(studentId);

        Path filePath = Path.of(imageDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try
                (InputStream is = file.getInputStream();
                 OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                 BufferedInputStream bis = new BufferedInputStream(is, 1024);
                 BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
                ) {
            bis.transferTo(bos);
        }

        Avatar avatar = findAvatarById(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());

        avatarRepository.save(avatar);

    }

    public Avatar findAvatarById(Long studentId) {
        return avatarRepository.findAvatarById(studentId).orElseThrow();
    }

    private byte[] generateImagePreview(Path filePath) throws IOException {

        try ( InputStream is = Files.newInputStream(filePath);
              BufferedInputStream bis = new BufferedInputStream(is, 1024);
              ByteArrayOutputStream baos = new ByteArrayOutputStream())
        {
            BufferedImage image = ImageIO.read(bis);

            int height  = image.getHeight() / (image.getWidth()/100);
            BufferedImage preview  = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0,0,100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    private String getExtension(String filename){
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

}

