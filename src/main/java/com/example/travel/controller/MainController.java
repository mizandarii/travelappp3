package com.example.travel.controller;


import com.example.travel.model.CustomUserDetails;
import com.example.travel.model.User;
import com.example.travel.service.CustomUserDetailsService;
import com.example.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String processRegister(User user, @RequestParam("image") MultipartFile file) throws IOException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
//        StringBuilder fileNames = new StringBuilder();
//        Path fileNameAndPath = Paths.get(resourceLoader.getResource("classpath:/static/images/").getFile().getAbsolutePath(), file.getOriginalFilename());
//        fileNames.append(file.getOriginalFilename());
//        Files.write(fileNameAndPath, file.getBytes());
//        user.setAvatar(fileNames.toString());
        String imagePath = resourceLoader.getResource("classpath:/static/images/").getFile().getAbsolutePath();
        String filePath = imagePath + File.separator + file.getOriginalFilename();
        System.out.println(filePath);
        try (  InputStream inputStream = file.getInputStream(); FileOutputStream outputStream = new FileOutputStream(new File(filePath))) {
            FileCopyUtils.copy(inputStream, outputStream);
            user.setAvatar(file.getOriginalFilename());
        }
        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userService.findAllUsers();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }

    @GetMapping("/userprofile")
    public String userInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(authentication.getName());
        Long id = userDetails.getId();
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "viewUserInfo";
    }


}
