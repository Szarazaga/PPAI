import { useEffect, useRef } from 'react';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import { ExternalLink, MapPin } from 'lucide-react';

function createPinIcon(numeroBolsin, isSelected) {
  const bg = isSelected ? '#0284c7' : '#0f2942';
  const border = isSelected ? '#bae6fd' : '#ffffff';
  const scale = isSelected ? 'transform: scale(1.12);' : '';

  return L.divIcon({
    className: 'custom-bolsin-marker',
    html: `
      <div style="
        display: flex;
        align-items: center;
        justify-content: center;
        min-width: 36px;
        height: 28px;
        padding: 0 8px;
        background: ${bg};
        color: #ffffff;
        border: 2px solid ${border};
        border-radius: 9999px;
        font-family: system-ui, -apple-system, sans-serif;
        font-size: 12px;
        font-weight: 700;
        box-shadow: 0 3px 10px rgba(15, 41, 66, 0.35);
        cursor: pointer;
        white-space: nowrap;
        ${scale}
      ">
        #${numeroBolsin}
      </div>
    `,
    iconSize: [44, 28],
    iconAnchor: [22, 14],
    popupAnchor: [0, -14],
  });
}

export default function MapaBolsines({
  ubicaciones = [],
  selectedBolsin,
  onSelectBolsin,
  urlMapa,
  formatFechaHora,
}) {
  const mapContainerRef = useRef(null);
  const mapInstanceRef = useRef(null);
  const markersLayerRef = useRef(null);
  const onSelectRef = useRef(onSelectBolsin);
  const mapTimeoutRef = useRef(null);

  useEffect(() => {
    onSelectRef.current = onSelectBolsin;
  }, [onSelectBolsin]);

  useEffect(() => {
    if (!mapContainerRef.current || mapInstanceRef.current) return;

    const map = L.map(mapContainerRef.current, {
      center: [-34.6037, -63.6167],
      zoom: 5,
      zoomControl: true,
    });

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; OpenStreetMap contributors',
      maxZoom: 18,
    }).addTo(map);

    const markersLayer = L.layerGroup().addTo(map);

    mapInstanceRef.current = map;
    markersLayerRef.current = markersLayer;

    // Recalcular el tamaño real de Leaflet para que se ajuste al nuevo contenedor.
    map.invalidateSize();
    mapTimeoutRef.current = setTimeout(() => {
      map.invalidateSize();
    }, 120);

    return () => {
      if (mapTimeoutRef.current) clearTimeout(mapTimeoutRef.current);
      map.remove();
      mapInstanceRef.current = null;
      markersLayerRef.current = null;
    };
  }, []);

  useEffect(() => {
    const map = mapInstanceRef.current;
    const markersLayer = markersLayerRef.current;
    if (!map || !markersLayer) return;

    markersLayer.clearLayers();

    const validPoints = [];

    ubicaciones.forEach((item) => {
      const [numeroBolsin, latStr, lngStr, fechaHora] = item;
      const lat = parseFloat(latStr);
      const lng = parseFloat(lngStr);

      if (Number.isNaN(lat) || Number.isNaN(lng)) return;

      validPoints.push([lat, lng]);
      const isSelected = String(selectedBolsin) === String(numeroBolsin);

      const marker = L.marker([lat, lng], {
        icon: createPinIcon(numeroBolsin, isSelected),
      });

      const formattedDate = formatFechaHora ? formatFechaHora(fechaHora) : fechaHora;

      marker.bindTooltip(
        `<div style="font-size:12px; line-height:1.4;">
          <strong>Bolsín N° ${numeroBolsin}</strong><br/>
          Lat: ${latStr} | Lng: ${lngStr}<br/>
          Actualización: ${formattedDate}<br/>
          <em style="color:#0284c7;">Clic para seleccionar</em>
        </div>`,
        { direction: 'top', offset: [0, -10] }
      );

      marker.on('click', () => {
        if (onSelectRef.current) {
          onSelectRef.current(numeroBolsin);
        }
      });

      marker.addTo(markersLayer);
    });

    // Recalcular tamaño y ajustar el encuadre centralizando los marcadores.
    const encuadrar = () => {
      map.invalidateSize();

      if (validPoints.length === 1) {
        map.setView(validPoints[0], 11, { animate: true });
      } else if (validPoints.length > 1) {
        const bounds = L.latLngBounds(validPoints);
        map.fitBounds(bounds, { padding: [45, 45], maxZoom: 7, animate: true });
      } else {
        map.setView([-34.6037, -63.6167], 5, { animate: true });
      }
    };

    encuadrar();
    if (mapTimeoutRef.current) clearTimeout(mapTimeoutRef.current);
    mapTimeoutRef.current = setTimeout(encuadrar, 120);
  }, [ubicaciones, selectedBolsin, formatFechaHora]);

  return (
    <div className="card map-card">
      <div className="card-header">
        <div className="card-title-group">
          <MapPin size={18} className="icon-accent" />
          <div>
            <h2 className="card-title">Mapa de Ubicación de Bolsines</h2>
            <p className="card-subtitle">
              Haga clic en un marcador del mapa para seleccionar el bolsín y notificar al GCM destino.
            </p>
          </div>
        </div>
        <span className="pill-count">
          {ubicaciones.length} {ubicaciones.length === 1 ? 'bolsín' : 'bolsines'}
        </span>
      </div>

      <div className="map-wrapper">
        <div ref={mapContainerRef} className="leaflet-container-box" />
        {ubicaciones.length === 0 && (
          <div className="map-empty-overlay">
            <p>No hay coordenadas para mostrar en el mapa.</p>
          </div>
        )}
      </div>

      {urlMapa && (
        <div className="map-footer">
          <span className="map-footer-note">
            Mapa estático generado por el servicio externo (Google Maps):
          </span>
          <a
            href={urlMapa}
            target="_blank"
            rel="noopener noreferrer"
            className="map-external-link"
          >
            Ver URL de mapa estático <ExternalLink size={14} />
          </a>
        </div>
      )}
    </div>
  );
}
