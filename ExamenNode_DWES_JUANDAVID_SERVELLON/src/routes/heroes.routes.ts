import {Router} from 'express'
import * as heroesController from '../controller/heroes.controller'

const router = Router()

router.get('/', heroesController.getAllHeroes)
router.get('/:id', heroesController.getHeroeById)
router.post('/registro', heroesController.crearHeroe)
router.put('/:id', heroesController.actualizarHeroe)
router.delete('/:id', heroesController.eliminarHeroe)

export default router