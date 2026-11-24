import { useCallback, useEffect, useState } from 'react';
import {
  Building2,
  CalendarClock,
  CheckCircle2,
  ClipboardList,
  Eye,
  EyeOff,
  LockKeyhole,
  MapPinned,
  Package,
  RefreshCw,
  Send,
  ShieldCheck,
  User,
  XCircle,
} from 'lucide-react';
import {
  confirmarEnvioCorreo,
  consultarUbicacionBolsines,
  filtrarBolsines,
  login,
  seleccionarBolsin,
} from '../services/api';
import FiltroBolsines from '../components/FiltroBolsines';
import MapaBolsines from '../components/MapaBolsines';
import ModalConfirmacion from '../components/ModalConfirmacion';
import Stepper from '../components/Stepper';
import TablaBolsines from '../components/TablaBolsines';

const CM_INICIAL = {
  codigoCM: 'CM-CBA-01',
  nombreCM: 'Comisión Médica Córdoba 1',
};

export function formatFechaHora(valor) {
  if (!valor) return '—';
  const fecha = new Date(valor);
  if (Number.isNaN(fecha.getTime())) return String(valor);
  return fecha.toLocaleString('es-AR', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
    hour12: false,
  });
}

const COLORES_ALERTA = {
  warning: { icon: XCircle, clase: 'alert-warning' },
  error: { icon: XCircle, clase: 'alert-error' },
  info: { icon: CheckCircle2, clase: 'alert-info' },
  success: { icon: CheckCircle2, clase: 'alert-success' },
};

function Alerta({ tipo = 'info', titulo, mensaje, onClose }) {
  const config = COLORES_ALERTA[tipo] || COLORES_ALERTA.info;
  const Icono = config.icon;

  return (
    <div className={`alert ${config.clase}`} role="alert">
      <Icono size={20} className="alert-icon" />
      <div className="alert-content">
        <strong className="alert-title">{titulo}</strong>
        <p className="alert-text">{mensaje}</p>
      </div>
      {onClose && (
        <button type="button" className="alert-close" onClick={onClose} aria-label="Cerrar aviso">
          ×
        </button>
      )}
    </div>
  );
}

const STEPS = [
  { id: 'INICIO_CONSULTA', nombre: 'Consultar Ubicación' },
  { id: 'MAPA_Y_SELECCION', nombre: 'Mapa y Selección' },
  { id: 'CONFIRMACION_CORREO', nombre: 'Confirmar Notificación' },
  { id: 'FIN_CU', nombre: 'Finalización' },
];

export default function PantecaEncargado() {
  const [pasoActual, setPasoActual] = useState('LOGIN');
  const [usuario, setUsuario] = useState('encargado1');
  const [contrasena, setContrasena] = useState('1234');
  const [mostrarContrasena, setMostrarContrasena] = useState(false);
  const [loadingLogin, setLoadingLogin] = useState(false);
  const [errorLogin, setErrorLogin] = useState('');

  const [comision, setComision] = useState(CM_INICIAL);
  const [numeroPrecinto, setNumeroPrecinto] = useState('');
  const [codigoCmDestino, setCodigoCmDestino] = useState('');
  const [ubicaciones, setUbicaciones] = useState([]);
  const [urlMapa, setUrlMapa] = useState('');

  const [loadingConsulta, setLoadingConsulta] = useState(false);
  const [loadingFiltro, setLoadingFiltro] = useState(false);
  const [loadingSeleccion, setLoadingSeleccion] = useState(false);
  const [loadingConfirmacion, setLoadingConfirmacion] = useState(false);

  const [alerta, setAlerta] = useState(null);
  const [resultadoEnvio, setResultadoEnvio] = useState(null);
  const [selectedBolsin, setSelectedBolsin] = useState(null);
  const [seleccionData, setSeleccionData] = useState(null);
  const [modalAbierto, setModalAbierto] = useState(false);

  const aplicarRespuesta = useCallback((data) => {
    setUbicaciones(Array.isArray(data?.ubicaciones) ? data.ubicaciones : []);
    setUrlMapa(data?.urlMapa || '');
    if (data?.codigoCM) {
      setComision({ codigoCM: data.codigoCM, nombreCM: data.nombreCM || data.codigoCM });
    }
  }, []);

  // ----- PASO 1: consultar ubicación de bolsines -----
  const ejecutarConsulta = async () => {
    setLoadingConsulta(true);
    setAlerta(null);
    setResultadoEnvio(null);
    setSelectedBolsin(null);

    try {
      const respuesta = await consultarUbicacionBolsines();
      if (respuesta.ok) {
        aplicarRespuesta(respuesta.data);
        setPasoActual('MAPA_Y_SELECCION');
      } else {
        // Flujo Alternativo A1: sin bolsines enviados
        aplicarRespuesta({ ...respuesta.data, ubicaciones: [] });
        setAlerta({
          tipo: 'warning',
          titulo: 'Flujo Alternativo A1 — Sin bolsines enviados',
          mensaje: respuesta.data?.error || 'No se encuentran bolsines en estado enviado.',
        });
      }
    } catch (error) {
      setUbicaciones([]);
      setUrlMapa('');
      setAlerta({ tipo: 'error', titulo: 'No se pudo completar la consulta', mensaje: error.message });
    } finally {
      setLoadingConsulta(false);
    }
  };

  // ----- PASO 2: mapa, filtros y selección -----
  const ejecutarFiltro = async () => {
    setLoadingFiltro(true);
    setAlerta(null);

    try {
      const respuesta = await filtrarBolsines({ numeroPrecinto, codigoCmDestino });
      if (respuesta.ok) {
        aplicarRespuesta(respuesta.data);
      } else {
        // Flujo Alternativo A2
        aplicarRespuesta({ ...respuesta.data, ubicaciones: [] });
        setAlerta({
          tipo: 'warning',
          titulo: 'Flujo Alternativo A2 — Sin coincidencias',
          mensaje: respuesta.data?.error || 'No se encuentra bolsín con el número de precinto o CM destino ingresado.',
        });
      }
    } catch (error) {
      setAlerta({ tipo: 'error', titulo: 'No se pudo aplicar el filtro', mensaje: error.message });
    } finally {
      setLoadingFiltro(false);
    }
  };

  const onSelect = useCallback(
    async (numeroBolsin) => {
      setSelectedBolsin(numeroBolsin);
      setLoadingSeleccion(true);
      setAlerta(null);
      try {
        const data = await seleccionarBolsin(numeroBolsin);
        setSeleccionData(data);
        setModalAbierto(true);
        setPasoActual('CONFIRMACION_CORREO');
      } catch (error) {
        setSelectedBolsin(null);
        setAlerta({ tipo: 'error', titulo: 'No se pudo seleccionar el bolsín', mensaje: error.message });
      } finally {
        setLoadingSeleccion(false);
      }
    },
    [setPasoActual]
  );

  // ----- PASO 3: confirmación de envío -----
  const onConfirmModal = useCallback(
    async (confirmado) => {
      setLoadingConfirmacion(true);
      try {
        const data = await confirmarEnvioCorreo(confirmado);
        if (data?.enviado === false) {
          // Flujo Alternativo A5
          setAlerta({
            tipo: 'info',
            titulo: 'Flujo Alternativo A5 — Envío cancelado',
            mensaje: data.mensaje || 'El envío de correo electrónico fue cancelado.',
          });
        } else {
          setResultadoEnvio(data);
          setAlerta(null);
        }
        setModalAbierto(false);
        setSeleccionData(null);
        setSelectedBolsin(null);
        setPasoActual('FIN_CU');
      } catch (error) {
        setAlerta({ tipo: 'error', titulo: 'No se pudo confirmar la notificación', mensaje: error.message });
        setModalAbierto(false);
      } finally {
        setLoadingConfirmacion(false);
      }
    },
    [setPasoActual]
  );

  const onVolverAMapa = () => {
    setPasoActual('MAPA_Y_SELECCION');
    setAlerta(null);
  };

  const onReiniciarConsulta = () => {
    setPasoActual('INICIO_CONSULTA');
    setUbicaciones([]);
    setUrlMapa('');
    setSelectedBolsin(null);
    setAlerta(null);
    setResultadoEnvio(null);
  };

  const onFinalizar = () => {
    onReiniciarConsulta();
  };

  const doLogin = async (nombreLimpio, pass) => {
    setLoadingLogin(true);
    setErrorLogin('');
    try {
      await login(nombreLimpio, pass);
      setPasoActual('INICIO_CONSULTA');
    } catch (error) {
      setErrorLogin(error.message);
    } finally {
      setLoadingLogin(false);
    }
  };

  const onLogin = (e) => {
    e?.preventDefault();
    const nombreLimpio = usuario.trim();
    if (!nombreLimpio || !contrasena) {
      setErrorLogin('Ingrese usuario y contraseña para continuar.');
      return;
    }
    doLogin(nombreLimpio, contrasena);
  };

  const onQuickLogin = () => doLogin('encargado1', '1234');

  const cerrarSesion = () => setPasoActual('LOGIN');

  useEffect(() => {
    if (!alerta) return undefined;
    const t = setTimeout(() => setAlerta(null), 12000);
    return () => clearTimeout(t);
  }, [alerta]);

  // ----- Login (Paso LOGIN) -----
  if (pasoActual === 'LOGIN') {
    return (
      <div className="login-shell login-shell--wizard">
        <div className="login-box">
          <div className="login-box-mark">
            <LockKeyhole size={24} />
          </div>
          <h2 className="login-box-title">Iniciar sesión</h2>
          <p className="login-box-subtitle">Acceso exclusivo para Encargados de Bolsines</p>
          <form className="login-form-vertical" onSubmit={onLogin} noValidate>
            <div className="form-group">
              <label className="form-label" htmlFor="wizard-usuario">Usuario</label>
              <div className="input-icon-wrap">
                <User size={16} className="input-icon" />
                <input id="wizard-usuario" className="form-control" value={usuario}
                  onChange={(e) => setUsuario(e.target.value)}
                  autoComplete="username" disabled={loadingLogin} />
              </div>
            </div>
            <div className="form-group">
              <label className="form-label" htmlFor="wizard-contrasena">Contraseña</label>
              <div className="input-icon-wrap">
                <LockKeyhole size={16} className="input-icon" />
                <input id="wizard-contrasena" type={mostrarContrasena ? 'text' : 'password'}
                  className="form-control has-action" value={contrasena}
                  onChange={(e) => setContrasena(e.target.value)} autoComplete="current-password"
                  disabled={loadingLogin} />
                <button type="button" className="input-action" onClick={() => setMostrarContrasena(!mostrarContrasena)}
                  tabIndex={-1}><EyeOff size={16} /></button>
              </div>
            </div>
            {errorLogin && <p className="login-error"><XCircle size={16} /> {errorLogin}</p>}
            <div className="login-actions">
              <button type="submit" className="btn btn-primary login-submit" disabled={loadingLogin}>
                {loadingLogin ? 'Verificando...' : 'Ingresar al sistema'}
              </button>
              <button type="button" className="btn btn-ghost login-quick" onClick={onQuickLogin} disabled={loadingLogin}>
                Ingresar como encargado1
              </button>
            </div>
          </form>
          <div className="login-demo">
            <span className="login-demo-label">Credenciales de prueba</span>
            <span>Usuario <code>encargado1</code> · Contraseña <code>1234</code></span>
          </div>
        </div>
      </div>
    );
  }

  // ----- Wizard del CU 36 -----
  return (
    <div className="app-shell">
      <header className="topbar">
        <div className="topbar-inner">
          <div className="brand">
            <div className="brand-mark"><ShieldCheck size={24} /></div>
            <div className="brand-text">
              <span className="brand-super">Correo Nacional</span>
              <span className="brand-system">Sistema de Gestión de Bolsines</span>
            </div>
          </div>
          <div className="topbar-right">
            <Stepper pasoActual={pasoActual} />
            <div className="cm-badge-fijo">
              <Building2 size={18} />
              <div className="cm-badge-fijo-text">
                <span className="cm-badge-fijo-label">Comisión Médica</span>
                <span className="cm-badge-fijo-value">{comision.codigoCM} · {comision.nombreCM}</span>
              </div>
            </div>
            <span className="session-chip"><span className="status-dot status-dot--online" />{usuario}</span>
            <button type="button" className="btn btn-ghost" onClick={cerrarSesion}><ShieldCheck size={15} />Cerrar sesión</button>
          </div>
        </div>
      </header>

      <main className="page">
        {/* PASO 1: INICIO_CONSULTA */}
        {pasoActual === 'INICIO_CONSULTA' && (
          <section className="welcome-card">
            <div className="card-header">
              <div className="card-title-group">
                <MapPinned size={18} className="icon-accent" />
                <div>
                  <h1 className="page-title">Consultar Seguimiento de Bolsines <span className="cu-tag">Caso de Uso 36</span></h1>
                  <p className="page-description">
                    Bienvenido/a {usuario}. Siga el flujo paso a paso para consultar la ubicación
                    de los bolsines en estado enviado y notificar al Gerente de la Comisión Médica
                    destino.
                  </p>
                </div>
              </div>
            </div>
            <div className="welcome-action">
              <button type="button" className="btn btn-primary btn-lg" onClick={ejecutarConsulta} disabled={loadingConsulta}>
                <RefreshCw size={17} className={loadingConsulta ? 'spin' : ''} />
                {loadingConsulta ? 'Consultando...' : 'Consultar Ubicación de Bolsines'}
              </button>
            </div>
          </section>
        )}

        {alerta && <Alerta tipo={alerta.tipo} titulo={alerta.titulo} mensaje={alerta.mensaje} onClose={() => setAlerta(null)} />}

        {/* PASO 2: MAPA_Y_SELECCION */}
        {pasoActual === 'MAPA_Y_SELECCION' && (
          <div className="mapa-y-seleccion">
            <FiltroBolsines
              numeroPrecinto={numeroPrecinto}
              setNumeroPrecinto={setNumeroPrecinto}
              codigoCmDestino={codigoCmDestino}
              setCodigoCmDestino={setCodigoCmDestino}
              onFiltrar={ejecutarFiltro}
              onLimpiar={() => { setNumeroPrecinto(''); setCodigoCmDestino(''); ejecutarConsulta(); }}
              onReiniciarConsulta={onReiniciarConsulta}
              loading={loadingFiltro}
            />
            <div className="content-grid">
              <MapaBolsines ubicaciones={ubicaciones} selectedBolsin={selectedBolsin}
                onSelectBolsin={onSelect} urlMapa={urlMapa} formatFechaHora={formatFechaHora} />
              <TablaBolsines ubicaciones={ubicaciones} selectedBolsin={selectedBolsin}
                onSelectBolsin={onSelect} loading={loadingFiltro} formatFechaHora={formatFechaHora} />
            </div>
            {ubicaciones.length > 0 && (
              <div className="step2-note"><Send size={15} />
                <span><strong>Paso 7:</strong> pulse “Seleccionar Bolsín” en el mapa o en la tabla.</span>
              </div>
            )}
          </div>
        )}

        {/* PASO 3: CONFIRMACION_CORREO (mapa atenuado + modal) */}
        {pasoActual === 'CONFIRMACION_CORREO' && (
          <>
            <div className="content-grid blocked">
              <MapaBolsines ubicaciones={ubicaciones} selectedBolsin={selectedBolsin}
                onSelectBolsin={onSelect} urlMapa={urlMapa} formatFechaHora={formatFechaHora} />
              <TablaBolsines ubicaciones={ubicaciones} selectedBolsin={selectedBolsin}
                onSelectBolsin={onSelect} formatFechaHora={formatFechaHora} />
            </div>
            <ModalConfirmacion isOpen={true} seleccionData={seleccionData}
              loading={loadingConfirmacion} onConfirm={onConfirmModal}
              extraActions={[{ label: 'Volver a elegir otro bolsín', className: 'btn-tertiary', onClick: onVolverAMapa }]} />
          </>
        )}

        {/* PASO 4: FIN_CU */}
        {pasoActual === 'FIN_CU' && (
          <section className="fin-card">
            {resultadoEnvio ? (
              <div className="success-card">
                <div className="success-header">
                  <div className="success-icon"><CheckCircle2 size={26} /></div>
                  <div>
                    <h2 className="success-title">Correo electrónico enviado correctamente</h2>
                    <p className="success-subtitle">{resultadoEnvio.mensaje || 'Ejecución exitosa del Caso de Uso 31: Notificar ubicación de bolsín.'}</p>
                  </div>
                </div>
                <div className="success-grid">
                  <div className="success-item"><span className="success-item-label"><MapPinned size={14} />Correo GCM destino</span><span className="success-item-value">{resultadoEnvio.emailGcmDestino || '—'}</span></div>
                  <div className="success-item"><span className="success-item-label"><Package size={14} />Bolsín notificado</span><span className="success-item-value">N° {resultadoEnvio.numeroBolsin ?? '—'}</span></div>
                  <div className="success-item"><span className="success-item-label"><MapPinned size={14} />Coordenadas informadas</span><span className="success-item-value mono">{resultadoEnvio.latitud ?? '—'}, {resultadoEnvio.longitud ?? '—'}</span></div>
                  <div className="success-item"><span className="success-item-label"><CalendarClock size={14} />Última actualización</span><span className="success-item-value">{formatFechaHora(resultadoEnvio.fechaHoraUltimaActualizacion)}</span></div>
                </div>
              </div>
            ) : (
              <Alerta tipo="info" titulo="Flujo Alternativo A5 — Envío cancelado"
                mensaje="El envío de correo electrónico fue cancelado." />
            )}
            <button type="button" className="btn btn-primary btn-lg fin-cta" onClick={onFinalizar}>
              <RefreshCw size={15} />Finalizar / Realizar nueva consulta
            </button>
          </section>
        )}
      </main>

      <footer className="page-footer">
        <span>Correo Nacional · Sistema de Gestión de Bolsines · Caso de Uso 36</span>
        <span className="footer-dot" />
        <span>Servicio backend: http://localhost:8081/api</span>
      </footer>
    </div>
  );
}
