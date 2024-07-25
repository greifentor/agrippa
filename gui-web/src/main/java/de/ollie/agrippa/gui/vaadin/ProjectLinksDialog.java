package de.ollie.agrippa.gui.vaadin;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import de.ollie.agrippa.core.model.Project;
import de.ollie.agrippa.core.model.ProjectLink;
import de.ollie.agrippa.core.model.localization.LocalizationSO;
import de.ollie.agrippa.core.service.localization.ResourceManager;

public class ProjectLinksDialog extends Dialog {

    public ProjectLinksDialog(Project project, ResourceManager resourceManager, LocalizationSO localization) {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidthFull();
        layout.setMargin(false);
        layout.setPadding(false);
        add(new H3(resourceManager.getLocalizedString("ProjectLinksDialog.header.label", localization)));
        add(new Hr());
        project.getProjectLinks().forEach(pl -> add(createLinkLayout(pl)));
        add(layout);
        setWidth("50%");
        open();
    }

    private HorizontalLayout createLinkLayout(ProjectLink projectLink) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();
        layout.setMargin(false);
        layout.setPadding(false);
        Span span = new Span(projectLink.getDescription());
        span.setWidth("67%");
        setMargin(span);
        layout.add(createLabel(getLinksAsHTML(projectLink)));
        layout.add(span);
        return layout;
    }

    private void setMargin(HtmlComponent c) {
        c.getStyle().set("margin", "10px 0px 10px");
    }

    private Component createLabel(String s) {
        Label label = new Label();
        label.getElement().setProperty("innerHTML", s);
        label.setWidth("33%");
        setMargin(label);
        return label;
    }

    private String getLinksAsHTML(ProjectLink pl) {
        return "<A HREF=\"" + pl.getUrl() + "\">" + pl.getTitle() + "</A>";
    }

}
