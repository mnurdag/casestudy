package com.zad.accountservice.account;

import com.zad.accountservice.exception.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountRepository {

    EntityManager entityManager;

    public void save(Account account) {
        entityManager.persist(account);
    }

    public Account findByUsernameAndCurrencyCode(String username, String currencyCode) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Account> query = cb.createQuery(Account.class);
            Root<Account> root = query.from(Account.class);
            Predicate usernameAndCurrencyCodePredicate = cb.and(
                    cb.equal(root.get("username"), username),
                    cb.equal(root.get("currencyCode"), currencyCode)
            );
            query.select(root).where(usernameAndCurrencyCodePredicate);
            return entityManager.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            throw new ApiException("Account doesn't exits! ",
                    "(username:" + username + ", currencyCode" + currencyCode + ")");
        }
    }

    public List<Account> getAccountList(List<GetAccountRequest> requestList) {
        StringBuilder sb = new StringBuilder("""
                select * from casestudy.account
                where (account.username, account.currency_code) in ( 
                """);
        List<String> accountPairList = requestList.stream().map(getAccountRequest -> {
            StringBuilder stringBuilder = new StringBuilder("('");
            stringBuilder.append(getAccountRequest.getUsername());
            stringBuilder.append("', '");
            stringBuilder.append(getAccountRequest.getCurrencyCode());
            stringBuilder.append("')");
            return stringBuilder.toString();
        }).toList();
        sb.append(String.join(",", accountPairList));
        sb.append(" )");

        return (List<Account>) entityManager.createNativeQuery(sb.toString(), Account.class).getResultList();
    }

}
