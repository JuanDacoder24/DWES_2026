import { Router } from 'express';
import { PistaController } from '../controllers/pistaController';

const router = Router();

// POST /pistas - Crear pista
router.post('/', PistaController.crear);

// GET /pistas - Listar todas las pistas
router.get('/', PistaController.listar);

// GET /pistas/:id - Obtener una pista
router.get('/:id', PistaController.obtenerPorId);

// PUT /pistas/:id - Actualizar una pista
router.put('/:id', PistaController.actualizar);

// DELETE /pistas/:id - Eliminar una pista
router.delete('/:id', PistaController.eliminar);

export default router;