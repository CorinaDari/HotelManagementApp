package org.example.views.master.details;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.HasUrlParameter;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.MainView;
import org.example.RezervareCamera;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@PageTitle("rezervari")
@Route(value = "rezervari", layout = MainView.class)
public class NavigableGridRezervareView extends VerticalLayout implements HasUrlParameter<Integer> {
    private static final long serialVersionUID = 1L;

    private EntityManager em;
    private List<RezervareCamera> rezervari = new ArrayList<>();
    private RezervareCamera rezervare = null;

    private H1 titluForm = new H1("Lista Rezervari");

    private VerticalLayout gridLayoutToolbar;
    private TextField filterText = new TextField();
    private Button cmdEditRezervare = new Button("Editeaza rezervarea...");
    private Button cmdAdaugaRezervare = new Button("Adauga rezervare...");
    private Button cmdStergeRezervare = new Button("Sterge rezervare");
    private Grid<RezervareCamera> grid = new Grid<>(RezervareCamera.class);

    public NavigableGridRezervareView() {
        initDataModel();
        initViewLayout();
        initControllerActions();
    }

    
    public void setParameter(BeforeEvent event, @OptionalParameter Integer id) {
        if (id != null) {
            this.rezervare = em.find(RezervareCamera.class, id);
            if (this.rezervare == null && !this.rezervari.isEmpty())
                this.rezervare = this.rezervari.get(0);
        }
        this.refreshForm();
    }

    private void initDataModel() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("untitled");
        em = emf.createEntityManager();

        List<RezervareCamera> lst = em
                .createQuery("SELECT rc FROM RezervareCamera rc ORDER BY rc.idRezervare", RezervareCamera.class)
                .getResultList();
        this.rezervari.addAll(lst);

        if (lst != null && !lst.isEmpty()) {
            Collections.sort(this.rezervari, (r1, r2) -> Integer.parseInt(r1.getCamera().toString()));
            this.rezervare = rezervari.get(0);
            this.rezervare.getServiciiCamera()
                    .sort((s1, s2) -> s1.getIdServiciu().compareTo(s2.getIdServiciu()));
        }

        grid.setItems(this.rezervari);
        grid.asSingleSelect().setValue(this.rezervare);
    }

    private void initViewLayout() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        filterText.setPlaceholder("Filter by id...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        HorizontalLayout gridToolbar = new HorizontalLayout(filterText, cmdEditRezervare, cmdAdaugaRezervare, cmdStergeRezervare);

        grid.setColumns("idRezervare");

        grid.addColumn(rc -> rc.getDataCheckIn() == null ? null : dateFormat.format(rc.getDataCheckIn()))
                .setHeader("Data Check-In")
                .setKey("dataCheckIn");

        grid.addColumn(rc -> rc.getDataCheckOut() == null ? null : dateFormat.format(rc.getDataCheckOut()))
                .setHeader("Data Check-Out")
                .setKey("dataCheckOut");

        grid.addColumn(rc -> rc.getClient().getNume())
                .setKey("client")
                .setHeader("Client");

        grid.addComponentColumn(rc -> createGridActionsButtons(rc))
                .setHeader("Actiuni");

        this.add(titluForm, gridToolbar, grid);
    }

    private void initControllerActions() {
        filterText.addValueChangeListener(e -> updateList());
        cmdEditRezervare.addClickListener(e -> editRezervare());
        cmdAdaugaRezervare.addClickListener(e -> adaugaRezervare());
        cmdStergeRezervare.addClickListener(e -> stergeRezervare());
    }

    private Component createGridActionsButtons(RezervareCamera rezervare) {
        Button cmdEditItem = new Button("Edit");
        cmdEditItem.addClickListener(e -> {
            grid.asSingleSelect().setValue(rezervare);
            editRezervare();
        });

        Button cmdDeleteItem = new Button("Sterge");
        cmdDeleteItem.addClickListener(e -> {
            grid.asSingleSelect().setValue(rezervare);
            stergeRezervare();
            refreshForm();
        });

        return new HorizontalLayout(cmdEditItem, cmdDeleteItem);
    }

    private void editRezervare() {
        this.rezervare = this.grid.asSingleSelect().getValue();
        if (this.rezervare != null) {
            this.getUI().ifPresent(ui -> ui.navigate(FormRezervareView.class, this.rezervare.getId()));
        }
    }

    private void updateList() {
        try {
            List<RezervareCamera> lstRezervariFiltrate = this.rezervari;

            if (filterText.getValue() != null) {
                lstRezervariFiltrate = this.rezervari.stream()
                        .filter(r -> r.getCamera().toString().contains(filterText.getValue()))
                        .toList();

                grid.setItems(lstRezervariFiltrate);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshForm() {
        if (this.rezervare != null) {
            grid.setItems(this.rezervari);
            grid.select(this.rezervare);
        }
    }

    private void adaugaRezervare() {
        this.getUI().ifPresent(ui -> ui.navigate(FormRezervareView.class, 20));
    }

    private void stergeRezervare() {
        this.rezervare = this.grid.asSingleSelect().getValue();
        this.rezervari.remove(this.rezervare);
        if (this.em.contains(this.rezervare)) {
            this.em.getTransaction().begin();
            this.em.remove(this.rezervare);
            this.em.getTransaction().commit();
        }

        if (!this.rezervari.isEmpty())
            this.rezervare = this.rezervari.get(0);
        else
            this.rezervare = null;
    }
}
