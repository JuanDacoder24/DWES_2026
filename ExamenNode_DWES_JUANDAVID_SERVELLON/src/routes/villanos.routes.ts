import {Router} from 'express'
import * as villanosController from '../controller/villanos.controller'

const router = Router()

router.get('/', villanosController.getAllVillanos)
router.get('/:id', villanosController.getVillanoById)
router.post('/registro', villanosController.crearVillano)
router.put('/:id', villanosController.actualizarVillano)
router.delete('/:id', villanosController.eliminarvillano)

export default router