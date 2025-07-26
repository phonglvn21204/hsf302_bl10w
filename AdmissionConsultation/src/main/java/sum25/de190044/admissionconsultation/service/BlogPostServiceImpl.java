package sum25.de190044.admissionconsultation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sum25.de190044.admissionconsultation.pojo.BlogPost;
import sum25.de190044.admissionconsultation.repository.BlogPostRepository;
import sum25.de190044.admissionconsultation.service.BlogPostService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BlogPostServiceImpl implements BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Override
    public List<BlogPost> getPublishedBlogs() {
        List<BlogPost> all = blogPostRepository.findAll();
        System.out.println("==> Có tất cả: " + all.size());
        all.forEach(b -> System.out.println("[" + b.getId() + "] " + b.getTitle() + " | " + b.getStatus()));
        return blogPostRepository.findByStatusIgnoreCase("published");
    }

    @Override
    public BlogPost getById(Long id) {
        return blogPostRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết"));
    }

    @Override
    public BlogPost save(BlogPost blog) {
        return blogPostRepository.save(blog);
    }

    @Override
    public List<BlogPost> getAll() {
        return blogPostRepository.findAll();
    }

    public void delete(Long id) {
        blogPostRepository.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        Optional<BlogPost> blog = blogPostRepository.findById(id);
        if (blog.isPresent()) {
            BlogPost post = blog.get();
            post.setStatus(status);
            post.setUpdatedAt(LocalDateTime.now());
            blogPostRepository.save(post);
        }
    }
}
