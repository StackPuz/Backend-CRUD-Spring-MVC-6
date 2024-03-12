package app.model;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import java.util.List;

@Entity
@Table(name="OrderHeader")
@NamedQuery(name="OrderHeader.findAll", query="SELECT o FROM OrderHeader o")
public class OrderHeader implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="customer_id")
    private Customer customer;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name="order_date")
    private Date orderDate;

    @OneToMany(mappedBy="orderHeader")
    private List<OrderDetail> orderDetails;

    public OrderHeader() {
    }
    
    public Integer getId() {
        return this.id;
    }
  
    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return this.customer;
    }
  
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getOrderDate() {
        return this.orderDate;
    }
  
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderDetail> getOrderDetails() {
        return this.orderDetails;
    }
  
    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

}