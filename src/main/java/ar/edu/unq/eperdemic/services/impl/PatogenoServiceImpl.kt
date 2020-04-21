package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.persistencia.dao.PatogenoDAO
import ar.edu.unq.eperdemic.services.PatogenoService

class PatogenoServiceImpl(patogenoDAO: PatogenoDAO) : PatogenoService {

    var dao: PatogenoDAO = patogenoDAO

    override fun crearPatogeno(patogeno: Patogeno): Int {
        val patogenoId: Int = dao.crear(patogeno)
        patogeno.id = patogenoId
        return patogenoId
    }

    override fun recuperarPatogeno(id: Int): Patogeno {
        return dao.recuperar(id)
    }

    override fun recuperarATodosLosPatogenos(): List<Patogeno> {
        return dao.recuperarATodos()
    }

    override fun agregarEspecie(id: Int, nombreEspecie: String, paisDeOrigen: String): Especie {
        var patogeno: Patogeno = this.recuperarPatogeno(id)
        var nuevaEspecie: Especie = patogeno.crearEspecie(nombreEspecie, paisDeOrigen)
        dao.actualizar(patogeno)
        return nuevaEspecie
    }
}