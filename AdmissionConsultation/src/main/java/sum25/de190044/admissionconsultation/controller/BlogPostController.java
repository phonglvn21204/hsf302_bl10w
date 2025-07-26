package sum25.de190044.admissionconsultation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sum25.de190044.admissionconsultation.pojo.BlogPost;
import sum25.de190044.admissionconsultation.service.BlogPostService;
import sum25.de190044.admissionconsultation.service.MajorService;
import sum25.de190044.admissionconsultation.service.SchoolService;
import sum25.de190044.admissionconsultation.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class BlogPostController {

    @Autowired
    private BlogPostService blogPostService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private MajorService majorService;

    @GetMapping("/")
    public String guestHome(@RequestParam(value = "loginRequired", required = false) Boolean loginRequired,
                            Model model) {
        List<BlogPost> blogs = blogPostService.getPublishedBlogs();
        blogs.forEach(blog -> {
            String plainText = blog.getContent()
                    .replaceAll("<[^>]*>", "")                         // Xoá thẻ HTML
                    .replaceAll("&[^;]+;", " ")                        // Xoá ký hiệu đặc biệt như &nbsp;
                    .trim();
            String shortText = plainText.length() > 100
                    ? plainText.substring(0, 100) + "..."
                    : plainText;
            blog.setContent(shortText); // Ghi đè content
        });

        model.addAttribute("blogs", blogs);
        model.addAttribute("schools", schoolService.findAll());
        model.addAttribute("majors", majorService.getAllMajors());
        if (Boolean.TRUE.equals(loginRequired)) {
            model.addAttribute("loginMessage", "Vui lòng đăng nhập trước khi gửi yêu cầu tư vấn.");
        }
        return "guest_home";
    }


    @GetMapping("/blogs/{id}")
    public String viewBlog(@PathVariable Long id, Model model) {
        BlogPost blog = blogPostService.getById(id);
        model.addAttribute("blog", blog);
        return "blog_detail";
    }

    // Route test để thêm blog
    @GetMapping("/test-add-blog")
    @ResponseBody
    public String addTestBlog() {
        BlogPost blog = new BlogPost();
        blog.setTitle("Blog test hiển thị");
        blog.setContent("Nội dung test");
        blog.setImageUrl("/images/blogs/sample.png");
        blog.setStatus("published");
        blog.setCreatedAt(LocalDateTime.now());
        blogPostService.save(blog);
        return "Đã thêm blog test";
    }

    @GetMapping("/majors/by-school/{schoolId}")
    @ResponseBody
    public List<sum25.de190044.admissionconsultation.pojo.Major> getMajorsBySchool(@PathVariable int schoolId) {
        return majorService.getMajorsBySchoolId(schoolId);
    }

}


