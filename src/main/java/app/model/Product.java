package app.model;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import java.util.List;

@Entity
@Table(name="Product")
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max=50)
    private String name;

    @NotNull
    private BigDecimal price;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="brand_id")
    private Brand brand;

    @Size(max=50)
    private String image;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="create_user")
    private UserAccount userAccount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_date")
    private Date createDate;

    @OneToMany(mappedBy="product")
    private List<OrderDetail> orderDetails;

    public Product() {
    }
    
    public Integer getId() {
        return this.id;
    }
  
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
  
    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }
  
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Brand getBrand() {
        return this.brand;
    }
  
    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getImage() {
        return this.image;
    }
  
    public void setImage(String image) {
        this.image = image;
    }

    public UserAccount getUserAccount() {
        return this.userAccount;
    }
  
    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Date getCreateDate() {
        return this.createDate;
    }
  
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<OrderDetail> getOrderDetails() {
        return this.orderDetails;
    }
  
    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

}