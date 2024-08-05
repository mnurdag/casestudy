package com.zad.transactionservice.history;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table(name = "history", schema = "casestudy")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String username;
    String currencyCode;
    BigDecimal balance;
    LocalDateTime operationTime;
    @Convert(converter = HistoryTypeConverter.class)
    HistoryType historyType;
}
