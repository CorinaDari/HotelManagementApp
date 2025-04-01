package org.example.views.camere;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.Camera;
import org.example.MainView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@PageTitle("camere")
@Route(value = "camere", layout = MainView.class)
public class NavigableGridCamereView extends VerticalLayout implements HasUrlParameter<Integer>{
    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Integer id) {
        if (id != null) {
            this.camera = em.find(Camera.class, id);
            System.out.println("Back camera: " + camera);
            if (this.camera == null) {
                // DELETED Item
                if (!this.camere.isEmpty())
                    this.camera = this.camere.get(0);
            }
            // else: EDITED or NEW Item
        }
        this.refreshForm();
    }

    // Definire model date
    private EntityManager em;
    private List<Camera> camere = new ArrayList<>();
    private Camera camera = null;
    // Definire componente view
    private H1 titluForm = new H1("Lista Camere");
    // Definire componente suport navigare
    private VerticalLayout gridLayoutToolbar;
    private TextField filterText = new TextField();
    private Button cmdEditCamera = new Button("Editeaza camera...");
    private Button cmdAdaugaCamera = new Button("Adauga camera...");
    private Button cmdStergeCamera = new Button("Sterge camera");
    private Grid<Camera> grid = new Grid<>(Camera.class);
    // init Data Model
    private void initDataModel() {
        System.out.println("DEBUG START FORM >>> ");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProduseJPA");
        em = emf.createEntityManager();
        List<Camera> lst = em
                .createQuery("SELECT c FROM Camera c ORDER BY c.numarCamera", Camera.class)
                .getResultList();
        camere.addAll(lst);
        if (lst != null && !lst.isEmpty()) {
            Collections.sort(this.camere, (c1, c2) -> c1.getNumarCamera().compareTo(c2.getNumarCamera()));
            this.camera = camere.get(0);
            System.out.println("DEBUG: camera init >>> " + camera.getNumarCamera());
        }
//
        grid.setItems(this.camere);
        grid.asSingleSelect().setValue(this.camera);
        // init View Mode
        //
    }
        private void initViewLayout() {
// Layout navigare -------------------------------------//
// Toolbar navigare
            filterText.setPlaceholder("Filter by nume...");
            filterText.setClearButtonVisible(true);
            filterText.setValueChangeMode(ValueChangeMode.LAZY);
            HorizontalLayout gridToolbar = new HorizontalLayout(filterText,
                    cmdEditCamera, cmdAdaugaCamera, cmdStergeCamera);
// Grid navigare
            grid.setColumns("id", "nume");
            grid.addComponentColumn(item -> createGridActionsButtons(item)).setHeader("Actiuni");
// Init Layout navigare
            gridLayoutToolbar = new VerticalLayout(gridToolbar, grid);
// ---------------------------
            this.add(titluForm, gridLayoutToolbar);
//
        }
        private Component createGridActionsButtons(Camera item) {
//
            Button cmdEditItem = new Button("Edit");
            cmdEditItem.addClickListener(e -> {
                grid.asSingleSelect().setValue(item);
                editCamera();
            });
            Button cmdDeleteItem = new Button("Sterge");
            cmdDeleteItem.addClickListener(e -> {
                System.out.println("Sterge item: " + item);
                grid.asSingleSelect().setValue(item);
                stergeCamera();
                refreshForm();
            } );
//
            return new HorizontalLayout(cmdEditItem, cmdDeleteItem);
        }

    // init Controller components
    private void initControllerActions() {
// Navigation Actions
        filterText.addValueChangeListener(e -> updateList());
        cmdEditCamera.addClickListener(e -> {
            editCamera();
        });
        cmdAdaugaCamera.addClickListener(e -> {
            adaugaCamera();
        });
        cmdStergeCamera.addClickListener(e -> {
            stergeCamera();
            refreshForm();
        });
    }
    // CRUD actions
// Adaugare: delegare catre Formular detalii camera
    private void adaugaCamera() {
        this.getUI().ifPresent(ui -> ui.navigate(String.valueOf(FormCamereView.class)));
    }
    // Editare: delegare catre Formular detalii camera
    private void editCamera() {
        this.camera = this.grid.asSingleSelect().getValue();
        System.out.println("Selected camera:: " + camera);
        if (this.camera != null) {
            this.getUI().ifPresent(ui -> ui.navigate(FormCamereView.class, this.camera.getNumarCamera()));
        }
    }
    // CRUD actions
// Stergere: tranzactie locala cu EntityManager
    private void stergeCamera() {
        this.camera = this.grid.asSingleSelect().getValue();
        System.out.println("To remove: " + this.camera);
        this.camere.remove(this.camera);
        if (this.em.contains(this.camera)) {
            this.em.getTransaction().begin();
            this.em.remove(this.camera);
            this.em.getTransaction().commit();
        }
        if (!this.camere.isEmpty())
            this.camera = this.camere.get(0);
        else
            this.camera = null;
    }
    // Start Form
    public NavigableGridCamereView() {
//
        initDataModel();
//
        initViewLayout();
//
        initControllerActions();
    }
    // Populare grid cu set de date din model - filtrare
    private void updateList() {
        try {
            List<Camera> lstCamereFiltered = this.camere;

            if (filterText.getValue() != null) {
                lstCamereFiltered = this.camere.stream()
                        .filter(c -> Boolean.parseBoolean(c.getNumarCamera().toString(Integer.parseInt(filterText.getValue())))) //contains(filterText.getValue()))
                        .toList();

                grid.setItems(lstCamereFiltered);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // refresh
    private void refreshForm() {
        System.out.println("Camera curenta: " + this.camera);
        if (this.camera != null) {
            grid.setItems(this.camera);
            grid.select(this.camera);
        }
    }
}