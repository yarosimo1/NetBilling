package dbg.netbill.contracts.repository;

import dbg.netbill.contracts.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}
