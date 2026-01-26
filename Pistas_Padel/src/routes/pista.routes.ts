// pista.routes.ts
import { Router } from 'express';
import { 
  crearPista, 
  listarPistas, 
  obtenerPista, 
  actualizarPista, 
  eliminarPista 
} from '../controllers/pistaController';

const router = Router();

// POST /pistas - Crear pista
router.post('/', crearPista);

// GET /pistas - Listar todas las pistas
router.get('/', listarPistas);

// GET /pistas/:id - Obtener una pista
router.get('/:id', obtenerPista);

// PUT /pistas/:id - Actualizar una pista
router.put('/:id', actualizarPista);

// DELETE /pistas/:id - Eliminar una pista
router.delete('/:id', eliminarPista);

export default router;