package org.example;

import jakarta.persistence.*;

import java.util.List;

import static jakarta.persistence.Persistence.createEntityManagerFactory;

public class Test {
    public static void main(String[] args) {
        EntityManagerFactory emf = createEntityManagerFactory("Hotel");
        EntityManager em = emf.createEntityManager();

        //populare serviciuCamera
        List<ServiciuCamera> lstServicii;
        lstServicii = em.createQuery("SELECT sc FROM ServiciuCamera sc", ServiciuCamera.class).getResultList();
        if (!lstServicii.isEmpty()) {
            em.getTransaction().begin();
            for (ServiciuCamera sc : lstServicii)
                em.remove(sc);
            em.getTransaction().commit();
        }

            //create
            em.getTransaction().begin();
            ServiciuCamera sc1 = new ServiciuCamera(101, "Curățenie zilnică");
            ServiciuCamera sc2 = new ServiciuCamera(102, "Room service");
            ServiciuCamera sc3 = new ServiciuCamera(103, "Wi-Fi gratuit");
            ServiciuCamera sc4 = new ServiciuCamera(104, "Schimbare prosoape");
            ServiciuCamera sc5 = new ServiciuCamera(105, "Televiziune prin cablu/satelit");

            em.persist(sc1);
            em.persist(sc2);
            em.persist(sc3);
            em.persist(sc4);
            em.persist(sc5);
            em.getTransaction().commit();


            // Read after create
        lstServicii = em.createQuery("SELECT sc FROM ServiciuCamera sc", ServiciuCamera.class).getResultList();
        System.out.println("Lista servicii camere persistente/salvate in baza de date");
        for (ServiciuCamera sc:  lstServicii)
            System.out.println("Id: " + sc.getIdServiciu() + ", nume: " + sc.getTipServiciu());

        //populare camera
        List<Camera> lstCamera;
        lstCamera = em.createQuery("SELECT sc FROM Camera sc", Camera.class).getResultList();
        if (!lstCamera.isEmpty()) {
            em.getTransaction().begin();
            for (Camera sc : lstCamera)
                em.remove(sc);
            em.getTransaction().commit();
        }

        //create camere
        em.getTransaction().begin();
        Camera camera1 = new Camera(1, 1, "simpla", false);
        Camera camera2 = new Camera(2, 1, "apartament", false);
        Camera camera3 = new Camera(3, 1, "dubla", false);
        Camera camera4 = new Camera(4, 2, "family room", false);
        Camera camera5 = new Camera(5, 2, "simpla", false);
        Camera camera6 = new Camera(6, 2, "simpla", false);
        Camera camera7 = new Camera(7, 3, "dubla", false);
        em.persist(camera1);
        em.persist(camera2);
        em.persist(camera3);
        em.persist(camera4);
        em.persist(camera5);
        em.persist(camera6);
        em.persist(camera7);
        em.getTransaction().commit();

        //read after create camere
        lstCamera = em.createQuery("SELECT sc FROM Camera sc", Camera.class).getResultList();
        System.out.println("Lista camere persistente/salvate in baza de date");
        for (Camera sc: lstCamera)
            System.out.println("nr camera: " + sc.getNumarCamera());



        //populare persoana

      List<Persoana> lstPersoana;
        lstPersoana = em.createQuery("SELECT sc FROM Persoana sc", Persoana.class).getResultList();
        if (!lstPersoana.isEmpty()) {
            em.getTransaction().begin();
            for (Persoana sc : lstPersoana)
                em.remove(sc);
            em.getTransaction().commit();
        }

        //create persoana
        em.getTransaction().begin();

        Angajat angajat1 = new Angajat(1001, "angajat", "Ion", "Popescu", "Recepționer", 4000.0);
        Angajat angajat2 = new Angajat(1002, "angajat", "Ana", "Ionescu", "Ospătar", 3500.0);
        Angajat angajat3 = new Angajat(1003, "angajat", "Mihai", "Dumitru", "Bucătar", 3800.0);
        Angajat angajat4 = new Angajat(1004, "angajat", "Elena", "Popa", "Menajeră", 3200.0);
        Angajat angajat5 = new Angajat(1005, "angajat", "Adrian", "Georgescu", "Sofor", 4000.0);
        Angajat angajat6 = new Angajat(1006, "angajat", "Simona", "Nistor", "Manager de Evenimente", 4500.0);
        Client client1 = new Client(2001, "client", "Andreea", "Popescu", "0740123456");
        Client client2 = new Client(2002, "client", "Marius", "Ionescu", "0721345678");
        Client client3 = new Client(2003, "client", "Cristina", "Dumitru", "0732567890");
        Client client4 = new Client(2004, "client", "Ion", "Georgescu", "0710901234");
        Client client5 = new Client(2005, "client", "Elena", "Marin", "0722123456");
        Client client6 = new Client(2006, "client", "Alexandru", "Nistor", "0743345678");

        em.persist(angajat1);
        em.persist(angajat2);
        em.persist(angajat3);
        em.persist(angajat4);
        em.persist(angajat5);
        em.persist(angajat6);
        em.persist(client1);
        em.persist(client2);
        em.persist(client3);
        em.persist(client4);
        em.persist(client5);
        em.persist(client6);
        em.getTransaction().commit();

        //read after create persoana
        lstPersoana = em.createQuery("SELECT sc FROM Persoana sc", Persoana.class).getResultList();
        System.out.println("Lista persoane persistente/salvate in baza de date");
        for (Persoana sc: lstPersoana)
            System.out.println("Id: " + sc.getIdPersoana() +" "+ sc.getTipPersoana());


        //populare rezervareCamera
        List<RezervareCamera> lstRezCam;
        lstRezCam = em.createQuery("SELECT sc FROM RezervareCamera sc", RezervareCamera.class).getResultList();
        if (!lstRezCam.isEmpty()) {
            em.getTransaction().begin();
            for (RezervareCamera sc : lstRezCam)
                em.remove(sc);
            em.getTransaction().commit();
        }

        //create rezervareCamera
        em.getTransaction().begin();
        RezervareCamera rez1 = new RezervareCamera(501, client1, camera1,  "11.01.2023", "15.01.2023");
        RezervareCamera rez2 = new RezervareCamera(502, client2, camera2,  "12.01.2023", "17.01.2023");
        RezervareCamera rez3 = new RezervareCamera(503, client3, camera3,  "18.02.2023", "15.03.2023");
        RezervareCamera rez4 = new RezervareCamera(504, client4, camera4,  "15.01.2023", "28.01.2023");
        RezervareCamera rez5 = new RezervareCamera(505, client5, camera5,  "17.01.2023", "29.01.2023");
        em.persist(rez1);
        em.persist(rez2);
        em.persist(rez3);
        em.persist(rez4);
        em.persist(rez5);

        em.getTransaction().commit();

        //read after create rezervareCamera
        lstRezCam = em.createQuery("SELECT sc FROM RezervareCamera sc", RezervareCamera.class).getResultList();
        System.out.println("Lista rezervari persistente/salvate in baza de date");
        for (RezervareCamera sc: lstRezCam)
            System.out.println("Id: "+ sc.getCamera().getNumarCamera() + " Id rez: "+ sc.getId());

        //populare recenzieHotel
        List<RecenzieHotel> lstRecenzii;
        lstRecenzii = em.createQuery("SELECT sc FROM RecenzieHotel sc", RecenzieHotel.class).getResultList();
        if (!lstRecenzii.isEmpty()) {
            em.getTransaction().begin();
            for (RecenzieHotel sc : lstRecenzii)
                em.remove(sc);
            em.getTransaction().commit();
        }
        //create recenzie
        em.getTransaction().begin();
        RecenzieHotel rec1 = new RecenzieHotel(401, client4, "Foarte frumos", 5.0);
        RecenzieHotel rec2 = new RecenzieHotel(402, client2, "Minunat!", 4.5);
        RecenzieHotel rec3 = new RecenzieHotel(403, client1, "Superb", 4.0);
        RecenzieHotel rec4 = new RecenzieHotel(404, client6, "Excepțional!", 4.8);
        RecenzieHotel rec5 = new RecenzieHotel(405, client5, "Perfect!", 4.5);
        try {
            em.persist(rec1);
            em.persist(rec2);
            em.persist(rec3);
            em.persist(rec4);
            em.persist(rec5);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }


        //read after create recenzie
        lstRecenzii = em.createQuery("SELECT sc FROM RecenzieHotel sc", RecenzieHotel.class).getResultList();

        for (RecenzieHotel sc: lstRecenzii)
            System.out.println("Id: "+ sc.getIdRecenzie() + " nota: "+ sc.getNota());

        //populare recenzieHotel
        List<Factura> lstFacturi;
        lstFacturi = em.createQuery("SELECT sc FROM Factura sc", Factura.class).getResultList();
        if (!lstRecenzii.isEmpty()) {
            em.getTransaction().begin();
            for (Factura sc : lstFacturi)
                em.remove(sc);
            em.getTransaction().commit();
        }
        //create factura
        em.getTransaction().begin();
        Factura factura1 = new Factura(601, client1, rez1, 680.0);
        Factura factura2 = new Factura(601, client2, rez2, 500.0);
        Factura factura3 = new Factura(601, client3, rez3, 450.0);
        Factura factura4 = new Factura(601, client4, rez4, 526.0);
        Factura factura5 = new Factura(601, client5, rez5, 490.0);
        try {
            em.persist(factura1);
            em.persist(factura2);
            em.persist(factura3);
            em.persist(factura4);
            em.persist(factura5);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }

        //read after create recenzie
        lstFacturi = em.createQuery("SELECT sc FROM Factura sc", Factura.class).getResultList();

        for (Factura sc: lstFacturi)
            System.out.println("Id: "+ sc.getIdFactura()+ " suma: "+ sc.getSuma());

        em.close();
        emf.close();

    }
    }