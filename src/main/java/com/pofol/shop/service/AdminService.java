package com.pofol.shop.service;

import com.pofol.shop.domain.Item;
import com.pofol.shop.domain.ItemImage;
import com.pofol.shop.domain.dto.ItemFormDTO;
import com.pofol.shop.repository.ItemImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static java.io.File.separator;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final ItemImageRepository itemImageRepository;

    @Value("${file.directory}")
    private String fileDirectory;

    public void uploader(ItemFormDTO DTO, Item item) {

        MultipartFile main = DTO.getMainImage();
        saveImage(item, main, true);

        List<MultipartFile> images = DTO.getImages();
        for (MultipartFile image : images) {
            saveImage(item, image, false);
        }


    }

    private void saveImage(Item item, MultipartFile image,Boolean isMain) {
        String extension = getExtension(image);
        String serveSavedName = getServeSavedName(extension);
        String[] location = createFolder();

        ItemImage itemImage = ItemImage.builder()
                .isMainImg(isMain)
                .userCustomName(image.getOriginalFilename())
                .serverSavedName(serveSavedName)
                .location(location[1])
                .item(item)
                .build();

        try {
            image.transferTo(Paths.get(location[0] + separator + serveSavedName));
        } catch(IOException e) {
            e.printStackTrace();
        }

        itemImageRepository.save(itemImage);
    }

    private String getServeSavedName(String extension) {
        String uuid = UUID.randomUUID().toString().substring(0, 16);
        return uuid + extension;
    }

    private String getExtension(MultipartFile image) {
        return image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
    }
    private String[] createFolder() {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        int day = LocalDate.now().getDayOfMonth();
        File folder = new File(fileDirectory + year + separator + month + separator + day + separator);
        String src = "/img/" + year + "/" + month + "/" + day;

        if(!folder.exists()) {
            try {
                folder.mkdirs();
            } catch(SecurityException e) {
                e.getMessage();
            }
        }
        return new String[]{folder.getPath(), src};
    }


}
