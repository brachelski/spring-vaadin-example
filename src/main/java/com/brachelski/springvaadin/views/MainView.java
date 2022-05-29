package com.brachelski.springvaadin.views;

import com.brachelski.springvaadin.models.Customer;
import com.brachelski.springvaadin.repositories.CustomerRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;

@Route("/customers")
public class MainView extends VerticalLayout {
    private final CustomerRepository repo;

    private final CustomerEditor editor;

    final Grid<Customer> grid;

    final TextField lastNameFilter;
    final TextField firstNameFilter;

    private final Button addNewBtn;

    public MainView(CustomerRepository repo, CustomerEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>(Customer.class);
        this.lastNameFilter = new TextField();
        this.firstNameFilter = new TextField();
        this.addNewBtn = new Button("New customer", VaadinIcon.PLUS.create());

        HorizontalLayout actions = new HorizontalLayout(firstNameFilter, lastNameFilter, addNewBtn);
        add(actions, grid, editor);

        grid.setHeight("50vh");
        grid.setColumns("id", "firstName", "lastName");
        grid.getColumnByKey("id").setFlexGrow(0);
        grid.getColumnByKey("firstName").setFlexGrow(3);
        grid.getColumnByKey("lastName").setFlexGrow(3);

        lastNameFilter.setPlaceholder("Filter by last name");
        firstNameFilter.setPlaceholder("Filter by first name");

        lastNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        lastNameFilter.addValueChangeListener(e -> listCustomers(e.getValue(), "lastName"));

        firstNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        firstNameFilter.addValueChangeListener(e -> listCustomers(e.getValue(), "firstName"));

        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editCustomer(e.getValue());
        });

        addNewBtn.addClickListener(e -> editor.editCustomer(new Customer("", "")));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listCustomers(lastNameFilter.getValue(), "lastName");
        });

        listCustomers(null, null);
    }

    void listCustomers(String filterText, String filterType) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        }
        else if (filterType != null && filterType.equals("lastName")) {
            firstNameFilter.setValue("");
            grid.setItems(repo.findByLastNameStartsWithIgnoreCase(filterText));
        } else if (filterType != null && filterType.equals("firstName")) {
            lastNameFilter.setValue("");
            grid.setItems(repo.findByFirstNameStartsWithIgnoreCase(filterText));
        }
    }
}
