package sum25.de190044.admissionconsultation.service;

import sum25.de190044.admissionconsultation.pojo.BlogPost;

import java.util.List;

public interface BlogPostService {
    List<BlogPost> getPublishedBlogs();
    BlogPost getById(Long id);
    BlogPost save(BlogPost blog);
    public List<BlogPost> getAll();
    public void delete(Long id);
    public void updateStatus(Long id, String status);
}

