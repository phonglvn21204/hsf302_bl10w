package sum25.de190044.admissionconsultation.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "schools")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "logo_url", length = 255)
    private String logoUrl;

    @Column(name = "website_url", length = 255)
    private String websiteUrl;

    // Constructors
    public School() {}

    public School(String name, String description, String logoUrl, String websiteUrl) {
        this.name = name;
        this.description = description;
        this.logoUrl = logoUrl;
        this.websiteUrl = websiteUrl;
    }

    public School(Long schoolId) {
        this.id = schoolId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }
}
