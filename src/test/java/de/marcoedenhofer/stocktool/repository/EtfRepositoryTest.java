package de.marcoedenhofer.stocktool.repository;

import de.marcoedenhofer.stocktool.model.Etf;
import de.marcoedenhofer.stocktool.testutils.DummyDataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EtfRepositoryTest {

    private final TestEntityManager testEntityManager;
    private final IEtfRepository etfRepository;

    @Autowired
    EtfRepositoryTest(TestEntityManager testEntityManager, IEtfRepository etfRepository) {
        this.testEntityManager = testEntityManager;
        this.etfRepository = etfRepository;
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void save_2Etfs_with2StocksEachOne() {
        Etf etf1 = DummyDataGenerator.generateDummyEtf1();
        Etf etf2 = DummyDataGenerator.generateDummyEtf2();
        etfRepository.save(etf1);
        etfRepository.save(etf2);

        long count = etfRepository.count();
        assertThat(count).isEqualTo(2);

        List<Etf> etfs = new ArrayList<>();
        etfRepository.findAll().forEach(etfs::add);
        etfs.forEach(etf -> {
            int elements = etf.getStocks().size();
            assertThat(elements).isEqualTo(2);
        });
    }

    @Test
    void delete_noEtfsLeft() {
        Etf etf1 = DummyDataGenerator.generateDummyEtf1();
        Etf etf2 = DummyDataGenerator.generateDummyEtf2();
        etfRepository.save(etf1);
        etfRepository.save(etf2);

        long countBeforeDeletion = etfRepository.count();
        assertThat(countBeforeDeletion).isEqualTo(2);

        etfRepository.findAll().forEach(etfRepository::delete);
        long countAfterDeletion = etfRepository.count();

        assertThat(countAfterDeletion).isEqualTo(0);
    }
}