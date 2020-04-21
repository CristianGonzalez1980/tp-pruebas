package ar.edu.unq.eperdemic.utils

import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.persistencia.dao.PatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.jdbc.JDBCPatogenoDAO
import ar.edu.unq.eperdemic.utils.jdbc.DataServiceJDBC
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import javax.validation.constraints.AssertTrue

class PatogenoDaoTest {

    private val dao: PatogenoDAO = JDBCPatogenoDAO()
    private val modelo: DataService = DataServiceJDBC()
    lateinit var patogenoRaro: Patogeno

    @Before
    fun crearModelo() {
        modelo.crearSetDeDatosIniciales()
    }

    @Test
    fun crerUnCuartoPatogenoSeCorroboraNumeroDeId() {
        patogenoRaro = Patogeno("Priones")
        Assert.assertEquals(4, dao.crear(patogenoRaro))
    }

    @Test
    fun seAgregaUnaEspecieSeCorroboraLaActualizacionDelPatogeno() {
        var patogeno: Patogeno = dao.recuperar(3)
        patogeno.crearEspecie("VacaLoca", "Reino Unido")
        dao.actualizar(patogeno)
        Assert.assertEquals(1, dao.recuperar(3).cantidadDeEspecies)
    }

    @Test
    fun seRecuperanTodosLosPatogenosSeCorroboraCantidad() {
        var patogenos: List<Patogeno> = dao.recuperarATodos()
        Assert.assertEquals(3, patogenos.size)
        var patogenos2: List<Patogeno> = patogenos.sortedBy { it.tipo }
        Assert.assertEquals(patogenos, patogenos2)
    }

    @After
    fun emilinarModelo() {
        modelo.eliminarTodo()
    }
}