import express, { Application, Request, Response, NextFunction } from 'express';
import sequelize from './config/database';
import pistaRoutes from './routes/pistaRoutes';
import reservaRoutes from './routes/reservaRoutes';

// Importar modelos para establecer relaciones
import './models/pista';
import './models/reserva';

const app: Application = express();
const PORT = process.env.PORT || 3000;

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.use((req: Request, res: Response, next: NextFunction) => {
  console.log(`[${new Date().toISOString()}] ${req.method} ${req.path}`);
  next();
});

// Rutas - DESPUÉS de los middlewares
app.use('/pistas', pistaRoutes);
app.use('/reservas', reservaRoutes);

// Ruta raíz - Documentación
app.get('/', (req: Request, res: Response) => {
  res.json({
    mensaje: '🎾 API de Reservas de Pistas de Pádel',
    version: '1.0.0',
    endpoints: {
      pistas: {
        'POST /pistas': 'Crear pista',
        'GET /pistas': 'Listar pistas',
        'GET /pistas/:id': 'Obtener pista por ID',
        'PUT /pistas/:id': 'Actualizar pista',
        'DELETE /pistas/:id': 'Eliminar pista',
      },
      reservas: {
        'POST /reservas': 'Crear reserva',
        'GET /reservas': 'Listar reservas (filtros: ?fecha=YYYY-MM-DD&pista_id=N)',
        'GET /reservas/:id': 'Obtener reserva por ID',
        'DELETE /reservas/:id': 'Cancelar reserva',
      },
    },
  });
});

// Manejo de rutas no encontradas
app.use((req: Request, res: Response) => {
  res.status(404).json({
    error: 'Ruta no encontrada',
    path: req.path,
  });
});

// Manejo de errores global
app.use((err: Error, req: Request, res: Response, next: NextFunction) => {
  console.error('Error:', err);
  res.status(500).json({
    error: 'Error interno del servidor',
    detalle: err.message,
  });
});

// Manejo de rutas no encontradas
app.use((req: Request, res: Response) => {
  res.status(404).json({
    error: 'Ruta no encontrada',
    path: req.path,
  });
});

// Middlewares - DEBEN estar ANTES de las rutas
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Luego las rutas
app.use('/pistas', pistaRoutes);
app.use('/reservas', reservaRoutes);

// Función para iniciar el servidor
async function iniciarServidor() {
  try {
    // Verificar conexión a la base de datos
    console.log('🔄 Verificando conexión a MySQL...');
    await sequelize.authenticate();
    console.log('✅ Conexión a MySQL establecida correctamente');
    console.log('📊 Base de datos: club_padel_db');

    app.listen(PORT, () => {
      console.log('');
      console.log('='.repeat(50));
      console.log('Servidor iniciado correctamente');
      console.log('URL: http://localhost:' + PORT);
      console.log('Base de datos: club_padel_db');
    });
  } catch (error) {
    console.error('Error al iniciar el servidor:', error);
    process.exit(1);
  }
}

iniciarServidor();

export default app;