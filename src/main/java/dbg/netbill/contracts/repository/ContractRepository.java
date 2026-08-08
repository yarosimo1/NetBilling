package dbg.netbill.contracts.repository;

import dbg.netbill.contracts.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    Optional<Contract> findByNumberAndUserId(Long contractNumber, Long userId);

    List<Contract> findByUserId(Long userId);


    Optional<Contract> findByNumber(Long contactNumber);
}
