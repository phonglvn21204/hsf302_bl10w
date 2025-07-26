package sum25.de190044.admissionconsultation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sum25.de190044.admissionconsultation.pojo.BlogPost;

import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    List<BlogPost> findByStatusIgnoreCase(String status);
}

