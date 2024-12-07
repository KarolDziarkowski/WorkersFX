package com.example.workersfxx.models;

import javafx.beans.property.*;

public class Employee implements Comparable<Employee> {
    private StringProperty imie;
    private StringProperty nazwisko;
    private ObjectProperty<EmployeeCondition> stan;  // Assuming EmployeeCondition is an enum
    private IntegerProperty rokUrodzenia;
    private DoubleProperty wynagrodzenie;

    public Employee(String imie, String nazwisko, EmployeeCondition stan, int rokUrodzenia, double wynagrodzenie) {
        this.imie = new SimpleStringProperty(imie);
        this.nazwisko = new SimpleStringProperty(nazwisko);
        this.stan = new SimpleObjectProperty<>(stan);
        this.rokUrodzenia = new SimpleIntegerProperty(rokUrodzenia);
        this.wynagrodzenie = new SimpleDoubleProperty(wynagrodzenie);
    }

    // JavaFX property getters
    public StringProperty imieProperty() {
        return imie;
    }

    public StringProperty nazwiskoProperty() {
        return nazwisko;
    }

    public ObjectProperty<EmployeeCondition> stanProperty() {
        return stan;
    }

    public IntegerProperty rokUrodzeniaProperty() {
        return rokUrodzenia;
    }

    public DoubleProperty wynagrodzenieProperty() {
        return wynagrodzenie;
    }

    // Standard getters
    public String getNazwisko() {
        return nazwisko.get();
    }

    public String getImie() {
        return imie.get();
    }

    public EmployeeCondition getStan() {
        return stan.get();
    }

    public void setStan(EmployeeCondition stan) {
        this.stan.set(stan);
    }

    public double getWynagrodzenie() {
        return wynagrodzenie.get();
    }

    public void setWynagrodzenie(double wynagrodzenie) {
        this.wynagrodzenie.set(wynagrodzenie);
    }

    @Override
    public int compareTo(Employee other) {
        return this.nazwisko.get().compareTo(other.nazwisko.get());
    }

    public void printing() {
        System.out.println("Imię: " + imie.get() + ", Nazwisko: " + nazwisko.get() + ", Stan: " + stan.get() +
                ", Rok urodzenia: " + rokUrodzenia.get() + ", Wynagrodzenie: " + wynagrodzenie.get());
    }
}
