import { Router } from "express";
import * as antiequiposController from '../controller/antiequipos.controller'

const router = Router()

router.get('/', antiequiposController.getAllAntiEquipos)
router.get('/:id', antiequiposController.getAntiEquipoById)
router.post('/registro', antiequiposController.crearAntiequipo)
router.put('/:id', antiequiposController.actualizarAntiEquipo)
router.delete('/:id', antiequiposController.eliminarAntiEquipo)

export default router