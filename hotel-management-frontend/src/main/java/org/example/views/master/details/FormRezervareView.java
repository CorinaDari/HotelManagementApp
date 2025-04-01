package org.example.views.master.details;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.example.MainView;
import org.example.RezervareCamera;
import org.example.ServiciuCamera;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;

import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
@PageTitle("rezervare")
@Route(value = "rezervare", layout = MainView.class)
public class FormRezervareView extends VerticalLayout implements HasUrlParameter<Integer> {
    private static final long serialVersionUID = 1L;

    private EntityManager em;
    private RezervareCamera rezervare = null;
    private List<ServiciuCamera> serviciiCamera = new ArrayList<>();
    private Binder<RezervareCamera> binder = new Binder<>(RezervareCamera.class);

    private VerticalLayout formLayoutToolbar;
    private IntegerField id = new IntegerField("ID Rezervare:");
    private DatePicker dataCheckIn = new DatePicker("Data Check-In:");
    private DatePicker dataCheckOut = new DatePicker("Data Check-Out:");
    private ComboBox<ServiciuCamera> serviciuCamera = new ComboBox<>("Serviciu Camera");

    private Button cmdAdaugare = new Button("Adauga");
    private Button cmdSterge = new Button("Sterge");
    private Button cmdAbandon = new Button("Abandon");
    private Button cmdSalveaza = new Button("Salveaza");

    private Grid<ServiciuCamera> serviciiCameraGrid = new Grid<>(ServiciuCamera.class, false);
    private Button cmdAdaugaServiciuCamera = new Button("Adauga Serviciu Camera");
    private Button cmdStergeServiciuCamera = new Button("Sterge Serviciu Camera");

    public FormRezervareView() {
        initDataModel();
        initViewLayout();
        initControllerActions();
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Integer id) {
        if (id != null) {
            this.rezervare = em.find(RezervareCamera.class, id);
            if (this.rezervare == null && !this.serviciiCamera.isEmpty()) {
                this.rezervare = this.serviciiCamera.get(0).getRezervare();
            }
        }
        refreshForm();
    }

    private void initDataModel() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("RezervareCameraJPA");
        em = emf.createEntityManager();

        this.serviciiCamera = em.createQuery("SELECT sc FROM ServiciuCamera sc", ServiciuCamera.class).getResultList();

        binder.bind(id, "idRezervare");
        binder.bind(dataCheckIn, "dataCheckIn");
        binder.bind(dataCheckOut, "dataCheckOut");
        binder.bind(serviciuCamera, "serviciiCamera");

        serviciiCameraGrid.addColumn("idServiciu").setHeader("ID Serviciu");
        serviciiCameraGrid.addColumn(sc -> sc.getTipServiciu()).setHeader("Tip Serviciu");

        initDetailsGridEditor();
        serviciiCameraGrid.setItems(serviciiCamera);
    }

    private void initViewLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(id, dataCheckIn, dataCheckOut, serviciuCamera);
        formLayout.setResponsiveSteps(new ResponsiveStep("0", 1));
        formLayout.setMaxWidth("400px");

        HorizontalLayout actionToolbar = new HorizontalLayout(cmdAdaugare, cmdSterge, cmdAbandon, cmdSalveaza);

        serviciiCameraGrid.setColumns("idServiciu", "tipServiciu");

        HorizontalLayout gridDetailsToolbar = new HorizontalLayout(cmdAdaugaServiciuCamera, cmdStergeServiciuCamera);

        VerticalLayout formLayoutToolbar = new VerticalLayout(formLayout, actionToolbar, serviciiCameraGrid, gridDetailsToolbar);

        add(formLayoutToolbar);
    }

    private void initControllerActions() {
        cmdAdaugare.addClickListener(e -> {
            adaugaRezervare();
            refreshForm();
        });

        cmdSterge.addClickListener(e -> {
            stergeRezervare();
            getUI().ifPresent(ui -> ui.navigate(NavigableGridRezervareView.class));
        });

        cmdAbandon.addClickListener(e -> {
            getUI().ifPresent(ui -> ui.navigate(NavigableGridRezervareView.class));
        });

        cmdSalveaza.addClickListener(e -> {
            salveazaRezervare();
            getUI().ifPresent(ui -> ui.navigate(NavigableGridRezervareView.class, rezervare.getId()));
        });

        cmdAdaugaServiciuCamera.addClickListener(e -> adaugaServiciuCamera());
        cmdStergeServiciuCamera.addClickListener(e -> stergeServiciuCamera());
    }

    private void initDetailsGridEditor() {
        Binder<ServiciuCamera> gridBinder = new Binder<>(ServiciuCamera.class);
        Editor<ServiciuCamera> gridEditor = serviciiCameraGrid.getEditor();

        IntegerField idServiciuField = new IntegerField();
        idServiciuField.setWidthFull();
        idServiciuField.setReadOnly(true);
        gridBinder.bind(idServiciuField, "idServiciu");
        serviciiCameraGrid.getColumnByKey("idServiciu").setEditorComponent(idServiciuField);

        TextField tipServiciuField = new TextField();
        tipServiciuField.setWidthFull();
        gridBinder.bind(tipServiciuField, "tipServiciu");
        serviciiCameraGrid.getColumnByKey("tipServiciu").setEditorComponent(tipServiciuField);

        serviciiCameraGrid.addItemDoubleClickListener(e -> {
            gridEditor.editItem(e.getItem());
            Component editorComponent = e.getColumn().getEditorComponent();
            if (editorComponent instanceof Focusable) {
                ((Focusable) editorComponent).focus();
            }
        });
    }

    private void adaugaRezervare() {
        rezervare = new RezervareCamera();
        rezervare.setId(999);
        rezervare.setDataCheckIn(null);
        rezervare.setDataCheckOut(null);
        rezervare.setServiciiCamera(new ArrayList<>());
    }

    private void stergeRezervare() {
        if (em.contains(rezervare)) {
            em.getTransaction().begin();
            em.remove(rezervare);
            em.getTransaction().commit();
        }
    }

    private void salveazaRezervare() {
        em.getTransaction().begin();
        rezervare = em.merge(rezervare);
        em.getTransaction().commit();
    }

    private void adaugaServiciuCamera() {
        ServiciuCamera newServiciuCamera = new ServiciuCamera();
        newServiciuCamera.setIdServiciu(999);
        newServiciuCamera.setRezervare(rezervare);
        newServiciuCamera.setTipServiciu("Nou");
        rezervare.getServiciiCamera().add(newServiciuCamera);

        updateServiciuCameraGrid();

        serviciiCameraGrid.asSingleSelect().setValue(newServiciuCamera);
        serviciiCameraGrid.getEditor().editItem(newServiciuCamera);

        Focusable gridColumnEditor = (Focusable) serviciiCameraGrid.getColumnByKey("idServiciu").getEditorComponent();
        gridColumnEditor.focus();
    }

    private void stergeServiciuCamera() {
        ServiciuCamera currentServiciuCamera = serviciiCameraGrid.asSingleSelect().getValue();
        if (currentServiciuCamera != null) {
            rezervare.getServiciiCamera().remove(currentServiciuCamera);
            updateServiciuCameraGrid();
        }
    }

    private void updateServiciuCameraGrid() {
        if (rezervare != null && rezervare.getServiciiCamera() != null) {
            serviciiCameraGrid.setItems(rezervare.getServiciiCamera());
        }
    }

    private void refreshForm() {
        if (rezervare != null) {
            binder.setBean(rezervare);
            updateServiciuCameraGrid();
        }
    }
}