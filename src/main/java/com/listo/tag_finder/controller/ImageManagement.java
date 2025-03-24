package com.listo.tag_finder.controller;

import com.listo.tag_finder.model.Tag;
import com.listo.tag_finder.service.ParameterStoreService;
import com.listo.tag_finder.service.S3Service;
import com.listo.tag_finder.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.*;

@Controller
public class ImageManagement {
    private final S3Service s3Service;
    private final TagService tagService;

    public ImageManagement(S3Service s3Service, TagService tagService) {
        this.tagService = tagService;
        this.s3Service = s3Service;
    }

    @GetMapping("/")
    public String getImages(@RequestParam(value = "token", required = false) String token, Model model) {
        List<Tag> tags = tagService.findAll();

        model.addAttribute("tags", tags);
        return "index";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("image") MultipartFile tagImage, @RequestParam("description") String description, RedirectAttributes redirectAttributes) throws IOException {
        if (tagImage.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select an image to upload.");
            return "redirect:/";
        }

        if (description.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please enter a description.");
            return "redirect:/";
        }

        try{
            // Upload image to S3
            String bucketName = "tag-finder-s3";
            String fileKey = "listo_" + tagImage.getOriginalFilename();

            s3Service.uploadFile(bucketName, fileKey, tagImage);

            String imageUrl = "https://tag-finder-s3.s3.amazonaws.com/" + fileKey;
            Tag newTag = new Tag(imageUrl, description);
            tagService.addTag(newTag);

            return "redirect:/";
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error uploading image.");
            return "redirect:/";
        }

    }

    @GetMapping("/display")
    public String showImagePage() {
        return "new-image";
    }

    @PostMapping("/delete-image")
    public String deleteImage(@RequestParam("tagId") String tagId) {
        Tag tag = tagService.findById(Long.parseLong(tagId));
        String fileKey = tag.getImageUrl().substring(tag.getImageUrl().lastIndexOf("/") + 1);
        s3Service.deleteObject("tag-finder-s3", fileKey);
        tagService.deleteById(Long.parseLong(tagId));
        return "redirect:/";
    }
}
