package ar.edu.unq.eperdemic.utils

import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.persistencia.dao.PatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.jdbc.JDBCPatogenoDAO
import ar.edu.unq.eperdemic.utils.jdbc.DataServiceJDBC
import org.junit.After
import org.junit.Assert
import org.junit.Test

class PatogenoDaoTest {

    private val dao: PatogenoDAO = JDBCPatogenoDAO()
    private val modelo: DataService = DataServiceJDBC()
    lateinit var patogenoRaro: Patogeno

    @Test
    fun crerUnCuartoPatogenoSeCorroboraNumeroDeId() {
        patogenoRaro = Patogeno("Priones")
        // patogenoRaro.id = es necesario guardar el id en el modelo (es un VAL incializado como Int?)
        Assert.assertEquals(4, dao.crear(patogenoRaro))
    }

    @Test
    fun seAgregaUnaEspecieSeCorroboraLaActualizacionDelPatogeno() {
        patogenoRaro.crearEspecie("VacaLoca", "Reino Unido")
        dao.actualizar(patogenoRaro)
        Assert.assertEquals(1, dao.recuperar(4).cantidadDeEspecies)
    }

    @Test
    fun seRecuperanTodosLosPatogenosSeCorroboraCantidad() {
        Assert.assertEquals(4, dao.recuperarATodos().size)
    }

    @After
    fun emilinarModelo() {
        modelo.eliminarTodo()
    }
}