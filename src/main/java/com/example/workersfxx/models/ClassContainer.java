package com.example.workersfxx.models;

import java.util.*;
import java.util.stream.Collectors;

public class ClassContainer {
    private Map<String, ClassEmployee> grupyPracownicze;

    public ClassContainer() {
        this.grupyPracownicze = new HashMap<>();
    }

    public void addClass(String nazwa, int maxPracownicy) {
        if (!grupyPracownicze.containsKey(nazwa)) {
            grupyPracownicze.put(nazwa, new ClassEmployee(nazwa, maxPracownicy));
        } else {
            System.out.println("Grupa o nazwie " + nazwa + " już istnieje.");
        }
    }

    public void removeClass(String nazwa) {
        if (grupyPracownicze.containsKey(nazwa)) {
            grupyPracownicze.remove(nazwa);
        } else {
            System.out.println("Grupa o nazwie " + nazwa + " nie istnieje.");
        }
    }

    public List<String> findEmpty() {
        return grupyPracownicze.entrySet().stream()
                .filter(entry -> entry.getValue().getEmployees().isEmpty())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public void summary() {
        grupyPracownicze.forEach((nazwa, grupa) -> {
            int liczbaPracownikow = grupa.getEmployees().size();
            double procentoweZapelnienie = (double) liczbaPracownikow / grupa.getMaxEmployees() * 100;
            System.out.printf("Nazwa grupy: %s, Zapełnienie: %.2f%% (%d/%d)%n",
                    nazwa, procentoweZapelnienie, liczbaPracownikow, grupa.getMaxEmployees());
        });
    }

    public ClassEmployee getClassEmployee(String nazwa) {
        return grupyPracownicze.get(nazwa);
    }
}
