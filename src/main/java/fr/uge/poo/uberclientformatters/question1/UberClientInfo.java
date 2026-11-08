package fr.uge.poo.uberclientformatters.question1;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record UberClientInfo(String firstName, String lastName, List<String> emails, List<Integer> grades) {
    public UberClientInfo {
        Objects.requireNonNull(firstName, "First name cannot be null");
        Objects.requireNonNull(lastName, "Last name cannot be null");
        Objects.requireNonNull(grades, "Grades cannot be null");
        if (grades.isEmpty()) {
            throw new IllegalArgumentException("A client must have at least one grade");
        }
    }
    
    private String maskEmail(String email){
        Objects.requireNonNull(email, "email cannot be null");
        var parts = email.split("@");
        if(parts.length != 2) return email;
        return parts[0].charAt(0) + "*@" + parts[1].charAt(0) + "*";
    }

    public List<String> maskedEmails(){
        return emails.stream().map(this::maskEmail).collect(Collectors.toList());
    }

    public double calculateAverage(int limit) {
        return grades.stream()
                .limit(limit > 0 ? limit : grades.size())
                .mapToLong(l -> l)
                .average()
                .orElseThrow(() -> new AssertionError("Client must have at least one grade"));
    }
}

