package com.example.first;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

enum TipCurs{INCEPATOR, MEDIU, ANANSAT}

//setarea foreign key-ului al tabelei useri, care e cursId -> arata ce curs are in progres userul
@Entity(tableName = "cursuri",foreignKeys =
@ForeignKey(entity=User.class,parentColumns ="id",childColumns = "userId",onDelete = CASCADE)
        ,indices = @Index("userId"))
public class Curs implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String titlu;
    private String continut;
    private String tipCurs;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Ignore
    private String uid;

    //info pentru foreign key folosit la tabela useri
    @ColumnInfo(name="userId")
    private long userId;

    public long getUserId(){
        return userId;
    }
    public void setUserId(long userId){
        this.userId=userId;
    }

    @Ignore
    public Curs(){

    }


    public Curs(String titlu, String continut, String tipCurs, long userId) {
        this.titlu = titlu;
        this.continut = continut;
        this.tipCurs = tipCurs;
        this.userId = userId;
    }

    @Ignore
    //constructorul de curs ce contine si id-ul userului care l-a postat
    public Curs(int id, String titlu, String continut, String tipCurs, long userId) {
        this.id = id;
        this.titlu = titlu;
        this.continut = continut;
        this.tipCurs = tipCurs;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getContinut() {
        return continut;
    }

    public void setContinut(String continut) {
        this.continut = continut;
    }

    public String getTipCurs() {
        return tipCurs;
    }

    public void setTipCurs(String tipCurs) {
        this.tipCurs = tipCurs;
    }

    @Override
    public String toString() {
        return "Curs{" +
                "id=" + id +
                ", titlu='" + titlu + '\'' +
                ", continut='" + continut + '\'' +
                ", tipCurs='" + tipCurs + '\'' +
                ", userId=" + userId +
                '}';
    }
}
