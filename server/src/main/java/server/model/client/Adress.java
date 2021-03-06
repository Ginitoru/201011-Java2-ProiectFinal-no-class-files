package server.model.client;

import javax.persistence.Embeddable;

@Embeddable
public class Adress {

    private String street;

    private String number;

    public String getStreet() {
        return street;
    }

    public Adress(String street, String number) {
        this.street = street;
        this.number = number;
    }

    public Adress() {
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
