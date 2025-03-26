package org.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.Camera;
import org.example.MainView;

@PageTitle("camera")
@Route(value = "camera", layout = MainView.class)
public class FormCamereView extends VerticalLayout implements HasUrlParameter<Integer> {

    private EntityManager em;
    private Camera camera;
    private Binder<Camera> binder = new BeanValidationBinder<>(Camera.class);

    private VerticalLayout formLayoutToolbar;
    private H1 titluForm = new H1("Form Camera");
    private IntegerField id = new IntegerField("ID camera:");
    private TextField nume = new TextField("Nume camera:");

    private Button cmdAdaugare = new Button("Adauga");
    private Button cmdSterge = new Button("Sterge");
    private Button cmdAbandon = new Button("Abandon");
    private Button cmdSalveaza = new Button("Salveaza");

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Integer id) {
        System.out.println("Camera ID: " + id);
        if (id != null) {
            this.camera = em.find(Camera.class, id);
            System.out.println("Selected camera to edit: " + camera);
            if (this.camera == null) {
                System.out.println("ADD camera: " + camera);
                this.adaugaCamera();
            }
        }
        this.refreshForm();
    }

    private void initDataModel() {
        System.out.println("DEBUG START FORM >>> ");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("untitled");
        this.em = emf.createEntityManager();
        this.camera = em.createQuery("SELECT c FROM Camera c ORDER BY c.id", Camera.class)
                .getResultStream().findFirst().orElse(new Camera());
        binder.bindInstanceFields(this);
        refreshForm();
    }

    private void initViewLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(id, nume);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        formLayout.setMaxWidth("400px");

        HorizontalLayout actionToolbar = new HorizontalLayout(cmdAdaugare, cmdSterge, cmdAbandon, cmdSalveaza);
        actionToolbar.setPadding(false);

        this.formLayoutToolbar = new VerticalLayout(formLayout, actionToolbar);
        this.add(titluForm, formLayoutToolbar);
    }

    private void initControllerActions() {
        cmdAdaugare.addClickListener(e -> {
            adaugaCamera();
            refreshForm();
        });

        cmdSterge.addClickListener(e -> {
            stergeCamera();
            this.getUI().ifPresent(ui -> ui.navigate(NavigableGridCamereView.class));
        });

        cmdAbandon.addClickListener(e -> {
            this.getUI().ifPresent(ui -> ui.navigate(NavigableGridCamereView.class, this.camera.getNumarCamera()));
        });

        cmdSalveaza.addClickListener(e -> {
            salveazaCamera();
            this.getUI().ifPresent(ui -> ui.navigate(NavigableGridCamereView.class, this.camera.getNumarCamera()));
        });
    }

    private void refreshForm() {
        System.out.println("Camera curenta: " + this.camera);
        if (this.camera != null) {
            binder.setBean(this.camera);
        }
    }

    private void salveazaCamera() {
        try {
            this.em.getTransaction().begin();
            this.camera = this.em.merge(this.camera);
            this.em.getTransaction().commit();
            System.out.println("Camera Salvata");
        } catch (Exception ex) {
            if (this.em.getTransaction().isActive())
                this.em.getTransaction().rollback();
            System.out.println("*** EntityManager Validation ex: " + ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    private void adaugaCamera() {
        this.camera = new Camera();
        this.camera.setNumarCamera(999); // ID arbitrar, inexistent în baza de date
        this.camera.setEtaj(3);
    }

    private void stergeCamera() {
        System.out.println("To remove: " + this.camera);
        if (this.em.contains(this.camera)) {
            this.em.getTransaction().begin();
            this.em.remove(this.camera);
            this.em.getTransaction().commit();
        }
    }

    public FormCamereView() {
        initDataModel();
        initViewLayout();
        initControllerActions();
    }
}
