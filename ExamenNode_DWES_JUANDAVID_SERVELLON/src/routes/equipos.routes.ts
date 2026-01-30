import { Router } from "express";
import * as equiposController from '../controller/equipos.controller'

const router = Router()

router.get('/', equiposController.getAllEquipos)
router.get('/:id', equiposController.getEquipoById)
router.post('/registro', equiposController.crearequipo)
router.put('/:id', equiposController.actualizarEquipo)
router.delete('/:id', equiposController.eliminarEquipo)

export default router