package ar.edu.unq.eperdemic.utils.jdbc

import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.persistencia.dao.PatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.jdbc.JDBCConnector
import ar.edu.unq.eperdemic.persistencia.dao.jdbc.JDBCPatogenoDAO
import ar.edu.unq.eperdemic.utils.DataService

class DataServiceJDBC : DataService {

    private val patogenoDao: PatogenoDAO = JDBCPatogenoDAO()
    lateinit var patogeno1: Patogeno
    lateinit var patogeno2: Patogeno
    lateinit var patogeno3: Patogeno

    override fun crearSetDeDatosIniciales() {
        patogeno1 = Patogeno("Virus")
        patogeno2 = Patogeno("Bacteria")
        patogeno3 = Patogeno("Hongo")
        var idpatogeno: Int = patogenoDao.crear(patogeno1)
        patogeno1.id = idpatogeno
        idpatogeno = patogenoDao.crear(patogeno2)
        patogeno2.id = idpatogeno
        idpatogeno = patogenoDao.crear(patogeno3)
        patogeno3.id = idpatogeno
    }

    override fun eliminarTodo() {
        val initializeScript = javaClass.classLoader.getResource("deleteAll.sql").readText()
        JDBCConnector.execute {
            val ps = it.prepareStatement(initializeScript)
            ps.execute()
            ps.close()
            null
        }
    }
}

//quitar este metodo fue implementado para probar que funcionaba el DATASERVICE
fun main(){
    var inicio : DataService = DataServiceJDBC()
   // inicio.crearSetDeDatosIniciales()
    inicio.eliminarTodo()
}