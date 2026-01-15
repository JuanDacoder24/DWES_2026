import { Request, Response } from 'express';
import Pista from '../models/pista';

export class PistaController {
  // POST /pistas - Crear pista
  static async crear(req: Request, res: Response): Promise<void> {
    try {
      const { nombre, tipo, precio_hora } = req.body;

      // Validaciones
      if (!nombre || !tipo || precio_hora === undefined) {
        res.status(400).json({
          error: 'Faltan campos obligatorios: nombre, tipo, precio_hora',
        });
        return;
      }

      if (!['INDOOR', 'OUTDOOR'].includes(tipo)) {
        res.status(400).json({
          error: 'El tipo debe ser INDOOR o OUTDOOR',
        });
        return;
      }

      const pista = await Pista.create({
        nombre,
        tipo,
        precio_hora,
      });

      res.status(201).json({
        mensaje: 'Pista creada correctamente',
        pista,
      });
    } catch (error: any) {
      if (error.name === 'SequelizeUniqueConstraintError') {
        res.status(409).json({
          error: 'Ya existe una pista con ese nombre',
        });
        return;
      }
      res.status(500).json({
        error: 'Error al crear la pista',
        detalle: error.message,
      });
    }
  }

  // GET /pistas - Listar todas las pistas
  static async listar(req: Request, res: Response): Promise<void> {
    try {
      const pistas = await Pista.findAll({
        order: [['id', 'ASC']],
      });

      res.json({
        total: pistas.length,
        pistas,
      });
    } catch (error: any) {
      res.status(500).json({
        error: 'Error al listar las pistas',
        detalle: error.message,
      });
    }
  }

  // GET /pistas/:id - Obtener una pista
  static async obtenerPorId(req: Request, res: Response): Promise<void> {
    try {
      const { id } = req.params;
      const pista = await Pista.findByPk(id);

      if (!pista) {
        res.status(404).json({
          error: 'Pista no encontrada',
        });
        return;
      }

      res.json(pista);
    } catch (error: any) {
      res.status(500).json({
        error: 'Error al obtener la pista',
        detalle: error.message,
      });
    }
  }

  // PUT /pistas/:id - Actualizar una pista
  static async actualizar(req: Request, res: Response): Promise<void> {
    try {
      const { id } = req.params;
      const { nombre, tipo, precio_hora } = req.body;

      // Validaciones
      if (!nombre || !tipo || precio_hora === undefined) {
        res.status(400).json({
          error: 'Faltan campos obligatorios: nombre, tipo, precio_hora',
        });
        return;
      }

      if (!['INDOOR', 'OUTDOOR'].includes(tipo)) {
        res.status(400).json({
          error: 'El tipo debe ser INDOOR o OUTDOOR',
        });
        return;
      }

      const pista = await Pista.findByPk(id);

      if (!pista) {
        res.status(404).json({
          error: 'Pista no encontrada',
        });
        return;
      }

      await pista.update({
        nombre,
        tipo,
        precio_hora,
      });

      res.json({
        mensaje: 'Pista actualizada correctamente',
        pista,
      });
    } catch (error: any) {
      if (error.name === 'SequelizeUniqueConstraintError') {
        res.status(409).json({
          error: 'Ya existe una pista con ese nombre',
        });
        return;
      }
      res.status(500).json({
        error: 'Error al actualizar la pista',
        detalle: error.message,
      });
    }
  }

  // DELETE /pistas/:id - Eliminar una pista
  static async eliminar(req: Request, res: Response): Promise<void> {
    try {
      const { id } = req.params;
      const pista = await Pista.findByPk(id);

      if (!pista) {
        res.status(404).json({
          error: 'Pista no encontrada',
        });
        return;
      }

      await pista.destroy();

      res.json({
        mensaje: 'Pista eliminada correctamente',
      });
    } catch (error: any) {
      if (error.name === 'SequelizeForeignKeyConstraintError') {
        res.status(409).json({
          error: 'No se puede eliminar la pista porque tiene reservas asociadas',
        });
        return;
      }
      res.status(500).json({
        error: 'Error al eliminar la pista',
        detalle: error.message,
      });
    }
  }
}