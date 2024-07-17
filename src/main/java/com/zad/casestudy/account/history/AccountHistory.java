package com.zad.casestudy.account.history;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NaturalId;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table(name = "accounthistory", schema = "casestudy")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String username;
    String currencyCode;
    BigDecimal balance;
    LocalDateTime operationTime;
    @Convert(converter = OperationTypeConverter.class)
    OperationType operationType;
}
