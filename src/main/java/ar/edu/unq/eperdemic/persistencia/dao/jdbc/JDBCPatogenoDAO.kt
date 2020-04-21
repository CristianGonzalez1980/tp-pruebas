package ar.edu.unq.eperdemic.persistencia.dao.jdbc

import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.persistencia.dao.PatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.jdbc.JDBCConnector.execute
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement


class JDBCPatogenoDAO : PatogenoDAO {

    override fun crear(patogeno: Patogeno): Int {
        var idGenerado = 0
        execute { conn: Connection ->
            val ps =
                    conn.prepareStatement("INSERT INTO patogeno (tipo, cantEspecies) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)
            ps.setString(1, patogeno.tipo)
            ps.setInt(2, patogeno.cantidadDeEspecies)
            ps.execute()
            if (ps.updateCount != 1) {
                throw RuntimeException("No se inserto el personaje $patogeno")
            }
            val generatedKeys: ResultSet = ps.getGeneratedKeys()
            if (generatedKeys.next()) {
                idGenerado = generatedKeys.getInt(1)
            }
            ps.close()
        }
        return idGenerado
    }

    override fun actualizar(patogeno: Patogeno) {/*
        execute { conn: Connection ->
            val ps =
                    conn.prepareStatement("UPDATE patogeno SET cantEspecies = ? WHERE tipo = ?")//o utilizar ID
            ps.setInt(1, patogeno.cantidadDeEspecies)
            ps.setString(2, patogeno.tipo)
            ps.execute()
            if (ps.updateCount != 1) {
                throw RuntimeException("No se actualizo el patogeno $patogeno")
            }
            ps.close()
            null
        }*/
    }

    override fun recuperar(idDelPatogeno: Int): Patogeno {
        return execute { conn: Connection ->
            val ps = conn.prepareStatement("SELECT tipo, cantEspecies FROM patogeno WHERE id = ?")
            ps.setInt(1, idDelPatogeno)
            val resultSet = ps.executeQuery()
            var patogeno: Patogeno? = null
            while (resultSet.next()) {
                //si patogeno no es null aca significa que el while dio mas de una vuelta, eso
                //suele pasar cuando el resultado (resultset) tiene mas de un elemento.
                if (patogeno != null) {
                    throw RuntimeException("Existe mas de un patogeno con el id $idDelPatogeno")
                }
                patogeno = Patogeno(resultSet.getString("tipo"))
                patogeno.cantidadDeEspecies = resultSet.getInt("cantEspecies")
            }
            ps.close()
            patogeno!!
        }
    }

    override fun recuperarATodos(): List<Patogeno> {
        return execute { conn: Connection ->
            val ps = conn.prepareStatement("SELECT tipo, cantEspecies FROM patogeno")
            val resultSet = ps.executeQuery()
            val patogenos: MutableList<Patogeno> = mutableListOf<Patogeno>()
            while (resultSet.next()) {
                var patogeno = Patogeno(resultSet.getString("tipo"))
                patogeno.cantidadDeEspecies = resultSet.getInt("cantEspecies")
                patogenos.add(patogeno)
            }
            ps.close()
            patogenos
        }
    }


    init {
        val initializeScript = javaClass.classLoader.getResource("createAll.sql").readText()
        execute {
            val ps = it.prepareStatement(initializeScript)
            ps.execute()
            ps.close()
            null
        }
    }
}