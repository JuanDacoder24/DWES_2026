import express, { Application } from 'express';
import cors from 'cors';
import dotenv from 'dotenv';
import bookRoutes from './routes/book.routes';
import { errorHandler, notFound } from './middlewares/errorHandler';
import { testConnection } from './config/database';

dotenv.config();

const app: Application = express();
const PORT = process.env.PORT || 3000;

// Middlewares
app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Ruta raíz
app.get('/', (req, res) => {
  res.json({
    message: '📚 API de Biblioteca - Bienvenido',
    version: '1.0.0',
    endpoints: {
      books: '/api/books',
      search: '/api/books/search?q=query',
      bookById: '/api/books/:id'
    }
  });
});

// Rutas de la API
app.use('/api', bookRoutes);

// Manejo de errores
app.use(notFound);
app.use(errorHandler);

// Iniciar servidor
const startServer = async () => {
  try {
    await testConnection();
    
    app.listen(PORT, () => {
      console.log(`🚀 Servidor corriendo en http://localhost:${PORT}`);
      console.log(`📝 Entorno: ${process.env.NODE_ENV || 'development'}`);
    });
  } catch (error) {
    console.error('Error al iniciar el servidor:', error);
    process.exit(1);
  }
};

startServer();
