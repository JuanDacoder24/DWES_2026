import { Request, Response } from 'express';
import { Op } from 'sequelize';
import Reserva from '../models/reserva';
import Pista from '../models/pista';

export class ReservaController {
  // Método auxiliar para verificar solapes
  private static async verificarSolape(
    pista_id: number,
    fecha: Date,
    hora_inicio: string,
    hora_fin: string,
    reserva_id?: number
  ): Promise<boolean> {
    const whereClause: any = {
      pista_id,
      fecha,
      [Op.or]: [
        // Caso 1: La nueva reserva empieza durante una reserva existente
        {
          hora_inicio: { [Op.lt]: hora_fin },
          hora_fin: { [Op.gt]: hora_inicio },
        },
        // Caso 2: La nueva reserva envuelve completamente una reserva existente
        {
          hora_inicio: { [Op.gte]: hora_inicio },
          hora_fin: { [Op.lte]: hora_fin },
        },
      ],
    };

    // Si estamos actualizando, excluir la reserva actual
    if (reserva_id) {
      whereClause.id = { [Op.ne]: reserva_id };
    }

    const reservasExistentes = await Reserva.findAll({
      where: whereClause,
    });

    return reservasExistentes.length > 0;
  }

  // POST /reservas - Crear reserva
  static async crear(req: Request, res: Response): Promise<void> {
    try {
      const { pista_id, fecha, hora_inicio, hora_fin } = req.body;

      // Validaciones
      if (!pista_id || !fecha || !hora_inicio || !hora_fin) {
        res.status(400).json({
          error: 'Faltan campos obligatorios: pista_id, fecha, hora_inicio, hora_fin',
        });
        return;
      }

      // Validar formato de hora (HH:MM:SS)
      const horaRegex = /^([0-1]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$/;
      if (!horaRegex.test(hora_inicio) || !horaRegex.test(hora_fin)) {
        res.status(400).json({
          error: 'El formato de hora debe ser HH:MM:SS',
        });
        return;
      }

      // Validar que hora_fin > hora_inicio
      if (hora_fin <= hora_inicio) {
        res.status(400).json({
          error: 'La hora de fin debe ser posterior a la hora de inicio',
        });
        return;
      }

      // Verificar que la pista existe
      const pista = await Pista.findByPk(pista_id);
      if (!pista) {
        res.status(404).json({
          error: 'La pista especificada no existe',
        });
        return;
      }

      // Verificar solapes
      const haySolape = await ReservaController.verificarSolape(
        pista_id,
        fecha,
        hora_inicio,
        hora_fin
      );

      if (haySolape) {
        res.status(409).json({
          error: 'Ya existe una reserva que solapa con este horario',
          mensaje: 'Conflicto de horario: ya existe una reserva en ese intervalo',
        });
        return;
      }

      // Crear la reserva
      const reserva = await Reserva.create({
        pista_id,
        fecha,
        hora_inicio,
        hora_fin,
      });

      res.status(201).json({
        mensaje: 'Reserva creada correctamente',
        reserva,
      });
    } catch (error: any) {
      res.status(500).json({
        error: 'Error al crear la reserva',
        detalle: error.message,
      });
    }
  }

  // GET /reservas - Listar reservas con filtros opcionales
  static async listar(req: Request, res: Response): Promise<void> {
    try {
      const { fecha, pista_id } = req.query;

      const whereClause: any = {};

      if (fecha) {
        whereClause.fecha = fecha;
      }

      if (pista_id) {
        whereClause.pista_id = pista_id;
      }

      const reservas = await Reserva.findAll({
        where: whereClause,
        include: [
          {
            model: Pista,
            as: 'pista',
            attributes: ['id', 'nombre', 'tipo', 'precio_hora'],
          },
        ],
        order: [
          ['fecha', 'ASC'],
          ['hora_inicio', 'ASC'],
        ],
      });

      let mensaje = 'Listado de reservas';
      if (fecha) mensaje += ` para la fecha ${fecha}`;
      if (pista_id) mensaje += ` de la pista ${pista_id}`;

      res.json({
        mensaje,
        total: reservas.length,
        reservas,
      });
    } catch (error: any) {
      res.status(500).json({
        error: 'Error al listar las reservas',
        detalle: error.message,
      });
    }
  }

  // GET /reservas/:id - Obtener una reserva
  static async obtenerPorId(req: Request, res: Response): Promise<void> {
    try {
      const { id } = req.params;

      const reserva = await Reserva.findByPk(id, {
        include: [
          {
            model: Pista,
            as: 'pista',
            attributes: ['id', 'nombre', 'tipo', 'precio_hora'],
          },
        ],
      });

      if (!reserva) {
        res.status(404).json({
          error: 'Reserva no encontrada',
        });
        return;
      }

      res.json(reserva);
    } catch (error: any) {
      res.status(500).json({
        error: 'Error al obtener la reserva',
        detalle: error.message,
      });
    }
  }

  // DELETE /reservas/:id - Cancelar/eliminar reserva
  static async eliminar(req: Request, res: Response): Promise<void> {
    try {
      const { id } = req.params;

      const reserva = await Reserva.findByPk(id);

      if (!reserva) {
        res.status(404).json({
          error: 'Reserva no encontrada',
        });
        return;
      }

      await reserva.destroy();

      res.json({
        mensaje: 'Reserva cancelada/eliminada correctamente',
      });
    } catch (error: any) {
      res.status(500).json({
        error: 'Error al eliminar la reserva',
        detalle: error.message,
      });
    }
  }
}