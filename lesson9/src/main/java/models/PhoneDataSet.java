package models;

import javax.persistence.*;

/**
 * Created by tully.
 */
@Entity
@Table(name = "phones")
public class PhoneDataSet extends DataSet {

    @Column(name = "code")
    private int code;

    @Column(name = "number")
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserDataSet user;

    public PhoneDataSet() {
    }

    public PhoneDataSet(int code, String number, UserDataSet user) {
        this.code = code;
        this.number = number;
        this.user = user;
    }

    public String getNumber() {
        return number;
    }

    public int getCode() {
        return code;
    }

    public UserDataSet getUser() {
        return user;
    }

    public void setUser(UserDataSet user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "code=" + code +
                ", number='" + number + '\'' +
                '}';
    }
}
