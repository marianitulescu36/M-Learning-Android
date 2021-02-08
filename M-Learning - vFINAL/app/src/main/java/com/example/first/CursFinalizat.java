package com.example.first;

import java.io.Serializable;

//clasa pentru obiectul din JSON
public class CursFinalizat implements Serializable {
    //nodul 1: user
    private int id;
    private String email;
    private int nrCursuriFinalizate;
    //private String cursant;
    //nodul 2 :cursant
    private String numePrenume;
    private int varsta;
    private String gen;
    //private String cursFinalizat;
    //nodul 3 :curs
    private String numeCurs;
    private String dificultate;
    private String notita;

    public CursFinalizat(int id, String email, int nrCursuriFinalizate) {
        this.id = id;
        this.email = email;
        this.nrCursuriFinalizate = nrCursuriFinalizate;
    }

    public CursFinalizat(String nume_prenume, int varsta, String gen) {
        this.numePrenume = nume_prenume;
        this.varsta = varsta;
        this.gen = gen;
    }

    public CursFinalizat(int id, String email, int nrCursuriFinalizate, String nume_prenume, int varsta, String gen, String numeCurs, String dificultate, String notita) {
        this.id = id;
        this.email = email;
        this.nrCursuriFinalizate = nrCursuriFinalizate;
        this.numePrenume = nume_prenume;
        this.varsta = varsta;
        this.gen = gen;
        this.numeCurs = numeCurs;
        this.dificultate = dificultate;
        this.notita = notita;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNrCursuriFinalizate() {
        return nrCursuriFinalizate;
    }

    public void setNrCursuriFinalizate(int nrCursuriFinalizate) {
        this.nrCursuriFinalizate = nrCursuriFinalizate;
    }

    public String getNume_prenume() {
        return numePrenume;
    }

    public void setNume_prenume(String nume_prenume) {
        this.numePrenume = nume_prenume;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public String getNumeCurs() {
        return numeCurs;
    }

    public void setNumeCurs(String numeCurs) {
        this.numeCurs = numeCurs;
    }

    public String getDificultate() {
        return dificultate;
    }

    public void setDificultate(String dificultate) {
        this.dificultate = dificultate;
    }

    public String getNotita() {
        return notita;
    }

    public void setNotita(String notita) {
        this.notita = notita;
    }

    @Override
    public String toString() {
        return "CursFinalizat{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nrCursuriFinalizate='" + nrCursuriFinalizate + '\'' +
                ", nume_prenume='" + numePrenume + '\'' +
                ", varsta=" + varsta +
                ", gen='" + gen + '\'' +
                ", numeCurs='" + numeCurs + '\'' +
                ", dificultate='" + dificultate + '\'' +
                ", notita='" + notita + '\'' +
                '}';
    }
}
