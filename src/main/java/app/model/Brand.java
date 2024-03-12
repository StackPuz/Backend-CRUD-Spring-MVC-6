package app.model;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name="Brand")
@NamedQuery(name="Brand.findAll", query="SELECT b FROM Brand b")
public class Brand implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max=50)
    private String name;

    @OneToMany(mappedBy="brand")
    private List<Product> products;

    public Brand() {
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

    public List<Product> getProducts() {
        return this.products;
    }
  
    public void setProducts(List<Product> products) {
        this.products = products;
    }

}