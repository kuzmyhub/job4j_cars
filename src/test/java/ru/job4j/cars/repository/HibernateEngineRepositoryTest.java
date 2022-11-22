package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Engine;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class HibernateEngineRepositoryTest {

    private static SessionFactory sessionFactory;

    @BeforeAll
    public static void createSessionFactory() {
        StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                        .configure()
                        .build();
        sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }

    @AfterEach
    public void cleanTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Engine")
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void whenAddEngineThenSetEngineId() {
        TemplateRepository hibernateTemplateRepository =
                new HibernateTemplateRepository(sessionFactory);
        EngineRepository hibernateEngineRepository =
                new HibernateEngineRepository(hibernateTemplateRepository);
        Engine engine = new Engine();
        engine.setName("diesel");
        hibernateEngineRepository.add(engine);
        int notExpected = 0;
        assertThat(notExpected).isNotEqualTo(engine.getId());
    }

    @Test
    public void thenAddEngineThenGetSomeEngine() {
        TemplateRepository hibernateTemplateRepository =
                new HibernateTemplateRepository(sessionFactory);
        EngineRepository hibernateEngineRepository =
                new HibernateEngineRepository(hibernateTemplateRepository);
        Engine engine = new Engine();
        engine.setName("diesel");
        hibernateEngineRepository.add(engine);
        Engine dbEngine = hibernateEngineRepository
                .findById(engine.getId())
                .get();
        assertThat(engine.getName()).isEqualTo(dbEngine.getName());
    }

    @Test
    public void thenAddEnginesThenGetSomeEngines() {
        TemplateRepository hibernateTemplateRepository =
                new HibernateTemplateRepository(sessionFactory);
        EngineRepository hibernateEngineRepository =
                new HibernateEngineRepository(hibernateTemplateRepository);
        Engine firstEngine = new Engine();
        firstEngine.setName("diesel");
        Engine secondEngine = new Engine();
        secondEngine.setName("petrol");
        hibernateEngineRepository.add(firstEngine);
        hibernateEngineRepository.add(secondEngine);
        List<Engine> engines = hibernateEngineRepository.findAll();
        assertTrue(engines.contains(firstEngine));
        assertTrue(engines.contains(secondEngine));
    }
}