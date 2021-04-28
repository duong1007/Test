package c1020g1.social_network.repository;

import c1020g1.social_network.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {



    @Modifying
    @Query(value = "INSERT INTO account (account_name, password) VALUES (:#{#account.accountName}, :#{#account.password})",nativeQuery = true)
    void createAccount(Account account);

    @Transactional
    @Modifying
    @Query(value ="UPDATE account SET password = :password WHERE account_name = :accountName",nativeQuery = true)
    void changePassword(@Param("password") String encodePw, @Param("accountName") String accountName );

    @Query(value = "SELECT * FROM account WHERE account.account_name = :accountName", nativeQuery = true)
    Account getAccountByName(@Param("accountName") String accountName);
}
