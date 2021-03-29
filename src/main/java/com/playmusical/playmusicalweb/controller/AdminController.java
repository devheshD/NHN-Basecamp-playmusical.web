package com.playmusical.playmusicalweb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.playmusical.playmusicalweb.dto.MusicalDTO;
import com.playmusical.playmusicalweb.service.AdminService;
import com.playmusical.playmusicalweb.service.MusicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final MusicalService musicalService;
    private final AdminService adminService;

    @GetMapping("")
    public String admin() throws JsonProcessingException {
        adminService.AuthToken();
        return "main/admin_register";
    }

    @PostMapping("")
    public String register(MusicalDTO musicalDTO,
        @RequestParam("bannerfile") MultipartFile bannerFile,
        @RequestParam("posterfile") MultipartFile posterFile, RedirectAttributes redirectAttributes)
        throws IOException {
        adminService.makeImageAndUploadFile(musicalDTO, bannerFile, "_banner_");
        adminService.makeImageAndUploadFile(musicalDTO, posterFile, "_poster_");
        redirectAttributes.addFlashAttribute("msg", musicalService.register(musicalDTO));
        return "redirect:/admin";
    }
}
