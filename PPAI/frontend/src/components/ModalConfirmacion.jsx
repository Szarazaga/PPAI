import { Mail, X, CheckCircle2, XCircle, Package } from 'lucide-react';

export default function ModalConfirmacion({
  isOpen,
  seleccionData,
  loading,
  onConfirm,
  extraActions = [],
}) {
  if (!isOpen || !seleccionData) return null;

  return (
    <div className="modal-backdrop" role="dialog" aria-modal="true" aria-labelledby="modal-title">
      <div className="modal-panel">
        <div className="modal-header">
          <div className="modal-title-row">
            <div className="modal-icon-badge">
              <Mail size={20} />
            </div>
            <div>
              <h3 id="modal-title" className="modal-title">
                Confirmar Notificación al Gerente
              </h3>
              <span className="modal-subtitle">Caso de Uso 31: Notificar ubicación de bolsín</span>
            </div>
          </div>
          <button
            type="button"
            className="modal-close-btn"
            onClick={() => onConfirm(false)}
            disabled={loading}
            aria-label="Cancelar envío"
          >
            <X size={18} />
          </button>
        </div>

        <div className="modal-body">
          <div className="modal-bolsin-chip">
            <Package size={16} />
            <span>Bolsín seleccionado:</span>
            <strong>N° {seleccionData.numeroBolsin}</strong>
          </div>

          <p className="modal-message">{seleccionData.mensajeConfirmacion}</p>
        </div>

        <div className="modal-footer">
          <button
            type="button"
            className="btn btn-secondary"
            onClick={() => onConfirm(false)}
            disabled={loading}
          >
            <XCircle size={16} />
            Cancelar
          </button>
          {extraActions.map((action) => (
            <button
              key={action.label}
              type="button"
              className={action.className || 'btn btn-tertiary'}
              onClick={action.onClick}
              disabled={loading}
            >
              {action.icon ? <action.icon size={14} /> : null}
              {action.label}
            </button>
          ))}

          <button
            type="button"
            className="btn btn-primary"
            onClick={() => onConfirm(true)}
            disabled={loading}
          >
            <CheckCircle2 size={16} />
            {loading ? 'Enviando correo...' : 'Confirmar y Enviar Correo'}
          </button>
        </div>
      </div>
    </div>
  );
}
