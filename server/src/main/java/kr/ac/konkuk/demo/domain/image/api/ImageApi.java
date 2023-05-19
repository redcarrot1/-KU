package kr.ac.konkuk.demo.domain.image.api;

import kr.ac.konkuk.demo.domain.image.application.ImageService;
import kr.ac.konkuk.demo.domain.image.dto.SaveImageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/users/images")
public class ImageApi {

    private final ImageService imageService;

    @GetMapping("/{filename}")
    public Resource showImage(@PathVariable String filename) {
        // <img src="/images/[파일이름]">
        // th:src="@{${item.getImgPath()}}"
        return imageService.getImageFile(filename);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaveImageResponse saveImages(MultipartFile faceImageFile) {
        String faceImageUri = imageService.registryImage(faceImageFile);
        return new SaveImageResponse(faceImageUri);
    }

}
