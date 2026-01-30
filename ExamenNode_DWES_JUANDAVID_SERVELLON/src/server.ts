import  express  from "express";
import heroesRoutes from '../src/routes/heroes.routes'
import villanosRoutes from '../src/routes/villanos.routes'
import equiposRoutes from '../src/routes/equipos.routes'
import antiequiposRoutes from '../src/routes/antiequipos.routes'

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(express.json());

// Ruta raíz
app.get('/', (req, res) => {
  res.json({ 
    mensaje: 'API de Heroes de Marvel',
    version: '1.0.0',
    endpoints: {
      heroes: '/api/heroes',
      villanos: '/api/villanos',
      equipos: '/api/equipos',
      antiequipos: '/api/antiequipos'
    }
  });
});

app.use('/api/heroes', heroesRoutes)
app.use('/api/villanos', villanosRoutes)
app.use('/api/equipos', equiposRoutes)
app.use('/api/antiequipos', antiequiposRoutes)

// Manejo de rutas no encontradas
app.use((req, res) => {
  res.status(404).json({ error: 'Ruta no encontrada' });
});

// Iniciar servidor
app.listen(PORT, () => {
  console.log(`Servidor corriendo en http://localhost:${PORT}`);
  console.log(`API Endpoints:`);
  console.log(`   - heroes: http://localhost:${PORT}/api/heroes`);
  console.log(`   - villanos: http://localhost:${PORT}/api/villanos`);
  console.log(`   - equipos: http://localhost:${PORT}/api/equipos`);
  console.log(`   - antiequipos: http://localhost:${PORT}/api/antiequipos`);
});

export default app;