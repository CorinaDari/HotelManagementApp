package org.example;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import org.example.views.camere.FormCamereView;
import org.example.views.camere.NavigableGridCamereView;

@Route
public class MainView extends VerticalLayout implements RouterLayout {
    public MainView() {
        setMenuBar();
    }
    private void setMenuBar() {
        MenuBar mainMenu = new MenuBar();

        MenuItem homeMenu = mainMenu.addItem("Home");
        homeMenu.addClickListener(event -> UI.getCurrent().navigate(MainView.class));

        MenuItem gridFormsCamereMenu = mainMenu.addItem("Camere");
        SubMenu gridFormsCamereMenuBar = gridFormsCamereMenu.getSubMenu();

        gridFormsCamereMenuBar.addItem("Lista Camere...",
                event -> UI.getCurrent().navigate(NavigableGridCamereView.class));

        gridFormsCamereMenuBar.addItem("Form Editare Camere...",
                event -> UI.getCurrent().navigate(FormCamereView.class));

        add(new HorizontalLayout(mainMenu));
    }
}