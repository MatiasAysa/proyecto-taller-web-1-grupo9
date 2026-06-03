package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Presupuesto;
import com.tallerwebi.dominio.RepositorioPresupuesto;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioPresupuestoTest {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    RepositorioPresupuesto repositorioPresupuesto;

    @Test
    @Transactional
    @Rollback
    public void sePuedeGuardarUnPresupuesto()
    {
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setMonto(100000);
        presupuesto.setIntervalo(7);
        presupuesto.setFecha(LocalDate.now());

        repositorioPresupuesto.guardarPresupuesto(presupuesto);

        assertThat(presupuesto.getId(), notNullValue());
    }
}
