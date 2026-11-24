import { Package, Send, MapPin, Clock, Navigation } from 'lucide-react';

export default function TablaBolsines({
  ubicaciones = [],
  selectedBolsin,
  onSelectBolsin,
  loading,
  formatFechaHora,
}) {
  if (loading) {
    return (
      <div className="card table-card">
        <div className="skeleton-table">
          {[...Array(4)].map((_, i) => (
            <div key={i} className="skeleton-row" />
          ))}
        </div>
      </div>
    );
  }

  return (
    <div className="card table-card">
      <div className="card-header">
        <div className="card-title-group">
          <Package size={18} className="icon-accent" />
          <div>
            <h2 className="card-title">Bolsines en Viaje</h2>
            <p className="card-subtitle">
              Detalle de ubicaciones recibidas desde el servicio de seguimiento GPS.
            </p>
          </div>
        </div>
        <span className="pill-count">{ubicaciones.length} registros</span>
      </div>

      {ubicaciones.length === 0 ? (
        <div className="empty-state">
          <Package size={36} className="empty-icon" />
          <p className="empty-title">Sin bolsines para mostrar</p>
          <p className="empty-text">
            Ejecute la consulta o ajuste los filtros para visualizar los bolsines en estado enviado.
          </p>
        </div>
      ) : (
        <>
          <div className="table-responsive">
            <table className="data-table">
              <thead>
                <tr>
                  <th>N° Bolsín</th>
                  <th>Latitud</th>
                  <th>Longitud</th>
                  <th>Última Actualización</th>
                  <th className="text-right">Acción</th>
                </tr>
              </thead>
              <tbody>
                {ubicaciones.map(([numeroBolsin, lat, lng, fechaHora]) => {
                  const isSelected = String(selectedBolsin) === String(numeroBolsin);
                  return (
                    <tr
                      key={numeroBolsin}
                      className={isSelected ? 'row-selected' : ''}
                      onClick={() => onSelectBolsin(numeroBolsin)}
                      title="Haga clic para seleccionar este bolsín"
                    >
                      <td>
                        <span className="bolsin-badge">#{numeroBolsin}</span>
                      </td>
                      <td className="mono-cell">
                        <Navigation size={13} className="inline-icon" />
                        {lat}
                      </td>
                      <td className="mono-cell">
                        <Navigation size={13} className="inline-icon" />
                        {lng}
                      </td>
                      <td>
                        <span className="date-cell">
                          <Clock size={13} className="inline-icon" />
                          {formatFechaHora(fechaHora)}
                        </span>
                      </td>
                      <td className="text-right">
                        <button
                          type="button"
                          className="btn btn-sm btn-outline"
                          onClick={(e) => {
                            e.stopPropagation();
                            onSelectBolsin(numeroBolsin);
                          }}
                        >
                          <Send size={14} />
                          Seleccionar Bolsín
                        </button>
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </div>

          <div className="mobile-cards">
            {ubicaciones.map(([numeroBolsin, lat, lng, fechaHora]) => {
              const isSelected = String(selectedBolsin) === String(numeroBolsin);
              return (
                <div
                  key={numeroBolsin}
                  className={`mobile-card ${isSelected ? 'row-selected' : ''}`}
                  onClick={() => onSelectBolsin(numeroBolsin)}
                >
                  <div className="mobile-card-top">
                    <span className="bolsin-badge">#{numeroBolsin}</span>
                    <span className="date-cell">
                      <Clock size={12} className="inline-icon" />
                      {formatFechaHora(fechaHora)}
                    </span>
                  </div>
                  <div className="mobile-card-coords">
                    <span>
                      <MapPin size={13} className="inline-icon" />
                      {lat}
                    </span>
                    <span>
                      <MapPin size={13} className="inline-icon" />
                      {lng}
                    </span>
                  </div>
                  <button
                    type="button"
                    className="btn btn-sm btn-outline btn-block"
                    onClick={(e) => {
                      e.stopPropagation();
                      onSelectBolsin(numeroBolsin);
                    }}
                  >
                    <Send size={14} />
                    Seleccionar Bolsín
                  </button>
                </div>
              );
            })}
          </div>
        </>
      )}
    </div>
  );
}
