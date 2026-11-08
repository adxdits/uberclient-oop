package fr.uge.poo.uberclientformatters.question1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class UberClient {

    private final String firstName;
    private final String lastName;
    private final long uid;
    private final List<Integer> grades;
    private final List<String> emails;
    private final List<String> phoneNumbers;

    public static class UberClientBuilder {

        private  String firstName;
        private  String lastName;
        private  long uid;
        private boolean uidSet = false;
        private  List<Integer> grades = new ArrayList<>();
        private  List<String> emails = new ArrayList<>();
        private  List<String> phoneNumbers = new ArrayList<>();

        public UberClientBuilder firstName(String firstName){
            this.firstName=Objects.requireNonNull(firstName);
            return this;
        }

        public UberClientBuilder lastName(String lastName){
            this.lastName=Objects.requireNonNull(lastName);
            return this;
        }

        public UberClientBuilder grade(Integer grade){
            if (grade < 1 ||grade > 5) {
                throw new IllegalArgumentException("All grades must be between 1 and 5");
            }
            grades.add(grade);
            return this;
        }

        public UberClientBuilder phoneNumber(String phoneNumber){
            phoneNumbers.add(Objects.requireNonNull(phoneNumber));
            return this;
        }

        public UberClientBuilder uid(long uid){
            if (uid < 0) {
                throw new IllegalArgumentException("UID must be positive");
            }
            this.uid = uid;
            this.uidSet = true;
            return this;
        }

        public UberClientBuilder randomUID(){
            this.uid = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
            this.uidSet = true;
            return this;
        }

        public UberClientBuilder email(String email){
            emails.add(Objects.requireNonNull(email));
            return this;
        }

        public UberClient build(){
            if (firstName == null || lastName == null){
                throw new IllegalStateException();
            }
            if(!uidSet){
                throw new IllegalStateException("UID must be set explicitly using uid() or randomUid()");
            }
            return new UberClient(this);
        }

    }

    public String toHTML() {
        var averageGrade= grades.stream().mapToLong(l -> l).average().orElseThrow(() -> new AssertionError("Client are meant to have at least one grade"));
        return String.format("<h2>%s %s  (%1.2f*)</h2>",firstName,lastName,averageGrade);
    }

    public String toHTMWithAverageOverLast7Grades() {
        var averageGrade= grades.stream().limit(7).mapToLong(l -> l).average().orElseThrow(() -> new AssertionError("Client are meant to have at least one grade"));
        return String.format("<h2>%s %s  (%1.2f*)</h2>",firstName,lastName,averageGrade);
    }

    public String toHTMLSimple() {
        return String.format("<h2>%s %s </h2>",firstName,lastName);
    }

    public String toHtmlWithEmails() {
            var averageGrade = grades.stream().mapToLong(l -> l).average().orElseThrow(() -> new AssertionError("Client are meant to have at least one grade"));
            return String.format("<h2>%s %s (%1.2f*) : %s </h2>",firstName,lastName,averageGrade,emails);
    }

    public String toHtmlWithEmailsAndAverageOverLast5Grades() {
        var averageGrade= grades.stream().limit(5).mapToLong(l -> l).average().orElseThrow(() -> new AssertionError("Client are meant to have at least one grade"));
        return String.format("<h2>%s %s (%1.2f*) : %s </h2>",firstName,lastName,averageGrade,emails);
    }
    private static UberClientBuilder with() {
        return new UberClientBuilder();
    }

    private UberClient(UberClientBuilder uberClientBuilder) {
        this.firstName = uberClientBuilder.firstName;
        this.lastName = uberClientBuilder.lastName;
        this.uid = uberClientBuilder.uid;
        this.grades = List.copyOf(uberClientBuilder.grades);
        this.emails = List.copyOf(uberClientBuilder.emails);
        this.phoneNumbers = List.copyOf(uberClientBuilder.phoneNumbers);
    }

    public static void main(String[] args) {
        var arnaud = UberClient.with()
                .firstName("Arnaud")
                .lastName("Carayol")
                .uid(1)
                .grade(1)
                .grade(2)
                .grade(5)
                .grade(5)  .grade(2)  .grade(5)  .grade(1)  .grade(2)  .grade(5)  .grade(5)
                .email("arnaud.carayol@univ-eiffel.fr")
                .email("arnaud.carayol@u-pem.fr")
                .phoneNumber("07070707070707")
                .build();

        System.out.println(arnaud.toHTML());
        System.out.println(arnaud.toHTMLSimple());
        System.out.println(arnaud.toHTMWithAverageOverLast7Grades());
        System.out.println(arnaud.toHtmlWithEmails());
        System.out.println(arnaud.toHtmlWithEmailsAndAverageOverLast5Grades());
    }
}


