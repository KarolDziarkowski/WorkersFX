package com.example.workersfxx.models;

import java.util.*;

public class ClassEmployee {
    private String nazwaGrupy;
    private List<Employee> pracownicy;
    private int maxPracownicy;

    public ClassEmployee(String nazwaGrupy, int maxPracownicy) {
        this.nazwaGrupy = nazwaGrupy;
        this.maxPracownicy = maxPracownicy;
        this.pracownicy = new ArrayList<>();
    }

    public String getNazwaGrupy() {
        return nazwaGrupy;
    }

    public int getMaxEmployees() {
        return maxPracownicy;
    }

    public List<Employee> getEmployees() {
        return pracownicy;
    }

    public void addEmployee(Employee employee) {
        if (pracownicy.size() >= maxPracownicy) {
            System.out.println("Nie można dodać więcej pracowników do grupy " + nazwaGrupy);
            return;
        }
        if (pracownicy.stream().anyMatch(e -> e.getImie().equals(employee.getImie()) && e.getNazwisko().equals(employee.getNazwisko()))) {
            System.out.println("Pracownik o tym imieniu i nazwisku już istnieje.");
        } else {
            pracownicy.add(employee);
        }
    }

    public void addSalary(Employee employee, double kwota) {
        if (pracownicy.contains(employee)) {
            employee.setWynagrodzenie(employee.getWynagrodzenie() + kwota);
        } else {
            System.out.println("Pracownik nie znajduje się w tej grupie.");
        }
    }

    public void removeEmployee(Employee employee) {
        if (pracownicy.remove(employee)) {
            System.out.println("Pracownik został usunięty z grupy " + nazwaGrupy);
        } else {
            System.out.println("Pracownika nie ma w tej grupie.");
        }
    }

    public void changeCondition(Employee employee, EmployeeCondition newCondition) {
        if (pracownicy.contains(employee)) {
            employee.setStan(newCondition);
        } else {
            System.out.println("Pracownik nie znajduje się w tej grupie.");
        }
    }

    public Employee search(String nazwisko) {
        return pracownicy.stream()
                .filter(e -> e.getNazwisko().equals(nazwisko))
                .findFirst()
                .orElse(null);
    }

    public List<Employee> searchPartial(String fragment) {
        return pracownicy.stream()
                .filter(e -> e.getImie().contains(fragment) || e.getNazwisko().contains(fragment))
                .toList();
    }

    public long countByCondition(EmployeeCondition condition) {
        return pracownicy.stream().filter(e -> e.getStan() == condition).count();
    }

    public void summary() {
        System.out.println("Grupa: " + nazwaGrupy);
        pracownicy.forEach(Employee::printing);
    }

    public List<Employee> sortByName() {
        return pracownicy.stream()
                .sorted()
                .toList();
    }

    public List<Employee> sortBySalary() {
        return pracownicy.stream()
                .sorted((e1, e2) -> Double.compare(e2.getWynagrodzenie(), e1.getWynagrodzenie()))
                .toList();
    }

    public Employee max() {
        return pracownicy.isEmpty() ? null :
                Collections.max(pracownicy, Comparator.comparingDouble(Employee::getWynagrodzenie));
    }
}
