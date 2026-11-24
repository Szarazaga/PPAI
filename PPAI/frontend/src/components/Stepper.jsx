import { Check } from 'lucide-react';

const STEPS = [
  { id: 'INICIO_CONSULTA', nombre: 'Consultar Ubicación' },
  { id: 'MAPA_Y_SELECCION', nombre: 'Mapa y Selección' },
  { id: 'CONFIRMACION_CORREO', nombre: 'Confirmar Notificación' },
  { id: 'FIN_CU', nombre: 'Finalización' },
];

export default function Stepper({ pasoActual }) {
  const idx = STEPS.findIndex((s) => s.id === pasoActual);

  return (
    <nav className="stepper" aria-label="Flujo del Caso de Uso 36">
      {STEPS.map((step, i) => {
        const estado = i < idx ? 'completed' : i === idx ? 'active' : 'pending';
        return (
          <div className="stepper-step" key={step.id}>
            {i > 0 && (
              <span className={`step-line ${i <= idx ? 'done' : ''}`} aria-hidden="true" />
            )}
            <div className={`step-marker ${estado}`}>
              {estado === 'completed'
                ? <Check size={13} strokeWidth={3} />
                : estado === 'active'
                  ? step.nombre
                  : i + 1}
            </div>
            {estado !== 'active' && (
              <span className={`step-label ${estado}`}>{step.nombre}</span>
            )}
          </div>
        );
      })}
    </nav>
  );
}
