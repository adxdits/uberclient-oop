package fr.uge.poo.uberclientformatters.question1;

public class HTMLSimpleFormat implements UberClientFormatter {

    @Override
    public String format(UberClientInfo uberClient) {
        return String.format("<h2>%s %s</h2>", uberClient.firstName(), uberClient.lastName());
    }
}

