package site.nomoreparties.stellarburgers.api.order.client;

public class OrderNumber {
    private Integer number;

    public OrderNumber(Integer number){
        this.number = number;
    }

    public OrderNumber(){};

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
