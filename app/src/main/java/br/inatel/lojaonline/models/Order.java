package br.inatel.lojaonline.models;

import java.util.List;

/**
 * Created by bccre on 22/06/2016.
 */
public class Order {

    public int Id;
    public String userName;
    public double precoFrete;
    public List<OrderItem> OrderItems;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getPrecoFrete() {
        return precoFrete;
    }

    public void setPrecoFrete(double precoFrete) {
        this.precoFrete = precoFrete;
    }

    public List<OrderItem> getOrderItems() {
        return OrderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        OrderItems = orderItems;
    }
}
