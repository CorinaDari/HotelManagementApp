package org.example;

import jakarta.persistence.*;

public class TestGenerareSchema {
    public static void main(String[] args){
        System.out.println(1);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HotelProiect");
        EntityManager em = emf.createEntityManager();

    }
}