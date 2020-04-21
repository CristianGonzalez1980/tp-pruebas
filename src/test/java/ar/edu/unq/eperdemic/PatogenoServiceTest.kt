package ar.edu.unq.eperdemic

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.persistencia.dao.PatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.jdbc.JDBCPatogenoDAO
import ar.edu.unq.eperdemic.services.PatogenoService
import ar.edu.unq.eperdemic.services.impl.PatogenoServiceImpl
import ar.edu.unq.eperdemic.utils.DataService
import ar.edu.unq.eperdemic.utils.jdbc.DataServiceJDBC
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class PatogenoServiceTest {
    private val dao: PatogenoDAO = JDBCPatogenoDAO()
    private val serviceDao: PatogenoServiceImpl = PatogenoServiceImpl(dao)
    private val modelo: DataService = DataServiceJDBC()
    lateinit var patogenoRaro: Patogeno


    @Before
    fun crearModelo() {
        modelo.crearSetDeDatosIniciales()
    }

    @Test
    fun crerUnCuartoPatogenoSeCorroboraNumeroDeId() {
        patogenoRaro = Patogeno("Priones")
        patogenoRaro.id = serviceDao.crearPatogeno(patogenoRaro)
        Assert.assertEquals(4, patogenoRaro.id )
    }

    @Test
    fun seAgregaUnaEspecieSeCorroboraLaActualizacionDelPatogeno() {
        serviceDao.agregarEspecie(3, "VacaLoca", "Reino Unido")
        Assert.assertEquals(1, serviceDao.recuperarPatogeno(3).cantidadDeEspecies)
    }

    @Test
    fun seRecuperanTodosLosPatogenosSeCorroboraCantidad() {
        Assert.assertEquals(3, serviceDao.recuperarATodosLosPatogenos().size)
    }

    @After
    fun emilinarModelo() {
        modelo.eliminarTodo()
    }
}