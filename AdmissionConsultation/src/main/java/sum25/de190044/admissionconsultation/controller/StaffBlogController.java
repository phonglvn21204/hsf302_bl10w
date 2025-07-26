package sum25.de190044.admissionconsultation.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sum25.de190044.admissionconsultation.pojo.BlogPost;
import sum25.de190044.admissionconsultation.pojo.User;
import sum25.de190044.admissionconsultation.service.BlogPostService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/staff/blogs")
public class StaffBlogController {
    @Autowired
    private BlogPostService blogPostService;

    @GetMapping("/create")
    public String showCreateForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null || !"staff".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute("blogPost", new BlogPost());
        return "staff_blog_create";
    }

    @PostMapping("/create")
    public String submitBlog(@ModelAttribute BlogPost blogPost,
                             @RequestParam("imageFile") MultipartFile imageFile,
                             HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) return "redirect:/login";

        if (!imageFile.isEmpty()) {
            try {
                // Lưu file vào thư mục static/images/blogs/
                String fileName = imageFile.getOriginalFilename();
                String uploadDir = "src/main/resources/static/images/blogs/";
                Path path = Paths.get(uploadDir + fileName);
                Files.createDirectories(path.getParent());
                Files.copy(imageFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                blogPost.setImageUrl("/images/blogs/" + fileName); // lưu URL ảnh vào DB

            } catch (IOException e) {
                e.printStackTrace(); // hoặc xử lý lỗi khác
            }
        } else {
            blogPost.setImageUrl("/images/blogs/default.jpg");
        }

        blogPost.setAuthor(user);
        blogPost.setCreatedAt(LocalDateTime.now());
        blogPostService.save(blogPost);
        return "redirect:/staff/blogs/list";
    }


    @GetMapping("/list")
    public String showBlogList(Model model, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null || (!"admin".equalsIgnoreCase(user.getRole()) && !"staff".equalsIgnoreCase(user.getRole()))) {
            return "redirect:/login";
        }
        model.addAttribute("blogs", blogPostService.getAll()); // viết hàm getAll() nếu chưa có
        return "staff_blog_list";
    }

    @PostMapping("/delete/{id}")
    public String deleteBlog(@PathVariable Long id) {
        blogPostService.delete(id); // cần triển khai delete trong service
        return "redirect:/staff/blogs/list";
    }

    @PostMapping("/update-status")
    public String updateStatus(@RequestParam Long id, @RequestParam String status) {
        blogPostService.updateStatus(id, status); // cần viết updateStatus trong service
        return "redirect:/staff/blogs/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null || !"staff".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        BlogPost blogPost = blogPostService.getById(id);
        if (blogPost == null) {
            return "redirect:/staff/blogs/list";
        }

        model.addAttribute("blogPost", blogPost);
        return "staff_blog_update";
    }


    @PostMapping("/update/{id}")
    public String updateBlog(@PathVariable Long id,
                             @ModelAttribute BlogPost blogPost,
                             @RequestParam("imageFile") MultipartFile imageFile,
                             HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) return "redirect:/login";

        BlogPost existing = blogPostService.getById(id);
        if (existing == null) return "redirect:/staff/blogs/list";

        // Cập nhật các trường văn bản
        existing.setTitle(blogPost.getTitle());
        existing.setContent(blogPost.getContent());
        existing.setStatus(blogPost.getStatus());
        existing.setUpdatedAt(LocalDateTime.now());

        // ✅ Xử lý ảnh nếu được upload
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename(); // tránh trùng tên
                String uploadDir = "src/main/resources/static/images/blogs/";
                Path uploadPath = Paths.get(uploadDir);

                // Tạo thư mục nếu chưa tồn tại
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Lưu ảnh
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Cập nhật đường dẫn ảnh
                existing.setImageUrl("/images/blogs/" + fileName);
            } catch (IOException e) {
                e.printStackTrace(); // hoặc log lỗi
            }
        }

        blogPostService.save(existing);
        return "redirect:/staff/blogs/list";
    }

    @PostMapping("/upload-image")
    @ResponseBody
    public Map<String, String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            String uploadDir = "uploads/images/";
            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) uploadPath.mkdirs();

            Path filePath = Paths.get(uploadDir + filename);
            Files.write(filePath, file.getBytes());

            Map<String, String> response = new HashMap<>();
            response.put("location", "/uploads/images/" + filename);
            return response;

        } catch (IOException e) {
            throw new RuntimeException("Upload ảnh lỗi", e);
        }
    }

}
