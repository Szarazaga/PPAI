import { Filter, RefreshCw, RotateCcw, Search } from 'lucide-react';

const CM_DESTINO_OPCIONES = [
  { value: '', label: '-- Todas las Comisiones Médicas --' },
  { value: 'CM-ROS-02', label: 'CM-ROS-02 (Rosario 2)' },
  { value: 'CM-MDZ-03', label: 'CM-MDZ-03 (Mendoza 3)' },
  { value: 'CM-BA-01', label: 'CM-BA-01 (C.A.B.A. 1)' },
  { value: 'CM-CBA-01', label: 'CM-CBA-01 (Córdoba 1)' },
  { value: 'CM-CBA-02', label: 'CM-CBA-02 (Córdoba 2)' },
  { value: 'CM-TUC-01', label: 'CM-TUC-01 (Tucumán 1)' },
  { value: 'CM-SLA-01', label: 'CM-SLA-01 (Salta 1)' },
  { value: 'CM-JUJ-01', label: 'CM-JUJ-01 (Jujuy 1)' },
  { value: 'CM-NQN-01', label: 'CM-NQN-01 (Neuquén 1)' },
  { value: 'CM-MDQ-01', label: 'CM-MDQ-01 (Mar del Plata 1)' },
  { value: 'CM-LPL-01', label: 'CM-LPL-01 (La Plata 1)' },
];

export default function FiltroBolsines({
  numeroPrecinto,
  setNumeroPrecinto,
  codigoCmDestino,
  setCodigoCmDestino,
  onFiltrar,
  onLimpiar,
  loading,
  onReiniciarConsulta,
}) {
  // Múltiples criterios de filtro simultáneos están excluidos.
  const precinto = String(numeroPrecinto ?? '').trim();
  const destino = String(codigoCmDestino ?? '').trim();

  // Si el input tiene texto, el select queda bloqueado; si el select tiene valor, el input queda bloqueado.
  const precintoDesabilitado = destino !== '' || loading;
  const destinoDesabilitado = precinto !== '' || loading;

  const handlePrecintoChange = (e) => {
    const v = e.target.value;
    setNumeroPrecinto(v);
    // Al borrar el texto del precinto, la CM destino vuelve a su opción vacía.
    if (!v.trim() && destino !== '') {
      setCodigoCmDestino('');
    }
  };

  const handleDestinoChange = (e) => {
    const v = e.target.value;
    setCodigoCmDestino(v);
    if (v !== '' && precinto !== '') {
      setNumeroPrecinto('');
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (loading) return;
    if (precinto !== '' && destino !== '') return; // excluidos mediante bloqueo; double check
    onFiltrar();
  };

  return (
    <div className="card filter-card">
      <div className="card-header">
        <div className="card-title-group">
          <Filter size={18} className="icon-accent" />
          <div>
            <h2 className="card-title">Filtros de Búsqueda</h2>
            <span className="card-subtitle">
              Filtrar por número de precinto o por CM destino (un solo criterio a la vez).
            </span>
          </div>
        </div>
        {onReiniciarConsulta && (
          <button type="button" className="btn btn-outline" onClick={onReiniciarConsulta}>
            <RefreshCw size={15} />
            Reiniciar Consulta
          </button>
        )}
      </div>

      <form className="filter-form" onSubmit={handleSubmit}>
        <div className="filter-row">
          <div className="form-group filter-group-precinto">
            <label htmlFor="numeroPrecinto" className="form-label">
              Número de Precinto:
            </label>
            <input
              id="numeroPrecinto"
              type="number"
              placeholder="Ej: 5001"
              value={numeroPrecinto}
              onChange={handlePrecintoChange}
              className="form-control"
              min="1"
              step="1"
              disabled={precintoDesabilitado}
            />
          </div>

          <div className="form-group filter-group-destino">
            <label htmlFor="codigoCmDestino" className="form-label">
              Comisión Médica Destino:
            </label>
            <select
              id="codigoCmDestino"
              value={codigoCmDestino}
              onChange={handleDestinoChange}
              className="form-control"
              disabled={destinoDesabilitado}
            >
              {CM_DESTINO_OPCIONES.map((op) => (
                <option key={op.value} value={op.value}>
                  {op.label}
                </option>
              ))}
            </select>
          </div>

          <div className="filter-actions">
            <button type="button" className="btn btn-secondary" onClick={onLimpiar} disabled={loading}>
              <RotateCcw size={16} />
              Limpiar Filtros
            </button>
            <button type="submit" className="btn btn-primary" disabled={loading}>
              <Search size={16} />
              {loading ? 'Buscando...' : 'Filtrar'}
            </button>
          </div>
        </div>
      </form>
    </div>
  );
}
