package fr.uge.poo.uberclientformatters.question1;

public class HTMLFormat implements UberClientFormatter {

    @Override
    public String format(UberClientInfo uberClient) {
        return String.format("<h2>%s %s  (%1.2f*)</h2>", uberClient.firstName(), uberClient.lastName(), uberClient.calculateAverage(uberClient.grades().size()));
    }
}

