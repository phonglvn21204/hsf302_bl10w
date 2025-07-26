// 1. Entity: Scholarship.java
package sum25.de190044.admissionconsultation.pojo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Scholarships")
public class Scholarship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major; // nullable - có thể apply cho nhiều ngành

    private BigDecimal amount; // số tiền học bổng
    private String type; // "full", "partial", "monthly"

    @Column(name = "min_gpa")
    private Double minGpa;

    @Column(name = "application_deadline")
    private LocalDate applicationDeadline;

    @Column(name = "requirements", columnDefinition = "NVARCHAR(500)")
    private String requirements;

    private String status; // "active", "expired", "draft"
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructors
    public Scholarship() {}

    public Scholarship(String title, String description, School school, BigDecimal amount, String type) {
        this.title = title;
        this.description = description;
        this.school = school;
        this.amount = amount;
        this.type = type;
        this.status = "active";
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public School getSchool() { return school; }
    public void setSchool(School school) { this.school = school; }

    public Major getMajor() { return major; }
    public void setMajor(Major major) { this.major = major; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Double getMinGpa() { return minGpa; }
    public void setMinGpa(Double minGpa) { this.minGpa = minGpa; }

    public LocalDate getApplicationDeadline() { return applicationDeadline; }
    public void setApplicationDeadline(LocalDate applicationDeadline) { this.applicationDeadline = applicationDeadline; }

    public String getRequirements() { return requirements; }
    public void setRequirements(String requirements) { this.requirements = requirements; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
