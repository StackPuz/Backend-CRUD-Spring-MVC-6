package app.model;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="OrderDetail")
@NamedQuery(name="OrderDetail.findAll", query="SELECT o FROM OrderDetail o")
public class OrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private OrderDetailPK id;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="order_id", insertable=false, updatable=false)
    private OrderHeader orderHeader;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="product_id")
    private Product product;

    @NotNull
    private Short qty;

    public OrderDetail() {
    }
    
    public OrderDetailPK getId() {
        return this.id;
    }

    public void setId(OrderDetailPK id) {
        this.id = id;
    }
    public OrderHeader getOrderHeader() {
        return this.orderHeader;
    }
  
    public void setOrderHeader(OrderHeader orderHeader) {
        this.orderHeader = orderHeader;
    }

    public Product getProduct() {
        return this.product;
    }
  
    public void setProduct(Product product) {
        this.product = product;
    }

    public Short getQty() {
        return this.qty;
    }
  
    public void setQty(Short qty) {
        this.qty = qty;
    }

}